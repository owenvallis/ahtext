import java.util.ArrayList;

import oscP5.OscMessage;

public class ZoneCollection implements TuioObserver {

	AhTextContext ahTextContext;

	private TouchZone touchzone;
	private int touchEventIsInsideThisZone;

	ArrayList<TouchZone> tzones;
	ArrayList<Long> sessionIDs;

	/**
	 * Class constructor for a new collection of active zones
	 * 
	 */
	public ZoneCollection(AhTextContext ahTextContext) {
		this.ahTextContext = ahTextContext;
		this.tzones = new ArrayList<TouchZone>();
		this.sessionIDs = new ArrayList<Long>();
	}

	public void tuioCursorAdded(long sessionID, int cursorX, int cursorY) {
		createNewZone(sessionID, cursorX, cursorY, (int)(ahTextContext.width * 0.21), (int)(ahTextContext.height * 0.22));
	}

	public void tuioCursorUpdate(long sessionID, int cursorX, int cursorY) {
		updateZone(sessionID, cursorX, cursorY);
	}

	public void tuioCursorRemove(long sessionID) {
		RemoveSessionID(sessionID);
	}



	/**
	 * Receives a new TUIO event and determines if a new zone needs to be
	 * created, an existing zone needs a new owner, or the TUIO event should be
	 * ignored.
	 * 
	 * @param zoneName
	 *            The sessionID for the zone as a string
	 * @param cursorX
	 *            The current X cursor position on the screen in pixels
	 * @param cursorY
	 *            The current Y cursor position on the screen in pixels
	 * @param width
	 *            The width of the new Zone
	 * @param height
	 *            The height of the new Zone
	 */
	public void createNewZone(long zoneName, int cursorX, int cursorY,
			int width, int height) {

		if (cursurIsInsideAnExistingZone(cursorX, cursorY)) {
			if (zoneDoesNotHaveAnActiveOwner(touchEventIsInsideThisZone)) {
				//Pack and send OSC
				ahTextContext.myMessage = new OscMessage("/animate/newowner");
				ahTextContext.myMessage.add((int)zoneName);
				ahTextContext.myMessage.add((int)tzones.get(touchEventIsInsideThisZone).getZoneName());
				ahTextContext.myMessage.add(cursorX);
				ahTextContext.myMessage.add(cursorY);
				ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);

				tzones.get(touchEventIsInsideThisZone).setZoneName(zoneName);
				sessionIDs.add(zoneName);
				tzones.get(touchEventIsInsideThisZone).setX(cursorX); 			// then update position
				tzones.get(touchEventIsInsideThisZone).setY(cursorY);
			}
		} else if (cursorY > ahTextContext.height - (int)(ahTextContext.height * 0.11) && sessionIDIsNotAlive(zoneName)) {
			//Pack and send OSC
			ahTextContext.myMessage = new OscMessage("/animate/new");
			ahTextContext.myMessage.add((int)zoneName);
			ahTextContext.myMessage.add(cursorX);
			ahTextContext.myMessage.add(cursorY);
			ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);

			touchzone = new TouchZone(zoneName, cursorX, cursorY, width, height);
			tzones.add(touchzone);
			sessionIDs.add(zoneName);
		} else if (cursorX < (int)(ahTextContext.width * 0.07) && cursorY < (int)(ahTextContext.height * 0.11)) {
			//Pack and send OSC
			ahTextContext.myMessage = new OscMessage("/animate/killall");
			ahTextContext.myMessage.add(1);		
			ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);
			KillAllActiveZonesAndIDs();
		} else if (cursorX > (int)(ahTextContext.width * 0.9) && cursorY < (int)(ahTextContext.height * 0.11)) {
			//Pack and send OSC
			ahTextContext.myMessage = new OscMessage("/animate/killall");
			ahTextContext.myMessage.add(1);		
			ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);
			KillAllActiveZonesAndIDs();
		}
	}

	/**
	 * If TUIO event is tied to an Active Zone then update the X and Y
	 * coordinates
	 * 
	 * @param zoneName
	 *            The sessionID for the zone as a string
	 * @param cursorX
	 *            The current X cursor position on the screen in pixels
	 * @param cursorY
	 *            The current Y cursor position on the screen in pixels
	 */
	public void updateZone(long zoneName, int cursorX, int cursorY) {

		for (int i = 0; i < tzones.size(); i++) {
			if (tzones.get(i).getZoneName() == zoneName) { 	// if the TUIO event is an owner of one of the Zones
				//Pack and send OSC
				ahTextContext.myMessage = new OscMessage("/animate/update");
				ahTextContext.myMessage.add((int)zoneName);
				ahTextContext.myMessage.add(cursorX);
				ahTextContext.myMessage.add(cursorY);		
				ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);

				tzones.get(i).setX(cursorX); 			// then update position
				tzones.get(i).setY(cursorY);
				break;
			}
		}

	}

	/**
	 * Remove the Session ID as the TUIO event is removed from the screen
	 * 
	 * @param zoneName
	 *            The sessionID for the zone as a string
	 */
	public void RemoveSessionID(long zoneName) {
		sessionIDs.remove(zoneName);
	}



	/**
	 * Determines if a TUIO event is inside of an existing Zone by comparing the
	 * position of the incoming TUIO event against the boundaries of all the
	 * zones within the collection
	 * 
	 * @param cursorX
	 *            The current X cursor position on the screen in pixels
	 * @param cursorY
	 *            The current Y cursor position on the screen in pixels
	 * @return Whether the current cursor event is within an existing zone
	 */
	private boolean cursurIsInsideAnExistingZone(int cursorX, int cursorY) {

		boolean insideExistingZone = false;

		for (int i = 0; i < tzones.size(); i++) {
			if (Math.abs(tzones.get(i).getX() - cursorX) < tzones.get(i).getWidth() 
					&& Math.abs(tzones.get(i).getY() - cursorY) < tzones.get(i).getHeight()) {
				insideExistingZone = true;
				touchEventIsInsideThisZone = i;
				break;
			}
		}
		return insideExistingZone;
	}

	/**
	 * Determines if the current zone still has an active TUIO event as its
	 * owner by comparing the sessionID passed in to the method against the list
	 * of active touchZone IDs
	 * 
	 * @param index
	 *            The index position of the Zone which the current TUIO event
	 *            falls inside of, within the collection of Zones
	 * @return Whether owner is still alive or not
	 */
	private boolean zoneDoesNotHaveAnActiveOwner(int index) {

		boolean noActiveOwner = true;

		for (int j = 0; j < sessionIDs.size(); j++) {
			if (sessionIDs.get(j) == tzones.get(index).getZoneName()) {
				noActiveOwner = false;
				break;
			}
		}
		return noActiveOwner;
	}

	/**
	 * Determines if the current sessionID still has an active TUIO event as its
	 * owner by comparing the sessionID passed in to the method against the list
	 * of active touchZone IDs
	 * 
	 * @param sessionID
	 *            The sessionID of the current TUIO event           
	 * @return Whether owner is still alive or not
	 */
	private boolean sessionIDIsNotAlive(long sessionID) {

		boolean noActiveOwner = true;

		for (int j = 0; j < sessionIDs.size(); j++) {
			if (sessionIDs.get(j) == sessionID) {
				noActiveOwner = false;
				break;
			}
		}
		return noActiveOwner;
	}



	/**
	 * Clears all members of both the touchZone collection and the active
	 * sessionIDs Collection
	 */
	public void KillAllActiveZonesAndIDs() {
		tzones.clear();
		sessionIDs.clear();
	}
}
