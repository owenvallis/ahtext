import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

/**
 * Draws a single Mandala node, enables the fade behavior, and returns the state of the trigger
 * 
 * @author jhochenbaum, ovallis, jmurphy, ddiakopoulos
 *
 */
public class MandalaNode implements Observer {


	Observable observable;
	MandalaViewController mandalaViewController;
	PApplet parent;
	PFont mandalaFont;

	//node fields
	private int numberOfNodes;
	private int nodePosition;
	private float nodeCenterX, nodeCenterY;
	private float circleDiameter, circleRadius;
	private float nodeDiameter, nodeRadius;
	private int nodeStrokeWeight;

	//node fade fields
	private float nodeFadeDiameter;
	private int nodeFadeStrokeWeight;
	private int lineFadeStrokeWeight;
	private float fadeRate;

	//node fade color fields
	private int red, redTemp, fadeStateRed;
	private int green, greenTemp, fadeStateGreen;
	private int blue, blueTemp, fadeStateBlue;
	private int alpha, alphaTemp, fadeStateAlpha;


	//TBar fade position holders
	private float spokeXA, spokeXB, spokeYA, spokeYB; //the spokes
	private float tbarCounterClockwiseXA, tbarCounterClockwiseXB, tbarCounterClockwiseYA, tbarCounterClockwiseYB; //the spokes
	private float tbarXA, tbarXB, tbarYA, tbarYB; //the spokes
	private float radiansOfNodeIntersect;
	private boolean shouldFade;

	//global pulse fields
	private float angle;
	private float pulser;
	private float pulsate;

	//text
	private String nodeStoryName;
	private float textXAlign, textYAlign;

	//tuio input
	private int cursorX, cursorY;

	public MandalaNode(Observable observable, MandalaViewController mandalaViewController, PApplet parent, PFont mandalaFont){
		// constructor
		this.observable = observable;
		observable.addObserver(this);
		this.mandalaViewController = mandalaViewController;
		mandalaViewController.addObserver(this);
		this.parent = parent;
		this.mandalaFont = mandalaFont;

		//text fields
		textXAlign = parent.width/2;
		textYAlign = parent.height/20;
		pulser = (float) .015;
		nodeStoryName = "default";

		//node fields
		numberOfNodes = 12; //TODO add number of nodes as field to pass in from view controller instead of hard setting
		nodePosition = 0;
		nodeCenterX = 0; 
		nodeCenterY = 0;
		circleDiameter = this.parent.height/(float)1.5;
		circleRadius = circleDiameter/2;
		nodeDiameter = this.parent.height/(float)10;
		nodeRadius = nodeDiameter/2;
		nodeStrokeWeight = this.parent.height/60;

		//node fade fields
		nodeFadeDiameter = this.parent.height/(float)9;
		nodeFadeStrokeWeight = this.parent.height / 36;
		lineFadeStrokeWeight = this.parent.height / 30;
		fadeRate = 4;
		setRadiansOfNodeIntersect();
		shouldFade = false;
	}

	// /////////////////////////////////////////////////////////////
	// SET SETTERS AND GETTER///////////////////////////////////////
	// /////////////////////////////////////////////////////////////
	/**
	 * @param numberOfNodes sets the number of nodes along the circumference of the mandala
	 */
	public void setNumberOfNodes(int numberOfNodes) {
		if(numberOfNodes > 0)
			this.numberOfNodes = numberOfNodes;
		else
			this.numberOfNodes = 1;
	}	

	/**
	 * @return the mandalaFont
	 */
	public PFont getMandalaFont() {
		return mandalaFont;
	}

	/**
	 * @param mandalaFont the mandalaFont to set
	 */
	public void setMandalaFont(PFont mandalaFont) {
		this.mandalaFont = mandalaFont;
	}

	/**
	 * @return the nodeRadius
	 */
	public float getNodeRadius() {
		return nodeRadius;
	}

