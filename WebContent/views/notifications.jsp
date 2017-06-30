<%@page import="sakuramoe.Cites"%>
<%@page import="sakuramoe.UserInfo"%>
<%@page import="sakuramoe.User"%>
<%@ page contentType="text/html; charset=utf-8" language="java"%>

<%
	if (session.getAttribute("user") == null) {
		session.setAttribute("user", new User());
	}
	User user = (User) session.getAttribute("user");
	if (!user.isLogin()) {
		response.sendRedirect("login.jsp");
		return;
	}
%>

<ol class="breadcrumb">
	<li class="breadcrumb-item">Home</li>
	<li class="breadcrumb-item active">Friends</li>
</ol>
<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<%
				Cites[] cites = Cites.getCites(user);
				for (Cites cite : cites) {
					UserInfo info = new UserInfo(cite.citeId);
			%>
			<div class="col-sm-4 col-sm-6">
				<div class="card">
					<div class="card-block">
						<div class="row">
							<div class="col-sm-auto">
								<img src="<%out.print(info.getAvatar());%>" height="50em"
									class="img-avatar" alt="admin@bootstrapmaster.com">
							</div>
							<div class="col-sm-auto">
								<strong> <%
 	out.print(info.getUserDesc());
 %>
								</strong>
							</div>
						</div>
					</div>
					<div class="card-footer">
						<div class="row">
							<div class="col-sm-auto">
								<button type="button" class="btn btn-success btn-friends-accept"
									userid="<%out.print(info.userId);%>">Accept</button>
							</div>
						</div>
					</div>
				</div>
			</div>
			<%
				}
			%>
		</div>
	</div>
</div>
<script type="text/javascript" src="js/comment.js"></script>