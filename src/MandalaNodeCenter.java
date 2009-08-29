import processing.core.PApplet;


/**
 * Draws the center Mandala node, enables the fade behavior, and returns the state of the trigger
 * 
 * @author jhochenbaum, ovallis
 *
 */
public final class MandalaNodeCenter extends MandalaNode {
	


	
	private float[][] mandalaSpokeLineCoordinates = new float[12][4];

	public MandalaNodeCenter(StateMandala stateMandala, AhTextContext ahTextContext){
		//constructor
		super(stateMandala, ahTextContext);
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

	/**
	 * Draws the color fade overlay once a mandala is touched
	 * 
	 * @return whether or not the fade method should implement
	 */
	@Override
	public void fadeOverlay(){
		super.setAngle(super.getAngle() + super.getPulser());
		super.setPulsate(PApplet.abs(255 * PApplet.sin(super.getAngle())));
		drawTBarFades();
		super.drawFadeNode();
		if(ahTextContext.millis() - super.getLastNodeTouchTime() > super.getNodeTouchTime()) {
			super.setTriggerActive(true);
			if(!super.isAnimationActive()){
				g2d.setColor(black);
				g2d.setFont(font);
				super.paintHorizontallyCenteredText(g2d, "TOUCH", super.getNodeCenterX(), super.getNodeCenterY()+(ahTextContext.textWidth("G")/2));
			}
		}
	}

	/**
	 * Draws the InterNode connecting lines when an event is detected over the node
	 * @return 
	 */
	private void drawTBarFades() {
		ahTextContext.stroke(0,0,0,(super.getPulsate()*(float)0.75));
		ahTextContext.strokeWeight(super.getLineFadeStrokeWeight());
		for(int x = 0; x < super.getNumberOfNodes(); x++){
			super.setSpokeXA(mandalaSpokeLineCoordinates[x][0]);
			super.setSpokeYA(mandalaSpokeLineCoordinates[x][1]);
			super.setSpokeXB(mandalaSpokeLineCoordinates[x][2]);
			super.setSpokeYB(mandalaSpokeLineCoordinates[x][3]);
			ahTextContext.line(super.getSpokeXA(), super.getSpokeYA(), super.getSpokeXB(), super.getSpokeYB());		
		}
	}
	


}
