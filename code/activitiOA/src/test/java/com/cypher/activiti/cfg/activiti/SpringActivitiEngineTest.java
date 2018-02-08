package com.cypher.activiti.cfg.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试spring activiti集成
 * 
 * @author Administrator
 *
 */
public class SpringActivitiEngineTest {

	private ApplicationContext ac;
	private ProcessEngine processEngine;

	@Before
	public void init() {
		ac = new ClassPathXmlApplicationContext("classpath:springmvc/spring-mybatis.xml");
		processEngine = (ProcessEngine) ac.getBean("processEngine");
	}

	// 测试引擎是否由spring托管成功
	@Ignore
	public void testInitProcessEngine() {
		Deployment deployment = processEngine.getRepositoryService().createDeployment()//
				.addClasspathResource("diagrams/leave.bpmn")//
				.addClasspathResource("diagrams/leave.png")//
				.name("请假申请")//
				.deploy();

		System.out.println(deployment.getId());
		System.out.println(deployment.getName());
	}
	
	@Test
	public void testRepositoryService() {
		RepositoryService repositoryService = (RepositoryService) ac.getBean("repositoryService");
		System.out.println(repositoryService);
	}
}
