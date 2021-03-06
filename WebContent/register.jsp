<%@page import="sakuramoe.Util"%>
<%@page import="sakuramoe.OperationResult"%>
<%@page import="sakuramoe.Util.CookieReader"%>
<%@page import="sakuramoe.User"%>
<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>

<%
	boolean jump_to_home = false;
	String alert = null;
	String ip = Util.getRemortIP(request);

	if (session.getAttribute("user") == null) {
		session.setAttribute("user", new User());
	}
	User user = (User) session.getAttribute("user");
	if (user.isLogin()) {
		jump_to_home = true;
	} else {
		if (request.getMethod().equals("POST")) {
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String password2 = request.getParameter("password2");
			String email = request.getParameter("email");

			if (username != null && password != null && password2 != null && email != null)
				if (password.equals(password2)) {
					OperationResult regresult = User.register(username, email, password, ip);
					if (regresult.success) {
						if (user.login(username, password, ip).success)
							jump_to_home = true;
					} else
						alert = regresult.reason;
				} else {
					alert = "Difference between two passwords";
				}
		}
	}
%>

<!DOCTYPE html>

<%
	if (jump_to_home) {
%>
<html lang="en">
<head>
<meta http-equiv="refresh" content="0;url=.">
</head>
</html>
<%
	} else {
%>

<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Sakuramoe">
<meta name="author" content="Wenjie Zhang">
<meta name="keyword" content="Sakuramoe">
<link rel="shortcut icon" href="assets/ico/favicon.png">

<title>Sakuramoe</title>

<!-- Icons -->
<link href="css/font-awesome.min.css" rel="stylesheet">
<link href="css/simple-line-icons.css" rel="stylesheet">

<!-- Main styles for this application -->
<link href="css/style.css" rel="stylesheet">

</head>

<body class="app flex-row align-items-center">
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-6">
				<div class="card mx-4">
					<div class="card-block p-4">
						<form method="post" action="#">
							<h1>Register</h1>
							<p class="text-muted">Create your account</p>
							<div class="input-group mb-3">
								<span class="input-group-addon"><i class="icon-user"></i>
								</span> <input type="text" name="username" class="form-control"
									placeholder="Username">
							</div>

							<div class="input-group mb-3">
								<span class="input-group-addon">@</span> <input type="text"
									name="email" class="form-control" placeholder="Email">
							</div>

							<div class="input-group mb-3">
								<span class="input-group-addon"><i class="icon-lock"></i>
								</span> <input type="password" name="password" class="form-control"
									placeholder="Password">
							</div>

							<div class="input-group mb-4">
								<span class="input-group-addon"><i class="icon-lock"></i>
								</span> <input type="password" name="password2" class="form-control"
									placeholder="Repeat password">
							</div>

							<%
								if (alert != null) {
							%>
							<div class="row">
								<div class="col-12">
									<div class="alert alert-danger" role="alert">
										<%
											out.print(alert);
										%>
									</div>
								</div>
							</div>
							<%
								}
							%>

							<button type="submit" class="btn btn-block btn-success">Create
								Account</button>

							<a href="login.jsp"><button type="button"
									class="btn btn-link px-0">Already have an account?
									Sign in?</button></a>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- Bootstrap and necessary plugins -->
	<script src="bower_components/jquery/dist/jquery.min.js"></script>
	<script src="bower_components/tether/dist/js/tether.min.js"></script>
	<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>


</body>

</html>
<%
	}
%>