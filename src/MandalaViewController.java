import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;

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
	int button;



	PImage image;
	PGraphics pg;
	PFont mandalaFont;
	MandalaHover mandalaHover;
	MandalaNode[] mandalaNodeList;
	float[][] nodePos;



	PApplet parent; // The parent PApplet that we will render ourselves onto



	MandalaViewController(PApplet p, PGraphics pgraphics ) {
		parent = p;
		pg = pgraphics;
		nodeRadius = pg.height/(float)10;
		nodeStroke = pg.height/60;
		circleRadius = pg.height/(float)1.5;
		circleStroke = pg.height/37;
		xCenter = pg.width/2;
		yCenter = pg.height/2;
		mandalaFont = parent.loadFont("AvantGuard-30.vlw");
		nodePos = new float [(int) numberOfNodes][2];
		mandalaNodeList = new MandalaNode[(int) numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			xA = PApplet.cos(PApplet.radians((float) (i * (360.0 / numberOfNodes))))
			* (pg.height / 3);
			yA = PApplet.sin(PApplet.radians((float) (i * (360.0 / numberOfNodes))))
			* (pg.height / 3);
			mandalaNodeList[i] = new MandalaNode(pg, i + 1, xA, yA, nodeRadius, nodeStroke);
			nodePos[i][0] = xA;
			nodePos[i][1] = yA;
		}
		mandalaHover = new MandalaHover(pg, mandalaFont, numberOfNodes, nodeRadius, circleRadius, nodePos);
	}





	void drawMandalaNode(MandalaNode[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i].drawMandalaNode();
		}

	}

	void displayMandala() {

		pg.beginDraw();
		pg.smooth();
		pg.ellipseMode(PConstants.CENTER);
		pg.translate(xCenter, yCenter);

		// rot = (mouseX) * .00475;
		// rotate(rot);

		pg.background(255);
		pg.stroke(0);
		pg.strokeWeight(circleStroke);
		pg.ellipseMode(PConstants.CENTER);
		pg.ellipse(0, 0, circleRadius, circleRadius);
		pg.strokeWeight(nodeStroke);
		pg.ellipseMode(PConstants.CENTER);
		pg.ellipse(0, 0, nodeRadius, nodeRadius);

		// textleft();

		drawMandalaNode(mandalaNodeList);
		mandalaHover.drawNodeHover(parent.mouseX, parent.mouseY);
		pg.endDraw();
		parent.image(pg,0,0);
		// hover();
		//System.out.println(button);
	}

}