	/**
	 * @param nodeRadius the nodeRadius to set
	 */
	public void setNodeRadius(float nodeRadius) {
		this.nodeRadius = nodeRadius;
	}

	/**
	 * @return the nodeFadeStrokeWeight
	 */
	public int getNodeFadeStrokeWeight() {
		return nodeFadeStrokeWeight;
	}

	/**
	 * @param nodeFadeStrokeWeight the nodeFadeStrokeWeight to set
	 */
	public void setNodeFadeStrokeWeight(int nodeFadeStrokeWeight) {
		this.nodeFadeStrokeWeight = nodeFadeStrokeWeight;
	}

	/**
	 * @return the fadeRate
	 */
	public float getFadeRate() {
		return fadeRate;
	}

	/**
	 * @param fadeRate the fadeRate to set
	 */
	public void setFadeRate(float fadeRate) {
		this.fadeRate = fadeRate;
	}

	/**
	 * @return the red
	 */
	public int getRed() {
		return red;
	}

	/**
	 * @param red the red to set
	 */
	public void setRed(int red) {
		this.red = red;
	}

	/**
	 * @return the redTemp
	 */
	public int getRedTemp() {
		return redTemp;
	}

	/**
	 * @param redTemp the redTemp to set
	 */
	public void setRedTemp(int redTemp) {
		this.redTemp = redTemp;
	}

	/**
	 * @return the fadeStateRed
	 */
	public int getFadeStateRed() {
		return fadeStateRed;
	}

	/**
	 * @param fadeStateRed the fadeStateRed to set
	 */
	public void setFadeStateRed(int fadeStateRed) {
		this.fadeStateRed = fadeStateRed;
	}

	/**
	 * @return the green
	 */
	public int getGreen() {
		return green;
	}

	/**
	 * @param green the green to set
	 */
	public void setGreen(int green) {
		this.green = green;
	}

	/**
	 * @return the greenTemp
	 */
	public int getGreenTemp() {
		return greenTemp;
	}

	/**
	 * @param greenTemp the greenTemp to set
	 */
	public void setGreenTemp(int greenTemp) {
		this.greenTemp = greenTemp;
	}

	/**
	 * @return the fadeStateGreen
	 */
	public int getFadeStateGreen() {
		return fadeStateGreen;
	}

	/**
	 * @param fadeStateGreen the fadeStateGreen to set
	 */
	public void setFadeStateGreen(int fadeStateGreen) {
		this.fadeStateGreen = fadeStateGreen;
	}

	/**
	 * @return the blue
	 */
	public int getBlue() {
		return blue;
	}

	/**
	 * @param blue the blue to set
	 */
	public void setBlue(int blue) {
		this.blue = blue;
	}

	/**
	 * @return the blueTemp
	 */
	public int getBlueTemp() {
		return blueTemp;
	}

	/**
	 * @param blueTemp the blueTemp to set
	 */
	public void setBlueTemp(int blueTemp) {
		this.blueTemp = blueTemp;
	}

	/**
	 * @return the fadeStateBlue
	 */
	public int getFadeStateBlue() {
		return fadeStateBlue;
	}

	/**
	 * @param fadeStateBlue the fadeStateBlue to set
	 */
	public void setFadeStateBlue(int fadeStateBlue) {
		this.fadeStateBlue = fadeStateBlue;
	}

	/**
	 * @return the alpha
	 */
	public int getAlpha() {
		return alpha;
	}

	/**
	 * @param alpha the alpha to set
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	/**
	 * @return the alphaTemp
	 */
	public int getAlphaTemp() {
		return alphaTemp;
	}

	/**
	 * @param alphaTemp the alphaTemp to set
	 */
	public void setAlphaTemp(int alphaTemp) {
		this.alphaTemp = alphaTemp;
	}

	/**
	 * @return the fadeStateAlpha
	 */
	public int getFadeStateAlpha() {
		return fadeStateAlpha;
	}

