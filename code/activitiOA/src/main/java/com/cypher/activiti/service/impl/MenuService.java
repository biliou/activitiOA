package com.cypher.activiti.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cypher.activiti.dao.MenuMapper;
import com.cypher.activiti.model.Menu;
import com.cypher.activiti.service.IMenuService;

@Service
public class MenuService implements IMenuService{
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Override
	public List<Menu> getAllMenuInfo() {
		return menuMapper.selectAllMenuInfo();
	}

}
