import java.util.Observable;
import java.math.*;

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

	private int cursorX, cursorY;			// blob positions
	private long sessionID;					// blob ID
	private int lastTouchTime;             	// last time (in ms) from which a touch/click event happened
	TouchPoint tpoint[];// touch point array
	TouchZone tzone[];// touch zone array


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
		tpoint = new TouchPoint[1];// start with one point
		tzone = new TouchZone[1];// start with canvas
		tzone[0] = new TouchZone("canvas", 0, 0, this.parent.width,this.parent.height);
	}

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
		if(cursorY > parent.height-100){ 		//TODO fix this to define the boundary based off text height!!
			setZone("zone"+tcur.getSessionID(), cursorX, cursorY, 200, 300);			
			setZoneParameter("zone"+tcur.getSessionID(),"DRAGGABLE",true);
			System.out.println(tcur.getCursorID()+" "+tcur.getSessionID());
		}else{
			for(int x = 1; x < tzone.length; x++){
				if(Math.abs(tzone[x].x-cursorX) < tzone[x].w && Math.abs(tzone[x].y-cursorY) < tzone[x].h){
					tzone[x].name = "zone"+tcur.getSessionID();
					break;
				}
			}
		}
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
		setZoneData("zone"+tcur.getSessionID(), cursorX, cursorY, 200, 300);
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
		//killZone("zone"+tcur.getSessionID());
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

	/**
	 * Notify the Observers whenever we get a mouse event
	 * 
	 */
	public void mouseEventsChanged(){
		setChanged();
		notifyObservers();
		resetTouchTimer();
	}

	/**
	 * holds the time at which the last touch event occurred
	 */
	private void resetTouchTimer() {
		lastTouchTime = parent.millis();
	}










	/**
	 * Get the zone width.
	 * 
	 * @param zNameIn
	 *            the name of the zone.
	 * @return an integer
	 * 
	 */
	public int getZoneWidth(String zNameIn) {
		int width = -999, i = getZoneIndex(zNameIn);
		if (i != -999) {
			width = tzone[i].w;
		}
		return width;
	}

	/**
	 * Get the zone height.
	 * 
	 * @param zNameIn
	 *            the name of the zone.
	 * @return an integer
	 * 
	 */
	public int getZoneHeight(String zNameIn) {
		int height = -999, i = getZoneIndex(zNameIn);
		if (i != -999) {
			height = tzone[i].h;
		}
		return height;
	}

	/**
	 * Get coordinates and touch ID of all the touch points (TUIO cursors) on
	 * the screen.
	 * <p>
	 * [i][0] - x coordinate
	 * <p>
	 * [i][1] - y coordinate
	 * <p>
	 * [i][2] - ID (Useful for getTrail method)
	 * 
	 * 
	 * @return double integer array
	 * 
	 */
	public int[][] getPoints() {
		int[][] coord = new int[0][3];
		for (int i = 0; i < tpoint.length; i++) {
			if (tpoint[i] != null) {
				int[] toAppend = {
						tpoint[i].coord[tpoint[i].coord.length - 1][0],
						tpoint[i].coord[tpoint[i].coord.length - 1][1], i };
				coord = (int[][]) PApplet.append(coord, toAppend);
			}
		}
		return coord;
	}

	/**
	 * Define a new circular zone that will respond to TUIO events in a unique
	 * way.
	 * 
	 * @param zNameIn
	 *            a name for the zone
	 * @param xIn
	 *            the x-coordinate of the center of the circular zone
	 * @param yIn
	 *            the y-coordinate of the center of the circular zone
	 * @param rIn
	 *            the radius of the circular zone
	 */
	public void setZone(String zNameIn, int xIn, int yIn, int rIn) { // like a
		// constructor
		boolean set = false;
		for (int i = 0; i < tzone.length; i++) {
			if (tzone[i] == null) {
				tzone[i] = new TouchZone(zNameIn, xIn, yIn, rIn);
				set = true;
				break;
			}
		}
		if (!set) {
			tzone = (TouchZone[]) PApplet.append(tzone, new TouchZone(zNameIn,
					xIn, yIn, rIn)); // + or -1???
		}
	}

	/**
	 * Define a new rectangular zone that will respond to TUIO events in a
	 * unique way.
	 * 
	 * @param zNameIn
	 *            a name for the zone
	 * @param xIn
	 *            the x-coordinate of the upper left corner of the zone
	 * @param yIn
	 *            the y-coordinate of the upper left corner of the zone
	 * @param wIn
	 *            the width of the zone
	 * @param hIn
	 *            the height of the zone
	 */
	public void setZone(String zNameIn, int xIn, int yIn, int wIn, int hIn) {// like
		// constructor
		boolean set = false;
		for (int i = 0; i < tzone.length; i++) {
			if (tzone[i] == null) {
				tzone[i] = new TouchZone(zNameIn, xIn, yIn, wIn, hIn);
				set = true;
				break;
			}
		}
		if (!set) {
			tzone = (TouchZone[]) PApplet.append(tzone, new TouchZone(zNameIn,
					xIn, yIn, wIn, hIn)); // + or -1???
		}
	}

	/**
	 * Modify rectangular zone primitive data.
	 * 
	 * @param zNameIn
	 *            the name of the zone to be modified
	 * @param xIn
	 *            the x-coordinate of the upper left corner of the zone
	 * @param yIn
	 *            the y-coordinate of the upper left corner of the zone
	 * @param wIn
	 *            the width of the zone
	 * @param hIn
	 *            the height of the zone
	 */
	public void setZoneData(String zNameIn, int xIn, int yIn, int wIn, int hIn) {
		for (int i = 0; i < tzone.length; i++) {
			if (tzone[i] != null) {
				if (tzone[i].name.equals(zNameIn)) {
					tzone[i].x = xIn;
					tzone[i].y = yIn;
					tzone[i].w = wIn;
					tzone[i].h = hIn;
				}
			}
		}
	}

	/**
	 * Modify circular zone primitive data.
	 * 
	 * @param zNameIn
	 *            the name of the zone to be modified
	 * @param xIn
	 *            the x-coordinate of the upper left corner of the zone
	 * @param yIn
	 *            the y-coordinate of the upper left corner of the zone
	 * @param rIn
	 *            the radius of the zone
	 */
	public void setZoneData(String zNameIn, int xIn, int yIn, int rIn) {
		for (int i = 0; i < tzone.length; i++) {
			if (tzone[i] != null) {
				if (tzone[i].name.equals(zNameIn)) {
					tzone[i].x = xIn;
					tzone[i].y = yIn;
					tzone[i].r = rIn;
					tzone[i].w = rIn * 2;
					tzone[i].h = rIn * 2;
				}
			}
		}
	}

	/**
	 * Set zone parameters, like 'DRAGGABLE', 'SCALABLE', or 'HSWIPEABLE'.
	 * 
	 * @param zNameIn
	 *            the name of the zone to be modified
	 * @param paramName
	 *            the name of the parameter to be set. Parameters names can be
	 *            'DRAGGABLE', 'SCALABLE', 'VSWIPEABLE', 'HSWIPEABLE', or
	 *            'WINDOW3D'. Note, 'HSWIPEABLE' and 'VSWIPEABLE' can only be
	 *            applied to rectangle zones. 'WINDOW3D' enables a one-finger
	 *            gesture for applying 3D rotations to an object.
	 * @param bool
	 *            Set the parameter to be true or false.
	 * @example _tzDrag
	 * @example _tzThrow
	 * @example _tzSwipe
	 */
	public void setZoneParameter(String zNameIn, String paramName, boolean bool) {
		for (int i = 0; i < tzone.length; i++) {
			if (tzone[i] != null) {
				if (tzone[i].name.equals(zNameIn)) {
					if (paramName.equals("DRAGGABLE"))
						tzone[i].draggable = bool;
					else if (paramName.equals("SCALABLE"))
						tzone[i].scalable = bool;
					else if (paramName.equals("HSWIPEABLE"))
						tzone[i].hSwipeable = bool;
					else if (paramName.equals("VSWIPEABLE"))
						tzone[i].vSwipeable = bool;
					else if (paramName.equals("THROWABLE"))
						tzone[i].throwable = bool;
					else if (paramName.equals("ROTATABLE"))
						tzone[i].rotatable = bool;
					else if (paramName.equals("WINDOW3D"))
						tzone[i].window3d = bool;
					break;
				}
			}
		}
	}

	/**
	 * Pull a zone to the top layer.
	 * 
	 * @param zNameIn
	 *            the name of the zone to pull to the top layer.
	 * 
	 */
	public void pullZoneToTop(String zNameIn) {
		for (int i = 0; i < tzone.length; i++) {
			if (tzone[i] != null) {
				if (tzone[i].name.equals(zNameIn)) { // remove zone
					TouchZone[] front = (TouchZone[]) PApplet.subset(tzone, 0,
							i);
					TouchZone[] back = (TouchZone[]) PApplet.subset(tzone,
							i + 1, (tzone.length) - i - 1);
					TouchZone pulled = tzone[i];
					tzone = (TouchZone[]) PApplet.concat(front, back);
					tzone = (TouchZone[]) PApplet.append(tzone, pulled);
					break;
				}
			}
		}
	}

	/**
	 * Kill a zone.
	 * 
	 * @param zNameIn
	 *            the name of the zone to be killed.
	 * 
	 */
	public void killZone(String zNameIn) {
		for (int i = 0; i < tzone.length; i++) {
			if (tzone[i] != null) {
				if (tzone[i].name.equals(zNameIn)) { // remove zone
					TouchZone[] front = (TouchZone[]) PApplet.subset(tzone, 0,
							i);
					TouchZone[] back = (TouchZone[]) PApplet.subset(tzone,
							i + 1, (tzone.length) - i - 1);
					tzone = (TouchZone[]) PApplet.concat(front, back);
					break;
				}
			}
		}
	}

	/**
	 * Kill all zones.
	 * 
	 * @param zNameIn
	 *            the name of the zone to be killed.
	 * 
	 */
	public void killZones() {
		for (int i = 1; i < tzone.length; i++) {
			if (tzone[i] != null) {
				TouchZone[] front = (TouchZone[]) PApplet.subset(tzone, 0,
						i);
				TouchZone[] back = (TouchZone[]) PApplet.subset(tzone,
						i + 1, (tzone.length) - i - 1);
				tzone = (TouchZone[]) PApplet.concat(front, back);
			}
		}
	}
	// /////////////////////////////////////////////////////////////
	// HELPER METHODS//////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	int getZoneIndex(String zNameIn) {
		int index = -999;
		for (int i = 0; i < tzone.length; i++) {
			if (tzone[i] != null) {
				if (tzone[i].name.equals(zNameIn)) {
					index = i;
					break;
				}
			}
		}
		return index;
	}

	// /////////////////////////////////////////////////////////////
	// TOUCH POINT CLASS//////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////
	class TouchPoint {
		int coord[][];
		int SID, zone, zone0, touchID;
		String zName, zName0;
		boolean scalar = false;

		TouchPoint(int SidIn, int xIn, int yIn, int zoneIn, String zNameIn,
				int tpointIn) {
			coord = new int[1][6];
			coord[0][0] = xIn;// x coordinate
			coord[0][1] = yIn;// y coordinate
			coord[0][2] = 0;// x velocity
			coord[0][3] = 0;// y velocity
			coord[0][4] = parent.millis();// time created
			coord[0][5] = zoneIn;// zone containing this point
			SID = SidIn;// SID from TUIO
			zName = zName0 = zNameIn;// zone name
			zone = zone0 = zoneIn;// zone array index
			touchID = parent.millis() + SID; // TODO this should go away.
			// transform zones
			markZone(tpointIn, zone);// try to take ownership of a zone

		}

		// create a trail of points (history) for this touch point
		void addPoint(int SidIn, int xIn, int yIn, int vxIn, int vyIn,
				int zoneIn, int tpointIn) {
			int tIn = parent.millis();
			int[] toAppend = { xIn, yIn, vxIn, vyIn, tIn, zoneIn };
			coord = (int[][]) PApplet.append(coord, toAppend);
			SID = SidIn;
			zone = zoneIn;
			modifyZone(xIn, yIn, vxIn, vyIn, tpointIn, zone0);
		}

		// get the last point in trail - return x,y coordinates and velocities
		int[] getLastPoint() {
			int[] xy = { coord[coord.length - 1][0],
					coord[coord.length - 1][1], coord[coord.length - 1][2],
					coord[coord.length - 1][3] };
			return xy;
		}

		// get the first point in trail
		int[] getFirstPoint() {
			int[] xy = { coord[0][0], coord[0][1], coord[0][2], coord[0][3] };
			return xy;
		}
	}

	void modifyZone(int xIn, int yIn, int vxIn, int vyIn, int tpointIn, int zone) {
		int[] touches = new int[0];
		// COUNT TOUCHES
		for (int i = 0; i < tpoint.length; i++) {
			if (tpoint[i] != null) {
				if (tpoint[i].zone0 == zone) {
					touches = (int[]) PApplet.append(touches, i);
				}
			}
		}
		// IF DRAGGABLE (single touch action)
		if (tzone[zone].primePoint == tpointIn && tzone[zone].draggable
				&& touches.length == 1 && tpoint[tpointIn].scalar == false) {// 
			tzone[zone].x = tzone[zone].xi
			+ (xIn - tpoint[tpointIn].coord[0][0]);
			tzone[zone].y = tzone[zone].yi
			+ (yIn - tpoint[tpointIn].coord[0][1]);
			tzone[zone].cx = tzone[zone].x + tzone[zone].w / 2;
			tzone[zone].cx = tzone[zone].y + tzone[zone].h / 2;
			tzone[zone].vx = (tzone[zone].vx + vxIn) / 2;
			tzone[zone].vy = (tzone[zone].vy + vyIn) / 2;
		}		
	}

	// Give a zone a primary point
	void markZone(int tpointIn, int zone) {
		if (tzone[zone].primePoint == -999) {
			tzone[zone].primePoint = tpointIn;
			tzone[zone].touches++;
		}
	}


	// /////////////////////////////////////////////////////////////
	// TOUCH ZONE CLASS//////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////
	class TouchZone {
		int x, y, x0, y0, xi, yi, r, h, w, r0, h0, w0, ri, hi, wi, vx, vy, cx,
		cy, primePoint = -999, friction, touches = 0, releaseTime = 0;
		boolean rectangle, circle, gravity = false, draggable = false,
		scalable = false, hSwipeable = false, vSwipeable = false,
		throwable = false, rotatable = false, window3d = false;
		String name;

		float scl = 1.0f, scli = 1.0f;

		// 3d rotation variables
		int mP = 0, mxi = 0, myi = 0;
		float rX = 0, rY = 0, d = 0;
		float mInit[][] = new float[3][3];
		float mFin[][] = new float[3][3];
		float mNew[][] = new float[3][3];
		float mIdent[][] = new float[3][3];
		float theta;

		TouchZone(String zNameIn, int xIn, int yIn, int rIn) {
			name = zNameIn;
			// variables followed by 0 are the original values
			// variables followed by i are intermediate values for a historical
			// reference
			x0 = xi = x = xIn;// center of zone x
			y0 = yi = y = yIn;// center of zone y
			r0 = ri = r = rIn;// radius of circle boundary
			w0 = wi = w = 2 * rIn;// width of the square the circle inscribes
			h0 = hi = h = 2 * rIn;// height of the square the circle inscribes
			vx = vy = 0;// velocities
			cx = x;// centerline x
			cy = y;// centerline y
			circle = true;
			rectangle = false;
			makeIdentity();
			copyMatrix();
		}

		TouchZone(String zNameIn, int xIn, int yIn, int wIn, int hIn) {
			name = zNameIn;
			x0 = xi = x = xIn;// upper left corner x
			y0 = yi = y = yIn;// upper left corner y
			w0 = wi = w = wIn;// width
			h0 = hi = h = hIn;// width
			r0 = ri = r = (int) PApplet.sqrt(PApplet.pow(hIn, 2)
					+ PApplet.pow(wIn, 2)) / 2;
			vx = vy = 0;// velocities
			cx = x + w / 2;
			cy = y + h / 2;
			circle = false;
			rectangle = true;
			makeIdentity();
			copyMatrix();
		}

		void apply3dMatrix() {
			parent.translate(cx, cy, 0);
			parent.applyMatrix(mFin[0][0], mFin[0][1], mFin[0][2], 0,
					mFin[1][0], mFin[1][1], mFin[1][2], 0, mFin[2][0],
					mFin[2][1], mFin[2][2], 0, 0, 0, 0, 1);

		}

		float[] get3dMatrix() {
			float[] matrix = { mFin[0][0], mFin[0][1], mFin[0][2], 0f,
					mFin[1][0], mFin[1][1], mFin[1][2], 0f, mFin[2][0],
					mFin[2][1], mFin[2][2], 0f, 0f, 0f, 0f, 1 };
			return matrix;

		}

		void makeIdentity() { // for 3-d rotation
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (i == j) {
						mFin[i][j] = 1f;
						mIdent[i][j] = 1f;
					} else {
						mFin[i][j] = 0f;
						mIdent[i][j] = 0f;
					}
				}
			}
		}

		void makeNewMatrix() {// need to simplify, do not need to multiply by
			// Identity
			for (int i = 0; i < 3; i++) {
				mNew[i][0] = mIdent[i][0]
				                       * (PApplet.cos(theta) + (1f - PApplet.cos(theta)) * rX
				                    		   * rX) + mIdent[i][1]
				                    		                     * ((1f - PApplet.cos(theta)) * rY * rX) + mIdent[i][2]
				                    		                                                                         * (-PApplet.sin(theta) * rY);
				mNew[i][1] = mIdent[i][0]
				                       * ((1f - PApplet.cos(theta)) * rY * rX)
				                       + mIdent[i][1]
				                                   * (PApplet.cos(theta) + (1f - PApplet.cos(theta)) * rY
				                                		   * rY) + mIdent[i][2] * PApplet.sin(theta) * rX;
				mNew[i][2] = mIdent[i][0] * PApplet.sin(theta) * rY
				+ mIdent[i][1] * (-PApplet.sin(theta) * rX)
				+ mIdent[i][2] * PApplet.cos(theta);
			}
		}

		void makeFinMatrix() {
			for (int i = 0; i < 3; i++) {
				mFin[i][0] = mNew[i][0] * mInit[0][0] + mNew[i][1]
				                                                * mInit[1][0] + mNew[i][2] * mInit[2][0];
				mFin[i][1] = mNew[i][0] * mInit[0][1] + mNew[i][1]
				                                                * mInit[1][1] + mNew[i][2] * mInit[2][1];
				mFin[i][2] = mNew[i][0] * mInit[0][2] + mNew[i][1]
				                                                * mInit[1][2] + mNew[i][2] * mInit[2][2];
			}
		}

		void copyMatrix() {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					mInit[i][j] = mFin[i][j];
				}
			}
		}

	}



}
