<%@ page language="java" contentType="text/html;charset=euc-kr" %>
<%@ page import = "java.io.IOException"%>
<%@ page import = "java.io.*"%>
<%@ page import = "java.net.URLDecoder"%>
<%@ page import = "java.util.Arrays"%>
<%@ page import = "com.amplusc.ep.ticket.*"%> 

<%@ page import = "java.net.URLEncoder" %>
<%@ page import = "com.amplusc.ep.ticket.*"%>


<%
	String cookieval = request.getParameter("MYSAPSSO2");
	String server = request.getServerName();

	int dotPos = server.indexOf(".");

	if (cookieval != null && !"".equals(cookieval)) {
		Cookie cookie = new Cookie ("MYSAPSSO2", SAPTicketVerifier.PartEncoder(URLEncoder.encode(cookieval)));
		if (dotPos != -1) {
			cookie.setDomain(server.substring(dotPos+1));
		}

		cookie.setPath("/");
		//정탐 -	45110614	[SP] HTTPS 세션내에 보안속성없는 민감한 쿠키
		response.addCookie(cookie);
	}

	else {
		response.setStatus(403);
	}


	//Declare & Initialize Variables
	String	MYSAPCOOKIENAME 				= "MYSAPSSO2";
	String	KEYSTORE_PATH 					= "/data3/keystore/portal.store";
	String	KEYSTORE_PASSWORD 				= "sitecms";
	String	RequestPage						= null;

	
	char passwd[] 							= KEYSTORE_PASSWORD.toCharArray();
	SAPTicketVerifier SAPVerifier			= SAPTicketVerifier.getInstance();
	SAPTicketVerifier.SAPTicketInfo	info	= null;



	Cookie SAPCookie 						= null;
	Cookie cookies[] 						= request.getCookies();
	String base64Value 						= null;
	String cookieValue 						= null;
	Cookie setcookie 						= null;

		

		

	//Initialize TicketVerifier	
	//정탐 -	45110259	[SP] 하드코드된 ...
	SAPVerifier.setCertificatesFromKeyStoreFile(KEYSTORE_PATH, passwd);
	Arrays.fill(passwd, ' ');
	RequestPage		=	request.getParameter("page");

	

	//Get Logon Ticket from Cookie
	if(cookies == null) {
		out.println("Request does not contain cookies");
	} else {
	
		for(int i = 0; i < cookies.length; i++) {
			Cookie cook	= cookies[i];
			
			//정탐 -	11716	If 문에 중괄호 사용 권장  
			if(!cook.getName().trim().equals(MYSAPCOOKIENAME))
				continue;

			SAPCookie	= cook;
			break;
		}

	

		cookieValue = SAPCookie.getValue();

		

		//Verify Logon Ticket	
		if(!SAPTicketVerifier.isNullOrEmptyString(cookieValue)){
			try {
				base64Value	= URLDecoder.decode(cookieValue);
				// JDK 1.3 버전에서는 윗부분 주석처리 후 아랫 부분을 
				// base64Value	= URLDecoder.decode(cookieValue, "UTF-8");

				info = SAPVerifier.verifyTicket(base64Value);
				
				if(info != null){
					//out.println("/jsp/cm/login.jsp?isSSO=Y&LOGIN_ID="+info.getUser());
					%>
					<form name="fm" method="post" action="/jsp/cm/login.jsp" target="_top">
						<input type="hidden" name="SSO_TYPE" value="DUMMY">
						<input type="hidden" name="USER_PWD" value="info.getUser()">
						<input type="hidden" name="LOGIN_ID" value="<%=info.getUser()%>">
					</form>
					<script>
						fm.submit();
					</script>
					<%
					//정탐 -	45119302	[SP] 주석문 안에 포함된 ...
					//response.sendRedirect("/jsp/cm/login.jsp?SSO_TYPE=DUMMY&USER_PWD=1&LOGIN_ID="+info.getUser());
					//out.println("User_ID is " + info.getUser()+"<br>");	
					//out.println("System is " + info.System()+"<br>");		
					//out.println("Client is " + info.Client()+"<br>");	
					//out.println("Expiration is " + info.Expiration()+"<br>");	
				}

			} catch(UnsupportedEncodingException useex) {
				out.println("UTF-8 encoding not supported");
			}
		}
	}
%>







