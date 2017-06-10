<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Sakuramoe">
<meta name="author" content="Wenjie Zhang">
<meta name="keyword" content="Sakuramoe">
<!-- <link rel="shortcut icon" href="assets/ico/favicon.png"> -->

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
			<div class="col-md-8">
				<div class="card-group mb-0">
					<div class="card p-4">
						<div class="card-block">
							<form method="post" action="action_login.jsp">
								<h1>Login</h1>
								<p class="text-muted">Sign In to your account</p>
								<div class="input-group mb-3">
									<span class="input-group-addon"><i class="icon-user"></i>
									</span> <input type="text" name="username" class="form-control"
										placeholder="Username">
								</div>
								<div class="input-group mb-4">
									<span class="input-group-addon"><i class="icon-lock"></i>
									</span> <input type="password" name="password" class="form-control"
										placeholder="Password">
								</div>
								<div class="row">
									<div class="col-12">
										Remember-me:&nbsp;&nbsp;&nbsp; <label
											class="switch switch-default switch-primary"> <input
											type="checkbox" class="switch-input" checked=""> <span
											class="switch-label"></span> <span class="switch-handle"></span>
										</label>
									</div>
								</div>
								<div class="row">
									<div class="col-6">
										<button type="submit" class="btn btn-primary px-4">Login</button>
									</div>
									<div class="col-6 text-right">
										<button type="button" class="btn btn-link px-0">Forgot
											password?</button>
									</div>
								</div>
							</form>
						</div>
					</div>
					<div class="card card-inverse card-primary py-5 d-md-down-none"
						style="width: 44%">
						<div class="card-block text-center">
							<div>
								<h2>Sign up</h2>
								<p>New user? Sign up to create your account.</p>
								<a href="register.jsp">
									<button type="button" class="btn btn-primary active mt-3">Register
										Now!</button>
								</a>
							</div>
						</div>
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