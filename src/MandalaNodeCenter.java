import java.util.Observable;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

/**
 * Draws the center Mandala node, enables the fade behavior, and returns the state of the trigger
 * 
 * @author jhochenbaum, ovallis
 *
 */
public final class MandalaNodeCenter extends MandalaNode {

	private float[][] mandalaSpokeLineCoordinates = new float[12][4];

	public MandalaNodeCenter(Observable observable, MandalaViewController mandalaViewController, PApplet parent, PFont mandalaFont){
		//constructor
		super(observable, mandalaViewController, parent, mandalaFont);

		super.setNodeCenterX(0);
		super.setNodeCenterY(0);
		setTBarPositions();



	}

	/**
	 * sets the draw coordinates for the tbar connector segments of the fade method
	 */
	private void setTBarPositions(){
		//Calculate coordinates for center spoke lines
		for(int x = 0; x < super.getNumberOfNodes(); x++){
			super.setSpokeXA(PApplet.cos(PApplet.radians((float) (x * (360.0 / super.getNumberOfNodes()))))
					* super.getNodeFadeDiameter()/(float)1.85);
			super.setSpokeYA(PApplet.sin(PApplet.radians((float) (x * (360.0 / super.getNumberOfNodes()))))
					* super.getNodeFadeDiameter()/(float)1.85);	

			super.setSpokeXB(PApplet.cos(PApplet.radians((float) (x * (360.0 / super.getNumberOfNodes()))))
					* (super.getCircleRadius()-(super.getNodeFadeDiameter()/(float)1.85)));
			super.setSpokeYB(PApplet.sin(PApplet.radians((float) (x * (360.0 / super.getNumberOfNodes()))))
					* (super.getCircleRadius()-(super.getNodeFadeDiameter()/(float)1.85)));

			mandalaSpokeLineCoordinates[x][0] = super.getSpokeXA(); 
			mandalaSpokeLineCoordinates[x][1] = super.getSpokeYA();
			mandalaSpokeLineCoordinates[x][2] = super.getSpokeXB();
			mandalaSpokeLineCoordinates[x][3] = super.getSpokeYB();	

		}
	}

	public void update(Observable o, Object arg) {
		if(o instanceof TuioHandler){
			TuioHandler tuioHandler = (TuioHandler)o;
			super.setCursorX(tuioHandler.getCursorX());
			super.setCursorY(tuioHandler.getCursorY());
			if(over(super.getNodeCenterX(), super.getNodeCenterY(), super.getCursorX(), super.getCursorY())){
				super.setShouldFade(true);
			} else {
				super.setShouldFade(false);
			}

		} else if (o instanceof MandalaViewController){
			drawMandalaNode();
			if(super.isShouldFade()){
				fadeOverlay();
			} else {
				super.setRedTemp(super.getRed());
				super.setGreenTemp(super.getGreen()); 
				super.setBlueTemp(super.getBlue());
				super.setAlphaTemp(super.getAlpha());
				super.setAngle(0);
			}
		}
	}

	/**
	 * Draws the color fade overlay once a mandala is touched
	 * 
	 * @return whether or not the fade method should implement
	 */
	private void fadeOverlay(){
		parent.fill(255);
		super.setAngle(super.getAngle() + super.getPulser());
		super.setPulsate(PApplet.abs(255 * PApplet.sin(super.getAngle())));
		drawTBarFades();
		super.drawFadeNode();
		this.parent.textFont(mandalaFont);
		this.parent.textAlign(PConstants.CENTER);
		parent.fill(0, 0, 0, super.getPulsate());
		parent.text(super.getNodeStoryName(), super.getTextXAlign(), super.getTextYAlign());		
	}

	/**
	 * Draws the InterNode connecting lines when an event is detected over the node
	 * @return 
	 */
	private void drawTBarFades() {
		parent.stroke(0,0,0,(super.getPulsate()*(float)0.75));
		parent.strokeWeight(super.getLineFadeStrokeWeight());
		for(int x = 0; x < super.getNumberOfNodes(); x++){
			super.setSpokeXA(mandalaSpokeLineCoordinates[x][0]);
			super.setSpokeYA(mandalaSpokeLineCoordinates[x][1]);
			super.setSpokeXB(mandalaSpokeLineCoordinates[x][2]);
			super.setSpokeYB(mandalaSpokeLineCoordinates[x][3]);
			parent.line(super.getSpokeXA(), super.getSpokeYA(), super.getSpokeXB(), super.getSpokeYB());		
		}
	}


}
