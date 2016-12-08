<%@page import="java.security.spec.InvalidKeySpecException"%>
<%@page import="java.security.NoSuchAlgorithmException"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@ page contentType="text/html; charset=utf-8" %>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.util.*"%>
<%@ page import="com.itplus.wf.work.*" %>
<%@ page import="com.itplus.wf.inst.*" %>
<%@ page import="com.itplus.wf.def.proc.*" %>
<%@ page import="com.itplus.wf.def.lane.*" %>
<%@ page import="com.itplus.wf.def.act.*" %>
<%@ page import="com.itplus.wf.def.*" %>
<%@ page import="jspeed.base.util.*" %>
<%@ page import="jspeed.base.http.*" %>
<%@ page import="jspeed.base.log.*" %>
<%@ page import="java.util.*"%>
<%@ page import="java.sql.*"%>
<%@ page import="jspeed.base.jdbc.*"%>


<%@ page import="jspeed.base.util.*" %>
<%@ page import="jspeed.admin.*" %>
<%@ page import="jspeed.admin.auth.user.*" %>



 
<%	
	try{
	request.setCharacterEncoding("utf-8") ;
	session.invalidate();
	session = request.getSession(true);
	session.setAttribute("JSESSIONID", session.getId());
	Logger log  = LogService.getInstance().getLogger();
	 
	String strUserId  = null;
	String strUserPwd = null;
	java.security.KeyPairGenerator generator =  java.security.KeyPairGenerator.getInstance("RSA");

	//정탐 -	45119310	[SP] 취약한 암호화: 충분하지 못한 키의 길이
	generator.initialize(512);

	java.security.KeyPair keyPair = generator.genKeyPair();
	java.security.KeyFactory keyFactory = java.security.KeyFactory.getInstance("RSA");

	java.security.PublicKey publicKey = keyPair.getPublic();
	java.security.PrivateKey privateKey = keyPair.getPrivate();
	 
	//세션에 공개키의 문자열을 키로하여 개인키를 저장한다.
	session.setAttribute("__rsaPrivateKey__", privateKey);
	
	java.security.spec.RSAPublicKeySpec publicSpec = (java.security.spec.RSAPublicKeySpec) keyFactory.getKeySpec(publicKey, java.security.spec.RSAPublicKeySpec.class);

	String publicKeyModulus = publicSpec.getModulus().toString(16);
	String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

	request.setAttribute("publicKeyModulus", publicKeyModulus);
	request.setAttribute("publicKeyExponent", publicKeyExponent);

	/*******************************
	 * 아이디 저장을 위한 로직 추가 시작
	 *******************************/
	String userIdCookie = "";
	String userIdCookieExist = "";
	HttpRequestWrapper req = new HttpRequestWrapper (request) ;
	Cookie[] cfCookie = req.getCookies();
	if(cfCookie != null){
		for(int i = 0; i < cfCookie.length; i++){
			if("userIdCookie".equals(cfCookie[i].getName())){
				userIdCookie = cfCookie[i].getValue();
// 				userIdCookieExist = "checked";
				break;
			}
		}
	}
	/*******************************
	 * 아이디 저장을 위한 로직 추가 끝
	 *******************************/
	

		 
		strUserId  = StringHelper.evl(request.getParameter("LOGIN_ID").trim(), null);
		strUserPwd = StringHelper.evl(request.getParameter("USER_PWD"), null);
	}catch(UnsupportedEncodingException ue){
		Logger.parseLocation(ue.toString());
	}catch(NoSuchAlgorithmException ne){
		Logger.parseLocation(ne.toString());
	}catch(InvalidKeySpecException ise){
		Logger.parseLocation(ise.toString());
	}
		
%>
<html>
<head>
<title><fmt:message key="configuration.management.login.title"/></title>
<link href="/css/changeflow/style.css" rel="stylesheet" type="text/css" />
<link rel="shortcut icon" href="/owner.ico">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script language="javascript" src="/js/changeflow/cfcommon.js"></script>
	 <script type="text/javascript" src="/js/security/jsbn.js"></script>
        <script type="text/javascript" src="/js/security/rsa.js"></script>
        <script type="text/javascript" src="/js/security/prng4.js"></script>
        <script type="text/javascript" src="/js/security/rng.js"></script>
