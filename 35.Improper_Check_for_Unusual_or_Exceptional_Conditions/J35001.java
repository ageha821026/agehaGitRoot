import java.io.*;

public class J35001 {
    public void readFromFile(String fileName) {
	try {
	    // ...
	    File myFile = new File(fileName);
	    FileReader fr = new FileReader(myFile);
	    // ...
	} catch (Exception ex) {
	    // ...
	}
    }
}
