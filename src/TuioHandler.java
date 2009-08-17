import java.util.Observable;

import processing.core.PApplet;
import TUIO.TuioClient;
import TUIO.TuioCursor;
import TUIO.TuioListener;
import TUIO.TuioObject;
import TUIO.TuioTime;

/**
 * Tuio listener handles all incoming tuio events. Also acts as the Source for all listening Observers implementing
 * the Observer interface. 
 * 
 * @author jhochenbaum, ovallis
 *
 */

public class TuioHandler extends Observable implements TuioListener {
	
	TuioClient tuioClient;
	PApplet parent;

	private int cursorX, cursorY;			//blob positions
	private int lastTouchTime;                                // last time (in ms) from which a cursor was pressed on the table


	/**
	 * Define a new circular zone that will respond to TUIO events in a unique
	 * way.
	 * 
	 * @param parent
	 *            reference to the parent PApplet in the main class
	 */
	public TuioHandler(PApplet parent){
		// constructor
		this.parent = parent;
		tuioClient = new TuioClient();
		tuioClient.addTuioListener(this);
		tuioClient.connect();
		System.out.println("hello world");
	}

	/**
	 * @return the cursorX
	 */
	public int getCursorX() {
		return cursorX;
	}

	/**
	 * @return the cursorY
	 */
	public int getCursorY() {
		return cursorY;
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

	// /////////////////////////////////////////////////////////////
	// SET METHODS//////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

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
		this.cursorX = tcur.getScreenX(parent.width);
		this.cursorY = tcur.getScreenY(parent.height);
		tuioEventsChanged();
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
		setChanged();
		notifyObservers();
		resetTouchTimer();
	}
	
	private void resetTouchTimer() {
		    lastTouchTime = parent.millis();
	}



}
