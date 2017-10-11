package com.cypher.activiti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.cypher.activiti.dao.UserMapper;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IUserService;

@Service
public class UserService implements IUserService{
	
	@Autowired
	private UserMapper userMapper;

	public User getUserInfoById(int userId) {
		return this.userMapper.selectByPrimaryKey(userId);
	}

}
