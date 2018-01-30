package com.cypher.activiti.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cypher.activiti.dao.DeptMapper;
import com.cypher.activiti.model.Dept;
import com.cypher.activiti.service.IDeptService;

@Service
public class DeptService implements IDeptService {
	
	@Autowired
	private DeptMapper deptMapper;

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
	public boolean addDept(Dept dept,Long userId) {
		dept.setUpdateBy(userId.toString());
		dept.setUpdateDate(new Date());
		return deptMapper.addDept(dept);
	}

	@Override
	public boolean updateDept(Dept dept, Long userId) {
		dept.setUpdateBy(userId.toString());
		dept.setUpdateDate(new Date());
		return deptMapper.updateDept(dept);
	}


}
