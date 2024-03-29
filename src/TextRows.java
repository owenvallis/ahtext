import java.util.Random;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;

public class TextRows {

	private int fillPos;
	private int charPosX;
	private int columns;
	private int x;
	private int direction;
	private float fillRate;
	private float fadeRate;
	private float charAlphaValue;
	private float boundHeight;
	private float boundWidth;
	static int flag;
	boolean addFlag = true;
	static int alphaFlag;
	boolean addAlphaFlag = true;
	
	PApplet parent; // The parent PApplet that we will render ourselves onto
	PGraphics pg;
	PFont ahTextFont;

	char[] characterArray;
	char[] randCharacterArray;
	Random r = new Random();


	// sets the column position, and column width
	TextRows(PApplet p, PGraphics pgraphics, int posX, float charBounds, float textHght, int clmns, PFont at) {
		parent = p;
		pg = pgraphics;
		ahTextFont = at;


		if (clmns > 0) {
			columns = clmns;
		} else {
			columns = 1;
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
		
		characterArray = new char[columns];
		direction = (int) parent.random(1, 10);
		fillRate = parent.random(1,3);
		fadeRate = ((r.nextFloat()*15)+5);

		randomCharFill();

	}

	void displayFilledLine() {
		randomCharFill();
		stringCharFill();
		pushChar();
		if(fillPos == columns){
			if(addFlag){
			flag++;
			addFlag = false;
			}
		}
	}
	
	void displayCharFade(){
		char letter;
		charAlphaValue += fadeRate;
		for(int i = 0; i < columns; i++){
		pg.fill(charAlphaValue);	
		letter = characterArray[i];	
		pg.text(letter, (float) ((i+1) * boundWidth-(boundWidth*.25)), charPosX * boundHeight);
		}
		if(charAlphaValue > 280){
			if(addAlphaFlag){
			alphaFlag ++;
			addAlphaFlag = false;
			}
		}
		
	}

	void randomCharFill() {
		char letter;
		int let;
		randCharacterArray = new char[columns];

		for (int i = 0; i < columns; i++) {
			let = (int) parent.random(65, 88);
			pg.fill(parent.random(255));
			letter = (char) let;
			randCharacterArray[i] = letter;
		}

	}

	void stringCharFill() {
		char letter;

		if (direction < 5) {
			for (int i = 0; i < fillPos; i++) {
				pg.fill(0);
				letter = characterArray[i];
				pg.text(letter, (float) ((i+1) * boundWidth-(boundWidth*.25)), charPosX * boundHeight); 
			}
			for (int i = columns; i > fillPos; i--) {
				pg.fill(parent.random(255));
				letter = randCharacterArray[i - 1];
				pg.text(letter,
						(float) (i * boundWidth-(boundWidth*.25)), charPosX * boundHeight);
			}

		} else if (direction >= 5) {
			for (int i = 0; i < columns - fillPos; i++) {
				pg.fill(parent.random(255));
				letter = randCharacterArray[i];
				pg.text(letter,
						(float) ((i+1) * boundWidth-(boundWidth*.25)), charPosX * boundHeight);
			}
			for (int i = columns; i > columns - fillPos; i--) {
				pg.fill(0);
				letter = characterArray[i - 1];
				pg.text(letter,
						(float) (i * boundWidth-(boundWidth*.25)), charPosX * boundHeight);

			}
		}

	}

	void pushChar() {
		if (x > fillRate) {
			if (fillPos < columns) {
				fillPos += 1;
			} else {
				fillPos = columns;
			}
			x = 0;
		}
		x++;
	}
	
	void resetValues(){
		fillPos = 0;
		x = 0;
		direction = 0;
		fillRate = 0;
		fadeRate = 0;
		charAlphaValue = 0;
		flag = 0;
		addFlag = true;
		alphaFlag = 0;
		addAlphaFlag = true;
		
		direction = (int) parent.random(1, 10);
		fillRate = parent.random(1, 5);
		fadeRate = (r.nextInt(4)+1);

		randomCharFill();
	}

}
