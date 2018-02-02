package com.cypher.activiti.Vo;

import java.util.Map;

import com.cypher.activiti.model.User;

/**
 * JSON结构：<br/>
 * {<br/>
 * "roleIds":{23:23},<br/>
 * "user":{"deptId":1,"email":"","loginName":"test2",<br/>
 * "mobile":"21312","phone":"","remarks":"",<br/>
 * "userId":12312314,"userName":"tester","userNo":"121211"}<br/>
 * }<br/>
 * 
 * @author Administrator
 *
 */
public class UserVo {
	private User user;
	private Map<Long, Long> roleIds;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Map<Long, Long> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(Map<Long, Long> roleIds) {
		this.roleIds = roleIds;
	}
}
