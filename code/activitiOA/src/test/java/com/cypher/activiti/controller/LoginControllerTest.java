package com.cypher.activiti.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.cypher.activiti.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springmvc/mvc-dispatcher-servlet.xml",
		"classpath:springmvc/spring-mybatis.xml" })
//配置事务的回滚,对数据库的增删改都会回滚,便于测试用例的循环利用
@Rollback
@Transactional
@WebAppConfiguration
public class LoginControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;
	private MockHttpSession session;

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
	 * 测试跳转登录页 goToLogin
	 * 
	 * @throws Exception
	 */
	@Test
	public void testGoToLogin() throws Exception {
		mockMvc.perform(get("/login") // 请求的hurl,请求的方法是get
				.accept(MediaType.parseMediaType("application/json;charset=UTF-8"))// 数据的格式
		).andExpect(status().isOk())// 返回的状态是200
				.andExpect(view().name("login"));
	}

	/**
	 * 测试登录功能<br/>
	 * 成功登录
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogin() throws Exception {
		mockMvc.perform(post("/login")// 请求地址
				.param("loginName", "admin") // 参数
				.param("password", "123")// 参数
				.accept("application/json;charset=UTF-8"))// 请求格式
				.andExpect(MockMvcResultMatchers.status().is3xxRedirection())// 返回的状态是302
				.andExpect(MockMvcResultMatchers.redirectedUrl("main"));// 重定向
	}

	/**
	 * 测试登录功能<br/>
	 * 登录失败，输入参数为空<br/>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogin2() throws Exception {
		mockMvc.perform(post("/login")// 请求地址
				.param("loginName", "") // 参数
				.param("password", "123")// 参数
				.accept("application/json;charset=UTF-8"))// 请求格式
				.andExpect(MockMvcResultMatchers.status().isOk())// 返回的状态是200
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/pages/login.jsp"))// 转发
				.andExpect(MockMvcResultMatchers.model().attribute("loginFlag", "登录失败,请输入正确的用户名 和 密码")); // 验证存储模型数据
	}

	/**
	 * 测试登录功能<br/>
	 * 登录失败，输入错误参数<br/>
	 * 
	 * @throws Exception
	 */
	@Test
	public void testLogin3() throws Exception {
		mockMvc.perform(post("/login")// 请求地址
				.param("loginName", "11") // 参数
				.param("password", "123")// 参数
				.accept("application/json;charset=UTF-8"))// 请求格式
				.andExpect(MockMvcResultMatchers.status().isOk())// 返回的状态是200
				.andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/pages/login.jsp"))// 转发
				.andExpect(MockMvcResultMatchers.model().attribute("loginFlag", "登录失败")); // 验证存储模型数据
	}

	/**
	 * 测试跳转主页成功
	 * 
	 * @throws Exception
	 */
	@Test
	public void testMain() throws Exception {
		mockMvc.perform(get("/main")// 请求地址
				.session(session)// 传入session
				.accept("application/json;charset=UTF-8"))// 请求格式
				.andExpect(MockMvcResultMatchers.status().isOk())// 返回的状态200
				.andExpect(MockMvcResultMatchers.model().attribute("userName", "keven")) // 用户名
				.andExpect(MockMvcResultMatchers.model().attributeExists("menuList")) // 存在model属性 menuList
				.andExpect(MockMvcResultMatchers.view().name("main/main"));
	}
	
	/**
	 * 测试登录功能
	 * @throws Exception
	 */
	@Test 
	public void testLogout() throws Exception {
		mockMvc.perform(get("/logout")//请求地址
		.session(session) //传入session
		.accept("application/json;charset=UTF-8"))// 请求格式
		.andExpect(MockMvcResultMatchers.status().is3xxRedirection())//状态302
		.andExpect(MockMvcResultMatchers.redirectedUrl("/")); //重定向
	}
}
