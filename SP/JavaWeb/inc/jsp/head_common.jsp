<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.text.*"%>
<!-- //정탐 -	11794	Java.Lang의 Import 금지 -->
<%@ page import="java.lang.*"%>
<%@ page import="java.net.*"%>
<%@ page import=" java.net.URLEncoder"%>
<%@ page import=" java.net.URLDecoder"%>
<%@ page import="java.beans.PropertyDescriptor"%>
<%@ page import="java.util.logging.*"%>
<%
//path 를 가져와서 기본값으로 셋팅
String rootDir = request.getRealPath("/");	
Properties properties = new Properties();
String str2 = rootDir + "WEB-INF/config/database.properties";
FileInputStream file = null;
String path = null;
try
{
//정탐 -	45110404	[SP] 자원의 부적절한 반환
  file = new FileInputStream(str2);
  properties.load(file);
  path = properties.getProperty("path");
}
//정탐 -	45110754	[SP]  적절하지않은 예외처리
//정탐 -	45110390	[SP] 액션 없는 오류 조건 탐지 (오류 상황에 대한 처리 부재)
catch (Exception ex)
{
}	
	/* 로그인 여부 체크 */
	String SESSION_SSN = null;
	String SESSION_USER_NAME = null;
	String SESSION_USER_EMAIL = null; 
	String SESSION_USER_MOBILE = null;
	String SESSION_E_INSU = null;
	String SESSION_CERTIFICATED = null;
	String SESSION_OLD_SSN = null;
	
	try{
		SESSION_SSN = (String)session.getAttribute("user_ssn");  //주민등록번호
		SESSION_USER_NAME = (String)session.getAttribute("user_name");  //이름
		SESSION_USER_EMAIL = (String)session.getAttribute("user_email");  //이메일
		SESSION_USER_MOBILE = (String)session.getAttribute("user_mobile");  //휴대폰번호
		SESSION_E_INSU = (String)session.getAttribute("user_e_insu");		//전자거래 고객 여부 (전자거래고객 : Y, 일반고객 : N)
		SESSION_CERTIFICATED = (String)session.getAttribute("user_certificated");	  //공인인증 여부

		// 현재 로그인 되어 있는지 확인	
		if ((SESSION_SSN == null || "".equals(SESSION_SSN)) && !"Y".equals((String)session.getAttribute("notLogin"))){
			
			//로그인이 되어 있지 않다면 로그인 페이지로 이동 (현제 페이지와 쿼리스트링을 포함해서 보낸다)
			String query = request.getQueryString();
			String uri = request.getRequestURI();
			uri = uri.replaceAll("\n","");
			uri = uri.replaceAll("\r","");

// 			path = path.replaceAll("\n","");
// 			path = path.replaceAll("\r","");

			String loginPage = path +"/util/login/login.jsp";
			
			String returnPage = null;

			if( query != null && query.length() > 0 ){
				returnPage = uri + "?" + query;
			}
			//정탐 -	11715	If~Else 문에 중괄호 사용 권장
			else
				returnPage = uri;
			if( returnPage != null && returnPage.length() > 0 ) {
				//미탐 - 	45110113	[SP] HTTP 응답 분할
				//returnPage에 대해서는 개행문자 처리가 있으나 loginPage에 대해서는 개행문자 처리가 없으므로 HTTP 응답 분할이 일어날 수 있음
				//정탐 -	45110601	[SP] 신뢰되지 않는 URL 주소로의 자동 접속 연결
				response.sendRedirect( loginPage + "?return_url=" +  URLEncoder.encode( returnPage ) );
			} else {
				// 정탐 -	45110113	[SP] HTTP 응답 분할
				// 입력값 path에 대해 개행문자 처리가 없으므로 
				//정탐 -	45110601	[SP] 신뢰되지 않는 URL 주소로의 자동 접속 연결
				response.sendRedirect( loginPage );
			}
			//정탐 -	11700	빈 (Empty) If 문 사용 금지
		}else{
			//이미 로그인이 되어 있다면 아무 처리 없다.
		
		}			
		//정탐 -	45110754	[SP]  적절하지않은 예외처리
	}catch (Exception ex){
		Logger.getLogger(ex.toString());
	}finally{
		session.removeAttribute("notLogin"); //로그인 패스 여부 초기화 
	}

	
//현재 페이지의 파일 및 디렉토리 명 확인해서 메뉴 구조에 대한 항목 값을 지정

	
	
%>
<script>
	var sessionSsn = "<%=SESSION_SSN%>";  
	var sessionName = "<%=SESSION_USER_NAME%>";  
	var sessionEmail = "<%=SESSION_USER_EMAIL%>";  
	var sessionMobile = "<%=SESSION_USER_MOBILE%>";  
	var sessionEinsu = "<%=SESSION_E_INSU%>";  
	var sessionCertificated = "<%=SESSION_CERTIFICATED%>";  
</script>
<meta http-equiv="X-UA-Compatible" content="IE=Edge" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="shortcut icon" href="" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />
<link href="<%=path%>/inc/css/common.css" type="text/css" rel="stylesheet"  />
<link href="<%=path%>/inc/css/util.css" type="text/css" rel="stylesheet"  />

<script src="<%=path%>/inc/js/jquery.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/inc/js/jquery.tmpl.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/inc/js/json2.js" type="text/javascript" charset="utf-8"></script>

<script src="<%=path%>/inc/js/tab.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/inc/js/common.js" type="text/javascript" charset="utf-8"></script>

<link href="<%=path%>/inc/css/base/jquery-ui-1.9.0.custom.min.css" type="text/css" rel="stylesheet"  />
<link href="<%=path%>/inc/css/base/jquery.ui.theme.css" type="text/css" rel="stylesheet"  />
<script src="<%=path%>/inc/js/jquery-ui-1.9.0.custom.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/inc/js/jquery.ba-hashchange.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/inc/js/jquery.easing.1.3.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/inc/js/jquery.ba-resize.min.js" type="text/javascript" charset="utf-8"></script>
<script src="<%=path%>/inc/js/syze.min.js" type="text/javascript" charset="utf-8"></script>


<script src="<%=path%>/inc/js/lib/util.js"></script>
<script src="<%=path%>/inc/js/lib/business_common.js"></script>

<script>
	/*
	헤더 값을 기준으로 모바일 관련 인증서 js 로드
	PC의 경우와 모바일의 경우 js 가 각기 다른것이 로드됨.
	*/
	
	function sendCert(juminVal, fnc){
		eval(fnc + "()");  //공인인증 완료 후 동작 함수
	}
	function sendCert_1(juminVal){
		return true;
	}	
</script>

