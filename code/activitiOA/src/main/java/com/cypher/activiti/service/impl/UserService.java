package com.cypher.activiti.service.impl;

import java.util.List;

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
		return this.userMapper.getUserById(userId);
	}

	public int addUser(User user) {
		userMapper.addUser(user);
		int addId = user.getId();
		return addId;
	}

	public boolean delUser(int userId) {
		int result = userMapper.delUser(userId);
		if(result>0) {
			return true;
		}else {
			return false;
		}
	}

	public boolean updateUser(User user) {
		int result = userMapper.updateUser(user);
		if(result>0) {
			return true;
		}else {
			return false;
		}
	}

	public List<User> getUsersInfo() {
		return userMapper.getUserList();
	}
	
	

}
