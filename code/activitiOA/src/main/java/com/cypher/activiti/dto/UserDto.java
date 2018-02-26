package com.cypher.activiti.dto;

import java.util.List;

import com.cypher.activiti.model.User;
import com.cypher.activiti.model.UserToRole;
/**
 * 视图模型</br>
 * 用于个人信息展示</br>
 * @author Administrator
 *
 */
public class UserDto extends User implements java.io.Serializable{
	
	private static final long serialVersionUID = -1842851322725345725L;
	
	private String deptName;
	
	private List<String> roleNameList;

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public List<String> getRoleNameList() {
		return roleNameList;
	}

	public void setRoleNameList(List<String> roleNameList) {
		this.roleNameList = roleNameList;
	}

	
}
