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

import com.cypher.activiti.model.Area;
import com.cypher.activiti.model.AreaTree;

/**
 * 测试区域功能Dao接口
 * 
 * @author Administrator
 *
 */
public class AreaDaoTest {

	private ApplicationContext ac = null;
	private AreaMapper areaMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		areaMapper = (AreaMapper) ac.getBean("areaMapper");
	}

	public Area initTestArea() {
		Area areaTest = new Area();
		areaTest.setName("test");
		areaTest.setParentId(0L);
		areaTest.setSort(20L);
		areaTest.setUpdateBy("1");
		areaTest.setUpdateDate(new Date());
		return areaTest;
	}

	/**
	 * 测试获取所有区域信息
	 */
	@Test
	public void testGetAllAreaInfo() {
		List<Area> areaList = areaMapper.getAllAreaInfo();
		int len = areaList.size();
		assertThat(len, greaterThan(0));
		assertEquals(len, 1);
	}

	/**
	 * 测试通过id获取区域信息
	 */
	@Test
	public void testGetAreaById() {
		Area area = areaMapper.getAreaById(1L);
		Long areaId = 0L;
		assertEquals(area.getName(), "中国");
		assertEquals(area.getCode(), "086");
		assertEquals(area.getParentId(), areaId);
	}

	/**
	 * 测试删除区域信息
	 */
	@Test
	public void testDelArea() {
		// 添加一个tester
		Area areaTest = initTestArea();
		areaMapper.addArea(areaTest);
		Area result = areaMapper.getAreaById(areaTest.getId());
		assertEquals(result.getName(), areaTest.getName());
		assertEquals(result.getParentId(), result.getParentId());

		// 删除测试区域
		areaMapper.delArea(result.getId());

		// 查看是否存在一个tester
		result = areaMapper.getAreaById(result.getId());
		assertEquals(result, null);

	}

	/**
	 * 测试获取所有子节点个数
	 */
	@Test
	public void testGetChildrenCount() {
		int count = areaMapper.getChildrenCount(1L);
		assertEquals(count, 0);
	}

	/**
	 * 测试添加区域
	 */
	@Test
	public void testInsertArea() {
		// 添加一个tester
		Area areaTest = initTestArea();
		areaMapper.addArea(areaTest);
		Area result = areaMapper.getAreaById(areaTest.getId());
		assertEquals(result.getName(), areaTest.getName());
		assertEquals(result.getParentId(), result.getParentId());

		// 删除测试区域
		areaMapper.delArea(result.getId());

		// 查看是否存在一个tester
		result = areaMapper.getAreaById(result.getId());
		assertEquals(result, null);
	}

	/**
	 *  测试修改区域
	 */
	@Test
	public void testUpdateArea() {
		// 添加一个tester
		Area areaTest = initTestArea();
		areaMapper.addArea(areaTest);
		Area result = areaMapper.getAreaById(areaTest.getId());
		assertEquals(result.getName(), areaTest.getName());
		assertEquals(result.getParentId(), result.getParentId());

		// 更新区域Name，parentId，code
		String newName = "test2";
		Long newParentId = 1L;
		String newCode = "010";
		areaTest.setName(newName);
		areaTest.setParentId(newParentId);
		areaTest.setCode(newCode);
		areaMapper.updateArea(areaTest);
		Area updateArea = areaMapper.getAreaById(areaTest.getId());
		assertEquals(updateArea.getName(), newName);
		assertEquals(updateArea.getCode(), newCode);
		assertEquals(updateArea.getParentId(), newParentId);

		// 删除测试区域
		areaMapper.delArea(updateArea.getId());
		
		// 查看是否存在一个tester
		result = areaMapper.getAreaById(areaTest.getId());
		assertEquals(result, null);
		
	}

	/**
	 *  测试查询某部门下的子节点 (自关联)
	 */
	@Test
	public void testGetChildByPidResultMap() {
		Area area = areaMapper.getChildByPidResultMap(1L);
		assertEquals(area, null);
	}

	/**
	 *  测试查询某部门下的所有子节点 (自关联)
	 */
	@Test
	public void testGetAllChildByPidResultMap() {
		AreaTree areaTree = areaMapper.GetAllChildByPidResultMap();
//		System.out.println(JSON.toJSONString(areaTree));
		Long areaId = 0L;
		assertEquals(areaTree.getChileAreaList().size(), 0);
		assertEquals(areaTree.getName(), "中国");
		assertEquals(areaTree.getParentId(), areaId);
	}
}
