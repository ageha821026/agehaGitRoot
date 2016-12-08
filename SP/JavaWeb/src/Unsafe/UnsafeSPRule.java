package Unsafe;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URLClassLoader;
import java.net.URL;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Logger;
import javax.naming.*;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathException;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xquery.*;
import java.security.MessageDigest;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;




public class UnsafeSPRule extends HttpServlet {	
	// 검출 - 31000489	[SP] 제거되지 않고 남은 디버거 코드  -- main이 있으면 안 됨 
	public static void main(String args[]) throws SQLException{
		UnsafeSPRule u = new UnsafeSPRule();
		DAO dao = new DAO();
		
		dao.getName(args[0]);
		u.getBoolean(true);
		
        String data = null;
		getHashCodeUsingMD5(data);
        useMathrandom();

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws IOException, ServletException {
        String param = request.getParameter("name");
        String param2 = "sky"+param;
        
        PrintWriter out = response.getWriter();
        if ( param != null ){
        // 검출 - 31000080	[SP] 서버전송 사이트 교차접속 스크립트 공격 취약성 -- 동적 페이지 생성 시 parameter에서 < > &를 치환하지 않음 
        	out.print("Hello " + param2 + " !");
        }
        out.print("<html><body>");
        // 미검출 - 31000352	[SP] 사이트 간 요청 위조 -- sky 이게 java 룰이야?? 
        out.print("<form action='/index.jsp' method='get'");
        String fname = request.getParameter("FileName");
        out.print("<input type='text' name='file' value='fname'>");
        String script = "a "+ fname;
        // 검출 - 31000078	[SP] 운영체제 명령어 삽입 -- run time 시 exec 메소드의 parameter에 대한 검사 수행 
        Runtime.getRuntime().exec(script);
        // 검출 - 31000113	[SP] HTTP 응답 분할 -- param2에 CR, LF 제거 하지 않음 
        response.addHeader("author",param2);
        out.print("</form>");
        out.print("</body></html>");
    }
	
	public void ldap() throws IOException {
		ServerSocket socket = null;

		try{
        	Properties pro = new Properties();
        	Hashtable env = new Hashtable();
        	SearchControls searchCtls = new SearchControls();
        	
        	env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        	env.put(Context.PROVIDER_URL, "ldap://127.0.0.1:386");
        	// 미탐 - 31000285	[SP] 부적절한 인가 : anonymous 로 접근 
        	if (env == null) env.put(Context.SECURITY_AUTHENTICATION, "none");
        	if (env != null) env.put(Context.SECURITY_AUTHENTICATION, "simple");
        	env.put(Context.SECURITY_PRINCIPAL, "cn=user, dc=maxcrc,dc=com");
        	// 미탐 - 31000259	[SP] 하드코드된 패스워드 -- sky LDAP 인증을 위해 비밀번호를 qa로 하드코딩 
        	String passwd = "qa";
        	if (passwd == null || "".equals(passwd)) return;
        	if (!passwd.matches("") && passwd.indexOf("@!#") > 4 && passwd.length() > 8) {
        		env.put(Context.SECURITY_CREDENTIALS, passwd);
        	}

        	javax.naming.directory.DirContext ctx = new InitialDirContext(pro);
            String url = pro.getProperty("id");
            String fname = pro.getProperty("name");
            // 검출 - 31000023	[SP] 상대 디렉토리 경로 조작 -- 외부 입력값 사용 시  / \ 를 치환하지 않음, 외부 입력값에 string을 추가하면 상대  
            File rFile = new File("/qa/APPG/"+fname);
            // 검출 - 31000036	[SP] 절대 디렉토리 경로 조작 -- 외부 입력값 사용 시  / \ 를 치환하지 않음, 외부 입력값을 그대로 사용하면 절대 
            File aFile = new File(fname);
            rFile.delete();
            aFile.delete();
            // 검출 - 31000090	[SP] LDAP 삽입 -- search 문자열에서  = + < > # ; \ 를 치환하지 않음  
            NamingEnumeration result = ctx.search("url", url, searchCtls);    
            
            int port = Integer.parseInt(pro.getProperty("port"));
            // 검출 - 31000099	[SP] 자원 삽입 -- 외부 입력값 사용 시 리스트에서 선택하지 않음 
            socket = new ServerSocket(port);
            pro.put(Context.OBJECT_FACTORIES, result);
        } catch(NamingException e){
        	Logger.getLogger(e.toString());
        }
        catch(IOException e){
        	Logger.getLogger(e.toString());
        }
        finally {
        	if (socket != null) socket.close();
        }
	}
	
	public void getBoolean(Boolean result1) {
		PrintWriter out = new PrintWriter(System.out, true);
		if (result1 == true) out.println(result1);
	}
//	미탐 - 45110674 [SP] 제대로 제어되지 않은 재귀 - 조건없이 재귀함수 호출했음 
	public int recursive(int n) {
		return (recursive(n-1));
	  }
	
//	미탐 - 45110652 - [SP] XQuery 삽입  
	public String useXQuery(String name) throws NamingException, IOException {
		Properties props = new Properties();
		String fileName = "MakeDB.properties";
		FileInputStream in = new FileInputStream(fileName);
		props.load(in);

		//외부로 부터 입력을 받음
		String name2 = props.getProperty("name");
		
		String es = "doc('users.xml')/userlist/user[uname='"+name+"']";
		String es2 = "doc('users.xml')/userlist/user[uname='"+name2+"']";
		
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, "ldap://localhost:389/o=rootDir");
		javax.naming.directory.DirContext ctx = new InitialDirContext(env);

		javax.xml.xquery.XQConnection conn = null;
		XQPreparedExpression expr, expr2 = null;
		XQResultSequence result = null;

		javax.xml.xquery.XQDataSource xqds = (javax.xml.xquery.XQDataSource)ctx.lookup("xqj/personnel");
		try{
			conn = xqds.getConnection();

			// name을 외부에서 받아 처리없이 사용
			expr = conn.prepareExpression(es);
			expr2 = conn.prepareExpression(es2);
			
			result = expr.executeQuery();
			while(result.next()){
				String str = result.getAtomicValue();
				if(str.indexOf('>')<0){
					System.out.println(str);
			}
		}
		}catch(XQException xqe){
			Logger.getLogger(xqe.toString());
		}finally{
			in.close();
		}
		return name;
	}
//	미탐 - 45110643	[SP] XPath 삽입
	public void useXPath(String name, String password) throws IOException{
		Properties props = new Properties();
		String fileName = "External.properties";
		FileInputStream in = new FileInputStream(fileName);
		props.load(in);

		// 외부로 부터 입력을 받음
		String prname = props.getProperty("name");
		String passwd = props.getProperty("password");

		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		XPathExpression expr, expr2 = null;
		Object result, result2 = null;
		NodeList nodes = null;
		InputSource doc = null;
		try{
			 expr = xpath.compile("//users/user[login/text()='" + name+ "' and password/text() = '" + password + "']/home_dir/text()");
			 //name과 password를 아무런 처리 없이 받아  처리 (인증과정없이 로그인이 가능해짐)

			 expr2 = xpath.compile("//users/user[login/text()='" + prname+ "' and password/text() = '" + passwd + "']/home_dir/text()");
			 //name과 password를 아무런 처리 없이 받아  처리 (인증과정없이 로그인이 가능해짐)
			 
			 result = expr.evaluate(doc , XPathConstants.NODESET);
			 result2 = expr2.evaluate(doc , XPathConstants.NODESET);
			 
			 nodes = (NodeList) result;
		}catch(XPathExpressionException e){
			Logger.getLogger(e.toString());
		}finally{
			in.close();
		}
	}
// 45110247	[SP] 보안 결정시 DNS lookup에 의존
	public void useDNSLookup(HttpServletRequest req, HttpServletResponse res) throws SecurityException, IOException{
		boolean trusted = false;
		String ip = req.getRemoteAddr();
		
		InetAddress addr = InetAddress.getByName(ip);
		//DNS 이름에 의존하고 있음 
		if(addr.getCanonicalHostName().endsWith("trustme.com")){
			trusted = true;
		}
	}
//45110732	[SP]  중요한 자원에 대한 잘못된 권한설정
	public void useFaltAuth() throws IOException{
		String cmd = "umask 0";
		Runtime.getRuntime().exec(cmd);
		File file  = new File("/home/report/report.txt");
	    }
//45110759	[SP] 솔트 없이 일방향 해쉬 함수 사용 - 196라인에서 호출을 하기 때문에 검출됨. 
//검출되지 않기 위해서는 203라인위에서 algorithm.update(b); 이 필요함. 
    private static void getHashCodeUsingMD5(String inData) {
    	MessageDigest algorithm = null;
        try {
        	if(inData !=null && algorithm !=null){
			algorithm.digest(inData.getBytes("UTF-8"));
        	}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(e.toString());
		}
    }

//    45110330	[SP] 적절하지 않은 난수값의 사용
    private static double useMathrandom(){
    	return Math.random();
    }
//45110306	[SP] 적절한 인증 없는 중요기능 허용-- 사이트 커스터마이징 사항 (계좌이체, 개인정보) 열람또는 변경시 재인증없이 실행할 때 위반
    //계좌이체, 개인정보 클래스 및 메소드가 정의되어야 할 수 있음 . 

//45110494	[SP] 무결성 검사없는 코드 다운로드
    public void notCheckedDownload() throws ClassNotFoundException {
    	URL[] classURLs;
    	URLClassLoader loader;
		try {
			classURLs = new URL[] {
			    new URL("file:subdir/")
			};
	    	 loader = new URLClassLoader(classURLs);
	    	Class loadedClass = Class.forName("LoadMe", true, loader);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			Logger.getLogger(e.toString());
		}
        }
    
}


