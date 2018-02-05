package com.cypher.activiti.controller;

import java.util.ArrayList;
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

import com.alibaba.fastjson.JSON;
import com.cypher.activiti.Vo.UserVo;
import com.cypher.activiti.dto.UserDto;
import com.cypher.activiti.model.Role;
import com.cypher.activiti.model.User;
import com.cypher.activiti.model.UserToRole;
import com.cypher.activiti.service.IRoleService;
import com.cypher.activiti.service.IUserService;
import com.cypher.activiti.util.PageUtils;
import com.github.pagehelper.PageInfo;

/**
 * 用于用户信息管理的Controller
 * 
 * @author Administrator
 *
 */
@Controller
public class UserController {

	private static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	public IUserService userService;

	@Autowired
	public IRoleService roleService;

	// 进入个人信息页
	@RequestMapping(value = "/sysmg/user/gotoUserInfo")
	public String gotoUserInfo() {
		return "sysmg/user/userInfo";
	}

	// 进入获取个人信息
	@RequestMapping(value = "/sysmg/user", method = RequestMethod.GET)
	public @ResponseBody UserDto getUserInfoById(HttpServletRequest request) {

		// 从session中获取用户id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		long userId = user.getUserId();

		// 请求Service层，得到UserDto对象
		UserDto userDto = userService.getUserInfoById(userId);

		logger.info("getUserInfoById");

		return userDto;
	}

	// 进入保存个人信息
	@RequestMapping(value = "/sysmg/user", method = RequestMethod.PUT)
	public @ResponseBody Map<String, Object> saveSelfUserInfo(@RequestBody User user) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 请求Service层，保存user对象
		boolean result = userService.saveSelfUserInfo(user);

		try {
			if (result) {
				resultMap.put("result", "修改用户信息成功");
				logger.info(user.getUserId() + " 修改用户信息成功");
			} else {
				resultMap.put("result", "修改用户信息失败");
				logger.error("修改用户信息失败");
			}
		} catch (Exception e) {
			resultMap.put("result", "修改用户信息失败");
			logger.error("修改用户信息失败", e);
		}

		return resultMap;
	}

	// 进入修改密码页
	@RequestMapping(value = "/sysmg/user/gotoChangePwd")
	public String gotoChangePwd() {
		return "sysmg/user/changePwd";
	}

	// 修改密码
	@RequestMapping(value = "/sysmg/user/pwd", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveChangePwd(HttpServletRequest request, String oldPassword,
			String newPassword) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 从session中获取用户id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		long userId = user.getUserId();

		// 请求Service层，修改密码
		boolean result = userService.saveChangePwd(userId, oldPassword, newPassword);

		if (result) {
			resultMap.put("result", "修改用户密码成功");
			logger.info(user.getUserId() + " 修改用户信息成功");
		} else {
			resultMap.put("result", "修改用户密码失败，请输入正确的旧密码");
			logger.info(user.getUserId() + " 修改用户信息失败");
		}

		return resultMap;
	}

	// 用户管理列表页跳转
	@RequestMapping(value = "/sysmg/user/gotoUserList")
	public String gotoUserList() {
		return "/sysmg/user/userList";
	}

	// 用户管理编辑页跳转
	@RequestMapping(value = "/sysmg/user/gotoUserEdit")
	public String gotoUserEdit(@ModelAttribute("editFlag") Integer editFlag, Long userId, Model model) {

		// 将所有的角色列表查询出来
		List<Role> roleList = roleService.getAllRoleInfo();
		model.addAttribute("roleList", roleList);

		// 修改
		if (editFlag == 2) {
			// 获取用户信息
			UserDto userDto = userService.getUserInfoById(userId);
			model.addAttribute("userDto", userDto);

			// 获取用户角色
			List<UserToRole> userRoleList = userService.getUserRoleByUserId(userId);
			if (userRoleList != null) {
				Map<Long, Long> roleCheckMap = new HashMap<Long, Long>();
				for (UserToRole userRole : userRoleList) {
					roleCheckMap.put(userRole.getRoleId(), userRole.getRoleId());
				}
				model.addAttribute("roleCheckMap", roleCheckMap);
			}

		}

		return "/sysmg/user/userEdit";
	}

	// 获取用户列表信息
	@RequestMapping(value = "/sysmg/user/getUserList")
	public @ResponseBody Map<String, Object> getUserList(Long deptId, String userName, Integer pageNo,
			Integer pageSize) {
		// 获取查询条件
		User user = new User();
		if (deptId != null)
			user.setDeptId(deptId);
		if (userName != null)
			user.setUserName(userName);

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 获取所有用户信息
		PageInfo<UserDto> pageInfo = userService.getUserDtoList(user, pageNo, pageSize);
		List<UserDto> userList = new ArrayList<UserDto>();
		userList = pageInfo.getList();
		resultMap.put("userList", userList);

		// 获取分页后信息
		String pageStr = PageUtils.pageStr(pageInfo, "userMgr.getUserListPage");
		resultMap.put("pageStr", pageStr);

		return resultMap;
	}

	// 增加用户和修改用户
	@RequestMapping(value = "/sysmg/user/saveUser", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveUser(@RequestBody UserVo userVo, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 获取session用户id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			if (userVo != null && userVo.getUser() != null && userVo.getUser().getUserId() != null) {
				userService.updateUserVo(userVo, user.getUserId());
				resultMap.put("result", "修改用户信息成功");
			} else {// 增加
				userService.addUserVo(userVo, user.getUserId());
				resultMap.put("result", "增加用户信息成功");
			}
		} catch (Exception e) {
			resultMap.put("result", "操作用户失败");
			logger.error("操作用户失败", e);
		}
		return resultMap;
	}

	// 删除用户
	@RequestMapping(value = "/sysmg/user/delUser/{userId}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delUser(@PathVariable Long userId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (userId != null) {

			try {
				userService.delUser(userId);
				resultMap.put("result", "删除用户信息成功");
			} catch (Exception e) {
				resultMap.put("result", "删除用户信息失败");
			}

		} else {
			resultMap.put("result", "请输入userId");
		}

		return resultMap;

	}

}
