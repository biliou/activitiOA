package com.cypher.activiti.activiti;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.alibaba.fastjson.JSON;

@Ignore
public class LeaveProcessTest {

	private ProcessEngine processEngine;

	@Before
	public void initProcessEngine() {
		processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti/activiti.cfg.xml").buildProcessEngine();

	}

	/**
	 * 流程部署
	 */
	@Test
	public void testLeaveProcessDeploy() {
		// // 1.通过流程定义相关的接口 RepositoryService 创建部署构建器
		// RepositoryService repositoryService = processEngine.getRepositoryService();
		// // 流程部署和流程定义相关的服务接口
		//
		// DeploymentBuilder deploymentBuilder = repositoryService.createDeployment();
		//
		// // 2.添加资源，进行部署
		// deploymentBuilder.addClasspathResource("leave.bpmn");
		// deploymentBuilder.addClasspathResource("leave.png");
		// deploymentBuilder.name("请假流程");
		//
		// // 3.进行部署
		// Deployment deploy = deploymentBuilder.deploy();

		// 链式
		Deployment deploy = processEngine.getRepositoryService() // 流程部署和流程定义相关的服务接口
				.createDeployment() // 创建部署构建器
				.addClasspathResource("diagrams/leave1.bpmn") // 添加资源
				.addClasspathResource("diagrams/leave1.png")// 添加资源
				.name("请假流程")// 定义流程名字
				.deploy();// 进行部署

		System.out.println(deploy.getId());
		System.out.println(deploy.getName());
	}

	/**
	 * 流程启动 涉及到的表： <br/>
	 * act_ru_execution 生成一条流程实例数据 <br/>
	 * act_ru_task 生成一条待执行的任务记录 <br/>
	 * act_hi_taskinst 生成一条历史任务记录，但是没有结束时间
	 */
	@Test
	public void testStartLeaveProcess() {
		String processDefinitionKey = "myProcess";
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey);
		System.out.println("流程部署ID=" + processInstance.getDeploymentId());
		System.out.println("流程定义ID=" + processInstance.getProcessDefinitionId());
		System.out.println("流程实例ID=" + processInstance.getProcessInstanceId());
		System.out.println("流程任务ID=" + processInstance.getActivityId());

