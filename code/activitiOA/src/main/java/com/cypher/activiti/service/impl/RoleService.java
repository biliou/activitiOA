package com.cypher.activiti.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cypher.activiti.dao.RoleMapper;
import com.cypher.activiti.dto.RoleDto;
import com.cypher.activiti.model.Menu;
import com.cypher.activiti.model.Role;
import com.cypher.activiti.model.RoleToArea;
import com.cypher.activiti.model.RoleToDept;
import com.cypher.activiti.model.RoleToMenu;
import com.cypher.activiti.service.IRoleService;

@Service
public class RoleService implements IRoleService {

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Role> getAllRoleInfo() {
		return roleMapper.getAllRoleInfo();
	}

	@Override
	public List<RoleToMenu> getMenuListByRoleId(Long roleId) {
		return roleMapper.getMenuListByRoleId(roleId);
	}

	@Override
	public List<RoleToDept> getDeptListByRoleId(Long roleId) {
		return roleMapper.getDeptListByRoleId(roleId);
	}

	@Override
	public List<RoleToArea> getAreaListByRoleId(Long roleId) {
		return roleMapper.getAreaListByRoleId(roleId);
	}

	@Override
	public Role getRoleById(Long roleId) {
		return roleMapper.getRoleById(roleId);
	}

	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public boolean delRole(Long roleId) {
		boolean flag = false;

		// 删除角色部门关联信息
		flag = roleMapper.delRoleToDept(roleId);
		// 删除角色菜单关联信息
		flag = roleMapper.delRoleToMenu(roleId);
		// 删除角色区域关联信息
		flag = roleMapper.delRoleToArea(roleId);
		// 删除角色
		flag = roleMapper.delRole(roleId);
		return flag;
	}

	/**
	 * 1:保存修改的角色信息<br/>
	 * 2:根据角色id删除角色部门对应关系,角色菜单对应关系,角色区域对应关系<br/>
	 * 3:根据角色id,部门id组合角色部门对应列表,进行批量插入<br/>
	 * 4:根据角色id,区域id组合角色区域对应列表,进行批量插入<br/>
	 * 5:根据角色id,菜单id组合角色菜单对应列表,进行批量插入<br/>
	 */
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public boolean updateRole(RoleDto roleDto, Long userId) {
		boolean flag = false;

		// 保存修改的角色信息
		Role role = roleDto.getRole();
		role.setUpdateBy(userId.toString());
		role.setUpdateDate(new Date());
		flag = roleMapper.updateRole(role);

		Long roleId = role.getId();
		// 根据角色id删除角色部门对应关系,角色菜单对应关系,角色区域对应关系
		flag = roleMapper.delRoleToDept(roleId);
		flag = roleMapper.delRoleToMenu(roleId);
		flag = roleMapper.delRoleToArea(roleId);

		// 根据新增对象的id (角色id) 菜单id组合角色菜单对应列表,进行批量插入
		if (roleDto.getMenuIds().size() > 0) {
			List<RoleToMenu> roleMenuList = new ArrayList<RoleToMenu>();
			RoleToMenu roleMenu;
			for (Long menuId : roleDto.getMenuIds().values()) {
				roleMenu = new RoleToMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menuId);
				roleMenuList.add(roleMenu);
			}
			// 批量插入菜单角色对应表
			flag = roleMapper.addRoleToMenuBatch(roleMenuList);
		}

		if (roleDto.getDeptIds().size() > 0) {
			// 根据新增对象的id (角色id) 组合角色部门对应列表,进行批量插入
			List<RoleToDept> roleDeptList = new ArrayList<RoleToDept>();
			RoleToDept roleDept;
			for (Long deptId : roleDto.getDeptIds().values()) {
				roleDept = new RoleToDept();
				roleDept.setRoleId(roleId);
				roleDept.setDeptId(deptId);
				roleDeptList.add(roleDept);
			}
			// 批量插入部门角色对应表
			flag = roleMapper.addRoleToDeptBatch(roleDeptList);
		}

		if (roleDto.getAreaIds().size() > 0) {
			// 根据新增对象的id (角色id) 区域id组合角色区域对应列表,进行批量插入
			List<RoleToArea> roleAreaList = new ArrayList<RoleToArea>();
			RoleToArea roleArea;
			for (Long areaId : roleDto.getAreaIds().values()) {
				roleArea = new RoleToArea();
				roleArea.setRoleId(roleId);
				roleArea.setAreaId(areaId);
				roleAreaList.add(roleArea);
			}
			// 批量插入部门角色对应表
			flag = roleMapper.addRoleToAreaBatch(roleAreaList);
		}

		return false;
	}

	/**
	 * 1:保存角色信息,返回角色ID<br/>
	 * 2:根据角色id,部门id组合角色部门对应列表,进行批量插入<br/>
	 * 3:根据角色id,区域id组合角色区域对应列表,进行批量插入<br/>
	 * 4:根据角色id,菜单id组合角色菜单对应列表,进行批量插入<br/>
	 */
	@Override
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public boolean addRole(RoleDto roleDto, Long userId) {
		boolean flag = false;

		// 增加角色信息
		Role role = roleDto.getRole();
		role.setUpdateBy(userId.toString());
		role.setUpdateDate(new Date());
		flag = roleMapper.addRole(role) && flag;

		Long roleId = role.getId();

		// 根据新增对象的id (角色id) 菜单id组合角色菜单对应列表,进行批量插入
		if (roleDto.getMenuIds().size() > 0) {
			List<RoleToMenu> roleMenuList = new ArrayList<RoleToMenu>();
			RoleToMenu roleMenu;
			for (Long menuId : roleDto.getMenuIds().values()) {
				roleMenu = new RoleToMenu();
				roleMenu.setRoleId(roleId);
				roleMenu.setMenuId(menuId);
				roleMenuList.add(roleMenu);
			}
			// 批量插入菜单角色对应表
			flag = roleMapper.addRoleToMenuBatch(roleMenuList);
		}

		if (roleDto.getDeptIds().size() > 0) {
			// 根据新增对象的id (角色id) 组合角色部门对应列表,进行批量插入
			List<RoleToDept> roleDeptList = new ArrayList<RoleToDept>();
			RoleToDept roleDept;
			for (Long deptId : roleDto.getDeptIds().values()) {
				roleDept = new RoleToDept();
				roleDept.setRoleId(roleId);
				roleDept.setDeptId(deptId);
				roleDeptList.add(roleDept);
			}
			// 批量插入部门角色对应表
			flag = roleMapper.addRoleToDeptBatch(roleDeptList);
		}

		if (roleDto.getAreaIds().size() > 0) {
			// 根据新增对象的id (角色id) 区域id组合角色区域对应列表,进行批量插入
			List<RoleToArea> roleAreaList = new ArrayList<RoleToArea>();
			RoleToArea roleArea;
			for (Long areaId : roleDto.getAreaIds().values()) {
				roleArea = new RoleToArea();
				roleArea.setRoleId(roleId);
				roleArea.setAreaId(areaId);
				roleAreaList.add(roleArea);
			}
			// 批量插入部门角色对应表
			flag = roleMapper.addRoleToAreaBatch(roleAreaList);
		}

		return flag;
	}

}
