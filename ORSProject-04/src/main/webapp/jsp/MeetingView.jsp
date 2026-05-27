<%@page import="in.co.rays.proj4.controller.MeetingCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Meeting View</title>
</head>
<body>

<form action="<%=ORSView.MEETING_CTL%>" method="post">

	<%@ include file="Header.jsp" %>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.MeetingBean" scope="request"></jsp:useBean>

	<div align="center">

		<h1 style="color: navy">
			<%
				if (bean != null && bean.getId() > 0) {
			%>
				Update
			<%
				} else {
			%>
				Add
			<%
				}
			%>
			Meeting
		</h1>

		<h3>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h3>
		<h3>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h3>

		<input type="hidden" name="id" value="<%=bean.getId()%>">
		<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

		<table>

			
			<tr>
				<th align="left">Host Name<span style="color:red">*</span></th>
				<td>
					<input type="text" name="hostName"
						placeholder="Enter Host Name"
						value="<%=DataUtility.getStringData(bean.getHostName())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("hostName", request)%>
					</font>
				</td>
			</tr>
			
			<tr>
				<th align="left">Platform<span style="color:red">*</span></th>
				<td>
					<input type="text" name="platform"
						placeholder="Enter Platform"
						value="<%=DataUtility.getStringData(bean.getPlatform())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("platform", request)%>
					</font>
				</td>
			</tr>

			
			<tr>
				<th align="left">Duration<span style="color:red">*</span></th>
				<td>
					<input type="text" name="duration"
						placeholder="Enter duration in minutes"
						value="<%=DataUtility.getStringData(bean.getDuration())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("duration", request)%>
					</font>
				</td>
			</tr>


			<!-- Status -->
			<tr>
				<th align="left">Participants<span style="color:red">*</span></th>
				<td>
					<input type="text" name="participants"
						placeholder="Enter participants"
						value="<%=DataUtility.getStringData(bean.getParticipants())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("participants", request)%>
					</font>
				</td>
			</tr>

			
			<tr>
				<th></th>
				<td>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
						<input type="submit" name="operation" value="<%=MeetingCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=MeetingCtl.OP_CANCEL%>">
					<%
						} else {
					%>
						<input type="submit" name="operation" value="<%=MeetingCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=MeetingCtl.OP_RESET%>">
					<%
						}
					%>
				</td>
			</tr>

		</table>
	</div>

</form>

</body>
</html>