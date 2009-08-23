import java.util.ArrayList;
import processing.core.PApplet;

public class ZoneCollection implements TUIOObserver {

	PApplet parent;

	private TouchZone touchzone;
	private int touchEventIsInsideThisZone;
	@SuppressWarnings("unused")
	private TUIOSubject tuioHandler;

	ArrayList<TouchZone> tzones;
	long[] sessionIDs;

	/**
	 * Class constructor for a new collection of active zones
	 * 
	 */
	public ZoneCollection(PApplet parent, TUIOSubject tuioHandler) {
		this.parent = parent;
		this.tzones = new ArrayList<TouchZone>();
		this.sessionIDs = new long[0];
		this.tuioHandler = tuioHandler;
	}

	public void tuioCursorAdded(long sessionID, int cursorX, int cursorY) {
		createNewZone(sessionID, cursorX, cursorY, 200, 300);
	}
	
	public void tuioCursorUpdate(long sessionID, int cursorX, int cursorY) {
		System.out.println(sessionID);
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
				tzones.get(touchEventIsInsideThisZone).setZoneName(zoneName);
				sessionIDs = (long[]) PApplet.append(sessionIDs, zoneName);
			}
		} else if (cursorY > parent.height - 100) {
			touchzone = new TouchZone(zoneName, cursorX, cursorY, width, height);
			tzones.add(touchzone);
			sessionIDs = (long[]) PApplet.append(sessionIDs, zoneName);
		} else if (cursorX < 100 && cursorY < 100) {
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
			System.out.println(tzones.get(i).getZoneName()+"  "+zoneName);
			if (tzones.get(i).getZoneName() == zoneName) { 	// if the TUIO event is an owner of one of the Zones 	
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

		for (int j = 0; j < sessionIDs.length; j++) {
			if (sessionIDs[j] != 0) {
				if (sessionIDs[j] == zoneName) { // remove zone
					long[] front = (long[]) PApplet.subset(sessionIDs, 0, j);
					long[] back = (long[]) PApplet.subset(sessionIDs, j + 1, (sessionIDs.length) - j - 1);
					sessionIDs =  (long[]) PApplet.concat(front, back);
					break;
				}			
			}
		}
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

		for (int j = 0; j < sessionIDs.length; j++) {
			if (sessionIDs[j] == tzones.get(index).getZoneName()) {
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
		while (sessionIDs.length > 0) {
		sessionIDs = (long[]) PApplet.shorten(sessionIDs);
		}
	}
}
