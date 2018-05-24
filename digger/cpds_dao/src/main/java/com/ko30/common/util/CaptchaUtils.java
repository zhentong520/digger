package com.ko30.common.util;

import java.util.Random;

public class CaptchaUtils {

	private CaptchaUtils() {
	}

	/**
	 * 生成指定位数的验证码
	 */
	public static String generateCaptcha(int len) {
		Random random = new Random();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(random.nextInt(10));
		}

		return sb.toString();
	}

}
