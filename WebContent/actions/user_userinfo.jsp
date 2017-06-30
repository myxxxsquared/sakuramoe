<%@page import="sakuramoe.UserInfo"%>
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
		return;
	}

	UserInfo userinfo = new UserInfo(user);

	if (request.getMethod().equals("POST")) {
		String nickname = request.getParameter("nickname");
		String gender = request.getParameter("gender");
		String birthday = request.getParameter("birthday");
		String intro = request.getParameter("intro");

		if (nickname != null)
			userinfo.setNickName(nickname);
		if (gender != null)
			try {
				userinfo.setGender(Integer.parseInt(gender));
			} catch (Exception e) {
			}
		if (birthday != null)
			userinfo.setBirthday(birthday);
		if (intro != null)
			userinfo.setIntroduction(intro);
	}
	response.sendRedirect("../");
%>
