<%@page import="sakuramoe.Post"%>
<%@page import="sakuramoe.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	int postId = Integer.parseInt(request.getParameter("postId"));
	if (session.getAttribute("user") == null) {
		session.setAttribute("user", new User());
	}
	User user = (User) session.getAttribute("user");
	if (!user.isLogin()) {
		response.sendError(500);
		return;
	}
	
	Post p = new Post(postId);
	if(p.like(user))
		p.setLike(user, false);
	else
		p.setLike(user, true);
%>