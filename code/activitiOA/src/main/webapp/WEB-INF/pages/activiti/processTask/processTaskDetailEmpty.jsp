<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>区域编辑</title>
<%@ include file="/WEB-INF/pages/include/head.jsp"%>
<meta charset="utf-8" />
<meta name="renderer" content="webkit">

</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/activiti/processTask/gotoProcessTaskList">待处理任务列表</a></li>
		<li class="active"><a href="javascript:void(0);">任务处理</a></li>
	</ul>
	<br />
	<div>
		<h3>请选择从“待处理任务列表”中选择需要处理的任务</h3>
	</div>

</body>
</html>