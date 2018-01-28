package com.cypher.activiti.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cypher.activiti.dto.TreeDto;
import com.cypher.activiti.model.Menu;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IMenuService;
import com.cypher.activiti.util.TreeUtils;

@Controller
public class MenuController {

	private static Logger logger = Logger.getLogger(MenuController.class);

	@Autowired
	private IMenuService menuService;

	// 跳转菜单管理
	@RequestMapping(value = "/sysmg/menu/gotoMenuList")
	public String gotoMenuEdit(Model model) {

		// 请求MenuService 层，获取所有菜单
		List<Menu> menuList = menuService.getAllMenuInfo();

		List<Menu> sortMenuList = new ArrayList<Menu>();
		// 因为前台组件treeTable正常显示树形结构的数据,就必须让我们的列表按照树形的结构顺序摆放
		TreeUtils.sortTreeList(sortMenuList, menuList, 0l);

		model.addAttribute("menuList", sortMenuList);

		return "/sysmg/menu/menuList";
	}

	// 跳转菜单编辑
	@RequestMapping(value = "/sysmg/menu/gotoMenuEdit")
	public String gotoMenuEdit(@ModelAttribute("editFlag") Integer editFlag, Long parentId, Long menuId, Model model) {
		// 修改页面
		if (editFlag == 2) {
			// 请求MenuService层，获取当前菜单
			Menu menu = menuService.getMenuById(menuId);

			model.addAttribute("menu", menu);
		}
		// 增加页面
		if (editFlag == 1) {
			if (parentId != null) {
				Menu parentMenu = menuService.getMenuById(parentId);
				Menu menu = new Menu();
				menu.setParentId(parentMenu.getId());
				menu.setParentName(parentMenu.getName());
				model.addAttribute("menu",menu);	
			}
		}
		return "/sysmg/menu/menuEdit";
	}

	// 修改和增加菜单
	@RequestMapping(value = "/sysmg/menu/saveMenu", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveMenu(@RequestBody Menu menu, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 获取用户id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			if (menu != null && menu.getId() != null) {
				// 修改
				menuService.updateMenu(menu, user.getUserName());
				resultMap.put("result", "更新菜单信息成功");
			} else {
				// 增加
				menuService.addMenu(menu, user.getUserName());
				resultMap.put("result", "添加菜单信息成功");
			}
		} catch (Exception e) {
			logger.error("操作菜单信息失败", e);
			resultMap.put("result", "操作菜单信息失败");
		}

		return resultMap;

	}

	// 删除菜单
	@RequestMapping(value = "/sysmg/menu/delMenu/{menuId}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delMenu(@PathVariable Long menuId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 对于树形结构的数据,我们在删除的时候要注意 ,必须确保无子节点才可以直接删除,否则要给出提示
		if (menuService.getChildCount(menuId).intValue() > 0) {
			resultMap.put("result", "此菜单下面还有子菜单,请确定删除所有的子菜单后再进行此操作");
			return resultMap;
		}

		// 请求MenuService层，删除菜单
		try {
			if (menuService.delMenu(menuId))
				resultMap.put("result", "删除菜单信息成功");
		} catch (Exception e) {
			resultMap.put("result", "删除菜单信息失败");
			logger.error("删除菜单信息失败", e);
		}

		return resultMap;
	}

	// 获取所有树形结构 菜单节点信息
	@RequestMapping(value = "/sysmg/menu/getParentMenuTreeData")
	public @ResponseBody List<TreeDto> getParentMenuTreeData(Long menuId) {
		List<TreeDto> treeList = new ArrayList<TreeDto>();

		List<Menu> menuList = menuService.getAllMenuInfo();
		for (Menu menu : menuList) {
			TreeDto treeDto = new TreeDto();
			treeDto.setId(menu.getId());
			treeDto.setName(menu.getName());
			treeDto.setParentId(menu.getParentId());
			treeList.add(treeDto);
		}

		// 找到自己与自己的子节点
		List<Long> removeIdList = new ArrayList<Long>();
		if (menuId != null && menuId >= 0) {
			removeIdList.add(menuId);

			TreeUtils.getAllChildrenIdList(treeList, removeIdList, menuId);
		}

		// 去掉自己与自己的子节点
		Iterator<TreeDto> treeIterator = treeList.iterator();
		while (treeIterator.hasNext()) {
			TreeDto treeDto = treeIterator.next();
			for (Long removeId : removeIdList) {
				if (removeId == treeDto.getId()) {
					treeIterator.remove();
				}
			}
		}

		return treeList;
	}

}
