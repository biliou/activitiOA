package com.cypher.activiti.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cypher.activiti.core.restful.Response;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IUserService;

@Controller
public class MainController {

	@Autowired
	private IUserService userService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "index";

	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String showUser(Model model , @RequestParam(value="id",required=true) Integer id) {
		User user = userService.getUserInfoById(id);
		model.addAttribute("user", user);

		return "user/showUser";

	}
	
	@RequestMapping(value = "/api/user/json", method = RequestMethod.GET)
	public @ResponseBody Response showUserToJSON(Model model, @RequestParam(value="id",required=true) Integer id) {
		User user = userService.getUserInfoById(id);
		model.addAttribute("user", user);
		return new Response().success(model);

	}

}
