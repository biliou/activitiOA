package com.cypher.activiti.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:springmvc/mvc-dispatcher-servlet.xml",
		"classpath:springmvc/spring-mybatis.xml" })
@WebAppConfiguration
public class LoginControllerTest {
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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

}
