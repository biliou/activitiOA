package com.cypher.activiti.dao;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.cypher.activiti.model.Area;
import com.cypher.activiti.model.AreaTree;
import com.cypher.activiti.model.Dept;

public class AreaDaoTest {

	private ApplicationContext ac = null;
	private AreaMapper areaMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		areaMapper = (AreaMapper) ac.getBean("areaMapper");
	}

	// 测试获取所有区域信息
	@Test
	public void testGetAllAreaInfo() {
		List<Area> areaList = areaMapper.getAllAreaInfo();
		System.out.println(JSON.toJSONString(areaList));
		int len = areaList.size();
		assertThat(len, greaterThan(0));
	}

	// 测试通过id获取区域信息
	@Test
	public void testGetAreaById() {
		Area area = areaMapper.getAreaById(1L);
		System.out.println(JSON.toJSONString(area));
		assertEquals(area.getName(), "中国");
	}

	// 测试删除区域信息
	@Test
	public void testDelArea() {
		// TODO: 未添加然后删除
		Long areaId = 15L;
		boolean result = areaMapper.delArea(areaId);
		System.out.println("删除成功");
		Area area = areaMapper.getAreaById(areaId);
		assertEquals(area, null);
	}

	// 测试获取所有子节点个数
	@Test
	public void testGetChildrenCount() {
		int count = areaMapper.getChildrenCount(1L);
		assertEquals(count, 2);
	}

	// 测试添加区域
	@Test
	public void testInsertArea() {
		Area area = new Area();
		area.setName("test");
		area.setParentId(0L);
		area.setSort(20L);
		area.setCreateBy("1");
		area.setCreateDate(new Date());
		area.setUpdateBy("1");
		area.setUpdateDate(new Date());
		boolean result = areaMapper.addArea(area);
		System.out.println(area.getId());
		Area addArea = areaMapper.getAreaById(area.getId());
		assertEquals(addArea.getName(), area.getName());
	}

	// 测试修改区域
	@Test
	public void testUpdateArea() {
		Long updateAreaId = 15L;
		Area oldArea = areaMapper.getAreaById(updateAreaId);
		Area area = new Area();
		area.setId(updateAreaId);
		area.setName("test");
		area.setParentId(0L);
		area.setSort(20L);
		area.setCreateBy("1");
		area.setCreateDate(new Date());
		area.setUpdateBy("1");
		area.setUpdateDate(new Date());
		boolean result = areaMapper.updateArea(area);
		System.out.println(updateAreaId);
		Area updateArea = areaMapper.getAreaById(area.getId());
		System.out.println("oldName = " + oldArea.getName());
		System.out.println("updateName = " + updateArea.getName());
		assertNotEquals(oldArea.getName(), updateArea.getName());
	}

	// 测试查询某部门下的子节点 (自关联)
	@Test
	public void testGetChildByPidResultMap() {
		Area area = areaMapper.getChildByPidResultMap(1L);
		System.out.println(JSON.toJSONString(area));

	}

	// 测试查询某部门下的所有子节点 (自关联)
	@Test
	public void testGetAllChildByPidResultMap() {
		AreaTree area = areaMapper.GetAllChildByPidResultMap();
		System.out.println(JSON.toJSONString(area));
	}
}
