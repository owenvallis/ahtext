import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PGraphics;

public class MandalaHover {

	float addfill = 100;
	float pulsate;
	float nodeRadius;
	float nodeStroke;
	float textYAlign;
	PGraphics pg;
	PFont mandalaFont;

	MandalaHover(PGraphics pgraphics, PFont mf) {
		pg = pgraphics;
		mandalaFont = mf;
		nodeStroke = pg.height / 36;
		nodeRadius = pg.height / 9;
		textYAlign = pg.height / (float)2.14;
	}

	void drawNodeHover(int x, int y, float puls) {
		pulsate = puls;
		pg.textFont(mandalaFont);
		pg.textAlign(PConstants.CENTER);
		if (x > ((pg.width / 2) - pg.height / (float) 10)
				&& x < ((pg.width / 2) + pg.height / (float) 10)
				&& y > ((pg.height / 2) - pg.height / (float) 10)
				&& y < ((pg.height / 2) + pg.height / (float) 10)) {
			// print("13");
			pg.strokeWeight(nodeStroke);
			pg.stroke(addfill++, 0, 0, addfill++);
			pg.ellipse(0, 0, nodeRadius, nodeRadius);
			pg.strokeWeight(20);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("the story of being invisible", 0, textYAlign);
			pg.fill(255);
		}

		else {
			addfill = 100;
		}
	}

}
