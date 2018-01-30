package com.cypher.activiti.dao;

import java.util.List;

import com.cypher.activiti.model.Dept;

public interface DeptMapper {
	/**
	 * 获取部门所有信息
	 * 
	 * @return
	 */
	public List<Dept> getAllDeptInfo();

	/**
	 * 通过id获取部门信息
	 * 
	 * @param dept
	 * @return
	 */
	public Dept getDeptInfoById(Long dept);

	/**
	 * 删除部门
	 * 
	 * @param deptId
	 * @return
	 */
	public boolean delDept(Long deptId);

	/**
	 * 获取所有子节点个数
	 * 
	 * @param parentId
	 * @return
	 */
	public int getChildrenCount(Long parentId);

	/**
	 * 添加部门
	 * 
	 * @param dept
	 * @return
	 */
	public boolean addDept(Dept dept);

	/**
	 * 修改部门
	 * 
	 * @param dept
	 * @return
	 */
	public boolean updateDept(Dept dept);
}