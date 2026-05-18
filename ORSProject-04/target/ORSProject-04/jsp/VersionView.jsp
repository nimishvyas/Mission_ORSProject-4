<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.VersionCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Version View</title>
</head>
<body>

<form action="<%=ORSView.VERSION_CTL%>" method="post">

	<%@ include file="Header.jsp" %>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.VersionBean" scope="request"></jsp:useBean>

	<div align="center">

		<h1 style="color: navy">
			<%
				if (bean != null && bean.getVersionId() > 0) {
			%>
				Update
			<%
				} else {
			%>
				Add
			<%
				}
			%>
			Version
		</h1>

		<h3>
			<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
		</h3>
		<h3>
			<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
		</h3>

		<input type="hidden" name="id" value="<%=bean.getVersionId()%>">
		<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
		<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
		<input type="hidden" name="createdDatetime"
			value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
		<input type="hidden" name="modifiedDatetime"
			value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

		<table>

			<!-- Event Code -->
			<tr>
				<th align="left">Version Code<span style="color:red">*</span></th>
				<td>
					<input type="text" name="versionCode"
						placeholder="Enter Version Code"
						value="<%=DataUtility.getStringData(bean.getVersionCode())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("versionCode", request)%>
					</font>
				</td>
			</tr>

			<!-- Version Name -->
			<tr>
				<th align="left">Version Name<span style="color:red">*</span></th>
				<td>
					<input type="text" name="versionName"
						placeholder="Enter Version Name"
						value="<%=DataUtility.getStringData(bean.getVersionName())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("versionName", request)%>
					</font>
				</td>
			</tr>

			<!-- Version Date -->
			<tr>
				<th align="left">Release Date<span style="color:red">*</span></th>
				<td>
					<input type="text" id="udate" name="releaseDate"
						placeholder="Select Release Date"
						value="<%=DataUtility.getDateString(bean.getReleaseDate())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("releaseDate", request)%>
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
						if (bean != null && bean.getVersionId() > 0) {
					%>
						<input type="submit" name="operation" value="<%=VersionCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=VersionCtl.OP_CANCEL%>">
					<%
						} else {
					%>
						<input type="submit" name="operation" value="<%=VersionCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=VersionCtl.OP_RESET%>">
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