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

import com.cypher.activiti.model.Dept;

/**
 * 测试部门功能Dao接口
 * 
 * @author Administrator
 *
 */
public class DeptDaoTest {
	private ApplicationContext ac = null;
	private DeptMapper deptMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		deptMapper = (DeptMapper) ac.getBean("deptMapper");
	}

	public Dept initTestDept() {
		Dept deptTest = new Dept();
		deptTest.setName("test");
		deptTest.setParentId(1L);
		deptTest.setSort(20L);
		deptTest.setUpdateBy("1");
		deptTest.setUpdateDate(new Date());
		return deptTest;
	}

	/**
	 * 获取部门所有信息
	 */
	@Test
	public void testGetAllDeptInfo() {
		List<Dept> deptList = deptMapper.getAllDeptInfo();
		int len = deptList.size();
		assertThat(len, greaterThan(0));
		assertEquals(len, 1);
	}

	/**
	 * 通过id获取部门信息
	 */
	@Test
	public void testGetDeptById() {
		Dept dept = deptMapper.getDeptInfoById(1L);
		Long deptId = 0L;
		assertEquals(dept.getName(), "潭州学院");
		assertEquals(dept.getParentId(), deptId);
		assertEquals(dept.getCode(), "100000");
	}

	/**
	 * 删除部门
	 */
	@Test
	public void testDelDept() {
		// 添加一个tester
		Dept deptTest = initTestDept();
		deptMapper.addDept(deptTest);
		Dept result = deptMapper.getDeptInfoById(deptTest.getId());
		assertEquals(result.getName(), deptTest.getName());
		assertEquals(result.getParentId(), result.getParentId());

		// 删除测试部门
		deptMapper.delDept(result.getId());

		// 查看是否存在一个tester
		result = deptMapper.getDeptInfoById(result.getId());
		assertEquals(result, null);
	}

	/**
	 * 获取子节点个数
	 */
	@Test
	public void testGetChildrenCount() {
		int count = deptMapper.getChildrenCount(0L);
		assertThat(count, greaterThan(0));
		assertEquals(count, 1);
	}

	/**
	 * 添加部门
	 */
	@Test
	public void testAddDept() {
		// 添加一个tester
		Dept deptTest = initTestDept();
		deptMapper.addDept(deptTest);
		Dept result = deptMapper.getDeptInfoById(deptTest.getId());
		assertEquals(result.getName(), deptTest.getName());
		assertEquals(result.getParentId(), result.getParentId());

		// 删除测试部门
		deptMapper.delDept(result.getId());

		// 查看是否存在一个tester
		result = deptMapper.getDeptInfoById(result.getId());
		assertEquals(result, null);
	}

	/**
	 * 修改部门
	 */
	@Test
	public void testUpdateDept() {
		// 添加一个tester
		Dept deptTest = initTestDept();
		deptMapper.addDept(deptTest);
		Dept result = deptMapper.getDeptInfoById(deptTest.getId());
		assertEquals(result.getName(), deptTest.getName());
		assertEquals(result.getParentId(), result.getParentId());

		// 更新部门Name，parentId，code
		String newName = "test2";
		Long newParentId = 1L;
		String newCode = "010";
		deptTest.setName(newName);
		deptTest.setParentId(newParentId);
		deptTest.setCode(newCode);
		deptMapper.updateDept(deptTest);
		Dept updateDept = deptMapper.getDeptInfoById(deptTest.getId());
		assertEquals(updateDept.getName(), newName);
		assertEquals(updateDept.getCode(), newCode);
		assertEquals(updateDept.getParentId(), newParentId);

		// 删除测试部门
		deptMapper.delDept(updateDept.getId());

		// 查看是否存在一个tester
		result = deptMapper.getDeptInfoById(deptTest.getId());
		assertEquals(result, null);

	}
}
