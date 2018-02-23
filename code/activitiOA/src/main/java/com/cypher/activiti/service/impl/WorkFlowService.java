package com.cypher.activiti.service.impl;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cypher.activiti.dao.LeaveMapper;
import com.cypher.activiti.dto.LeaveBean;
import com.cypher.activiti.service.IWorkFlowService;

@Service
public class WorkFlowService implements IWorkFlowService {

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private FormService formService;
	@Autowired
	private LeaveMapper leaveMapper;

	@Override
	public List<Deployment> getAllDeploymentList() {
		return repositoryService.createDeploymentQuery().list();
	}

	@Override
	public Deployment addDeployment(InputStream inputStream, String name) {
		// 转换格式
		ZipInputStream zipInputStream = new ZipInputStream(inputStream);

		Deployment deployment = repositoryService.createDeployment() // 创建部署
				.addZipInputStream(zipInputStream)// 添加资源
				.name(name) // 添加名字
				.deploy();

		return deployment;
	}

	@Override
	public void delDeployment(String deploymentId, boolean flag) {
		repositoryService.deleteDeployment(deploymentId, flag);
	}

	@Override
	public List<ProcessDefinition> getAllDefinitionList() {
		return repositoryService.createProcessDefinitionQuery()// 获取流程定义查询接口
				.orderByProcessDefinitionKey()// 排序：key
				.orderByProcessDefinitionVersion().desc()// 排序：version
				.list();

	}

	@Override
	public InputStream getDefinitionImage(String deploymentId, String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}

	@Override
	public ProcessInstance startProcess(String processDefinitionKey, String businessKey,
			Map<String, Object> variables) {
		return runtimeService.startProcessInstanceByKey(processDefinitionKey, businessKey, variables);

	}

	@Override
	public ProcessDefinition getDeploymentId(String processInstanceId) {
		// 查找流程实例对象
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()// 获取流程实例查询接口
				.processInstanceId(processInstanceId).singleResult();

		String processDefinitionId = "";

		// 获取流程定义id
		if (processInstance == null) {
			// 若流程实例已结束，要去历史库查询
			HistoricProcessInstance historicProcessInstance = historyService//
					.createHistoricProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//
					.singleResult();

			processDefinitionId = historicProcessInstance.getProcessDefinitionId();

		} else {
			processDefinitionId = processInstance.getProcessDefinitionId();
		}

		// 获取流程部署id
		ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();

		return processDefinition;
	}

	@Override
	public ActivityImpl getActivitiCoordinate(String processInstanceId) {
		// 查找流程实例对象
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		if (processInstance != null) {
			// 获取此流程实例当前的活动id
			String activityId = processInstance.getActivityId();
			// 通过流程定义的实现类ProcessDefinitionEntity 来获取当前活动的坐标
			String processDefinitionId = processInstance.getProcessDefinitionId();
			ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
					.getProcessDefinition(processDefinitionId);
			ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
			return activityImpl;
		} else {
			return null;
		}
	}

	@Override
	public List<Task> getTaskListByAssignee(String assignee) {

		return taskService // 跟任务处理相关的服务类
				.createTaskQuery()// 创建一个任务查询
				.taskAssignee(assignee) // 加入查询条件: 委托人
				.list();
	}

	@Override
	public Task getTaskById(String taskId) {
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)//
				.singleResult();
		return task;
	}

	@Override
	public ProcessInstance getProcessInstanceById(String processInstanceId) {
		return runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId).singleResult();
	}

	@Override
	public String getTaskFormKeyByTaskId(String taskId) {
		TaskFormData taskformData = formService.getTaskFormData(taskId);
		String url = taskformData.getFormKey();
		return url;
	}

	/**
	 * 1:根据任务ID 查询任务对象 ,获取流程定义ID和流程实例ID<br/>
	 * 2:根据流程实例id找到流程实例对象,查询当前活动节点<br/>
	 * 3:根据流程定义id,查找流程定义对象(processDfinition->processDefinitionEntity)<br/>
	 * 4:根据当前活动节点从processDefinitonEntity对象里面获取当前活动后面的连线信息<br/>
	 */
	public List<PvmTransition> getOutcomeListByTaskId(String taskId) {
		// 1:根据任务ID 查询任务对象 ,获取流程定义ID和流程实例ID
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processDefinitionId = task.getProcessDefinitionId();
		String processInstanceId = task.getProcessInstanceId();

		// 2:根据流程实例id找到流程实例对象,查询当前活动节点
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(processInstanceId).singleResult();
		String activityId = processInstance.getActivityId();

		// 3:根据流程定义id,查找流程定义对象(processDefinition->processDefinitionEntity)
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);

		// 4:根据当前活动节点从processDefinitonEntity对象里面获取当前活动后面的连线信息
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		List<PvmTransition> pvmTransitionList = activityImpl.getOutgoingTransitions();

		// if (pvmTransitionList != null && pvmTransitionList.size() > 0) {
		// for (PvmTransition pvm : pvmTransitionList) {
		// System.out.println(pvm.getId());
		// System.out.println(pvm.getSource());
		// }
		// }
		return pvmTransitionList;
	}

	@Override
	public List<Comment> getCommentListByTaskId(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();
		return taskService.getProcessInstanceComments(processInstanceId);
	}

	/**
	 * 1.通过流程变量记录流程走向<br/>
	 * 2.添加任务批注<br/>
	 * 3.完成任务,并指定下一步处理人<br/>
	 * 4.判断是否最后一步，如果是，流程结束修改请假单状态
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public boolean completeTask(String taskId, String outcome, String commentMsg, String userName) {
		// 1.通过流程变量记录流程走向
		Map<String, Object> variables = new HashMap<String, Object>();
		if (outcome != null && (!outcome.equals("确认提交"))) { // 代表有多个连线选择
			variables.put("outcome", outcome);
		}

		// 2.添加任务批注
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInstanceId = task.getProcessInstanceId();

		// 填入批注信息的userId
		Authentication.setAuthenticatedUserId(userName);

		// 如果批注不为空，添加任务批注
		if (StringUtils.isNotEmpty(commentMsg)) {
			taskService.addComment(taskId, processInstanceId, commentMsg);
		}

		// 3.完成任务
		taskService.complete(taskId, variables);

		// 4.判断是否最后一步，如果是，流程结束修改请假单状态
		ProcessInstance processInstance = runtimeService // 获取跟执行流程相关的服务类
				.createProcessInstanceQuery() // 创建流程实例查询
				.processInstanceId(processInstanceId) // 查询条件：实例id
				.singleResult();

		LeaveBean leaveBean = leaveMapper.getLeaveBeanByProcessInstanceId(processInstanceId);

		if (processInstance == null) {
			leaveMapper.updateLeaveState(leaveBean.getLeaveId(), 2);
		}

		return true;
	}

}
