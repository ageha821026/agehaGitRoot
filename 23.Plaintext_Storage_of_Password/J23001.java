package testbed.unsafe;

import java.sql.*;
import java.util.Properties;
import java.io.*;

public class J23001 {
    public void f(String url, String name) throws IOException {
        Connection con = null;
        try {
            Properties props = new Properties();
            FileInputStream in = new FileInputStream("External.properties");
            byte[] pass = new byte[8];

            
            in.read(pass);

            
            con = DriverManager.getConnection(url, name, new String(pass));

            con.close();
        } catch (SQLException e) {
            System.err.println("SQLException Occured ");
        } finally {
            try {
                if (con != null)
                    con.close();
            } catch (SQLException e) {
                System.err.println("SQLException Occured ");
            }
        }
    }
}
