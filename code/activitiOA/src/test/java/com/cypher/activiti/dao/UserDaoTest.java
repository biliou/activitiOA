package com.cypher.activiti.dao;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.alibaba.fastjson.JSON;
import com.cypher.activiti.dao.UserMapper;
import com.cypher.activiti.dto.UserDto;

/**
 * 
 * @author bili.ou
 *
 */
public class UserDaoTest {

	private ApplicationContext ac = null;
	private UserMapper userMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		userMapper = (UserMapper) ac.getBean("userMapper");
	}

	@Test
	public void testGetUserInfoById() {
		UserDto userDto = userMapper.getUserInfoById(1L);
		System.out.println(JSON.toJSONString(userDto));
	}
	
	@Test
	public void testUpdatePwd() {
		boolean result = userMapper.updatePwd(12L, "1234");
		System.out.println(result);
	}
}
