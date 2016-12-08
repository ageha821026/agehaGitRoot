<%--
* 작 성 자 : 서경은
* 작 성 일 : 2012/05/23
* 설 명 : DB비교 페이지   
--%>
<%-- <%@page import="com.itplus.cm.common.util.BaseProperties"%> --%>
<%@page import="org.jdom.JDOMException"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.lang.reflect.Method"%>
<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "jspeed.base.util.StringHelper"%>
<%@ page import = "java.sql.*" %>
<%@ page import = "org.jdom.Document" %>
<%@ page import = "org.jdom.input.SAXBuilder" %>
<%@ page import = "org.jdom.Element" %>
<%@ page import = "java.util.*" %>
<%@ page import="java.util.logging.*"%>
<%@ page import = "java.io.*" %>

<%@ include file="/changeflow/header.jsp" %>
<%-- <%@ include file="/common/_header.jsp" %> --%>
<%@ include file="/site/qa/DmlDiff.jsp" %>

<%
	String product = request.getParameter("product");
	String biz_nm = StringHelper.evl(request.getParameter("biz_nm"),"");
	String basic_url = StringHelper.evl(
			request.getParameter("basic_url"),
			"jdbc:oracle:thin:@172.16.2.50:1521:ORCL");
	String basic_user = StringHelper.evl(
			request.getParameter("basic_user"), "");
	String basic_password = StringHelper.evl(
			request.getParameter("basic_password"), "");
	String target_url = StringHelper.evl(
			request.getParameter("target_url"),
			"");
	String target_dblink = StringHelper.evl(
			request.getParameter("target_dblink"), "");
	String target_user = StringHelper.evl(
			request.getParameter("target_user"), "");
	String target_password = StringHelper.evl(
			request.getParameter("target_password"), "");
	String compare_obj = StringHelper.evl(request.getParameter("compare_obj"), "DDL");
	String filePath = application.getRealPath("/").replace("\\", "/")
			+ "site/qa/xml/";
	String[] result =null;
	
	Connection baseConn = null;
	Connection targetConn = null;

// 	파라미터 디버깅용 코드 
// 	Enumeration param = request.getParameterNames();
// 	while (param.hasMoreElements())
// 	{
// 		String name = (String)param.nextElement();
// 		out.println(name + ":" + request.getParameter(name) + "<br>");
// 	}

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="text/javascript">
	function pageReload(){
	   	var param ="";
	   	param += "&compare_obj=" + returnSelectValue(compareDBForm.COMPARE_TYPE);
		param += "&MENU_ACT_ID=CF_WEB_QA_COMPARE_DB&PAR_MENU_ACT_ID=CF_WEB_ROOT_9";	
		window.location.href ="/index.do?cmd=redirect&viewName=Util_CompareDB&"+param;
	}
	function compare()
	{
		var compare_obj = returnSelectValue(compareDBForm.COMPARE_TYPE);
	   	var param ="";
	   	param += "&compare_obj=" + compare_obj;
   		param += "&biz_nm=" + document.compareDBForm.biz_nm.value;
	   	param += "&basic_url=" + document.compareDBForm.basic_url.value;
	   	param += "&basic_user=" + document.compareDBForm.basic_user.value;
	   	param += "&basic_password=" + document.compareDBForm.basic_password.value;
	   	param += "&target_dblink=" + document.compareDBForm.target_dblink.value;
	   	param += "&target_user=" + document.compareDBForm.target_user.value;
	   	param += "&target_password=" + document.compareDBForm.target_password.value;
		param += "&MENU_ACT_ID=CF_WEB_QA_COMPARE_DB&PAR_MENU_ACT_ID=CF_WEB_ROOT_9";	
		
		if(compare_obj=="DML" || compare_obj=="DML_KEY" ){
			var product = returnSelectValue(compareDBForm.SEL_PRODUCT);
			param += "&product=" + product;	
			param += "&target_url=" + document.compareDBForm.target_url.value;	
		}

		window.location.href ="/index.do?cmd=redirect&viewName=Util_CompareDB&"+param;
		viewProgressMessageDiv("<fmt:message key="deploy.alert.processing"/>..");
		
	}
