
package Unsafe;

import java.net.Socket;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import org.exist.util.serializer.IndentingXMLWriter;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.security.PrivilegedAction;




/**
 * @author ageha
 *
 */

public class UnsafeCpRule extends Throwable{
	

	
	public UnsafeCpRule(){
		String iden_num = u103(5);
	}
	//검출 - 11824	클래스와 같은 이름을 사용하는 메소드
	public void UnsafeCpRule(){
		
	}
    public static String url = "jdbc:mysql://172.16.2.74:3306/testlink";

    //검출 -11883	미참조 Private 필드 정의 금지 
    //검출 -11725	Volatile 사용 금지
    //검출 - 11823	메소드 명명 규칙
    private volatile static String Property = "?characterEncoding=EUC_KR";

    //검출 - 11782	Final 필드는 반드시 Static으로 선언해야 함 
    public final String user = "testlink";
    public static String password = "testlink";
    public static String q;

	private StringBuffer queryBuff = new StringBuffer("");
    //검출 - 11874	Char를 사용한 StringBuffer의 인스턴스 생성
	private StringBuffer queryBuff2 = new StringBuffer('a');
    private static final SimpleDateFormat sdf = new SimpleDateFormat();
    private String date1 = "2013-04-02";
    
	public boolean compareClass(Class a, Class aa) throws ParseException{
		java.util.Date timedates = sdf.parse(date1);
		//검출 - 11769	동기화되지 않은 Static 날짜 포매터(Formatter)
		String datestr = sdf.format(timedates);
		
		//검출 - 11683	ThreadGroup 사용 금지
		ThreadGroup tg = Thread.currentThread().getThreadGroup();
		boolean b = true;
		Double db = null;
		//검출 - 14	getName()을 사용한 Class 비교 금지
		if(a.getName().equals(aa)){
			b = true;
		}else{
			b = false;
		}
		//검출 - 11703	빈 (Empty) Synchronize Block 사용 금지 
		synchronized (UnsafeCpRule.class){
			
		}
		//검출 - 11746	부적절한 비교
		if(Double.NaN !=db){
			b = true;
		}
		return b;
	}
	//검출 - 48	가변 파라미터 사용 금지
    public String U104(int ... flag){
        //검출 - 주민등록번호 하드코딩 금지
    	String idenNumber = "8402182813239"; //위반
    	String idenNumber2 = "840218-2813239"; //위반
    	
    	//검출 - 11867	String 값으로 String 인스턴스 생성 금지 
    	String returnValue = new String("init");
    	Short st = Short.valueOf(idenNumber);

    	//검출 - 11811	Short 인스턴스 생성
    	Short st1 = new Short(st);

    	//검출 - 11814	Byte 초기화
    	//검출 - 11877	불필요한 String.valueOf 메소드 호출 (String을 합할 때 검출 ) 
    	Byte b = new Byte(String.valueOf(flag[0])+idenNumber2);
    	
    	//검출 - 11815	Integer 초기화
    	Integer i = new Integer(idenNumber);

    	//검출 - 11684	8진수 변수 사용 금지
    	int ii = 073;
    	
    	switch (flag[0]) {
		case 1:
			returnValue = idenNumber;
		default:
			returnValue = idenNumber2;
		}
    	//검출 - 11692	For 문을 While 문으로 대체 권장 
    	for(;1==1;){
    		if(flag[0]==0){
    			break;
    		}
    	}
    	//검출 - 11868	String 객체에 대해 toString() 사용 금지
    	return returnValue.toUpperCase().toString();
    }
    
