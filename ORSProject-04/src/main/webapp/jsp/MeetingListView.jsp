<%@page import="in.co.rays.proj4.controller.MeetingListCtl"%>
<%@page import="in.co.rays.proj4.controller.GamingListCtl"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.bean.MeetingBean"%>

<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Meeting List</title>
</head>
<body>

	<%@include file="ModuleHeader.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.MeetingBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">Meeting
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.MEETING_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<MeetingBean> list = (List<MeetingBean>) ServletUtility.getList(request);
			Iterator<MeetingBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> 
			<input type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Host Name : </b></label> <input
						type="text" name="hostName"
						value="<%=ServletUtility.getParameter("hostName", request)%>">&emsp;

						<label><b>Platform : </b></label> <input type="text"
						name="platform"
						value="<%=ServletUtility.getParameter("platform", request)%>">&emsp;
						
 						  <input type="submit" name="operation" value="<%=MeetingListCtl.OP_SEARCH%>"> &nbsp;
						 <input type="submit" name="operation" value="<%=MeetingListCtl.OP_RESET%>"></td>
				</tr>
			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S. No.</th>
					<th width="15%">Host Name</th>
					<th width="20%">Platform</th>
					<th width="20%">Duration</th>		
					<th width="15%">Participants</th>
					<th width="10%">Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					MeetingBean v = it.next();
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=v.getId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center;"><%=v.getHostName()%></td>
					<td style="text-align: center;"><%=v.getPlatform()%></td>
					<td style="text-align: center;"><%=v.getDuration()%></td>
					<td style="text-align: center;"><%=v.getParticipants()%></td>
					<td style="text-align: center;"><a
						href="MeetingCtl?id=<%=v.getId()%>">Edit</a></td>
				</tr>

				<%
				}
				%>

			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=MeetingListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=MeetingListCtl.OP_NEW%>"></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=MeetingListCtl.OP_DELETE%>"></td>

					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=MeetingListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
			} else {
			%>

			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=MeetingListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
			}
			%>

		</form>
	</div>

</body>
</html>