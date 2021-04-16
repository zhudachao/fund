package com.vista.model;

import java.util.Date;
import lombok.Data;

@Data
public class FundList {

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
	 * 基金简称
	 */
	private String simpleName;
	/**
	 * 基金类型
	 */
	private String catagory;
	/**
	 * 风险等级
	 */
	private String riskLevel;
	/**
	 * 基金规模
	 */
	private String fundScale;
	/**
	 * 基金经理
	 */
	private String fundManager;
	/**
	 * 成立日期
	 */
	private String buildDate;


}