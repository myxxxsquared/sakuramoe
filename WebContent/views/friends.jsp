<%@page import="sakuramoe.UserInfo"%>
<%@page import="sakuramoe.User"%>
<%@page import="sakuramoe.Friends"%>
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
			<div class="col-12">
				<div class="card">
					<div class="form-horizontal">
						<div class="card-header">Add a friend</div>
						<div class="card-block">
							<div class="input-group">
								<input class="form-control" size="16" type="text"
									placeholder="User Name or Email Address" id="friend_add_id">
								<span class="input-group-btn">
									<button class="btn btn-primary" id="friend_add_button"
										type="button">Add</button>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<%
				Friends[] friends = Friends.getFriends(user.getUserId());
				for (Friends friend : friends) {
					UserInfo info = new UserInfo(friend.friendId);
			%>
			<div class="col-sm-4 col-sm-6">
				<div class="card card-inverse card-primary">
					<div class="card-block">
						<div class="row">
							<div class="col-sm-auto">
								<img src="img/avatars/6.jpg" height="50em" class="img-avatar"
									alt="admin@bootstrapmaster.com">
							</div>
							<div class="col-sm-auto">
								<strong> <%
 	out.print(info.getUserDesc());
 %>
								</strong>
							</div>
						</div>
						<hr />
						<div class="row">
							<div class="col-12">
								<%
									out.print(info.getGender());
								%>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								Birthday :
								<%
								out.print(info.getBirthday());
							%>
							</div>
						</div>
						<div class="row">
							<div class="col-12">
								Introduction :
								<%
								out.print(info.getIntroduction());
							%>
							</div>
						</div>
					</div>
					<div class="card-footer">
						<div class="row">
							<div class="col-sm-auto">
								<button type="button" class="btn btn-success btn-friends-home"
									userid="<%out.print(info.userId);%>">Home</button>
							</div>
							<div class="col-sm-auto">
								<button type="button" class="btn btn-danger btn-friends-remove"
									userid="<%out.print(info.userId);%>">Remove</button>
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