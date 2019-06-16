package indi.zyf.sso.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class EncryptionUtil {
	public static String base64Encode(String data) {
		return Base64.encodeBase64String(data.getBytes());
	}

	public static byte[] base64Decode(String data) {
		return Base64.decodeBase64(data.getBytes());
	}

	public static String md5(String data) {
		return DigestUtils.md5Hex(data);
	}
	
	public static void main(String args[]) {
		String password = RandomUtil.genCharacterString(6);
		String md5 = EncryptionUtil.md5(password);
		System.out.println(password);
		System.out.println(md5);
	}
}
