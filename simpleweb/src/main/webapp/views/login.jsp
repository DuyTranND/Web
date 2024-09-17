<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/simpleweb/login" method="post">
		<h2>Sign Up</h2>
		<c:if test="${alert !=null}">
			<h3 class="alert alert danger">${alert}</h3>
		</c:if>
		<section>
			<label class="input login-input">
				<div class="input-group">
					<span class="input-group-addon"><i class="fa fa-user"></i></span> <input
						type="text" placeholder="username" name="username"
						class="form-control">
				</div>
			</label>
		</section>
		<section>
			<label class="input login-input">
				<div class="input-group">
					<span class="input-group-addon"><i class="fa fa-user"></i></span> <input
						type="text" placeholder="password" name="password"
						class="form-control">
				</div>
			</label>
		</section>
		<section>
			<label class="input login-input">
				<div class="input-group">
					<span class="input-group-addon"><i class="fa fa-user"></i></span> <input
						type="checkbox" name="rememberme" class="form-control">Save
					login
				</div>
			</label>
		</section>
		<section>
			<label class="input login-input">
				<div class="input-group">
					<span class="input-group-addon"><i class="fa fa-user"></i></span> <input
						type="submit" value="Login" class="form-control">
				</div>
			</label>
		</section>
	</form>
</body>
</html>