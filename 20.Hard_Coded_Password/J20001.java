package testbed.unsafe;

import java.sql.*;

public class J20001 {
    private Connection conn;

    public void bad(String url, String id) {
        try {
            // 
            // 
            conn = DriverManager.getConnection(url, id, "tiger");
        } catch (SQLException e) {
            System.err.println("...");
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("conn.close() error");
            }
        }
    }
}
