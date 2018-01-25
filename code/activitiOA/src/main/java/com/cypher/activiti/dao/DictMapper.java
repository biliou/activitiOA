package com.cypher.activiti.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cypher.activiti.model.Dict;

public interface DictMapper {

	/**
	 * 查询所有字典信息</br>
	 * 使用原生的SQL进行分页</br>
	 * 
	 * @param type
	 * @param desp
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	List<Dict> selectAllDictInfoisPaged(@Param("type") String type, @Param("desp") String desp,
			@Param("pageNo") int pageNo, @Param("pageSize") int pageSize);

	/**
	 * 查询所有字典信息</br>
	 * 未分页</br>
	 * 
	 * @param type
	 * @param desp
	 * @return
	 */
	List<Dict> selectAllDictInfo(@Param("type") String type, @Param("desp") String desp);

	/**
	 * 查询所有字典类型
	 * 
	 * @return
	 */
	List<String> selectAllDictType();

	/**
	 * 通过id查询字典
	 * 
	 * @return
	 */
	Dict selectDictInfoById(long dictId);

	/**
	 * 删除一个字典信息
	 * 
	 * @param dictId
	 * @return
	 */
	boolean delDict(long dictId);

	/**
	 * 修改字典
	 * 
	 * @param dict
	 * @return
	 */
	boolean updateDict(Dict dict);

	/**
	 * 增加字典
	 * 
	 * @param dict
	 * @return
	 */
	boolean addDict(Dict dict);
}