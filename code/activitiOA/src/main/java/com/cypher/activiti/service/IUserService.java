package com.cypher.activiti.service;

import com.cypher.activiti.dto.UserDto;
import com.cypher.activiti.model.User;

public interface IUserService {
	/**
	 * 通过用户userId获取个人信息
	 * 
	 * @param userId
	 * @return
	 */
	public UserDto getUserInfoById(Long userId);

	/**
	 * 保存个人信息
	 * 
	 * @param userId
	 * @return
	 */
	public boolean saveSelfUserInfo(User user);
	
	/**
	 * 修改个人密码
	 * 
	 * @param userId
	 * @return
	 */
	public boolean saveChangePwd(long userId, String oldPassword, String newPassword);


}
