package com.cypher.activiti.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cypher.activiti.core.encrypt.PwdEncrypt;
import com.cypher.activiti.dao.UserMapper;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.ILoginService;

@Service
public class LoginService implements ILoginService {

	@Autowired
	private UserMapper userMapper;

	@Override
	public User login(String loginName, String password) {

		User user = new User();
		user.setLoginName(loginName);

		List<User> userList = userMapper.getUserList(user);

		if (userList.size() > 0) {
			String destPwd = userList.get(0).getPassword();
			boolean result = PwdEncrypt.validataPwd(password, destPwd);

			if (result)
				return userList.get(0);
			else {
				return null;
			}
		} else {
			return null;
		}

	}

}
