package com.wewe.android.string;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {

	/**
	 * 将字符串装换为MD5
	 * 
	 * @param str
	 * @return
	 */
	public static String strToMd5(String str) {
		String md5Str = null;
		if (str != null && str.length() != 0) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(str.getBytes());
				byte b[] = md.digest();
				int i;
				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				// 32位
				// md5Str = buf.toString();
				// 16位
				md5Str = buf.toString().substring(8, 24);
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return md5Str;
	}

	public static String strToMd5_32(String str) {
		String md5Str = null;
		if (str != null && str.length() != 0) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
				md.update(str.getBytes());
				byte b[] = md.digest();
				int i;
				StringBuffer buf = new StringBuffer("");
				for (int offset = 0; offset < b.length; offset++) {
					i = b[offset];
					if (i < 0)
						i += 256;
					if (i < 16)
						buf.append("0");
					buf.append(Integer.toHexString(i));
				}
				// 32位
				md5Str = buf.toString();
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return md5Str;
	}

	/**
	 * 取md5的低7位转化成int
	 * 
	 * @param md5
	 * @return
	 */
	public static int md5L8ToInt(String md5) {
		if (md5 == null)
			return 0;
		md5 = md5.replaceAll("-", "");
		if (md5.length() == 0)
			return 0;
		if (md5.length() >= 8)
			md5 = md5.substring(md5.length() - 7);
		return (int) Long.parseLong(md5, 16);
	}

	/**
	 * 转换url成md5码，再截取前8位为int值，并取绝对值
	 * 
	 * @param url
	 * @return
	 */
	public static int buildSsidByUrl(String coverUrl, String bookUrl) {
		if (!StringUtil.isEmpty(coverUrl)) {
			return Math.abs(md5L8ToInt(strToMd5_32(coverUrl)));
		} else {
			return Math.abs(md5L8ToInt(strToMd5_32(bookUrl)));
		}
	}
}