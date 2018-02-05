package com.cypher.activiti.util;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.cypher.activiti.dao.MenuMapper;
import com.cypher.activiti.model.Menu;

public class TreeUtilsTest {
	private ApplicationContext ac = null;
	private MenuMapper menuMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		menuMapper = (MenuMapper) ac.getBean("menuMapper");
	}
	
	@Ignore
	public void testTreeUtilsForMenuList() {
		List<Menu> menuList = menuMapper.getAllMenuInfo();

		List<Menu> sortMenuList = new ArrayList<Menu>();
		// 因为前台组件treeTable正常显示树形结构的数据,就必须让我们的列表按照树形的结构顺序摆放
		TreeUtils.sortTreeList(sortMenuList, menuList, 0l);

		System.out.println(JSON.toJSONString(menuList));
		System.out.println(JSON.toJSONString(sortMenuList));
	}
}
