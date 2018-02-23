package com.cypher.activiti.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
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
import com.cypher.activiti.model.Menu;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.ILoginService;
import com.cypher.activiti.service.IMenuService;
import com.cypher.activiti.service.impl.MenuService;

/**
 * 用于登录功能的Controller
 * @author Administrator
 *
 */
@Controller
public class LoginController {

	private static Logger logger = Logger.getLogger(LoginController.class);

	@Autowired
	private ILoginService loginService;
	
	@Autowired
	private IMenuService menuService;

	// 跳转登录页
	@RequestMapping(value = "/")
	public String goToIndex() {
		return "login";
	}

	// 跳转登录页（防值get方式出异常）
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String goToLogin() {
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

	// 跳转主页
	@RequestMapping("/main")
	public String main(Model model,HttpServletRequest request) {
		//获取用户名显示
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		model.addAttribute("userName", user.getUserName());
		
		// 将操作用户对应的所有菜单查询出来,返回给页面进行动态加载
		Long userId = user.getUserId();
		List<Menu> menuList = menuService.getMenuListByUserId(userId);
		model.addAttribute("menuList", menuList);
		
		return "main/main";
	}

	// 登出
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
}
