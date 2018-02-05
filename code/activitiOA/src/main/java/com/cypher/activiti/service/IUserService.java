package com.cypher.activiti.service;

import java.util.List;

import com.cypher.activiti.Vo.UserVo;
import com.cypher.activiti.dto.UserDto;
import com.cypher.activiti.model.User;
import com.cypher.activiti.model.UserToRole;
import com.github.pagehelper.PageInfo;

/**
 * 用户信息管理业务接口
 * 
 * @author Administrator
 *
 */
public interface IUserService {
	/**
	 * 通过用户userId获取个人信息
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public UserDto getUserInfoById(Long userId);

	/**
	 * 保存个人信息
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public boolean saveSelfUserInfo(User user);

	/**
	 * 修改个人密码
	 * 
	 * @param userId
	 *            用户id
	 * @param oldPassword
	 *            旧密码
	 * @param newPassword
	 *            新密码
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
	 *            user对象（用户信息查询条件）
	 * @param pageNo
	 *            页数
	 * @param pageSize
	 *            一页显示多少条
	 * @return
	 */
	public PageInfo<UserDto> getUserDtoList(User user, Integer pageNo, Integer pageSize);

	/**
	 * 删除用户
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public boolean delUser(Long userId);

	/**
	 * 
	 * 获取用户角色
	 * 
	 * @param userId
	 *            用户id
	 * @return
	 */
	public List<UserToRole> getUserRoleByUserId(Long userId);

	/**
	 * 修改用户以及角色对应关系
	 * 
	 * @param userVo
	 * @param updateUserId
	 *            修改者用户id
	 * @return
	 */
	public boolean updateUserVo(UserVo userVo, Long updateUserId);

	/**
	 * 增加用户信息包含用户角色对应信息
	 * 
	 * @param userVo
	 * @param updateUserId
	 *            修改者用户id
	 * @return
	 */
	public boolean addUserVo(UserVo userVo, Long updateUserId);

}
