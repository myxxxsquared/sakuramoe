<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>

<%@include file="include_header.jsp"%>

<main class="main">
<ol class="breadcrumb">
	<li class="breadcrumb-item">Home</li>
	<li class="breadcrumb-item">Settings</li>
	<li class="breadcrumb-item active">Delete user</li>
</ol>

<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<form action="" method="post" enctype="multipart/form-data"
						class="form-horizontal">
						<div class="card-header">Delete User</div>
						<div class="card-block">
							<div class="form-group row">
								<div class="col-12">To continue, first verify it's you</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="text-input">Email
								</label>
								<div class="col-md-9">
									<input type="email" id="text-input" name="text-input"
										class="form-control">
								</div>
							</div>
						</div>
						<div class="card-footer">
							<button type="submit" class="btn btn-sm btn-primary">
								<i class="fa fa-dot-circle-o"></i> Submit
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</main>

<%@include file="include_footer.jsp"%>