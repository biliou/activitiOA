package com.cypher.activiti.dao;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import java.util.Date;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.alibaba.fastjson.JSON;
import com.cypher.activiti.model.Dict;

/**
 * 测试字典功能Dao接口
 * @author Administrator
 *
 */
public class DictDaoTest {

	private ApplicationContext ac = null;
	private DictMapper dictMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		dictMapper = (DictMapper) ac.getBean("dictMapper");
	}

	public Dict initTestDict() {
		Dict dictTest = new Dict();
		dictTest.setValue("0");
		dictTest.setLabel("1");
		dictTest.setType("test type");
		dictTest.setDescription("test desp");
		dictTest.setSort(10L);
		dictTest.setUpdateBy("1");
		dictTest.setUpdateDate(new Date());
		dictTest.setRemarks("1");
		return dictTest;
	}

	/**
	 * 测试查询所有字典信息,无查询条件</br>
	 * 使用原生的SQL进行分页</br>
	 */
	@Test
	public void testSelectAllDictInfoisPaged() {
		List<Dict> dictList = dictMapper.selectAllDictInfoisPaged(null, null, 2, 3);
		int len = dictList.size();
		assertThat(len, greaterThan(0));
		Long dictId = 4L;
		assertEquals(dictList.get(0).getId(), dictId);
		assertEquals(dictList.get(0).getType(), "show_hide");
	}

	/**
	 * 测试查询所有字典信息,带查询条件type</br>
	 * 使用原生的SQL进行分页</br>
	 */
	@Test
	public void testSelectAllDictInfoisPaged2() {
		List<Dict> dictList = dictMapper.selectAllDictInfoisPaged("del_flag", null, 1, 3);
		int len = dictList.size();
		assertEquals(len, 2);
		Long dictId = 1L;
		assertEquals(dictList.get(0).getId(), dictId);
		assertEquals(dictList.get(0).getType(), "del_flag");
	}

	/**
	 * 测试查询所有字典信息,带查询条件desp</br>
	 * 使用原生的SQL进行分页</br>
	 */
	@Test
	public void testSelectAllDictInfoisPaged3() {
		List<Dict> dictList = dictMapper.selectAllDictInfoisPaged(null, "类型", 1, 3);
		int len = dictList.size();
		assertEquals(len, 3);
		Long dictId = 17L;
		assertEquals(dictList.get(0).getId(), dictId);
		assertEquals(dictList.get(0).getType(), "sys_area_type");
	}

	/**
	 * 测试查询所有字典信息,无查询条件</br>
	 * 未分页</br>
	 */
	@Test
	public void testSelectAllDictInfo() {
		List<Dict> dictList = dictMapper.selectAllDictInfo(null, null);
		int len = dictList.size();
		assertEquals(len, 32);
	}

	/**
	 * 测试查询所有字典信息,带查询条件type</br>
	 * 未分页</br>
	 */
	@Test
	public void testSelectAllDictInfo2() {
		List<Dict> dictList = dictMapper.selectAllDictInfo("show_hide", null);
		int len = dictList.size();
		assertEquals(len, 2);
		Long dictId = 3L;
		assertEquals(dictList.get(0).getId(), dictId);
		assertEquals(dictList.get(0).getValue(), "1");
		assertEquals(dictList.get(0).getLabel(), "显示");
	}

	/**
	 * 测试查询所有字典信息,带查询条件desp</br>
	 * 未分页</br>
	 */
	@Test
	public void testSelectAllDictInfo3() {
		List<Dict> dictList = dictMapper.selectAllDictInfo(null, "数据范围");
		int len = dictList.size();
		assertEquals(len, 7);
		Long dictId = 32L;
		assertEquals(dictList.get(0).getId(), dictId);
		assertEquals(dictList.get(0).getValue(), "1");
		assertEquals(dictList.get(0).getLabel(), "所有数据");
	}

	/**
	 * 测试查询所有字典类型</br>
	 */
	@Test
	public void testSelectAllDictType() {
		List<String> dictTypeList = dictMapper.selectAllDictType();
		int len = dictTypeList.size();
		assertThat(len, greaterThan(0));
		assertEquals(len, 10);

	}

	/**
	 * 测试通过id查询字典
	 */
	@Test
	public void testSelectDictInfoById() {
		Dict dict = dictMapper.selectDictInfoById(1L);
		assertEquals(dict.getType(), "del_flag");
		assertEquals(dict.getValue(), "0");
		assertEquals(dict.getLabel(), "正常");
		assertEquals(dict.getDescription(), "删除标记");
	}

	/**
	 * 测试删除一个字典信息
	 */
	@Test
	public void testDelDict() {
		// 添加一个tester
		Dict dictTest = initTestDict();
		dictMapper.addDict(dictTest);
		List<Dict> dictList = dictMapper.selectAllDictInfo("test type", null);
		Dict result = dictList.get(0);
		assertEquals(dictList.size(), 1);
		assertNotEquals(result.getId(), null);

		// 删除测试用户
		dictMapper.delDict(result.getId());
		// 查看是否存在一个tester
		dictList = dictMapper.selectAllDictInfo("test type", null);
		assertEquals(dictList.size(), 0);
	}

	/**
	 * 测试修改字典信息
	 */
	@Test
	public void testUpdateDict() {
		// 添加一个tester
		Dict dictTest = initTestDict();
		dictMapper.addDict(dictTest);
		List<Dict> dictList = dictMapper.selectAllDictInfo("test type", null);
		Dict result = dictList.get(0);
		assertEquals(dictList.size(), 1);
		assertNotEquals(result.getId(), null);

		// 更新字典label，描述，type
		String newLable = "100";
		String newDesp = "test desp2";
		String newType = "test type2";
		dictTest.setLabel(newLable);
		dictTest.setDescription(newDesp);
		dictTest.setType(newType);
		dictMapper.updateDict(dictTest);
		dictList = dictMapper.selectAllDictInfo("test type2", null);
		Dict updateDict = dictList.get(0);
		assertEquals(updateDict.getLabel(), newLable);
		assertEquals(updateDict.getDescription(), newDesp);
		assertEquals(updateDict.getType(), newType);

		// 删除测试用户
		dictMapper.delDict(updateDict.getId());
		// 查看是否存在一个tester
		dictList = dictMapper.selectAllDictInfo("test type2", null);
		assertEquals(dictList.size(), 0);
	}

	/**
	 * 测试添加一个字典信息
	 */
	@Test
	public void testAddDict() {
		// 添加一个tester
		Dict dictTest = initTestDict();
		dictMapper.addDict(dictTest);
		List<Dict> dictList = dictMapper.selectAllDictInfo("test type", null);
		Dict result = dictList.get(0);
		assertEquals(dictList.size(), 1);
		assertNotEquals(result.getId(), null);

		// 删除测试用户
		dictMapper.delDict(result.getId());
		// 查看是否存在一个tester
		dictList = dictMapper.selectAllDictInfo("test type", null);
		assertEquals(dictList.size(), 0);

	}
}
