<%@page import="sakuramoe.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	if (session.getAttribute("user") == null) {
		session.setAttribute("user", new User());
	}
	User user = (User) session.getAttribute("user");

	String password = request.getParameter("password");
	if (user.deleteUser(password)) {
		session.invalidate();
		response.sendRedirect("/");
	}
%>