package com.cypher.activiti.service;

import java.util.List;

import com.cypher.activiti.model.Dict;
import com.github.pagehelper.PageInfo;

/**
 * 字典管理的业务层接口
 * 
 * @author Administrator
 *
 */
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
	 *            字典id
	 * @return
	 */
	public Dict getDictInfoById(long dictId);

	/**
	 * 删除字典
	 * 
	 * @param dictId
	 *            字典id
	 * @return
	 */
	public boolean delDict(long dictId);

	/**
	 * 修改字典
	 * 
	 * @param dict
	 *            字典对象（修改信息）
	 * @param userId
	 *            修改者用户id
	 * @return
	 */
	public boolean updateDict(Dict dict, Long userId);

	/**
	 * 增加字典
	 * 
	 * @param dict
	 *            字典对象（修改信息）
	 * @param userId
	 *            修改者用户id
	 * @return
	 */
	public boolean addDict(Dict dict, Long userId);
}
