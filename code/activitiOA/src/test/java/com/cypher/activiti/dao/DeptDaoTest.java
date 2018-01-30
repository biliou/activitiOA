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
import com.cypher.activiti.model.Dept;

public class DeptDaoTest {
	private ApplicationContext ac = null;
	private DeptMapper deptMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		deptMapper = (DeptMapper) ac.getBean("deptMapper");
	}

	// 获取部门所有信息
	@Test
	public void testGetAllDeptInfo() {
		List<Dept> deptList = deptMapper.getAllDeptInfo();
		System.out.println(JSON.toJSONString(deptList));
		assertThat(deptList.size(), greaterThan(0));
	}

	// 通过id获取部门信息
	@Test
	public void testGetDeptById() {
		Dept dept = deptMapper.getDeptInfoById(1L);
		System.out.println(JSON.toJSONString(dept));
		assertEquals(dept.getName(), "潭州学院");
	}

	// 删除部门
	@Test
	public void testDelDept() {
		Long deptId = 28L;
		boolean result = deptMapper.delDept(deptId);
		System.out.println("删除成功");
		Dept dept = deptMapper.getDeptInfoById(deptId);
		assertEquals(dept, null);
	}

	// 获取子节点个数
	@Test
	public void testGetChildrenCount() {
		Long parentId = 22L;
		int count = deptMapper.getChildrenCount(parentId);
		assertThat(count, greaterThan(0));
	}

	// 添加部门
	@Test
	public void testAddDept() {
		String name = "test";
		Dept dept = new Dept();
		dept.setName(name);
		dept.setParentId(1L);
		dept.setSort(20L);
		dept.setUpdateBy("1");
		dept.setUpdateDate(new Date());

		deptMapper.addDept(dept);
		System.out.println("添加成功");
		Dept result = deptMapper.getDeptInfoById(dept.getId());
		System.out.println(JSON.toJSONString(result));
		assertEquals(dept.getName(), result.getName());
	}

	// 修改部门
	@Test
	public void testUpdateDept() {
		Long id = 29L;
		Dept result = deptMapper.getDeptInfoById(id);
		Dept dept = new Dept();
		dept.setId(id);
		dept.setName("tester");
		dept.setParentId(1L);
		dept.setSort(20L);
		dept.setUpdateBy("1");
		dept.setUpdateDate(new Date());

		deptMapper.updateDept(dept);
		System.out.println("修改成功");

		System.out.println(JSON.toJSONString(result));
		assertNotEquals(dept.getName(), result.getName());
	}
}
