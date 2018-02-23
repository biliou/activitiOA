package com.cypher.activiti.dao;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.cypher.activiti.dto.LeaveBean;

public class LeaveDaoTest {
	private ApplicationContext ac = null;
	private LeaveMapper leaveMapper = null;

	private LeaveBean leaveBeanTest;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		leaveMapper = (LeaveMapper) ac.getBean("leaveMapper");
	}

	public LeaveBean initLeaveBean() {
		leaveBeanTest = new LeaveBean();
		leaveBeanTest.setLeaveUserId(1L);
		leaveBeanTest.setLeaveDate(new Date());
		leaveBeanTest.setLeaveDays(1);
		leaveBeanTest.setLeaveReason("测试原因");
		leaveBeanTest.setRemark("测试备注");
		leaveBeanTest.setLeaveState(0);
		return leaveBeanTest;

	}

	/**
	 * 测试获取所有请假流程信息列表
	 */
	@Test
	public void testGetLeaveProcessList() {
		// 初始化数据
		LeaveBean leaveBeanTest = initLeaveBean();
		// 添加一个请假流程信息
		leaveMapper.addLeaveProcess(leaveBeanTest);
		LeaveBean result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());

		List<LeaveBean> leaveList = leaveMapper.getLeaveProcessList(1l);
		assertThat(leaveList.size(), greaterThan(0));

		// 删除测试用户请假信息
		leaveMapper.delLeaveProcess(result.getLeaveId());

		result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());
		assertEquals(result, null);
	}

	/**
	 * 测试通过LeaveId 获取请假流程信息
	 */
	@Test
	public void testGetLeaveProcessByLeaveId() {
		// 初始化数据
		LeaveBean leaveBeanTest = initLeaveBean();
		// 添加一个请假流程信息
		leaveMapper.addLeaveProcess(leaveBeanTest);

		LeaveBean result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());

		assertNotEquals(result, null);
		assertNotEquals(result.getLeaveId(), null);
		assertEquals(result.getLeaveUserId(), leaveBeanTest.getLeaveUserId());
		assertEquals(result.getLeaveDays(), leaveBeanTest.getLeaveDays());
		assertEquals(result.getLeaveReason(), leaveBeanTest.getLeaveReason());
		assertEquals(result.getRemark(), leaveBeanTest.getRemark());
		assertEquals(result.getLeaveState(), leaveBeanTest.getLeaveState());

		// 删除测试用户请假信息
		leaveMapper.delLeaveProcess(result.getLeaveId());

		result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());
		assertEquals(result, null);
	}

	/**
	 * 测试添加请假流程信息
	 */
	@Test
	public void testAddLeaveProcess() {

		// 初始化数据
		LeaveBean leaveBeanTest = initLeaveBean();
		// 添加一个请假流程信息
		leaveMapper.addLeaveProcess(leaveBeanTest);
		LeaveBean result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());

		assertNotEquals(result, null);
		assertNotEquals(result.getLeaveId(), null);
		assertEquals(result.getLeaveUserId(), leaveBeanTest.getLeaveUserId());
		assertEquals(result.getLeaveDays(), leaveBeanTest.getLeaveDays());
		assertEquals(result.getLeaveReason(), leaveBeanTest.getLeaveReason());
		assertEquals(result.getRemark(), leaveBeanTest.getRemark());
		assertEquals(result.getLeaveState(), leaveBeanTest.getLeaveState());

		// 删除测试用户请假信息
		leaveMapper.delLeaveProcess(result.getLeaveId());

		result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());
		assertEquals(result, null);
	}

	/**
	 * 测试修改请假流程信息
	 */
	@Test
	public void testUpdateLeaveProcess() {

		// 初始化数据
		LeaveBean leaveBeanTest = initLeaveBean();
		// 添加一个请假流程信息
		leaveMapper.addLeaveProcess(leaveBeanTest);
		LeaveBean result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());

		assertNotEquals(result, null);
		assertNotEquals(result.getLeaveId(), null);
		assertEquals(result.getLeaveUserId(), leaveBeanTest.getLeaveUserId());
		assertEquals(result.getLeaveDays(), leaveBeanTest.getLeaveDays());
		assertEquals(result.getLeaveReason(), leaveBeanTest.getLeaveReason());
		assertEquals(result.getRemark(), leaveBeanTest.getRemark());
		assertEquals(result.getLeaveState(), leaveBeanTest.getLeaveState());

		// 更新请假流程信息
		result.setLeaveDays(5);
		result.setLeaveReason("更新测试原因");
		result.setRemark("更新测试备注");
		leaveMapper.updateLeaveProcess(result);
		LeaveBean updateResult = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());

		assertNotEquals(updateResult, null);
		assertEquals(updateResult.getLeaveUserId(), result.getLeaveUserId());
		assertNotEquals(updateResult.getLeaveDays(), leaveBeanTest.getLeaveDays());
		assertNotEquals(updateResult.getLeaveReason(), leaveBeanTest.getLeaveReason());
		assertNotEquals(updateResult.getRemark(), leaveBeanTest.getRemark());
		assertEquals(updateResult.getLeaveState(), result.getLeaveState());

		// 删除测试用户请假信息
		leaveMapper.delLeaveProcess(updateResult.getLeaveId());

		result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());
		assertEquals(result, null);
	}

	/**
	 * 测试删除请假流程信息
	 */
	@Test
	public void testDelLeaveProcess() {

		// 初始化数据
		LeaveBean leaveBeanTest = initLeaveBean();
		// 添加一个请假流程信息
		leaveMapper.addLeaveProcess(leaveBeanTest);
		LeaveBean result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());

		assertNotEquals(result, null);
		assertNotEquals(result.getLeaveId(), null);
		assertEquals(result.getLeaveUserId(), leaveBeanTest.getLeaveUserId());
		assertEquals(result.getLeaveDays(), leaveBeanTest.getLeaveDays());
		assertEquals(result.getLeaveReason(), leaveBeanTest.getLeaveReason());
		assertEquals(result.getRemark(), leaveBeanTest.getRemark());
		assertEquals(result.getLeaveState(), leaveBeanTest.getLeaveState());

		// 删除测试用户请假信息
		leaveMapper.delLeaveProcess(result.getLeaveId());

		result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());
		assertEquals(result, null);
	}

	/**
	 * 测试修改请假流程状态
	 */
	@Test
	public void testUpdateLeaveState() {
		// 初始化数据
		LeaveBean leaveBeanTest = initLeaveBean();
		// 添加一个请假流程信息
		leaveMapper.addLeaveProcess(leaveBeanTest);
		LeaveBean result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());

		assertNotEquals(result, null);
		assertNotEquals(result.getLeaveId(), null);
		assertEquals(result.getLeaveUserId(), leaveBeanTest.getLeaveUserId());
		assertEquals(result.getLeaveDays(), leaveBeanTest.getLeaveDays());
		assertEquals(result.getLeaveReason(), leaveBeanTest.getLeaveReason());
		assertEquals(result.getRemark(), leaveBeanTest.getRemark());
		assertEquals(result.getLeaveState(), leaveBeanTest.getLeaveState());

		// 更新请假流程信息
		leaveMapper.updateLeaveState(result.getLeaveId(), 1);

		LeaveBean updateResult = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());

		assertNotEquals(updateResult, null);
		assertEquals(updateResult.getLeaveState(), Integer.valueOf(1));

		// 删除测试用户请假信息
		leaveMapper.delLeaveProcess(result.getLeaveId());

		result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());
		assertEquals(result, null);
	}

	/**
	 * 测试修改请假流程实例绑定
	 */
	@Test
	public void testUpdateLeaveProcessInstanceId() {
		// 初始化数据
		LeaveBean leaveBeanTest = initLeaveBean();
		// 添加一个请假流程信息
		leaveMapper.addLeaveProcess(leaveBeanTest);
		LeaveBean result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());

		assertNotEquals(result, null);
		assertNotEquals(result.getLeaveId(), null);
		assertEquals(result.getLeaveUserId(), leaveBeanTest.getLeaveUserId());
		assertEquals(result.getLeaveDays(), leaveBeanTest.getLeaveDays());
		assertEquals(result.getLeaveReason(), leaveBeanTest.getLeaveReason());
		assertEquals(result.getRemark(), leaveBeanTest.getRemark());
		assertEquals(result.getLeaveState(), leaveBeanTest.getLeaveState());

		// 更新请假流程信息
		leaveMapper.updateLeaveProcessInstanceId(result.getLeaveId(), "1111");

		LeaveBean updateResult = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());

		assertNotEquals(updateResult, null);
		assertEquals(updateResult.getProcessInstanceId(), "1111");

		// 删除测试用户请假信息
		leaveMapper.delLeaveProcess(result.getLeaveId());

		result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());
		assertEquals(result, null);
	}

	/**
	 * 测试通过流程实例id获取请假单信息
	 */
	@Test
	public void testGetLeaveBeanByProcessInstanceId() {
		// 初始化数据
		LeaveBean leaveBeanTest = initLeaveBean();
		// 添加一个请假流程信息
		leaveMapper.addLeaveProcess(leaveBeanTest);
		LeaveBean result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());

		assertNotEquals(result, null);
		assertNotEquals(result.getLeaveId(), null);
		assertEquals(result.getLeaveUserId(), leaveBeanTest.getLeaveUserId());
		assertEquals(result.getLeaveDays(), leaveBeanTest.getLeaveDays());
		assertEquals(result.getLeaveReason(), leaveBeanTest.getLeaveReason());
		assertEquals(result.getRemark(), leaveBeanTest.getRemark());
		assertEquals(result.getLeaveState(), leaveBeanTest.getLeaveState());

		String processInstanceIdTest = "test";

		leaveMapper.updateLeaveProcessInstanceId(result.getLeaveId(), processInstanceIdTest);

		// 测试通过流程实例id获取请假单信息
		result = leaveMapper.getLeaveBeanByProcessInstanceId(processInstanceIdTest);
		assertNotEquals(result, null);
		assertEquals(result.getLeaveUserId(), leaveBeanTest.getLeaveUserId());
		assertEquals(result.getLeaveDays(), leaveBeanTest.getLeaveDays());
		assertEquals(result.getLeaveReason(), leaveBeanTest.getLeaveReason());
		assertEquals(result.getRemark(), leaveBeanTest.getRemark());
		assertEquals(result.getLeaveState(), leaveBeanTest.getLeaveState());
		assertEquals(result.getProcessInstanceId(), processInstanceIdTest);

		// 删除测试用户请假信息
		leaveMapper.delLeaveProcess(result.getLeaveId());

		result = leaveMapper.getLeaveProcessByLeaveId(leaveBeanTest.getLeaveId());
		assertEquals(result, null);
	}

}
