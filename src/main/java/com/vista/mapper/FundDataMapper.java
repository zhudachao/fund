package com.vista.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.vista.model.FundData;

@Mapper
public interface FundDataMapper {

	@InsertProvider(type = FundDataSqlProvider.class, method = "AddFundData")
	@Options(useGeneratedKeys = true, keyProperty = "fund_data.id")
	int addFundData(FundData data);

	/**
	 * 批量增加模式一
	 * 
	 * @param funds
	 * @return
	 */
	@Insert("<script>"
			+ "INSERT INTO fund_data(code,net_worth_date,net_asset_value,net_accumulate_value,day_of_growth,state_purchase,state_redeem) "
			+ "VALUES <foreach collection=\"list\" item=\"funds\" index=\"index\" separator=\",\">"
			+ "(#{funds.code},#{funds.netWorthDate},#{funds.netAssetValue},#{funds.netAccumulateValue},#{funds.dayOfGrowth},#{funds.statePurchase},#{funds.stateRedeem})"
			+ " </foreach>" + "</script>")
	int batchAdd(@Param("list") List<FundData> funds);

	// @InsertProvider(type = FundDataSqlProvider.class, method = "insertAll")
	// int insertAll(@Param("list") List<FundData> funds);

	@Select("select net_worth_date from fund_data where code = #{code}")
	List<String> selWorthDates(String code);

	@Select("select distinct code from fund_data")
	List<String> selCodes();

	// 查询登记日期不是今日日期的代码及最近日期
	@Results({ @Result(property = "code", column = "code"),
		@Result(property = "netWorthDate", column = "max_date") })
	@Select("select code,max_date from (select code,max(net_worth_date) as max_date from fund_data GROUP BY code) a where a.max_date < CURDATE()")
	List<FundData> selFundDatasForUp();

}
