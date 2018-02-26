package com.cypher.activiti.dao;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.cypher.activiti.core.encrypt.PwdEncrypt;
import com.cypher.activiti.dto.UserDto;
import com.cypher.activiti.model.User;
import com.cypher.activiti.model.UserToRole;
import com.cypher.activiti.util.EncryptUtil;

/**
 * 测试用户管理功能Dao接口
 *
 */
public class UserDaoTest {

	private ApplicationContext ac = null;
	private UserMapper userMapper = null;

	private User userTest;
	private User userAdmin;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		userMapper = (UserMapper) ac.getBean("userMapper");

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
	 * 测试根据条件查询 用户列表 <br/>
	 * 测试用户使用LoginName查询,查询到用户admin
	 */
	@Test
	public void testGetUserList() {
		User user = new User();
		user.setLoginName("admin");

		List<User> userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 1);
		assertEquals(userList.get(0).getUserName(), userAdmin.getUserName());
	}

	/**
	 * 测试根据条件查询 用户列表 <br/>
	 * 测试用户使用不存在的用户查询
	 */
	@Test
	public void testGetUserList2() {
		User user = new User();
		user.setLoginName("1");

		List<User> userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 0);
	}

	// 测试根据用户id查询 用户信息
	@Test
	public void testGetUserInfoById() {
		UserDto userDto = userMapper.getUserInfoById(1L);
//		System.out.println(JSON.toJSON(userDto));
		assertEquals(userDto.getUserName(), userAdmin.getUserName());
		assertEquals(userDto.getLoginName(), userAdmin.getLoginName());
	}

	// 测试保存用户信息并返回用户id
	@Test
	public void testUpdateUser() {
		// 查看是否存在一个tester
		User user = new User();
		user.setLoginName("tester");
		List<User> userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 0);

		// 添加一个tester
		userMapper.addUser(userTest);
		userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 1);

		// 更新tester的名字
		String oldName = userTest.getUserName();
		userTest.setUserName("tester1");
		userMapper.updateUser(userTest);
		assertNotEquals(userTest.getUserName(), oldName);

		// 删除测试用户
		userMapper.delUser(userTest.getUserId());
		// 查看是否存在一个tester
		userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 0);

	}

	// 测试保存用户修改密码
	@Test
	public void testUpdatePwd() {

		// 添加一个tester
		User user = new User();
		user.setLoginName("tester");
		userMapper.addUser(userTest);
		List<User> userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 1);

		// 更新密码
		String newPwd = "1234";
		String oldPwd = userTest.getPassword();
		userMapper.updatePwd(userTest.getUserId(), newPwd);
		userList = userMapper.getUserList(user);
		User updateUser = userList.get(0);
		assertEquals(updateUser.getPassword(), newPwd);
		assertNotEquals(oldPwd, newPwd);

		// 删除测试用户
		userMapper.delUser(userTest.getUserId());
		// 查看是否存在一个tester
		userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 0);
	}

	// 测试获取所有用户信息,返回User 类型对象
	@Test
	public void testGetAllUserInfo() {
		List<User> userList = userMapper.getAllUserInfo();
		assertThat(userList.size(), greaterThan(0));
		assertEquals(userList.get(0).getLoginName(), "admin");
	}

	/**
	 * 测试获取所有用户信息,返回UserDto类型对象 <br/>
	 * 通过用户名搜索
	 */
	@Test
	public void testGetUserDtoList() {
		User user = new User();
		user.setUserName(userAdmin.getUserName());

		List<UserDto> userDtoList = userMapper.getUserDtoList(user);
		assertThat(userDtoList.size(), greaterThan(0));
		assertEquals(userDtoList.get(0).getUserName(), user.getUserName());
	}

	// 测试新增用户信息
	@Test
	public void testAddUser() {
		// 添加一个tester
		User user = new User();
		user.setLoginName("tester");
		userMapper.addUser(userTest);
		List<User> userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 1);
		assertEquals(userList.get(0).getUserName(), userTest.getUserName());
		assertEquals(userList.get(0).getLoginName(), userTest.getLoginName());

		// 删除测试用户
		userMapper.delUser(userTest.getUserId());
		// 查看是否存在一个tester
		userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 0);

	}

	// 测试删除用户
	@Test
	public void testDelUser() {
		// 添加一个tester
		User user = new User();
		user.setLoginName("tester");
		userMapper.addUser(userTest);
		List<User> userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 1);
		assertEquals(userList.get(0).getUserName(), userTest.getUserName());
		assertEquals(userList.get(0).getLoginName(), userTest.getLoginName());

		// 删除测试用户
		userMapper.delUser(userTest.getUserId());
		// 查看是否存在一个tester
		userList = userMapper.getUserList(user);
		assertEquals(userList.size(), 0);
	}

	// 测试根据用户id查询用户所拥有的角色
	@Test
	public void testGetUserRoleByUserId() {
		List<UserToRole> userRoleList = userMapper.getUserRoleByUserId(1L);
		assertEquals(userRoleList.size(), 1);
		Long roleId = 1l;
		assertEquals(userRoleList.get(0).getRoleId(), roleId);
	}

	// 测试根据用户id查询用户所拥有的角色和角色名
	@Test
	public void testGetUserRoleNameListByUserId() {
		List<String> userRoleNameList = userMapper.getUserRoleNameListByUserId(1L);
		// System.out.println(JSON.toJSONString(userRoleNameList));
		assertEquals(userRoleNameList.size(), 1);
	}

}
