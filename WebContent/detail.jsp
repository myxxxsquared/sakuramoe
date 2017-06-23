<%@page import="java.util.Date"%>
<%@page import="sakuramoe.PostInfo"%>
<%@page import="sakuramoe.Post"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>
<%@include file="include_header.jsp"%>

<%
	int postId = -1;
	try {
		postId = Integer.parseInt(request.getParameter("postId"));
	} catch (Exception e) {
		response.sendRedirect(".");
		return;
	}

	Post post = new Post(postId);
	PostInfo info = post.getPostInfo();
	if (null == info) {
		response.sendRedirect(".");
		return;
	}
	UserInfo postUserInfo = new UserInfo(user);
%>

<main class="main">
<ol class="breadcrumb">
	<li class="breadcrumb-item">Friends</li>
	<li class="breadcrumb-item"><%out.print(postUserInfo.getUserDesc()); %></li>
	<li class="breadcrumb-item active">Post Detial</li>
</ol>
<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<div class="card-header">
						<div class="float-left">
							<img src="img/avatars/6.jpg" height="50em" class="img-avatar"
								alt="admin@bootstrapmaster.com">
						</div>
						<div class="float-left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<div class="float-left">
							<b><%out.print(postUserInfo.getUserDesc()); %></b><br /> <%out.print(info.timePosted.toString()); %>
						</div>
					</div>
					<div class="card-block"><%out.print(info.content); %></div>
					<div class="card-footer">
						<div class="row">
							<div class="col-sm-auto">
								<button type="button" class="btn btn-success">
									<i class="icon-action-redo"></i>&nbsp;Forward
								</button>
							</div>
							<div class="col-sm-auto">
								<button type="button" class="btn btn-success">
									<i class="icon-like"></i>&nbsp;Like
								</button>
							</div>
							<div class="col-sm-6">
								<div class="controls">
									<div class="input-group">
										<input id="appendedInputButton" class="form-control" size="16"
											type="text"> <span class="input-group-btn">
											<button class="btn btn-primary" type="button">Reply
											</button>
										</span>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="card-block">
						<div>
							<div class="row">
								<div class="col-12">
									<div class="float-left">
										<img src="img/avatars/6.jpg" height="25em" class="img-avatar"
											alt="admin@bootstrapmaster.com">
									</div>
									<div class="float-left">&nbsp;&nbsp;&nbsp;</div>
									<div class="float-left">
										<b> Admin </b> 1970-01-01 00:00
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-12">
									Lorem ipsum dolor sit amet, consectetuer adipiscing elit, sed
									diam nonummy nibh euismod tincidunt ut laoreet dolore magna
									aliquam erat volutpat. Ut wisi enim ad minim veniam, quis
									nostrud exerci tation ullamcorper suscipit lobortis nisl ut
									aliquip ex ea commodo consequat.
									<hr />
									<div class="controls">
										<div class="input-group">
											<input id="appendedInputButton" class="form-control"
												size="16" type="text"> <span class="input-group-btn">
												<button class="btn btn-primary" type="button">Reply
												</button>
											</span>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</main>
<%@include file="include_footer.jsp"%>
