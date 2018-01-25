package com.cypher.activiti.service;

import com.cypher.activiti.model.User;

public interface ILoginService {
	/**
	 * 登录
	 * @param loginName
	 * @param password
	 * @return
	 */
	public User login(String loginName,String password);
}
