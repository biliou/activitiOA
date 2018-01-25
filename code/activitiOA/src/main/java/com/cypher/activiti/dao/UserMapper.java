package com.cypher.activiti.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cypher.activiti.dto.UserDto;
import com.cypher.activiti.model.User;

@Repository
public interface UserMapper {
	/**
	 * 根据条件查询用户列表
	 * 
	 * @param user
	 * @return
	 */
	List<User> getUserList(User user);

	/**
	 * 根据id获取用户明细 包含部门名称以及角色列表
	 * @param userId
	 * @return
	 */
	UserDto getUserInfoById(Long userId);
	
	/**
	 * 保存用户信息并返回用户id
	 * @param user
	 * @return
	 */
	boolean updateUser(User user);
	
	/**
	 * 保存用户信息并返回用户id
	 * @param user
	 * @return
	 */
	boolean updatePwd(Long userId,String password);
}