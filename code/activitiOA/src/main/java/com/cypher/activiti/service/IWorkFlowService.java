package com.cypher.activiti.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

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
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public ActivityImpl getActivitiCoordinate(String processInstanceId);

	/**
	 * 通过处理人的名字获取他的所有任务信息
	 * 
	 * @param assingnee
	 * @return
	 */
	public List<Task> getTaskListByAssignee(String assignee);

	/**
	 * 通过任务id获取任务对象信息
	 * 
	 * @param taskId
	 * @return
	 */
	public Task getTaskById(String taskId);

	/**
	 * 根据流程实例id获取流程实例对象
	 * 
	 * @param ProcessInstanceId
	 * @return
	 */
	public ProcessInstance getProcessInstanceById(String processInstanceId);

	/**
	 * 根据任务id获取对应任务的明细页面处理url
	 * 
	 * @param taskId
	 * @return
	 */
	public String getTaskFormKeyByTaskId(String taskId);

	/**
	 * 通过任务id 获取当前任务完成以后的连线信息
	 * 
	 * @param taskId
	 * @return
	 */
	public List<PvmTransition> getOutcomeListByTaskId(String taskId);

	/**
	 * 通过任务id 获取流程实例ID获取整个流程实例的评论
	 * 
	 * @param taskId
	 * @return
	 */
	public List<Comment> getCommentListByTaskId(String taskId);

	/**
	 * 任务处理
	 * 
	 * @param taskId
	 * @param outcome
	 * @param commentMsg
	 * @param userName
	 * @return
	 */
	public boolean completeTask(String taskId, String outcome, String commentMsg, String userName);
}
