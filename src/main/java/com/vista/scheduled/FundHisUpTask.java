package com.vista.scheduled;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vista.service.FundService;
import com.vista.utils.DateUtil;
import com.vista.utils.LogUtils;

@Component
public class FundHisUpTask implements ScheduledOfTask {
    private Logger logger = LogUtils.getBusiLogger();
    
    @Autowired
    private FundService service;
    
    @Override
    public void execute() {
    	logger.info("基金净值同步开始");
    	service.fundScheduledUpdate();
    	logger.info("截至日期 {},基金净值同步完成",DateUtil.getDate());
    }
    
    
    
}