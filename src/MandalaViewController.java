import processing.core.PApplet;
import processing.core.PConstants;

// ========================================================================= // 
// AH-Opera | Interactive 2D Mandala Wheel
// (C) 2009 Jim Murphy, Dimitri Diakopoulos
// http://ah-opera.org
// ========================================================================= //


public class MandalaViewController {

	float rot;
	float addfill = 100;
	float addfillb = 10;
	float addfillc = 255;
	float pulse;
	float pulsate;
	float pulser;
	float angle;
	int button;
	float x;
	float y;
	

	PApplet parent; // The parent PApplet that we will render ourselves onto
	MandalaNode[] mandalaNodeList;
	
	MandalaViewController(PApplet p, MandalaNode[] x) {
		parent = p;
		mandalaNodeList = x;
	}
	




	void drawMandalaNode(MandalaNode[] x) {
		for (int i = 0; i < x.length; i++) {
			x[i].drawMandalaNode();
		}

	}

	void displayMandala() {

		pulser = (float) .006;
		angle = angle + pulser;
		pulsate = PApplet.abs(70 * PApplet.sin(angle));
		parent.ellipseMode(PConstants.CENTER);
		parent.translate((float)(parent.width/2), (float)(parent.height/2));

		// rot = (mouseX) * .00475;
		// rotate(rot);

		parent.background(255);
		parent.stroke(0);
		parent.strokeWeight(parent.height/37);
		parent.ellipseMode(PConstants.CENTER);
		parent.ellipse(0, 0, parent.height/(float)1.5, parent.height/(float)1.5);
		parent.strokeWeight(parent.height/60);
		parent.ellipseMode(PConstants.CENTER);
		parent.ellipse(0, 0, parent.height/(float)10, parent.height/(float)10);

		// textleft();

		drawMandalaNode(mandalaNodeList);
		// hover();
		System.out.println(button);
	}

}
