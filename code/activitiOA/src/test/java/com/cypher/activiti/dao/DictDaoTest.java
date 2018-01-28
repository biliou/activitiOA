package com.cypher.activiti.dao;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.alibaba.fastjson.JSON;
import com.cypher.activiti.model.Dict;

public class DictDaoTest {

	private ApplicationContext ac = null;
	private DictMapper dictMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		dictMapper = (DictMapper) ac.getBean("dictMapper");
	}

	@Test
	public void testSelectAllDictInfo() {
		List<Dict> dictList = dictMapper.selectAllDictInfoisPaged(null,null,2,3);
		int len = dictList.size();
		assertThat( len, greaterThan(0));  
		System.out.println(JSON.toJSONString(dictList));

	}
	
	@Test
	public void testSelectAllDictType() {
		List<String> dictTypeList = dictMapper.selectAllDictType();
		int len = dictTypeList.size();
		assertThat( len, greaterThan(0));  
		System.out.println(JSON.toJSONString(dictTypeList));

	}
	
	@Test
	public void testSelectDictInfoById() {
		Dict dict = dictMapper.selectDictInfoById(1L);
		assertEquals(dict.getLabel(), "正常"); 
		System.out.println(JSON.toJSONString(dict));

	}
	
	@Test
	public void testAddDict() {
		Dict dict = new Dict();
		dict.setValue("1");
		dict.setLabel("1");
		dict.setType("1");
		dict.setDescription("1");
		dict.setSort(10L);
		dict.setUpdateBy("1");
		dict.setUpdateDate(new Date());
		dict.setRemarks("1");
		boolean resultRecord = dictMapper.addDict(dict);
		System.out.println(dict.getId());
		System.out.println(resultRecord);
		Dict result = dictMapper.selectDictInfoById(dict.getId());
		System.out.println(JSON.toJSONString(result));

	}
	
	@Test
	public void testUpdateDict() {
		Dict dict = new Dict();
		dict.setId(121L);
		dict.setValue("11111");
		dict.setLabel("1");
		dict.setType("1");
		dict.setDescription("1");
		dict.setSort(10L);
		dict.setUpdateBy("1");
		dict.setUpdateDate(new Date());
		dict.setRemarks("1");
		boolean resultRecord = dictMapper.updateDict(dict);
		System.out.println(resultRecord);
		Dict result = dictMapper.selectDictInfoById(dict.getId());
		System.out.println(JSON.toJSONString(result));

	}
}
