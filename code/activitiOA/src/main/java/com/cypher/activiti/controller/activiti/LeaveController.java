package com.cypher.activiti.controller.activiti;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.apache.commons.lang3.StringUtils;
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

import com.cypher.activiti.dto.LeaveBean;
import com.cypher.activiti.model.User;
import com.cypher.activiti.service.ILeaveProcessService;
import com.cypher.activiti.service.IWorkFlowService;

/**
 * 用于请假申请功能的Controller
 * 
 * @author Administrator
 *
 */
@Controller
public class LeaveController {

	private static Logger logger = Logger.getLogger(LeaveController.class);

	@Autowired
	private ILeaveProcessService leaveProcessService;
	@Autowired
	private IWorkFlowService workFlowService;

	// 跳转请假流程信息列表页
	@RequestMapping(value = "/activiti/leaveProcess/gotoLeaveProcessList")
	public String gotoLeavelProcessList() {
		return "/activiti/leaveProcess/leaveProcessList";
	}

	// 获取所有请假流程信息列表
	@RequestMapping(value = "/activiti/leaveProcess/getLeaveProcessList", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> getLeaveProcessList(HttpServletRequest request) {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 获取当前用户id
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		List<LeaveBean> leaveBeanList = leaveProcessService.getLeaveProcessList(user.getUserId());

		resultMap.put("leaveBeanList", leaveBeanList);

		return resultMap;
	}

	// 跳转请假单编辑页
	@RequestMapping(value = "/activiti/leaveProcess/gotoLeaveProcessEdit")
	public String gotoLeaveProcessEdit(@ModelAttribute("editFlag") Integer editFlag, Long leaveId, Model model) {
		// 请假单录入,无处理信息

		// 请假单修改
		if (editFlag == 2) {
			// 获取请假单信息
			LeaveBean leaveBean = leaveProcessService.getLeaveProcessByLeaveId(leaveId);
			leaveBean.setLeaveDate(new Date());
			model.addAttribute("leaveBean", leaveBean);
		}

		return "/activiti/leaveProcess/leaveProcessEdit";
	}

	// 修改请假申请
	@RequestMapping(value = "/activiti/leaveProcess/saveLeaveProcess", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> saveLeaveProcess(@RequestBody LeaveBean leaveBean,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 获取当前用户id（请假用户）
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {

			if (leaveBean != null && leaveBean.getLeaveId() != null) {
				// 修改
				leaveProcessService.updateLeaveProcess(leaveBean);
				resultMap.put("result", "请假申请修改成功");

			} else {
				// 添加
				leaveProcessService.addLeaveProcess(leaveBean, user.getUserId());
				resultMap.put("result", "请假申请添加成功");
			}

		} catch (Exception e) {
			logger.error("请假申请操作失败", e);
			resultMap.put("result", "请假申请操作失败");
		}

		return resultMap;
	}

	// 删除请假申请
	@RequestMapping(value = "/activiti/leaveProcess/delLeaveProcess/{leaveId}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delLeaveProcess(@PathVariable Long leaveId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (leaveId == null) {
			resultMap.put("result", "请输入删除请假申请的id");
		} else {
			try {

				leaveProcessService.delLeaveProcess(leaveId);
				resultMap.put("result", "删除请假申请操作成功");
			} catch (Exception e) {
				logger.error("删除请假申请操作失败", e);
				resultMap.put("result", "删除请假申请操作失败");
			}
		}
		return resultMap;
	}

	// 请假确认申请
	@RequestMapping(value = "/activiti/leaveProcess/doLeaveProcess", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> doLeaveProcess(Long leaveId, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 获取当前用户id（请假用户）
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
			leaveProcessService.doLeaveProcess(leaveId, user.getUserId());

			resultMap.put("result", "申请请假成功,请转往任务处理功能查看请假单");

		} catch (Exception e) {
			logger.error("申请请假失败", e);
			resultMap.put("result", "申请请假失败");
		}

		return resultMap;
	}

	// 查看流程图状态
	@RequestMapping(value = "/activiti/leaveProcess/gotoLeaveProcessImage")
	public String gotoLeaveProcessImage(String processInstanceId, Model model) {

		if (StringUtils.isNotEmpty(processInstanceId)) {

			// 获取图片信息
			ProcessDefinition processDefinition = workFlowService.getDeploymentId(processInstanceId);

			model.addAttribute("deploymentId", processDefinition.getDeploymentId());
			model.addAttribute("imageName", processDefinition.getKey() + ".png");

			// 获取流程任务执行坐标
			ActivityImpl activityImpl = workFlowService.getActivitiCoordinate(processInstanceId);
			model.addAttribute("activityImpl", activityImpl);

		}

		return "/activiti/leaveProcess/leaveProcessImage";
	}

	// 跳转请假任务处理详情页
	@RequestMapping(value = "/activiti/leaveProcess/gotoProcessTaskDetail")
	public String gotoLeaveTaskDetail(@ModelAttribute("taskId") String taskId, Model model) {

		// 1.使用任务id 查询相关的某个流程定义相关的表单信息
		LeaveBean leaveBean = leaveProcessService.getLeaveBeanByTaskId(taskId);
		model.addAttribute("leaveBean", leaveBean);

		// 2.根据任务id查询当前任务完成后的连线名称，返回给页面动态生成处理按钮
		List<PvmTransition> pvmTransitionList = workFlowService.getOutcomeListByTaskId(taskId);

		// 构造页面按钮列表
		List<String> buttonNameList = new ArrayList<String>();
		if (pvmTransitionList != null && pvmTransitionList.size() > 0) {
			for (PvmTransition pvm : pvmTransitionList) {
				String outcomeName = (String) pvm.getProperty("name");
				if (StringUtils.isNotBlank(outcomeName)) {
					buttonNameList.add(outcomeName);
				} else {
					// 连线无名称时，默认显示 “确认提交”
					buttonNameList.add("确认提交");
				}
			}
		}
		model.addAttribute("buttonNameList", buttonNameList);

		// 3.查询历史审批信息
		List<Comment> commentList = this.workFlowService.getCommentListByTaskId(taskId);
		model.addAttribute("commentList", commentList);
		
		return "/activiti/leaveProcess/leaveProcssTaskDetail";
	}
}
