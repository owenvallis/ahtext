import processing.core.PApplet;
import processing.core.PConstants;

// ========================================================================= // 
// AH-Opera | Interactive 2D Mandala Wheel
// (C) 2009 Jim Murphy, Dimitri Diakopoulos
// http://ah-opera.org
// ========================================================================= // 

public class MandalaNode {

	PApplet parent; // The parent PApplet that we will render ourselves onto
	int id;
	float x;
	float y;
	int r, g, b;

	MandalaNode(PApplet p, int cId, float cX, float cY) {
		parent = p;
		id = cId;
		x = cX;
		y = cY;
	}

	void drawMandalaNode(){
		parent.stroke(0);
		parent.strokeWeight(parent.height/60);
		parent.fill(255);
		parent.ellipseMode(PConstants.CENTER);
		parent.ellipse(x, y, parent.height/(float)10, parent.height/(float)10);
		// noStroke();
	}

}
