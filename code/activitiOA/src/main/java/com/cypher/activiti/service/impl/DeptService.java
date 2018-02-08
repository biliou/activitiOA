package com.cypher.activiti.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cypher.activiti.dao.DeptMapper;
import com.cypher.activiti.dao.RoleMapper;
import com.cypher.activiti.model.Dept;
import com.cypher.activiti.model.RoleToDept;
import com.cypher.activiti.service.IDeptService;

@Service
public class DeptService implements IDeptService {

	@Autowired
	private DeptMapper deptMapper;
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Dept> getAllDeptInfo() {
		return deptMapper.getAllDeptInfo();
	}

	@Override
	public Dept getDeptInfoById(Long deptId) {
		return deptMapper.getDeptInfoById(deptId);
	}

	@Override
	public boolean delDept(Long deptId) {
		return deptMapper.delDept(deptId);
	}

	@Override
	public int getChildrenCount(Long parentId) {
		return deptMapper.getChildrenCount(parentId);
	}

	@Override
	public boolean addDept(Dept dept, Long userId) {
		boolean flag = false;
		dept.setUpdateBy(userId.toString());
		dept.setUpdateDate(new Date());
		flag = deptMapper.addDept(dept);

		// 在增加部门的时候,同时需要给超级管理增加一条映射记录
		RoleToDept roleDept = new RoleToDept();
		roleDept.setRoleId(1l);
		roleDept.setDeptId(userId);
		flag = roleMapper.addRoleToDept(roleDept);

		return flag;
	}

	@Override
	public boolean updateDept(Dept dept, Long userId) {
		dept.setUpdateBy(userId.toString());
		dept.setUpdateDate(new Date());
		return deptMapper.updateDept(dept);
	}

}
