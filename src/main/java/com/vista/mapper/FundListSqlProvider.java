package com.vista.mapper;

import org.apache.ibatis.jdbc.SQL;

import com.vista.model.FundList;
import com.vista.utils.StringUtils;

public class FundListSqlProvider {
	
	
	public String AddFundList(FundList fund) {
		return new SQL() {
			{	
				INSERT_INTO("fund_list");				
				if(StringUtils.isNotEmpty(fund.getCode())){
					VALUES("code", "#{code}");
				}
				if(!StringUtils.isEmpty(fund.getName())){
					VALUES("name", "#{name}");
				}
				if(!StringUtils.isEmpty(fund.getSimpleName())){
					VALUES("simple_name", "#{simpleName}");
				}
				if(!StringUtils.isEmpty(fund.getCatagory())){
					VALUES("catagory", "#{catagory}");
				}				
			}
		}.toString();
	}
	
	public String updateFundList(FundList fundList) {
		return new SQL() {
			{	
				UPDATE("fund_list");	
				
				if(!StringUtils.isEmpty(fundList.getCode())){
					SET("code = #{code}");
				}
				if(!StringUtils.isEmpty(fundList.getRiskLevel())){
					SET("risk_level = #{riskLevel}");
				}
				if(!StringUtils.isEmpty(fundList.getFundScale())){
					SET("fund_scale = #{fundScale}");
				}
				if(!StringUtils.isEmpty(fundList.getFundManager())){
					SET("fund_manager = #{fundManager}");
				}
				if(!StringUtils.isEmpty(fundList.getBuildDate())){
					SET("build_date = #{buildDate}");
				}				
				WHERE("code = #{code}");
			}
		}.toString();
	}

}
