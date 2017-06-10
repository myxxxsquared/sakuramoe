<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>

<%
session.invalidate();
response.sendRedirect("/");
%>