    //[샘플]IP 주소의 하드코딩 금지
	public String u103(int flag){
		 String ip = "127.0.0.1";
		 String  ipv6 ="fe80::f8e6:d014:56c3:4ebf%11";
		 
		 //검출 - 11749	Switch 문에서 Default 구문이 마지막에 존재하지 않음 
		 switch (flag) {
			default:
			System.out.println("error");
		//미검출 - 11783	Switch의 Case 블럭에서 Break문 누락 금지 	
			case 2 : 
			System.out.println(ipv6);
		}
		 //검출 - 11765	Switch 문에 너무 적은 분기 사용
		 //검출 - 11702	빈 (Empty) Switch 문 사용 금지 
		switch (flag) {
//		case 1:
//			System.out.println(ip);
//			break;
//		case 2 : 
//			System.out.println(ipv6);
//			break;
//		default:
//			System.out.println("error");
//			break;
		}
		//검출 - 11755	Switch 문의 Case 누락
		switch (flag) {
		case 1:
			mylabel: 
			break;
		default:
			break;
		}
		return ip;
	}
	//INSERT 문에서 컬럼과 입력값의 개수 일치 
	public void u115(double doubleIData){
		PreparedStatement psmt = null;
		//검출 - 11682	여러 개의 단항 연산자 사용 금지
		int rB = +-+1;
		String qry = "insert into cm_src(src_id, sname, spath) values (1,2)"; //위반 - 컬럼갯수 3개 입력값 갯수 2개
		
		DecimalFormat df = new DecimalFormat("0.0");
		double resultD2 = -9999.0d;
        String d2Str = df.format(doubleIData);
        resultD2 = Double.parseDouble(d2Str);
        
        //검출 11681	BigDecimal 생성자에서 Decimal 리터럴 사용 금지
        resultD2 = new java.math.BigDecimal(doubleIData).setScale(2, java.math.BigDecimal.ROUND_UP).doubleValue();
		Connection con  = s4418();
		
		try{
			psmt = con.prepareStatement(qry);
		    for (int k = 0;k<1; k++) {
		    	//미검출 100	Loop문 내에서 다중 데이터 저장(addBatch 사용권장)
				rB = psmt.executeUpdate(qry);
				if(rB==0){
					//미검출 - 11706	혼동된 Loop 조건 변수 조작 금지 
					k=1;
				}
		    }
		}catch(SQLException sqle){
			Logger.getLogger(sqle.toString());
		}finally{
			if(con !=null && rB>0 && psmt !=null){
				try{
					psmt.close();
					con.close();
				}catch(SQLException e){
					Logger.getLogger(e.toString());
				}
			}else{
				//검출 - 11853	NullPointerException의 Throw 금지
				 throw new NullPointerException();
			}
			
		}
	}

	public void u114(int flag){
		PreparedStatement stmt = null;
		ResultSet rs = null;

		
		//검출 - 114	INSERT 문에서 테이블 컬럼 이름 명시 권장
		//검출 - 108	Join 테이블에 대해 앨리아스 사용 권장
		//검출 - 106	연산만 수행하는 SQL 문 금지	
		String iQry = "insert into cm_src values ";
				iQry += "(SELECT C_PKG.PKG_NAME, c_pkg_menu_map.MENU_ACT_ID  FROM C_PKG , C_PKG_MENU_MAP "; 
				iQry += " WHERE c_pkg.PKG_ID = 1" ;
				iQry += " AND c_pkg.PKG_ID = c_pkg_menu_map.PKG_ID";
				iQry += " UNION";
				iQry += " SELECT C_PKG.PKG_NAME, c_pkg_menu_map.MENU_ACT_ID  FROM C_PKG , C_PKG_MENU_MAP "; 
				iQry += " WHERE c_pkg.PKG_ID = 3 ";
				iQry += " AND c_pkg.PKG_ID = (SELECT 1+100 FROM dual)) ";

		//검출 - 190	WHERE 절이 없는 DELETE 또는 UPDATE 사용 금지				
		String uQry = "update cm_src set sname = 'a'";
		String dQry = "delete from cm_src";
		String qry = "";
		
		Connection con  = s4418();
		try{
			
			//if else 의 경우 미검출 
			if(flag == 1){
				qry = iQry;
			}else if(flag ==2){
				qry = uQry;
			}else{
				qry = dQry;
			}
			stmt = con.prepareStatement(qry);
			
			if( flag >1){
				for(int i=0;i<flag;i++){
					//검출 - 100	Loop문 내에서 다중 데이터 저장(addBatch 사용권장)
					int a = stmt.executeUpdate();
					//검출 - 11706	혼동된 Loop 조건 변수 조작 금지 / 이중 for문에서만 검출 
					//검출 - 22	Loop에 빈 (Empty) 조건 사용 금지
					for(a=0;;i++){
						System.out.println("update Result : "+a);
					}
					
				}
			}else{
				rs = stmt.executeQuery();
			}
		}catch(SQLException e){
			Logger.getLogger(e.toString());
		}finally{
			if(con !=null && rs !=null){
				try{
					rs.close();
					con.close();
				}catch(SQLException e){
					Logger.getLogger(e.toString());
				}
			}
			
		}
	}



