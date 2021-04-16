package com.vista.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ScheduledCron implements Serializable {

	private Integer id;
	private String cronKey;
	private String cronClass;
	private String cronExpression;
	private String taskExplain;
	private Integer status; // 1.正常2.停用

}
