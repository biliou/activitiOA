package com.cypher.activiti.activiti;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.cypher.activiti.activiti.bean.ApproveForm;

@Ignore
public class ApproveProcessTest {
	private ProcessEngine processEngine;

	@Before
	public void initProcessEngine() {
		processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti/activiti.cfg.xml").buildProcessEngine();

	}

	// 部署流程
	@Test
	public void testProcessDeploy() {
		Deployment deploy = processEngine.getRepositoryService() //
				.createDeployment()//
				.addClasspathResource("diagrams/approve.bpmn")//
				.addClasspathResource("diagrams/approve.png")//
				.name("报账流程")//
				.deploy();

		System.out.println(deploy.getId());
		System.out.println(deploy.getName());
	}

	// 启动流程
	@Test
	public void testProcessStart() {
		String processDefinitionKey = "approve";
		ProcessInstance processInstance = processEngine.getRuntimeService()//
				.startProcessInstanceByKey(processDefinitionKey);//

		System.out.println("流程部署ID=" + processInstance.getDeploymentId());
		System.out.println("流程定义ID=" + processInstance.getProcessDefinitionId());
		System.out.println("流程实例ID=" + processInstance.getProcessInstanceId()); //
		System.out.println("流程任务ID=" + processInstance.getActivityId());

	}

	// 查看执行的任务
	@Test
	public void testProcessTaskQuery() {
		String processInstanceId = "32501";
		String assignee = "user1";
		List<Task> taskList = processEngine.getTaskService()//
				.createTaskQuery()//
				.processInstanceId(processInstanceId)//
				.taskAssignee(assignee).list();

		if (taskList != null && taskList.size() > 0) {
			for (Task task : taskList) {
				System.out.println("流程定义ID:" + task.getProcessDefinitionId());
				System.out.println("流程实例ID:" + task.getProcessInstanceId());
				System.out.println("执行对象ID:" + task.getExecutionId());
				System.out.println("任务ID:" + task.getId());// 任务ID:32504
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
			}
		}

		String taskId = "32504";
		// 设置流程变量 方式一
		// 存放在act_ru_variable
		processEngine.getTaskService().setVariable(taskId, "报账原因", "出差");
		processEngine.getTaskService().setVariable(taskId, "报账金额", 7880);
		processEngine.getTaskService().setVariable(taskId, "报账时间", new Date());
		// 设置流程变量 方式二
		// 存放在act_ru_variable
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("报账原因o", "出差");
		variables.put("报账金额o", 7880);
		variables.put("报账时间", new Date());
		processEngine.getTaskService().setVariables(taskId, variables);
		// 设置流程变量 方式三 针对前端是一个form表单,后来会是一个序列化的java bean
		// 存放在act_ge_bytearray 表中
		ApproveForm approveForm = new ApproveForm();
		approveForm.setReason("报账原因bean");
		approveForm.setMoney(500);
		approveForm.setDate(new Date());
		processEngine.getTaskService().setVariable(taskId, "报账信息", approveForm);

	}
}