</script>
<script language="javascript" src="/js/changeflow/cfcommon.js"></script>
<script src="/GTDataGrid/GTDataGrid.js" language="javascript"></script>			
<%-- <%@ taglib uri="/tld/jSpeedTag.tld" prefix="jSpeedTag"%> --%>
<%-- <%@ taglib uri="/tld/cfTag.tld" prefix="cfTag"%> --%>
<%-- <jsp:include page="/changeflow/progressBar.jsp" /> --%>
<%-- <jsp:include page="/changeflow/progressMessage.jsp" /> --%>
</head>
<body leftmargin="0" marginwidth="0" topmargin="0" marginheight="0"	onload="">
<link href="/css/changeflow/style.css" rel="stylesheet" type="text/css" />
<%-- <%@include file="/changeflow/common/title.jsp" %> --%>
<form method=post name=compareDBForm id=compareDBForm action="">
<iframe
	id="divProgressIframe"
	src="javascript:false"
	scrolling="no"
	frameborder="0"
	style="position:absolute;top:0px;left:0px;display:none">
</iframe>
    <table width="100%" border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
<!-- 			조회조건 시작 -->
				<table>
					<tr>
						<td nowrap class="title_search"><fmt:message key="qa.comparedb.object"/></td>
						<td>
							<jSpeedTag:select.code groupCode="COMPARE_TYPE" className="search_combo" headValue="" name="COMPARE_TYPE" selectedValue="<%=compare_obj %>" onChange="pageReload()"/>				
						</td>
						<%if (compare_obj.matches("DML")||compare_obj.matches("DML_KEY")) {%>
						<td nowrap class="title_search"><fmt:message key="qa.organization.license.product"/></td>
						<td>
							<jSpeedTag:select.code groupCode="PRODUCT" className="search_combo" headValue="" name="SEL_PRODUCT" selectedValue="<%=product %>"/>				
						</td>
						<%}%>
						<%if (compare_obj.matches("ANA_1") || compare_obj.matches("ANA_2")|| compare_obj.matches("ANA_3")) {%>
						<td nowrap class="title_search"><fmt:message key="qa.comparedb.biz.name"/></td>	
						<td><input type="text" id="biz_nm" name="biz_nm" value="<%=biz_nm %>" class="search_input"></td>
						<%} else {%>
						<td><input type="hidden" id="biz_nm" name="biz_nm" value="<%=biz_nm %>" class="search_input"></td>
						<%} %>
						
					</tr>
					<tr>
						<td nowrap class="title_search"><fmt:message key="qa.comparedb.basic.url"/></td>
						<td colspan="3"><input type="text" id="basic_url" name="basic_url" value="<%=basic_url%>" style="width: 100%" class="search_input"></td>
					</tr>
					<tr>
						<td nowrap class="title_search" width="120"><fmt:message key="qa.comparedb.basic.user"/></td>
						<td width="150"><input type="text" id="basic_user" name="basic_user" value="<%=basic_user%> "style="width: 100%" class="search_input"></td>
						<td nowrap class="title_search" width="120"><fmt:message key="qa.comparedb.basic.password"/></td>
						<td width="150"><input type="text" id="basic_password" name="basic_password" value="<%=basic_password%>" style="width: 100%" class="search_input"></td>
					</tr>
					<%
						if (compare_obj.matches("DML") || compare_obj.matches("DML_KEY")) {
					%>
					<tr>
						<td nowrap class="title_search"><fmt:message key="qa.comparedb.target.url"/></td>
						<td colspan="3">
							<input type="hidden" id="target_dblink" name="target_dblink" value="<%=target_dblink%> "style="width: 100%" class="search_input">
							<input type="text" id="target_url" name="target_url" value="<%=target_url%>" style="width: 100%" class="search_input">
						</td>
					</tr>
					<%
						} else {
					%>
					<tr>
						<td nowrap class="title_search"><fmt:message key="qa.comparedb.target.dblink"/></td>
						<td width="150">
							<input type="text" id="target_dblink" name="target_dblink" value="<%=target_dblink%> "style="width: 100%" class="search_input">
							<input type="hidden" id="target_url" name="target_url" value="<%=target_url%> "style="width: 100%" class="search_input">
						</td>
					</tr>
					<%
						}
					%>
					<tr>
						<td nowrap class="title_search"><fmt:message key="qa.comparedb.target.user"/></td>
						<td><input type="text" id="target_user" name="target_user" value="<%=target_user%>" style="width: 100%" class="search_input"></td>
						<td nowrap class="title_search"><fmt:message key="qa.comparedb.target.password"/></td>
