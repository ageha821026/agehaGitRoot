import java.net.*;
import java.io.*;

public class J30001 {
    public void bad() {
        URL[] classURLs = null;
        URLClassLoader loader = null;

        try { 
            classURLs = new URL[] {
                new URL("file:subdir/")
            };

            loader = new URLClassLoader(classURLs);

            
            Class loadedClass = Class.forName("LoadMe", true, loader);
            // ...
        } catch (MalformedURLException me) {
            System.err.println("MalformedURLException");
        } catch (ClassNotFoundException ce) {
            System.err.println("ClassNotFoundException");
        } finally {
            try {
                if (loader != null) loader.close();
            } catch (IOException ie) {
                System.err.println("close error");
            }
        }
    }
}
