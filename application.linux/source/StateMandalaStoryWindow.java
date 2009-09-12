import oscP5.OscMessage;
import processing.core.PConstants;
import processing.core.PImage;


public class StateMandalaStoryWindow implements StateInterface {

	/**
	 * Fields for StateMandalaStoryWindow
	 */
	StateMandala stateMandala;
	AhTextContext ahTextContext;
	PImage directions;
	PImage menuLabels;

	public StateMandalaStoryWindow(StateMandala stateMandala, AhTextContext ahTextContext){
		this.stateMandala = stateMandala;
		this.ahTextContext = ahTextContext;
		directions = ahTextContext.loadImage("media/labelpics/draguptext.png");
		menuLabels = ahTextContext.loadImage("media/labelpics/menuLabels.png");
	}

	public void touchEventAddCursor(long sessionID, int cursorX, int cursorY) {
		if (cursorX < (int)(ahTextContext.width * 0.07) && cursorY < (int)(ahTextContext.height * 0.11)) {
			ahTextContext.tuioHandler.removeAllObservers();
			ahTextContext.tuioHandler.registerObserver(ahTextContext);
			ahTextContext.myMessage = new OscMessage("/mode");
			ahTextContext.myMessage.add(7); // Mandala Back to Menu Mode
			ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);
			stateMandala.setState(stateMandala.getBackToMenuState());
			stateMandala.alpha = 255;
		}
	}

	public void touchEventUpdateCursor(long sessionID, int cursorX, int cursorY) {
		// TODO Auto-generated method stub

	}

	public void touchEventRemoveCursor(long sessionID) {
		// TODO Auto-generated method stub

	}

	public void timer() {
		// TODO Auto-generated method stub

	}

	public void displayGraphics() {
		stateMandala.mandalaNodeList[stateMandala.currentNode].storyName();
		if(stateMandala.alpha > 0){
			ahTextContext.tint(255,stateMandala.alpha);
			ahTextContext.imageMode(PConstants.CENTER);
			ahTextContext.image(directions, ahTextContext.width/2, ahTextContext.height/2, (int)(ahTextContext.width * 0.48), (int)(ahTextContext.height * 0.36));
			ahTextContext.tint(255,255);
			ahTextContext.imageMode(PConstants.CORNER);
			stateMandala.alpha -= 5;
		}
		ahTextContext.image(menuLabels, 0, 0, ahTextContext.width, (int)(ahTextContext.height * 0.11));
		stateMandala.drawZoneRectangles.displayAllAcitiveRectangles();
		ahTextContext.translate(stateMandala.xCenter*stateMandala.scaleFactor, stateMandala.yCenter*stateMandala.scaleFactor);
		ahTextContext.scale(stateMandala.scaleFactor);
		ahTextContext.noFill();
		ahTextContext.stroke(0);
		ahTextContext.strokeWeight(stateMandala.largeCircleStroke);
		ahTextContext.ellipse(0,0,stateMandala.circleDiameter,stateMandala.circleDiameter);
		for(int i = 0; i < stateMandala.mandalaNodeList.length; i++){
			stateMandala.mandalaNodeList[i].drawMandalaNode();
		}
		stateMandala.mandalaNodeList[stateMandala.currentNode].fadeOverlay();
	}

	public void reset() {
		stateMandala.reset();		
	}

}
