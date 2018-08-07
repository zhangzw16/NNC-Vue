<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<title>尼基营养后台管理</title>
		<link href="css/nickNutrition.css" rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="js/jquery-1.11.0.min.js"></script>
	</head>
	<body class="home_body">
		<div class="home_content_login">
			<div class="main">
				<div class="home_content_title">
					<div class="topslot">
						尼基营养后台管理
					</div>
					<div class="note">
						<p class="line135" style="margin-left:22px"></p>
						<p class="note_txt">欢迎您</p>
						<p class="line135"></p>						
					</div>
				</div>
				<div class="home_main_logo"></div>
				<form class="home_main_login" method="POST" action="rest/user/login"
					accept-charset="UTF-8">
					<input placeholder="用户名" name="loginId" type="text" />
					<input placeholder="密码" name="passwd" type="password" />
					<div class="remember_pwd_div">
						<!--<input class="remember_pwd" type="checkbox"/>
                    <div class="remember_pwd_lab">记住密码</div>-->
					</div>
					<input id="submit" type="submit" value="登录" />
					<div class="error_box error_box_on">
						${login_message_tip}
					</div>
				</form>
			</div>
		</div>
		<p style="position:fixed;bottom:10px;color:#333;width:100%;text-align:center">©尼基营养版权所有 2015-2017</p>
	</body>
</html>