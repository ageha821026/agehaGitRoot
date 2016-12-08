<%@page import="jspeed.base.dao.BaseDao"%><%@ page import = "org.jdom.Document" %><%@ page import = "org.jdom.input.SAXBuilder" %><%@ page import = "org.jdom.Element" %><%@ page import="java.util.logging.*"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%><%@ page import = "java.util.Date" %><%@ page import = "java.text.*" %><%@ page import = "java.util.*" %><%@ page import = "javax.sql.*" %><%@ page import = "java.sql.*" %><%@ page import = "javax.naming.*" %><%@ page import = "javax.sql.*" %><%@ page import = "java.io.*" %><%@ page import = "java.util.Map.Entry" %><%!
//오탐 -	11883	미참조 Private 필드 정의 금지
//CompareDB.jsp 에서 호출하고 있음 
private static HashMap getSqlData(ResultSet rs, ArrayList al, String tableNm , String sqlStr) throws SQLException {
	HashMap hm = new HashMap();
	ArrayList alColData = new ArrayList();
	StringBuffer sbHmKey = new StringBuffer();
    alColData = new ArrayList();
	Long lStart = System.currentTimeMillis();
   sbHmKey = new StringBuffer();
//	StringBuffer sbColData = new StringBuffer();
	while(rs.next()) {
		//정탐 -	11864	비효율적인 StringBuffer 사용 금지
		sbHmKey.append(tableNm+'|');

	 //정탐 -	86	Loop 문 내에서 상수 할당 금지
	   for(int i=1; i<=rs.getMetaData().getColumnCount(); i++) {
		   if(i<rs.getMetaData().getColumnCount()){
		   		alColData.add(i-1,rs.getString(i)+"@.@");
		   }else{
			   alColData.add(i-1,rs.getString(i));
		   }
//		   sbColData.append(rs.getString(i));
	   }
	 //정탐 -	86	Loop 문 내에서 상수 할당 금지
	   for (int i=0; i<al.size(); i++) {
		 //정탐 -	11865	1글자 인 경우 Char 문자 사용 권장
		 //정탐 -	11864	비효율적인 StringBuffer 사용 금지
		   sbHmKey.append(rs.getString((String)al.get(i))+"|");
	   }
	   hm.put(sbHmKey.toString(),alColData);

//	   hm.put(hmKey,sbColData.toString());
//	   sbColData = new StringBuffer();
	}
// 	Long lEnd = System.currentTimeMillis();
// 	System.out.println("getSqlData:::"+(lEnd-lStart));
	
	return hm;
}
//오탐 -	11883	미참조 Private 필드 정의 금지
//CompareDB.jsp 에서 호출하고 있음 
private static String[] dataDiff(HashMap baseHm, HashMap targetHm, String replaceYn, String  OnlyKeyValueCheckYn) { 
	Iterator iter = baseHm.entrySet().iterator();
	String baseStr = null;
	String targetStr = null;
	String return_val[] = {"","","",""};
	//정탐 -	11873	StringBuffer 선언보다 더 큰 용량을 사용하는 경우 
	StringBuffer[] sb1 = new StringBuffer[4];
	sb1[0] = new StringBuffer();
	sb1[1] = new StringBuffer();
	sb1[2] = new StringBuffer();
	sb1[3] = new StringBuffer();
	
    while(iter.hasNext()) {
		   Entry entry = (Entry)iter.next();
		   if (!targetHm.containsKey(entry.getKey())) { // Key값이  없을 때
			   sb1[0].append("Base Only =>");
			   sb1[0].append(entry.getKey());
			 //오탐 -	103	[샘플]IP 주소의 하드코딩 금지
			   sb1[0].append("::");
			   sb1[0].append(entry.getValue());

		   		
		   } else if (!"Y".equals(OnlyKeyValueCheckYn)){ 									// Key값은 있으나 값이 다를때
			   if (!"Y".equals(replaceYn)) {
				 //오탐 -	11772	깊은 중첩 If 문
				 //허용이 4 이므로
				   if (targetHm.get(entry.getKey()).hashCode() !=  baseHm.get(entry.getKey()).hashCode() ) {
//  					   System.out.println("Difference =>"+entry.getKey()+"==>"+ baseHm.get(entry.getKey()).toString()+" <> "+targetHm.get(entry.getKey()).toString());
					   sb1[1].append("Difference =>");
					   sb1[1].append(entry.getKey());
					   sb1[1].append("==>");
					   sb1[1].append(baseHm.get(entry.getKey()).toString());
					   sb1[1].append(" <> ");
					   sb1[1].append(targetHm.get(entry.getKey()).toString());
				   }
				   
			   } else {
				   baseStr = baseHm.get(entry.getKey()).toString().replaceAll(" ", "").replaceAll("\r","").replaceAll("\n","").replaceAll("\t","");
				   targetStr = targetHm.get(entry.getKey()).toString().replaceAll(" ", "").replaceAll("\r","").replaceAll("\n","").replaceAll("\t","");
				   //정탐 -	11772	깊은 중첩 If 문
				   if ( !baseStr.equals(targetStr) ) { // Key값은 있으나 값이 다를때
//  					   System.out.println("Difference =>"+entry.getKey()+"==>"+ baseStr+" <> "+targetStr);
					   sb1[2].append("Difference =>");
					   sb1[2].append(entry.getKey());
					   sb1[2].append("==>");
					   sb1[2].append(" <> ");
					   sb1[2].append(targetStr);
				   }
			   }
		   }
	   }

		iter = targetHm.entrySet().iterator();
		//정탐 -	11729	Null의 할당
		targetStr = null;
		   
		   while(iter.hasNext()) {
			   Entry entry = (Entry)iter.next();
			   if (!baseHm.containsKey(entry.getKey())) { // Key값이  없을 때
				   sb1[3].append("Target Only ==>");
				   sb1[3].append(entry.getKey());
				 //오탐 -	103	[샘플]IP 주소의 하드코딩 금지
				   sb1[3].append("::");
				   sb1[3].append(entry.getValue());
				   
			   }
		   }
		   return_val[0] = sb1[0].toString();
		   return_val[1] = sb1[1].toString();
		   return_val[2] = sb1[2].toString();
		   return_val[3] = sb1[3].toString();
		
		   return return_val;
}
%>