		// 流程部署ID=null
		// 流程定义ID=myProcess:1:47504
		// 流程实例ID=50001
		// 流程任务ID=usertask1
	}

	/**
	 * 流程任务执行:查询个人任务 task1 请假申请
	 */
	@Test
	public void testQueryMyTask1() {
		String processInstanceId = "50001";
		// String assignee = "user2";
		List<Task> taskList = processEngine.getTaskService() // 跟任务处理相关的服务类
				.createTaskQuery()// 创建一个任务查询
				// .taskAssignee(assignee) // 加入查询条件: 委托人
				.processInstanceId(processInstanceId) // 加入查询条件: 流程实例ID
				.list();

		if (taskList != null && taskList.size() > 0) {
			for (Task task : taskList) {
				System.out.println("流程定义ID:" + task.getProcessDefinitionId());
				System.out.println("流程实例ID:" + task.getProcessInstanceId());
				System.out.println("执行对象ID:" + task.getExecutionId());
				System.out.println("任务ID:" + task.getId());// 任务ID:10004
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
			}
		}
	}

	/**
	 * 流程任务完成task1 请假申请
	 */
	@Test
	public void testExecutionTask1() {
		String taskId = "52502";
		processEngine.getTaskService()// 跟任务处理相关的服务类
				.complete(taskId); // 完成任务
		System.out.println("请假申请任务完成");
	}

	/**
	 * 流程执行过程中，查询流程执行到哪一个状态
	 */
	@Test
	public void testQueryProInstanceState() {
		String processInstanceId = "5";
		// String taskId = "90010";

		// Task task =
		// processEngine.getTaskService().createTaskQuery().taskId(taskId).singleResult();
		// String processDefinitionId = task.getProcessDefinitionId();
		// String processInstanceId = task.getProcessInstanceId();

		ProcessInstance processInstance = processEngine.getRuntimeService() // 获取跟执行流程相关的服务类
				.createProcessInstanceQuery() // 创建流程实例查询
				.processInstanceId(processInstanceId) // 查询条件：实例id
				.singleResult();

		// 查到当前执行的任务id
		if (processInstance != null) {
			System.out.println("当前流程执行到：" + processInstance.getActivityId());
		} else {
			System.out.println("当前流程已执行结束");
		}

	}

	/**
	 * 流程任务执行:查询个人任务 task2 主管审批
	 */

	@Test
	public void testQueryMyTask2() {
		String processInstanceId = "22504";
		String assignee = "user2";
		List<Task> taskList = processEngine.getTaskService() // 跟任务处理相关的服务类
				.createTaskQuery()// 创建一个任务查询
				.taskAssignee(assignee) // 加入查询条件: 委托人
				.processInstanceId(processInstanceId) // 加入查询条件: 流程实例ID
				.list();

		if (taskList != null && taskList.size() > 0) {
			for (Task task : taskList) {
				System.out.println("流程定义ID:" + task.getProcessDefinitionId());
				System.out.println("流程实例ID:" + task.getProcessInstanceId());
				System.out.println("执行对象ID:" + task.getExecutionId());
				System.out.println("任务ID:" + task.getId());// 任务ID:10004
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
			}
		}
	}

	/**
	 * 流程任务完成task1 请假申请
	 */
	@Test
	public void testExecutionTask2() {
		String taskId = "30004";
		processEngine.getTaskService()// 跟任务处理相关的服务类
				.complete(taskId); // 完成任务
		System.out.println("审批任务完成");
	}

	/**
	 * 流程任务完成,查看历史记录<br/>
	 * act_ru_task 中记录会被删除<br/>
	 * act_hi_taskinst 记录的endtime会加上<br/>
	 * act_hi_procinst 记录的endtime会加上<br/>
	 */
	@Test
	public void testQueryMyTaskComplate() {
		String processInstanceId = "5";
		List<HistoricTaskInstance> historicTaskInstancesList = processEngine.getHistoryService() // 跟任务历史相关的服务类
				.createHistoricTaskInstanceQuery()// 创建一个任务历史查询
				.processInstanceId(processInstanceId)// 加入查询条件: 流程实例ID
				.list();

		if (historicTaskInstancesList != null && historicTaskInstancesList.size() > 0) {
			for (HistoricTaskInstance hisTask : historicTaskInstancesList) {
				System.out.println("流程任务ID:" + hisTask.getId());
				System.out.println("流程任务执行者:" + hisTask.getAssignee());
				System.out.println("流程任务id:" + hisTask.getTaskDefinitionKey());
				System.out.println("流程任务开始时间:" + hisTask.getCreateTime());
				System.out.println("流程任务结束时间:" + hisTask.getEndTime());
				System.out.println("-----------------------------------");
			}
		}

		// 查询任务历史中最新的一条
		HistoricProcessInstance hisTask = processEngine.getHistoryService() // 跟任务历史相关的服务类
				.createHistoricProcessInstanceQuery()// 创建一个任务历史查询
				.processInstanceId(processInstanceId)// 加入查询条件: 流程实例ID
				.singleResult();

		System.out.println("-----查询任务历史中最新的一条-----");
		System.out.println("流程实例ID:" + hisTask.getId());
		System.out.println("流程定义ID:" + hisTask.getProcessDefinitionId());
		System.out.println("流程实例结束时间:" + hisTask.getEndTime());
		// System.out.println("流程任务id:" + hisTask.getTaskDefinitionKey());
		// System.out.println("流程任务开始时间:" + hisTask.getCreateTime());
		// System.out.println("流程任务结束时间:" + hisTask.getEndTime());

	}

	/**
	 * 获取当前流程实例的部署id
	 */
	@Test
	public void testGetDeploymentId() {
		String processInstanceId = "5";
		String processDefinitionId = "";

		ProcessInstance processInstance = processEngine.getRuntimeService()//
				.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId).singleResult();

		if (processInstance != null) {
			System.out.println("流程定义id =" + processInstance.getProcessDefinitionId());
			System.out.println("流程部署id =" + processInstance.getDeploymentId());
			System.out.println("流程部署id =" + processInstance.getProcessDefinitionKey());

			processDefinitionId = processInstance.getProcessDefinitionId();
		} else {
			// 查询执行到哪一个节点
			HistoricProcessInstance historicProcessInstance = processEngine.getHistoryService()//
					.createHistoricProcessInstanceQuery()//
					.processInstanceId(processInstanceId)//
					.singleResult();

			System.out.println("-----查询任务历史中最新的一条-----");
			System.out.println("流程实例ID:" + historicProcessInstance.getId());
			System.out.println("流程定义ID:" + historicProcessInstance.getProcessDefinitionId());
			System.out.println("流程实例结束时间:" + historicProcessInstance.getEndTime());
			System.out.println("流程任务id:" + historicProcessInstance.getEndActivityId());

			processDefinitionId = historicProcessInstance.getProcessDefinitionId();
		}

		ProcessDefinition processDefinition = processEngine.getRepositoryService().createProcessDefinitionQuery()
				.processDefinitionId(processDefinitionId).singleResult();

		System.out.println("流程部署id =" + processDefinition.getDeploymentId());

	}

	/**
	 * 通过任务id获取当前任务对象
	 */
	@Test
	public void testGetTaskByTaskId() {
		String taskId = "77509";

		Task task = processEngine.getTaskService()//
				.createTaskQuery()//
				.taskId(taskId)//
				.singleResult();

		System.out.println("流程任务ID:" + task.getId());
		System.out.println("流程任务执行者:" + task.getAssignee());
		System.out.println("流程任务定义id:" + task.getTaskDefinitionKey());
		System.out.println("流程任务开始时间:" + task.getCreateTime());
		System.out.println("流程定义ID:" + task.getProcessDefinitionId());
		System.out.println("流程实例ID:" + task.getProcessInstanceId());

	}

	@Test
	public void testGetTaskFormKeyByTaskId() {
		String formKey = processEngine.getFormService()//
				.getTaskFormData("60009")//
				.getFormKey();

		System.out.println(formKey);
	}

	@Test
	public void testGetLineName() {
		List<Comment> commentList = processEngine.getTaskService()//
				.getTaskComments("60009");

		System.out.println(commentList);
	}

}
