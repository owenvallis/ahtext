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

		PApplet.main(new String[] { "AhText" });

	}

	FullScreen fs;
	PGraphics pg;

	TextViewController textViewController;
	MandalaViewController mandalaViewController;


	public void setup() {

		size(screen.width, screen.height, JAVA2D);
		pg = createGraphics(screen.width, screen.height, P2D);

		fs = new FullScreen(this);
		fs.setShortcutsEnabled(false);
		fs.enter();

		background(255);
		smooth();
		noStroke();

		textViewController = new TextViewController(this, pg);
		mandalaViewController = new MandalaViewController(this);

		loop();
		frameRate(30);
		
	}
	

	

	public void draw() {

		switch(key) {

		case 't':  // text mode
			textViewController.displayText();  
			break;
		case 'm': // mandala mode
			mandalaViewController.displayMandala();
			break;
		}

		
	}
}
