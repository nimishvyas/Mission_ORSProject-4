<%@page import="in.co.rays.proj4.controller.CloudListCtl"%>
<%@page import="in.co.rays.proj4.bean.CloudBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.bean.EventBean"%>
<%@page import="in.co.rays.proj4.controller.EventListCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>File List</title>
</head>
<body>

	<%@include file="ModuleHeader.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.CloudBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">File
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.CLOUD_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<CloudBean> list = (List<CloudBean>) ServletUtility.getList(request);
			Iterator<CloudBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>File Name : </b></label> <input
						type="text" name="fileName"
						value="<%=ServletUtility.getParameter("fileName", request)%>">&emsp;

						<label><b>User Name : </b></label> <input type="text"
						name="userName"
						value="<%=ServletUtility.getParameter("userName", request)%>">&emsp;
						<input type="submit" name="operation"
						value="<%=CloudListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=CloudListCtl.OP_RESET%>">

					</td>
				</tr>
			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S. No.</th>
					<th width="15%">File Name</th>
					<th width="20%">File Size</th>
					<th width="20%">Upload Date</th>
					<th width="15%">User Name</th>
					<th width="10%">Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					CloudBean e = it.next();

					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String date = (e.getUploadDate() != null) ? sdf.format(e.getUploadDate()) : "";
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=e.getFileId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center;"><%=e.getFileName()%></td>
					<td style="text-align: center;"><%=e.getFileSize()%></td>
					<td style="text-align: center;"><%=date%></td>
					<td style="text-align: center;"><%=e.getUserName()%></td>
					<td style="text-align: center;"><a
						href="CloudCtl?id=<%=e.getFileId()%>">Edit</a></td>
				</tr>

				<%
				}
				%>

			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=CloudListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=CloudListCtl.OP_NEW%>"></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=CloudListCtl.OP_DELETE%>"></td>

					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=CloudListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
			} else {
			%>

			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=CloudListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
			}
			%>

		</form>
	</div>

</body>
</html>