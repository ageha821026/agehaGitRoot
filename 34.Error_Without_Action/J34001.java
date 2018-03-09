package testbed.unsafe;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

public class J34001 {
    private Connection conn;

    public Connection bad(String url, String id, String password) {
        try {
            String CONNECT_STRING = url + ":" + id + ":" + password;
            InitialContext ctx = new InitialContext(); 
            DataSource datasource = (DataSource) ctx.lookup(CONNECT_STRING); 
            conn = datasource.getConnection();
        } catch (SQLException e) {
	    
        } catch (NamingException e) {
            
        }

        return conn;
    }
}
