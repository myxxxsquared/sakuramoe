<%@page import="sakuramoe.Post.PostInfo"%>
<%@page import="java.util.List"%>
<%@page import="sakuramoe.Post"%>
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

	List<Post> posts = Post.getNews(user.getUserId());
%>

<ol class="breadcrumb">
	<li class="breadcrumb-item">Home</li>
	<li class="breadcrumb-item active">News</li>
</ol>
<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<%
				for (Post p : posts) {
					PostInfo pi = p.getPostInfo();
			%>
			<div class="col-md-6">
				<div class="card">
					<div class="card-header">
						<div class="float-left">
							<img src="<%out.print(pi.userAvatar); %>" height="50em" class="img-avatar">
						</div>
						<div class="float-left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<div class="float-left">
							<b><%out.print(pi.userDesc); %></b><br /> <%out.print(pi.timePosted); %>
						</div>
					</div>
					<div class="card-block"><%out.print(pi.content); %></div>
					<div class="card-footer">
						<button type="button" class="btn btn-success btn-post-forward"
							postid="<%out.print(pi.postID); %>">
							<i class="icon-action-redo"></i>&nbsp;Forward
						</button>
						<button type="button" class="btn btn-success btn-post-like"
							postid="<%out.print(pi.postID); %>">
							<i class="icon-like"></i>&nbsp;Like(<span liked="<%out.print(p.like(user) ? "1" : "0");%>"><%out.print(p.getNumberLike());%></span>)
						</button>
						<button type="button" class="btn btn-success btn-post-more"
							postid="<%out.print(pi.postID); %>">
							<i class="icon-list"></i>&nbsp;More
						</button>
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