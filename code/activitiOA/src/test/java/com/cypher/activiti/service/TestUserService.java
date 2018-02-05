package com.cypher.activiti.service;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cypher.activiti.core.encrypt.PwdEncrypt;
import com.cypher.activiti.dao.UserMapper;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.impl.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = { "classpath:springmvc/mvc-dispatcher-servlet.xml",
		"classpath:springmvc/spring-mybatis.xml" })
public class TestUserService {

	private User userTest;
	private User userAdmin;

	@Autowired
	private IUserService userService;

	@Autowired
	private UserMapper userMapper;

	@Before
	public void before() {

		// 初始化测试数据
		initAdminUser();
		initTestUser();
	}

	@Before
	public void initTestUser() {
		userTest = new User();
		userTest.setDeptId(1L);
		userTest.setUserName("tester");
		userTest.setLoginName("tester");
		String pwd = "123";
		String pwdEncrype = PwdEncrypt.encodePwd(pwd);
		userTest.setPassword(pwdEncrype);
		userTest.setUpdateBy("admin");
		userTest.setUpdateDate(new Date());
	}

	@Before
	public void initAdminUser() {
		userAdmin = new User();
		userAdmin.setUserName("keven");
		userAdmin.setLoginName("admin");
	}

	/**
	 * 测试修改密码<br/>
	 * 修改时输入密码不正确
	 */
	@Test
	public void testSaveChangePwd() {
		// 添加一个tester
		User user = new User();
		user.setLoginName("tester");
		userMapper.addUser(userTest);
		List<User> userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 1);
		user = userList.get(0);

		// 旧密码
		String oldPwd = "1234";
		// 新密码
		String newPwd = "1234";
		// 修改密码
		boolean result = userService.saveChangePwd(user.getUserId(), oldPwd, newPwd);
		assertEquals(result, false);
		
		// 删除测试用户
		userMapper.delUser(userTest.getUserId());
		// 查看是否存在一个tester
		userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 0);
	}

	/**
	 * 测试修改密码<br/>
	 * 修改时输入密码正确
	 */
	@Test
	public void testSaveChangePwd2() {
		// 添加一个tester
		User user = new User();
		user.setLoginName("tester");
		userMapper.addUser(userTest);
		List<User> userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 1);
		User addUser = userList.get(0);

		// 旧密码
		String oldPwd = "123";
		// 新密码
		String newPwd = "1234";
		// 修改密码
		boolean result = userService.saveChangePwd(addUser.getUserId(), oldPwd, newPwd);
		assertEquals(result, true);
		//获取新的用户信息
		userList = userMapper.getUserList(user);
		addUser = userList.get(0);
		result = PwdEncrypt.validataPwd(newPwd, addUser.getPassword());
		assertEquals(result, true);

		// 删除测试用户
		userMapper.delUser(addUser.getUserId());
		// 查看是否存在一个tester
		userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 0);

	}
}
