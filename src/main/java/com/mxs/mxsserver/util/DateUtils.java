package com.mxs.mxsserver.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	public static String dateFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = new Date();
		return sdf.format(date);
	}
	
	public static Long subtractForDate(Date start, Date end, long value) {
		if(null == start || null == end || value <= 0)
			return null;
		return (end.getTime() - start.getTime())/value;
	}
}
