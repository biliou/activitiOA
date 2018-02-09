package com.cypher.activiti.service.impl;

import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
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

}
