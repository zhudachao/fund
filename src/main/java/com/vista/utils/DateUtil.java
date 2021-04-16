package com.vista.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateUtil {
	
	private static final SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat f_date_8 = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat f_time_6 = new SimpleDateFormat("HHmmss");
	private static final SimpleDateFormat f_date_time_14 = new SimpleDateFormat("yyyyMMddHHmmss");
	
	private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);

	// 判断是否特定日期格式
	public static boolean isDate(String strDate) {

		String regex = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s((([0-1]?[0-9])|(2?[0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	//根据日期获取周几
	 public static int dateToWeek(String date)  {
		 int[] weekdays= {0,1,2,3,4,5,6};
		 Calendar cal = Calendar.getInstance();
		 try {
			cal.setTime(f.parse(date));
		} catch (ParseException e) {
			logger.info("日期转换异常");
		}
		 int w =cal.get(Calendar.DAY_OF_WEEK)-1;		 
		 return weekdays[w];
	 }
	 //判断是否交易日
	public static boolean isTranDay(String date) {
		 //只判断周末情况其他节假日情况省略
		return dateToWeek(date)==0||dateToWeek(date)==6?false:true;
	 }
	
	//获取当前时间
	public static String getDate() {
		return f.format(new Date());
	}
	
	public static String getDate8() {
		return f_date_8.format(new Date());
	}
	public static String getTime6() {
		return f_time_6.format(new Date());
	}
	public static String getDateTime14() {
		return f_date_time_14.format(new Date());
	}
	
	 
	
	public static void main(String[] args) {
		System.out.println(isTranDay("2020-12-26"));
		System.out.println(getDate8());
	}

}
