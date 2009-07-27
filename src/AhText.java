import fullscreen.*;
import processing.core.*;



public class AhText extends PApplet {

	/**
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 */
	public static void main(String args[]) {

		PApplet.main(new String[] {"AhText" });

	}

	PFont font;
	FullScreen fs;

	int numberOfColumns;
	int numberOfRows;
	int flagFall;
	int flagChar;
	int flagAlpha;
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
		fs.enter(); 
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
		frameRate(10);		
	}

	public void draw() {
		fill(255, 255, 255, 200);
		rect(0, 0, width, height);
		noStroke();


		if (flagFall != numberOfColumns) {
			flagFall = 0;

			for (int i = 0; i < numberOfColumns; i++) {
				column[i].display();
				if (column[i].flag == 1) {
					flagFall += 1;
				}
			}
		}else {

			if (flagChar != numberOfRows){
				flagChar = 0;

				for (int i = 0; i < numberOfRows; i++) {
					row[i].displayFilledLine();
					if (row[i].flag == 1) {
						flagChar += 1;
					}
				}
			}else{

				if(flagAlpha != numberOfRows){
					flagAlpha = 0;

					for (int i = 0; i < numberOfRows; i++){					
						row[i].displayCharFade();
						if (row[i].alphaFlag == 1) {
							flagAlpha += 1;
						}
					}
				}else{

					for (int i = 0; i < numberOfRows; i++){
						row[i].resetValues();
					}
					for (int i = 0; i < numberOfColumns; i++){
						column[i].resetValues();
					}
					flagFall = 0;
					flagChar = 0;
					flagAlpha = 0;
					stringController.numStringToUse();
					stringController.fillRows();
				}
			}				
		}
	}
		 

/*	
		for (int i = 0; i < numberOfRows; i++) {
			row[i].display();
			row[i].pushChar();
		}
*/		
/*
		if(flagAlpha != numberOfRows){
			flagAlpha = 0;
			for (int i = 0; i < numberOfRows; i++){					
				row[i].displayCharFade();
				if (row[i].alphaFlag == 1) {
					flagAlpha += 1;
				}
				System.out.println(flagAlpha);
			}
		}
*/
	

}


