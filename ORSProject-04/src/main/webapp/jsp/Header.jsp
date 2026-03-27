<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		UserBean user = (UserBean) session.getAttribute("user");
	%>
	<%
		if(user != null) {
	%>
	<h3>
		Hi,
		<%=user.getFirstName()%>
		(<%=session.getAttribute("role") %>)
	</h3>
	<a href="#"><b>Add User</b></a>
	<b>|</b>
	<a href="#"><b>User List</b></a>
	<b>|</b>
	<a href="#"><b>Add Role</b></a>
	<b>|</b>
	<a href="#"><b>Role List</b></a>
	<b>|</b>
	<a href="<%=ORSView.LOGIN_CTL + "?operation=Logout"%>"><b>Logout</b></a>
	<%
	} else {
	%>
	<h3>Hi, Guest</h3>
	<a href="<%=ORSView.WELCOME_CTL%>"><b>Welcome</b></a> |
	<a href="<%=ORSView.LOGIN_CTL%>"><b>Login</b></a> |
	<a href="<%=ORSView.USER_REGISTRATION_CTL%>">SignUp</a>
	<%
	}
	%>
	<hr>
</body>
</html>