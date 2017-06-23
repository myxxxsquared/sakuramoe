<%@ page contentType="text/html; charset=utf-8" language="java"%>

<script src="bower_components/ckeditor/ckeditor.js"></script>

<ol class="breadcrumb">
	<li class="breadcrumb-item">Home</li>
	<li class="breadcrumb-item active">Create a post</li>
</ol>
<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<div class="col-12">
				<div class="card">
					<form id="post-form" action="action_post.jsp" method="post">
						<div class="card-header">What's on your mind?</div>
						<div class="card-block">
							<textarea name="post-content" rows="9" class="form-control"
								placeholder="Content.."></textarea>
							<script>
								CKEDITOR.replace('post-content');
							</script>
						</div>
						<div class="card-footer">
							<div class="row">
								<div class="col-sm-auto">
									<select name="access" class="form-control">
										<option value="2">Public</option>
										<option value="1">Friends</option>
										<option value="0">Only me</option>
									</select>
								</div>
								<div class="col-sm-auto">
									<button id="post-submit" type="button" class="btn btn-primary">Post</button>
								</div>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		$("#post-submit").click(function() {
			$.post("action_post.jsp", $("#post-form").serialize(), function() {
				setUpUrl("home.jsp");
			});
		});
	});
</script>