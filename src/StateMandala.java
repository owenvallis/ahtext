import processing.core.PApplet;


public class StateMandala implements StateInterface {

	/**
	 * Fields for MandalaState
	 */
	AhTextContext ahTextContext;
	ExplodingText explodingText;
	DrawZoneRectangles drawZoneRectangles;
	ZoneCollection zoneCollection;
	
	StateInterface growState;
	StateInterface menuState;
	StateInterface storyWindowState;
	StateInterface backToMenuState;
	
	StateInterface mandalaState;
	
	public float xCenter, yCenter;
	public float scaleFactor;
	public float circleDiameter;
	public float largeCircleStroke;
	public int currentNode;
	
	MandalaNode[] mandalaNodeList;		
	
	public StateMandala(AhTextContext ahTextContext) {
		this.ahTextContext = ahTextContext;
		explodingText = new ExplodingText(ahTextContext.pg, ahTextContext);
		
		growState = new StateMandalaGrow(this, ahTextContext);
		menuState = new StateMandalaMenu(this, ahTextContext);
		storyWindowState = new StateMandalaStoryWindow(this, ahTextContext);
		backToMenuState = new StateMandalaBackToMenu(this, ahTextContext);
		
		mandalaState = growState;
		
		// size of the large circle on which the nodes arrange themselves
		circleDiameter = this.ahTextContext.height / (float) 1.5;
		largeCircleStroke = ahTextContext.height/37;
		
		xCenter = this.ahTextContext.width/2;
		yCenter = this.ahTextContext.height/2;
		scaleFactor = (float)1.0;
		
		zoneCollection = new ZoneCollection(ahTextContext);
		drawZoneRectangles = new DrawZoneRectangles(ahTextContext, zoneCollection, this);
		
		MandalaNode.numberOfNodes = 12;		
		mandalaNodeList = new MandalaNode[MandalaNode.numberOfNodes];
		
		// create outer nodes
		for(int i = 0; i < MandalaNode.numberOfNodes; i++){
			mandalaNodeList[i] = new MandalaNode(this, ahTextContext);
			mandalaNodeList[i].setNodePosition(i);
		}
		
		// create center node	
		mandalaNodeList = (MandalaNode[]) PApplet.append(mandalaNodeList, new MandalaNodeCenter(this, ahTextContext));
		
		// Set Story names and fade colors
		mandalaNodeList[0].setFadeColor(100, 1, 180, 0, 100, 1, 100, 1);
		mandalaNodeList[0].setNodeStoryName("the origins of conflict");
		mandalaNodeList[1].setFadeColor(1, 0, 100, 1, 100, 1, 100, 1);
		mandalaNodeList[1].setNodeStoryName("intergalactic archeologists");
		mandalaNodeList[2].setFadeColor(88, 0, 100, 1, 0, 0, 100, 1);
		mandalaNodeList[2].setNodeStoryName("cans with labels");
		mandalaNodeList[3].setFadeColor(100, 1, 199, 0, 0, 0, 100, 1);
		mandalaNodeList[3].setNodeStoryName("heads or tails");
		mandalaNodeList[4].setFadeColor(100, 1, 50, 0, 50, 0, 100, 1);
		mandalaNodeList[4].setNodeStoryName("measurment - tallying divinity");
		mandalaNodeList[5].setFadeColor(22, 0, 128, 0, 100, 1, 100, 1);
		mandalaNodeList[5].setNodeStoryName("the group");
		mandalaNodeList[6].setFadeColor(80, 0, 80, 0, 100, 1, 100, 1);
		mandalaNodeList[6].setNodeStoryName("two glimpse one");
		mandalaNodeList[7].setFadeColor(100, 1, 1, 0, 222, 0, 100, 1);
		mandalaNodeList[7].setNodeStoryName("trouble is my middle name");
		mandalaNodeList[8].setFadeColor(100, 1, 188, 0, 222, 0, 100, 1);
		mandalaNodeList[8].setNodeStoryName("mirroring");
		mandalaNodeList[9].setFadeColor(166, 0, 188, 0, 100, 1, 100, 1);
		mandalaNodeList[9].setNodeStoryName("well-used dollar bill");
		mandalaNodeList[10].setFadeColor(198, 0, 11, 0, 100, 1, 100, 1);
		mandalaNodeList[10].setNodeStoryName("nested awakenings");
		mandalaNodeList[11].setFadeColor(25, 0, 11, 0, 100, 1, 100, 1);
		mandalaNodeList[11].setNodeStoryName("passengers - parasites");
		mandalaNodeList[12].setFadeColor(100, 1, 0, 0, 0, 0, 100, 1);
		mandalaNodeList[12].setNodeStoryName("the story of being invisible");


	}
	
	// /////////////////////////////////////////////////////////////
	// SET METHODS//////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	public void touchEventAddCursor(long sessionID, int cursorX, int cursorY) {
		mandalaState.touchEventAddCursor(sessionID, cursorX, cursorY);
	}
	
	public void touchEventUpdateCursor(long sessionID, int cursorX, int cursorY) {
		mandalaState.touchEventUpdateCursor(sessionID, cursorX, cursorY);
	}

	public void touchEventRemoveCursor(long sessionID) {
		mandalaState.touchEventRemoveCursor(sessionID);
	}

	public void timer() {
		// TODO Auto-generated method stub
		
	}
	
	public void displayGraphics() {
		ahTextContext.frameRate(25);
		explodingText.drawXplode();
		mandalaState.displayGraphics();
	}
	
	public void setState(StateInterface mandalaState){
		this.mandalaState = mandalaState;
	}

	public StateInterface getGrowState(){
		return growState;
	}
	
	public StateInterface getMenuState(){
		return menuState;
	}

	public StateInterface getStoryWindowState(){
		return storyWindowState;
	}
	
	public StateInterface getBackToMenuState(){
		return backToMenuState;
	}
	
	public void reset(){
		explodingText.resetParticles();
		ahTextContext.tuioHandler.removeAllObservers();
		ahTextContext.tuioHandler.registerObserver(ahTextContext);
		scaleFactor = (float)1.0;
		zoneCollection.KillAllActiveZonesAndIDs();
		for(int i = 0; i < mandalaNodeList.length; i++){
			mandalaNodeList[i].setAnimationActive(false);
			mandalaNodeList[i].setTriggerActive(false);
			mandalaNodeList[i].setShouldFade(false);
			mandalaNodeList[i].resetColors();
		}
		mandalaState = growState;
	}
	
	// /////////////////////////////////////////////////////////////
	// SET SETTERS AND GETTER///////////////////////////////////////
	// /////////////////////////////////////////////////////////////

}
