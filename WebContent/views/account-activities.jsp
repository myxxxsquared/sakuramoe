﻿<%@page import="sakuramoe.UserInfo"%>
<%@page import="sakuramoe.User"%>
<%@page import="sakuramoe.OperationInfo"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>

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
%>


<ol class="breadcrumb">
	<li class="breadcrumb-item">Home</li>
	<li class="breadcrumb-item">Settings</li>
	<li class="breadcrumb-item active">Account activities</li>
</ol>

<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<div class="col-lg-12">
				<div class="card">
					<div class="card-header">
						<i class="fa fa-align-justify"></i> Recent Activities
					</div>
					<div class="card-block">
						<table class="table table-bordered table-striped table-sm">
							<thead>
								<tr>
									<th>ID</th>
									<th>Time</th>
									<th>Type</th>
									<th>Result</th>
									<th>IP</th>
								</tr>
							</thead>

							<tbody>
								<%
									OperationInfo[] infos = user.getOperations();
									for (OperationInfo i : infos)
										out.print(String.format("<tr><td>%d</td><td>%s</td><td>%s</td><td>%s</td><td>%s</td></tr>", i.id,
												i.time, i.operationType, i.operationResult, i.ip));
								%>
							</tbody>
						</table>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>
