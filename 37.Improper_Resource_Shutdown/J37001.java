package testbed.unsafe;

import java.sql.*;

public class J38001 {
    public void bad() {
        Connection conn = null;
        final String url = "jdbc:mysql://127.0.0.1/example?user=root&password=1234";

        try {
            Class.forName("com.mysql.jdbc.Driver");

            
            conn = DriverManager.getConnection(url);

            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (final ClassNotFoundException e) {
            System.err.println("ClassNotFoundException occured");
        } catch (final SQLException e) {
            System.err.println("SQLException occured");
        }
        
    }
}
