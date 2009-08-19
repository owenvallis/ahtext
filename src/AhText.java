import processing.core.PApplet;
import processing.core.PGraphics;
import processing.video.Movie;
import fullscreen.FullScreen;

public class AhText extends PApplet {

	/**
	 * Fields for AhText
	 */
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	FullScreen fs;
	PGraphics pg;

	Movie myMovie;
	TextViewController textViewController;
	TuioHandler tuioHandler;
	MandalaViewController mandalaViewController;
	final static int notouchTime = 30 * 1000;               // how many ms to wait before restarting the falling text

	
	/**
	 * @param args
	 */
	public static void main(String args[]) {

		PApplet.main(new String[] { "AhText" });

	}

	
	
	public void setup() {

		size(screen.width, screen.height, JAVA2D);
		pg = createGraphics(screen.width, screen.height, P2D);

		fs = new FullScreen(this);
		fs.setShortcutsEnabled(false);
		fs.enter();
		myMovie = new Movie(this, "xplode.mov");
		myMovie.noLoop();
		textViewController = new TextViewController(this, pg);
		tuioHandler = new TuioHandler(this);
		mandalaViewController = new MandalaViewController(tuioHandler, this, textViewController, myMovie);


		tuioHandler.setLastTouchTime(-(notouchTime+1)); //Hack by making the number negative to make the "millis() - tuioHandler.getLastTouchTime()" come out to a positive number the first time
		background(255);
		loop();

	}




	public void draw() {

		if(millis() - tuioHandler.getLastTouchTime() > notouchTime) {
			myMovie.jump((float)0.0);
			textViewController.displayText();  
			mandalaViewController.resetMandala();
			mandalaViewController.grow = (float)0.0;
		} else{
			textViewController.resetFallText();
			if(mandalaViewController.grow < 1.0){
				mandalaViewController.grow += 0.02;
			}
			mandalaViewController.displayMandala();
		}
	}
	
	
	/**
	 * Overriding the default mousePressed method in processing to notify listeners
	 */
	public void mousePressed(){
		tuioHandler.setCursorX(mouseX);
		tuioHandler.setCursorY(mouseY);
		tuioHandler.mouseEventsChanged();		
	}
}
