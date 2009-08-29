import oscP5.OscMessage;


public class StateMandalaGrow implements StateInterface {
	
	/**
	 * Fields for GrowState
	 */
	StateMandala stateMandala;
	AhTextContext ahTextContext;
	
	public float grow;
	
	public StateMandalaGrow(StateMandala stateMandala, AhTextContext ahTextContext){
		this.stateMandala = stateMandala;
		this.ahTextContext = ahTextContext;
		grow = 0;
	}

	public void touchEventAddCursor(long sessionID, int cursorX, int cursorY) {
		// Do Nothing
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
		ahTextContext.translate(stateMandala.xCenter, stateMandala.yCenter);
		ahTextContext.scale(grow);
		ahTextContext.noFill();
		ahTextContext.stroke(0);
		ahTextContext.strokeWeight(stateMandala.largeCircleStroke);
		ahTextContext.ellipse(0,0,stateMandala.circleDiameter,stateMandala.circleDiameter);
		for(int i = 0; i < stateMandala.mandalaNodeList.length; i++){
		stateMandala.mandalaNodeList[i].drawMandalaNode();
		}
		if(grow < 1.0){
			grow += 0.009;
		} else {
			for(int i = 0; i < stateMandala.mandalaNodeList.length; i++){
				ahTextContext.tuioHandler.registerObserver(stateMandala.mandalaNodeList[i]);
				}
			grow = 0;
			ahTextContext.myMessage = new OscMessage("/mode");
			ahTextContext.myMessage.add(4); // Menu Mode
			ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);
			stateMandala.setState(stateMandala.getMenuState());
		}
	}

	public void reset() {
		stateMandala.reset();		
	}

}
