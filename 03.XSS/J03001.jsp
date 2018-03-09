<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<html> 
<head> 
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"> 
</head> 
<body> 
<h1>XSS Sample</h1> 
<% 
String name = request.getParameter("name");
%>
<p>NAME:<%=name%></p> 
</body> 
</html>
