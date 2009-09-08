import processing.core.PConstants;
import processing.core.PImage;

public class DrawZoneRectangles {

	AhTextContext ahTextContext;
	ZoneCollection zoneCollection;
	StateMandala stateMandala;
	PImage storyText;
	int storypngxSize;
	int storypngySize;

	public DrawZoneRectangles(AhTextContext ahTextContext, ZoneCollection zoneCollection, StateMandala stateMandala) {
		// constructor
		this.ahTextContext = ahTextContext;
		this.zoneCollection = zoneCollection;
		this.stateMandala = stateMandala;
		
		storypngxSize = (int)(ahTextContext.width * 0.21);
		storypngySize = (int)(ahTextContext.height * 0.22);
	}

	public void displayAllAcitiveRectangles() {
		for (int x = 0; x < zoneCollection.tzones.size(); x++) {
			ahTextContext.fill( stateMandala.mandalaNodeList[stateMandala.currentNode].getRed(), 
								stateMandala.mandalaNodeList[stateMandala.currentNode].getGreen(), 
								stateMandala.mandalaNodeList[stateMandala.currentNode].getBlue(), 
								stateMandala.mandalaNodeList[stateMandala.currentNode].getAlpha());
			ahTextContext.stroke(0);
			ahTextContext.strokeWeight(10);
			ahTextContext.rectMode(PConstants.CENTER);
			ahTextContext.strokeJoin(PConstants.ROUND);
			ahTextContext.rect(zoneCollection.tzones.get(x).getX(),
					zoneCollection.tzones.get(x).getY(), 
					zoneCollection.tzones.get(x).getWidth(), 
					zoneCollection.tzones.get(x).getHeight());
			//ahTextContext.imageMode(PConstants.CENTER);
			String folder = "media/" + Integer.toString(stateMandala.currentNode) + "/" + Integer.toString(x%2) + ".png";
			
			storyText = ahTextContext.loadImage(folder);
			ahTextContext.image(storyText, (zoneCollection.tzones.get(x).getX() - (int)(storypngxSize * 0.5)), (zoneCollection.tzones.get(x).getY() - (int)(storypngySize * 0.5)),storypngxSize,storypngySize);
		}
	}
}