<script language=javascript>
function init(error_flag) {
	if('<c:out value="${USER_CNT}"/>' != ''){
		if('<c:out value="${USER_CNT}"/>' == '0'){
			alert('<fmt:message key="login.validation.noUser"/>');
		}else{
			alert('<fmt:message key="login.validation.sucessClearPass"/>');
		}
	}
	if(error_flag == "Y"){
		<c:choose>
			<c:when test="${error_msg eq 'ABSENT'}">
				alert("<c:out value="${USER_INFO.USER_NM}"/>" + "<fmt:message key="mr"/>" + " " + "<fmt:message key="offduty.on"/>");
			</c:when>
			<c:when test="${error_msg eq 'NOGROUP'}">
			alert("<fmt:message key="login.message.nogroup"/>");
		</c:when>
			<c:otherwise>	
				alert("<c:out value='${error_msg}'/>");
			</c:otherwise>
		</c:choose>
	}
	try{
		if( document.login.LOGIN_ID.value != "")
			document.login.USER_PWD.focus();
		else
			document.login.LOGIN_ID.focus();
	}catch(e)
	{}
}	

function inputReset() {
	login.LOGIN_ID.value = "";
	login.USER_PWD.value = "";
	login.LOGIN_ID.focus();
}

function releaseAbsent(){
	location.href="/index.do?cmd=release_absent";
}

function registUser(){
	var pop = window.open("/index.do?cmd=user_register_form","registerUser","width=500, height=270, status=yes");
	pop.focus();
}

function setLoginIdCookie(){
	var expdate = new Date();
	if(document.login.saveId.checked){
		expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 7); // 7일
		document.cookie = "userIdCookie=" + escape (login.LOGIN_ID.value) + "; path=/; expires=" + expdate.toGMTString() + ";";
	}else{
		expdate.setTime(expdate.getDate() - 1); // 7일
		document.cookie = "userIdCookie=" + escape (login.LOGIN_ID.value) + "; path=/; expires=" + expdate.toGMTString() + ";";
	}
}

function searchId(){
	var pop = window.open("/index.do?cmd=user_searchId_form","searchId","width=500, height=160, scrollbars=yes");
	pop.focus();
}

function clearPassword(){
	if( ! validInputField(login.LOGIN_ID, '<fmt:message key="login.validation.loginId"/>') ) return ;
	if (confirm('<fmt:message key="login.validation.password"/>')) {
		login.cmd.value = "clearPass";
		login.submit();
	}
	
}
function loginFun(){
	if (login.LOGIN_ID.value == "") {
		alert("<fmt:message key="alert.input.id"/>");
		login.LOGIN_ID.focus();
		return;
	} else if (login.USER_PWD.value == ""){
		alert("<fmt:message key="alert.input.password"/>");
		login.USER_PWD.focus();
		return;
	}
	if(document.login.saveId.checked){
		var expdate = new Date();
		expdate.setTime(expdate.getTime() + 1000 * 3600 * 24 * 7); // 7일
		document.cookie = "userIdCookie=" + escape (login.LOGIN_ID.value) + "; path=/; expires=" + expdate.toGMTString() + ";";
	}
	try {
        var rsaPublicKeyModulus = document.getElementById("rsaPublicKeyModulus").value;
        var rsaPublicKeyExponent = document.getElementById("rsaPublicKeyExponent").value;
        submitEncryptedForm(login.LOGIN_ID.value,login.USER_PWD.value, rsaPublicKeyModulus, rsaPublicKeyExponent);
    } catch(err) {
        alert(err);
    }
     
}

function submitEncryptedForm(username, password, rsaPublicKeyModulus, rsaPpublicKeyExponent) {
	 
    var rsa = new RSAKey();
    rsa.setPublic(rsaPublicKeyModulus, rsaPpublicKeyExponent);

    // 사용자ID와 비밀번호를 RSA로 암호화한다.
    var securedUsername = rsa.encrypt(username);
    var securedPassword = rsa.encrypt(password);
    
    // POST 로그인 폼에 값을 설정하고 발행(submit) 한다.
    
    loginForm.securedUsername.value = securedUsername;
    loginForm.securedPassword.value = securedPassword;
    
    loginForm.cmd.value = "login";
    loginForm.submit();
}

</script>

