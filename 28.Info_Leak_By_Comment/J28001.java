package testbed.unsafe;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class J28001 {
    /*
     * Password for administrator is "tiger."
     */
     
    public void DBConnect(String usrId, String password) {
        String url = "DBServer";
		
		
        Connection con = null;

        try {
            con = DriverManager.getConnection(url, usrId, password);
            con.close();
        } catch (SQLException e) {
			
            throw e;
        }

        if( con != null ){
            con.close();
        }
    }
}
