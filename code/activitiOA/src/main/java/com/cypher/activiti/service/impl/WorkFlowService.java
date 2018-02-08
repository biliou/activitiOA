package com.cypher.activiti.service.impl;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
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

}
