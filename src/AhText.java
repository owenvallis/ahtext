import processing.core.*;
import fullscreen.*;



public class AhText extends PApplet {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String args[]) {

		PApplet.main(new String[] { "--present", "AhText" });

	}

	PFont font;
	FullScreen fs;

	int numberOfColumns;
	int numberOfRows;
	int flagIncrement;
	int boundHeight;
	int boundWidth;

	TextColumns[] column;
	TextRows[] row;
	MediaDatabase mediaDatabase = new MediaDatabase();
	StringController stringController;
	

	public void setup() {
		
		fs = new FullScreen(this);
		fs.setShortcutsEnabled(false);
		size(screen.width, screen.height, P2D);
		background(255);
		smooth();
		noStroke();
		
		

		// load and set the font
		font = loadFont("Helvetica-24.vlw");
		boundHeight = 24;
		textFont(font);
		textAlign(CENTER);
		textMode(SCREEN);

		// find text bounding box width for the # of columns
		for (int x = 65; x < 89; x++) {

			if (textWidth((char) x) > boundWidth) {
				boundWidth = (int) textWidth((char) x);
			}
		}
		
		
		numberOfColumns = width / boundWidth;
		numberOfRows = height / boundHeight;

		column = new TextColumns[numberOfColumns];
		for (int i = 0; i < numberOfColumns; i++)
			column[i] = new TextColumns(this, i, boundWidth, boundHeight, numberOfRows);

		row = new TextRows[numberOfRows];
		for (int i = 0; i < numberOfRows; i++)
			row[i] = new TextRows(this, i, boundWidth, boundHeight, numberOfColumns);	
		stringController = new StringController(numberOfRows, numberOfColumns, row, mediaDatabase);
		

		loop();
		frameRate(30);
		fs.enter(); 
	}

	public void draw() {
		fill(255, 255, 255, 200);
		rect(0, 0, width, height);
		noStroke();
/*
		if (flagIncrement != numberOfColumns) {
			flagIncrement = 0;

			for (int i = 0; i < numberOfColumns; i++) {
				column[i].display();
				if (column[i].flag == 1) {
					flagIncrement += 1;
				}
			}
		}

		else {
			for (int i = 0; i < numberOfRows; i++) {
				row[i].display();
				row[i].pushChar();
			}
		}

*/
		for (int i = 0; i < numberOfRows; i++) {
			row[i].display();
			row[i].pushChar();
		}
		

	}

}
