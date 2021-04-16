package com.vista.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vista.constant.FundConstants;
import com.vista.mapper.FundDataMapper;
import com.vista.mapper.FundListMapper;
import com.vista.model.FundData;
import com.vista.model.FundList;
import com.vista.utils.HttpUtils;
import com.vista.utils.LogUtils;
import com.vista.utils.StringUtils;

@Service
public class FundService {

	private static final Logger logger = LogUtils.getBusiLogger();

	@Autowired
	private FundListMapper fundListMapper;
	@Autowired
	private FundDataMapper fundDataMapper;
	@Autowired
	private JsoupService jsoupService;

	// 查询所有基金数据并登记
	public String[][] queryFundList() {
		// 1.外调 2.返回登记
		String response = HttpUtils.get(FundConstants.FUND_List_URL, "", "utf-8", "");
		String listStr = response.substring(9, response.length() - 1);
		String[][] fundList = JSON.parseObject(listStr, String[][].class);
		FundList fund = new FundList();
		for (String[] fundInfo : fundList) {
			fund.setCode(fundInfo[0]);
			fund.setName(fundInfo[2]);
			fund.setSimpleName(fundInfo[1]);
			fund.setCatagory(fundInfo[3]);
			fundListMapper.addFundList(fund);
		}
		return fundList;
	}

	// 查询基金类型
	public List<String> queryCatagorys() {
		return fundListMapper.findCatagorys();
	}

	// 根据基金代码登记详细信息
	public void updFundInfoByCode(String code) {
		FundList fundList = jsoupService.getInfo(code);
		fundListMapper.updateFundList(fundList);
	}

	// 根据基金类型查询基金历史增长率信息
	public void updFundInfoByCatagory(String catagroy) {
		List<String> codes = fundListMapper.findFundCodeListByCatagoryAndRiskLevel(catagroy);
		logger.info("基金类型:{},基金代码列表：{}", catagroy, codes);
		for (String code : codes) {
			FundList fundList = jsoupService.getInfo(code);
			fundListMapper.updateFundList(fundList);
		}
	}

	// 根据基金代码及开始、截止日期查询基金 数据
	public JSONObject addHistoryFundData(String startDate, String code) {

		List<String> dates = fundDataMapper.selWorthDates(code);
		JSONObject data = getFundHistoryByCode(startDate, code);

		if (data != null) {
			JSONArray list = data.getJSONArray("LSJZList");
			List<FundData> FundDataList = new ArrayList<FundData>();
			for (Object object : list) {
				JSONObject fundData = (JSONObject) object;
				String FSRQ = fundData.getString("FSRQ"); // 净值日期;
				String DWJZ = fundData.getString("DWJZ"); // 单位净值;
				String LJJZ = fundData.getString("LJJZ"); // 累计净值;
				String JZZZL = fundData.getString("JZZZL"); // 日增长率;
				String SGZT = fundData.getString("SGZT"); // 申购状态;
				String SHZT = fundData.getString("SHZT"); // 赎回状态;
				if (dates.indexOf(FSRQ) < 0) {
					FundData fund = new FundData();
					fund.setCode(code);
					fund.setNetWorthDate(FSRQ);
					fund.setNetAssetValue(new BigDecimal(DWJZ));
					fund.setNetAccumulateValue(new BigDecimal(LJJZ));
					fund.setDayOfGrowth(new BigDecimal(JZZZL));
//					fund.setNetAccumulateValue(LJJZ);
//					fund.setDayOfGrowth(JZZZL);
					fund.setStatePurchase(StateChange(SGZT));
					fund.setStateRedeem(StateChange(SHZT));
					// 插入fund_data表
					// fundDataMapper.addFundData(fund);
					FundDataList.add(fund);
				}
			}
			if (FundDataList.size() >= 1) {
				fundDataMapper.batchAdd(FundDataList);
			}
			logger.info("基金代码：{} 历史记录表登记完成,登记条数：{}", code, FundDataList.size());
		}
		return data;
	}

	/**
	 * 查询某基金历史数据
	 */
	public JSONObject getFundHistoryByCode(String startDate, String code) {
		// 涉及外调，开始日期页数及显示数在constants默认配置 2000-01-01，10000条数据
		String endDate = DateUtils.formatDate(new Date(), "yyyy-MM-dd");
		String pageIndex = "1";
		String pageSize = "10000";
		long time = System.currentTimeMillis();

		String url = FundConstants.FUND_DATA_URL;
		String referer = FundConstants.FUND_REFERER_URL.replace("fund_code", code);
		String param = "fundCode=%s&pageIndex=%s&pageSize=%s&startDate=%s&endDate=%s&_=%s";
		param = String.format(param, code, pageIndex, pageSize, startDate, endDate, time);

		String str = HttpUtils.get(FundConstants.FUND_DATA_URL, param, "utf-8", referer);
		JSONObject data = JSON.parseObject(str).getJSONObject("Data");
		return data;
	}

	/**
	 * 根据基金类型新增基金历史数据
	 * 
	 * @param catagory
	 */
	public void addHistoryFundDataByCatagory(String startDate, String catagory) {
		List<String> codes = fundListMapper.findFundCodeListByCatagory(catagory);
		List<String> fund_data_codes = fundDataMapper.selCodes();
		for (String code : codes) {
			if (fund_data_codes.indexOf(code) < 0) {
				addHistoryFundData(startDate, code);
			}
		}
	}

	public static Integer StateChange(String state) {
		// 开放申购或赎回 返回1，关闭返回0 无值返回 null
		if (StringUtils.isNotEmpty(state)) {
			return state.equals("开放申购") || state.equals("开放赎回") ? 1 : 0;
		} else {
			return null;
		}
	}

	public void fundScheduledUpdate() {
		logger.info("对fund_data表已登记的基金净值进行同步");
		List<FundData> datas = fundDataMapper.selFundDatasForUp();
		for (FundData fundData : datas) {
			addHistoryFundData(fundData.getCode(), fundData.getCode());
		}
	}
	
	public static void main(String[] args) {
		BigDecimal v1= new BigDecimal("0.00");
		System.out.println(v1.equals(BigDecimal.ZERO));
		
		System.out.println(BigDecimal.ZERO);
	}

}
