import java.net.*;
import java.io.*;
import javax.net.ssl.SSLHandshakeException;

public class J33001 {
    public void bad(String param) {
        String urlString = param;
        try{
            URL url = new URL(urlString);
            URLConnection cmx = url.openConnection();
            cmx.connect();
        } catch (SSLHandshakeException e) {
            
            e.printStackTrace();
        }
    }
}
