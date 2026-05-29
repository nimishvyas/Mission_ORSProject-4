<%@page import="in.co.rays.proj4.bean.RoleBean"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.bean.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Header</title>
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
	<!--  Common Menu -->
	<a href="<%=ORSView.MY_PROFILE_CTL%>"><b>My Profile</b></a>
	<b>|</b>
	<a href="<%=ORSView.CHANGE_PASSWORD_CTL%>"><b>Change Password</b></a>
	<b>|</b>
	<a href="<%=ORSView.GET_MARKSHEET_CTL%>"><b>Get Marksheet</b></a>
	<b>|</b>
	<a href="<%=ORSView.MARKSHEET_MERIT_LIST_CTL%>"><b>Marksheet
			Merit List</b></a>
	<b>|</b>
	<!-- Logout -->
	<a href="<%=ORSView.LOGIN_CTL + "?operation=Logout"%>"><b>Logout</b></a>
	<b>|</b>
	<!-- ADMIN -->
	<%
		if (user.getRoleId() == RoleBean.ADMIN) {
	%>


	<a href="<%=ORSView.USER_CTL%>"><b>Add User</b></a>
	<b>|</b>
	<a href="<%=ORSView.USER_LIST_CTL%>"><b>User List</b></a>
	<b>|</b>
	<a href="<%=ORSView.ROLE_CTL%>"><b>Add Role</b></a>
	<b>|</b>
	<a href="<%=ORSView.ROLE_LIST_CTL%>"><b>Role List</b></a>
	<b>|</b>
	<a href="<%=ORSView.COLLEGE_CTL%>"><b>Add College</b></a>
	<b>|</b>
	<a href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>College List</b></a>
	<b>|</b>
	<a href="<%=ORSView.STUDENT_CTL%>"><b>Add Student</b></a>
	<b>|</b>
	<a href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a>
	<b>|</b>
	<a href="<%=ORSView.MARKSHEET_CTL%>"><b>Add Marksheet</b></a>
	<b>|</b>
	<a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet List</b></a>
	<b>|</b>
	<a href="<%=ORSView.COURSE_CTL%>"><b>Add Course</b></a>
	<b>|</b>
	<a href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a>
	<b>|</b>
	<a href="<%=ORSView.SUBJECT_CTL%>"><b>Add Subject</b></a>
	<b>|</b>
	<a href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>Subject List</b></a>
	<b>|</b>
	<a href="<%=ORSView.TIMETABLE_CTL%>"><b>Add Timetable</b></a>
	<b>|</b>
	<a href="<%=ORSView.TIMETABLE_LIST_CTL%>"><b>Timetable List</b></a>
	<b>|</b>
	<a href="<%=ORSView.FACULTY_CTL%>"><b>Add Faculty</b></a>
	<b>|</b>
	<a href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty List</b></a>
	<b>|</b>
	<a target="blank" href="<%=ORSView.JAVA_DOC%>"><b>Java Doc</b></a>
	<b>|</b>
	<a href="<%=ORSView.MODULE_CTL%>"><b>Daily Module</b></a>
	<b>|</b>
	<a href="<%=ORSView.ATM_CTL%>"><b>Add Atm</b></a>
	<b>|</b>
	<a href="<%=ORSView.ATM_LIST_CTL%>"><b>Atm List</b></a>
	
	<%
		}
	%>
	<%
		if (user.getRoleId() == RoleBean.COLLEGE) {
	%>
	<a href="<%=ORSView.COLLEGE_CTL%>"><b>Add College</b></a>
	<b>|</b>
	<a href="<%=ORSView.COLLEGE_LIST_CTL%>"><b>College List</b></a>
	<b>|</b>
	<a href="<%=ORSView.STUDENT_CTL%>"><b>Add Student</b></a>
	<b>|</b>
	<a href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a>
	<b>|</b>
	<a href="<%=ORSView.MARKSHEET_CTL%>"><b>Add Marksheet</b></a>
	<b>|</b>
	<a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet List</b></a>
	<b>|</b>
	<a href="<%=ORSView.COURSE_CTL%>"><b>Add Course</b></a>
	<b>|</b>
	<a href="<%=ORSView.COURSE_LIST_CTL%>"><b>Course List</b></a>
	<b>|</b>
	<a href="<%=ORSView.SUBJECT_CTL%>"><b>Add Subject</b></a>
	<b>|</b>
	<a href="<%=ORSView.SUBJECT_LIST_CTL%>"><b>Subject List</b></a>
	<b>|</b>
	<a href="<%=ORSView.TIMETABLE_CTL%>"><b>Add Timetable</b></a>
	<b>|</b>
	<a href="<%=ORSView.TIMETABLE_LIST_CTL%>"><b>Timetable List</b></a>
	<b>|</b>
	<a href="<%=ORSView.FACULTY_CTL%>"><b>Add Faculty</b></a>
	<b>|</b>
	<a href="<%=ORSView.FACULTY_LIST_CTL%>"><b>Faculty List</b></a>
	<b>|</b>
	<%
		}
	%>
	<%
		if (user.getRoleId() == RoleBean.FACULTY) {
	%>
	<a href="<%=ORSView.MARKSHEET_CTL%>"><b>Add Marksheet</b></a>
	<b>|</b>
	<a href="<%=ORSView.MARKSHEET_LIST_CTL%>"><b>Marksheet List</b></a>
	<b>|</b>
	<a href="<%=ORSView.TIMETABLE_CTL%>"><b>Add Timetable</b></a>
	<b>|</b>
	<a href="<%=ORSView.TIMETABLE_LIST_CTL%>"><b>Timetable List</b></a>
	<b>|</b>
	<a href="<%=ORSView.STUDENT_LIST_CTL%>"><b>Student List</b></a>
	<b>|</b>
	<%
		}
	%>
	<%
	} else {
	%>
	<h3>Hi, Guest</h3>
	<a href="<%=ORSView.WELCOME_CTL%>"><b>Welcome</b></a> |
	<a href="<%=ORSView.LOGIN_CTL%>"><b>Login</b></a>

	<%
	}
	%>
	<hr>
</body>
<%@ include file="Footer.jsp"%>
</html>