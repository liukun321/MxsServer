package com.mxs.mxsserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static String dateFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		return sdf.format(date);
	}
	
//	public static void main(String[] args) {
//		String time = dateFormat("yyyyMMddHHmmss");
//		System.out.println(time);
//	}
}
