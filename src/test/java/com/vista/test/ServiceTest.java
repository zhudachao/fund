package com.vista.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.vista.service.FundService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ServiceTest {
	
	@Autowired
	private FundService service;
	
	@Test
	public void test() {
		//连接数据库测试
		List<String> catagorys =service.queryCatagorys();
		System.out.println(catagorys);
	}
	

}
