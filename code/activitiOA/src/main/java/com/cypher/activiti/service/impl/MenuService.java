package com.cypher.activiti.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cypher.activiti.dao.MenuMapper;
import com.cypher.activiti.model.Menu;
import com.cypher.activiti.service.IMenuService;

@Service
public class MenuService implements IMenuService {

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public List<Menu> getAllMenuInfo() {
		return menuMapper.selectAllMenuInfo();
	}

	@Override
	public Menu getMenuById(long menuId) {
		return menuMapper.getMenuInfo(menuId);
	}

	@Override
	public boolean delMenu(long menuId) {
		return menuMapper.delMenu(menuId);
	}

	@Override
	public boolean updateMenu(Menu menu, String menuName) {
		menu.setUpdateBy(menuName);
		menu.setUpdateDate(new Date());
		return menuMapper.updateMenu(menu);
	}

	@Override
	public boolean addMenu(Menu menu, String menuName) {
		menu.setUpdateBy(menuName);
		menu.setUpdateDate(new Date());
		return menuMapper.addMenu(menu);
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
