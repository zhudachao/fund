package com.vista.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtils {
	// 业务日志
	private static Logger busiLogger = LoggerFactory.getLogger("busi");
	// 外调日志
	private static Logger linkLogger = LoggerFactory.getLogger("link");
	// 启动日志
	private static Logger bootLogger = LoggerFactory.getLogger("boot");

	public static Logger getBusiLogger() {
		if (null == busiLogger) {
			busiLogger = LoggerFactory.getLogger("busi");
		}
		return busiLogger;
	}

	public static Logger getLinkLogger() {
		if (null == linkLogger) {
			linkLogger = LoggerFactory.getLogger("link");
		}
		return linkLogger;
	}

	public static Logger getBootLogger() {
		if (null == bootLogger) {
			bootLogger = LoggerFactory.getLogger("boot");
		}
		return bootLogger;
	}

}
