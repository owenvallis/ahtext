import processing.core.PApplet;
import processing.core.PGraphics;
import fullscreen.FullScreen;

public class AhText extends PApplet {

	/**
	 * Fields for AhText
	 */
	private static final long serialVersionUID = 1L;
	FullScreen fs;
	PGraphics pg;

	TuioHandler tuioHandler;
	TextViewController textViewController;
	MandalaViewController mandalaViewController;
	
	final static int notouchTime = 30 * 1000;               // how many ms to wait before restarting the falling text

	
	/**
	 * @param args
	 */
	public static void main(String args[]) {		
		PApplet.main(new String[] { "AhText" });	
	}

	
	
	
	@Override
	public void setup() {

		size(screen.width, screen.height, JAVA2D);
		pg = createGraphics(screen.width, screen.height, P2D); // off screen render buffer
		
		// add fullscreen support for osx to hide top menu bar
		fs = new FullScreen(this);
		fs.setShortcutsEnabled(false);
		//fs.enter();
		
		// create main program controllers
		tuioHandler = new TuioHandler(this);
		textViewController = new TextViewController(this, pg);
		mandalaViewController = new MandalaViewController(tuioHandler, this, textViewController, pg);


		tuioHandler.setLastTouchTime(-(notouchTime+1)); //Hack by making the number negative to make the "millis() - tuioHandler.getLastTouchTime()" come out to a positive number the first time
		background(255);
		smooth();
		loop();
	}

	@Override
	public void draw() {

		if(millis() - tuioHandler.getLastTouchTime() > notouchTime) {
			textViewController.displayText();  
			mandalaViewController.resetMandala();  		//TODO needs to only happen once
			mandalaViewController.grow = (float)0.0;	//TODO add into resetMandala
			} else{
			textViewController.resetFallText();			//TODO needs to only happen once
			if(mandalaViewController.grow < 1.0){
				mandalaViewController.grow += 0.009;
			}
			mandalaViewController.displayMandala();
		}
	}
	
	
	/**
	 * Overriding the default mousePressed method in processing to notify listeners
	 */
	@Override
	public void mousePressed(){
		tuioHandler.setCursorX(mouseX);
		tuioHandler.setCursorY(mouseY);
		tuioHandler.mouseEventsChanged();		
	}
}
