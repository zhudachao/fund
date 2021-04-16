package com.vista.mapper;

import java.util.List;

import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.vista.model.ScheduledCron;

@Mapper
@CacheNamespace(blocking = true)
public interface ScheduledCronMapper {

	@Results({ @Result(property = "id", column = "id"), 
			@Result(property = "cronKey", column = "cron_key"),
			@Result(property = "cronClass", column = "cron_class"),
			@Result(property = "cronExpression", column = "cron_expression"),
			@Result(property = "taskExplain", column = "task_explain"),
			@Result(property = "status", column = "status") })
	@Select("select * from scheduled_cron where status ='1'")
	public List<ScheduledCron> getScheduledAll();

	@Results({ @Result(property = "id", column = "id"), 
			@Result(property = "cronKey", column = "cron_key"),
			@Result(property = "cronClass", column = "cron_class"),
			@Result(property = "cronExpression", column = "cron_expression"),
			@Result(property = "taskExplain", column = "task_explain"),
			@Result(property = "status", column = "status") })
	@Select("select * from scheduled_cron where cron_class = #{cornClass}")
	public ScheduledCron findByCronClass(String cornClass);

}
