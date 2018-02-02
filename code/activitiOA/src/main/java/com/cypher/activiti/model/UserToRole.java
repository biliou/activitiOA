package com.cypher.activiti.model;

/**
 * 角色区域对应表
 * 类描述：  
 * 类名称：com.tz.ssspm.sysmanage.bean.RoleToMenu       
 * 创建人：keven  
 * 创建时间：2016年11月30日 下午1:37:03
 * @version   V1.0
 */
public class UserToRole implements java.io.Serializable {

	private static final long serialVersionUID = 5740104362530862141L;
	
	private Long userId;
	private Long roleId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	 	 
}
