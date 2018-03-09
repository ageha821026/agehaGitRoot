package testbed.unsafe;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class U488 extends HttpServlet {
    private String name;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        
        name = request.getParameter("name");
        if (-1 != name.indexOf("/"))
            System.err.printf("bad input");
        else
            System.err.printf(name + ", thanks for visiting!");
    }

}
