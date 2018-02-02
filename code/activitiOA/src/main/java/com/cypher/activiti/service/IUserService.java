package com.cypher.activiti.service;

import java.util.List;

import com.cypher.activiti.Vo.UserVo;
import com.cypher.activiti.dto.UserDto;
import com.cypher.activiti.model.User;
import com.cypher.activiti.model.UserToRole;
import com.github.pagehelper.PageInfo;

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

	/**
	 * 获取所有用户信息,返回User类型对象
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
	public PageInfo<UserDto> getUserDtoList(User user, Integer pageNo, Integer pageSize);

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
	 * 修改用户以及角色对应关系
	 * 
	 * @param userVo
	 * @return
	 */
	public boolean updateUserVo(UserVo userVo,Long updateUserId);

	/**
	 * 增加用户信息包含用户角色对应信息
	 * 
	 * @param userVo
	 */
	public boolean addUserVo(UserVo userVo,Long updateUserId);

}
