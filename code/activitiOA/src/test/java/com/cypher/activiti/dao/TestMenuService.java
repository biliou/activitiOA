package com.cypher.activiti.dao;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.cypher.activiti.model.Menu;

public class TestMenuService {
	private ApplicationContext ac = null;
	private MenuMapper menuMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		menuMapper = (MenuMapper) ac.getBean("menuMapper");
	}
	
	@Test
	public void testSelectAllMenuInfo() {
		List<Menu> menuList = menuMapper.selectAllMenuInfo();
		int len = menuList.size();
		assertThat( len, greaterThan(0));  
		System.out.println(JSON.toJSONString(menuList));
	}
}
