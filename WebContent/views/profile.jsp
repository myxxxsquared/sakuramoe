<%@page import="sakuramoe.UserInfo"%>
<%@page import="sakuramoe.User"%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%
	if (session.getAttribute("user") == null) {
		session.setAttribute("user", new User());
	}
	User user = (User) session.getAttribute("user");
	if (!user.isLogin()) {
		response.sendRedirect("login.jsp");
		return;
	}

	UserInfo userinfo = new UserInfo(user);

	if (request.getMethod().equals("POST")) {
		String nickname = request.getParameter("nickname");
		String gender = request.getParameter("gender");
		String birthday = request.getParameter("birthday");
		String intro = request.getParameter("intro");

		if (nickname != null)
			userinfo.setNickName(nickname);
		if (gender != null)
			try {
				userinfo.setGender(Integer.parseInt(gender));
			} catch (Exception e) {
			}
		if (birthday != null)
			userinfo.setBirthday(birthday);
		if (intro != null)
			userinfo.setIntroduction(intro);
	}
%>

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
					<form action="#" method="post" class="form-horizontal">
						<div class="card-block">

							<div class="form-group row">
								<label class="col-md-3 form-control-label">Profile
									Picture </label>
								<div class="col-md-9">
									<img src="<%out.print(userinfo.getAvatar());%>" height="50em"
										class="img-avatar">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<button type="button" class="btn btn-secondary">Update</button>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="nickname">Nick
									Name</label>
								<div class="col-md-9">
									<input type="text" id="nickname" name="nickname"
										class="form-control" placeholder="Text"
										value="<%out.print(userinfo.getUserDesc());%>">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for=gender">Gender</label>
								<div class="col-md-9">
									<select id="gender" name="gender" class="form-control">
										<%
											int gendar = userinfo.getGender();
										%>
										<option value="0"
											<%if (gendar == 0)
				out.print("selected=\"selected\"");%>>Please
											select</option>
										<option value="1"
											<%if (gendar == 1)
				out.print("selected=\"selected\"");%>>Male</option>
										<option value="2"
											<%if (gendar == 2)
				out.print("selected=\"selected\"");%>>Female</option>
									</select>
								</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="birthday">Birthday</label>
								<div class="col-md-9">
									<input type="date" id="birthday" name="birthday"
										class="form-control" placeholder="Text"
										value="<%out.print(userinfo.getBirthday());%>">
								</div>
							</div>
							<div class="form-group row">
								<label class="col-md-3 form-control-label" for="intro">Introduction</label>
								<div class="col-md-9">
									<textarea id="intro" name="intro" rows="9" class="form-control"
										placeholder="Content..">
										<%
											out.print(userinfo.getIntroduction());
										%>
									</textarea>
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