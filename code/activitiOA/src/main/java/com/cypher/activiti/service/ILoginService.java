package com.cypher.activiti.service;

import com.cypher.activiti.model.User;

/**
 * 登录功能业务层接口
 * 
 * @author Administrator
 *
 */
public interface ILoginService {
	/**
	 * 登录
	 * 
	 * @param loginName
	 *            登录名
	 * @param password
	 *            密码
	 * @return
	 */
	public User login(String loginName, String password);
}
