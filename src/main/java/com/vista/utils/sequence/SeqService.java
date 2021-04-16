package com.vista.utils.sequence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SeqService {

	
	private static final Logger logger = LoggerFactory.getLogger(SeqService.class);

	// TODO 生成流水每日自动刷新保存
	// TODO 定义templet以curVal结尾
	
	@Autowired
	private SeqMapper sqlMapper;

	@Cacheable(value="sequence",key="#moduleCode")
//	@CachePut(value = "sequence", key = "#moduleCode")
	public Sequence findSequence(String moduleCode) {
		return sqlMapper.find(moduleCode);		
	}
	
	@CacheEvict(value="sequence",key="#moduleCode")
	public int save(String maxSerial,String moduleCode) {
		return sqlMapper.save(maxSerial, moduleCode);	
	}
	
	@Cacheable(value="templet",key="#moduleCode")
	public String  getTemplet(String moduleCode) {
		return sqlMapper.getTemplet(moduleCode);
	}
	
	
	

	public static void main(String[] args) {
		String templet1 = "G${date}${time}1000${curVal}"; // 5+14=19 32-19=13
		String templet = "G${date}1000${curVal}"; // 5+8=13
		String templet2 = "G${time}1000${curVal}";
		String templet4 = "G1000${curVal}";
		// 获取

		Integer seqLength = 32;
		// 判断是否含日期、时间
		boolean isContainsDate = templet.contains("date") ? true : false;
		boolean isContainsTime = templet.contains("time") ? true : false;
		// 获取curVal长度
		int curValIndex = templet.indexOf("${curVal}");
		System.out.println(curValIndex);
		int curValLength = 0;
		if (isContainsDate) {
			curValLength = isContainsTime ? seqLength - curValIndex : seqLength - curValIndex - 1;
		} else {
			curValLength = isContainsTime ? seqLength - curValIndex + 1 : seqLength - curValIndex;
		}
		System.out.println(curValLength);
		String patten = "%013d";
		String seq = String.format(patten, 20);
		System.out.println(seq);
		
		
		
	}
	


}
