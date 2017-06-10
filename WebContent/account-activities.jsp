﻿<%@ page contentType="text/html; charset=utf-8" language="java"
	errorPage=""%>

<%@include file="include_header.jsp"%>

<main class="main">
<ol class="breadcrumb">
	<li class="breadcrumb-item">Home</li>
	<li class="breadcrumb-item">Settings</li>
	<li class="breadcrumb-item active">Account activities</li>
</ol>

<div class="container-fluid">
	<div class="animated fadeIn">
		<div class="row">
			<div class="col-lg-12">
				<div class="card">
					<div class="card-header">
						<i class="fa fa-align-justify"></i> Combined All Table
					</div>
					<div class="card-block">
						<table class="table table-bordered table-striped table-sm">
							<thead>
								<tr>
									<th>Username</th>
									<th>Date registered</th>
									<th>Role</th>
									<th>Status</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>Vishnu Serghei</td>
									<td>2012/01/01</td>
									<td>Member</td>
									<td><span class="badge badge-success">Active</span></td>
								</tr>
								<tr>
									<td>Zbyněk Phoibos</td>
									<td>2012/02/01</td>
									<td>Staff</td>
									<td><span class="badge badge-danger">Banned</span></td>
								</tr>
								<tr>
									<td>Einar Randall</td>
									<td>2012/02/01</td>
									<td>Admin</td>
									<td><span class="badge badge-default">Inactive</span></td>
								</tr>
								<tr>
									<td>Félix Troels</td>
									<td>2012/03/01</td>
									<td>Member</td>
									<td><span class="badge badge-warning">Pending</span></td>
								</tr>
								<tr>
									<td>Aulus Agmundr</td>
									<td>2012/01/21</td>
									<td>Staff</td>
									<td><span class="badge badge-success">Active</span></td>
								</tr>
							</tbody>
						</table>

					</div>
				</div>
			</div>
		</div>
	</div>
</div>
</main>

<%@include file="include_footer.jsp"%>