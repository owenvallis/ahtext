import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final public class MediaDatabase {

	// <------------------Setup Class IVars------------------>//
	//
	ArrayList<String> mediaListOuter = new ArrayList<String>(); // 2D ArrayList to hold data "structs"
	FilenameFilter filterTxt = new TXTFilter(); //data filter types
	File directory = new File("data/media"); //set data folder path
	File[] files = directory.listFiles(filterTxt); //create an array of filtered files from the data folder

	// <------------------Setup Class Methods------------------>//
	////
	
	MediaDatabase(){
		readTextFiles();
		System.out.println(directory.listFiles(filterTxt));
	}
	
	void readTextFiles() {
		int x = 0;

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

				mediaListOuter.add(str);
				x++;

				reader.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

}

class TXTFilter implements FilenameFilter {
	public boolean accept(File dir, String name) {
		return (name.endsWith(".txt"));
	}

}
