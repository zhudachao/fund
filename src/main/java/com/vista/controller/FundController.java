package com.vista.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.vista.service.FundDataService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(value = "基金相关接口", tags = {"基金相关接口"})
public class FundController {

	@Autowired
	private FundDataService fundService;
	
	@ApiOperation(value = "基金列表查询", notes = "基金列表查询")
	@GetMapping("/fundlist")	
	public String[][] fundlist() {
		return fundService.queryFundList();
	}
	
	@ApiOperation(value = "基金类型查询", notes = "基金类型查询", response = List.class)
	@GetMapping("/catagorys")	
	public List<String> catagorys() {
		return fundService.queryCatagorys();
	}
	
	@ApiImplicitParam(name = "code", value = "基金代码", required = true, dataType = "String")
	@ApiOperation(value = "基金详细信息更新", notes = "基金详细信息更新", response = List.class)
	@GetMapping("/renew/{code}")
	public String renewFundInfo(@PathVariable String code) {
		fundService.updFundInfoByCode(code);
		return code + "信息更新完成";
	}
	
	@ApiOperation(value = "基金详细信息更新", notes = "基金详细信息更新", response = List.class)
	@PostMapping("/renew")
	public String renewFundInfo(HttpServletRequest request) {
		String catagory = request.getParameter("catagory");
		fundService.updFundInfoByCatagory(catagory);
		return "类型" + catagory + " 信息更新完成";
	}

	@ApiOperation(value = "基金历史记录登记", notes = "基金历史记录登记", response = JSONObject.class)
	@PutMapping("/data/{code}")
	public JSONObject getFundHistoryByCode(@PathVariable String code) {
		String startDate = "2014-01-01";
		return fundService.addHistoryFundData(startDate, code);
	}
	 //参数示例@ApiParam(name = "username", value = "用户名或邮箱或手机号", required = false) @RequestParam(name = "username", required = false) String username,
	@ApiOperation(value = "基金历史记录登记by基金类型", notes = "基金历史记录登记by基金类型", response = String.class)
	@GetMapping("/data")
	public String getFundHistoryByCatagory(HttpServletRequest request) {
		String startDate = "2014-01-01";
		String catagory = request.getParameter("catagory");
		fundService.addHistoryFundDataByCatagory(startDate, catagory);
		return "类型" + catagory + " 历史记录登记完成";
	}
}
