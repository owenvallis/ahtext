import java.util.ArrayList;
import java.util.Random;

public class StringController {

	private int numOfRows;
	private int numOfElements;
	int stringCtrlFlag;
	private int totalNumOfChars;
	private int stringPasserStringNum; // to keep track of which String were
	// using from the MediaDatabase
	private int stringPasserStringNumTemp; // temp flag
	private int stringPasserArrayPos; // to keep track of which parsed string
	// were using from the original unparsed
	// string
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

		stringCtrlFlag = 1;

	}

	void setStringCtrlFlagToZero() {
		stringCtrlFlag = 0;
	}

	void numStringToUse() {

		totalNumOfChars = numOfRows * numOfElements;
		int x = 0;

		while (totalNumOfChars > 0) {
			x = (x%mediaDatabase.mediaListOuter.size());
			//System.out.println(x);
			totalNumOfChars = totalNumOfChars - ((String) mediaDatabase.mediaListOuter.get(x).get(0)).length();
			result.add(((String) mediaDatabase.mediaListOuter.get(x).get(0)).split(" "));
			//System.out.println(result.size());
			x++;
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
			String spaces = "      ";
			passer = spaces.concat(passer);
			stringPasserStringNumTemp = stringPasserStringNum;
		}
		
		System.out.println(stringPasserStringNum);
		if (stringPasserArrayPos < (result.get(stringPasserStringNum).length-1)) {
			stringPasserArrayPos++;
		} else {
			stringPasserStringNum++;
			stringPasserArrayPos = 0;
		}
		
		//passer = passer.toUpperCase(); //to turn all text to CAPS
		return passer;

	}

	void fillRows() {

		for (int i = 0; i < numOfRows; i++) {
			int posA = 0;
			int posC = 0;
			int n = 0; 
			int numElements = numOfElements;
			
			while (numElements > 0) {
				String temp = stringPasser();
				int numChar = (temp.length() + 1);
				if (numChar <= numElements) {
					for(posC = 0; posC < temp.length(); posC++){
						row[i].characterArray[posA] = temp.charAt(posC);
						posA++;
					}
					row[i].characterArray[posA] = (char) 32;
					posA++; 
					numElements = numElements - (temp.length() + 1);
				} else if ((numChar-1) == numElements) {
					for(posC = 0; posC < temp.length(); posC++){
						row[i].characterArray[posA] = temp.charAt(posC);
						posA++;
					}
					numElements = numElements - temp.length();
				} else {
					posA -= 1;
					row[i].characterArray[posA] = (char)32;
					numElements--;
					posA++;
					int leftOffset = (int)(numElements/2);
					System.arraycopy(row[i].characterArray, 0, row[i].characterArray, leftOffset+1, (row[i].characterArray.length-(numElements+1)));
					for(n = 0; n <= (leftOffset); n++){
					row[i].characterArray[n] = (char)47;
					}
					numElements -= leftOffset;
					 while(numElements >= 0){
						row[i].characterArray[posA+leftOffset] = (char)92;
						numElements--;
						posA++;
					}
				}
			}
		}
	}
}
