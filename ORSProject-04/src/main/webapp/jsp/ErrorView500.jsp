<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Error 500</title>
</head>
<body>
	<div align="center">
        <h2>500 - Internal Server Error</h2>
        <p>Something went wrong on our end. Please try again later.</p>
        
		<img src="img/error500.png"  width="550" height="250">
		<hr>
		<a href=<%=ORSView.WELCOME_CTL%>>Go to home Page</a>
	</div>
</body>
</html>