package testbed.unsafe;

import javax.servlet.*;
import javax.servlet.http.*;

public class J26001 extends HttpServlet {
    public void makeCookie(ServletRequest request) {
        String r = request.getParameter("maxAge");

        if (r.matches("[0-9]+")) {
            String sessionID = request.getParameter("sesionID");

            if (sessionID.matches("[A-Z=0-9a-z]+")) {
                Cookie c = new Cookie("sessionID", sessionID);

                
                c.setMaxAge(Integer.parseInt(r));
            }
        }
    }
}
