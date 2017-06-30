<%@page import="sakuramoe.Util"%>
<%@page import="sakuramoe.User"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>

<%
	String ip = Util.getRemortIP(request);
	try {
		User user = (User) session.getAttribute("user");
		user.logout(ip);
	} catch (Exception e) {
	}
	session.invalidate();
	
	Cookie cookie1 = new Cookie("autologin_userskey", "");
	cookie1.setPath("/");
	Cookie cookie2 = new Cookie("autologin_userid", "");
	cookie2.setPath("/");
	response.addCookie(cookie1);
	response.addCookie(cookie2);
%>

<script type="text/javascript">
	$(document).ready(function(){location.replace('.');});
</script>