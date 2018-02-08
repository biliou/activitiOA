package com.cypher.activiti.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cypher.activiti.dao.MenuMapper;
import com.cypher.activiti.dao.RoleMapper;
import com.cypher.activiti.model.Menu;
import com.cypher.activiti.model.RoleToMenu;
import com.cypher.activiti.service.IMenuService;

@Service
public class MenuService implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Menu> getAllMenuInfo() {
		return menuMapper.getAllMenuInfo();
	}

	@Override
	public Menu getMenuById(long menuId) {
		return menuMapper.getMenuById(menuId);
	}

	@Override
	public boolean delMenu(long menuId) {
		return menuMapper.delMenu(menuId);
	}

	@Override
	public boolean updateMenu(Menu menu, Long userId) {
		menu.setUpdateBy(userId.toString());
		menu.setUpdateDate(new Date());
		return menuMapper.updateMenu(menu);
	}

	@Override
	public boolean addMenu(Menu menu, Long userId) {
		boolean flag = false;
		
		menu.setUpdateBy(userId.toString());
		menu.setUpdateDate(new Date());
		flag = menuMapper.addMenu(menu);
		// 在增加菜单的时候,同时需要给超级管理增加一条映射记录
		RoleToMenu roleMenu = new RoleToMenu();
		roleMenu.setRoleId(1l);
		roleMenu.setMenuId(menu.getId());
		flag = this.roleMapper.addRoleToMenu(roleMenu);
		
		return flag;
	}

	@Override
	public Integer getChildCount(Long menuId) {
		return menuMapper.getChildCount(menuId);
	}

	@Override
	public List<Menu> getMenuListByUserId(Long menuId) {
		return menuMapper.getMenuListByUserId(menuId);
	}

}
