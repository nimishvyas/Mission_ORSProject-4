<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.RevenueCtl"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Revenue View</title>
</head>
<body>

<form action="<%=ORSView.REVENUE_CTL%>" method="post">

	<%@ include file="Header.jsp" %>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.RevenueBean" scope="request"></jsp:useBean>

	<div align="center">

		<h1 style="color: navy">
			<%
				if (bean != null && bean.getExpenseId() > 0) {
			%>
				Update
			<%
				} else {
			%>
				Add
			<%
				}
			%>
			Revenue
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
				<th align="left">Expense Code<span style="color:red">*</span></th>
				<td>
					<input type="text" name="expenseCode"
						placeholder="Enter Expense Code"
						value="<%=DataUtility.getStringData(bean.getExpenseCode())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("expenseCode", request)%>
					</font>
				</td>
			</tr>

			
			<tr>
				<th align="left">Amount<span style="color:red">*</span></th>
				<td>
					<input type="text" name="amount"
						placeholder="Enter Amount"
						value="<%=DataUtility.getStringData(bean.getAmount())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("amount", request)%>
					</font>
				</td>
			</tr>

			
			<tr>
				<th align="left">Category<span style="color:red">*</span></th>
				<td>
					<input type="text" name="category"
						placeholder="Enter Category"
						value="<%=DataUtility.getStringData(bean.getCategory())%>">
				</td>
				<td style="position: fixed;">
					<font color="red">
						<%=ServletUtility.getErrorMessage("category", request)%>
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

			
			<tr>
				<th></th>
				<td>
					<%
						if (bean != null && bean.getExpenseId() > 0) {
					%>
						<input type="submit" name="operation" value="<%=RevenueCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=RevenueCtl.OP_CANCEL%>">
					<%
						} else {
					%>
						<input type="submit" name="operation" value="<%=RevenueCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=RevenueCtl.OP_RESET%>">
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