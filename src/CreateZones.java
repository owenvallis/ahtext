import java.util.Observable;
import java.util.Observer;
import oscP5.*;
import processing.core.PApplet;
import processing.core.PConstants;
import netP5.*;
import tuioZones.*;

public class CreateZones implements Observer{
	
	Observable observable;
	PApplet parent;
	TuioHandler tuioHandler;

	
	public CreateZones(Observable observable, PApplet parent){
		// constructor
		this.observable = observable;
		tuioHandler = (TuioHandler) observable;
		observable.addObserver(this);
		this.parent = parent;
	}
	
	public void displayAllAcitiveRectangles(){
		//System.out.println(tuioHandler.tzone.length);
		for(int x = 1; x < tuioHandler.tzone.length; x++){
		parent.noFill();
		parent.stroke(0);
		parent.strokeWeight(10);
		parent.rectMode(PConstants.CENTER);
		parent.strokeJoin(PConstants.ROUND);
		parent.rect(tuioHandler.tzone[x].x, tuioHandler.tzone[x].y, tuioHandler.tzone[x].w, tuioHandler.tzone[x].h);
		}
	}

	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	
	// also pass in text and audio form database  only as needed. Use story string to filter search into folders/story
	
	// add stroke, no fill, and round corners to rect same size as Zone
	// *********how do we know which special words are links to which other stories/text?

}
