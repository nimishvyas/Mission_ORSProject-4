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
<title>Event List</title>
</head>
<body>

	<%@include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.EventBean"
		scope="request"></jsp:useBean>

	<div align="center">

		<h1 align="center" style="margin-bottom: -15; color: navy;">Event
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.EVENT_LIST_CTL%>" method="post">

			<%
			int pageNo = ServletUtility.getPageNo(request);
			int pageSize = ServletUtility.getPageSize(request);
			int index = ((pageNo - 1) * pageSize) + 1;
			int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

			List<EventBean> list = (List<EventBean>) ServletUtility.getList(request);
			Iterator<EventBean> it = list.iterator();

			if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Event Name : </b></label> <input
						type="text" name="eventName"
						value="<%=ServletUtility.getParameter("eventName", request)%>">&emsp;

						<label><b>Event Code : </b></label> <input type="text"
						name="eventCode"
						value="<%=ServletUtility.getParameter("eventCode", request)%>">&emsp;

						<label><b>Status : </b></label> <%
 HashMap<String, String> map = new HashMap<String, String>();
 map.put("Active", "Active");
 map.put("Inactive", "Inactive");

 String htmlList = HTMLUtility.getList("status", ServletUtility.getParameter("status", request), map);
 %> <%=htmlList%>&emsp; <input type="submit" name="operation"
						value="<%=EventListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation" value="<%=EventListCtl.OP_RESET%>">

					</td>
				</tr>
			</table>

			<br>

			<table border="1" style="width: 100%; border: groove;">
				<tr style="background-color: #e1e6f1e3;">
					<th width="5%"><input type="checkbox" id="selectall" /></th>
					<th width="5%">S. No.</th>
					<th width="15%">Event Code</th>
					<th width="20%">Event Name</th>
					<th width="20%">Event Date</th>
					<th width="15%">Status</th>
					<th width="10%">Edit</th>
				</tr>

				<%
				while (it.hasNext()) {
					EventBean e = it.next();

					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String date = (e.getEventTime() != null) ? sdf.format(e.getEventTime()) : "";
				%>

				<tr>
					<td style="text-align: center;"><input type="checkbox"
						class="case" name="ids" value="<%=e.getEventId()%>"></td>
					<td style="text-align: center;"><%=index++%></td>
					<td style="text-align: center;"><%=e.getEventCode()%></td>
					<td style="text-align: center;"><%=e.getEventName()%></td>
					<td style="text-align: center;"><%=date%></td>
					<td style="text-align: center;"><%=e.getStatus()%></td>
					<td style="text-align: center;"><a
						href="EventCtl?id=<%=e.getEventId()%>">Edit</a></td>
				</tr>

				<%
				}
				%>

			</table>

			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=EventListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=EventListCtl.OP_NEW%>"></td>

					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=EventListCtl.OP_DELETE%>"></td>

					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=EventListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
			} else {
			%>

			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=EventListCtl.OP_BACK%>"></td>
				</tr>
			</table>

			<%
			}
			%>

		</form>
	</div>

</body>
</html>