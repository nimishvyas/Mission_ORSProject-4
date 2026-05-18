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
</head>
<body>
	<br>
	<img src="<%=request.getContextPath()%>/img/customLogo.jpg"
		align="right" width="100" height="40" border="0">
	<%
	UserBean user = (UserBean) session.getAttribute("user");
	%>
	<%
	if (user != null) {
	%>
	<h3>
		Hi,
		<%=user.getFirstName()%>
		(<%=session.getAttribute("role")%>)
	</h3>
	<a href="MyProfileCtl"><b>My Profile</b></a>
	<b>|</b>
	<a href="ChangePasswordCtl"><b>Change Password</b></a>
	<b>|</b>
	<a href="GetMarksheetCtl"><b>Get Marksheet</b></a>
	<b>|</b>
	<a href="MarksheetMeritListCtl"><b>Marksheet Merit List</b></a>
	<b>|</b>
	<a href="<%=ORSView.USER_CTL%>"><b>Add User</b></a>
	<b>|</b>
	<a href="<%=ORSView.USER_LIST_CTL%>"><b>User List</b></a>
	<b>|</b>
	<a href="<%=ORSView.ROLE_CTL%>"><b>Add Role</b></a>
	<b>|</b>
	<a href="<%=ORSView.ROLE_LIST_CTL%>"><b>Role List</b></a>
	<b>|</b>
	<a href="CollegeCtl"><b>Add College</b></a>
	<b>|</b>
	<a href="CollegeListCtl"><b>College List</b></a>
	<b>|</b>
	<a href="StudentCtl"><b>Add Student</b></a>
	<b>|</b>
	<a href="StudentListCtl"><b>Student List</b></a>
	<b>|</b>
	<a href="MarksheetCtl"><b>Add Marksheet</b></a>
	<b>|</b>
	<a href="MarksheetListCtl"><b>Marksheet List</b></a>
	<b>|</b>
	<a href="CourseCtl"><b>Add Course</b></a>
	<b>|</b>
	<a href="CourseListCtl"><b>Course List</b></a>
	<b>|</b>
	<a href="SubjectCtl"><b>Add Subject</b></a>
	<b>|</b>
	<a href="SubjectListCtl"><b>Subject List</b></a>
	<b>|</b>
	<a href="TimetableCtl"><b>Add Timetable</b></a>
	<b>|</b>
	<a href="TimetableListCtl"><b>Timetable List</b></a>
	<b>|</b>
	<a href="FacultyCtl"><b>Add Faculty</b></a>
	<b>|</b>
	<a href="FacultyListCtl"><b>Faculty List</b></a>
	<b>|</b>
	<a target="blank" href="<%=ORSView.JAVA_DOC%>"><b>Java Doc</b></a>
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
	<a href="<%=ORSView.LOGIN_CTL + "?operation=Logout"%>"><b>Logout</b></a>
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
</html>