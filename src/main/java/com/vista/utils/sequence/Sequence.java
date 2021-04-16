package com.vista.utils.sequence;

import lombok.Data;

@Data
public class Sequence {

	private String moduleName;//模块名称
	private String moduleCode;//模块编码
	private String templet;//当前模块使用序号模板
	private String maxSerial;//存放当前流水号的值
	private Integer preMaxNum;  //预生成流水号存放在缓存中的个数
	private Integer icreNum;//增长数量
	private Integer seqLength;//流水长度
	

}
