package com.cypher.activiti.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cypher.activiti.Vo.UserVo;
import com.cypher.activiti.core.encrypt.PwdEncrypt;
import com.cypher.activiti.dao.RoleMapper;
import com.cypher.activiti.dao.UserMapper;
import com.cypher.activiti.dto.UserDto;
import com.cypher.activiti.model.User;
import com.cypher.activiti.model.UserToRole;
import com.cypher.activiti.service.IUserService;
import com.cypher.activiti.util.EncryptUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RoleMapper roleMapper;

	@Override
	public UserDto getUserInfoById(Long userId) {

		return userMapper.getUserInfoById(userId);
	}

	@Override
	public boolean saveSelfUserInfo(User user) {
		return userMapper.updateUser(user);
	}

	@Override
	public boolean saveChangePwd(long userId, String oldPassword, String newPassword) {
		// 对比用户输入的旧密码是否匹配当前密码
		UserDto userDto = userMapper.getUserInfoById(userId);

		String destPwd = userDto.getPassword();
		boolean result = PwdEncrypt.validataPwd(oldPassword, destPwd);

		if (result) {
			// 新的密码加密后的值
			String encodeNewPwd = PwdEncrypt.encodePwd(newPassword);

			// 保存修改密码
			return userMapper.updatePwd(userId, encodeNewPwd);
		} else {
			return false;
		}
	}

	@Override
	public List<User> getAllUserInfo() {
		return userMapper.getAllUserInfo();
	}

	@Override
	public PageInfo<UserDto> getUserDtoList(User user, Integer pageNo, Integer pageSize) {
		// 分页
		PageHelper.startPage(pageNo, pageSize);
		List<UserDto> userList = userMapper.getUserDtoList(user);
		PageInfo<UserDto> pageInfo = new PageInfo<UserDto>(userList);

		return pageInfo;
	}

	@Override
	public boolean delUser(Long userId) {
		return userMapper.delUser(userId);
	}

	@Override
	public List<UserToRole> getUserRoleByUserId(Long userId) {
		return userMapper.getUserRoleByUserId(userId);
	}

	/**
	 * 修改用户以及角色对应关系<br/>
	 * 1.更新用户个人信息<br/>
	 * 2.删除用户原本拥有角色<br/>
	 * 3.批量添加用户角色<br/>
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public boolean updateUserVo(UserVo userVo, Long updateUserId) {
		boolean flag = false;

		// 更新用户信息
		User user = userVo.getUser();
		user.setUpdateBy(updateUserId.toString());
		user.setUpdateDate(new Date());
		flag = userMapper.updateUser(userVo.getUser());

		// 删除用户原本拥有角色
		flag = roleMapper.delUserRoleByUserId(user.getUserId());

		// 批量添加用户角色
		List<UserToRole> userToRoleList = new ArrayList<UserToRole>();
		for (Long roleId : userVo.getRoleIds().keySet()) {
			UserToRole userToRole = new UserToRole();
			userToRole.setUserId(user.getUserId());
			userToRole.setRoleId(roleId);
			userToRoleList.add(userToRole);
		}
		flag = roleMapper.addUserRole(userToRoleList);

		return flag;
	}

	/**
	 * 增加用户信息包含用户角色对应信息<br/>
	 * 1.添加用户个人信息<br/>
	 * 2.批量添加用户角色<br/>
	 */
	@Transactional(isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
	public boolean addUserVo(UserVo userVo, Long updateUserId) {
		boolean flag = false;

		// 添加用户个人信息
		User user = userVo.getUser();
		user.setUpdateBy(updateUserId.toString());
		user.setUpdateDate(new Date());
		//设置默认密码
		String pwd = "123";
		String encryptPwd = PwdEncrypt.encodePwd(pwd);
		user.setPassword(encryptPwd);
		flag = userMapper.addUser(user);

		// 批量添加用户角色
		List<UserToRole> userToRoleList = new ArrayList<UserToRole>();
		for (Long roleId : userVo.getRoleIds().keySet()) {
			UserToRole userToRole = new UserToRole();
			userToRole.setUserId(user.getUserId());
			userToRole.setRoleId(roleId);
			userToRoleList.add(userToRole);
		}
		flag = roleMapper.addUserRole(userToRoleList);

		return flag;
	}

}
