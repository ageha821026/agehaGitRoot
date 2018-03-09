package testbed.unsafe;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class J39001 extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) 
        throws ServletException, IOException {
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) 
        throws ServletException, IOException {
    }

    
    public static void main(String args[]) {
        System.err.printf("Print debug code");
    }
}
