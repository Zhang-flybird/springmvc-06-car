<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8">
	<title>登录</title>
	<style type="text/css">
		.login{
			background:#FFFFFF;
			width:350px;
			height:150px;
			margin:200px auto;
			padding:70px 30px 30px 50px;
			border:1px solid #cacaca;
			border-radius:20px;
			box-shadow: 0px 0px 20px #cacaca;
		}
		body{
		   background:url("${pageContext.request.contextPath}/static/img/bg.jpg") no-repeat;
			width:100%;
			height:100%;
			position: fixed;
			background-size: cover;
		}
	</style>
	</head>
	<body>
	<div class="login">
			<form action="${pageContext.request.contextPath}/user/login" method="post">
				用户名：<input type="text" name="name" autocomplete="off" ><br>
				密码：<input type="password" name="password" autocomplete="off" ><br>
				验证码：<input type="text" name="code" autocomplete="off" >
					<img id="codeImg" src="${pageContext.request.contextPath}/user/code"/>
				<br>
				<input type="submit" value="登录">&nbsp;&nbsp;
			</form>
		</div>
<script src="${pageContext.request.contextPath }/static/js/jquery/jquery.min.js" type="text/javascript"></script>
<script type="text/javascript">
		const path="${pageContext.request.contextPath}";
		$(function(){
			$('#codeImg').click(function(){
				$('#codeImg').attr('src', path + '/user/code?' + new Date().getTime());
			});
		})

		</script>	
	</body>
</html>