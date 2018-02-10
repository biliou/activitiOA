package com.cypher.activiti.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

			processDefinitionId = historicProcessInstance.getId();

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

}
