import processing.core.PConstants;

public class DrawZoneRectangles {

	AhTextContext ahTextContext;
	ZoneCollection zoneCollection;
	StateMandala stateMandala;

	public DrawZoneRectangles(AhTextContext ahTextContext, ZoneCollection zoneCollection, StateMandala stateMandala) {
		// constructor
		this.ahTextContext = ahTextContext;
		this.zoneCollection = zoneCollection;
		this.stateMandala = stateMandala;
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
		}
	}
}
