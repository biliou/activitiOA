package com.cypher.activiti.controller.activiti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DeployController {
	
	@RequestMapping(value="/activiti/processDeploy/gotoProcessDeploy")
	public String gotoProcessDeploy() {
		return "activiti/processDeploy/processDeployList";
	}
	
}
