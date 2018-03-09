package testbed.unsafe;

import java.io.*;

class FileAccessThread extends Thread {
    public void run() {
        BufferedReader br = null;
        try {
            File f  = new File("Test_367.txt");
            if(f.exists()) {   
                br = new BufferedReader(new FileReader(f));
            }
        } catch(IOException e) {
            System.err.println("IOException occured");
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ie) {
                System.err.println("Close error");
            }
        }
    }
}

class FileDeleteThread extends Thread {
    public void run() {
        File f  = new File("Test_367.txt");

        if(f.exists()) {  
            f.delete();
        }
    }
}

public class J31001 {
    public void bad() {
        
        FileAccessThread fileAccessThread = new FileAccessThread();
        FileDeleteThread fileDeleteThread = new FileDeleteThread();
        fileAccessThread.start();
        fileDeleteThread.start();
    }
}
