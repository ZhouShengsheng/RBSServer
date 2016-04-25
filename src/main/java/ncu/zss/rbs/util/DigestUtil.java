package ncu.zss.rbs.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class DigestUtil {

	/**
	 * SHA1算法实现。
	 * 
	 * @param decript 需要进行sha1加密的字符串。
	 * @return sha1加密之后的字符串。
	 */
	public static String sha1(String decript) {
		try {
			MessageDigest digest = java.security.MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			// 字节数组转换为 十六进制 数
			for (int i = 0; i < messageDigest.length; i++) {
				String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
				if (shaHex.length() < 2) {
					hexString.append(0);
				}
				hexString.append(shaHex);
			}
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

}
