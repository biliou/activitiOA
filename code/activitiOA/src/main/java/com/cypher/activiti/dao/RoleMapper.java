package com.cypher.activiti.dao;

import java.util.List;

import com.cypher.activiti.model.Role;
import com.cypher.activiti.model.RoleToArea;
import com.cypher.activiti.model.RoleToDept;
import com.cypher.activiti.model.RoleToMenu;
import com.cypher.activiti.model.User;
import com.cypher.activiti.model.UserToRole;

/**
 * 角色的增删改查mapper代理接口
 * 
 * @author Administrator
 *
 */
public interface RoleMapper {
	/**
	 * 获取所有角色信息
	 * 
	 * @return
	 */
	public List<Role> getAllRoleInfo();

	/**
	 * 根据角色id查询角色菜单对应关系
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
	 * 删除角色
	 * 
	 * @param roleId
	 * @return
	 */
	public boolean delRole(Long roleId);

	/**
	 * 删除角色菜单关联信息
	 * 
	 * @param roleId
	 * @return
	 */
	public boolean delRoleToMenu(Long roleId);

	/**
	 * 删除角色部门关联信息
	 * 
	 * @param roleId
	 * @return
	 */
	public boolean delRoleToDept(Long roleId);

	/**
	 * 删除角色区域关联信息
	 * 
	 * @param roleId
	 * @return
	 */
	public boolean delRoleToArea(Long roleId);

	/**
	 * 增加角色对象
	 * 
	 * @param role
	 * @return
	 */
	public boolean addRole(Role role);

	/**
	 * 批量插入角色菜单对应信息
	 * 
	 * @param roleMenuList
	 * @return
	 */
	public boolean addRoleToMenuBatch(List<RoleToMenu> roleMenuList);

	/**
	 * 批量插入角色部门对应信息
	 * 
	 * @param roleDeptList
	 * @return
	 */
	public boolean addRoleToDeptBatch(List<RoleToDept> roleDeptList);

	/**
	 * 批量插入角色区域对应信息
	 * 
	 * @param roleAreaList
	 * @return
	 */
	public boolean addRoleToAreaBatch(List<RoleToArea> roleAreaList);

	/**
	 * 查找在roleIdList中的角色信息
	 * 
	 * @param roleIdList
	 * @return
	 */
	public List<Role> getRoleInfoByRoleIdList(List<Long> roleIdList);

	/**
	 * 修改角色
	 * 
	 * @param role
	 * @return
	 */
	public boolean updateRole(Role role);

	/**
	 * 根据用户id查询用户所拥有的角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserToRole> getUserRoleByUserId(Long userId);

	/**
	 * 删除用户拥有的角色信息
	 * 
	 * @param userId
	 * @return
	 */
	public boolean delUserRoleByUserId(Long userId);

	/**
	 * 批量增加用户角色
	 * 
	 * @param userToRoleList
	 * @return
	 */
	public boolean addUserRole(List<UserToRole> userToRoleList);

	/**
	 * 增加角色菜单对应记录
	 * 
	 * @param roleMenu
	 * @return
	 */
	public boolean addRoleToMenu(RoleToMenu roleMenu);

	/**
	 * 增加角色区域对应记录
	 * 
	 * @param roleArea
	 * @return
	 */
	public boolean addRoleToArea(RoleToArea roleArea);

	/**
	 * 增加角色部门对应记录
	 * 
	 * @param roleDept
	 * @return
	 */
	public boolean addRoleToDept(RoleToDept roleDept);

	/**
	 * 通过角色名字查找所有用户列表
	 * 
	 * @param roleName
	 * @return
	 */
	public List<Long> getUserIdListByRoleName(String roleName);
}