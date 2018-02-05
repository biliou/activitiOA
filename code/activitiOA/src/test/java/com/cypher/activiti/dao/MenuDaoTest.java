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

/**
 * 测试菜单管理功能Dao接口
 * 
 * @author Administrator
 *
 */
public class MenuDaoTest {
	private ApplicationContext ac = null;
	private MenuMapper menuMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		menuMapper = (MenuMapper) ac.getBean("menuMapper");
	}

	public Menu initTestMenu() {
		Menu menuTest = new Menu();
		menuTest.setName("test");
		menuTest.setSort(20L);
		menuTest.setIsShow("1");
		menuTest.setUpdateBy("1");
		menuTest.setUpdateDate(new Date());
		menuTest.setParentId(1L);
		menuTest.setHref("/sysmg/test");
		return menuTest;
	}

	/**
	 * 测试查询所有菜单
	 */
	@Test
	public void testSelectAllMenuInfo() {
		List<Menu> menuList = menuMapper.getAllMenuInfo();
		int len = menuList.size();
		assertThat(len, greaterThan(0));
		assertEquals(menuList.get(0).getName(), "功能菜单");
	}

	/**
	 * 测试通过菜单id查询菜单信息
	 */
	@Test
	public void testgetMenuById() {
		Menu menu = menuMapper.getMenuById(1L);
		assertEquals(menu.getName(), "功能菜单");
		Long parentId = 0L;
		assertEquals(menu.getParentId(), parentId);
		assertEquals(menu.getHref(), "");
		assertEquals(menu.getIsShow(), "1");
	}

	/**
	 * 测试删除菜单
	 */
	@Test
	public void testDelMenu() {
		// 添加一个tester
		Menu menuTest = initTestMenu();
		menuMapper.addMenu(menuTest);
		Menu result = menuMapper.getMenuById(menuTest.getId());
		assertEquals(result.getName(), menuTest.getName());
		assertEquals(result.getHref(), menuTest.getHref());
		assertEquals(result.getParentId(), menuTest.getParentId());

		// 删除测试菜单
		menuMapper.delMenu(result.getId());

		// 查看是否存在一个tester
		result = menuMapper.getMenuById(menuTest.getId());
		assertEquals(result, null);
	}

	/**
	 * 测试修改菜单
	 */
	@Test
	public void testUpdateMenu() {
		// 添加一个tester
		Menu menuTest = initTestMenu();
		menuMapper.addMenu(menuTest);
		Menu result = menuMapper.getMenuById(menuTest.getId());
		assertEquals(result.getName(), menuTest.getName());
		assertEquals(result.getHref(), menuTest.getHref());
		assertEquals(result.getParentId(), menuTest.getParentId());

		// 更新菜单Name，parentId，href
		String newName = "test2";
		Long newParentId = 2L;
		String newHref = "testhref";
		menuTest.setName(newName);
		menuTest.setParentId(newParentId);
		menuTest.setHref(newHref);
		menuMapper.updateMenu(menuTest);
		Menu updateMenu = menuMapper.getMenuById(menuTest.getId());
		assertEquals(updateMenu.getName(), newName);
		assertEquals(updateMenu.getHref(), newHref);
		assertEquals(updateMenu.getParentId(), newParentId);

		// 删除测试菜单
		menuMapper.delMenu(updateMenu.getId());

		// 查看是否存在一个tester
		result = menuMapper.getMenuById(updateMenu.getId());
		assertEquals(result, null);
	}

	/**
	 * 测试添加菜单
	 */
	@Test
	public void testAddMenu() {

		// 添加一个tester
		Menu menuTest = initTestMenu();
		menuMapper.addMenu(menuTest);
		Menu result = menuMapper.getMenuById(menuTest.getId());
		assertEquals(result.getName(), menuTest.getName());
		assertEquals(result.getHref(), menuTest.getHref());
		assertEquals(result.getParentId(), menuTest.getParentId());

		// 删除测试菜单
		menuMapper.delMenu(result.getId());

		// 查看是否存在一个tester
		result = menuMapper.getMenuById(menuTest.getId());
		assertEquals(result, null);
	}
}
