package com.cypher.activiti.controller.activiti;

import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cypher.activiti.service.IWorkFlowService;

@Controller
public class DeployController {

	private static Logger logger = Logger.getLogger(DeployController.class);

	@Autowired
	private IWorkFlowService workFlowService;

	@RequestMapping(value = "/activiti/processDeploy/gotoProcessDeploy")
	public String gotoProcessDeploy(Model model) {
		// 获取部署列表信息
		List<Deployment> deploymentList = workFlowService.getAllDeploymentList();

		model.addAttribute("deploymentList", deploymentList);
		
		return "activiti/processDeploy/processDeployList";
	}

	@RequestMapping(value = "/activiti/processDeploy/gotoProcessDeployAdd")
	public String gotoProcessDeployAdd() {
		return "activiti/processDeploy/gotoProcessDeployAdd";
	}

}
