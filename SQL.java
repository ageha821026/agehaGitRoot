//Modified111 by test test
import java.sql.Connection;
import java.sql.SQLException;

public class SQL {
	Public String harin = "harin";
	public void main (Connection con) throws SQLException {
		//CRUD_Q ÄÃ·³¿¡ ?·Î ÃßÃâ 
		
                String table = "sss from USER_TABLE";
		con.prepareStatement(table);
		
		//DynamicSQLPattern ÃßÃâ 
		String dynamic = "{call PR_TOUCH_USER(?)}";
		con.prepareStatement(dynamic);
		
	}
}
