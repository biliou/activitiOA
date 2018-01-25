package com.cypher.activiti.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.cypher.activiti.model.Dict;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IDictService;
import com.cypher.activiti.util.PageUtils;
import com.cypher.activiti.util.StringUtil;
import com.github.pagehelper.PageInfo;

@Controller
public class DictController {

	private static Logger logger = Logger.getLogger(DictController.class);

	@Autowired
	private IDictService dictService;

	// 进入字典列表页
	@RequestMapping(value = "/sysmg/dict/gotoDictList")
	public String gotoDictList(Model model) {

		// 请求DictService 层，获取所有字典类型
		List<String> dictTypeList = dictService.getAllDictTypeList();

		model.addAttribute("dictTypeList", dictTypeList);

		return "sysmg/dict/dictList";
	}

	// 查询字典
	@RequestMapping(value = "/sysmg/dict/getDictListPage", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getDictListPage(String type,
			@RequestParam(value = "description") String desp, int pageNo, int pageSize) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 过滤type 和 desp
		if (StringUtil.isEmpty(type))
			type = null;
		if (StringUtil.isEmpty(desp))
			desp = null;

		// 请求DictService 层，查询字典
		PageInfo<Dict> pageInfo = dictService.getDictListPage(type, desp, pageNo, pageSize);
		List<Dict> dictList = pageInfo.getList();

		resultMap.put("dictList", dictList);

		// 获取返回的分页条
		String pageStr = PageUtils.pageStr(pageInfo, "dictMgr.getDictListPage");
		resultMap.put("pageStr", pageStr);

		return resultMap;
	}

	// 进入字典编辑页
	@RequestMapping(value = "/sysmg/dict/gotoDictEdit")
	public String gotoDictEdit(@ModelAttribute("editFlag") int editFlag, Long dictId, Model model) {
		// 如果editFlag = 2 则是进入修改页面，我们需要查询待修改记录的明细信息
		// 如果editFlag = 1 是增加页面
		if (editFlag == 2) {
			// 请求DictService 层，查询一个字典信息
			Dict dict = dictService.getDictInfoById(dictId);

			model.addAttribute("dict", dict);
		}
		return "sysmg/dict/dictEdit";
	}

	// 增加字典 和 修改字典
	@RequestMapping(value = "/sysmg/dict/saveDict", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveDict(@RequestBody Dict dict,HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		//获取修改用户id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		Long userId = user.getUserId();

		try {
			if (dict != null && dict.getId() != null) {
				// 修改字典
				dictService.updateDict(dict,userId);
				resultMap.put("result", "修改字典信息成功");
				
			} else {
				// 增加字典
				dictService.addDict(dict,userId);
				resultMap.put("result", "修改字典信息成功");
			}
		} catch (Exception e) {
			resultMap.put("result", "修改字典信息失败");
			logger.error("修改字典信息失败", e);
		}

		return resultMap;

	}

	// 删除字典
	@RequestMapping(value = "/sysmg/dict/delDict/{dictId}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delDict(@PathVariable Long dictId, Model model) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 请求DictService 层，删除一个字典信息
		try {
			if (dictService.delDict(dictId))
				resultMap.put("result", "删除字典信息成功");
		} catch (Exception e) {
			resultMap.put("result", "删除字典信息失败");
			logger.error("删除字典信息失败", e);
		}
		return resultMap;
	}

}
