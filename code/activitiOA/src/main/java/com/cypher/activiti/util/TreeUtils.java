package com.cypher.activiti.util;

import java.util.List;
import java.util.Map;

import com.cypher.activiti.dto.TreeDto;

/**
 * 关于树形结构的工具类
 * 
 * @author Administrator
 *
 */
public class TreeUtils {

	/*
	 * public static void sortMenuList(List<Menu> sortMenuList, List<Menu> menuList,
	 * Long parentId){
	 * 
	 * for(Menu menu :menuList){
	 * if(menu.getParentId()!=null&&menu.getParentId().equals(parentId)){
	 * sortMenuList.add(menu); for(Menu childMenu: menuList){
	 * if(childMenu.getParentId()!=null&&childMenu.getParentId().equals(parentId)){
	 * //递归 sortMenuList(sortMenuList,menuList,menu.getId()); break; } }
	 * 
	 * } } }
	 */

	/**
	 * 对于菜单列表进行一个父子结构的排序
	 * 
	 * @param sortMenuList
	 * @param menuList
	 * @param parentId
	 * 
	 *            1:轮询待排序列表,找到当前父节点的下一级某个节点 2:将当前节点放入指定的列表 3:轮询待排序列表,找到当前菜单 4:重复
	 *            1,2,3 5:break;
	 */
	public static <T> void sortTreeList(List<T> sortTreeList, List<T> treeList, Long parentId) {
		/**
		 * 1:轮询待排序列表,找到当前父节点的下一级某个节点 2:将当前节点放入指定的列表 3:轮询待排序列表,找到当前菜单 4:重复 1,2,3 5:break;
		 */
		/*
		 * for(int i = 0; i<treeList.size();i++){ TreeDto m = (TreeDto) treeList.get(i);
		 * //找到第一级 if(m.getParentId()!=null&&m.getParentId().equals(parentId)){
		 * sortTreeList.add((T)m); for(T child: treeList){
		 * if(((TreeDto)child).getParentId()!=null&&((TreeDto)child).getParentId().
		 * equals(parentId)){ //递归 sortTreeList(sortTreeList,treeList,m.getId()); break;
		 * } } }
		 * 
		 * }
		 */
		for (int i = 0; i < treeList.size(); i++) {
			TreeDto m = (TreeDto) treeList.get(i);
			// 找到第一级
			if (m.getParentId() != null && m.getParentId().equals(parentId)) {
				sortTreeList.add((T) m);
				for (T child : treeList) {
					if (((TreeDto) child).getParentId() != null && ((TreeDto) child).getParentId().equals(parentId)) {
						// 递归
						sortTreeList(sortTreeList, treeList, m.getId());
						break;
					}
				}
			}

		}
	}

	/**
	 * 获取指定父节点下面的所有子节点(儿子,孙子)
	 * @param treeList
	 * @param removeIdList
	 * @param parentId
	 * @return
	 */
	public static List<Long> getAllChildrenIdList(List<TreeDto> treeList, List<Long> removeIdList, long parentId) {

		for (TreeDto treeDto : treeList) {
			if (parentId == treeDto.getParentId()) {
				removeIdList.add(treeDto.getId());
				getAllChildrenIdList(treeList, removeIdList, treeDto.getId());
			}
		}

		return removeIdList;
	}

}
