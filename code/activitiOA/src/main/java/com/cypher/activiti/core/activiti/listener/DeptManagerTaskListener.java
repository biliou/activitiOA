package com.cypher.activiti.core.activiti.listener;

import java.util.List;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;

import com.cypher.activiti.dao.RoleMapper;

/**
 * activiti 监听器<br/>
 * 传入部门经理
 * 
 * @author Administrator
 *
 */
public class DeptManagerTaskListener implements TaskListener {

	@Autowired
	private RoleMapper roleMapper;

	private static final long serialVersionUID = 4170294677763089768L;

	@Override
	public void notify(DelegateTask delegateTask) {
		List<Long> userIdList = roleMapper.getUserIdListByRoleName("部门经理");
		if (userIdList != null && userIdList.size() > 0) {
			delegateTask.setAssignee(userIdList.get(0).toString());
		} else {
			// TODO ：异常？
		}
	}

}
