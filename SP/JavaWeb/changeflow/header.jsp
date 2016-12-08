<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib uri="/tld/jSpeedTag.tld" prefix="jSpeedTag"%>
<%@taglib uri="/tld/expressTag.tld" prefix="expressTag"%>
<%@ page import="java.util.logging.*"%>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.*" %>
<%
		java.util.Locale locale = request.getLocale();
		String currentLocale = "en";
		String langType = "ko-KR";
		//정탐 -	11759	리터럴은 항상 비교문의 왼쪽에 배치
		if ( locale.toString().equals("ko") || locale.toString().equals("ko_kr") || locale.toString().equals("ko_KR") ){
		        	currentLocale = "ko";
		}else if ("ja".equals( locale.toString()) ){
		        	currentLocale = locale.toString();
		        	langType = "ja-JP";
		}else{
		        	//오탐 -	45110190	[SP] 정수 오버플로우
        			currentLocale = "en";
		        	langType = "en-US";
		}
		
		String IMG = "/images/"+currentLocale;
 		try
 		{
 			com.itplus.common.server.user.SessionHelper helper = new com.itplus.common.server.user.SessionHelper(session);
 	 		//정탐 -	4306	Catch 문 내의 미처리 Exception 금지
 			if(helper.isSuperUser())
			{
				request.setAttribute("ADMIN_YN", "Y");
			}
			else
			{
				request.setAttribute("ADMIN_YN", "N");
			}
		
			request.setAttribute("SESS_IS_OFF_DUTY",  helper.isOffDuty());
			request.setAttribute("SESS_DEP_ID",  helper.getDeptId());
			
 		}
 		catch(IllegalStateException ignore)
 		{
 			Logger.getLogger(ignore.toString());
 		}
		request.setAttribute("DATE_PATTERN", "yyyyMMddHHmmss");
		request.setAttribute("DATE_FORMAT", "yyyy-MM-dd HH:mm:ss");
		request.setAttribute("DATE_LONG_PATTERN", "yyyyMMddHHmmssSSS");
		request.setAttribute("DATE_FORMAT", "yyyy-MM-dd HH:mm:ss");
		
%>
