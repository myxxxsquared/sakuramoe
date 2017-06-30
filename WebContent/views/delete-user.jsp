<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>

<ol class="breadcrumb">
	<li class="breadcrumb-item">Home</li>
	<li class="breadcrumb-item">Settings</li>
	<li class="breadcrumb-item active">Delete user</li>
</ol>

<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<div class="col-12">
				<div class="card card-danger card-inverse">
					<form action="actions/user_delete.jsp" method="post" class="form-horizontal" id="dangerous-form">
						<div class="card-header">Delete User.</div>
						<div class="card-block">
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="text-input">Password
								</label>
								<div class="col-md-9">
									<input type="password" name="password"
										class="form-control">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="text-input"> I'm sure what I'm doing.
								</label>
								<div class="col-md-9">
									<input type="checkbox" id="sure"
										class="switch switch-default switch-danger">
								</div>
							</div>
						</div>
						<div class="card-footer">
							<button type="button" class="btn btn-sm btn-danger btn-delete-submit">
								<i class="fa fa-dot-circle-o"></i> DANGEROUS! Submit
							</button>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$(".btn-delete-submit").click(function() {
			if ($("#sure").prop("checked")) {
				if (confirm("Do you really want to delete your user?")) {
					$("#dangerous-form").submit();
				}
			}
		});
	});
</script>