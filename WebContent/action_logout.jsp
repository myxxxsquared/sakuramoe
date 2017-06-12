<%@page import="sakuramoe.Tools"%>
<%@page import="sakuramoe.User"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>

<%
String ip = Tools.getRemortIP(request);
	try {
		User user = (User) session.getAttribute("user");
		user.logout(ip);
	} catch (Exception e) {
	}
	session.invalidate();
	response.addCookie(new Cookie("autologin_userskey",""));
	response.addCookie(new Cookie("autologin_userid",""));
	response.sendRedirect("/");
%>