package com.cypher.activiti.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cypher.activiti.Vo.UserVo;
import com.cypher.activiti.dto.UserDto;
import com.cypher.activiti.model.User;
import com.cypher.activiti.model.UserToRole;

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
	 * 
	 * @param userId
	 * @return
	 */
	UserDto getUserInfoById(Long userId);

	/**
	 * 保存用户信息并返回用户id
	 * 
	 * @param user
	 * @return
	 */
	boolean updateUser(User user);

	/**
	 * 保存用户信息并返回用户id
	 * 
	 * @param user
	 * @return
	 */
	boolean updatePwd(Long userId, String password);

	/**
	 * 获取所有用户信息,返回User 类型对象
	 * 
	 * @return
	 */
	public List<User> getAllUserInfo();

	/**
	 * 获取所有用户信息,返回UserDto类型对象
	 * 
	 * @param user
	 * @return
	 */
	public List<UserDto> getUserDtoList(User user);

	/**
	 * 新增用户信息
	 * 
	 * @param user
	 * @return
	 */
	public boolean addUser(User user);

	/**
	 * 删除用户
	 * 
	 * @param userId
	 * @return
	 */
	public boolean delUser(Long userId);

	/**
	 * 
	 * 获取用户角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserToRole> getUserRoleByUserId(Long userId);
	
	/**
	 * 
	 * 根据用户id查询用户所拥有的角色和角色名
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> getUserRoleNameListByUserId(Long userId);
}