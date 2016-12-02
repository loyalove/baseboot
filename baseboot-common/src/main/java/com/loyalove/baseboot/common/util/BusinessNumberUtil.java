package com.loyalove.baseboot.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *                       
 * @Filename BusinessNumberUtil.java
 *
 * @Description 业务流水号工具类
 *
 */
public class BusinessNumberUtil {

	public static String GainNumber() {
		StringBuffer number = new StringBuffer();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyyMMddHHmmssSSS");
		number.append(simpleDateFormat.format(new Date()));
		int a = (int) (Math.random() * 1000);
		if (a < 100) {
			a = a * 10;
		}
		number.append(a);
		return number.toString();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {

			System.out.println(BusinessNumberUtil.GainNumber());
		}
	}
}