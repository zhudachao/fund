package com.vista.scheduled;

import com.vista.mapper.ScheduledCronMapper;
import com.vista.model.ScheduledCron;
import com.vista.utils.SpringUtils;

public interface ScheduledOfTask extends Runnable {
	
	
    /**
     * 定时任务方法
     */
    void execute();
    /**
     * 实现控制定时任务启用或禁用的功能
     */
    @Override
    default void run() {
    	
    	ScheduledCronMapper mapper = SpringUtils.getBean(ScheduledCronMapper.class);
        ScheduledCron scheduledCron = mapper.findByCronClass(this.getClass().getName());
       
        if (StatusEnum.DISABLED.getCode().equals(scheduledCron.getStatus())) {
            // 任务是禁用状态
            return;
        }
        execute();
    }
}