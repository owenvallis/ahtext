
public class StateMandalaMenu implements StateInterface {

	/**
	 * Fields for MenuState
	 */
	StateMandala stateMandala;
	AhTextContext ahTextContext;


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
					stateMandala.setState(stateMandala.getStoryWindowState());
				}
			}
		}	
	}

	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
