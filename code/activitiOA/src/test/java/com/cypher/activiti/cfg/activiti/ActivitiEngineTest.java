package com.cypher.activiti.cfg.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 初始化工作流Activiti数据库表
 * 
 * @author Administrator
 *
 */
public class ActivitiEngineTest {
	// 使用创建对象方式初始化
	@Ignore
	public void testActivitiEngine() {
		// 创建Activiti配置对象的实例
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
				.createStandaloneProcessEngineConfiguration();

		// 设置数据库四要素
		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
		processEngineConfiguration.setJdbcUrl(
				"jdbc:mysql://localhost:3306/student_test?useUnicode=true&characterEncoding=utf-8&useSSL=false");
		processEngineConfiguration.setJdbcUsername("root");
		processEngineConfiguration.setJdbcPassword("123456");

		/**
		 * DB_SCHEMA_UPDATE_FALSE: 不能自动创建表，需要表存在</br>
		 * DB_SCHEMA_UPDATE_CREATE_DROP: 先删除表再创建表</br>
		 * DB_SCHEMA_UPDATE_TRUE: 如果表不存在，自动创建表
		 */
		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

		// 工作流的核心对象，ProcessEnginee对象
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.out.println("processEngine:" + processEngine);
		System.out.println("初始化Activiti数据库表结束");
	}

	// 使用配置文件方式初始化
	@Ignore
	public void testActivitiEngineFromResource() {
		// 创建Activiti配置对象的实例
		 ProcessEngineConfiguration processEngineConfiguration =
		 ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti/activiti.cfg.xml");
		 ProcessEngine processEngine =
		 processEngineConfiguration.buildProcessEngine();
		
		// 工作流的核心对象，ProcessEnginee对象
		System.out.println("processEngine:" + processEngine);
		System.out.println("初始化Activiti数据库表结束");
	}
}
