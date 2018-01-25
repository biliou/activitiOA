package com.cypher.activiti.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cypher.activiti.dao.DictMapper;
import com.cypher.activiti.model.Dict;
import com.cypher.activiti.service.IDictService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class DictService implements IDictService {

	@Autowired
	private DictMapper dictMapper;

	@Override
	public PageInfo<Dict> getDictListPage(String type, String desp, int pageNo, int pageSize) {

		// 使用分页方法
		// return dictMapper.selectAllDictInfoisPaged(type, desp, pageNo, pageSize);

		// 使用pageHelper插件分页
		PageHelper.startPage(pageNo, pageSize);
		List<Dict> dictList = dictMapper.selectAllDictInfo(type, desp);
		PageInfo<Dict> pageInfo = new PageInfo<Dict>(dictList);

		return pageInfo;
	}

	@Override
	public List<String> getAllDictTypeList() {
		return dictMapper.selectAllDictType();
	}

	@Override
	public Dict getDictInfoById(long dictId) {
		return dictMapper.selectDictInfoById(dictId);
	}

	@Override
	public boolean delDict(long dictId) {
		return dictMapper.delDict(dictId);
	}

	@Override
	public boolean addDict(Dict dict,Long userId) {
		dict.setUpdateDate(new Date());
		dict.setUpdateBy(userId.toString());
		return dictMapper.addDict(dict);
	}

	@Override
	public boolean updateDict(Dict dict,Long userId) {
		dict.setUpdateDate(new Date());
		dict.setUpdateBy(userId.toString());
		return dictMapper.updateDict(dict);
	}

}
