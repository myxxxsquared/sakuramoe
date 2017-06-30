<%@page import="sakuramoe.Post"%>
<%@page import="sakuramoe.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	request.setCharacterEncoding("utf-8");

	if (session.getAttribute("user") == null) {
		session.setAttribute("user", new User());
	}
	User user = (User) session.getAttribute("user");
	if (!user.isLogin()) {
		response.sendRedirect("login.jsp");
	} else {
		String content = request.getParameter("post-content");
		if (content != null && content.length() > 0) {
			Post.createPost(user, content);
		}
	}
%>