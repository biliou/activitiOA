package com.cypher.activiti.dao;

import java.util.List;

import com.cypher.activiti.model.Area;
import com.cypher.activiti.model.AreaTree;

public interface AreaMapper {
	/**
	 * 获取所有区域信息
	 * 
	 * @return
	 */
	public List<Area> getAllAreaInfo();

	/**
	 * 通过id获取区域信息
	 * 
	 * @param areaId
	 * @return
	 */
	public Area getAreaById(Long areaId);

	/**
	 * 删除区域
	 * 
	 * @param areaId
	 * @return
	 */
	public boolean delArea(Long areaId);

	/**
	 * 获取某个节点的子节点数目
	 * 
	 * @param parentId
	 * @return
	 */
	public int getChildrenCount(Long parentId);

	/**
	 * 添加区域
	 * 
	 * @param area
	 * @return
	 */
	public boolean addArea(Area area);

	/**
	 * 修改区域
	 * 
	 * @param area
	 * @return
	 */
	public boolean updateArea(Area area);

	/**
	 * 查询某区域下的子节点 (自关联)
	 * 
	 * @param areaId
	 * @return
	 */
	public Area getChildByPidResultMap(Long areaId);
	
	/**
	 * 查询某区域下的所有子节点 (自关联)
	 * 
	 * @return
	 */
	public AreaTree getAllChildByPidResultMap();

}