<script language="javascript">
<!--
	// notice popup //
	var position  = 0;
	var add_pos   = 50;
	
	 
	
	function doLoad(){

		/*
		if ( getCookie( "expirePopup" ) != "done" ){
			url = "/jspeed/common/systemAccountExpirePopup.jsp";

			winObj = window.open(url, "expirePopup", "status=yes,resizable=yes,scrollbars=yes,top=" + position + ",left=" + position + ",width=600,height=480");
			winObj.focus();
		}
		*/
	}

	window.onload = doLoad;

	function getCookie(varname) {
		varname += "=";
		startpos = document.cookie.indexOf(varname);

        if (startpos >= 0) {
			startpos += varname.length;
			endpos = document.cookie.indexOf(";", startpos);
			if (endpos == -1) endpos = document.cookie.length;
    		return unescape(document.cookie.substring(startpos, endpos));
	    }
	}
//-->
</script>

</head>



	
<body   leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" scroll="no"  onload="init('<c:out value="${error_flag}"/>')" >
<!-- <form name=login method="Post" action="index.do" onsubmit="setAction();">-->

<form name=login >       
<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td class="login_bg">&nbsp;</td>
        <td width="966" valign="top">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td><img src="images/changeflow/login/log_bg01.jpg"></td>
                    <td><img src="images/changeflow/login/log_bg02.jpg"></td>
                </tr>
                <tr>
                    <td><img src="images/changeflow/login/log_bg03.jpg"></td>
                    <td class="log_box">
						<!-- login box 시작 -->
						<table border="0" cellspacing="0" cellpadding="0">
                            <tr>
                                <td height="39" valign="top"><img src="images/changeflow/login/tit_log.gif"></td>
                            </tr>
                            <tr>
                                <td>
          	<c:choose>
	          	<c:when test="${error_msg eq 'ABSENT'}">
	    			<a href="javascript:releaseAbsent()"><fmt:message key="offduty.release"/></a>
	    		</c:when>
	          	<c:otherwise>
	    			<table border="0" cellpadding="0" cellspacing="0">   
	    			     
		              <tr>
		                <td height="24">
		                  <input name="LOGIN_ID" type="text" tabindex="1" class="border_gray" style="width:110;ime-mode:inactive;" value="<%=userIdCookie %>" onKeyDown="javascript:if(event.keyCode == 13) {loginFun(); return false;}">
		                </td>
		               
		               <td rowspan="2"><a href="#" onclick="loginFun();return false"><img src="images/changeflow/login/bt_login.gif" alt="Login" hspace="10" border="0" onMouseOver="this.src='images/changeflow/login/bt_login_on.gif'" onMouseOut="this.src='images/changeflow/login/bt_login.gif'"></a></td>
		              </tr>
		              
		              <tr> 
		                <td colspan=1 height="24">
		                  <input name="USER_PWD" type="password"  tabindex="2" class="border_gray" style="width:110" value="" onKeyDown="javascript:if(event.keyCode == 13) {loginFun(); return false;}">
		                </td>
		                
		              </tr>
		               <tr>
                                <td>
									<table border="0" cellspacing="0" cellpadding="0">
                                        <tr>
		              					 <td align="left" class="t_home">
						 				 <input type="checkbox" name="saveId" onclick="setLoginIdCookie()" <%=userIdCookieExist %>><fmt:message key="save.id"/> 	                
		               					 </td>
		               					 </tr>
		               					 </table>
		               					 </td>
		               					 </tr>
		             			 
		            </table>
    			</c:otherwise>
       		</c:choose>
          </td>
        </tr>
                        </table>
						<!-- login box 끝 -->
					</td>
                </tr>
            </table>
		</td>
        <td class="login_bg">&nbsp;</td>
    </tr>
    <tr>
        <td class="copy_bg02">&nbsp;</td>
        <td class="copy_bg01 bot_copyright">Copyright (c) GTONE Co.,Ltd. All Rights Reserved.</td>
        <td class="copy_bg02">&nbsp;</td>
    </tr>
</table>
</form>
<form name=loginForm method="Post" action="index.do" >
<input type=hidden name=SESS_LANG_TYPE value=<%=langType %>>
<input type="hidden" name="cmd" value="login" onSubmit="return false">
 <input type="hidden" id="rsaPublicKeyModulus" value="<%=publicKeyModulus%>" />
            <input type="hidden" id="rsaPublicKeyExponent" value="<%=publicKeyExponent%>" />
            <input type="hidden" name="securedUsername" id="securedUsername" value="" />
            <input type="hidden" name="securedPassword" id="securedPassword" value="" />
            </form>
</body>
</html>



   