class DAO {
	public static Connection conn = null;
	public static Statement stmt = null;
	public static PreparedStatement pstmt = null;
	public static Integer rset = null;
	public static String passwd = "tiger";
	
	public Boolean getName(String mgr_nm) throws SQLException{
		Boolean result = false;
		try {
			// 미탐 - 31000259	[SP] 하드코드된 패스워드 - public static field로 tiger 하드코딩  
			conn = DriverManager.getConnection("jdbc:oracle:thin:@172.16.2.50:1521", "scott", passwd);
			String selectQuery1, selectQuery2;
			String updateQuery;
			selectQuery1 = "SELECT DEPTNO FROM DEPT;";
			selectQuery2 = "SELECT GRADE FROM SALGRADE;";
			pstmt = conn.prepareStatement(selectQuery1);
			// 미탐 - 11939	DB 자원 재사용 금지 - close하지 않고 prepareStatement 사용 
			pstmt = conn.prepareStatement(selectQuery2);
			if (!mgr_nm.equals(null)) {
				updateQuery = "UPDATE EMP SET MGR_NM = '" + mgr_nm + "' WHERE EMPNO = 0;";
		        stmt = conn.createStatement();
		        //검출 - 31000089	[SP] SQL 삽입  -- 쿼리의 parameter는 prepareStatement에 set 함수를 사용해야 함 
		        rset = stmt.executeUpdate(updateQuery);
		        result = (rset!=0);
			}
			// 미탐 - 11940	DB Transaction 사용시 무결성 보장 - autoCommit을 사용하지 않는 경우 rollback 메소드 사용해야 함 
			conn.setAutoCommit(false); 
			conn.close();  
	   } catch (SQLException e) {
		   Logger.getLogger(e.toString());
	   } finally {
		   conn.close();
		   pstmt.close();
	   }
	   return result;
	}

}