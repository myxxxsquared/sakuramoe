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
		int postId = Integer.parseInt(request.getParameter("postId"));
		int parentId = Integer.parseInt(request.getParameter("parentId"));
		String content = (request.getParameter("content"));
		 
		new Post(postId).createComment(user, parentId, content);
	}
%>