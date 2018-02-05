package com.cypher.activiti.service;

import java.util.List;

import com.cypher.activiti.model.Menu;

/**
 * 菜单管理的业务层接口
 * 
 * @author Administrator
 *
 */
public interface IMenuService {
	/**
	 * 查询所有菜单列表
	 * 
	 * @return
	 */
	public List<Menu> getAllMenuInfo();

	/**
	 * 通过菜单id查询菜单信息
	 * 
	 * @param menuId
	 * @return
	 */
	public Menu getMenuById(long menuId);

	/**
	 * 删除菜单
	 * 
	 * @param menuId
	 * @return
	 */
	public boolean delMenu(long menuId);

	/**
	 * 更新菜单
	 * 
	 * @param menu
	 * @param userId
	 *            修改者用户id
	 * @return
	 */
	public boolean updateMenu(Menu menu, Long userId);

	/**
	 * 添加菜单
	 * 
	 * @param menu
	 * @param userId
	 *            修改者用户id
	 * @return
	 */
	public boolean addMenu(Menu menu, Long userId);

	/**
	 * 获取某个节点的子节点数目,用于删除的特殊判断
	 * 
	 * @param menuId
	 * @return
	 */
	public Integer getChildCount(Long menuId);

	/**
	 * 查询用户权限控制内的所有菜单
	 * 
	 * @param menuId
	 * @return
	 */
	public List<Menu> getMenuListByUserId(Long menuId);
}
