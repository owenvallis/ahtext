import oscP5.OscMessage;


public class StateMandalaMenu implements StateInterface {

	/**
	 * Fields for MenuState
	 */
	StateMandala stateMandala;
	AhTextContext ahTextContext;
	private int iTemp = 999;


	public StateMandalaMenu(StateMandala stateMandala, AhTextContext ahTextContext){
		this.stateMandala = stateMandala;
		this.ahTextContext = ahTextContext;
	}

	public void touchEventAddCursor(long sessionID, int cursorX, int cursorY) {
		// TODO Auto-generated method stub

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
		for(int i = 0; i < stateMandala.mandalaNodeList.length; i++){
			if(stateMandala.mandalaNodeList[i].isShouldFade()){
				stateMandala.mandalaNodeList[i].storyName();
			}
		}
		ahTextContext.translate(stateMandala.xCenter*stateMandala.scaleFactor, stateMandala.yCenter*stateMandala.scaleFactor);
		ahTextContext.scale(stateMandala.scaleFactor);
		ahTextContext.noFill();
		ahTextContext.stroke(0);
		ahTextContext.strokeWeight(stateMandala.largeCircleStroke);
		ahTextContext.ellipse(0,0,stateMandala.circleDiameter,stateMandala.circleDiameter);
		for(int i = 0; i < stateMandala.mandalaNodeList.length; i++){
			stateMandala.mandalaNodeList[i].drawMandalaNode();
		}
		for(int i = 0; i < stateMandala.mandalaNodeList.length; i++){
			if(stateMandala.mandalaNodeList[i].isShouldFade()){
				if(iTemp != i){
				ahTextContext.myMessage = new OscMessage("/mandala/noteid");
				ahTextContext.myMessage.add(i); // Mandala Note ID
				ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);
				iTemp = i;
				}
				stateMandala.mandalaNodeList[i].fadeOverlay();
			} else {
				stateMandala.mandalaNodeList[i].resetColors();
			}
			if(stateMandala.mandalaNodeList[i].isAnimationActive()){
				ahTextContext.tuioHandler.removeAllObservers();
				ahTextContext.tuioHandler.registerObserver(stateMandala.zoneCollection);
				ahTextContext.tuioHandler.registerObserver(ahTextContext); 
				if(stateMandala.scaleFactor > .1){
					stateMandala.scaleFactor -= .05;
				} else {
					stateMandala.currentNode = i;
					ahTextContext.myMessage = new OscMessage("/animate/noteid");
					ahTextContext.myMessage.add(i); // Animate Note ID
					ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);
					ahTextContext.myMessage = new OscMessage("/mode");
					ahTextContext.myMessage.add(6); // Dragable Text Mode
					ahTextContext.oscHandler.sendOSCMessage(ahTextContext.myMessage);
					iTemp = stateMandala.mandalaNodeList.length;
					stateMandala.setState(stateMandala.getStoryWindowState());
				}
			}
		}	
	}

	public void reset() {
		stateMandala.reset();		
	}
}
