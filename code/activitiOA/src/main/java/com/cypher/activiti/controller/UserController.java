package com.cypher.activiti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cypher.activiti.core.restful.Response;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IUserService;

@Controller
public class UserController {
	
	@Autowired
	private IUserService userService;
	
	/**
	 * 增加用户个人信息
	 * @return
	 */
	@RequestMapping(value = "/user/{name}/{password}/{age}", method = RequestMethod.POST)
	public @ResponseBody Response addUserInfo(Model model,@PathVariable(value="name") String name,
			@PathVariable(value="password") String password,
			@PathVariable(value="age") Integer age) {
		User user = new User();
		user.setUserName(name);
		user.setPassword(password);
		user.setAge(age);
		
		int result = userService.addUser(user);
		model.addAttribute("id", result);
		return new Response().success(model);
	}
	
	/**
	 * 获取个人信息
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public @ResponseBody Response getUserInfo(Model model , @PathVariable(value="id") Integer id) {
		User user = userService.getUserInfoById(id);
		model.addAttribute("user", user);
		
		return new Response().success(model);

	}
	
	/**
	 * 删除个人信息
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public @ResponseBody Response delUserInfo(Model model , @PathVariable(value="id") Integer id) {
		boolean result = userService.delUser(id);
		model.addAttribute("result", result);
		
		return new Response().success(model);

	}
	
	/**
	 * 通过id更新个人信息
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}/{name}", method = RequestMethod.PUT)
	public @ResponseBody Response updateUserInfo(Model model , @PathVariable(value="id") Integer id
			, @PathVariable(value="name") String name) {
		
		User user = new User();
		user.setId(id);
		user.setUserName(name);
		
		boolean result = userService.updateUser(user);
		model.addAttribute("result", result);
		
		return new Response().success(model);

	}
	
	/**
	 * 获取所有用户信息
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody Response getUsersInfo(Model model) {
		List<User> result = userService.getUsersInfo();
		model.addAttribute("UserList", result);
		
		return new Response().success(model);

	}
}
