import oscP5.OscMessage;
import fullscreen.FullScreen;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;


public class AhTextContext extends PApplet implements TuioObserver {

	/**
	 * Fields for AhText
	 */
	private static final long serialVersionUID = 1L;
	FullScreen fs;
	PGraphics pg;
	PFont ahTextFont;

	TuioHandler tuioHandler;
	OSCHandler oscHandler;
	OscMessage myMessage;

	StateInterface textColumns;
	StateInterface textRows;
	StateInterface textRowsFade;
	StateInterface mandala;

	StateInterface stateInterface;

	MediaDatabase mediaDatabase;
	StringController stringController;

	int numberOfColumns;
	int numberOfRows;
	int boundHeight;
	int boundWidth;

	TextColumns[] column;

	TextRows[] row;

	static int notouchTime;             // how many ms to wait before restarting the falling text
	int lastTouchTime;             		// last time in ms from which a touch/click event happened
	boolean switchBackToStart;





	/**
	 * @param args
	 */
	public static void main(String args[]) {		
		
		PApplet.main(new String[] {"--present",  "AhTextContext" });	
	}

	@Override
	public void setup() {

		size(1280, 800, JAVA2D);
		// off screen rendering buffer
		pg = createGraphics(screen.width, screen.height, P2D);

		// add Full Screen support for OSX to hide top menu bar
		fs = new FullScreen(this);
		fs.setShortcutsEnabled(false);
		//fs.enter();  //enable/disable Full Screen

		// load and set the font
		ahTextFont = loadFont("Helvetica-24.vlw");

		boundHeight = 24;
		textFont(ahTextFont);
		textAlign(CENTER);
		textMode(SCREEN);

		// find text bounding box width for the # of columns
		for (int x = 65; x < 89; x++) {
			if (textWidth((char) x) > boundWidth) {
				boundWidth = (int) textWidth((char) x);
			}
		}

		// find number of Columns and Rows
		numberOfColumns = width / boundWidth;
		numberOfRows = height / boundHeight;

		// create main program controllers
		tuioHandler = new TuioHandler(this);
		tuioHandler.registerObserver(this);
		oscHandler = new OSCHandler(this);

		textColumns = new StateTextColumn(this);
		textRows = new StateTextRow(this);
		textRowsFade = new StateTextRowFade(this);
		mandala = new StateMandala(this);
		stateInterface = textColumns;
		
		// OSC msg for initial state
		myMessage = new OscMessage("/mode");
		myMessage.add(0); // Fall Mode 
		oscHandler.sendOSCMessage(myMessage);

		// create array of text columns
		column = new TextColumns[numberOfColumns];
		for (int i = 0; i < numberOfColumns; i++){
			column[i] = new TextColumns(this, pg, i, boundWidth, boundHeight, numberOfRows, ahTextFont);
		}

		// create array of text rows
		row = new TextRows[numberOfRows];
		for (int i = 0; i < numberOfRows; i++){
			row[i] = new TextRows(this, pg, i, boundWidth, boundHeight, numberOfColumns, ahTextFont);
		}

		// fill our rows with text from the AhText database
		mediaDatabase = new MediaDatabase();
		stringController = new StringController(numberOfRows, numberOfColumns, row, mediaDatabase);

		notouchTime = 30 * 1000; 
		switchBackToStart = true;

		background(255);
		smooth();
		loop();
	}

	// /////////////////////////////////////////////////////////////
	// SET METHODS//////////////////////////////////////////////////
	// /////////////////////////////////////////////////////////////

	@Override
	public void draw() {
		if(millis() - lastTouchTime > notouchTime){
			if(stateInterface.equals(mandala)){
				if(switchBackToStart){
					resetMandalaState();
					myMessage = new OscMessage("/mode");
					myMessage.add(0); // Fall Mode
					oscHandler.sendOSCMessage(myMessage);
					switchBackToStart = false;
					stateInterface = textColumns;
				}
			} else {
				 resetTouchTimer();
			}
		}
		stateInterface.displayGraphics();		

	}

	public void tuioCursorAdded(long sessionID, int cursorX, int cursorY) {
		stateInterface.touchEventAddCursor(sessionID, cursorX, cursorY);
		resetTouchTimer();
	}

	public void tuioCursorUpdate(long sessionID, int cursorX, int cursorY) {
		stateInterface.touchEventUpdateCursor(sessionID, cursorX, cursorY);
		resetTouchTimer();
	}

	public void tuioCursorRemove(long sessionID) {
		stateInterface.touchEventRemoveCursor(sessionID);
		resetTouchTimer();
	}

	public void setState(StateInterface stateInterface){
		this.stateInterface = stateInterface;
	}

	public StateInterface getTextRowsState(){
		return textRows;
	}

	public StateInterface getTextRowsFadeState(){
		return textRowsFade;
	}

	public StateInterface getTextColumnsState(){
		return textColumns;
	}

	public StateInterface getMandalaState(){
		return mandala;
	}

	/**
	 * holds the time at which the last touch event occurred
	 */
	private void resetTouchTimer() {
		lastTouchTime = millis();
		switchBackToStart = true;
	}

	private void resetMandalaState() {
		stateInterface.reset();
	}
	
	public void mousePressed(){
		tuioHandler.setCursorX(mouseX);
		tuioHandler.setCursorY(mouseY);
		tuioHandler.mouseEventsClicked();
	}
	
	public void mouseDragged(){
		tuioHandler.setCursorX(mouseX);
		tuioHandler.setCursorY(mouseY);
		tuioHandler.mouseEventsDragged();
	}
	
	public void mouseReleased(){
		tuioHandler.mouseEventsReleased();
	}


}
