import java.util.ArrayList;

import oscP5.OscMessage;

public class ZoneCollection implements TuioObserver {

	AhTextContext ahTextContext;

	private TouchZone touchzone;
	private int touchEventIsInsideThisZone;
	@SuppressWarnings("unused")
	private TuioSubject tuioHandler;
	OSCHandler oscHandler;

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
		this.tuioHandler = ahTextContext.tuioHandler;
		this.oscHandler = ahTextContext.oscHandler;
	}

	public void tuioCursorAdded(long sessionID, int cursorX, int cursorY) {
		createNewZone(sessionID, cursorX, cursorY, 300, 200);
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
				OscMessage myMessage = new OscMessage("/animate/newowner");
				myMessage.add((int)zoneName);
				myMessage.add((int)tzones.get(touchEventIsInsideThisZone).getZoneName());
				myMessage.add(cursorX);
				myMessage.add(cursorY);
				oscHandler.sendOSCMessage(myMessage);

				tzones.get(touchEventIsInsideThisZone).setZoneName(zoneName);
				sessionIDs.add(zoneName);
				tzones.get(touchEventIsInsideThisZone).setX(cursorX); 			// then update position
				tzones.get(touchEventIsInsideThisZone).setY(cursorY);
			}
		} else if (cursorY > ahTextContext.height - 100 && sessionIDIsNotAlive(zoneName)) {
			//Pack and send OSC
			OscMessage myMessage = new OscMessage("/animate/new");
			myMessage.add((int)zoneName);
			myMessage.add(cursorX);
			myMessage.add(cursorY);
			oscHandler.sendOSCMessage(myMessage);

			touchzone = new TouchZone(zoneName, cursorX, cursorY, width, height);
			tzones.add(touchzone);
			sessionIDs.add(zoneName);
		} else if (cursorX < 100 && cursorY < 100) {
			//Pack and send OSC
			OscMessage myMessage = new OscMessage("/animate/killall");
			myMessage.add(1);		
			oscHandler.sendOSCMessage(myMessage);

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
				OscMessage myMessage = new OscMessage("/animate/update");
				myMessage.add((int)zoneName);
				myMessage.add(cursorX);
				myMessage.add(cursorY);		
				oscHandler.sendOSCMessage(myMessage);

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
			if (Math.abs(tzones.get(i).getX() - cursorX) < tzones.get(i)
					.getWidth()
					&& Math.abs(tzones.get(i).getY() - cursorY) < tzones.get(i)
					.getHeight()) {
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
