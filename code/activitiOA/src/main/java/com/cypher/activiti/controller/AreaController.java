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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cypher.activiti.dto.TreeDto;
import com.cypher.activiti.model.Area;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IAreaService;
import com.cypher.activiti.util.TreeUtils;

/**
 * 用于区域管理的Controller
 * 
 * @author Administrator
 *
 */
@Controller
public class AreaController {

	private static Logger logger = Logger.getLogger(AreaController.class);

	@Autowired
	private IAreaService areaService;

	// 跳转区域列表页
	@RequestMapping(value = "/sysmg/area/gotoAreaList")
	public String gotoAreaList(Model model) {
		List<Area> areaList = areaService.getAllAreaInfo();

		// 排序
		List<Area> sortAreaList = new ArrayList<Area>();
		TreeUtils.sortTreeList(sortAreaList, areaList, 0L);

		model.addAttribute("areaList", sortAreaList);

		return "/sysmg/area/areaList";
	}

	// 跳转区域编辑页
	@RequestMapping(value = "/sysmg/area/gotoAreaEdit")
	public String gotoAreaEdit(@ModelAttribute("editFlag") int editFlag, Long areaId, Long parentId, Model model) {

		// 修改
		if (editFlag == 2) {
			Area area = areaService.getAreaById(areaId);

			model.addAttribute("area", area);
		}
		// 增加
		if (editFlag == 1) {
			// 将当前节点的区域信息作为添加节点的父节点
			if (parentId != null) {
				Area area = new Area();
				Area parentArea = areaService.getAreaById(parentId);
				area.setParentId(parentArea.getId());
				area.setParentName(parentArea.getName());
				model.addAttribute("area", area);
			}
		}
		return "/sysmg/area/areaEdit";
	}

	// 修改和增加区域
	@RequestMapping(value = "/sysmg/area/saveArea", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveArea(@RequestBody Area area, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 获取创建人id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			if (area.getId() == null) { 
				// 添加
				areaService.addArea(area, user.getUserId());
				resultMap.put("result", "添加区域成功");
			} else {
				// 修改
				areaService.updateArea(area, user.getUserId());
				resultMap.put("result", "修改区域成功");
			}
		} catch (Exception e) {
			logger.error("操作区域信息失败", e);
			resultMap.put("result", "操作区域信息失败");
		}
		return resultMap;

	}

	// 删除区域
	@RequestMapping(value = "/sysmg/area/delArea/{areaId}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delArea(@PathVariable Long areaId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (areaId != null) {
			// 对于树形结构的数据,我们在删除的时候要注意 ,必须确保无子节点才可以直接删除,否则要给出提示
			if (areaService.getChildrenCount(areaId) > 0) {
				resultMap.put("result", "此区域下面还有子区域,请确定删除所有的子区域后再进行此操作");
				return resultMap;
			}

			try {
				boolean result = areaService.delArea(areaId);

				if (result) {
					resultMap.put("result", "删除区域成功");
				} else {
					resultMap.put("result", "删除区域失败");
					logger.warn("删除区域失败");
				}

			} catch (Exception e) {
				resultMap.put("result", "删除区域失败");
				logger.warn("删除区域失败", e);
			}
			
		}else {
			resultMap.put("result", "未输入删除的区域id");
		}

		return resultMap;
	}

	// 获取所有树形结构 区域节点信息
	@RequestMapping(value = "/sysmg/area/getParentAreaTreeData")
	public @ResponseBody List<TreeDto> getParentAreaTreeData(Long areaId) {
		List<TreeDto> treeList = new ArrayList<TreeDto>();

		// 获取所有区域信息
		List<Area> areaList = areaService.getAllAreaInfo();
		for (Area area : areaList) {
			TreeDto treeDto = new TreeDto();
			treeDto.setId(area.getId());
			treeDto.setParentId(area.getParentId());
			treeDto.setName(area.getName());
			treeList.add(treeDto);
		}

		// 删除所有子节点与自己，得到区域树形结构
		if (areaId != null) {// 代表进入的是修改页面
			// 找到所有子节点与自己
			List<Long> removeIdList = new ArrayList<>();
			removeIdList.add(areaId);

			TreeUtils.getAllChildrenIdList(treeList, removeIdList, areaId);
			Iterator<TreeDto> treeIterable = treeList.listIterator();
			while (treeIterable.hasNext()) {
				TreeDto treeDto = treeIterable.next();
				for (Long removeId : removeIdList) {
					if (removeId == treeDto.getId()) {
						treeIterable.remove();
					}
				}
			}
		}
		return treeList;

	}
}
