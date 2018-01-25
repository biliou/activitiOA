package com.cypher.activiti.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cypher.activiti.core.restful.Response;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.ILoginService;

@Controller
public class LoginController {

	private static Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private ILoginService loginService;

	// 进入首页
	@RequestMapping(value = "/")
	public String goToIndex() {
		return "login";
	}

	// 进入首页
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String goToLogin2() {
		return "login";
	}

	// 登录
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@RequestParam String loginName, @RequestParam String password, HttpSession session,
			Model model) {
		User user = loginService.login(loginName, password);

		// 验证参数
		if (StringUtils.isNotEmpty(loginName) && StringUtils.isNoneEmpty(password)) {
			if (user != null) {
				logger.info("登录用户名 = " + loginName);
				logger.info("登录成功");

				session.setAttribute("user", user);
				return "redirect:main";
				// return new Response().success(model);
			} else {
				model.addAttribute("loginFlag", "登录失败");
				return "forward:/WEB-INF/pages/login.jsp";
				// return new Response().failure("登录失败");
			}
		} else {
			model.addAttribute("loginFlag", "登录失败,请输入正确的用户名 和 密码");
			return "forward:/WEB-INF/pages/login.jsp";
			// return new Response().failure("登录失败,请输入正确的用户名 和 密码");
		}
	}

	// 主页跳转
	@RequestMapping("/main")
	public String main() {
		return "main/main";
	}
}
