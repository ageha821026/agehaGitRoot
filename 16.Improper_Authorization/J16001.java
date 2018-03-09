import java.util.logging.Logger;
import javax.servlet.http.*;
import java.io.*;

public class J16001 extends HttpServlet {
    public void f(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String msgId    = request.getParameter("msgId");

        if (username == null || password == null || !Auth.isAuthenticatedUser(username, password)) {
            return;
        }
        if (msgId == null) {
            throw new Exception("Invalid msgId");            
        }

        /** After authentication, doing something here... */
    }


}

class Auth {
    public static boolean isAuthenticatedUser(String user, String pass) {
        /** authentication routine */
        return user == pass;
    }
}
