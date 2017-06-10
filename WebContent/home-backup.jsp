<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>

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

<body
	class="app header-fixed sidebar-fixed aside-menu-fixed aside-menu-hidden">
	<header class="app-header navbar">
		<button class="navbar-toggler mobile-sidebar-toggler d-lg-none"
			type="button">☰</button>
		<a class="navbar-brand" href="#"></a>
		<ul class="nav navbar-nav d-md-down-none">
			<li class="nav-item"><a
				class="nav-link navbar-toggler sidebar-toggler" href="#">☰</a></li>
		</ul>
		<ul class="nav navbar-nav ml-auto">
			<li class="nav-item d-md-down-none"><a class="nav-link" href="#"><i
					class="icon-bell"></i><span class="badge badge-pill badge-danger">5</span></a>
			</li>
			<li class="nav-item dropdown"><a
				class="nav-link dropdown-toggle nav-link" data-toggle="dropdown"
				href="#" role="button" aria-haspopup="true" aria-expanded="false">
					<img src="img/avatars/6.jpg" class="img-avatar"
					alt="admin@bootstrapmaster.com"> <span class="d-md-down-none">admin</span>
			</a>
				<div class="dropdown-menu dropdown-menu-right">
					<div class="dropdown-header text-center">
						<strong>Account</strong>
					</div>

					<a class="dropdown-item" href="#"><i class="fa fa-bell-o"></i>
						Account activities</a> <a class="dropdown-item" href="#"><i
						class="fa fa-exchange"></i> Change password</a> <a
						class="dropdown-item" href="#"><i class="fa fa-remove"></i>
						Delete user</a> <a class="dropdown-item" href="#"><i
						class="fa fa-lock"></i> Logout</a>

				</div></li>
			<li class="nav-item d-md-down-none"></li>
		</ul>
	</header>

	<div class="app-body">
		<div class="sidebar">
			<nav class="sidebar-nav">
				<ul class="nav">
					<li class="nav-item"><a class="nav-link" href="home.jsp"><i
							class="icon-home"></i> Home <span class="badge badge-info"></span></a>
					</li>
					<li class="nav-item"><a class="nav-link" href="/"><i
							class="icon-plus"></i> Create a post <span
							class="badge badge-info"></span></a></li>
					<li class="nav-item"><a class="nav-link" href="/"><i
							class="icon-people"></i> Friends <span class="badge badge-info"></span></a>
					</li>
					<li class="nav-item"><a class="nav-link" href="widgets.html"><i
							class="icon-book-open"></i> News</a></li>
					<li class="nav-item"><a class="nav-link" href="charts.html"><i
							class="icon-list"></i> Profile</a></li>
					<li class="nav-item"><a class="nav-link" href="charts.html"><i
							class="icon-settings"></i> Settings</a></li>
					<li class="divider"></li>
				</ul>
			</nav>
		</div>

		<!-- Main content -->
		<main class="main"> <!-- Breadcrumb -->
		<ol class="breadcrumb">
			<li class="breadcrumb-item">Home</li>
			<li class="breadcrumb-item"><a href="#">Admin</a></li>
			<li class="breadcrumb-item active">Dashboard</li>
		</ol>

		<div class="container-fluid">
			<div class="animated fadeIn">
				<!--/.row-->
			</div>
		</div>
		<!-- /.conainer-fluid --> </main>

	</div>

	<footer class="app-footer">
		Sakuramoe 数据库概论大作业 张文杰 <span class="float-right">Powered by <a
			href="http://coreui.io">CoreUI</a>
		</span>
	</footer>

	<script src="bower_components/jquery/dist/jquery.min.js"></script>
	<script src="bower_components/tether/dist/js/tether.min.js"></script>
	<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
	<script src="bower_components/pace/pace.min.js"></script>
	<script src="bower_components/chart.js/dist/Chart.min.js"></script>
	<script src="js/app.js"></script>
	<script src="js/views/main.js"></script>

</body>

</html>