package testbed.unsafe;

import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class J12001 extends HttpServlet {
	
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {
        response.setContentType("text/html");

        
        String author = request.getParameter("authorName");
        Cookie cookie = new Cookie("replidedAuthor", author);
        cookie.setMaxAge(1000);
        cookie.setSecure(true);

        
        response.addCookie(cookie);

        RequestDispatcher frd = request.getRequestDispatcher("cookieTest.jsp");
        frd.forward(request, response);
    }
}
