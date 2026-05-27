<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Daily Module</title>
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<script src="https://code.jquery.com/ui/1.13.2/jquery-ui.min.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.13.2/themes/base/jquery-ui.css">

<script src="/ORSProject-04/js/checkbox.js"></script>
<script src="/ORSProject-04/js/datepicker.js"></script>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16">
</head>
<body>

	<img src="<%=request.getContextPath()%>/img/customLogo.jpg"
		align="right" width="100" height="40" border="0">
	<%
	UserBean user = (UserBean) session.getAttribute("user");
	boolean loggedIn = user != null;
	%>
	<%
	if (loggedIn) {
	%>
	<h3>
		Hi,
		<%=user.getFirstName()%>
		(<%=session.getAttribute("role")%>)
	</h3>
	<a href="<%=ORSView.WELCOME_CTL%>"><b>Main</b></a>
	<b>|</b>
	<a href="<%=ORSView.EVENT_CTL%>"><b>Add Event</b></a>
	<b>|</b>
	<a href="<%=ORSView.EVENT_LIST_CTL%>"><b>Event List</b></a>
	<b>|</b>
	<a href="<%=ORSView.VERSION_CTL%>"><b>Add Version</b></a>
	<b>|</b>
	<a href="<%=ORSView.VERSION_LIST_CTL%>"><b>Version List</b></a>
	<b>|</b>
	<a href="<%=ORSView.REVENUE_CTL%>"><b>Add Revenue</b></a>
	<b>|</b>
	<a href="<%=ORSView.REVENUE_LIST_CTL%>"><b>Revenue List</b></a>
	<b>|</b>
	<a href="<%=ORSView.GAMING_CTL%>"><b>Add Game</b></a>
	<b>|</b>
	<a href="<%=ORSView.GAMING_LIST_CTL%>"><b>Game List</b></a>
	<b>|</b>
	<a href="<%=ORSView.CLOUD_CTL%>"><b>Add File</b></a>
	<b>|</b>
	<a href="<%=ORSView.CLOUD_LIST_CTL%>"><b>File List</b></a>
	<b>|</b>
	<a href="<%=ORSView.EVICTION_CTL%>"><b>Add Eviction</b></a>
	<b>|</b>
	<a href="<%=ORSView.EVICTION_LIST_CTL%>"><b>Eviction List</b></a>
	<%
		}
	%>
	
<hr>
</body>
</html>