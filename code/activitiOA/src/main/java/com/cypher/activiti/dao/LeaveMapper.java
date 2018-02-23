package com.cypher.activiti.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cypher.activiti.dto.LeaveBean;

public interface LeaveMapper {
	/**
	 * 获取所有请假流程信息列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<LeaveBean> getLeaveProcessList(@Param(value = "userId") Long userId);

	/**
	 * 通过LeaveId 获取请假流程信息
	 * 
	 * @param leaveId
	 * @return
	 */
	public LeaveBean getLeaveProcessByLeaveId(@Param(value = "leaveId") Long leaveId);

	/**
	 * 添加请假申请
	 * 
	 * @param leaveBean
	 * @return
	 */
	public boolean addLeaveProcess(LeaveBean leaveBean);

	/**
	 * 修改请假申请
	 * 
	 * @param leaveBean
	 * @return
	 */
	public boolean updateLeaveProcess(LeaveBean leaveBean);

	/**
	 * 删除请假申请
	 * 
	 * @param leaveId
	 * @return
	 */
	public boolean delLeaveProcess(Long leaveId);

	/**
	 * 请假状态修改
	 * 
	 * @param leaveId
	 * @param state
	 * @return
	 */
	public boolean updateLeaveState(Long leaveId, Integer state);

	/**
	 * 请假单与请假流程实例绑定
	 * 
	 * @param leaveId
	 * @param processInstanceId
	 * @return
	 */
	public boolean updateLeaveProcessInstanceId(Long leaveId, String processInstanceId);

	/**
	 * 通过流程实例id获取请假单信息
	 * 
	 * @param processInstanceId
	 * @return
	 */
	public LeaveBean getLeaveBeanByProcessInstanceId(String processInstanceId);

}