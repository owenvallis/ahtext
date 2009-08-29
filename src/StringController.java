import java.util.ArrayList;
import java.util.Random;

public class StringController {

	private int numOfRows;
	private int numOfElements;
	private int totalNumOfChars;
	private int stringPasserStringNum; // to keep track of which String we're using from the MediaDatabase
	private int stringPasserStringNumTemp; // temp flag
	private int stringPasserArrayPos; // to keep track of which parsed string we're using from the original unparsed string
	Random r = new Random();
	MediaDatabase mediaDatabase;
	ArrayList<String[]> result = new ArrayList<String[]>();
	TextRows[] row;

	StringController(int numRws, int elmnRw, TextRows[] rw, MediaDatabase mdb) {

		if (numRws > 0) {
			numOfRows = numRws;
		} else {
			numOfRows = 1;
		}
		if (elmnRw > 0) {
			numOfElements = elmnRw;
		} else {
			numOfElements = 0;
		}

		row = rw;
		mediaDatabase = mdb;
		numStringToUse();
		fillRows();


	}

	void numStringToUse() {
		result.clear();
		totalNumOfChars = numOfRows * numOfElements;
		int x = 0;
		
		while (totalNumOfChars > 0) {
			x = (r.nextInt(mediaDatabase.mediaListOuter.size()));
			totalNumOfChars = totalNumOfChars - ((String) mediaDatabase.mediaListOuter.get(x).get(0)).length();
			result.add(((String) mediaDatabase.mediaListOuter.get(x).get(0)).split(" "));
		}

	}

	String stringPasser() {
		//modulo wrap for the result array
		stringPasserStringNum = stringPasserStringNum%result.size();
		String passer = null;
		if (stringPasserStringNumTemp == stringPasserStringNum) {
			passer = result.get(stringPasserStringNum)[stringPasserArrayPos];
		} else {
			passer = result.get(stringPasserStringNum)[stringPasserArrayPos];
			String spaces = "    ";
			passer = spaces.concat(passer);
			stringPasserStringNumTemp = stringPasserStringNum;
		}
		
		
		if (stringPasserArrayPos < (result.get(stringPasserStringNum).length-1)) {
			stringPasserArrayPos++;
		} else {
			stringPasserStringNum++;
			stringPasserArrayPos = 0;
		}

		return passer;

	}

	void fillRows() {
		int posA;
		int posC;
		int n; 
		int numElements;
		int numChar;
		int leftOffset;
		boolean stringUsed = true;
		String temp = null;
		
		for (int i = 0; i < numOfRows; i++) {
			posA = 0;
			posC = 0;
			n = 0; 
			numElements = numOfElements;
			
			while (numElements > 0) { 									//while the number of unfilled characters in a row is > 0
				if(stringUsed){											//check to see if the last string fit in the row
				temp = stringPasser();									//grab the next string
				}
				numChar = temp.length();								//find the string's length
				if ((numChar+1) <= numElements) {						// if the string's length is <= to the number of unfilled characters plus an additional space
					for(posC = 0; posC < temp.length(); posC++){
						row[i].characterArray[posA] = temp.charAt(posC);
						posA++;
					}
					row[i].characterArray[posA] = (char) 32;
					posA++; 
					numElements = numElements - (temp.length() + 1);	//subtract string length + a blank space from the number of unfilled characters
					stringUsed = true;
				} else if (numChar == numElements) {					//else if the string's length is the same as the number of unfilled characters
					for(posC = 0; posC < temp.length(); posC++){
						row[i].characterArray[posA] = temp.charAt(posC);
						posA++;
					}
					numElements = numElements - temp.length();
					stringUsed = true;
				} else {												//else if the string won't fit, then..
					leftOffset = (numElements/2);					//divide the remaining unfilled characters in half
					System.arraycopy(row[i].characterArray, 0, row[i].characterArray, leftOffset, (row[i].characterArray.length-numElements)); //shift the filled characters to the right by the left offset
					for(n = 0; n < leftOffset; n++){
					row[i].characterArray[n] = (char)47;				//fill in the left with forward slashes
					}
					numElements -= leftOffset;
					 while(numElements > 0){							//if any space is left on the right, then fill with back slashes
						row[i].characterArray[posA+leftOffset] = (char)92;
						numElements--;
						posA++;
					}
					stringUsed = false;
				}
			}
		}
		stringPasserArrayPos = 0;										//Reset Array and String pos variables
		stringPasserStringNum = 0;
	}
}
