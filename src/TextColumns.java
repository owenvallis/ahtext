import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PGraphics;

public class TextColumns {

	private float fallRate;
	private int charPosX;
	private int rows;
	private int x = 0;
	public int y = 1;
	private float boundHeight;
	private float boundWidth;
	int flag;
	
	PApplet parent;// The pg PApplet that we will render ourselves onto
	PGraphics pg;
	PFont ahTextFont;


	// sets the column position, and column width
	TextColumns(PApplet p, PGraphics pgraphics, int posX, float charBounds, float textHght, int rws, PFont at) {
		parent = p;
		pg = pgraphics;
		ahTextFont = at;
		
		if (rws > 0) {
			rows = rws;
		} else {
			rows = 1;
		}

		if (posX > 0) {
			charPosX = (posX + 1);
		} else {
			charPosX = 1;
		}

		if (charBounds > 0) {
			boundWidth = charBounds;
		} else {
			boundWidth = 1;
		}

		if (textHght > 0) {
			boundHeight = textHght;
		} else {
			boundHeight = 1;
		}
		fallRate = parent.random(1, 10);
	}

	void display()
	{
		pg.textFont(ahTextFont);
		pg.textAlign(PConstants.CENTER);
		pg.textMode(PConstants.SCREEN);

		char letter;
		int let;

		//draws the text column
		for (int i = 1; i <= y; i++)
		{
			let = (int)parent.random(65,88);
			pg.fill(parent.random(255));
			letter = (char)let;
			pg.text(letter, (float)(charPosX*boundWidth-(boundWidth*.25)), i*boundHeight);
		}

		//sets rate for column growth
		if(x > fallRate)
		{
			if(y >= rows)
			{
				y = rows;
				flag = 1;
			}
			else
			{
				y++;
			} 
			x=0;
		}
		x++;
	}
	
	void resetValues(){
		
		fallRate = 0;
		x = 0;
		y = 1;
		flag = 0;
		
		fallRate = parent.random(1, 10);
	}

}
