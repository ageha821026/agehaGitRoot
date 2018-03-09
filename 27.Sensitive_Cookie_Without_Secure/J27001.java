package testbed.unsafe;

import javax.servlet.*;
import javax.servlet.http.*;

public class J27001 extends HttpServlet {
    private final String ACCOUNT_ID = "account";

    public void setupCookies(ServletRequest r, HttpServletResponse response)  {
        String acctID = r.getParameter("accountID");
	
        
        Cookie c = new Cookie(ACCOUNT_ID, acctID);
		
        response.addCookie(c);
    }
}
