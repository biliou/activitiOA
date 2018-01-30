package com.cypher.activiti.service;

import java.util.List;

import com.cypher.activiti.model.Dept;

public interface IDeptService {

	/**
	 * 获取所有部门信息
	 * 
	 * @return
	 */
	public List<Dept> getAllDeptInfo();

	/**
	 * 通过deptId获取部门信息
	 * 
	 * @param deptId
	 * @return
	 */
	public Dept getDeptInfoById(Long deptId);

	/**
	 * 删除部门
	 * 
	 * @param deptId
	 * @return
	 */
	public boolean delDept(Long deptId);

	/***
	 * 获取子节点个数
	 * 
	 * @param parentId
	 * @return
	 */
	public int getChildrenCount(Long parentId);

	/**
	 * 添加部门
	 * 
	 * @param parentId
	 * @return
	 */
	public boolean addDept(Dept dept, Long userId);

	/**
	 * 修改部门
	 * 
	 * @param dept
	 * @param userId
	 * @return
	 */
	public boolean updateDept(Dept dept, Long userId);
}