	//DB 자원 해제 검사 
	public Connection s4418(){
	    Connection con = null;	
	    try{
	    	con = DriverManager.getConnection(url, user, password); //안전 
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    return con;
	}
	//File 자원 해제 검사
	public void u4402(String fileName){
		FileInputStream fs = null;
		try{
			 fs= new FileInputStream(fileName); //위반 new 한뒤 Close를 하지 않았음 
		//검출 - 11855	Catch 문에서 Throwable 객체 사용 금지 
		}catch(Throwable th){
			Logger.getLogger(th.toString());
		}
	}
	//Socket 자원 해제 검사
	public void u4412(){
		Socket so = null;
		try{
			so = new Socket(); //위반 finally 에서 해제 하지 않았음 
		}catch(Exception e){
			
		}finally{
			
		}
	}

	//위반 ResultSet 0참조
	//검출 - 11745	메소드 Synchronized 금지 
	public synchronized void u9(String biz_nm){
		Statement stmt = null;
		ResultSet rs = null;
		//검출 - 11734	로컬 변수에 대해 Final 사용 금지
		
		final String biz_nm_chk = biz_nm.replace('%', '/');
		short system_id  = 4;
		
		
		//미검출 - 182	ALIAS 명칭 중복 사용 금지		
		String  qry = "select a.SRC_ID, a.SPATH, a.SNAME ";
				qry+="from cm_src a ";
				qry+="WHERE a.SRC_ID IN "; 
				qry+="( ";
				qry+="SELECT b.SRC_ID FROM C_BIZ a, CM_BIZ_SRC_MAP b ";
				qry+="WHERE a.BIZ_NM='SP_JAVA_WEB' ";
				qry+="AND a.BIZ_id = b.BIZ_ID ";
				qry+=")";

		//검출 -182	ALIAS 명칭 중복 사용 금지
		//검출 -186	NULL 비교 조건 사용을 피함
		//검출 -187	불필요한 DISTINCT 사용을 피함
		//검출 -11873 StringBuffer 선언보다 더 큰 용량을 사용하는 경우 		
		//검출 -11864	 비효율적인 StringBuffer 사용 금지 
		//검출 -11842	 빈 (Empty) String 문자 더하기 연산 금지
		//검출 -113	SELECT 문에서 테이블 컬럼 이름 명시 권장 (SELECT * 사용 금지)
		//검출 -119	유사한 검색 조건에 대해 'OR' 조건 나열 금지		
		//검출 - 124	WHERE 절에서 LIKE 사용 시 첫문자로 '%', '_' 사용 금지
		//검출 - 4324	OR 대신 UNION ALL 사용 권장		
		//검출 - 4328	Sub-query 에 의한 데이터 존재 여부 확인 시 IN 보다는 EXISTS 를 사용 권장	
		//검출 - 11724	Short 타입 사용 금지		
		//검출 - 107	Select 문의 컬럼 열에 단일 컬럼이 아닌 경우(연산문 등) 알리아스 명시 권장	
		//검출 - 	109	Join 컬럼에 대해 앨리아스 명시 권장
        queryBuff.append(" select rownum, allTb.* from(");    
		queryBuff.append(" SELECT distinct SRC_ID,       concat(sPATH,SNAME) "); 
		queryBuff.append(" FROM cm_src a,  ");
		queryBuff.append("      (SELECT b.SRC_ID "); 
		queryBuff.append("          FROM C_BIZ a,  ");
		queryBuff.append("               CM_BIZ_SRC_MAP b "); 
		queryBuff.append("         WHERE (a.BIZ_NM='"+biz_nm+"' or a.biz_nm like '%SP_JAVA_WEB') "+""); 
		queryBuff.append("           AND a.BIZ_id = b.BIZ_ID AND a.UPD_DT IS NULL");
		queryBuff.append("    ) B  ");
		queryBuff.append(" WHERE a.SRC_ID = B.src_id AND ");
		queryBuff.append(" A.SPATH IN ( ");
		queryBuff.append(" SELECT REMOTE_FILE_PATH FROM CM_COLLECT_INFO WHERE SYSTEM_ID = "+system_id+" ");
		queryBuff.append(" OR SYSTEM_ID = (SELECT hdw_id+2 FROM C_HDW_INFO WHERE hdw_nm = 'QARULE')))allTb ");
			  
	    
		Connection con  = s4418();
		
		int a = 0;
		
		synchronized (UnsafeCpRule.class) {
			try{
				//11940	DB Transaction 사용시 무결성 보장
				con.setAutoCommit(false);
				stmt = con.createStatement();

				//검출 - 11716	If 문에 중괄호 사용 권장  
				//검출 - 11875	불필요한 대소문자 변경
				if(!biz_nm.toUpperCase().equals("ALL"))
					
				//검출 - 83	Nested synchronized 사용 금지	
				synchronized (UnsafeCpRule.class) {
					//미검출 - 11878	StringBuffer.length() 사용
					if(queryBuff.toString().length()>10){
						rs = stmt.executeQuery(queryBuff.toString());
					}
					//검출 - 11878	StringBuffer.length() 사용
					if(!queryBuff.toString().equals("")){
						rs = stmt.executeQuery(queryBuff.toString());	
					}
					//검출 - 11705	빈 (Empty) While 문 사용 금지 
					while(a>0){
						
					}
					
				}
				//검출 - 11687	ResultSet 확인 
				rs.next();
				
				//위반 a가 0이므로 
				System.out.println(rs.getString(a));
				
				//검출 11714	For 문에 중괄호 사용 권장 
				//검출 11869	String 타입 비교시 equlas() 사용 
				for(int i=0; i<rs.getRow()+1;i++)
					//위반
					System.out.println(rs.getString(i));
				
					//위반
				System.out.println(rs.getString(0));
			}catch(Exception e){
				System.out.println("Exception!!");
			}finally{
				if(con !=null && rs !=null){
					try{
						rs.close();
						con.close();
					}catch(Exception e){
						
					}
				}
				
			}
		}
		
	}
	static String t() {
		return null;
	}
	//Map/List는 Static 필드로 선언 금지
	private static final List<String> list= new ArrayList<String>(); //위반 - List를 Static로 선언
	private static Map map;	//위반 - Map을 Static로 선언
	static Map map2; //위반 - Map을 Static로 선언
	
	//객체 비교시 == 연산자 사용 금지 
	public void u11780(Boolean a, Boolean b){
		Boolean c = true;
		Integer iia = 1;
		Integer iib = 2;
		
		String sa="";
		String sb="";
		 
		if (a==b) {//위반  Boolean 객체 비교
			if (b == c) {//위반  Boolean 객체 비교 - 파라미터와 선언된 객체 비교
				if (sa==sb) {//위반 String 객체 비교
					if(iia==iib){//위반 Integer 객체 비교
						
						}
					}
				}
			}	
		}
	
	//equals()에서 Null 비교 금지 
	public void u11781(String a){
		String sa = null;
		
		Object o = "U11781";
		if (o.equals(null)) { // 위반 
			
		}
		if(a.equals(sa)){ // 위반 sa 가 null이므로 
			if(a.equals(null)){ //위반 
			}
		}
	}
	//잘못된 Null검사 
	public void u11693(String a){
		String sa = null;
		//위반 a가 null 일경우 NullPointException 발생 , 아닐경우 두번 째 비교가  의미 없음
		//두가지가 모두 만족 할 때만 룰 위반에 걸림 (파라미터비교)
		if(a.getClass()==null && a==null){  
			
		}
		//위반 sa가 null이므로 NullPointException 발생, 아닐경우 두번째 null 비교가 의미 없음
		//선언된 객체와 비교 
		if(sa.getClass()==null && sa==null){
			
		}
	}
	
	//불완전한 Null 검사
	//검출 - 11845	싸이클로매틱 복잡도( 기본값이 11임)  
	void u11686(int un) {
		String a=null,b=null;
		// 위반- a.length()에서 NullPointException 발생
		if (a == null && a.length() > 0) { 
			System.out.println("cyclo 1");
		}else if (a != null || a.length() > 0) { // 위반- a.length()에서 NullPointException 발생
			System.out.println("cyclo 2");
		}else if (t() != null || t().length() > 0) { // 위반- t().length()에서 NullPointException 발생
			System.out.println("cyclo 3");
		}else if (q != null || t().length() > 0) {// 위반- t().length()에서 NullPointException 발생
			System.out.println("cyclo 4");
		}else if (q != null || q.length() > 0) { // 위반- q.length()에서 NullPointException 발생
			System.out.println("cyclo 5");
		}else if (q != null || q.toLowerCase().length() > 0) { // 위반- q.toLowerCase().length()에서 NullPointException 발생
			System.out.println("cyclo 6");
		}else if (a.intern() == null && a.intern().getBytes() == null) { //위반 - a.intern()에서 NullPointException 발생
			System.out.println("cyclo 7");
		}else if (a.substring(1) == null && a.substring(2) == null) { //위반 - a.subString(1)에서 NullPointException 발생
			System.out.println("cyclo 8");
		}else if (a.substring(1) == null && a.substring(1).toCharArray() == null) { //위반 -a.subString(1)에서 NullPointException 발생
			System.out.println("cyclo 9");
		}else {
			System.out.println("cyclo 10");
		}
		switch (un) {
		case 1:
			System.out.println("cyclo: 11");
			break;
		case 2:
			System.out.println("cyclo : 12");
			break;
		default:
			break;
		}
		
	}
	//검출 - 11718	Clone 메소드는 Cloneable을 Implement 하여야 함
	//검출 - 11720	Clone이 CloneNotSupportedException을 Throw 함
    public Object clone()
    {
    	UnsafeCpRule ucr = null;
       try{
         ucr = (UnsafeCpRule)super.clone();
       }catch(CloneNotSupportedException e){
    		Logger.getLogger(e.toString());
       }finally{
        return ucr;
       }
    }
    public void exeThread(String flag){
    	Thread tr = new Thread();
    	if(flag != null){
	    	if("run".equals(flag)){
	    		tr.run();
	    	}else if("stop".equals(flag)){
	    		//검출 - 1	Thread.stop(), Thread.suspend() 사용 금지
	    		tr.stop();
	    	}else{
	    		//검출 - 1	Thread.stop(), Thread.suspend() 사용 금지
	    		tr.suspend();
	    	}
    	}
    }
    //검출 - 11707	equals() 정의 객체에 hashCode() 없음 
    public boolean equals(Object o){
    	
    	UnsafeCpRule ucr = new UnsafeCpRule();
    	
    	if(ucr.equals(o)){
    		return true;
    	}else{
    		//검출 - 11791	Object.finallize 사용  금지 
    		ucr.finalize();
    		return false;
    	}
    	
    }
//    protected void finalize() 함수는 gc할 때 자동적으로 호출이 이러남. 
//    원래 이 메서드는 아무것도 구현되어 있지 않음
//    검출 - 11790	Finalize는 Proected 이어야 함
    public void finalize(){
    	
    }
    public void toArrayTest(){
    	ArrayList<String> list = new ArrayList<String>();
    	list.add("t1");
    	list.add("t2");
    	//미검출 - 11688	toArray() 메소드의 ClassCaseException
    	String[] arr  = (String[])list.toArray();
    	
    }

    public void invalidCallsInMethod() throws SecurityException, NoSuchMethodException {
        // Possible call to forbidden getDeclaredConstructors
        Class[] arrayOfClass = new Class[1];
        this.getClass().getDeclaredConstructors();
        this.getClass().getDeclaredConstructor(arrayOfClass);
        Class clazz = this.getClass();
        clazz.getDeclaredConstructor(arrayOfClass);
        clazz.getDeclaredConstructors();

        //검출 - 11722	접근성 변경 금지
        clazz.getMethod("", arrayOfClass).setAccessible(false);
        AccessibleObject.setAccessible(null, false);
        Method.setAccessible(null, false);
        Method[] methodsArray = clazz.getMethods();
        int nbMethod;
        for ( nbMethod = 0; nbMethod < methodsArray.length; nbMethod++ ) {
        //검출 - 11722	접근성 변경 금지
        	methodsArray[nbMethod].setAccessible(false);
        }

        PrivilegedAction priv = (PrivilegedAction) new Object(); priv.run();
       }

}

class UnSafeInterClass{
	String ip = "127.0.0.1";
	String ipv6 ="fe80::f8e6:d014:56c3:4ebf%11";
	static Map map;
	
	private UnSafeInterClass() {
		
	}
	
	//소켓을 닫지 않아 위반 
	public void u4412(){
		Socket so = null;
		try{
			so = new Socket();
		}catch(Exception e){
			
		}finally{
			
		}
		//검출 - 11704	빈 (Empty) Try 블럭 사용 금지 
		try{
            //String log_file = "D:\\workspace\\smoac_"+crs_name+"_str.sql";
            //ps = new PrintStream(new FileOutputStream(log_file, false));
            //ps.print(queryBuff);
		}catch(Exception e){
			//검출 - 11705	빈 (Empty) While 문 사용 금지
			//검출 - 11717	While 문에 중괄호 사용 권장
			while(so.isConnected())
			Logger.getLogger(e.toString());
			//검출 - 11711	불필요한 Return 문 사용 금지 
			return;
		}
	}

}
