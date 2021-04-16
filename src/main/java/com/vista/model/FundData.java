package com.vista.model;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import lombok.Data;

@Data
public class FundData {

	/**
	 * id
	 */
	private Integer id;
	/**
	 * 基金代码
	 */
	private String code;
	/**
	 * 基金名称
	 */
	private String name;
	/**
	 * 净值日期
	 */
	private String netWorthDate;
	/**
	 * 单位净值
	 */
	private BigDecimal netAssetValue;
	/**
	 * 累计净值
	 */
	private BigDecimal netAccumulateValue;
	/**
	 * 日增长率
	 */
	private BigDecimal dayOfGrowth;	
	/**
	 * 申购状态（0-关闭，1-开放）
	 */
	private Integer statePurchase;
	/**
	 * 申购状态（0-关闭，1-开放）
	 */
	private Integer stateRedeem;

}