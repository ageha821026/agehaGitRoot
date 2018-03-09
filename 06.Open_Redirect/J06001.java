package testbed.unsafe;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class J06001 extends HttpServlet {                                              
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
        throws ServletException, IOException {
        String query = request.getQueryString();
        if (query.contains("url")) {
            String url = request.getParameter("url");
             
            if( url != null  ) {
                url = url.replaceAll("\r", "").replaceAll("\n", "");
                response.sendRedirect(url); 
            }
        }
    }
}        
