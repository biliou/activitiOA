package com.cypher.activiti.service;

import java.util.List;

import com.cypher.activiti.dto.LeaveBean;
/**
 * 请假申请的业务层接口
 * 
 * @author Administrator
 *
 */
public interface ILeaveProcessService {
	/**
	 * 获取所有请假流程信息列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<LeaveBean> getLeaveProcessList(Long userId);

	/**
	 * 通过LeaveId获取请假流程信息
	 * 
	 * @param leaveId
	 * @return
	 */
	public LeaveBean getLeaveProcessByLeaveId(Long leaveId);

	/**
	 * 添加请假申请
	 * 
	 * @param leaveBean
	 * @param userId
	 *            当前用户(请假用户id)
	 * @return
	 */
	public boolean addLeaveProcess(LeaveBean leaveBean, Long userId);

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
	 * 请假处理(开始请假流程实例)
	 * 
	 * @param leaveId
	 * @return
	 */
	public boolean doLeaveProcess(Long leaveId, Long userId);
}
