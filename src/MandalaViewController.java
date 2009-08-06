import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;

// ========================================================================= // 
// AH-Opera | Interactive 2D Mandala Wheel
// (C) 2009 Jim Murphy, Dimitri Diakopoulos
// http://ah-opera.org
// ========================================================================= //


public class MandalaViewController {

	float rot;
	float addfillb = 10;
	float addfillc = 255;
	float x;
	float y;
	float xCenter;
	float yCenter;
	float circleRadius;
	float circleStroke;
	float nodeRadius;
	float nodeStroke;
	float numberOfNodes = 12;
	float xA;
	float yA;
	float screenHeight;
	float scalingFactor = (float) 1.0;
	int button;
	
	



	PApplet parent; // The parent PApplet that we will render ourselves onto
	PFont mandalaFont;
	MandalaHover mandalaHover;
	MandalaNode[] mandalaNodeList;
	float[][] nodePos;





	MandalaViewController(PApplet p) {
		parent = p;
		screenHeight = parent.height*scalingFactor;
		nodeRadius = screenHeight/(float)10;
		nodeStroke = screenHeight/60;
		circleRadius = screenHeight/(float)1.5;
		circleStroke = screenHeight/37;
		xCenter = parent.width/2;
		yCenter = parent.height/2;
		mandalaFont = parent.loadFont("AvantGuard-30.vlw");
		nodePos = new float [(int) numberOfNodes][2];
		mandalaNodeList = new MandalaNode[(int) numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			xA = PApplet.cos(PApplet.radians((float) (i * (360.0 / numberOfNodes))))
			* (screenHeight / 3);
			yA = PApplet.sin(PApplet.radians((float) (i * (360.0 / numberOfNodes))))
			* (screenHeight / 3);
			mandalaNodeList[i] = new MandalaNode(parent, i + 1, xA, yA, nodeRadius, nodeStroke);
			nodePos[i][0] = xA;
			nodePos[i][1] = yA;
		}
		mandalaHover = new MandalaHover(parent, mandalaFont, numberOfNodes, nodeRadius, circleRadius, nodePos, xCenter, yCenter, screenHeight);
	}





	void drawMandalaNode(MandalaNode[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i].drawMandalaNode();
		}

	}

	void displayMandala() {

		parent.smooth();
		parent.ellipseMode(PConstants.CENTER);
		parent.translate(xCenter, yCenter);
		//parent.translate(parent.screen.width, parent.screen.height);
	
		parent.background(255);
		parent.stroke(0);
		parent.strokeWeight(circleStroke);
		parent.ellipseMode(PConstants.CENTER);
		parent.ellipse(0, 0, circleRadius, circleRadius);
		parent.strokeWeight(nodeStroke);
		parent.ellipseMode(PConstants.CENTER);
		parent.ellipse(0, 0, nodeRadius, nodeRadius);

		// textleft();

		drawMandalaNode(mandalaNodeList);
		mandalaHover.drawNodeHover(parent.mouseX, parent.mouseY);

		// hover();
		//System.out.println(button);
	}

}
