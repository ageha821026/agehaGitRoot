import java.io.*;

public class J17001 {
    public void f732() throws IOException {
	String cmd = "umask 0";
	File file  = new File("/home/report/report.txt");
	// ...
	Runtime.getRuntime().exec(cmd);
	// ...
    }
}
