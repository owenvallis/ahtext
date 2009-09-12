import oscP5.OscMessage;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;

public class StateTextRow implements StateInterface {

	/**
	 * Fields for TextColumns
	 */
	AhTextContext ahTextContext;
	PFont ahTextFont;
	PGraphics pg;
	PImage image;

	public StateTextRow(AhTextContext ahTextContext) {
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
		for (int i = 0; i < ahTextContext.numberOfRows; i++) {
			ahTextContext.row[i].resetValues();
		}
		ahTextContext.stringController.numStringToUse();
		ahTextContext.stringController.fillRows();
		ahTextContext.myMessage = new OscMessage("/mode");
		ahTextContext.myMessage.add(3); // Grow Mode
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
		// display Rows of Text
		ahTextContext.frameRate(10);
		pg.beginDraw();
		pg.fill(255, 255, 255, 200);
		pg.rect(0, 0, pg.width, pg.height);
		pg.noStroke();

		for (int i = 0; i < ahTextContext.numberOfRows; i++) {
			ahTextContext.row[i].displayFilledLine();
		}
		
		pg.endDraw();
		ahTextContext.image(pg, 0, 0);
		
		// if all Text Rows are finished filling then switch to Text Row Fade State
		if (TextRows.flag == ahTextContext.numberOfRows) {
			ahTextContext.myMessage = new OscMessage("/mode");
			ahTextContext.myMessage.add(2); // Fade Mode
			ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);
			ahTextContext.setState(ahTextContext.getTextRowsFadeState());
		}
		
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}