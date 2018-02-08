package com.cypher.activiti.service;

import java.util.List;

import org.activiti.engine.repository.Deployment;

public interface IWorkFlowService {
	/**
	 * 获取所有流程部署信息
	 * 
	 * @return
	 */
	public List<Deployment> getAllDeploymentList();
}
