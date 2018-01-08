package com.cypher.activiti.dao;

import java.util.List;

import com.cypher.activiti.model.User;
/**
 * 定义用户增删改查dao接口
 * @author Administrator
 *
 */
public interface UserMapper {
	
	/**
	 * 增加用户对象
	 * @param id
	 * @return
	 */
	public int addUser(User user);
	
	/**
	 * 根据用户id获取用户对象信息
	 * @param id
	 * @return
	 */
	public User getUserById(int id);
	
	/**
	 * 查询所有用户对象
	 * @return
	 */
	public List<User> getUserList();
	
	/**
	 * 删除用户对象
	 * @param id
	 * @return
	 */
    public int delUser(Integer id);
    
    /**
     * 更新用户对象
     * @param user
     * @return
     */
    public int updateUser(User user);

   
}