	/**
	 * @param fadeStateAlpha the fadeStateAlpha to set
	 */
	public void setFadeStateAlpha(int fadeStateAlpha) {
		this.fadeStateAlpha = fadeStateAlpha;
	}

	/**
	 * @return the tbarCounterClockwiseXA
	 */
	public float getTbarCounterClockwiseXA() {
		return tbarCounterClockwiseXA;
	}

	/**
	 * @param tbarCounterClockwiseXA the tbarCounterClockwiseXA to set
	 */
	public void setTbarCounterClockwiseXA(float tbarCounterClockwiseXA) {
		this.tbarCounterClockwiseXA = tbarCounterClockwiseXA;
	}

	/**
	 * @return the tbarCounterClockwiseXB
	 */
	public float getTbarCounterClockwiseXB() {
		return tbarCounterClockwiseXB;
	}

	/**
	 * @param tbarCounterClockwiseXB the tbarCounterClockwiseXB to set
	 */
	public void setTbarCounterClockwiseXB(float tbarCounterClockwiseXB) {
		this.tbarCounterClockwiseXB = tbarCounterClockwiseXB;
	}

	/**
	 * @return the tbarCounterClockwiseYA
	 */
	public float getTbarCounterClockwiseYA() {
		return tbarCounterClockwiseYA;
	}

	/**
	 * @param tbarCounterClockwiseYA the tbarCounterClockwiseYA to set
	 */
	public void setTbarCounterClockwiseYA(float tbarCounterClockwiseYA) {
		this.tbarCounterClockwiseYA = tbarCounterClockwiseYA;
	}

	/**
	 * @return the tbarCounterClockwiseYB
	 */
	public float getTbarCounterClockwiseYB() {
		return tbarCounterClockwiseYB;
	}

	/**
	 * @param tbarCounterClockwiseYB the tbarCounterClockwiseYB to set
	 */
	public void setTbarCounterClockwiseYB(float tbarCounterClockwiseYB) {
		this.tbarCounterClockwiseYB = tbarCounterClockwiseYB;
	}

	/**
	 * @return the tbarXA
	 */
	public float getTbarXA() {
		return tbarXA;
	}

	/**
	 * @param tbarXA the tbarXA to set
	 */
	public void setTbarXA(float tbarXA) {
		this.tbarXA = tbarXA;
	}

	/**
	 * @return the tbarXB
	 */
	public float getTbarXB() {
		return tbarXB;
	}

	/**
	 * @param tbarXB the tbarXB to set
	 */
	public void setTbarXB(float tbarXB) {
		this.tbarXB = tbarXB;
	}

	/**
	 * @return the tbarYA
	 */
	public float getTbarYA() {
		return tbarYA;
	}

	/**
	 * @param tbarYA the tbarYA to set
	 */
	public void setTbarYA(float tbarYA) {
		this.tbarYA = tbarYA;
	}

	/**
	 * @return the tbarYB
	 */
	public float getTbarYB() {
		return tbarYB;
	}

	/**
	 * @param tbarYB the tbarYB to set
	 */
	public void setTbarYB(float tbarYB) {
		this.tbarYB = tbarYB;
	}

	/**
	 * @return the radiansOfNodeIntersect
	 */
	public float getRadiansOfNodeIntersect() {
		return radiansOfNodeIntersect;
	}

	/**
	 * @param radiansOfNodeIntersect the radiansOfNodeIntersect to set
	 */
	public void setRadiansOfNodeIntersect(float radiansOfNodeIntersect) {
		this.radiansOfNodeIntersect = radiansOfNodeIntersect;
	}

	/**
	 * @return the shouldFade
	 */
	public boolean isShouldFade() {
		return shouldFade;
	}

	/**
	 * @param shouldFade the shouldFade to set
	 */
	public void setShouldFade(boolean shouldFade) {
		this.shouldFade = shouldFade;
	}

