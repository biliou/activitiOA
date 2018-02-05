package com.cypher.activiti.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cypher.activiti.model.Area;

/**
 * 区域管理的业务层接口
 * 
 * @author Administrator
 *
 */
@Service
public interface IAreaService {

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
	 * @param userId
	 *            修改者用户id
	 * @return
	 */
	public boolean addArea(Area area, Long userId);

	/**
	 * 修改区域
	 * 
	 * @param area
	 * @param userId
	 *            修改者用户id
	 * @return
	 */
	public boolean updateArea(Area area, Long userId);
}
