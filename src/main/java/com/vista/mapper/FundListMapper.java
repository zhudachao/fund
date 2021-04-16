package com.vista.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.vista.model.FundList;


//开启二级缓存
@CacheNamespace(blocking = true)   
@Mapper
public interface FundListMapper {
	
	@InsertProvider(type = FundListSqlProvider.class, method = "AddFundList")
    @Options(useGeneratedKeys = true, keyProperty = "fund_list.id")
    int addFundList(FundList list);
	
	@UpdateProvider(type = FundListSqlProvider.class, method = "updateFundList")
	int updateFundList(FundList list);
	
	
	@Select("select distinct catagory from fund_list")
	List<String> findCatagorys();
	
	@Select("select code from fund_list where catagory = #{catagory}")
	List<String> findFundCodeListByCatagory(String catagory);
	
	@Select("select code from fund_list where catagory = #{catagory} and risk_level IS NULL")
	List<String> findFundCodeListByCatagoryAndRiskLevel(String catagory);
	
	
	
	
}
