import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PGraphics;


public class MandalaHover {

	float addfill = 100;
	float pulsate;
	float pulser;
	float angle;
	float nodeRadius;
	float hoverNodeRadius;
	float hoverNodeStroke;
	float circleRadius;
	float lineStroke;
	float textYAlign;
	float numberOfNodes;
	float xA;
	float yA;
	float xB;
	float yB;
	float radiansOfNodeIntersect;
	PGraphics pg;
	PFont mandalaFont;

	float[][] mandalaSpokeLineCoordinates = new float[12][4];
	float[][] mandalaNodeConnectCoordinates = new float[12][4];
	float[][] nodePos;


	MandalaHover(PGraphics pgraphics, PFont mf, float numNodes,float ndRad, float crclRad,float[][] x) {

		pg = pgraphics;
		mandalaFont = mf;
		nodeRadius = ndRad;
		circleRadius = crclRad;
		numberOfNodes = numNodes;
		hoverNodeStroke = pg.height / 36;
		hoverNodeRadius = pg.height / 9;
		textYAlign = pg.height / (float)2.14;
		lineStroke = pg.height / 30;
		pulser = (float) .006;
		nodePos = x;
		radiansOfNodeIntersect = (float)((2.3*(Math.asin(((nodeRadius*.5))/circleRadius))));
		System.out.print(radiansOfNodeIntersect);



		for (int i = 0; i < numberOfNodes; i++) {

			//Calculate coordinates for center spokes lines
			xA = PApplet.cos(PApplet.radians((float) (i * (360.0 / numberOfNodes))))
			* hoverNodeRadius/(float)1.85;
			yA = PApplet.sin(PApplet.radians((float) (i * (360.0 / numberOfNodes))))
			* hoverNodeRadius/(float)1.85;	

			xB = PApplet.cos(PApplet.radians((float) (i * (360.0 / numberOfNodes))))
			* ((pg.height / 3)-(hoverNodeRadius/(float)1.85));
			yB = PApplet.sin(PApplet.radians((float) (i * (360.0 / numberOfNodes))))
			* ((pg.height / 3)-(hoverNodeRadius/(float)1.85));

			mandalaSpokeLineCoordinates[i][0] = xA; 
			mandalaSpokeLineCoordinates[i][1] = yA;
			mandalaSpokeLineCoordinates[i][2] = xB;
			mandalaSpokeLineCoordinates[i][3] = yB;	

			//Calculate coordinates for Node-Connect lines
			xA = PApplet.cos(PApplet.radians((float) (i * (360.0 / numberOfNodes)))+radiansOfNodeIntersect)
			* pg.height / (float)2.985;
			yA = PApplet.sin(PApplet.radians((float) (i * (360.0 / numberOfNodes)))+radiansOfNodeIntersect)
			* pg.height / (float)2.985;

			xB = PApplet.cos(PApplet.radians((float) ((i+1) * (360.0 / numberOfNodes)))-radiansOfNodeIntersect)
			* pg.height / (float)2.985;
			yB = PApplet.sin(PApplet.radians((float) ((i+1) * (360.0 / numberOfNodes)))-radiansOfNodeIntersect)
			* pg.height / (float)2.985;

			mandalaNodeConnectCoordinates[i][0] = xA; 
			mandalaNodeConnectCoordinates[i][1] = yA;
			mandalaNodeConnectCoordinates[i][2] = xB;
			mandalaNodeConnectCoordinates[i][3] = yB;




		}

	}
	 

	 boolean over(float tempxpos,float tempypos, int x, int y) {
		    float disX = (tempxpos+pg.width/2) - x;
		    float disY = (tempypos+pg.height/2) - y;
		    if (Math.sqrt(Math.pow(disX,2) + Math.pow(disY,2)) < hoverNodeRadius/1.7 ) {
		      return true;
		    } else {
		      return false;
		    }
		  }
	 
