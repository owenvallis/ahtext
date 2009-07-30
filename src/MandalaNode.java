import processing.core.PApplet;
import processing.core.PConstants;

// ========================================================================= // 
// AH-Opera | Interactive 2D Mandala Wheel
// (C) 2009 Jim Murphy, Dimitri Diakopoulos
// http://ah-opera.org
// ========================================================================= // 

public class MandalaNode {

	int id;
	float x;
	float y;
	float nodeRadius;
	float nodeStroke;
	int r, g, b;
	PApplet parent; // The parent PApplet that we will render ourselves onto

	MandalaNode(PApplet p,int cId, float cX, float cY, float nr, float ns) {
		id = cId;
		x = cX;
		y = cY;
		nodeRadius = nr;
		nodeStroke = ns;
		parent = p;
	}

	void drawMandalaNode(){
		parent.stroke(0);
		parent.strokeWeight(nodeStroke);
		parent.fill(255);
		parent.ellipseMode(PConstants.CENTER);
		parent.ellipse(x, y, nodeRadius, nodeRadius);
		// noStroke();
	}

}
