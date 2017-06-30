<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>

<ol class="breadcrumb">
	<li class="breadcrumb-item">Home</li>
	<li class="breadcrumb-item">Settings</li>
	<li class="breadcrumb-item active">Change Password</li>
</ol>
<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<form action="" method="post" enctype="multipart/form-data"
						class="form-horizontal">
						<div class="card-header">Change Password</div>
						<div class="card-block">
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="text-input">Old Password
								</label>
								<div class="col-md-9">
									<input type="password" name="oldpass"
										class="form-control">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="text-input">New Password
								</label>
								<div class="col-md-9">
									<input type="password" name="newpass"
										class="form-control">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="text-input">Confirm Password
								</label>
								<div class="col-md-9">
									<input type="password" name="newpass2"
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
