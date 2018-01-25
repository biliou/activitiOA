package com.cypher.activiti.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cypher.activiti.core.encrypt.PwdEncrypt;
import com.cypher.activiti.dao.UserMapper;
import com.cypher.activiti.dto.UserDto;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IUserService;

@Service
public class UserService implements IUserService{
	
	@Autowired
	private UserMapper userMapper;

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
		//对比用户输入的旧密码是否匹配当前密码
		UserDto userDto = userMapper.getUserInfoById(userId);
		
		String destPwd = userDto.getPassword();
		boolean result = PwdEncrypt.validataPwd(oldPassword, destPwd);
		
		if(result) {
			//新的密码加密后的值
			String encodeNewPwd = PwdEncrypt.encodePwd(newPassword);
			
			//保存修改密码
			return userMapper.updatePwd(userId, encodeNewPwd);
		}else {
			return false;
		}
	}


}
