import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PFont;


public class MandalaHover {

	float previousXtemp;
	float previousYtemp;
	float addfill = 100;
	float pulsate;
	float pulser;
	float angle;
	float nodeRadius;
	float hoverNodeRadius;
	float hoverNodeStroke;
	float circleRadius;
	float lineStroke;
	float textXAlign;
	float textYAlign;
	float numberOfNodes;
	float xA;
	float yA;
	float xB;
	float yB;
	float radiansOfNodeIntersect;
	float tempX;
	float tempY;
	float xCenter;
	float yCenter;
	float screenHeight;

	PApplet parent; // The parent PApplet that we will render ourselves onto
	PFont mandalaFont;

	float[][] mandalaSpokeLineCoordinates = new float[12][4];
	float[][] mandalaNodeConnectCoordinates = new float[12][4];
	float[][] nodePos;


	MandalaHover(PApplet p, PFont mf, float numNodes,float ndRad, float crclRad,float[][] x, float xCent, float yCent, float scrnHght) {

		parent = p;
		screenHeight = scrnHght;
		mandalaFont = mf;
		nodeRadius = ndRad;
		circleRadius = crclRad;
		numberOfNodes = numNodes;
		hoverNodeStroke = screenHeight / 36;
		hoverNodeRadius = screenHeight / 9;
		textXAlign = parent.width/2;
		textYAlign = parent.height / 20;
		lineStroke = screenHeight / 30;
		pulser = (float) .006;
		xCenter = xCent;
		yCenter = yCent;
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
			* ((screenHeight / 3)-(hoverNodeRadius/(float)1.85));
			yB = PApplet.sin(PApplet.radians((float) (i * (360.0 / numberOfNodes))))
			* ((screenHeight / 3)-(hoverNodeRadius/(float)1.85));

			mandalaSpokeLineCoordinates[i][0] = xA; 
			mandalaSpokeLineCoordinates[i][1] = yA;
			mandalaSpokeLineCoordinates[i][2] = xB;
			mandalaSpokeLineCoordinates[i][3] = yB;	

			//Calculate coordinates for Node-Connect lines
			xA = PApplet.cos(PApplet.radians((float) (i * (360.0 / numberOfNodes)))+radiansOfNodeIntersect)
			* screenHeight / (float)2.985;
			yA = PApplet.sin(PApplet.radians((float) (i * (360.0 / numberOfNodes)))+radiansOfNodeIntersect)
			* screenHeight / (float)2.985;

			xB = PApplet.cos(PApplet.radians((float) ((i+1) * (360.0 / numberOfNodes)))-radiansOfNodeIntersect)
			* screenHeight / (float)2.985;
			yB = PApplet.sin(PApplet.radians((float) ((i+1) * (360.0 / numberOfNodes)))-radiansOfNodeIntersect)
			* screenHeight / (float)2.985;

			mandalaNodeConnectCoordinates[i][0] = xA; 
			mandalaNodeConnectCoordinates[i][1] = yA;
			mandalaNodeConnectCoordinates[i][2] = xB;
			mandalaNodeConnectCoordinates[i][3] = yB;




		}

	}


	boolean over(float tempxpos,float tempypos, int x, int y) {

		float disX = (tempxpos+xCenter) - x;
		float disY = (tempypos+yCenter) - y;
		if (Math.sqrt(Math.pow(disX,2) + Math.pow(disY,2)) < hoverNodeRadius/1.7 ) {
			if(tempX != tempxpos && tempY != tempypos){
				addfill = 100;
				angle = 0;
			}
			tempX = tempxpos;
			tempY = tempypos;
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
		parent.line(xA, yA, xB, yB);
		xA = mandalaNodeConnectCoordinates[y][0];
		yA = mandalaNodeConnectCoordinates[y][1];
		xB = mandalaNodeConnectCoordinates[y][2];
		yB = mandalaNodeConnectCoordinates[y][3];
		parent.line(xA, yA, xB, yB);
		xA = mandalaSpokeLineCoordinates[x][0];
		yA = mandalaSpokeLineCoordinates[x][1];
		xB = mandalaSpokeLineCoordinates[x][2];
		yB = mandalaSpokeLineCoordinates[x][3];
		parent.line(xA, yA, xB, yB);
	}


	void drawNodeHover(int x, int y) {
		parent.textFont(mandalaFont);
		parent.textAlign(PConstants.CENTER);

		if (over(0, 0,x, y)) {

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke(addfill+=5, 0, 0, addfill+=5);
			parent.ellipse(0, 0, hoverNodeRadius, hoverNodeRadius);
			parent.strokeWeight(lineStroke);

			parent.stroke (addfill+=5,0,0,pulsate);
			for (int i = 0; i < numberOfNodes; i++) {
				xA = mandalaSpokeLineCoordinates[i][0];
				yA = mandalaSpokeLineCoordinates[i][1];
				xB = mandalaSpokeLineCoordinates[i][2];
				yB = mandalaSpokeLineCoordinates[i][3];
				parent.line(xA, yA, xB, yB);
			}
			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("the story of being invisible", parent.width/2, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[0][0], nodePos[0][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke (addfill+=5,180,addfill+=5,addfill+=5);
			parent.ellipse(nodePos[0][0], nodePos[0][1], hoverNodeRadius, hoverNodeRadius);	
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(0,11);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("the origins of conflict", textXAlign, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[1][0], nodePos[1][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke (1,addfill+=5,addfill+=5,addfill+=5);
			parent.ellipse(nodePos[1][0], nodePos[1][1], hoverNodeRadius, hoverNodeRadius); 
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(1,0);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("intergalactic archeologists", textXAlign, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[2][0], nodePos[2][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke (88,addfill+=5,0,addfill+=5);
			parent.ellipse(nodePos[2][0], nodePos[2][1], hoverNodeRadius, hoverNodeRadius);
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(2,1);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("cans with labels", textXAlign, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[3][0], nodePos[3][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke(addfill+=5, 199, 0, addfill+=5);
			parent.ellipse(nodePos[3][0], nodePos[3][1], hoverNodeRadius, hoverNodeRadius);
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(3,2);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("heads or tails", textXAlign, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[4][0], nodePos[4][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke (addfill+=5,50,50,addfill+=5);
			parent.ellipse(nodePos[4][0], nodePos[4][1], hoverNodeRadius, hoverNodeRadius);
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(4,3);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("measurment - tallying divinity", textXAlign, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[5][0], nodePos[5][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke (22,128,addfill+=5,addfill+=5);
			parent.ellipse(nodePos[5][0], nodePos[5][1], hoverNodeRadius, hoverNodeRadius);
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(5,4);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("the group", textXAlign, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[6][0], nodePos[6][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke (80,80,addfill+=5,addfill+=5);
			parent.ellipse(nodePos[6][0], nodePos[6][1], hoverNodeRadius, hoverNodeRadius);
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(6,5);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("two glimpse one", textXAlign, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[7][0], nodePos[7][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke (addfill+=5,1,222,addfill+=5);
			parent.ellipse(nodePos[7][0], nodePos[7][1], hoverNodeRadius, hoverNodeRadius); 
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(7,6);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("trouble is my middle name", textXAlign, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[8][0], nodePos[8][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke (addfill+=5,188,222,addfill+=5);
			parent.ellipse(nodePos[8][0], nodePos[8][1], hoverNodeRadius, hoverNodeRadius);
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(8,7);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("mirroring", textXAlign, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[9][0], nodePos[9][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke (166,188,addfill+=5,addfill+=5);
			parent.ellipse(nodePos[9][0], nodePos[9][1], hoverNodeRadius, hoverNodeRadius);
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(9,8);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("well-used dollar bill", textXAlign, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[10][0], nodePos[10][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke (198,11,addfill+=5,addfill+=5);
			parent.ellipse(nodePos[10][0], nodePos[10][1], hoverNodeRadius, hoverNodeRadius);
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(10,9);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("nested awakenings", textXAlign, textYAlign);
			parent.fill(255);
		}

		else if (over(nodePos[11][0], nodePos[11][1],x, y))
		{

			angle = angle + pulser;
			pulsate = PApplet.abs(70 * PApplet.sin(angle));
			parent.strokeWeight(hoverNodeStroke);
			parent.stroke (25,11,addfill+=5,addfill+=5);
			parent.ellipse(nodePos[11][0], nodePos[11][1], hoverNodeRadius, hoverNodeRadius);
			parent.strokeWeight(lineStroke);

			drawOuterHoverNodes(11,10);

			parent.fill(0, 0, 0, (pulsate*7));
			parent.text("passengers - parasites", textXAlign, textYAlign);
			parent.fill(255);
		}
		
		else{
			addfill = 100;
			angle = 0;
		}
	}
}
