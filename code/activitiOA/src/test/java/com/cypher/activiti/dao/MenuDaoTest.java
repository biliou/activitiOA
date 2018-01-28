package com.cypher.activiti.dao;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.cypher.activiti.model.Menu;

public class MenuDaoTest {
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
		assertThat(len, greaterThan(0));
		System.out.println(JSON.toJSONString(menuList));
	}

	@Test
	public void testGetMenuInfo() {
		Menu menu = menuMapper.getMenuInfo(1L);
		assertEquals(menu.getName(), "功能菜单xx");
		System.out.println(JSON.toJSONString(menu));
	}

	/**
	 * 测试删除菜单
	 */
	@Test
	public void testDelMenu() {
		boolean result = menuMapper.delMenu(33L);
		Menu menu = menuMapper.getMenuInfo(33L);
		assertEquals(menu, null);
		System.out.println(JSON.toJSONString(menu));
	}

	/**
	 * 测试更新菜单
	 */
	@Test
	public void testUpdateMenu() {
		long menuId = 33L;
		String menuName = "yoyo";
		Menu menu = new Menu();
		menu.setId(menuId);
		menu.setName("yoyo");

		boolean result = menuMapper.updateMenu(menu);
		Menu menuResult = menuMapper.getMenuInfo(menuId);
		assertEquals(menuResult.getName(), menuName);
	}
	
	/**
	 * 测试添加菜单
	 */
	@Test
	public void testAddMenu() {

		String menuName = "yoyo";
		Menu menu = new Menu();
		menu.setName("yoyo");
		menu.setSort(20L);
		menu.setIsShow("0");
		menu.setUpdateBy("1");
		menu.setUpdateDate(new Date());
		menu.setParentId(1L);
		menu.setDelFlag("0");
		

		boolean result = menuMapper.addMenu(menu);
		System.out.println(menu.getId());
		
		Menu menuResult = menuMapper.getMenuInfo(menu.getId());
		assertEquals(menuResult.getName(), menuName);
	}
}
