package com.cypher.activiti.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

public interface IWorkFlowService {
	/**
	 * 获取所有流程部署信息
	 * 
	 * @return
	 */
	public List<Deployment> getAllDeploymentList();

	/**
	 * 部署流程
	 * 
	 * @param inputStream
	 * @param name
	 * @return
	 */
	public Deployment addDeployment(InputStream inputStream, String name);

	/**
	 * 删除流程
	 * 
	 * @param deploymentId
	 * @param flag
	 *            是否级联删除
	 */
	public void delDeployment(String deploymentId, boolean flag);

	/**
	 * 获取所有流程定义列表信息
	 * 
	 * @return
	 */
	public List<ProcessDefinition> getAllDefinitionList();

	/**
	 * 获取流程定义图片
	 * 
	 * @param deploymentId
	 * @param imageName
	 * @return
	 */
	public InputStream getDefinitionImage(String deploymentId, String imageName);

	/**
	 * 启动流程
	 * 
	 * @param processDefinitionKey
	 * @param businessKey
	 * @param variables
	 * @return
	 */
	public ProcessInstance startProcess(String processDefinitionKey, String businessKey, Map<String, Object> variables);

	/**
	 * 通过流程实例id 获取流程部署id
	 */
	public ProcessDefinition getDeploymentId(String processInstanceId);

	/**
	 * 获取流程实例任务执行结点信息
	 * @param processInstanceId
	 * @return
	 */
	public ActivityImpl getActivitiCoordinate(String processInstanceId);
}
