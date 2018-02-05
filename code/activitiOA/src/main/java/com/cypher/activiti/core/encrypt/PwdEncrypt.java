package com.cypher.activiti.core.encrypt;

import com.cypher.activiti.util.EncryptUtil;

/**
 * 账号密码加密与解密
 * 
 * @author bili.ou
 *
 */
public class PwdEncrypt {
	/**
	 * 业务逻辑：</br>
	 * 1.获得一个随机数作盐值 salt</br>
	 * 2.将盐值 salt 进行可逆加密 HEX</br>
	 * 3.将密码，第一步的值，进行散列次数 1024 作为参数，使用SHA1不可逆加密</br>
	 * 4.将第三步的值进行可逆加密 HEX</br>
	 * 4.将2的值 和 4的值拼接后形成正式加密密码值</br>
	 * 
	 * 将生成每次都不同的加密密码值
	 * 
	 * @param pwd
	 * @return
	 */
	public static String encodePwd(String pwd) {
		byte[] random = EncryptUtil.generateSalt(8);
		String salt = EncryptUtil.encodeHex(random);
		byte[] sha1Pwd = EncryptUtil.sha1(pwd.getBytes(), random, 1024);
		String part2 = EncryptUtil.encodeHex(sha1Pwd);
		String encodePwd = salt + part2;

		return encodePwd;
	}

	/**
	 * 验证用户输入密码 和 数据库中密码是否一致
	 * 
	 * @param destPwd
	 *            用户输入密码
	 * @param srcPwd
	 *            数据库中密码
	 * @return
	 */
	public static Boolean validataPwd(String destPwd, String srcPwd) {
		String srcPwdPart2 = srcPwd.substring(16, srcPwd.length());

		byte[] random = EncryptUtil.decodeHex(srcPwd.substring(0, 16));
		byte[] sha1DestPwd = EncryptUtil.sha1(destPwd.getBytes(), random, 1024);
		String destPwdPart2 = EncryptUtil.encodeHex(sha1DestPwd);

		if (srcPwdPart2.equals(destPwdPart2)) {
			return true;
		} else {
			return false;
		}

	}
}