package com.cypher.activiti.dao;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cypher.activiti.model.Role;
import com.cypher.activiti.model.RoleToArea;
import com.cypher.activiti.model.RoleToDept;
import com.cypher.activiti.model.RoleToMenu;
import com.cypher.activiti.model.UserToRole;

/**
 * 测试角色功能Dao接口
 * 
 * @author Administrator
 *
 */
public class RoleDaoTest {

	private ApplicationContext ac = null;
	private RoleMapper roleMapper = null;

	@Before
	public void before() {
		ac = new ClassPathXmlApplicationContext("/springmvc/spring-mybatis.xml");
		roleMapper = (RoleMapper) ac.getBean("roleMapper");
	}

	public Role initTestRole() {
		Role roleTest = new Role();
		roleTest.setName("test");
		roleTest.setUpdateBy("1");
		roleTest.setUpdateDate(new Date());
		return roleTest;
	}

	/**
	 * 测试获取所有角色信息
	 */
	@Test
	public void testGetAllRoleInfo() {
		List<Role> roleList = roleMapper.getAllRoleInfo();
		int len = roleList.size();
		assertThat(len, greaterThan(0));
		assertEquals(len, 1);

	}

	/**
	 * 测试根据角色id查询角色菜单对应关系
	 */
	@Test
	public void testGetMenuListByRoleId() {
		List<RoleToMenu> roleToMenuList = roleMapper.getMenuListByRoleId(1L);
		assertThat(roleToMenuList.size(), greaterThan(0));
	}

	/**
	 * 测试根据角色id查询角色部门对应关系
	 */
	@Test
	public void testGetDeptListByRoleId() {
		List<RoleToDept> roleToDeptList = roleMapper.getDeptListByRoleId(1L);
		assertThat(roleToDeptList.size(), greaterThan(0));
	}

	/**
	 * 测试根据角色id查询角色区域对应关系
	 */
	@Test
	public void testGetAreaListByRoleId() {
		List<RoleToArea> roleToAreaList = roleMapper.getAreaListByRoleId(1L);
		assertThat(roleToAreaList.size(), greaterThan(0));
	}

	/**
	 * 根据角色id查询角色信息
	 */
	@Test
	public void testGetRoleById() {
		Long roleId = 1L;
		Role role = roleMapper.getRoleById(roleId);
		assertEquals(role.getName(), "超级系统管理员");
	}

	/**
	 * 删除角色
	 */
	@Test
	public void testDelRole() {
		// 添加一个tester
		Role roleTest = initTestRole();
		roleMapper.addRole(roleTest);
		Role result = roleMapper.getRoleById(roleTest.getId());
		assertEquals(result.getName(), roleTest.getName());

		// 删除测试角色
		roleMapper.delRole(roleTest.getId());

		// 查看是否存在一个tester
		result = roleMapper.getRoleById(roleTest.getId());
		assertEquals(result, null);
	}

	/**
	 * 删除角色菜单关联信息
	 */
	@Test
	public void testDelRoleToMenu() {
		Long testRoleId = 2L;

		// 添加一个角色菜单关联信息
		RoleToMenu roleToMenuTest1 = new RoleToMenu();
		roleToMenuTest1.setRoleId(testRoleId);
		roleToMenuTest1.setMenuId(1L);
		RoleToMenu roleToMenuTest2 = new RoleToMenu();
		roleToMenuTest2.setRoleId(testRoleId);
		roleToMenuTest2.setMenuId(2L);
		List<RoleToMenu> roleMenuList = new ArrayList<RoleToMenu>();
		roleMenuList.add(roleToMenuTest1);
		roleMenuList.add(roleToMenuTest2);

		roleMapper.addRoleToMenuBatch(roleMenuList);

		List<RoleToMenu> roleMenuListResult = roleMapper.getMenuListByRoleId(testRoleId);
		assertEquals(roleMenuListResult.size(), 2);

		// 删除测试角色菜单关联信息
		roleMapper.delRoleToMenu(testRoleId);

		// 查看是否存在一个测试角色菜单关联信息
		roleMenuListResult = roleMapper.getMenuListByRoleId(testRoleId);
		assertEquals(roleMenuListResult.size(), 0);
	}

