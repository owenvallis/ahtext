import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PGraphics;
import processing.core.PImage;

public class TextViewController {

	PFont ahTextFont;
	PApplet parent;
	PGraphics pg;
	PImage image;

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

	TextViewController(PApplet p, PGraphics pgraphics) {

		pg = pgraphics;
		parent = p;

		// load and set the font
		ahTextFont = parent.loadFont("Helvetica-24.vlw");

		boundHeight = 24;
		parent.textFont(ahTextFont);
		parent.textAlign(PConstants.CENTER);
		parent.textMode(PConstants.SCREEN);

		// find text bounding box width for the # of columns
		for (int x = 65; x < 89; x++) {

			if (parent.textWidth((char) x) > boundWidth) {
				boundWidth = (int) parent.textWidth((char) x);
			}
		}

		numberOfColumns = parent.width / boundWidth;
		numberOfRows = parent.height / boundHeight;

		column = new TextColumns[numberOfColumns];
		for (int i = 0; i < numberOfColumns; i++)
			column[i] = new TextColumns(parent, pg, i, boundWidth, boundHeight,
					numberOfRows, ahTextFont);

		row = new TextRows[numberOfRows];
		for (int i = 0; i < numberOfRows; i++)
			row[i] = new TextRows(parent, pg, i, boundWidth, boundHeight,
					numberOfColumns, ahTextFont);


		stringController = new StringController(numberOfRows, numberOfColumns,
				row, mediaDatabase);
	}

	void displayText() {
		pg.beginDraw();
		pg.fill(255, 255, 255, 200);
		pg.rect(0, 0, pg.width, pg.height);
		pg.noStroke();

		if (flagFall != numberOfColumns) {
			flagFall = 0;

			for (int i = 0; i < numberOfColumns; i++) {
				column[i].display();
				if (column[i].flag == 1) {
					flagFall += 1;
				}
			}
		} else {

			if (flagChar != numberOfRows) {
				flagChar = 0;

				for (int i = 0; i < numberOfRows; i++) {
					row[i].displayFilledLine();
					if (row[i].flag == 1) {
						flagChar += 1;
					}
				}
			} else {

				if (flagAlpha != numberOfRows) {
					flagAlpha = 0;

					for (int i = 0; i < numberOfRows; i++) {
						row[i].displayCharFade();
						if (row[i].alphaFlag == 1) {
							flagAlpha += 1;
						}
					}
				} else {

					for (int i = 0; i < numberOfRows; i++) {
						row[i].resetValues();
					}
					for (int i = 0; i < numberOfColumns; i++) {
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
		pg.endDraw();
		parent.image(pg, 0, 0);
	}
}
