import processing.core.PApplet;
import processing.core.PFont;

public class MandalaViewController {

	TuioHandler tuioHandler;
	PApplet parent;
	MandalaNodeCenter mandalaNodeCenter;
	MandalaNode[] mandalaNodeList;
	PFont mandalaFont;

	private float xCenter, yCenter;
	private float scaleFactor;

	public MandalaViewController(TuioHandler tuioHandler, PApplet parent){
		this.tuioHandler = tuioHandler;
		this.parent = parent;
		mandalaFont = parent.loadFont("AvantGuard-30.vlw");
		mandalaNodeCenter = new MandalaNodeCenter(this.tuioHandler, this, parent, mandalaFont);
		mandalaNodeCenter.setFadeColor(100, 1, 0, 0, 0, 0, 100, 1);
		mandalaNodeCenter.setNodeStoryName("the story of being invisible");
		mandalaNodeList = new MandalaNode[12]; //TODO fix to number of nodes
		for(int i = 0; i < 12; i++){
			mandalaNodeList[i] = new MandalaNode(this.tuioHandler, this, parent, mandalaFont);
			mandalaNodeList[i].setNodePosition(i);
		}
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
		xCenter = this.parent.width/2;
		yCenter = this.parent.height/2;
		scaleFactor = (float)1.0; // TODO use this to drive an animated move to the side screen with the mandala

	}

	// /////////////////////////////////////////////////////////////
	// SET SETTERS AND GETTER///////////////////////////////////////
	// /////////////////////////////////////////////////////////////
	/**
	 * @param xCenter the xCenter to set
	 */
	public void setxCenter(float xCenter) {
		this.xCenter = xCenter;
	}

	/**
	 * @return the xCenter
	 */
	public float getxCenter() {
		return xCenter;
	}

	/**
	 * @param yCenter the yCenter to set
	 */
	public void setyCenter(float yCenter) {
		this.yCenter = yCenter;
	}

	/**
	 * @return the yCenter
	 */
	public float getyCenter() {
		return yCenter;
	}

	/**
	 * @return the scaleFactor
	 */
	public float getScaleFactor() {
		return scaleFactor;
	}

	/**
	 * @param scaleFactor the scaleFactor to set
	 */
	public void setScaleFactor(float scaleFactor) {
		this.scaleFactor = scaleFactor;
	}

	// /////////////////////////////////////////////////////////////
	// SET METHODS//////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////	
	/**
	 * Notify the Observers whenever we get a TUIO event
	 * 
	 */
	public void displayMandala(){
		parent.frameRate(30);
		parent.smooth();
		parent.background(255);
		parent.translate(xCenter*scaleFactor, yCenter*scaleFactor);
		parent.scale(scaleFactor);
		parent.fill(255);
		parent.stroke(0);
		parent.strokeWeight(parent.height/37); // TODO This might extend MandalaNode, but we don't think so as it needs no additional behaviors
		parent.ellipse(0,0,parent.height/(float)1.5,parent.height/(float)1.5);
		mandalaNodeCenter.drawMandalaNode();
		if(mandalaNodeCenter.isShouldFade()){
			mandalaNodeCenter.fadeOverlay();
		} else {
			mandalaNodeCenter.resetColors();
		}
		if(mandalaNodeCenter.isAnimationActive()){
			tuioHandler.deleteObservers();
			if(scaleFactor > .1){
				scaleFactor -= .05;
			} 
		}
		for(int i = 0; i < 12; i++){
			mandalaNodeList[i].drawMandalaNode();
		}
		for(int i = 0; i < 12; i++){
			if(mandalaNodeList[i].isShouldFade()){
				mandalaNodeList[i].fadeOverlay();
			} else {
				mandalaNodeList[i].resetColors();
			}
			if(mandalaNodeList[i].isAnimationActive()){
				tuioHandler.deleteObservers();
				if(scaleFactor > .1){
					scaleFactor -= .05;
				} 
			}
		}
	}

	public void resetMandala() {
		scaleFactor = (float)1.0;
		mandalaNodeCenter.setAnimationActive(false);
		mandalaNodeCenter.setTriggerActive(false);
		mandalaNodeCenter.setShouldFade(false);
		mandalaNodeCenter.resetColors();
		tuioHandler.addObserver(mandalaNodeCenter);
		for(int i = 0; i < 12; i++){
			mandalaNodeList[i].setAnimationActive(false);
			mandalaNodeList[i].setTriggerActive(false);
			mandalaNodeList[i].setShouldFade(false);
			mandalaNodeList[i].resetColors();
			tuioHandler.addObserver(mandalaNodeList[i]);
		}
	}

}