	 void drawOuterHoverNodes(int x, int y){
		xA = mandalaNodeConnectCoordinates[x][0];
		yA = mandalaNodeConnectCoordinates[x][1];
		xB = mandalaNodeConnectCoordinates[x][2];
		yB = mandalaNodeConnectCoordinates[x][3];
		pg.line(xA, yA, xB, yB);
		xA = mandalaNodeConnectCoordinates[y][0];
		yA = mandalaNodeConnectCoordinates[y][1];
		xB = mandalaNodeConnectCoordinates[y][2];
		yB = mandalaNodeConnectCoordinates[y][3];
		pg.line(xA, yA, xB, yB);
		xA = mandalaSpokeLineCoordinates[x][0];
		yA = mandalaSpokeLineCoordinates[x][1];
		xB = mandalaSpokeLineCoordinates[x][2];
		yB = mandalaSpokeLineCoordinates[x][3];
		pg.line(xA, yA, xB, yB);
	 }

	
	void drawNodeHover(int x, int y) {
		pg.textFont(mandalaFont);
		pg.textAlign(PConstants.CENTER);
		if (over(0, 0,x, y)) {

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke(addfill++, 0, 0, addfill++);
			pg.ellipse(0, 0, hoverNodeRadius, hoverNodeRadius);
			pg.strokeWeight(lineStroke);

			pg.stroke (addfill++,0,0,pulsate);
			for (int i = 0; i < numberOfNodes; i++) {
				xA = mandalaSpokeLineCoordinates[i][0];
				yA = mandalaSpokeLineCoordinates[i][1];
				xB = mandalaSpokeLineCoordinates[i][2];
				yB = mandalaSpokeLineCoordinates[i][3];
				pg.line(xA, yA, xB, yB);
			}
			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("the story of being invisible", 0, textYAlign);
			pg.fill(255);
		}

		else if (over(nodePos[0][0], nodePos[0][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke(addfill++, 199, 0, addfill++);
			pg.ellipse(nodePos[0][0], nodePos[0][1], hoverNodeRadius, hoverNodeRadius);	
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(0,11);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("heads or tails", 0, textYAlign);
			pg.fill(255);
		}
		
		else if (over(nodePos[1][0], nodePos[1][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke (88,addfill++,0,addfill++);
			pg.ellipse(nodePos[1][0], nodePos[1][1], hoverNodeRadius, hoverNodeRadius); 
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(1,0);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("cans with labels", 0, textYAlign);
			pg.fill(255);
		}
		
		else if (over(nodePos[2][0], nodePos[2][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke (1,addfill++,addfill++,addfill++);
			pg.ellipse(nodePos[2][0], nodePos[2][1], hoverNodeRadius, hoverNodeRadius);
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(2,1);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("intergalactic archeologists", 0, textYAlign);
			pg.fill(255);
		}
		
		else if (over(nodePos[3][0], nodePos[3][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke (addfill++,180,addfill++,addfill++);
			pg.ellipse(nodePos[3][0], nodePos[3][1], hoverNodeRadius, hoverNodeRadius);
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(3,2);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("the origins of conflict", 0, textYAlign);
			pg.fill(255);
		}
		
		else if (over(nodePos[4][0], nodePos[4][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke (25,11,addfill++,addfill++);
			pg.ellipse(nodePos[4][0], nodePos[4][1], hoverNodeRadius, hoverNodeRadius);
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(4,3);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("passengers - parasites", 0, textYAlign);
			pg.fill(255);
		}
		
		else if (over(nodePos[5][0], nodePos[5][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke (198,11,addfill++,addfill++);
			pg.ellipse(nodePos[5][0], nodePos[5][1], hoverNodeRadius, hoverNodeRadius);
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(5,4);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("nested awakenings", 0, textYAlign);
			pg.fill(255);
		}
		
		else if (over(nodePos[6][0], nodePos[6][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke (166,188,addfill++,addfill++);
			pg.ellipse(nodePos[6][0], nodePos[6][1], hoverNodeRadius, hoverNodeRadius);
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(6,5);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("well-used dollar bill", 0, textYAlign);
			pg.fill(255);
		}
		
		else if (over(nodePos[7][0], nodePos[7][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke (addfill++,188,222,addfill++);
			pg.ellipse(nodePos[7][0], nodePos[7][1], hoverNodeRadius, hoverNodeRadius); 
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(7,6);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("mirroring", 0, textYAlign);
			pg.fill(255);
		}
		
		else if (over(nodePos[8][0], nodePos[8][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke (addfill++,1,222,addfill++);
			pg.ellipse(nodePos[8][0], nodePos[8][1], hoverNodeRadius, hoverNodeRadius);
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(8,7);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("trouble is my middle name", 0, textYAlign);
			pg.fill(255);
		}
		
		else if (over(nodePos[9][0], nodePos[9][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke (80,80,addfill++,addfill++);
			pg.ellipse(nodePos[9][0], nodePos[9][1], hoverNodeRadius, hoverNodeRadius);
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(9,8);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("two glimpse one", 0, textYAlign);
			pg.fill(255);
		}
		
		else if (over(nodePos[10][0], nodePos[10][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke (22,128,addfill++,addfill++);
			pg.ellipse(nodePos[10][0], nodePos[10][1], hoverNodeRadius, hoverNodeRadius);
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(10,9);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("the group", 0, textYAlign);
			pg.fill(255);
		}
		
		else if (over(nodePos[11][0], nodePos[11][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			pg.strokeWeight(hoverNodeStroke);
			pg.stroke (addfill++,50,50,addfill++);
			pg.ellipse(nodePos[11][0], nodePos[11][1], hoverNodeRadius, hoverNodeRadius);
			pg.strokeWeight(lineStroke);
			
			drawOuterHoverNodes(11,10);

			pg.fill(0, 0, 0, (pulsate*2));
			pg.text("measurment - tallying divinity", 0, textYAlign);
			pg.fill(255);
		}
		
		

		else {
			addfill = 100;
			angle = 0;
		}
	}

}
