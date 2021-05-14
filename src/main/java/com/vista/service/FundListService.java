package com.vista.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.vista.constant.FundConstants;
import com.vista.model.FundList;
import com.vista.utils.DateUtil;
import com.vista.utils.HttpUtils;
import com.vista.utils.LogUtils;

@Service
public class FundListService {

	private static final Logger logger = LogUtils.getBusiLogger();

	private final FundList fundList = new FundList();

	// 解析基金相关信息
	public FundList getInfo(String code) {
		// Document doc =Jsoup.connect(url).get();
    
		logger.info("查询  {} 基金详细信息", code);
		String url = FundConstants.FUND_BASE_URL.replace("fund_code", code);
		String html = "";
		try {
			fundList.setCode(code);
			html = HttpUtils.get(url, null, "utf-8", null);
			
			Document document = Jsoup.parse(html);
			Elements eles = document.getElementsByTag("table");
			Element tableEle = eles.get(2);
			Element tbody = tableEle.select("tbody").get(0);
			Elements trs = tbody.select("tr");
			Elements firstTds = trs.get(0).select("td");
			Elements secondTds = trs.get(1).select("td");

			String risk_level = firstTds.get(0).text().split(" | ")[2];
			// String catagory =firstTds.get(0).select("a").text();
			String fund_scale = firstTds.get(1).text().split("：")[1];
			String fund_manager = firstTds.get(2).select("a").text();
			String build_date = secondTds.get(0).text().split("：")[1];
			build_date =DateUtil.isDate(build_date)?build_date:null;
			
			fundList.setRiskLevel(risk_level);
			fundList.setFundScale(fund_scale);
			fundList.setFundManager(fund_manager);
			fundList.setBuildDate(build_date);
		} catch (Exception e) {
			logger.error("业务处理异常： {}", e.getMessage());
		}
		return fundList;

	}

	

}
