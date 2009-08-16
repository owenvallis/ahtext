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
		//fs.enter();



		textViewController = new TextViewController(this, pg);
		tuioHandler = new TuioHandler(this);
		mandalaViewController = new MandalaViewController(tuioHandler, this);

		background(255);
		loop();

	}




	public void draw() {

		if(millis() - tuioHandler.getLastTouchTime() > notouchTime) {
			textViewController.displayText();  
		} else{
			textViewController.resetFallText();
			mandalaViewController.displayMandala();
		}


	}
}
