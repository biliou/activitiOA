package com.cypher.activiti.service;

import java.util.List;

import com.cypher.activiti.model.User;

public interface IUserService {

	/**
	 * 获取个人明细
	 * @param userId
	 * @return
	 */
	public User getUserInfoById(int userId);
	
	/**
	 * 添加个人明细
	 * @param user
	 * @return
	 */
	public int addUser(User user);
	
	/**
	 * 删除个人明细
	 * @param userId
	 * @return
	 */
	public boolean delUser(int userId);
	
	/**
	 * 更新个人信息
	 * @param user
	 * @return
	 */
	public boolean updateUser(User user);
	
	public List<User> getUsersInfo();
}
