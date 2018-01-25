package com.cypher.activiti.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cypher.activiti.dto.UserDto;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IUserService;
import com.cypher.activiti.service.impl.UserService;

@Controller
public class UserController {

	private static Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	public IUserService userService;

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

}
