package com.cypher.activiti.core.activiti.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cypher.activiti.dao.RoleMapper;

/**
 * activiti 监听器<br/>
 * 传入部门主管
 * 
 * @author Administrator
 *
 */
public class TeamLeaderTaskListener implements TaskListener {

	@Autowired
	private RoleMapper roleMapper;

	private static final long serialVersionUID = -5939344239357822841L;

	public void notify(DelegateTask delegateTask) {
		// 注意TaskListener的实现类里想注入spring的bean
		List<Long> userIdList = roleMapper.getUserIdListByRoleName("部门主管");
		if (userIdList != null && userIdList.size() > 0) {
			delegateTask.setAssignee(userIdList.get(0).toString());
		} else {
			// TODO ：异常？
		}

	}

}
