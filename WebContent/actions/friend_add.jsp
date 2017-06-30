<%@page import="sakuramoe.UserInfo"%>
<%@page import="sakuramoe.Friends"%>
<%@page import="sakuramoe.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	if (session.getAttribute("user") == null) {
		session.setAttribute("user", new User());
	}
	User user = (User) session.getAttribute("user");
	if (!user.isLogin()) {
		response.sendRedirect("login.jsp");
	} else {
		String frinedid = request.getParameter("friend_id");
		if(frinedid == null || frinedid.length() == 0)
			return;
		Friends.addFriend(user, UserInfo.getUserIdByName(frinedid));
	}
%>