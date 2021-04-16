package com.vista.scheduled;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;

import com.vista.mapper.ScheduledCronMapper;
import com.vista.model.ScheduledCron;

@Configuration
public class ScheduledConfig implements SchedulingConfigurer {

	@Autowired
	private ApplicationContext context;
	@Autowired
	private ScheduledCronMapper cronMapper;

	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		for (ScheduledCron scheduledCron : cronMapper.getScheduledAll()) {
			Class<?> clazz;
			Object task;
			try {
				clazz = Class.forName(scheduledCron.getCronClass());
				task = context.getBean(clazz);
			} catch (ClassNotFoundException e) {
				throw new IllegalArgumentException("scheduled_cron表数据" + scheduledCron.getCronClass() + "有误", e);
			} catch (BeansException e) {
				throw new IllegalArgumentException(scheduledCron.getCronClass() + "未纳入到spring管理", e);
			}
			Assert.isAssignable(ScheduledOfTask.class, task.getClass(), "定时任务类必须实现ScheduledOfTask接口");
			// 多线程方法一
			// ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
			// scheduler.setPoolSize(5);

			taskRegistrar.setScheduler(Executors.newScheduledThreadPool(5));

			// 可以通过改变数据库数据进而实现动态改变执行周期
			taskRegistrar.addTriggerTask(((Runnable) task), triggerContext -> {
				String cronExpression = scheduledCron.getCronExpression();
				return new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
			});
		}
	}

	@Bean
	public Executor taskExecutor() {
		return Executors.newScheduledThreadPool(10);
	}

}
