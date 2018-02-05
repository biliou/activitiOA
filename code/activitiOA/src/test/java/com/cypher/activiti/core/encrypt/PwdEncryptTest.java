package com.cypher.activiti.core.encrypt;

import static org.junit.Assert.assertEquals;
import java.util.List;

import org.junit.Test;
import com.cypher.activiti.util.EncryptUtil;

public class PwdEncryptTest {

	/**
	 * 测试新输入的密码 和 旧的已加密的密码进行对比</br>
	 * 1.密码 pwd 已知</br>
	 * 2.将旧的加密密码，计算得到随机数random，与拼接后的值 oldPwdPart2</br>
	 * 3.将已知 密码 pwd 通过第二步得出的random结合进行散列次数得到业务逻辑第三步的值</br>
	 * 4.将第三步的值进行hex 加密，得到newPwdPart2 </br>
	 * 5.将oldPwdPart2 和 newPwdPart2 进行对比
	 */
	@Test
	public void testEncodePwd() {
		String pwd = "123456";
		String encodePwd = "bfcb30e646902708c2d1dc075ce8b178b9a8569d9157904efff724e2";

		String oldPwdPart2 = encodePwd.substring(16, encodePwd.length());

		byte[] random = EncryptUtil.decodeHex(encodePwd.substring(0, 16));
		byte[] plainSha1pwd = EncryptUtil.sha1(pwd.getBytes(), random, 1024);
		String newPwdPart2 = EncryptUtil.encodeHex(plainSha1pwd);

		assertEquals(oldPwdPart2, newPwdPart2);

	}
	
	@Test
	public void testValidataPwd() {
		String pwd = "1234";
		String encodePwd = "a4527621b5034c7705f4d758332366b544f11fbd8b8469220650bb1c";
		boolean result = PwdEncrypt.validataPwd(pwd, encodePwd);
		System.out.println(result);

	}

}
