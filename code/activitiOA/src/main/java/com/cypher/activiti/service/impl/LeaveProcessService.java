package com.cypher.activiti.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cypher.activiti.dao.DictMapper;
import com.cypher.activiti.dao.LeaveMapper;
import com.cypher.activiti.dao.UserMapper;
import com.cypher.activiti.dto.LeaveBean;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.ILeaveProcessService;
import com.cypher.activiti.service.IWorkFlowService;

@Service
public class LeaveProcessService implements ILeaveProcessService {

	@Autowired
	private LeaveMapper leaveMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private DictMapper dictMapper;
	@Autowired
	private IWorkFlowService workFlowService;

	@Override
	public List<LeaveBean> getLeaveProcessList(Long userId) {

		List<LeaveBean> leaveBeanList = leaveMapper.getLeaveProcessList(userId);
		for (LeaveBean leaveBean : leaveBeanList) {
			// 获取用户名
			User user = userMapper.getUserInfoById(leaveBean.getLeaveUserId());
			leaveBean.setLeaveUserName(user.getUserName());

			// 获取请假状态
			if (leaveBean.getLeaveState().intValue() == 0) {
				leaveBean.setLeaveStateDesc("初始录入");
			}
			if (leaveBean.getLeaveState().intValue() == 1) {
				leaveBean.setLeaveStateDesc("审核中");
			}

		}

		return leaveBeanList;
	}

	@Override
	public LeaveBean getLeaveProcessByLeaveId(Long leaveId) {
		return leaveMapper.getLeaveProcessByLeaveId(leaveId);
	}

	/**
	 * 1.添加申请当前用户 2.流程启动 3.获取流程实例id
	 */
	@Override
	public boolean addLeaveProcess(LeaveBean leaveBean, Long userId) {
		leaveBean.setLeaveUserId(userId);
		leaveBean.setLeaveState(0);

		return leaveMapper.addLeaveProcess(leaveBean);
	}

	@Override
	public boolean updateLeaveProcess(LeaveBean leaveBean) {
		return leaveMapper.updateLeaveProcess(leaveBean);
	}

	@Override
	public boolean delLeaveProcess(Long leaveId) {
		return leaveMapper.delLeaveProcess(leaveId);
	}

	/**
	 * 请假处理(开始请假流程实例)<br/>
	 * 1.修改请假单状态 = 1 <br/>
	 * 2.启动流程（需要：Key） <br/>
	 * 3.根据流程定义的key启动流程实例<br/>
	 * 1>将表单数据与流程实例用流程变量关联 <br/>
	 * 2>获取当前的操作用户，设置下一步处理人的流程变量 <br/>
	 * 3>启动流程变量 4.绑定流程实例与请假表记录
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public boolean doLeaveProcess(Long leaveId, Long userId) {
		boolean flag = false;

		// 修改请假单状态 = 1
		flag = leaveMapper.updateLeaveState(leaveId, 1);

		// 启动流程
		LeaveBean leaveBean = leaveMapper.getLeaveProcessByLeaveId(leaveId);
		// 获取流程启动key
		String leaveProcessKey = "leave";
		String businessKey = leaveProcessKey + "." + leaveId;

		// 构造流程变量，存储当前任务处理人
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("userId", userId);

		// 启动流程
		ProcessInstance processInstance = workFlowService.startProcess(leaveProcessKey, businessKey, variables);

		// 绑定流程实例与请假表记录
		String processInstanceId = processInstance.getProcessInstanceId();
		flag = leaveMapper.updateLeaveProcessInstanceId(leaveId, processInstanceId);

		return flag;
	}

}
