<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String output = null;
	if (request.getParameter("callback") != null)
		output = request.getParameter("callback") + "("
				+ "{\"status\":\"KO\", \"result\":"
				+ request.getAttribute("javax.servlet.error.message")
				+ "}"

				+ ")";
	else
		output = "{\"status\":\"KO\", \"result\":"
				+ request.getAttribute("javax.servlet.error.message")
				+ "}";
%>
<%=output%>