	/**
	 * @return the angle
	 */
	public float getAngle() {
		return angle;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAngle(float angle) {
		this.angle = angle;
	}

	/**
	 * @return the pulser
	 */
	public float getPulser() {
		return pulser;
	}

	/**
	 * @param pulser the pulser to set
	 */
	public void setPulser(float pulser) {
		this.pulser = pulser;
	}

	/**
	 * @return the pulsate
	 */
	public float getPulsate() {
		return pulsate;
	}

	/**
	 * @param pulsate the pulsate to set
	 */
	public void setPulsate(float pulsate) {
		this.pulsate = pulsate;
	}

	/**
	 * @return the textXAlign
	 */
	public float getTextXAlign() {
		return textXAlign;
	}

	/**
	 * @param textXAlign the textXAlign to set
	 */
	public void setTextXAlign(float textXAlign) {
		this.textXAlign = textXAlign;
	}

	/**
	 * @return the textYAlign
	 */
	public float getTextYAlign() {
		return textYAlign;
	}

	/**
	 * @param textYAlign the textYAlign to set
	 */
	public void setTextYAlign(float textYAlign) {
		this.textYAlign = textYAlign;
	}

	/**
	 * @return the cursorX
	 */
	public int getCursorX() {
		return cursorX;
	}

	/**
	 * @param cursorX the cursorX to set
	 */
	public void setCursorX(int cursorX) {
		this.cursorX = cursorX;
	}

	/**
	 * @return the cursorY
	 */
	public int getCursorY() {
		return cursorY;
	}

	/**
	 * @param cursorY the cursorY to set
	 */
	public void setCursorY(int cursorY) {
		this.cursorY = cursorY;
	}

	/**
	 * @return the nodeCenterX
	 */
	public float getNodeCenterX() {
		return nodeCenterX;
	}

	/**
	 * @return the nodeCenterY
	 */
	public float getNodeCenterY() {
		return nodeCenterY;
	}

	/**
	 * @param nodeStrokeWeight the nodeStrokeWeight to set
	 */
	public void setNodeStrokeWeight(int nodeStrokeWeight) {
		this.nodeStrokeWeight = nodeStrokeWeight;
	}

	/**
	 * @return the numberOfNodes
	 */
	public int getNumberOfNodes() {
		return numberOfNodes;
	}

	/**
	 * @param nodePosition sets the position in the Mandala
	 */
	public void setNodePosition(int nodePosition) {
		this.nodePosition = nodePosition;
		setNodeCenter();
		setTBarPositions();
	}
	/**
	 * @return the nodePosition
	 */
	public int getNodePosition() {
		return nodePosition;
	}
	/**
	 * Set the Node position center X and Y coordinates
	 */
	private void setNodeCenter(){
		this.nodeCenterX = PApplet.cos(PApplet.radians((float) (nodePosition * (360.0 / numberOfNodes))))
		* circleRadius;
		this.nodeCenterY = PApplet.sin(PApplet.radians((float) (nodePosition * (360.0 / numberOfNodes))))
		* circleRadius;
	}
	/**
	 * @param circleDiameter the circleDiameter to set
	 */
	public void setCircleDiameter(float circleDiameter) {
		this.circleDiameter = circleDiameter;
		this.circleRadius = circleDiameter/2;
	}
	/**
	 * @return the circleDiameter
	 */
	public float getCircleDiameter() {
		return circleDiameter;
	}
	/**
	 * @return the circleRadius
	 */
	public float getCircleRadius() {
		return circleRadius;
	}
	/**
	 * @param circleRadius the circleRadius to set
	 */
	public void setCircleRadius(float circleRadius) {
		this.circleRadius = circleRadius;
	}
	/**
	 * @param nodeCenterX the nodeCenterX to set
	 */
	public void setNodeCenterX(float nodeCenterX) {
		this.nodeCenterX = nodeCenterX;
	}		

	/**
	 * @param nodeCenterY the nodeCenterY to set
	 */
	public void setNodeCenterY(float nodeCenterY) {
		this.nodeCenterY = nodeCenterY;
	}		

	/**
	 * @param nodeDiameter the diameter of the node
	 */
	public void setNodeDiameter(float nodeDiameter) {
		this.nodeDiameter = nodeDiameter;
		this.nodeRadius = nodeDiameter/2;
	}
	/**
	 * @return the nodeDiameter
	 */
	public float getNodeDiameter() {
		return nodeDiameter;
	}

	/**
	 * @param nodeStrokeWeight the width of the ellipse stroke
	 */
	public void setnodeStrokeWeight(int nodeStrokeWeight) {
		this.nodeStrokeWeight = nodeStrokeWeight;
	}
	/**
	 * @return the nodeStrokeWeight
	 */
	public int getNodeStrokeWeight() {
		return nodeStrokeWeight;
	}

	/**
	 * @param fadeRate the rate at which the fade overlay will increment towards full color
	 */
	public void setfadeRate(float fadeRate) {
		this.fadeRate = fadeRate;
	}
	/**
	 * @return the nodeFadeDiameter
	 */
	public float getNodeFadeDiameter() {
		return nodeFadeDiameter;
	}

	/**
	 * @param nodeFadeDiameter the nodeFadeDiameter to set
	 */
	public void setNodeFadeDiameter(float nodeFadeDiameter) {
		this.nodeFadeDiameter = nodeFadeDiameter;
	}

	/**
	 * @return the spokeXA
	 */
	public float getSpokeXA() {
		return spokeXA;
	}

	/**
	 * @param spokeXA the spokeXA to set
	 */
	public void setSpokeXA(float spokeXA) {
		this.spokeXA = spokeXA;
	}

	/**
	 * @return the spokeXB
	 */
	public float getSpokeXB() {
		return spokeXB;
	}

	/**
	 * @param spokeXB the spokeXB to set
	 */
	public void setSpokeXB(float spokeXB) {
		this.spokeXB = spokeXB;
	}

	/**
	 * @return the spokeYA
	 */
	public float getSpokeYA() {
		return spokeYA;
	}

	/**
	 * @param spokeYA the spokeYA to set
	 */
	public void setSpokeYA(float spokeYA) {
		this.spokeYA = spokeYA;
	}

	/**
	 * @return the spokeYB
	 */
	public float getSpokeYB() {
		return spokeYB;
	}

	/**
	 * @param spokeYB the spokeYB to set
	 */
	public void setSpokeYB(float spokeYB) {
		this.spokeYB = spokeYB;
	}

	/**
	 * @return the lineFadeStrokeWeight
	 */
	public int getLineFadeStrokeWeight() {
		return lineFadeStrokeWeight;
	}

	/**
	 * @param lineFadeStrokeWeight the lineFadeStrokeWeight to set
	 */
	public void setLineFadeStrokeWeight(int lineFadeStrokeWeight) {
		this.lineFadeStrokeWeight = lineFadeStrokeWeight;
	}


	/**
	 * sets the draw coordinates for the tbar connector segments of the fade method
	 */
	private void setTBarPositions(){
		//Calculate coordinates for center spoke lines
		this.spokeXA = PApplet.cos(PApplet.radians((float) (nodePosition * (360.0 / numberOfNodes))))
		* nodeFadeDiameter/(float)1.85;
		this.spokeYA = PApplet.sin(PApplet.radians((float) (nodePosition * (360.0 / numberOfNodes))))
		* nodeFadeDiameter/(float)1.85;	

		this.spokeXB = PApplet.cos(PApplet.radians((float) (nodePosition * (360.0 / numberOfNodes))))
		* (circleRadius-(nodeFadeDiameter/(float)1.85));
		this.spokeYB = PApplet.sin(PApplet.radians((float) (nodePosition * (360.0 / numberOfNodes))))
		* (circleRadius-(nodeFadeDiameter/(float)1.85));

		//Calculate coordinates for Node-Connect lines
		// 0.995 = scales the diameter screenheight / 2.985
		//counterclockwise Tbar segment
		this.tbarCounterClockwiseXA = PApplet.cos(PApplet.radians((float) (nodePosition * (360.0 / numberOfNodes)))+radiansOfNodeIntersect)
		* circleRadius;
		this.tbarCounterClockwiseYA = PApplet.sin(PApplet.radians((float) (nodePosition * (360.0 / numberOfNodes)))+radiansOfNodeIntersect)
		* circleRadius;

		this.tbarCounterClockwiseXB = PApplet.cos(PApplet.radians((float) ((nodePosition+1) * (360.0 / numberOfNodes)))-radiansOfNodeIntersect)
		* circleRadius;
		this.tbarCounterClockwiseYB = PApplet.sin(PApplet.radians((float) ((nodePosition+1) * (360.0 / numberOfNodes)))-radiansOfNodeIntersect)
		* circleRadius;	

		//clockwise Tbar segment
		this.tbarXA = PApplet.cos(PApplet.radians((float) (nodePosition * (360.0 / numberOfNodes)))-radiansOfNodeIntersect)
		* circleRadius;
		this.tbarYA = PApplet.sin(PApplet.radians((float) (nodePosition * (360.0 / numberOfNodes)))-radiansOfNodeIntersect)
		* circleRadius;

		this.tbarXB = PApplet.cos(PApplet.radians((float) (((nodePosition+(numberOfNodes-1))%numberOfNodes) * (360.0 / numberOfNodes)))+radiansOfNodeIntersect)
		* circleRadius;
		this.tbarYB = PApplet.sin(PApplet.radians((float) (((nodePosition+(numberOfNodes-1))%numberOfNodes) * (360.0 / numberOfNodes)))+radiansOfNodeIntersect)
		* circleRadius;
	}

	/**
	 * Sets the position that the node intersects along the circumference of the larger mandala circle
	 */
	private void setRadiansOfNodeIntersect() {
		//radiansOfNodeIntersect = (float)((2.3*(Math.asin(((nodeRadius))/circleDiameter))));	
		radiansOfNodeIntersect = (float)((2.325*(Math.asin(((nodeRadius))/circleDiameter))));
	}

	/**
	 * @param nodeStoryName the nodeStoryName to set
	 */
	public void setNodeStoryName(String nodeStoryName) {
		this.nodeStoryName = nodeStoryName;
	}

	/**
	 * @return the nodeStoryName
	 */
	public String getNodeStoryName() {
		return nodeStoryName;
	}

	// /////////////////////////////////////////////////////////////
	// SET METHODS//////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////
	/**
	 * takes in the updates from all sources
	 */
	public void update(Observable o, Object arg) {
		if(o instanceof TuioHandler){
			TuioHandler tuioHandler = (TuioHandler)o;
			this.cursorX = tuioHandler.getCursorX();
			this.cursorY = tuioHandler.getCursorY();
			if(over(nodeCenterX, nodeCenterY, this.cursorX, this.cursorY)){
				shouldFade = true;
			} else {
				shouldFade = false;
			}

		} else if (o instanceof MandalaViewController){
			drawMandalaNode();
			if(shouldFade){
				fadeOverlay();
			} else {
				redTemp = red;
				greenTemp = green; 
				blueTemp = blue;
				alphaTemp = alpha;
				angle = 0;
			}
		}


	}

	/**
	 * Test to see if TUIO event is over this particular node
	 * 
	 * @param nodePostionX the center of the node along the x-axis
	 * @param nodePostionY the center of the node along the y-axis
	 * @param x the current TUIO Coordinates
	 * @param y
	 * 
	 * @return whether the TUIO event is inside the Node
	 */
	boolean over(float nodePostionX,float nodePostionY, int x, int y) {

		float disX = (nodePostionX+(mandalaViewController.getxCenter()*mandalaViewController.getScaleFactor())) - x;
		float disY = (nodePostionY+(mandalaViewController.getyCenter()*mandalaViewController.getScaleFactor())) - y;
		if (Math.sqrt(Math.pow(disX,2) + Math.pow(disY,2)) < nodeRadius) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Draw the Mandala node on the screen
	 */
	public void drawMandalaNode(){
		parent.fill(255);
		parent.stroke(0);
		parent.strokeWeight(nodeStrokeWeight);
		parent.ellipseMode(PConstants.CENTER);
		parent.ellipse(nodeCenterX, nodeCenterY, nodeDiameter, nodeDiameter);		
	}

	/**
	 * Draws the color fade overlay once a mandala is touched
	 * 
	 * @return whether or not the fade method should implement
	 */
	private void fadeOverlay(){
		parent.fill(255);
		angle = angle + pulser;
		pulsate = PApplet.abs(255 * PApplet.sin(angle));
		drawFadeNode();
		drawTBarFades();
		this.parent.textFont(mandalaFont);
		this.parent.textAlign(PConstants.CENTER);
		parent.fill(0, 0, 0, pulsate);
		parent.text(nodeStoryName, textXAlign, textYAlign);		
	}

	/**
	 * Draws the InterNode connecting lines when an event is detected over the node
	 */
	private void drawTBarFades() {
		parent.stroke(0,0,0,this.alphaTemp  += fadeRate);
		parent.strokeWeight(lineFadeStrokeWeight);
		parent.line(spokeXA, spokeYA, spokeXB, spokeYB);
		parent.line(tbarCounterClockwiseXA, tbarCounterClockwiseYA, tbarCounterClockwiseXB, tbarCounterClockwiseYB);
		parent.line(tbarXA, tbarYA, tbarXB, tbarYB);		
	}

	/**
	 * Set the node stroke weight and color, and draw the ellipse over the existing black node
	 */
	public void drawFadeNode() { //TODO figure out better way to extend to MandalaNodeCenter so this can remain private
		parent.strokeWeight(nodeFadeStrokeWeight);
		defineFadeColor();
		parent.ellipse(nodeCenterX, nodeCenterY, nodeFadeDiameter, nodeFadeDiameter);			
	}

	/**
	 * Defines the color of the fade overlay effect
	 * 
	 * @param red
	 * @param fadeStateRed whether fading is on or not
	 * @param green
	 * @param fadeStateGreen whether fading is on or not
	 * @param blue
	 * @param fadeStateBlue whether fading is on or not
	 * @param alpha
	 * @param fadeStateAlpha whether fading is on or not
	 */
	public void setFadeColor(int red, int fadeStateRed, int green, int fadeStateGreen, 
			int blue, int fadeStateBlue,int alpha, int fadeStateAlpha){	

		this.red = redTemp = red; 	this.fadeStateRed = fadeStateRed;
		this.green = greenTemp = green; this.fadeStateGreen = fadeStateGreen;
		this.blue = blueTemp = blue; 	this.fadeStateBlue = fadeStateBlue;
		this.alpha = alphaTemp = alpha; this.fadeStateAlpha = fadeStateAlpha;
	}

	/**
	 * Method implements the fade colors on the overlay stroke function
	 * This is called in the draw loop of the fadeOverlay() method
	 */
	public void defineFadeColor(){
		parent.stroke (this.redTemp += (fadeRate * this.fadeStateRed), this.greenTemp += (fadeRate * this.fadeStateGreen),
				this.blueTemp  += (fadeRate * this.fadeStateBlue), this.alphaTemp  += (fadeRate * this.fadeStateAlpha)); //define the color
	}

}
