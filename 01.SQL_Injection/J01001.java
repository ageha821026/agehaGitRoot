package testbed.unsafe;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class J01001 {
    public void f() {
        FileInputStream in = null;
        FileOutputStream out = null;
        DataOutputStream dos = null;
        ResultSet rs = null;
        Connection con = null;
        PreparedStatement stmt = null;
		
        try {
            Properties props = new Properties();
            String fileName = "MakeDB.properties";
            String outFileName = "out.txt";
            in = new FileInputStream(fileName);
            out = new FileOutputStream(outFileName);
            dos = new DataOutputStream(out);
            props.load(in);
            String drivers = props.getProperty("jdbc.drivers");
            if (drivers != null)
                System.setProperty("jdbc.drivers", drivers);
            System.out.println("Connecting ");
					
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jdbc.password");
            con = DriverManager.getConnection(url, username,
                                              password);

            System.out.println("Connected ... ");
			
            // 
            String tableName = props.getProperty("jdbc.tableName");
            String name = props.getProperty("jdbc.name");
            String query = "SELECT * FROM " + tableName + " WHERE Name =" + name;

            // 
            // 
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();
			
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            String printStr = "";
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    if (i > 1)
                        printStr += ", ";
                    printStr += rs.getString(i);
                }
                printStr += "\n";
            }
            dos.writeBytes(printStr);
        } catch (SQLException sqle) {
            System.err.println("SQL Exception Occurred!");
        } catch (IOException ex) {
            System.err.println("I/O Exception Occurred!");
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (in != null) {
                    in.close();
                }
                if (dos != null) {
                    dos.close();
                }
                if (out != null) {
                    out.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqle) {
                System.err.println("SQL Exception Occurred!");
            } catch (IOException ex) {
                System.err.println("I/O Exception Occurred!");
            }
        }
    }
}
