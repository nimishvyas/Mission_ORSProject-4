<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.EventCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Event View</title>
</head>
<body>

<form action="<%=ORSView.EVENT_CTL%>" method="post">

	<%@ include file="Header.jsp" %>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.EventBean" scope="request"></jsp:useBean>

	<div align="center">

		<h1 style="color: navy">
			<%
				if (bean != null && bean.getEventId() > 0) {
			%>
				Update
			<%
				} else {
			%>
				Add
			<%
				}
			%>
			Event
		</h1>

		<h3>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h3>
		<h3>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h3>

		<input type="hidden" name="id" value="<%=bean.getEventId()%>">
		<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

		<table>

			<!-- Event Code -->
			<tr>
				<th align="left">Event Code<span style="color:red">*</span></th>
				<td>
					<input type="text" name="eventCode"
						placeholder="Enter Event Code"
						value="<%=DataUtility.getStringData(bean.getEventCode())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("eventCode", request)%>
					</font>
				</td>
			</tr>

			<!-- Event Name -->
			<tr>
				<th align="left">Event Name<span style="color:red">*</span></th>
				<td>
					<input type="text" name="eventName"
						placeholder="Enter Event Name"
						value="<%=DataUtility.getStringData(bean.getEventName())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("eventName", request)%>
					</font>
				</td>
			</tr>

			<!-- Event Date -->
			<tr>
				<th align="left">Event Date<span style="color:red">*</span></th>
				<td>
					<input type="text" id="udate" name="eventTime"
						placeholder="Select Event Date"
						value="<%=DataUtility.getDateString(bean.getEventTime())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("eventTime", request)%>
					</font>
				</td>
			</tr>

			<!-- Status -->
			<tr>
				<th align="left">Status<span style="color:red">*</span></th>
				<td>
					<%
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("Active", "Active");
						map.put("Inactive", "Inactive");

						String htmlList = HTMLUtility.getList("status", bean.getStatus(), map);
					%>
					<%=htmlList%>
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("status", request)%>
					</font>
				</td>
			</tr>

			<!-- Buttons -->
			<tr>
				<th></th>
				<td>
					<%
						if (bean != null && bean.getEventId() > 0) {
					%>
						<input type="submit" name="operation" value="<%=EventCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=EventCtl.OP_CANCEL%>">
					<%
						} else {
					%>
						<input type="submit" name="operation" value="<%=EventCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=EventCtl.OP_RESET%>">
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