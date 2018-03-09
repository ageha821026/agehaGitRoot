package testbed.unsafe;

import java.io.*;
import java.sql.*;

public class J25001 {

    public void bad(String url, String id) {
        Connection con = null;
        String passwd = System.getProperty("passwd");

        try {
            con = DriverManager.getConnection(url, id, passwd);

            // ...
        } catch (SQLException e) {
            System.err.println("...");
        } finally {
            try {
                if (con != null) con.close();
            } catch(SQLException se) {
                System.err.println("close error");
            }
        }       
    }
}
