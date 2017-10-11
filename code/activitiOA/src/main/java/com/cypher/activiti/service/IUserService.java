package com.cypher.activiti.service;

import com.cypher.activiti.model.User;

public interface IUserService {
	/**
	 * 获取个人明细
	 */
	public User getUserInfoById(int userId);
	
	
}
