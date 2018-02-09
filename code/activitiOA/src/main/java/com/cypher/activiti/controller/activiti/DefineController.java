package com.cypher.activiti.controller.activiti;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cypher.activiti.service.IWorkFlowService;

@Controller
public class DefineController {

	private static Logger logger = Logger.getLogger(DefineController.class);

	@Autowired
	private IWorkFlowService workFlowService;

	// 跳转流程定义列表页
	@RequestMapping(value = "/activiti/processDefinition/gotoProcessDefinitionList")
	public String gotoProcessDefinetionList(Model model) {

		// 获取所有流程定义列表信息
		List<ProcessDefinition> processDefinitionList = workFlowService.getAllDefinitionList();

		model.addAttribute("processDefinitionList", processDefinitionList);

		return "/activiti/processDefinition/processDefinitionList";
	}

	@RequestMapping(value = "/activiti/processDefinition/gotoProcessDefinitionImage")
	public String gotoProcessDefinitionImage(@ModelAttribute("deploymentId") String deploymentId,
			@ModelAttribute("imageName") String imageName) {

		return "/activiti/processDefinition/processDefinitionImage";
	}

	@RequestMapping(value = "/activiti/processDefinition/getProcessDefinitionImage")
	public void getProcessDefinitionImage(String deploymentId, String imageName, HttpServletResponse response) {

		// 获取图片流
		InputStream imageStream = workFlowService.getDefinitionImage(deploymentId, imageName);
		response.setContentType("img/png");// 设置响应头content-type的内容
		response.setCharacterEncoding("UTF-8");

		OutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();

			int len = 0;
			byte[] buffer = new byte[1024];

			while ((len = imageStream.read(buffer, 0, 1024)) != -1) {
				outputStream.write(buffer, 0, len);
			}

			outputStream.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
