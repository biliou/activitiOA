package com.cypher.activiti.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cypher.activiti.model.Menu;
import com.cypher.activiti.service.IMenuService;

@Controller
public class MenuController {
	
	@Autowired
	private IMenuService menuService;
	
	//跳转菜单管理
	@RequestMapping(value="/gotoMenuList")
	public String gotoMenuEdit(Model model) {
		System.out.println("menuList");
		
		// 请求MenuService 层，获取所有菜单
		List<Menu> menuList = menuService.getAllMenuInfo();
		
		model.addAttribute("menuList", menuList);

		return "/menu/menuList";
	}
}
