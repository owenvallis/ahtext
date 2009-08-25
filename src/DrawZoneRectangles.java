import processing.core.PApplet;
import processing.core.PConstants;

public class DrawZoneRectangles {

	PApplet parent;
	ZoneCollection zoneCollection;

	public DrawZoneRectangles(PApplet parent, ZoneCollection zoneCollection) {
		// constructor
		this.parent = parent;
		this.zoneCollection = zoneCollection;
	}

	public void displayAllAcitiveRectangles() {
		for (int x = 0; x < zoneCollection.tzones.size(); x++) {
			parent.fill(255, 0, 0, 100);
			parent.stroke(0);
			parent.strokeWeight(10);
			parent.rectMode(PConstants.CENTER);
			parent.strokeJoin(PConstants.ROUND);
			parent.rect(zoneCollection.tzones.get(x).getX(),
					zoneCollection.tzones.get(x).getY(), 
					zoneCollection.tzones.get(x).getWidth(), 
					zoneCollection.tzones.get(x).getHeight());
		}
	}
}
