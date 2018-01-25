package com.cypher.activiti.service;

import java.util.List;

import com.cypher.activiti.model.Dict;
import com.github.pagehelper.PageInfo;

public interface IDictService {
	/**
	 * 获取所有字典类型
	 * 
	 * @return
	 */
	public List<String> getAllDictTypeList();

	/**
	 * 查询当前页的字典信息
	 * 
	 * @param type
	 *            字典类型
	 * @param desp
	 *            字典描述
	 * @param pageNo
	 *            字典页数
	 * @param pageSize
	 *            一页显示多少条
	 * @return
	 */
	public PageInfo<Dict> getDictListPage(String type, String desp, int pageNo, int pageSize);

	/**
	 * 查询一个字典信息
	 * 
	 * @param dictId
	 * @return
	 */
	public Dict getDictInfoById(long dictId);

	/**
	 * 删除字典
	 * 
	 * @param dictId
	 * @return
	 */
	public boolean delDict(long dictId);
	
	/**
	 * 修改字典
	 * 
	 * @param dict
	 * @return
	 */
	public boolean updateDict(Dict dict,Long userId);
	
	/**
	 * 增加字典
	 * @param dict
	 * @return
	 */
	public boolean addDict(Dict dict,Long userId);
}
