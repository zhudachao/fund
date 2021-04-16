package com.vista.scheduled;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.vista.utils.LogUtils;
@Component
public class DynamicTaskDemo implements ScheduledOfTask {
	private Logger logger = LogUtils.getBusiLogger();
	private int i;

	@Override
    public void execute() {
		Thread.currentThread().setName(String.valueOf(UUID.randomUUID()));
		 logger.info("线程名："+Thread.currentThread().getName());
        logger.info("thread id:{},DynamicPrintTask execute times:{}", Thread.currentThread().getId(), ++i);
    }
}