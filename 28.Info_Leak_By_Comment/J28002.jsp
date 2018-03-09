<%@ page import="java.util.*" %>
<%@ contentType="text/html;charset=UTF-8" %>
<html>
<body>
	<form action="./login.jsp" method="post">
		ID : <input type="text" name ="userID"><br>
		<!-- // ID: admin, PASS: qaz12wsx34 -->
		PASS : <input type="password" name ="userPWD"><br>
		<input type="submit" value="login">
	</form>
	<form action="s-http:ManageCert" method="post">
		<input type="submit" value="admin">
			</form>
	</body>
</html>