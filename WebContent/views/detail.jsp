<%@page import="sakuramoe.User"%>
<%@page import="sakuramoe.UserInfo"%>
<%@page import="sakuramoe.Util"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.List"%>
<%@page import="sakuramoe.Post.PostInfo"%>
<%@page import="sakuramoe.Post.CommentTreeNode"%>
<%@page import="sakuramoe.Post"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>

<%
	if (session.getAttribute("user") == null) {
		session.setAttribute("user", new User());
	}
	User user = (User) session.getAttribute("user");

	int postId = -1;
	try {
		postId = Integer.parseInt(request.getParameter("postId"));
	} catch (Exception e) {
		response.sendRedirect(".");
		return;
	}

	Post post = new Post(postId);
	PostInfo info = post.getPostInfo();
	List<CommentTreeNode> comments = Post.parseCommentTree(post.getComments(), info.userDesc);
	if (info == null) {
		response.sendRedirect(".");
		return;
	}
%>


<ol class="breadcrumb">
	<li class="breadcrumb-item">Friends</li>
	<li class="breadcrumb-item">
		<%
			out.print(info.userDesc);
		%>
	</li>
	<li class="breadcrumb-item active">Post Detial</li>
</ol>
<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<div class="card-header">
						<div class="float-left">
							<img src="<%out.print(info.userAvatar); %>" height="50em" class="img-avatar"
								alt="admin@bootstrapmaster.com">
						</div>
						<div class="float-left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<div class="float-left">
							<b> <%
 	out.print(info.userDesc);
 %>
							</b><br />
							<%
								out.print(info.timePosted.toString());
							%>
						</div>
					</div>
					<div class="card-block">
						<%
							out.print(info.content);
						%>
					</div>
					<div class="card-footer">
						<div class="row">
							<div class="col-sm-auto">
								<button type="button" class="btn btn-success btn-post-like"
									postid="<%out.print(postId);%>">
									<i class="icon-like"></i>&nbsp;Like(<span liked="<%out.print(post.like(user) ? "1" : "0");%>"><%out.print(post.getNumberLike());%></span>)
								</button>
							</div>
							<div class="col-sm-6">
								<div class="controls">
									<div class="input-group">
										<input id="appendedInputButton" class="form-control" size="16"
											type="text"> <span class="input-group-btn">
											<button class="btn btn-primary btn-post-comment"
												type="button" postid="<%out.print(postId);%>" parentid="-1">Reply</button>
										</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<%
						if (comments.size() != 0) {
					%>
					<div class="card-block">
						<%
							for (CommentTreeNode node : comments) {
						%>
						<div style="margin-left: <%out.print(node.depth*2);%>em;">
							<div class="row">
								<div class="col-12">
									<div class="float-left">
										<img src="<%out.print(node.userAvatar);%>" height="25em"
											class="img-avatar">
									</div>
									<div class="float-left">&nbsp;&nbsp;&nbsp;</div>
									<div class="float-left">
										<b> <%
 	out.print(node.userDesc);
 %>
										</b>
										<%
											out.print(node.timePosted);
										%>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									<p>
										<a href="#">Reply <%
											out.print(node.parentDesc);
										%>: &nbsp;
										</a>
										<%
											out.print(Util.htmlEncode(node.content));
										%>
									</p>
									<div class="controls">
										<div class="input-group">
											<input id="appendedInputButton" class="form-control"
												size="16" type="text"> <span class="input-group-btn">
												<button class="btn btn-primary btn-post-comment" type="button"
												postid="<%out.print(postId);%>" parentid="<%out.print(node.commentId);%>">Reply
												</button>
											</span>
										</div>
									</div>
								</div>
							</div>
						</div>
						<hr />
						<%
							}
						%>
					</div>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript" src="js/comment.js"></script>