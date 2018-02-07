package com.cypher.activiti.controller;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cypher.activiti.dao.RoleDaoTest;
import com.cypher.activiti.dao.RoleMapper;
import com.cypher.activiti.model.Role;
import com.cypher.activiti.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springmvc/mvc-dispatcher-servlet.xml",
		"classpath:springmvc/spring-mybatis.xml" })
// 配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@Transactional
@WebAppConfiguration
public class RoleControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	private MockHttpSession session;

	private ApplicationContext ac = null;
	private RoleMapper roleMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		roleMapper = (RoleMapper) ac.getBean("roleMapper");
	}

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		session = new MockHttpSession();

		User user = new User();
		user.setUserId(1L);
		user.setLoginName("admin");
		user.setUserName("keven");

		session.setAttribute("user", user);

	}

	/**
	 * 测试跳转角色列表页 gotoRoleList
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGotoRoleList() throws Exception {
		mockMvc.perform(get("/sysmg/role/gotoRoleList") // 请求的hurl,请求的方法是get
				.session(session)// 传入session
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8")))// 数据的格式
				.andExpect(MockMvcResultMatchers.status().isOk())// 返回的状态是200
				.andExpect(MockMvcResultMatchers.model().attributeExists("roleList")) // 验证model
				.andExpect(MockMvcResultMatchers.view().name("/sysmg/role/roleList")); // 验证跳转
	}

	/**
	 * 跳转角色编辑页<br/>
	 * 添加操作，参数editFlag=1
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGotoRoleEdit() throws Exception {
		mockMvc.perform(get("/sysmg/role/gotoRoleEdit")// 请求地址
				.param("editFlag", "1") // 参数
				.session(session)// 传入session
				.accept("application/json;charset=UTF-8"))// 请求格式
				.andExpect(MockMvcResultMatchers.status().isOk())// 返回的状态是200
				.andExpect(MockMvcResultMatchers.model().attributeDoesNotExist("role")) // 验证model
				.andExpect(MockMvcResultMatchers.view().name("/sysmg/role/roleEdit"));// 验证跳转
	}

	/**
	 * 跳转角色编辑页<br/>
	 * 添加操作，参数editFlag=2<br/>
	 * roleId = 1
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGotoRoleEdit2() throws Exception {
		mockMvc.perform(get("/sysmg/role/gotoRoleEdit")// 请求地址
				.param("editFlag", "2") // 参数
				.param("roleId", "1") // 参数
				.session(session)// 传入session
				.accept("application/json;charset=UTF-8"))// 请求格式
				.andExpect(MockMvcResultMatchers.status().isOk())// 返回的状态是200
				.andExpect(MockMvcResultMatchers.model().attributeExists("role")) // 验证model
				.andExpect(MockMvcResultMatchers.view().name("/sysmg/role/roleEdit"));// 验证跳转
	}

	/**
	 * 测试添加角色<br/>
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testAddRole() throws Exception {
		// 初始化参数
		Role role = new Role();
		role.setName("test");

		Map<String, String> areaIds = new HashMap<String, String>();
		areaIds.put("2", "2");
		Map<String, String> deptIds = new HashMap<String, String>();
		deptIds.put("2", "2");
		Map<String, String> menuIds = new HashMap<String, String>();
		menuIds.put("2", "2");

		JSONObject params = new JSONObject();
		params.put("role", role);
		params.put("areaIds", areaIds);
		params.put("deptIds", deptIds);
		params.put("menuIds", menuIds);

		// 格式留意！！！！ 传入的参数必须都以字符串形式
		// System.out.println(JSON.toJSONString(params));
		// String requestJson =
		// "{\"role\":{\"id\":\"\",\"name\":\"test\",\"remarks\":\"\"},\"menuIds\":{\"1\":\"1\"},\"deptIds\":{},\"areaIds\":{}}";

		mockMvc.perform(post("/sysmg/role/saveRole")// 请求地址
				.contentType(MediaType.APPLICATION_JSON)// 参数格式
				.content(JSON.toJSONString(params)) // 参数
				.session(session))// 传入session
				.andExpect(MockMvcResultMatchers.status().isOk())// 返回的状态是200
				.andExpect(MockMvcResultMatchers.jsonPath("$.result").value("增加角色信息成功"));
	}

	/**
	 * 测试修改角色<br/>
	 * 
	 * 
	 * @throws Exception
	 */
	@Test
	public void testUpdateRole() throws Exception {
		// 初始化参数
		Role role = new Role();
		role.setId(1L);
		role.setName("test");

		Map<String, String> areaIds = new HashMap<String, String>();
		areaIds.put("2", "2");
		Map<String, String> deptIds = new HashMap<String, String>();
		deptIds.put("2", "2");
		Map<String, String> menuIds = new HashMap<String, String>();
		menuIds.put("2", "2");

		JSONObject params = new JSONObject();
		params.put("role", role);
		params.put("areaIds", areaIds);
		params.put("deptIds", deptIds);
		params.put("menuIds", menuIds);

		mockMvc.perform(post("/sysmg/role/saveRole")// 请求地址
				.contentType(MediaType.APPLICATION_JSON)// 参数格式
				.content(JSON.toJSONString(params)) // 参数
				.session(session))// 传入session
				.andExpect(MockMvcResultMatchers.status().isOk())// 返回的状态是200
				.andExpect(MockMvcResultMatchers.jsonPath("$.result").value("修改角色信息成功"));

	}

	/**
	 * 测试删除角色成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testDelRelo() throws Exception {
		// 添加角色
		RoleDaoTest roleDaoTest = new RoleDaoTest();
		Role roleTest = roleDaoTest.initTestRole();
		roleMapper.addRole(roleTest);
		Role result = roleMapper.getRoleById(roleTest.getId());
		assertEquals(result.getName(), roleTest.getName());

		// 删除
		String requestUrl = "/sysmg/role/delRole/" + 2;
		mockMvc.perform(delete(requestUrl)// 请求地址
				.accept("application/json;charset=UTF-8")// 请求格式
				.session(session))// 传入session
				.andExpect(MockMvcResultMatchers.status().isOk())// 返回的状态是200
				.andExpect(MockMvcResultMatchers.jsonPath("$.result").value("删除角色信息成功"));

		// 删除测试角色
		roleMapper.delRole(roleTest.getId());

		// 查看是否存在一个tester
		result = roleMapper.getRoleById(roleTest.getId());
		assertEquals(result, null);
	}
}
