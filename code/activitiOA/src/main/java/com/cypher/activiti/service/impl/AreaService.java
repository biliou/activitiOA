package com.cypher.activiti.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cypher.activiti.dao.AreaMapper;
import com.cypher.activiti.dao.RoleMapper;
import com.cypher.activiti.model.Area;
import com.cypher.activiti.model.RoleToArea;
import com.cypher.activiti.service.IAreaService;

@Service
public class AreaService implements IAreaService {

	@Autowired
	private AreaMapper areaMapper;
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Area> getAllAreaInfo() {
		return areaMapper.getAllAreaInfo();
	}

	@Override
	public Area getAreaById(Long userId) {
		return areaMapper.getAreaById(userId);
	}

	@Override
	public boolean delArea(Long areaId) {
		return areaMapper.delArea(areaId);
	}

	@Override
	public int getChildrenCount(Long parentId) {
		return areaMapper.getChildrenCount(parentId);
	}

	@Override
	public boolean addArea(Area area, Long userId) {
		boolean flag = false;
		area.setUpdateBy(userId.toString());
		area.setUpdateDate(new Date());
		flag = areaMapper.addArea(area);

		// 在增加区域的时候,同时需要给超级管理增加一条映射记录
		RoleToArea roleArea = new RoleToArea();
		roleArea.setRoleId(1l);
		roleArea.setAreaId(area.getId());
		flag = this.roleMapper.addRoleToArea(roleArea);

		return flag;
	}

	@Override
	public boolean updateArea(Area area, Long userId) {
		area.setUpdateBy(userId.toString());
		area.setUpdateDate(new Date());
		return areaMapper.updateArea(area);
	}

}
