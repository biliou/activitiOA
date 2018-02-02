package com.cypher.activiti.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cypher.activiti.dto.RoleDto;
import com.cypher.activiti.model.Area;
import com.cypher.activiti.model.Dept;
import com.cypher.activiti.model.Menu;
import com.cypher.activiti.model.Role;
import com.cypher.activiti.model.RoleToArea;
import com.cypher.activiti.model.RoleToDept;
import com.cypher.activiti.model.RoleToMenu;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IAreaService;
import com.cypher.activiti.service.IDeptService;
import com.cypher.activiti.service.IMenuService;
import com.cypher.activiti.service.IRoleService;

@Controller
public class RoleController {

	private static Logger logger = Logger.getLogger(RoleController.class);

	@Autowired
	private IRoleService roleService;
	@Autowired
	private IMenuService menuService;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private IAreaService areaService;

	// 跳转角色列表页
	@RequestMapping(value = "/sysmg/role/gotoRoleList")
	public String gotoRoleList(Model model) {

		List<Role> roleList = roleService.getAllRoleInfo();

		model.addAttribute("roleList", roleList);
		return "/sysmg/role/roleList";
	}

	// 跳转角色编辑页
	@RequestMapping(value = "/sysmg/role/gotoRoleEdit")
	public String gotoRoleEdit(@ModelAttribute("editFlag") Integer editFlag, Long roleId, Model model) {
		// 不管是修改还是新增,我们都需要在编辑页面将部门树,菜单树,区域树显示出来
		List<Menu> menuList = menuService.getAllMenuInfo();
		List<Dept> deptList = deptService.getAllDeptInfo();
		List<Area> areaList = areaService.getAllAreaInfo();
		model.addAttribute("menuList", menuList);
		model.addAttribute("deptList", deptList);
		model.addAttribute("areaList", areaList);

		// 修改操作的时候,我们需要查询出该角色本身拥有的各项权限信息
		if (editFlag == 2) {
			List<RoleToMenu> roleMenuList = roleService.getMenuListByRoleId(roleId);
			List<RoleToDept> roleDeptList = roleService.getDeptListByRoleId(roleId);
			List<RoleToArea> roleAreaList = roleService.getAreaListByRoleId(roleId);
			model.addAttribute("roleMenuList", roleMenuList);
			model.addAttribute("roleDeptList", roleDeptList);
			model.addAttribute("roleAreaList", roleAreaList);

			Role role = roleService.getRoleById(roleId);
			model.addAttribute("role", role);
		}

		return "/sysmg/role/roleEdit";
	}

	// 修改和添加角色
	@RequestMapping(value = "/sysmg/role/saveRole", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveRole(@RequestBody RoleDto roleDto, HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 获取用户id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			// 判断新增还是修改,根据role对象是否有ID
			if (roleDto != null && roleDto.getRole() != null && roleDto.getRole().getId() != null) {
				roleService.updateRole(roleDto, user.getUserId());
				resultMap.put("result", "修改角色信息成功");
			} else {
				roleService.addRole(roleDto, user.getUserId());
				resultMap.put("result", "增加角色信息成功");
			}
		} catch (Exception e) {
			logger.error("操作角色信息失败", e);
			resultMap.put("result", "增加角色信息失败");
		}

		return resultMap;
	}

	// 删除角色
	@RequestMapping(value = "/sysmg/role/delRole/{roleId}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delRole(@PathVariable Long roleId) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (roleId != null) {
			try {
				roleService.delRole(roleId);
				resultMap.put("result", "删除角色信息成功");

			} catch (Exception e) {
				resultMap.put("result", "请输入roleId");
				logger.warn("删除角色信息失败", e);
			}

		} else {
			resultMap.put("result", "请输入roleId");
		}

		return resultMap;
	}

}
