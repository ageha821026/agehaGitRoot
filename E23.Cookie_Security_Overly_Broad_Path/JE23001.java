package testbed.unsafe;

import javax.servlet.http.Cookie;
import javax.servlet.http.*;

public class JE23001 extends HttpServlet {
    protected void f(HttpServletRequest request) {
        String path = request.getParameter("path");
        Cookie cookie = new Cookie("user", "cindy");
        cookie.setDomain("brainysoftware.com");
        cookie.setPath("http://" + path);
        cookie.setComment("This is an authorized user.");
    }
}