<!-- 		        	//오탐 -	45110190	[SP] 정수 오버플로우  -->
						<td width="150"><input type="text" id="target_password" name="target_password" value="<%=target_password%>" style="width: 100%" class="search_input"></td>
					</tr>
				</table>
			</td>
			<td width=100 vAlign=bottom>
	        <span class="btn01_en">
	        	<a href="javascript:compare();">
	        		<span><fmt:message key= "qa.button.compare"/></span>
	        	</a>
	        </span>    
	        </td>
		</tr>
	</table>
<%-- 	<%@include file="/changeflow/common/_searchBoxEnd.jsp" %> --%>
	<%
	if (!"00".equals(product) &&! "".equals(compare_obj) &&!"".equals(basic_url)
			//정탐 -	11869	String 타입 비교시 equlas() 사용 
		&& basic_user != "" && basic_password !="" && target_user !="" && target_password !="") 
	{
		if("DML".equals(compare_obj) || "DML_KEY".equals(compare_obj)){
			if(!"".equals(target_url)){
				System.out.println("*********DML COMPARE START2 ***********");
				System.out.println("product : "+product);
				System.out.println("basic_url : "+basic_url);
				System.out.println("basic_user : "+basic_user);
				System.out.println("basic_password : "+basic_password);
				System.out.println("target_url : "+target_url);
				System.out.println("target_dblink : "+target_dblink);
				System.out.println("target_user : "+target_user);
				System.out.println("target_password : "+target_password);
				System.out.println("filePath : "+filePath);
				System.out.println("*****************************************");
				
				PreparedStatement basePstmt = null;
				PreparedStatement targetPstmt = null;
				ResultSet baseRs = null;
				ResultSet targetRs = null;
				String OnlyKeyValueCheckYn = "N";
				long stime = System.currentTimeMillis();
	
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					//정탐 - 45119302	[SP] 주석문 안에 포함된 ....
					//정탐 -	45110256	[SP] 패스워드 평문 저장
					//정탐 -	45110319	[SP] 기밀 정보의 단순한 텍스트 전송
					baseConn = DriverManager.getConnection(basic_url,
							basic_user, basic_password);
					//정탐 - 45119302	[SP] 주석문 안에 포함된 ....
					//정탐 -	45110256	[SP] 패스워드 평문 저장
					//정탐 -	45110319	[SP] 기밀 정보의 단순한 텍스트 전송
					targetConn = DriverManager.getConnection(target_url,
							target_user, target_password);
					StringBuffer sqlBuff = new StringBuffer();
	
					Document doc = new SAXBuilder().build(new File(
							filePath+product+"DmlDiff.xml"));
					
				   	List lTbl = doc.getRootElement().getChildren();
				   	List lCol = null;
	
				   	Iterator iterTbl = lTbl.iterator();
				   	Iterator iterCol = null; 
				   	Element col = null;
	
					String tableNm = null;
					String colNm = null;
					String keyYn = null;
					String replaceYn = null; // 값비교시  Trim_Replace하여 비교할 테이블인지 체크
					String deli = "@.@,";
					
					int colSeq = 0;
					ArrayList alColKey = new ArrayList();
					//정탐 -	11772	깊은 중첩 If 문 
				   if("DML".equals(compare_obj)){
					   OnlyKeyValueCheckYn ="N";
				   }else{
					   OnlyKeyValueCheckYn ="Y";
				   }
					   colSeq = 0;
					   String sqlStr = null;
					   while(iterTbl.hasNext()) {
						   Element tbl = (Element)iterTbl.next();
						   tableNm = tbl.getAttributeValue("Name");
						   out.println(tableNm);
						   replaceYn = tbl.getAttributeValue("ReplaceYn");
						 //정탐 -	11835	루프에서 객체의 인스턴스 생성 금지
						   sqlBuff = new StringBuffer();
						   sqlBuff.append("select ");
						   
						   /* Column정보를 가져옴 */
						   lCol = tbl.getChildren();
						   iterCol = lCol.iterator();
						   
						 //정탐 -	11835	루프에서 객체의 인스턴스 생성 금지
						   alColKey = new ArrayList();
							%>
							<br/>
							<table border=1><tr>
							<td class="title_left">
							<fmt:message key="qa.comparedb.object"/>
							</td>
							<%


						   while(iterCol.hasNext()) {
							   col = (Element)iterCol.next();
							   colSeq++;
							   colNm = col.getAttributeValue("Name");
							   %><td class="title_left" nowrap="nowrap"><%=colNm %></td><%   
							   keyYn = col.getAttributeValue("KeyYn");
							   sqlBuff.append(colNm).append(',');
							 //정탐 -	11772	깊은 중첩 If 문 
							   if ("Y".equals(keyYn)) {
	//							   System.out.println(colNm);
								   alColKey.add(colNm);
							   }
							   
						   }
							%></tr><%

						   sqlStr = sqlBuff.toString().substring(0,sqlBuff.length()-1)+ " from "+tableNm;
							//정탐 -	45110089	[SP] SQL 삽입
							//정탐 - 11939	DB 자원 재사용 금지
							basePstmt = baseConn.prepareStatement(sqlStr);
						 	//정탐 -	45110089	[SP] SQL 삽입
						 	//정탐 - 11939	DB 자원 재사용 금지
						    targetPstmt = targetConn.prepareStatement(sqlStr);
	// 					   System.out.println(sqlStr);
						   
						   baseRs = basePstmt.executeQuery();
						   targetRs = targetPstmt.executeQuery();
						   
						   HashMap baseHm = getSqlData(baseRs, alColKey, tableNm, sqlStr);
						   HashMap targetHm = getSqlData(targetRs, alColKey, tableNm, sqlStr);
						   

						   result = dataDiff(baseHm, targetHm, replaceYn,OnlyKeyValueCheckYn );
	
						    String[] base_only_table = null;
							String[] base_only_tr=null;
							String[] base_only_td=null;
							
							String[] base_different_key_table = null;
							String[] base_different_key_table_temp = null;
							String[] base_different_key_tr = null;
							String[] base_different_key_td = null;
							
							String[] base_different_table = null;
							String[] base_different_table_temp = null;
							String[] base_different_tr = null;
							String[] base_different_td = null;
	
							
						    String[] target_only_table = null;
							String[] target_only_tr=null;
							String[] target_only_td=null;
							
							//정탐 -	11772	깊은 중첩 If 문 
							if(result !=null){
								%>
									<%
										base_only_table = result[0].split("Base Only =>");
									//정탐 -	86	Loop 문 내에서 상수 할당 금지
										for(int i=0;i<base_only_table.length;i++){
											//오탐 -	103	[샘플]IP 주소의 하드코딩 금지
											base_only_tr = base_only_table[i].split("::");
											//정탐 -	11772	깊은 중첩 If 문 
											if(base_only_tr.length > 1){%>
												<tr>
												<%//정탐 -	11772	깊은 중첩 If 문 
												if(base_only_tr[1].length()>1)
												{
													base_only_tr[1]=base_only_tr[1].substring(1,base_only_tr[1].length()-1);
												}
												base_only_td = base_only_tr[1].split(deli);
													%><td>base only</td><%
													//정탐 -	86	Loop 문 내에서 상수 할당 금지
												for(int j=0;j<base_only_td.length;j++){
													%><td><%=base_only_td[j] %></td><%
												}
											}%></tr><%
										}
										target_only_table = result[3].split("Target Only ==>");
										//정탐 -	86	Loop 문 내에서 상수 할당 금지
										for(int i=0;i<target_only_table.length;i++){
											//오탐 -	103	[샘플]IP 주소의 하드코딩 금지
											target_only_tr = target_only_table[i].split("::");
											//정탐 -	11772	깊은 중첩 If 문 
											if(target_only_tr.length > 1){%>
												<tr>
												<%//정탐 -	11772	깊은 중첩 If 문 
												if(target_only_tr[1].length()>1)
												{
													target_only_tr[1]=target_only_tr[1].substring(1,target_only_tr[1].length()-1);
												}
												target_only_td = target_only_tr[1].split(deli);
													%><td>target only</td><%
													//정탐 -	86	Loop 문 내에서 상수 할당 금지	 << 이 소스만 돌렸을때 해당 라인에서 [SP] 정수 오버플로우 검출됨 mhkim
												for(int j=0;j<target_only_td.length;j++){
													%><td><%=target_only_td[j] %></td><%
												}
											}%></tr><%
										}
	/* 									lEnd = System.currentTimeMillis();
										System.out.println("step1:::"+(lEnd-lStart));
										lStart = System.currentTimeMillis();
	 */
										base_different_table = result[1].split("Difference =>");
										//정탐 -	86	Loop 문 내에서 상수 할당 금지
	 									for(int i = 0; i < base_different_table.length; i++){
											base_different_table_temp = base_different_table[i].split("==>");
											//정탐 -	11772	깊은 중첩 If 문 
											if(base_different_table_temp.length > 1){
												base_different_tr = base_different_table_temp[1].split(" <> ");
												//정탐 -	86	Loop 문 내에서 상수 할당 금지
												for(int j = 0; j < base_different_tr.length; j++){
													//정탐 -	11772	깊은 중첩 If 문 
													if(base_different_tr.length>1){
														base_different_tr[j] = base_different_tr[j].substring(1, base_different_tr[j].length()-1);
													}
													%><tr><td>Difference</td>
													<%
														base_different_td = base_different_tr[j].split(deli);
														//정탐 -	86	Loop 문 내에서 상수 할당 금지
														for(int k=0;k< base_different_td.length;k++){
															%><td><%=base_different_td[k] %></td><%
														}
													%>
													</tr><%
												}
											}
										}
	
										base_different_key_table = result[2].split("Difference =>");
										//정탐 -	86	Loop 문 내에서 상수 할당 금지
										for(int i = 0; i < base_different_key_table.length; i++){
											base_different_key_table_temp = base_different_key_table[i].split("==>");
											//정탐 -	11772	깊은 중첩 If 문 
											if(base_different_key_table_temp.length > 1){
												base_different_key_tr = base_different_key_table_temp[1].split(" <> ");
												//정탐 -	86	Loop 문 내에서 상수 할당 금지
												for(int j = 0; j < base_different_key_tr.length; j++){
													//정탐 -	11772	깊은 중첩 If 문 
													if(base_different_key_tr.length>1){
														base_different_key_tr[j] = base_different_key_tr[j].substring(1, base_different_key_tr[j].length()-1);
													}
													%><tr><td>Difference</td>
													<%
														base_different_key_td = base_different_key_tr[j].split(deli);
														//정탐 -	86	Loop 문 내에서 상수 할당 금지
														for(int k=0;k< base_different_key_td.length;k++){
															%><td><%=base_different_key_td[k] %></td><%
														}
													%>
													</tr><%
												}
											}
										}
										
										
									%>
									</table>
							<br/>
							<%
							}
					   }
					   out.println("--Done!");
				} catch (JDOMException e) {
					Logger.getLogger(e.toString());
				}catch(IOException ie){
					Logger.getLogger(ie.toString());
				}catch(ClassNotFoundException ce){
					Logger.getLogger(ce.toString());
				}catch(SQLException se){
					Logger.getLogger(se.toString());
				}
				finally {
	
					try {
						//정탐 -	11772	깊은 중첩 If 문 
						if (baseRs != null && basePstmt != null && baseConn != null) {
							baseRs.close();
							basePstmt.close();
							baseConn.close();
						}
						//정탐 -	11772	깊은 중첩 If 문 
						if (targetRs != null && targetPstmt != null && targetConn != null ) {
							targetRs.close();
							targetPstmt.close();
						}

					} catch (SQLException ignore) {
						Logger.getLogger(ignore.toString());
					}
				}
	
			}
		}else{
				String fileName = compare_obj+".xml";
				ResultSet rset = null;
				PreparedStatement basePstmt = null;

				try{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					//정탐 -	45119302	[SP] 주석문 안에 포함된 ....
					//정탐 -	45110256	[SP] 패스워드 평문 저장
					//정탐 -	45110319	[SP] 기밀 정보의 단순한 텍스트 전송
					baseConn = DriverManager.getConnection(basic_url,basic_user, basic_password);
					Document doc = new SAXBuilder().build(new File(filePath+fileName));
					List sqlList = doc.getRootElement().getChildren();
					Iterator itersqlList = sqlList.iterator();
					String sqlNm = null;
					String sqlStr = null;
				    int seq =0;
					while(itersqlList.hasNext()) {
						   Element sql = (Element)itersqlList.next();
						   sqlNm = sql.getAttributeValue("ID");
						   sqlStr = sql.getText();
						   sqlStr = sqlStr.replaceAll("##USER",basic_user);
						   sqlStr = sqlStr.replaceAll("##TARGET",target_user);
						   sqlStr = sqlStr.replaceAll("##DBLINK",target_dblink);
						   sqlStr = sqlStr.replaceAll("##BIZ",biz_nm);
						 //정탐 -	45110209	[SP] 오류 메시지 통한 정보 노출
						   System.out.println(sqlStr);
						 	//정탐 -	45110089	[SP] SQL 삽입
						 	//오탐 - 11939	DB 자원 재사용 금지
						 	//if ~ else 구문으로 else 이후에서 처음 사용됨 
						   basePstmt = baseConn.prepareStatement(sqlStr);
						   rset = basePstmt.executeQuery();
						   ResultSetMetaData rsmd = rset.getMetaData();
						   int x = rsmd.getColumnCount();

						   out.println("--"+sqlNm);
						   %>
							<br/>
						   <table style="width:100%" border = 1> 
						   <%
						   while(rset.next()){ 
							   seq++;
							   if(seq==1){
								   %> <tr> <%
								 //정탐 -	86	Loop 문 내에서 상수 할당 금지
								   for (int j = 1 ; j <= x ; j++) {
									   %><td class="title_left" nowrap="nowrap"><B><%=rsmd.getColumnName(j)%></td>
								   <%}%>
								   </tr>
								   
								   <% 
								 //정탐 -	86	Loop 문 내에서 상수 할당 금지
								   for (int j=1;j<=x;j++) 
								   {%>
								   <td> <%=rset.getString(j)%> </td> 
	            					<%}%> 
								   </tr> 
							   <%}
						   }%>
						   </table>
						   <%
					}
					out.println("--Done!");
				}catch(ClassNotFoundException e){
					//정탐 -	45110497	[SP] 시스템 데이터 정보 누출
					//정탐 -	45110209	[SP] 오류 메시지 통한 정보 노출
					e.printStackTrace();
				}catch(JDOMException je){
					Logger.getLogger(je.toString());
				}catch(IOException ie){
					Logger.getLogger(ie.toString());
				}catch(SQLException se){
					Logger.getLogger(se.toString());
				}
				finally{
					if(rset !=null && baseConn !=null && basePstmt !=null){
						try{
							rset.close();
							basePstmt.close();							
							baseConn.close();
						}catch(SQLException se){
							Logger.getLogger(se.toString());
						}
					}
					
				}
		}
}

	%>
<%-- 	<%@include file="/changeflow/common/_contentBoxEnd.jsp" %> --%>
</form>
</body>
</html>
