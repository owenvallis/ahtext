import java.util.ArrayList;

import processing.core.PApplet;

public class ZoneCollection {

	PApplet parent;

	private TouchZone touchzone;
	private int touchEventIsInsideThisZone;

	ArrayList<TouchZone> tzones;
	ArrayList<String> sessionIDs;

	/**
	 * Class constructor for a new collection of active zones
	 * 
	 */
	public ZoneCollection(PApplet parent) {
		this.parent = parent;
		this.tzones = new ArrayList<TouchZone>();
		this.sessionIDs = new ArrayList<String>();
	}

	/**
	 * Receives a new TUIO event and determines if a new zone needs to be created, 
	 * an existing zone needs a new owner, 
	 * or the TUIO event should be ignored.
	 * 
	 * @param zoneName	The sessionID for the zone as a string
	 * @param cursorX	The current X cursor position on the screen in pixels 
	 * @param cursorY	The current Y cursor position on the screen in pixels
	 * @param width		The width of the new Zone
	 * @param height	The height of the new Zone
	 */
	public void createNewZone(String zoneName, int cursorX, int cursorY, int width, int height) {

		if(cursurIsInsideAnExistingZone(cursorX, cursorY)) { 
			if(zoneDoesNotHaveAnActiveOwner(touchEventIsInsideThisZone)){
				tzones.get(touchEventIsInsideThisZone).setZoneName(zoneName);
				sessionIDs.add(zoneName);
			}
		}
		else if(cursorY < parent.height-100) { 
			touchzone = new TouchZone(zoneName, cursorX, cursorY, width, height);
			tzones.add(touchzone);
			sessionIDs.add(zoneName);
		}
		else if(cursorX < 100 && cursorY < 100){
			KillAllActiveZonesAndIDs();
		}
	}

	/**
	 * If TUIO event is tied to an Active Zone then update the X and Y coordinates
	 * 
	 * @param zoneName	The sessionID for the zone as a string
	 * @param cursorX	The current X cursor position on the screen in pixels
	 * @param cursorY	The current Y cursor position on the screen in pixels
	 */
	public void updateNewZone(String zoneName, int cursorX, int cursorY){

		for(int i = 0; i < tzones.size(); i++){
			if(tzones.get(i).getZoneName() == zoneName){	// if the TUIO event is an owner of one of the Zones 
				if(cursorY > parent.height-100){			// If the TUIO event is above the create new zone line
					tzones.get(i).setX(cursorX);			// then update position
					tzones.get(i).setY(cursorY);
					break;
				}
			}
		}

	}

	/**
	 * Remove the Session ID as the TUIO event is removed from the screen
	 * 
	 * @param zoneName 	The sessionID for the zone as a string
	 */
	public void RemoveSessionID(String zoneName){

		for(int j = 0; j < sessionIDs.size(); j++){
			if(sessionIDs.get(j) == zoneName){
				sessionIDs.remove(j);
				break;
			}
		}
	}

	/**
	 * Determines if a TUIO event is inside of an existing Zone by comparing the position of the incoming TUIO event against the boundaries of all the zones within the collection
	 * 
	 * @param cursorX	The current X cursor position on the screen in pixels
	 * @param cursorY	The current Y cursor position on the screen in pixels
	 * @return			Whether the current cursor event is within an existing zone
	 */
	private boolean cursurIsInsideAnExistingZone(int cursorX, int cursorY) {

		boolean insideExistingZone = false;

		for(int i = 0; i < tzones.size(); i++){
			if(Math.abs(tzones.get(i).getX() - cursorX) < tzones.get(i).getWidth() && Math.abs(tzones.get(i).getY() - cursorY) < tzones.get(i).getHeight()){
				insideExistingZone = true;
				touchEventIsInsideThisZone = i;
				break;
			}
		}
		return insideExistingZone;
	}

	/**
	 * Determines if the current zone still has an active TUIO event as its owner by comparing the sessionID passed in to the method against the list of active touchZone IDs
	 * 
	 * @param index		The index position of the Zone which the current TUIO event falls inside of, within the collection of Zones
	 * @return 			Whether owner is still alive or not
	 */
	private boolean zoneDoesNotHaveAnActiveOwner(int index){

		boolean noActiveOwner = true;

		for(int j = 0; j < sessionIDs.size(); j++){
			if(sessionIDs.get(j) == tzones.get(index).getZoneName()){
				noActiveOwner = false;
				break;
			}
		}
		return noActiveOwner;
	}

	/**
	 * Clears all members of both the touchZone collection and the active sessionIDs Collection
	 */
	public void KillAllActiveZonesAndIDs(){
		tzones.clear();
		sessionIDs.clear();
	}

}
