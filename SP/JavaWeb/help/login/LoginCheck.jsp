<%
	String strUserName = "";
	String strUserId = "";
    String strUserSsn = "";
    String strUserEmail = "";
    
	// 로그인 했을경우
	LoginInfo loginInfo = (LoginInfo)session.getAttribute("loginInfo");
	if (loginInfo != null)
	{
		strUserName = loginInfo.getName();
		strUserId= loginInfo.getId();
		strUserSsn = loginInfo.getSsn();
		strUserEmail = loginInfo.getEmail();
	}
%>