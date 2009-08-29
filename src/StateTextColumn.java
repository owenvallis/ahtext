import oscP5.OscMessage;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;


public class StateTextColumn implements StateInterface {
	
	/**
	 * Fields for TextColumns
	 */
	AhTextContext ahTextContext;
	PFont ahTextFont;
	PGraphics pg;
	PImage image;
	

	
	public StateTextColumn(AhTextContext ahTextContext){
		this.ahTextContext = ahTextContext;
		this.ahTextFont = ahTextContext.ahTextFont;
		this.pg = ahTextContext.pg;
		
	}
	
	// /////////////////////////////////////////////////////////////
	// SET METHODS//////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	public void touchEventAddCursor(long sessionID, int cursorX, int cursorY) {
		ahTextContext.tuioHandler.removeAllObservers();
		ahTextContext.tuioHandler.registerObserver(ahTextContext);
		for (int i = 0; i < ahTextContext.numberOfColumns; i++) {
			ahTextContext.column[i].resetValues();
		}
		ahTextContext.myMessage = new OscMessage("/mode");
		ahTextContext.myMessage.add(1); // Row Mode 
		ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);
		ahTextContext.setState(ahTextContext.getMandalaState());
	}
	
	public void touchEventUpdateCursor(long sessionID, int cursorX, int cursorY) {
		// Do Nothing
	}

	public void touchEventRemoveCursor(long sessionID) {
		// Do Nothing
	}

	public void timer() {
		// Do Nothing
	}
	
	public void displayGraphics() {
		
		// display falling Text
		ahTextContext.frameRate(10);
		pg.beginDraw();
		pg.fill(255, 255, 255, 200);
		pg.rect(0, 0, pg.width, pg.height);
		pg.noStroke();
		
		for (int i = 0; i < ahTextContext.numberOfColumns; i++) {
			ahTextContext.column[i].display();
		}
		
		pg.endDraw();
		ahTextContext.image(pg, 0, 0);

		// if all Text Columns are finished filling then switch to Text Row State
		if(TextColumns.flag == ahTextContext.numberOfColumns){
			ahTextContext.setState(ahTextContext.getTextRowsState());
			for (int i = 0; i < ahTextContext.numberOfColumns; i++) {
				ahTextContext.column[i].resetValues();
			}
		}
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

}
