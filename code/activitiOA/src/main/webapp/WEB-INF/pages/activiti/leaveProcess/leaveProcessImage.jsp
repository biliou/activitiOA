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
		<li><a href="${ctx}/activiti/leaveProcess/gotoLeaveProcessList">请假单列表</a></li>
		<li><a href="${ctx}/activiti/leaveProcess/gotoLeaveProcessEdit?editFlag=1">请假单录入</a></li>
		<li class="active"><a href="javascript:void(0);">流程图</a></li>
	</ul>
	<br />
	<c:if test="${not empty deploymentId}">
		<img style="position: absolute; top: 40px; left: 0px; max-width: none;"
			src="${ctx}/activiti/processDefinition/getProcessDefinitionImage?deploymentId=${deploymentId}&imageName=${imageName}">

		<c:if test="${not empty activityImpl}">
			<div
				style="position:absolute;background:red;opacity: 0.2;
			top:${activityImpl.y+40}px;left:${activityImpl.x}px;width:${activityImpl.width}px;height:${activityImpl.height}px;border-radius:8px; ">
			</div>

		</c:if>
	</c:if>
	<c:if test="${empty deploymentId}">
		<div>
			<h3>请选择相应的流程定义查看对应的流程图片</h3>
		</div>
	</c:if>

</body>
</html>