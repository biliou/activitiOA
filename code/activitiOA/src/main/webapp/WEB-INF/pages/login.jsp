<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<%@include file="/WEB-INF/pages/include/head.jsp"%>
<title>oa 系统-登录页</title>
</head>
<body>
	<h1>OA 系统</h1>
	<div id="messageBoxLogin" class="alert alert-error ${empty loginFlag ? 'hide' :''} ">
		<button data-dismiss="alert" class="close">×</button>
		<label id="loginFlag" class="error">${loginFlag}</label>
	</div>
	<div id="messageBox" class="alert alert-error ${empty loginErrorMsg ? 'hide' :''} ">
		<button data-dismiss="alert" class="close">×</button>
		<label id="loginError" class="error">${loginErrorMsg}</label>
	</div>
	<form id="loginForm" action="${ctx}/login" method="post">
		用户名：
		<input type="text" name="loginName" required />
		密码：
		<input type="password" name="password" required />
		<input type="submit" value="登录" />
	</form>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$("#loginForm").validate({
			messages : {
				loginName : {
					required : "请填写用户名."
				},
				password : {
					required : "请填写密码."
				}
			},
			errorLabelContainer : "#messageBox",
			errorPlacement : function(error, element) {
				error.appendTo($("#loginError").parent());
			}
		});
	});
</script>
</html>