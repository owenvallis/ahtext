import processing.core.PConstants;
import processing.core.PGraphics;

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
	PGraphics pg;

	MandalaNode(PGraphics pgraphics,int cId, float cX, float cY, float nr, float ns) {
		id = cId;
		x = cX;
		y = cY;
		nodeRadius = nr;
		nodeStroke = ns;
		pg = pgraphics;
	}

	void drawMandalaNode(){
		pg.stroke(0);
		pg.strokeWeight(nodeStroke);
		pg.fill(255);
		pg.ellipseMode(PConstants.CENTER);
		pg.ellipse(x, y, nodeRadius, nodeRadius);
		// noStroke();
	}

}
