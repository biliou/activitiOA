package com.cypher.activiti.controller.activiti;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.repository.Deployment;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cypher.activiti.service.IWorkFlowService;

@Controller
public class DeployController {

	private static Logger logger = Logger.getLogger(DeployController.class);

	@Autowired
	private IWorkFlowService workFlowService;

	// 跳转流程列表页
	@RequestMapping(value = "/activiti/processDeploy/gotoProcessDeployList")
	public String gotoProcessDeploy(Model model) {
		// 获取部署列表信息
		List<Deployment> deploymentList = workFlowService.getAllDeploymentList();

		model.addAttribute("deploymentList", deploymentList);

		return "activiti/processDeploy/processDeployList";
	}

	// 跳转流程添加页
	@RequestMapping(value = "/activiti/processDeploy/gotoProcessDeployAdd")
	public String gotoProcessDeployAdd() {
		return "activiti/processDeploy/processDeployAdd";
	}

	/**
	 * 流程部署 <br/>
	 * 1.判断上传文件是否为空 <br/>
	 * 2.判断文件后缀，给出提示 <br/>
	 * 3.调用接口完成流程的部署<br/>
	 * 4.重定向至流程列表页<br/>
	 */
	@RequestMapping(value = "/activiti/processDeploy/addProcessDeploy")
	public String addProcessDeploy(HttpServletRequest request, MultipartFile file, Model model) {
		System.out.println(request.getParameter("name"));

		// 获取部署文件名
		String deploymentName = request.getParameter("name");

		if (file != null) {

			String fileName = file.getOriginalFilename();
			if (fileName.indexOf("zip") > 0) {
				// 完成流程部署
				InputStream inputStream = null;
				try {
					inputStream = file.getInputStream();
					Deployment deployment = workFlowService.addDeployment(inputStream, deploymentName);

				} catch (IOException e) {
					logger.error("流程部署添加流程操作失败", e);
					e.printStackTrace();
				}

				// 重定向至流程列表页
				return "redirect:/activiti/processDeploy/gotoProcessDeployList";

			} else {
				model.addAttribute("processAddErrorMsg", "流程部署压缩文件只能是ZIP格式");
				return "activiti/processDeploy/processDeployAdd";
			}

		} else {
			model.addAttribute("processAddErrorMsg", "请检查上传文件内容");
			return "activiti/processDeploy/processDeployAdd";
		}

	}

	// 删除流程部署
	@RequestMapping(value = "/activiti/processDeploy/delProcessDeploy/{deploymentId}", method = RequestMethod.DELETE)
	public @ResponseBody Map<String, Object> delProcessDeploy(@PathVariable String deploymentId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			workFlowService.delDeployment(deploymentId, true);
			resultMap.put("result", "删除流程部署信息成功");
		} catch (Exception e) {
			logger.error("删除流程部署信息失败", e);
			resultMap.put("result", "删除流程部署信息失败");
		}

		return resultMap;
	}

}
