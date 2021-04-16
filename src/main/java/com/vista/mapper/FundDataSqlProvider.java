package com.vista.mapper;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

import com.vista.model.FundData;
import com.vista.utils.StringUtils;

public class FundDataSqlProvider {
	
	
	public String AddFundData(FundData fundData) {
		return new SQL() {
			{	
				INSERT_INTO("fund_data");				
				if(StringUtils.isNotEmpty(fundData.getCode())){
					VALUES("code", "#{code}");
				}
				if(StringUtils.isNotEmpty(fundData.getName())){
					VALUES("name", "#{name}");
				}
				if(StringUtils.isNotEmpty(String.valueOf(fundData.getNetWorthDate()))){
					VALUES("net_worth_date", "#{netWorthDate}");
				}
				if(null!=fundData.getNetAssetValue()){
					VALUES("net_asset_value", "#{netAssetValue}");
				}		
				if(null!=fundData.getNetAccumulateValue()){
					VALUES("net_accumulate_value", "#{netAccumulateValue}");
				}	
				if(null!=fundData.getDayOfGrowth()){
					VALUES("day_of_growth", "#{dayOfGrowth}");
				}
				if(StringUtils.isNotEmpty(String.valueOf(fundData.getStatePurchase()))){
					VALUES("state_purchase", "#{statePurchase}");
				}	
				if(StringUtils.isNotEmpty(String.valueOf(fundData.getStateRedeem()))){
					VALUES("state_redeem", "#{stateRedeem}");
				}
			}
		}.toString();
	}
	
	
	/**
	 * 批量增加模式二,未成功
	 */
//	public String insertAll(Map map) {  
//        List<FundData> fundDatas = (List<FundData>) map.get("list");  
//        StringBuilder sb = new StringBuilder();  
//        sb.append("INSERT INTO fund_data");  
//        sb.append("(id,code,net_worth_date,net_asset_value,net_accumulate_value,day_of_growth,state_purchase,state_redeem) ");  
//        sb.append("VALUES ");  
//        MessageFormat mf = new MessageFormat("(null,#'{'list[{0}].code},#'{'list[{0}].netWorthDate},#'{'list[{0}].netAssetValue},#'{'list[{0}].netAccumulateValue},#'{'list[{0}].dayOfGrowth},#'{'list[{0}].statePurchase},#'{'list[{0}].stateRedeem})");  
//        for (int i = 0; i < fundDatas.size(); i++) {  
//            sb.append(mf.format(new Object[]{i}));  
//            if (i < fundDatas.size() - 1) {  
//                sb.append(",");  
//            }  
//        }  
//        return sb.toString();  
//    }  
	
	}
