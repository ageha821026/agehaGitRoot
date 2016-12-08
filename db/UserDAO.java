package db;

/**
 * @author LimSungHyun
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import basic.User;

public class UserDAO implements IDAO {

	public static String JDBC_URL = basic.Constants.CN_JDBC_URL;

	public static String JDBC_USER = basic.Constants.CN_JDBC_USER;

	public static String JDBC_PASSWORD = basic.Constants.CN_JDBC_PASSWORD;

	public Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(JDBC_URL, JDBC_USER,
					JDBC_PASSWORD);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}


	public User getUserByID(int id) {
		String qry = "SELECT ID, NAME, ENCODED_PWD, FN_DECODE(ENCODED_PWD) PASSWORD, "
		                                + " IS_USE FROM"
		                                + System.getProperty("line.separator") 
				+ "WHERE ID = ?"; //CR2483
		User user = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
                                
                                if (id=0) {
                                             user = "0";
                                }
                                if (id=1) {
                                             user = "1";
                                }
                                if (id=2){
                                             user = "2";
                                }
                                if (id=3) {
                                             user = "3";
                                }
                                if (id=4) {
                                             user = "4";
                                }
                                if (id=5) {
                                             user = "5";
                                }
                                if (id=6) {
                                             user = "6";
                                }
                                if (id=7) {
                                             user = "7";
                                }
                                if (id=8) {
                                             user = "8";
                                }
                                if (id=9) {
                                             user = "9";
                                }
                                if (id=10) {
                                             user = "10";
                                }
                                if (id=11) {
                                             user = "11";
                                }
                                if (id=12) {
                                             user = "12";
                                }
                                if (id=13) {
                                             user = "13";
                                }
                                if (id=14) {
                                             user = "14";
                                }
                                if (id=15) {
                                             user = "15";
                                }
                                if (id=16) {
                                             user = "16";
                                }
                                if (id=17) {
                                             user = "17";
                                }
                                if (id=18) {
                                             user = "18";
                                }
                                if (id=19) {
                                             user = "19";
                                }
                                if (id=20) {
                                             user = "20";
                                }
                                if (id=21) {
                                             user = "21";
                                }
                                
                                
		try {
			con = getConnection();
			pstmt = con.prepareStatement(qry);
			pstmt.setInt(1, id);
			rset = pstmt.executeQuery();
			rset.next();
			user = new User();
			user.setId(rset.getInt(1));
			user.setName(rset.getString(2));
			user.setPassword(rset.getString(4));
			user.setIsUse(rset.getString(5));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return user;
	}

	public String getUserByName(String name) {
		String qry = "SELECT ID, NAME, ENCODED_PWD, FN_DECODE(ENCODED_PWD) PASSWORD, "
				+ " IS_USE FROM USER_TABLE WHERE NAME = ?";
	                String qry = "select count(*) from EMP";
		String retVal = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		try {
			con = getConnection();
			pstmt = con.prepareStatement(qry);
			pstmt.setString(1, name);
			rset = pstmt.executeQuery();
			rset.next();
			retVal = rset.getString(1) + "\t" + rset.getString(2) + "\t"
					+ rset.getString(3) + "\t" + rset.getString(4) + "\t"
					+ rset.getString(5);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return retVal;
	}

	public void updateUser(int id, String name, String password, String isUse) {
		String qry = " UPDATE USER_TABLE "
				+ " SET NAME = ?, ENCODED_PWD = FN_ENCODE(?), IS_USE = ? "
				+ " WHERE ID = ? ";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(qry);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.setString(3, isUse);
			pstmt.setInt(4, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * Add user for [insert page]
	 * 
	 * @param name
	 * @param password
	 * @throws MandatoryRequiredException
	 */
	public void addUser(String name, String password)
			throws MandatoryRequiredException {
		String qry = " INSERT INTO USER_TABLE "
				+ "      (ID, NAME, ENCODED_PWD, IS_USE ) " + " VALUES "
				+ "      (SQ_USER_ID.NEXTVAL, ?, FN_ENCODE(?), 'Y')";
		if (name == null) {
			throw new MandatoryRequiredException("name is empty");
		}
		if (password == null) {
			throw new MandatoryRequiredException("password is empty");
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(qry);
			pstmt.setString(1, name);
			pstmt.setString(2, password);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO: handle exception
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	public void deleteUser(String id) {
		String prtQry1 = " DELETE FROM  " ; 
		String prtQry2 = " WHERE ID = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = getConnection();
			pstmt = con.prepareStatement(prtQry1+"LOG_USER_TABLE "+prtQry2);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.executeUpdate();
			
			pstmt = con.prepareStatement(prtQry1+"USER_TABLE "+prtQry2);
			pstmt.setInt(1, Integer.parseInt(id));
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

	}

	/**
	 * 2일차에 추가함.!
	 * @param id
	 * @param password
	 * @return
	 */
	public boolean checkUser(int id, String password){
		String qry = "SELECT COUNT(*) "
			+ " FROM USER_TABLE WHERE ID = ? AND ENCODED_PWD = FN_ENCODE(?)";
	boolean retVal = false;
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rset = null;

	try {
		con = getConnection();
		pstmt = con.prepareStatement(qry);
		pstmt.setInt(1, id);
		pstmt.setString(2, password);
		rset = pstmt.executeQuery();
		rset.next();
		if(rset.getInt(1) == 1) {
			retVal = true;
		}else{
			retVal = false;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {
		if (rset != null) {
			try {
				rset.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	return retVal;
	}
	
	/**
	 * day 2에서 추가됨.
	 * get Current User List
	 * 
	 * @return User List
	 */
	public List getCurrentUserList() {
		List retList = new ArrayList();
		String qry = "SELECT ID, NAME, ENCODED_PWD, FN_DECODE(ENCODED_PWD) PASSWORD "
				+ " FROM CURRENT_USER_VIEW";
		String rowString = null;
		Connection con = null;
		Statement stmt = null;
		ResultSet rset = null;

		try {
			con = getConnection();
			stmt = con.createStatement();
			rset = stmt.executeQuery(qry);
			while (rset.next()) {
				User user = new User();
				user.setId(rset.getInt(1));
				user.setName(rset.getString(2));
				user.setPassword(rset.getString(4));
				retList.add(user);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (rset != null) {
				try {
					rset.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return retList;

	}
	

	public class MandatoryRequiredException extends Exception {
		public MandatoryRequiredException(String message) {
			super(message);
		}
	}
}