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
	float pulse;
	float pulsate;
	float pulser;
	float angle;
	float x;
	float y;
	float xCenter;
	float yCenter;
	float circleRadius;
	float circleStroke;
	float nodeRadius;
	float nodeStroke;
	float numberOfNodes = 12;
	int button;
	

	PImage image;
	PGraphics pg;
	PFont mandalaFont;
	MandalaHover mandalaHover;
	MandalaNode[] mandalaNodeList;

	
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
		mandalaHover = new MandalaHover(pg, mandalaFont);
		mandalaNodeList = new MandalaNode[(int) numberOfNodes];
		for (int i = 0; i < numberOfNodes; i++) {
			float x = PApplet.cos(PApplet.radians((float) (i * (360.0 / numberOfNodes))))
					* (pg.height / 3);
			float y = PApplet.sin(PApplet.radians((float) (i * (360.0 / numberOfNodes))))
					* (pg.height / 3);
			mandalaNodeList[i] = new MandalaNode(pg, i + 1, x, y, nodeRadius, nodeStroke);
		}
	}
	




	void drawMandalaNode(MandalaNode[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i].drawMandalaNode();
		}

	}

	void displayMandala() {

		pg.beginDraw();
		pg.smooth();
		pulser = (float) .006;
		angle = angle + pulser;
		pulsate = PApplet.abs(70 * PApplet.sin(angle));
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
		mandalaHover.drawNodeHover(parent.mouseX, parent.mouseY, pulsate);
		pg.endDraw();
		parent.image(pg,0,0);
		// hover();
		//System.out.println(button);
	}

}
