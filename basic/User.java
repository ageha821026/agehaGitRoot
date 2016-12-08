/*
 * Created on 2005. 2. 18.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package basic;

/**
 * @author LimSungHyun
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.sql.Connection;
import java.sql.SQLException;

public class basic extends JApplet{  //CR2053, CR2476
   @AnnotationTest
	basic() {
        JApplet(super.JApplet);
		super.JApplet = "A";
	String result = (String) super.insert(getNameSpce()+".insertFile",file);	//CR2003
	}
	
    void case1() throws SQLException {
    	Connection conn = null;
        conn.prepareStatement("a");
    }
    
}

public class User extends AbUser{
	private int id;
	private String name;
	private String password;
	private String isUse;
	
	//Use Generic Type Class 
	public <QA extends Comparable<? super QQ>>QQ max(Collection<QQ> coll) {
			return null;
		}
		
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return Returns the isUse.
	 */
	public String getIsUse() {
		return isUse;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return Returns the password.
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param isUse The isUse to set.
	 */
	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @param password The password to set.
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	public String toString(){
		return id + "\t" + name + "\t"
		+ password + "\t";
	}
}
