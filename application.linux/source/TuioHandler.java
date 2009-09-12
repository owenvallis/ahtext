import java.util.ArrayList;

import processing.core.PApplet;
import TUIO.*;

/**
 * Tuio listener handles all incoming tuio events. Also acts as the Source for all listening Observers implementing
 * the Observer interface. 
 * 
 * @author jhochenbaum, ovallis
 *
 */

public class TuioHandler implements TuioListener, TuioSubject {

	TuioClient tuioClient;
	PApplet parent;

	private int cursorX, cursorY;			// blob positions
	private long sessionID;					// blob ID
	private int lastTouchTime;             	// last time (in ms) from which a touch/click event happened
	private int mouseSessionID;
	private ArrayList<TuioObserver> observers;


	public TuioHandler(PApplet parent){
		// constructor
		this.parent = parent;
		tuioClient = new TuioClient();
		tuioClient.addTuioListener(this);
		tuioClient.connect();
		observers = new ArrayList<TuioObserver>();
	}

	// /////////////////////////////////////////////////////////////
	// SET METHODS//////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////	
	public void registerObserver(TuioObserver o) {
		observers.add(o);
	}

	public void removeObserver(TuioObserver o) {
		int i = observers.indexOf(o);
		if(i >= 0){
			observers.remove(i);
		}
	}

	public void removeAllObservers(){
		observers.clear();
	}

	public void notifyObserverOfAddedCursor() {
		for(int i = 0; i < observers.size(); i++){
			TuioObserver observer = (TuioObserver)observers.get(i);
			observer.tuioCursorAdded(sessionID, cursorX, cursorY);
		}
	}

	public void notifyObserverOfUpdatedCursor() {
		for(int i = 0; i < observers.size(); i++){
			TuioObserver observer = (TuioObserver)observers.get(i);
			observer.tuioCursorUpdate(sessionID, cursorX, cursorY);
		}
	}

	public void notifyObserverOfRemovedCursor() {
		for(int i = 0; i < observers.size(); i++){
			TuioObserver observer = (TuioObserver)observers.get(i);
			observer.tuioCursorRemove(sessionID);
		}
	}

	/**
	 * called when an object is added to the scene
	 */
	public void addTuioObject(TuioObject tobj) {
		//println("add object "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle());
	}

	/**
	 * called when an object is removed from the scene
	 * 
	 * @param tobj
	 */
	public void removeTuioObject(TuioObject tobj) {
		//println("remove object "+tobj.getSymbolID()+" ("+tobj.getSessionID()+")");
	}

	/**
	 * called when an object is moved
	 * 
	 * @param tobj
	 */
	public void updateTuioObject (TuioObject tobj) {
		// println("update object "+tobj.getSymbolID()+" ("+tobj.getSessionID()+") "+tobj.getX()+" "+tobj.getY()+" "+tobj.getAngle()
		//         +" "+tobj.getMotionSpeed()+" "+tobj.getRotationSpeed()+" "+tobj.getMotionAccel()+" "+tobj.getRotationAccel());
	}

	/**
	 * called when a cursor is added to the scene
	 * 
	 * @param tcur Represents a reference to a finger blob event
	 */
	public void addTuioCursor(TuioCursor tcur) {
		//System.out.println("add cursor "+tcur.getCursorID()+" ("+tcur.getSessionID()+ ") " +tcur.getX()+" "+tcur.getY());
		if(tcur.getScreenX(parent.width) != 0 && tcur.getScreenY(parent.height) != 0){
			this.cursorX = tcur.getScreenX(parent.width);
			this.cursorY = tcur.getScreenY(parent.height);
			this.sessionID = tcur.getSessionID();
			notifyObserverOfAddedCursor();
			tuioEventsChanged();
		}
	}

	/**
	 * called when a cursor is moved
	 * 
	 * @param tcur Represents a reference to a finger blob event
	 */
	public void updateTuioCursor (TuioCursor tcur) {
		//println("update cursor "+tcur.getCursorID()+" ("+tcur.getSessionID()+ ") " +tcur.getX()+" "+tcur.getY()
		//         +" "+tcur.getMotionSpeed()+" "+tcur.getMotionAccel());
		this.cursorX = tcur.getScreenX(parent.width);
		this.cursorY = tcur.getScreenY(parent.height);
		this.sessionID = tcur.getSessionID();
		notifyObserverOfUpdatedCursor();
		tuioEventsChanged();   
	}

	/**
	 * called when a cursor is removed from the scene
	 * 
	 * @param tcur Represents a reference to a finger blob event
	 */
	public void removeTuioCursor(TuioCursor tcur) {
		//println("remove cursor "+tcur.getCursorID()+" ("+tcur.getSessionID()+")");
		this.cursorX = tcur.getScreenX(parent.width);
		this.cursorY = tcur.getScreenY(parent.height);
		this.sessionID = tcur.getSessionID();
		notifyObserverOfRemovedCursor();
		tuioEventsChanged();	    
	}

	/**
	 * called after each message bundle
	 * representing the end of an image frame
	 * @param bundleTime
	 */
	public void refresh(TuioTime bundleTime) { 
	}

	/**
	 * Notify the Observers whenever we get a tuio event
	 * 
	 */
	public void tuioEventsChanged(){
		resetTouchTimer();
	}

	/**
	 * Notify the Observers whenever we get a mouse event
	 * 
	 */
	public void mouseEventsClicked(){
		sessionID = mouseSessionID;
		notifyObserverOfAddedCursor();
		resetTouchTimer();
	}

	public void mouseEventsDragged(){
		sessionID = mouseSessionID;
		notifyObserverOfUpdatedCursor();
		resetTouchTimer();
	}

	public void mouseEventsReleased(){
		sessionID = mouseSessionID;
		notifyObserverOfUpdatedCursor();
		resetTouchTimer();
		mouseSessionID++;
	}

	/**
	 * holds the time at which the last touch event occurred
	 */
	private void resetTouchTimer() {
		lastTouchTime = parent.millis();
	}

	// /////////////////////////////////////////////////////////////
	// SETTERS AND GETTERS//////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	/**
	 * @return the cursorX
	 */
	public int getCursorX() {
		return cursorX;
	}

	public void setCursorX(int cursorX) {
		this.cursorX = cursorX;
	}

	/**
	 * @return the cursorY
	 */
	public int getCursorY() {
		return cursorY;
	}

	public void setCursorY(int cursorY) {
		this.cursorY = cursorY;
	}

	/**
	 * @param lastTouchTime the lastTouchTime to set
	 */
	public void setLastTouchTime(int lastTouchTime) {
		this.lastTouchTime = lastTouchTime;
	}

	/**
	 * @return the lastTouchTime
	 */
	public int getLastTouchTime() {
		return lastTouchTime;
	}

	public void setSessionID(long sessionID) {
		this.sessionID = sessionID;
	}

	public long getSessionID() {
		return sessionID;
	}

}
