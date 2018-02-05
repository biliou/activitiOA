package com.cypher.activiti.service;

import java.util.List;

import com.cypher.activiti.dto.RoleDto;
import com.cypher.activiti.model.Role;
import com.cypher.activiti.model.RoleToArea;
import com.cypher.activiti.model.RoleToDept;
import com.cypher.activiti.model.RoleToMenu;

/**
 * 角色管理业务接口
 * 
 * @author Administrator
 *
 */
public interface IRoleService {
	/**
	 * 获取所有角色信息
	 * 
	 * @return
	 */
	public List<Role> getAllRoleInfo();

	/**
	 * 通过角色id获取该角色的菜单权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RoleToMenu> getMenuListByRoleId(Long roleId);

	/**
	 * 根据角色id查询角色部门对应关系
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RoleToDept> getDeptListByRoleId(Long roleId);

	/**
	 * 根据角色id查询角色区域对应关系
	 * 
	 * @param roleId
	 * @return
	 */
	public List<RoleToArea> getAreaListByRoleId(Long roleId);

	/**
	 * 根据角色id获取角色信息
	 * 
	 * @param roleId
	 * @return
	 */
	public Role getRoleById(Long roleId);

	/**
	 * 删除角色信息以及跟角色相关的对应关系表
	 * 
	 * @param roleId
	 * @return
	 */
	public boolean delRole(Long roleId);

	/**
	 * 修改角色信息以及角色对应关系表
	 * 
	 * @param roleDto
	 * @return
	 */
	public boolean updateRole(RoleDto roleDto, Long userId);

	/**
	 * 增加角色信息以及角色对应关系表
	 * 
	 * @param roleDto
	 * @return
	 */
	public boolean addRole(RoleDto roleDto, Long userId);
}
