<%@page import="org.json.JSONObject"%>
<%@page import="sakuramoe.UserInfo"%>
<%@page import="sakuramoe.Friends"%>
<%@page import="sakuramoe.User"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<%
	User user = User.getSessionUser(session);

	if (!user.isLogin()) {
		return;
	} else {
		String frinedid = request.getParameter("friendId");
		if (frinedid == null || frinedid.length() == 0)
			return;
		Friends.acceptFriend(user, Integer.parseInt(frinedid));
	}
%>