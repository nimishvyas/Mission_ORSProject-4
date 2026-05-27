<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error 404</title>
</head>
<body>
	<div align="center">
        <h2>404 - Page Not Found</h2>
        <p>Something went wrong. The page you are looking for does not exist.</p>
        
		<img src="img/error404.jpg"  width="550" height="250">
		<hr>
		<a href=<%=ORSView.WELCOME_CTL%>>Go to home Page</a>
	</div>
</body>
</html>

