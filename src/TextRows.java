import processing.core.PApplet;
import java.util.Random;

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
	int flag;
	int alphaFlag;
	PApplet parent; // The parent PApplet that we will render ourselves onto

	char[] characterArray;
	char[] randCharacterArray;
	Random r = new Random();

	TextDatabase txdb = new TextDatabase();

	// sets the column position, and column width
	TextRows(PApplet p, int posX, float charBounds, float textHght, int clmns) {
		parent = p;
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
			flag = 1;
		}
	}
	
	void displayCharFade(){
		char letter;
		charAlphaValue += fadeRate;
		for(int i = 0; i < columns; i++){
		parent.fill(charAlphaValue);	
		letter = characterArray[i];	
		parent.text(letter, (float) ((i+1) * boundWidth-(boundWidth*.25)), charPosX * boundHeight);
		}
		System.out.println(charAlphaValue);
		System.out.println(fadeRate);
		System.out.println(" ");
		if(charAlphaValue > 280){
			alphaFlag = 1;
		}
		
	}

	void randomCharFill() {
		char letter;
		int let;
		randCharacterArray = new char[columns];

		for (int i = 0; i < columns; i++) {
			let = (int) parent.random(65, 88);
			parent.fill(parent.random(255));
			letter = (char) let;
			randCharacterArray[i] = letter;
		}

	}

	void stringCharFill() {
		char letter;

		if (direction < 5) {
			for (int i = 0; i < fillPos; i++) {
				parent.fill(0);
				letter = characterArray[i];
				parent.text(letter, (float) ((i+1) * boundWidth-(boundWidth*.25)), charPosX * boundHeight); 
			}
			for (int i = columns; i > fillPos; i--) {
				parent.fill(parent.random(255));
				letter = randCharacterArray[i - 1];
				parent.text(letter,
						(float) (i * boundWidth-(boundWidth*.25)), charPosX * boundHeight);
			}

		} else if (direction >= 5) {
			for (int i = 0; i < columns - fillPos; i++) {
				parent.fill(parent.random(255));
				letter = randCharacterArray[i];
				parent.text(letter,
						(float) ((i+1) * boundWidth-(boundWidth*.25)), charPosX * boundHeight);
			}
			for (int i = columns; i > columns - fillPos; i--) {
				parent.fill(0);
				letter = characterArray[i - 1];
				parent.text(letter,
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
		alphaFlag = 0;
		
		direction = (int) parent.random(1, 10);
		fillRate = parent.random(1, 5);
		fadeRate = (r.nextInt(4)+1);

		randomCharFill();
	}

}
