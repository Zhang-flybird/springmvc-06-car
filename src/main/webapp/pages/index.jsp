<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>首页</title>
<style type="text/css">
	* {
	padding:0px;
	margin:0px;
	}
	.main {
		width:1200px;
		margin:10px auto;
	}
	.leftMenu {
		width:120px;
		float:left;
		margin-right:15px;
		background-color: #F5F5F5;
	}
	.rightMain {
		margin-left:15px;
		width:1050px;
		float:left;
		background-color: #F5F5F5;
		/* height:600px; */
	}
		.leftMenu li {
		list-style: none;
		background-color: #FFFFFF;
		height: 40px;
		margin-top: 2px;
		line-height: 40px;
	}
	.info{
		height:30px;
		color:blue;
		line-height:30px;
	}
</style>
</head>
<body>
	<!-- 主区域 -->
	<div class="main">
	<!-- 左侧菜单 -->
		<div class="leftMenu">
			<div class="info">车辆管理</div>
			<ul>
				<li>
				<a href="${pageContext.request.contextPath}/car/toAdd" target="et">添加车辆</a>
				</li>
				<li>
				<a href="${pageContext.request.contextPath}/car/toList" target="et">车辆列表</a>
				</li>
			</ul>
		</div>
		<!-- 右侧菜单 -->
		<div class="rightMain">
			<iframe name="et" frameborder="0px" width="100%" height="600px"></iframe>
		</div>
	</div>
</body>
</html>
