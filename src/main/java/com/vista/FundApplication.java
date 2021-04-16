package com.vista;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // 开启定时任务功能
@EnableCaching //开启缓存 --cachingable
public class FundApplication {

	public static void main(String[] args) {
		SpringApplication.run(FundApplication.class, args);
	}

}
