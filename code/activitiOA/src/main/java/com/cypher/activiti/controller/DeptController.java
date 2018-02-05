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
import com.cypher.activiti.model.Dept;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IDeptService;
import com.cypher.activiti.util.TreeUtils;

/**
 * 用于部门管理的Controller
 * 
 * @author Administrator
 *
 */
@Controller
public class DeptController {

	private static Logger logger = Logger.getLogger(DeptController.class);

	@Autowired
	private IDeptService deptService;

	// 跳转部门列表页
	@RequestMapping(value = "/sysmg/dept/gotoDeptList")
	public String gotoDeptList(Model model) {
		List<Dept> deptList = deptService.getAllDeptInfo();

		model.addAttribute("deptList", deptList);

		return "/sysmg/dept/deptList";
	}

	// 跳转部门编辑页
	@RequestMapping(value = "/sysmg/dept/gotoDeptEdit")
	public String gotoDeptEdit(@ModelAttribute("editFlag") Integer editFlag, Long deptId, Long parentId, Model model) {
		// 添加
		if (editFlag == 1) {
			// 将当前节点的部门信息作为添加节点的父节点
			if (parentId != null) {
				Dept parentDept = deptService.getDeptInfoById(parentId);
				Dept dept = new Dept();
				dept.setParentId(parentDept.getId());
				dept.setParentName(parentDept.getName());

				model.addAttribute("dept", dept);
			}
		}
		// 修改
		if (editFlag == 2) {
			Dept dept = deptService.getDeptInfoById(deptId);

			model.addAttribute("dept", dept);
		}

		return "/sysmg/dept/deptEdit";
	}

	// 修改 和添加
	@RequestMapping(value = "/sysmg/dept/saveDept", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveDept(@RequestBody Dept dept, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 获取用户id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			// 修改
			if (dept != null && dept.getId() != null) {
				deptService.updateDept(dept, user.getUserId());
				resultMap.put("result", "修改部门信息成功");
			} else {// 添加
				deptService.addDept(dept, user.getUserId());
				resultMap.put("result", "添加部门信息成功");
			}
		} catch (Exception e) {
			logger.error("操作部门信息失败", e);
			resultMap.put("result", "操作部门信息失败");
		}

		return resultMap;
	}

	// 删除部门
	@RequestMapping(value = "/sysmg/dept/delDept/{deptId}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delDept(@PathVariable Long deptId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if (deptId != null) {
			// 对于树形结构的数据,我们在删除的时候要注意 ,必须确保无子节点才可以直接删除,否则要给出提示
			int childrenCount = deptService.getChildrenCount(deptId);
			if (childrenCount > 0) {
				resultMap.put("result", "此部门下面还有子部门,请确定删除所有的子部门后再进行此操作");
				return resultMap;
			}

			try {
				if (deptService.delDept(deptId)) {
					resultMap.put("result", "删除部门操作成功");
				} else {
					resultMap.put("result", "删除部门操作失败");
				}
			} catch (Exception e) {
				resultMap.put("result", "删除部门操作失败");
				logger.warn("删除部门操作失败", e);
			}
		} else {
			resultMap.put("result", "请输入部门id");
		}

		return resultMap;
	}

	// 获取所有树形结构 部门节点信息
	@RequestMapping(value = "/sysmg/dept/getParentDeptTreeData")
	public @ResponseBody List<TreeDto> getParentDeptTreeData(Long deptId) {
		List<TreeDto> treeList = new ArrayList<TreeDto>();

		// 获取所有部门
		List<Dept> deptList = deptService.getAllDeptInfo();
		for (Dept dept : deptList) {
			TreeDto treeDto = new TreeDto();
			treeDto.setId(dept.getId());
			treeDto.setName(dept.getName());
			treeDto.setParentId(dept.getParentId());
			treeList.add(treeDto);
		}

		if (deptId != null) {
			// 获取所有子节点和自己
			List<Long> removeIdList = new ArrayList<Long>();
			removeIdList.add(deptId);

			TreeUtils.getAllChildrenIdList(treeList, removeIdList, deptId);

			// 去除子节点和自己
			Iterator<TreeDto> treeIterator = treeList.listIterator();
			while (treeIterator.hasNext()) {
				TreeDto treeDto = treeIterator.next();
				for (Long removeId : removeIdList) {
					if (removeId == treeDto.getId()) {
						treeIterator.remove();
					}
				}
			}
		}

		return treeList;
	}

	// 获取所有部门树
	@RequestMapping("/sysmg/dept/getAllDeptList")
	public @ResponseBody List<TreeDto> getAllDeptList() {
		List<TreeDto> treeList = new ArrayList<TreeDto>();
		List<Dept> deptList = this.deptService.getAllDeptInfo();
		for (Dept dept : deptList) {
			TreeDto tree = new TreeDto();
			tree.setId(dept.getId());
			tree.setName(dept.getName());
			tree.setParentId(dept.getParentId());
			treeList.add(tree);
		}
		return treeList;
	}

}
