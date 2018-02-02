package com.cypher.activiti.dao;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.cypher.activiti.model.Role;
import com.cypher.activiti.model.RoleToArea;
import com.cypher.activiti.model.RoleToDept;
import com.cypher.activiti.model.RoleToMenu;
import com.cypher.activiti.model.UserToRole;

public class RoleDaoTest {

	private ApplicationContext ac = null;
	private RoleMapper roleMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		roleMapper = (RoleMapper) ac.getBean("roleMapper");
	}

	// 测试获取所有角色信息
	@Test
	public void testGetAllRoleInfo() {
		List<Role> roleList = roleMapper.getAllRoleInfo();
		System.out.println(JSON.toJSONString(roleList));
		assertThat(roleList.size(), greaterThan(0));
	}

	// 测试根据角色id查询角色菜单对应关系
	@Test
	public void testGetMenuListByRoleId() {
		List<RoleToMenu> roleToMenuList = roleMapper.getMenuListByRoleId(1L);
		System.out.println(JSON.toJSONString(roleToMenuList));
		assertThat(roleToMenuList.size(), greaterThan(0));
	}

	// 测试根据角色id查询角色部门对应关系
	@Test
	public void testGetDeptListByRoleId() {
		List<RoleToDept> roleToDeptList = roleMapper.getDeptListByRoleId(1L);
		System.out.println(JSON.toJSONString(roleToDeptList));
		assertThat(roleToDeptList.size(), greaterThan(0));
	}

	// 测试根据角色id查询角色区域对应关系
	@Test
	public void testGetAreaListByRoleId() {
		List<RoleToArea> roleToAreaList = roleMapper.getAreaListByRoleId(1L);
		System.out.println(JSON.toJSONString(roleToAreaList));
		assertThat(roleToAreaList.size(), greaterThan(0));
	}

	// 根据角色id查询角色信息
	@Test
	public void testGetRoleById() {
		Long roleId = 1L;
		Role role = roleMapper.getRoleById(roleId);
		System.out.println(JSON.toJSONString(role));
		assertEquals(role.getName(), "超级系统管理员");
	}

	// 删除角色
	@Test
	public void testDelRole() {
		Long roleId = 25L;
		boolean result = roleMapper.delRole(roleId);
		Role role = roleMapper.getRoleById(roleId);
		assertEquals(role, null);
	}

	// 删除角色菜单关联信息
	@Test
	public void testDelRoleToMenu() {
		Long roleId = 25L;
		boolean result = roleMapper.delRoleToMenu(roleId);
		List<RoleToMenu> roleToMenuList = roleMapper.getMenuListByRoleId(roleId);
		assertEquals(roleToMenuList.size(), 0);
	}

	// 删除角色部门关联信息
	@Test
	public void testDelRoleToDept() {
		Long roleId = 25L;
		boolean result = roleMapper.delRoleToDept(roleId);
		List<RoleToDept> roleToDeptList = roleMapper.getDeptListByRoleId(roleId);
		assertEquals(roleToDeptList.size(), 0);
	}

	// 删除角色区域关联信息
	@Test
	public void testDelRoleToArea() {
		Long roleId = 25L;
		boolean result = roleMapper.delRoleToArea(roleId);
		List<RoleToArea> roleToAreaList = roleMapper.getAreaListByRoleId(roleId);
		assertEquals(roleToAreaList.size(), 0);
	}

	// 批量插入角色菜单对应信息
	@Test
	public void testAddRoleToMenuBatch() {
		List<RoleToMenu> roleMenuList = new ArrayList<RoleToMenu>();
		Long roleId = 25L;
		// 1
		RoleToMenu roleMenu1 = new RoleToMenu();
		roleMenu1.setRoleId(roleId);
		roleMenu1.setMenuId(1L);
		roleMenuList.add(roleMenu1);

		// 2
		RoleToMenu roleMenu2 = new RoleToMenu();
		roleMenu2.setRoleId(roleId);
		roleMenu2.setMenuId(2L);
		roleMenuList.add(roleMenu2);

		boolean result = roleMapper.addRoleToMenuBatch(roleMenuList);
		System.out.println(result);
		List<RoleToMenu> roleToAreaList = roleMapper.getMenuListByRoleId(roleId);
		assertThat(roleToAreaList.size(), greaterThan(0));
		System.out.println(roleToAreaList.get(0).getMenuId());
		assertEquals(roleToAreaList.get(0).getMenuId(), (Long) 1L);
		assertEquals(roleToAreaList.get(1).getMenuId(), (Long) 2L);
	}

	// 查找在roleIdList中的角色信息
	@Test
	public void testGetRoleInfoByRoleIdList() {
		List<Long> roleIdList = new ArrayList<>();
		roleIdList.add(23l);
		roleIdList.add(24l);

		System.out.println(roleIdList.get(1));

		List<Role> result = roleMapper.getRoleInfoByRoleIdList(roleIdList);
		System.out.println(JSON.toJSONString(result));

	}

	// 修改角色信息
	@Test
	public void testUpdateRole() {
		Long roleId = 25l;
		Role oldRole = roleMapper.getRoleById(roleId);
		Role newRole = new Role();
		newRole.setId(roleId);
		newRole.setName("test111");
		newRole.setRemarks("笔记");
		newRole.setUpdateBy("0");
		newRole.setUpdateDate(new Date());

		boolean result = roleMapper.updateRole(newRole);
		assertNotEquals(oldRole.getName(), newRole.getName());
		System.out.println(JSON.toJSONString(result));

	}

	// 测试 删除用户拥有的角色信息  和 批量添加用户角色
	@Test
	public void testDelUserRoleByUserId() {
		Long addUserId = 25L;

		List<UserToRole> userToRoleList = new ArrayList<UserToRole>();

		UserToRole userToRole1 = new UserToRole();
		userToRole1.setUserId(addUserId);
		userToRole1.setRoleId(1L);

		userToRoleList.add(userToRole1);

		roleMapper.addUserRole(userToRoleList);

		List<UserToRole> userToRoleListResult = roleMapper.getUserRoleByUserId(addUserId);
		System.out.println(JSON.toJSONString(userToRoleListResult));
		assertEquals(userToRoleListResult.size(), 1);

		roleMapper.delUserRoleByUserId(addUserId);
		userToRoleListResult = roleMapper.getUserRoleByUserId(addUserId);
		assertEquals(userToRoleListResult.size(), 0);

	}
	
	
}
