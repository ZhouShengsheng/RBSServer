package ncu.zss.rbs.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * util class for date
 *
 */
public class DateUtil {
	
	public static String nowDate() {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		return df.format(new Date());// new Date()为获取当前系统时间
	}

}
