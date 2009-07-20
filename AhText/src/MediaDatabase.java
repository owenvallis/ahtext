import java.io.*;
import java.util.*;
import java.util.regex.*;

final public class MediaDatabase {

	// <------------------Setup Class IVars------------------>//
	//
	ArrayList<ArrayList<Object>> mediaListOuter = new ArrayList<ArrayList<Object>>(); // 2D ArrayList to hold data "structs"

	FilenameFilter filterTxt = new TXTFilter(); //data filter types
	FilenameFilter filterWav = new WAVFilter();
	
	File directory = new File("data/media"); //set data folder path

	// <------------------Setup Class Methods------------------>//
	//
	
	MediaDatabase(){
		readTextFiles();		
	}
	
	void readTextFiles() {
		int x = 0;

		File[] files = directory.listFiles(filterTxt); //create an array of filtered files from the data folder

		//Puts the strings into the first position of sequential ArrayList Elements
		for (File f : files) {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(f));

				String txt;
				Pattern p;
				Matcher m;
				String inputText = "";

				while ((txt = reader.readLine()) != null) {
					inputText = inputText + txt + "\n";
				}

				p = Pattern.compile("\n");
				m = p.matcher(inputText);
				String str = m.replaceAll(" ");

				mediaListOuter.add(new ArrayList<Object>());
				mediaListOuter.get(x).add(str);
				x++;

				reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	void readAudioFiles() {

		File[] files = directory.listFiles(filterWav);

		for (File f : files) {

			mediaListOuter.add(new ArrayList<Object>());
			mediaListOuter.get(1).add(f);

		}

	}

}

class TXTFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		return (name.endsWith(".txt"));
	}

}

class WAVFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		return (name.endsWith(".wav"));
	}
}