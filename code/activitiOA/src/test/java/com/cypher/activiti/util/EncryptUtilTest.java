package com.cypher.activiti.util;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;

public class EncryptUtilTest {

	/**
	 * 测试创建Salt时，传入参数小于0
	 */
	@Ignore
	public void testGenerateSaltArgLessThanZero() {
		try {
			EncryptUtil.generateSalt(-1);
		} catch (Exception ex) {
			assertEquals(ex.getMessage(), "numBytes argument must be a positive integer (1 or larger)");
		}

	}

}
