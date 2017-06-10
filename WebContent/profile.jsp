<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>
<%@include file="include_header.jsp"%>
<main class="main">
<ol class="breadcrumb">
	<li class="breadcrumb-item">Home</li>
	<li class="breadcrumb-item active">Profile</li>
</ol>
<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<div class="card-header">Profile</div>
					<div class="card-block">
						<form action="" method="post" enctype="multipart/form-data"
							class="form-horizontal">
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="text-input">Profile
									Picture </label>
								<div class="col-md-9">
									<img src="img/avatars/6.jpg" height="50em" class="img-avatar"
										alt="admin@bootstrapmaster.com">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-secondary">Update</button>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="text-input">Nick
									Name</label>
								<div class="col-md-9">
									<input type="text" id="text-input" name="text-input"
										class="form-control" placeholder="Text">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="select">Gender</label>
								<div class="col-md-9">
									<select id="select" name="select" class="form-control">
										<option value="0">Please select</option>
										<option value="1">Male</option>
										<option value="2">Female</option>
									</select>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="select">Birthday</label>
								<div class="col-md-9">
									<input type="date" id="text-input" name="text-input"
										class="form-control" placeholder="Text">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="textarea-input">Introduction</label>
								<div class="col-md-9">
									<textarea id="textarea-input" name="textarea-input" rows="9"
										class="form-control" placeholder="Content.."></textarea>
								</div>
							</div>



						</form>
					</div>
					<div class="card-footer">
						<button type="submit" class="btn btn-sm btn-primary">
							<i class="fa fa-dot-circle-o"></i> Submit
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</main>
<%@include file="include_footer.jsp"%>