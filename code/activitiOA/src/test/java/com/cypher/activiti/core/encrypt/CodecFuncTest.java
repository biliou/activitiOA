package com.cypher.activiti.core.encrypt;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Ignore;
import org.junit.Test;

/**
 * 测试加密算法方法使用
 * 
 * @author Administrator
 *
 */
public class CodecFuncTest {

	/**
	 * 不可逆运算：MD5 78a86b7b8efc37c75c46affadc989b1c
	 */
	@Ignore
	public void TestMD5() {
		System.out.println("==============MD5================");
		String pwd = "jinggujin1";
		byte[] data = pwd.getBytes();
		String encodePwd = DigestUtils.md5Hex(data);
		System.out.println("加密后:" + encodePwd);
	}

	/**
	 * 不可逆运算：SHA1 28af42a448752a760ac2fce0af54116a58dfd991
	 */
	@Ignore
	public void TestSHA1() {
		System.out.println("==============SHA1================");
		String pwd = "jinggujin1";
		byte[] data = pwd.getBytes();
		String encodePwd = DigestUtils.sha1Hex(data);
		System.out.println("加密后:" + encodePwd);
	}

	/**
	 * 可逆运算：Base64 jinggujin1 加密
	 */
	@Ignore
	public void TestEncodeBase64() {
		System.out.println("==============Base64==Encode==============");
		String pwd = "jinggujin1";
		byte[] data = pwd.getBytes();
		String encodePwd = Base64.encodeBase64String(data);
		System.out.println("加密后:" + encodePwd);
	}

	/**
	 * 可逆运算：Base64 amluZ2d1amluMQ== 解密
	 */
	@Ignore
	public void TestDecodeBase64() {
		System.out.println("==============Base64==Decode==============");
		String encodePwd = "amluZ2d1amluMQ==";
		String pwd = new String(Base64.decodeBase64(encodePwd));
		System.out.println("解密后:" + pwd);
	}

	/**
	 * 可逆运算：HEX jinggujin1 加密
	 */
	@Ignore
	public void TestEncodeHEX() {
		System.out.println("==============HEX==Encode==============");
		String pwd = "jinggujin1";
		byte[] data = pwd.getBytes();
		String encodePwd = Hex.encodeHexString(data);
		System.out.println("加密后:" + encodePwd);
	}

	/**
	 * 可逆运算：HEX 6a696e6767756a696e31 解密
	 */
	@Ignore
	public void TestDecodeHEX() {
		System.out.println("==============HEX==Decode==============");
		String encodePwd = "6a696e6767756a696e31";
		try {
			String pwd = new String(Hex.decodeHex(encodePwd.toCharArray()));
			System.out.println("解密后:" + pwd);
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		
	}

}
