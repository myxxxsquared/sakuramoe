<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>
<%@include file="include_header.jsp"%>
<main class="main">
<ol class="breadcrumb">
	<li class="breadcrumb-item">Home</li>
	<li class="breadcrumb-item active">Friends</li>
</ol>
<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<form action="" method="post" enctype="multipart/form-data"
						class="form-horizontal">
						<div class="card-header">Add a friend</div>
						<div class="card-block">
							<div class="input-group">
								<input id="appendedInputButton" class="form-control" size="16"
									type="text" placeholder="User Name or Email Address"> <span
									class="input-group-btn">
									<button class="btn btn-primary" type="button">Add</button>
								</span>
							</div>
						</div>

					</form>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-4 col-sm-6">
				<div class="card card-inverse card-primary">
					<div class="card-block">
						<div class="row">
							<div class="col-sm-auto">
								<img src="img/avatars/6.jpg" height="50em" class="img-avatar"
									alt="admin@bootstrapmaster.com">
							</div>
							<div class="col-sm-auto">
								<strong>Admin</strong><br /> 这里是描述
							</div>
						</div>
						<hr />
						<div class="row">
							<div class="col-12">Femail</div>
						</div>
						<div class="row">
							<div class="col-12">Birthday : 1970-01-01</div>
						</div>
						<div class="row">
							<div class="col-12">Introduction : Here is my introduction.
								Here is my introduction. Here is my introduction. Here is my
								introduction. Here is my introduction. Here is my introduction.
								Here is my introduction. Here is my introduction. Here is my
								introduction. Here is my introduction. Here is my introduction.
								Here is my introduction. Here is my introduction. Here is my
								introduction. Here is my introduction. Here is my introduction.
								Here is my introduction. Here is my introduction. Here is my
								introduction. Here is my introduction. Here is my introduction.
								Here is my introduction. Here is my introduction. Here is my
								introduction.</div>
						</div>
					</div>
					<div class="card-footer">
						<div class="row">
							<div class="col-sm-auto">
								<button type="button" class="btn btn-success">Home</button>
							</div>
							<div class="col-sm-auto">
								<button type="button" class="btn btn-danger">Remove</button>
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