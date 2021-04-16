package com.vista.utils;

public class StringUtils {	
	
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	
	public static boolean isEmpty(String str) {
		return null==str||"".equals(str)?true:false;
	}

}
