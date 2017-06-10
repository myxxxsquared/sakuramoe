<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>
<%@include file="include_header.jsp"%>
<main class="main">
<ol class="breadcrumb">
	<li class="breadcrumb-item">Home</li>
	<li class="breadcrumb-item active">News</li>
</ol>
<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<div class="col-md-6">
				<div class="card">
					<div class="card-header">
						<div class="float-left">
							<img src="img/avatars/6.jpg" height="50em" class="img-avatar"
								alt="admin@bootstrapmaster.com">
						</div>
						<div class="float-left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
						<div class="float-left">
							<b>Admin</b><br /> 1970-01-01 00:00
						</div>
					</div>
					<div class="card-block">Lorem ipsum dolor sit amet,
						consectetuer adipiscing elit, sed diam nonummy nibh euismod
						tincidunt ut laoreet dolore magna aliquam erat volutpat. Ut wisi
						enim ad minim veniam, quis nostrud exerci tation ullamcorper
						suscipit lobortis nisl ut aliquip ex ea commodo consequat.</div>
					<div class="card-footer">
						<button type="button" class="btn btn-success">
							<i class="icon-action-redo"></i>&nbsp;Forward
						</button>
						<button type="button" class="btn btn-success">
							<i class="icon-like"></i>&nbsp;Like
						</button>
						<button type="button" class="btn btn-success">
							<i class="icon-list"></i>&nbsp;More
						</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</main>
<%@include file="include_footer.jsp"%>
