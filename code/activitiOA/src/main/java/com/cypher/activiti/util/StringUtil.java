package com.cypher.activiti.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 字符串操作工具
 * @author Administrator
 *
 */
public class StringUtil {
	/**
	 * 判断是否字符串是空或者空字符串
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }
	
	/**
	 * 判断是否字符串不是空或者空字符串
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
        return !StringUtils.isEmpty(str);
    }
}
