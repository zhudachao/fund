package com.vista.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vista.utils.HttpUtils;

public class Test {
	
	public static void main(String[] args) {
		
//		getFundList();
		testFundData("002190");
//		String[][] arr = new String[][]{{"1","2","4"},{"5","7","8"}};
//		for (String[] strings : arr) {
//			System.out.println(strings[0]);
//		}
//		
	}
	

	
	public static void getFundList() {
		String url ="http://fund.eastmoney.com/js/fundcode_search.js";
		String str = HttpUtils.get(url, "", "utf-8","");
		str = str.substring(9, str.length()-1);
		String[][] arr = JSON.parseObject(str, String[][].class);
		for (String[] strings : arr) {
			
		}
		
		
		System.out.println(arr[1][1]);
		
//		List<String> strsToList =new ArrayList<>();
//		Collections.addAll(strsToList,listStr);
//		for (String string : strsToList) {
//			String[] fundInfo = string.split(",");
//			System.out.println(fundInfo[0]);
//		}
//		
		
		
//		for (String string : strsToList) {
//			List<String> fundInfoStr =new ArrayList<>();
//			Collections.addAll(fundInfoStr,string);
//			
//		}
//		System.out.println(listStr);
		
		
	}
	
	
	
	
	public static JSONArray testFundData(String code){
        Integer pageIndex = 1;
        Integer pageSize=10000;
        String startTime="2000-1-1";
        String endTime = "2020-4-15";
        String referer = "http://fundf10.eastmoney.com/f10/jjjz_" + code + ".html";
        long time = System.currentTimeMillis();
        String url = "http://api.fund.eastmoney.com/f10/lsjz";
//        url = String.format(url,code,pageIndex,pageSize,startTime,endTime,time);
//        String param = "callback=jQuery18306596328894644803_1571038362181&" +
        		String param = "fundCode=%s&pageIndex=%s&pageSize=%s&startDate=%s&endDate=%s&_=%s";
        param = String.format(param,code,pageIndex,pageSize,startTime,endTime,time);
        
        
        String str = HttpUtils.get(url, param, "utf-8", referer);

        //转换为Obj类型
        JSONObject jsonObject = JSON.parseObject(str);
        System.out.println(jsonObject);
        //获取数组
        JSONObject data = jsonObject.getJSONObject("Data");
        JSONArray jsonArray = data.getJSONArray("LSJZList");
        //计算数组的长度
        int size = jsonArray.size();
        System.out.println(size);

        return jsonArray;
    }

}
