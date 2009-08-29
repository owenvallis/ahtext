
public class StateMandalaBackToMenu implements StateInterface {

	/**
	 * Fields for StateMandalaBackToMenu
	 */
	StateMandala stateMandala;
	AhTextContext ahTextContext;


	public StateMandalaBackToMenu(StateMandala stateMandala, AhTextContext ahTextContext){
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
		stateMandala.mandalaNodeList[stateMandala.currentNode].storyName();
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
		if(stateMandala.scaleFactor < 1.0){
			stateMandala.scaleFactor += .05;
		} else {
			stateMandala.setState(stateMandala.getMenuState());
			for(int i = 0; i < stateMandala.mandalaNodeList.length; i++){
				ahTextContext.tuioHandler.registerObserver(stateMandala.mandalaNodeList[i]);
				ahTextContext.tuioHandler.registerObserver(ahTextContext);
				resetMandala();
			}
			
		}
	}
	
	public void resetMandala() {
		for(int i = 0; i < stateMandala.mandalaNodeList.length; i++){
			stateMandala.mandalaNodeList[i].setAnimationActive(false);
			stateMandala.mandalaNodeList[i].setTriggerActive(false);
			stateMandala.mandalaNodeList[i].setShouldFade(false);
			stateMandala.mandalaNodeList[i].resetColors();
		}
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}

}

