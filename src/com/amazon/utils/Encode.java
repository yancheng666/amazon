package com.amazon.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class Encode {
	 //md5加密
    public static String md5Encode(byte[] input) {
		return DigestUtils.md5Hex(input);
		
	}
    //使用亦或加密
    public static String xor(String input) {
		char[] chs = input.toCharArray();
		for (int i = 0; i < chs.length; i++) {
			chs[i]=(char) (chs[i]^666);  //对每一位进行加密
		}
		return new String(chs);
	}
}
