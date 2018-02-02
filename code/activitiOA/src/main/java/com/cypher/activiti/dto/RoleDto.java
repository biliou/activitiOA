package com.cypher.activiti.dto;

import java.util.Map;

import com.cypher.activiti.model.Role;

/**
 * 视图模型</br>
 * 用于角色信息展示</br>
 * 例子：</br>
 * {</br>
 * "areaIds":{},</br>
 * "deptIds":{1:1,22:22,23:23,24:24,25:25},</br>
 * "menuIds":{1:1,2:2,3:3,7:7,10:10,32:32},</br>
 * "role":{"name":"213","remarks":"123"}</br>
 * }</br>
 * 
 * @author Administrator
 *
 */
public class RoleDto {
	private Role role;
	private Map<Long, Long> menuIds;
	private Map<Long, Long> deptIds;
	private Map<Long, Long> areaIds;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Map<Long, Long> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(Map<Long, Long> menuIds) {
		this.menuIds = menuIds;
	}

	public Map<Long, Long> getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(Map<Long, Long> deptIds) {
		this.deptIds = deptIds;
	}

	public Map<Long, Long> getAreaIds() {
		return areaIds;
	}

	public void setAreaIds(Map<Long, Long> areaIds) {
		this.areaIds = areaIds;
	}

}