	/**
	 * 删除角色部门关联信息
	 */
	@Test
	public void testDelRoleToDept() {
		Long testRoleId = 2L;

		// 添加一个角色部门关联信息
		RoleToDept roleToDeptTest1 = new RoleToDept();
		roleToDeptTest1.setRoleId(testRoleId);
		roleToDeptTest1.setDeptId(1L);
		RoleToDept roleToDeptTest2 = new RoleToDept();
		roleToDeptTest2.setRoleId(testRoleId);
		roleToDeptTest2.setDeptId(2L);
		List<RoleToDept> roleDeptList = new ArrayList<RoleToDept>();
		roleDeptList.add(roleToDeptTest1);
		roleDeptList.add(roleToDeptTest2);

		roleMapper.addRoleToDeptBatch(roleDeptList);

		List<RoleToDept> roleDeptListResult = roleMapper.getDeptListByRoleId(testRoleId);
		assertEquals(roleDeptListResult.size(), 2);

		// 删除测试角色部门关联信息
		roleMapper.delRoleToDept(testRoleId);

		// 查看是否存在一个测试角色部门关联信息
		roleDeptListResult = roleMapper.getDeptListByRoleId(testRoleId);
		assertEquals(roleDeptListResult.size(), 0);
	}

	/**
	 * 删除角色区域关联信息
	 */
	@Test
	public void testDelRoleToArea() {
		Long testRoleId = 2L;

		// 添加一个角色区域关联信息
		RoleToArea roleToAreaTest1 = new RoleToArea();
		roleToAreaTest1.setRoleId(testRoleId);
		roleToAreaTest1.setAreaId(1L);
		RoleToArea roleToAreaTest2 = new RoleToArea();
		roleToAreaTest2.setRoleId(testRoleId);
		roleToAreaTest2.setAreaId(2L);
		List<RoleToArea> roleAreaList = new ArrayList<RoleToArea>();
		roleAreaList.add(roleToAreaTest1);
		roleAreaList.add(roleToAreaTest2);

		roleMapper.addRoleToAreaBatch(roleAreaList);

		List<RoleToArea> roleAreaListResult = roleMapper.getAreaListByRoleId(testRoleId);
		assertEquals(roleAreaListResult.size(), 2);

		// 删除测试角色区域关联信息
		roleMapper.delRoleToArea(testRoleId);

		// 查看是否存在一个测试角色区域关联信息
		roleAreaListResult = roleMapper.getAreaListByRoleId(testRoleId);
		assertEquals(roleAreaListResult.size(), 0);
	}

	/**
	 * 增加角色对象
	 */
	@Test
	public void testAddRole() {
		// 添加一个tester
		Role roleTest = initTestRole();
		roleMapper.addRole(roleTest);
		Role result = roleMapper.getRoleById(roleTest.getId());
		assertEquals(result.getName(), roleTest.getName());

		// 删除测试角色
		roleMapper.delRole(roleTest.getId());

		// 查看是否存在一个tester
		result = roleMapper.getRoleById(roleTest.getId());
		assertEquals(result, null);
	}

	/**
	 * 批量插入角色菜单对应信息
	 */
	@Test
	public void testAddRoleToMenuBatch() {
		Long testRoleId = 2L;

		// 添加一个角色菜单关联信息
		RoleToMenu roleToMenuTest1 = new RoleToMenu();
		roleToMenuTest1.setRoleId(testRoleId);
		roleToMenuTest1.setMenuId(1L);
		RoleToMenu roleToMenuTest2 = new RoleToMenu();
		roleToMenuTest2.setRoleId(testRoleId);
		roleToMenuTest2.setMenuId(2L);
		List<RoleToMenu> roleMenuList = new ArrayList<RoleToMenu>();
		roleMenuList.add(roleToMenuTest1);
		roleMenuList.add(roleToMenuTest2);

		roleMapper.addRoleToMenuBatch(roleMenuList);

		List<RoleToMenu> roleMenuListResult = roleMapper.getMenuListByRoleId(testRoleId);
		assertEquals(roleMenuListResult.size(), 2);

		// 删除测试角色菜单关联信息
		roleMapper.delRoleToMenu(testRoleId);

		// 查看是否存在一个测试角色菜单关联信息
		roleMenuListResult = roleMapper.getMenuListByRoleId(testRoleId);
		assertEquals(roleMenuListResult.size(), 0);
	}

