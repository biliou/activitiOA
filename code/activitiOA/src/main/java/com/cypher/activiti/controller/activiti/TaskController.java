package com.cypher.activiti.controller.activiti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cypher.activiti.model.User;
import com.cypher.activiti.service.IWorkFlowService;
import com.cypher.activiti.util.StringUtil;

/**
 * 用于任务处理功能的Controller
 * 
 * @author Administrator
 *
 */
@Controller
public class TaskController {

	private static Logger logger = Logger.getLogger(TaskController.class);

	@Autowired
	private IWorkFlowService workFlowService;

	// 跳转任务处理列表页
	@RequestMapping(value = "/activiti/processTask/gotoProcessTaskList")
	public String gotoProcessTaskList(HttpServletRequest request, Model model) {

		// 获取当前用户id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		// 获取当前用户个人任务处理列表
		List<Task> taskList = new ArrayList<Task>();
		taskList = workFlowService.getTaskListByAssignee(user.getUserId().toString());
		if (taskList != null && taskList.size() > 0) {
			for (Task task : taskList) {
				task.setAssignee(user.getUserName());
			}
		}
		model.addAttribute("taskList", taskList);

		return "/activiti/processTask/processTaskList";
	}

	// 跳转处理任务详细页（获取相关参数后，跳转至对应的流程下的方法中处理）
	@RequestMapping(value = "/activiti/processTask/gotoProcessTaskDetail")
	public String gotoProcessTaskDetail(String taskId) {
		if(StringUtil.isNotEmpty(taskId)) {
			String taskDetailUrl = workFlowService.getTaskFormKeyByTaskId(taskId);
			// 因为每个任务的明细页面都不一样，可以通过流程设计的formKey重定向到每类任务的明细页面
			// 通过formService可以获取每个任务对应的url
			return "redirect:" + taskDetailUrl + "?taskId=" + taskId;
		}else {
			return "/activiti/processTask/processTaskDetailEmpty";
		}
	}

	// 任务处理
	@RequestMapping(value = "/activiti/processTask/completeTask", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> completeTask(String taskId, String outcome, String commentMsg,
			HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 获取当前用户id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			workFlowService.completeTask(taskId, outcome, commentMsg, user.getUserName());
			resultMap.put("result", "任务处理成功");
		} catch (Exception e) {
			logger.warn("任务处理失败", e);
			resultMap.put("result", "任务处理失败");
		}

		return resultMap;
	}

}
