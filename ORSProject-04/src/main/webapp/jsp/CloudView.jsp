<%@page import="in.co.rays.proj4.controller.CloudCtl"%>
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
<title>Cloud View</title>
</head>
<body>

<form action="<%=ORSView.CLOUD_CTL%>" method="post">

	<%@ include file="ModuleHeader.jsp" %>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CloudBean" scope="request"></jsp:useBean>

	<div align="center">

		<h1 style="color: navy">
			<%
				if (bean != null && bean.getFileId() > 0) {
			%>
				Update
			<%
				} else {
			%>
				Add
			<%
				}
			%>
			File
		</h1>

		<h3>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h3>
		<h3>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h3>

		<input type="hidden" name="id" value="<%=bean.getFileId()%>">
		<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

		<table>

			<!-- Event Code -->
			<tr>
				<th align="left">File Name<span style="color:red">*</span></th>
				<td>
					<input type="text" name="fileName"
						placeholder="Enter file Name"
						value="<%=DataUtility.getStringData(bean.getFileName())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("fileName", request)%>
					</font>
				</td>
			</tr>

			<!-- Event Name -->
			<tr>
				<th align="left">File Size<span style="color:red">*</span></th>
				<td>
					<input type="text" name="fileSize"
						placeholder="Enter File Size"
						value="<%=DataUtility.getStringData(bean.getFileSize())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("fileSize", request)%>
					</font>
				</td>
			</tr>

			<!-- Event Date -->
			<tr>
				<th align="left">Upload Date<span style="color:red">*</span></th>
				<td>
					<input type="text" id="udate" name="uploadDate"
						placeholder="Select Upload Date"
						value="<%=DataUtility.getDateString(bean.getUploadDate())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("eventTime", request)%>
					</font>
				</td>
			</tr>

			<!-- Status -->
			<tr>
				<th align="left">User Name<span style="color:red">*</span></th>
				<td>
					<input type="text" name="userName"
						placeholder="Enter User Name"
						value="<%=DataUtility.getStringData(bean.getUserName())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("userName", request)%>
					</font>
				</td>
			</tr>

			<!-- Buttons -->
			<tr>
				<th></th>
				<td>
					<%
						if (bean != null && bean.getFileId() > 0) {
					%>
						<input type="submit" name="operation" value="<%=CloudCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=CloudCtl.OP_CANCEL%>">
					<%
						} else {
					%>
						<input type="submit" name="operation" value="<%=CloudCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=CloudCtl.OP_RESET%>">
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