	/**
	 * 删除角色部门关联信息
	 */
	@Test
	public void testAddRoleToDeptBatch() {
		Long testRoleId = 2L;

		// 添加一个角色部门关联信息
		RoleToDept roleToDeptTest1 = new RoleToDept();
		roleToDeptTest1.setRoleId(testRoleId);
		roleToDeptTest1.setDeptId(1L);
		RoleToDept roleToDeptTest2 = new RoleToDept();
		roleToDeptTest2.setRoleId(testRoleId);
		roleToDeptTest2.setDeptId(2L);
		List<RoleToDept> roleDeptList = new ArrayList<RoleToDept>();
		roleDeptList.add(roleToDeptTest1);
		roleDeptList.add(roleToDeptTest2);

		roleMapper.addRoleToDeptBatch(roleDeptList);

		List<RoleToDept> roleDeptListResult = roleMapper.getDeptListByRoleId(testRoleId);
		assertEquals(roleDeptListResult.size(), 2);

		// 删除测试角色部门关联信息
		roleMapper.delRoleToDept(testRoleId);

		// 查看是否存在一个测试角色部门关联信息
		roleDeptListResult = roleMapper.getDeptListByRoleId(testRoleId);
		assertEquals(roleDeptListResult.size(), 0);
	}

	/**
	 * 删除角色区域关联信息
	 */
	@Test
	public void testaddRoleToAreaBatch() {
		Long testRoleId = 2L;

		// 添加一个角色区域关联信息
		RoleToArea roleToAreaTest1 = new RoleToArea();
		roleToAreaTest1.setRoleId(testRoleId);
		roleToAreaTest1.setAreaId(1L);
		RoleToArea roleToAreaTest2 = new RoleToArea();
		roleToAreaTest2.setRoleId(testRoleId);
		roleToAreaTest2.setAreaId(2L);
		List<RoleToArea> roleAreaList = new ArrayList<RoleToArea>();
		roleAreaList.add(roleToAreaTest1);
		roleAreaList.add(roleToAreaTest2);

		roleMapper.addRoleToAreaBatch(roleAreaList);

		List<RoleToArea> roleAreaListResult = roleMapper.getAreaListByRoleId(testRoleId);
		assertEquals(roleAreaListResult.size(), 2);

		// 删除测试角色区域关联信息
		roleMapper.delRoleToArea(testRoleId);

		// 查看是否存在一个测试角色区域关联信息
		roleAreaListResult = roleMapper.getAreaListByRoleId(testRoleId);
		assertEquals(roleAreaListResult.size(), 0);
	}

	/**
	 * 查找在roleIdList中的角色信息
	 */
	@Test
	public void testGetRoleInfoByRoleIdList() {
		List<Long> roleIdList = new ArrayList<>();
		roleIdList.add(1l);

		List<Role> result = roleMapper.getRoleInfoByRoleIdList(roleIdList);
		assertEquals(result.size(), 1);

	}

	/**
	 * 修改角色信息
	 */
	@Test
	public void testUpdateRole() {
		// 添加一个tester
		Role roleTest = initTestRole();
		roleMapper.addRole(roleTest);
		Role result = roleMapper.getRoleById(roleTest.getId());
		assertEquals(result.getName(), roleTest.getName());

		// 更新角色Name
		String newName = "test";
		roleTest.setName(newName);
		roleMapper.updateRole(roleTest);
		Role updateRole = roleMapper.getRoleById(roleTest.getId());
		assertEquals(updateRole.getName(), newName);

		// 删除测试角色
		roleMapper.delRole(updateRole.getId());

		// 查看是否存在一个tester
		result = roleMapper.getRoleById(updateRole.getId());
		assertEquals(result, null);

	}

	/**
	 * 测试 删除用户拥有的角色信息 和 批量添加用户角色
	 */
	@Test
	public void testDelUserRoleByUserId() {
		// 添加用户与角色关联信息
		Long addUserId = 2L;
		List<UserToRole> userToRoleList = new ArrayList<UserToRole>();

		UserToRole userToRole1 = new UserToRole();
		userToRole1.setUserId(addUserId);
		userToRole1.setRoleId(1L);
		userToRoleList.add(userToRole1);
		UserToRole userToRole2 = new UserToRole();
		userToRole2.setUserId(addUserId);
		userToRole2.setRoleId(2L);
		userToRoleList.add(userToRole2);

		roleMapper.addUserRole(userToRoleList);

		List<UserToRole> userToRoleListResult = roleMapper.getUserRoleByUserId(addUserId);
		assertEquals(userToRoleListResult.size(), 2);
		
		// 删除测试区域
		roleMapper.delUserRoleByUserId(addUserId);
		userToRoleListResult = roleMapper.getUserRoleByUserId(addUserId);
		assertEquals(userToRoleListResult.size(), 0);

		// 查看是否存在一个tester
		userToRoleListResult = roleMapper.getUserRoleByUserId(addUserId);
		assertEquals(userToRoleListResult.size(), 0);
	}

}
