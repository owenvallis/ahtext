import processing.core.PApplet;

public class TextRows {

	private int fillPos;
	private int charPosX;
	private int columns;
	private int x = 0;
	private int direction;
	private float fillRate;
	private float boundHeight;
	private float boundWidth;
	PApplet parent; // The parent PApplet that we will render ourselves onto

	char[] characterArray;
	char[] randCharacterArray;

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
		fillRate = parent.random(1, 5);

		randomCharFill();

	}

	void display() {
		randomCharFill();
		stringCharFill();

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
				parent.text(letter,
						(float) ((i+1) * boundWidth-(boundWidth*.25)), charPosX * boundHeight); 
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

}
