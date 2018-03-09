package testbed.unsafe;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.*;

public class J43001 extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {
        boolean trusted = false;
        String ip = req.getRemoteAddr();

        
        InetAddress addr = InetAddress.getByName(ip);
		
        
        if (addr.getCanonicalHostName().endsWith("trustme.com") && !trusted) {
            trusted = true;
        }
		
        if (trusted) {
            // ...
        } else {
            // ...
        }
    }
}
