package com.cypher.activiti.dto;

import com.cypher.activiti.model.Leave;

/**
 * 请假表单的视图模型
 * 
 * @author Administrator
 *
 */
public class LeaveBean extends Leave {
	private String leaveUserName;
	private String leaveStateDesc;

	public String getLeaveUserName() {
		return leaveUserName;
	}

	public void setLeaveUserName(String leaveUserName) {
		this.leaveUserName = leaveUserName;
	}

	public String getLeaveStateDesc() {
		return leaveStateDesc;
	}

	public void setLeaveStateDesc(String leaveStateDesc) {
		this.leaveStateDesc = leaveStateDesc;
	}

}
