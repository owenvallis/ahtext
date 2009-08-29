import oscP5.OscMessage;


public class StateMandalaStoryWindow implements StateInterface {
	
	/**
	 * Fields for StateMandalaStoryWindow
	 */
	StateMandala stateMandala;
	AhTextContext ahTextContext;
	
	public StateMandalaStoryWindow(StateMandala stateMandala, AhTextContext ahTextContext){
		this.stateMandala = stateMandala;
		this.ahTextContext = ahTextContext;
	}

	public void touchEventAddCursor(long sessionID, int cursorX, int cursorY) {
		if (cursorX < 100 && cursorY < 100) {
			ahTextContext.tuioHandler.removeAllObservers();
			ahTextContext.tuioHandler.registerObserver(ahTextContext);
			ahTextContext.myMessage = new OscMessage("/mode");
			ahTextContext.myMessage.add(7); // Mandala Back to Menu Mode
			ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);
			stateMandala.setState(stateMandala.getBackToMenuState());
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
