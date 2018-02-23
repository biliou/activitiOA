package com.cypher.activiti.activiti;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ParallelGateWayTest {

	private ProcessEngine processEngine;

	// 初始化activiti引擎
	@Before
	public void initProcessEngine() {
		processEngine = ProcessEngineConfiguration
				.createProcessEngineConfigurationFromResource("activiti/activiti.cfg.xml") // 读取配置资源文件
				.buildProcessEngine();
	}

	// 部署流程
	@Test
	public void testDeployProcess() {
		Deployment deploy = processEngine.getRepositoryService() //
				.createDeployment()//
				.addClasspathResource("diagrams/parallelGateWay.bpmn")//
				.addClasspathResource("diagrams/parallelGateWay.png")//
				.name("会议流程").deploy();

		System.out.println(deploy.getId());
		System.out.println(deploy.getName());
	}

	// 添加角色与个人信息
	@Test
	public void testAddUserAndRole() {
		IdentityService identityService = processEngine.getIdentityService();
		// 创建角色
		identityService.saveGroup(new GroupEntity("部门主管"));
		identityService.saveGroup(new GroupEntity("部门经理"));
		identityService.saveGroup(new GroupEntity("CTO"));
		// 创建用户
		identityService.saveUser(new UserEntity("张三"));
		identityService.saveUser(new UserEntity("李四"));
		identityService.saveUser(new UserEntity("王五"));
		identityService.saveUser(new UserEntity("赵六"));
		identityService.saveUser(new UserEntity("田七"));
		identityService.saveUser(new UserEntity("胡八"));
		// 创建角色和用户的对应关系
		identityService.createMembership("张三", "部门主管");
		identityService.createMembership("李四", "部门主管");
		identityService.createMembership("王五", "部门经理");
		identityService.createMembership("赵六", "部门经理");
		identityService.createMembership("田七", "CTO");
		identityService.createMembership("胡八", "CTO");
	}

	// 启动流程实例
	@Test
	public void testStartProcess() {
		String processDefinitionKey = "parallelGateWay";
		ProcessInstance processInstance = processEngine.getRuntimeService()//
				.startProcessInstanceByKey(processDefinitionKey);

		System.out.println("流程实例id = " + processInstance.getProcessInstanceId()); // 47501
		System.out.println("流程实例名字 = " + processInstance.getName());
		System.out.println("流程活动任务id = " + processInstance.getActivityId()); // parallelgateway1

	}

	// 查询流程执行状态（显示两条任务，因为并行开关）
	@Test
	public void testQueryProcessState() {
		String processInstanceId = "67501";

		List<Task> taskList = processEngine.getTaskService()//
				.createTaskQuery()//
				.processInstanceId(processInstanceId)// 查询流程实例
				.list();

		if (taskList != null && taskList.size() > 0) {
			for (Task task : taskList) {
				System.out.println("流程定义ID:" + task.getProcessDefinitionId());
				System.out.println("流程实例ID:" + task.getProcessInstanceId());
				System.out.println("执行对象ID:" + task.getExecutionId());
				System.out.println("任务ID:" + task.getId());// 任务ID:130004
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
				System.out.println("-------------------------------");

			}
		}
	}

	// 查询那些人具有此次任务的处理权限
	@Test
	public void testQueryGroupUserByTaskId() {
		String taskId = "67510";
		List<IdentityLink> identityLinkList = processEngine.getTaskService()//
				.getIdentityLinksForTask(taskId);

		for (IdentityLink identityLink : identityLinkList) {
			System.out.println(identityLink.getGroupId());
			System.out.println(identityLink.getUserId());
			System.out.println(identityLink.getTaskId());
		}
	}

	// 查询流程执行状态（候选者读取任务状态）
	@Test
	public void testQueryProcessStateByCandidate() {
		String processInstanceId = "67501";
		String assignee = "胡八";

		List<Task> taskList = processEngine.getTaskService()//
				.createTaskQuery()//
				.taskCandidateUser(assignee) // 加入查询条件
				.processInstanceId(processInstanceId)// 查询流程实例
				.list();

		if (taskList != null && taskList.size() > 0) {
			for (Task task : taskList) {
				System.out.println("流程定义ID:" + task.getProcessDefinitionId());
				System.out.println("流程实例ID:" + task.getProcessInstanceId());
				System.out.println("执行对象ID:" + task.getExecutionId());
				System.out.println("任务ID:" + task.getId());// 任务ID:130004
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
				System.out.println("-------------------------------");

			}
		}
	}

	// 执行产品主管任务
	@Test
	public void testCompleteTask() {
		String taskId = "70003";
		String userId = "胡八";
		processEngine.getTaskService()//
				.claim(taskId, userId); // 拾取任务
		processEngine.getTaskService().complete(taskId);// 完成任务
		System.out.println("审批任务完成");
	}

	// 查询历史记录
	@Test
	public void testHistoryTask() {
		String processInstanceId = "67501";
		List<HistoricTaskInstance> histaskList = processEngine.getHistoryService()//
				.createHistoricTaskInstanceQuery()//
				.processInstanceId(processInstanceId)//
				.list();
		
		for (HistoricTaskInstance hisTask : histaskList) {
			System.out.println("流程定义ID:"+hisTask.getProcessDefinitionId());
			System.out.println("流程实例ID:"+hisTask.getProcessInstanceId());
			System.out.println("执行对象ID:"+hisTask.getExecutionId());
			System.out.println("历史任务ID:"+hisTask.getId());//任务ID:12502
			System.out.println("历史任务名称:"+hisTask.getName());
			System.out.println("历史任务的结束时间:"+hisTask.getEndTime());
			System.out.println("历史任务的处理人:"+hisTask.getAssignee());
			System.out.println("---------------------");
		}

	}

}
