import javax.servlet.http.*;

public class J14001 {
    public void f807(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies(); 

        for (int i = 0; i < cookes.length; i++) {
            Cookie c = cookies[i];
            if (c.getName().equals("role")) {
                userRole = c.getValue(); 
            }
        }
    }
}
