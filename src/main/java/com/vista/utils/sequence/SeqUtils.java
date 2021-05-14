package com.vista.utils.sequence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.vista.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @ClassName: SeqTool
 * @Description: 流水生成工具类
 * @author zdchao
 * @date 2021年1月6日
 *
 */
@Component
public class SeqUtils {

	private static final Logger logger = LoggerFactory.getLogger(SeqUtils.class);

	@Autowired
	private SeqService seqService;

	private static final ReentrantLock lock = new ReentrantLock();
	private static final ReentrantLock preLock = new ReentrantLock();
	/** 预生成流水号 */
	private static final HashMap<String, List<String>> prepareSeqMap = new HashMap<>();

	/**
	 * 
	 * @Title: getSeq @Description: 获取流水 @param @param moduleCode @param @return
	 * 参数 @return String 返回类型 @throws
	 */
	public String getSeq(String moduleCode) {
		preLock.lock();
		boolean flag = false;
		try {
			if (null != prepareSeqMap.get(moduleCode) && prepareSeqMap.get(moduleCode).size() > 0) {
				String seq = prepareSeqMap.get(moduleCode).get(0);
				flag = isResetSeq(seq, moduleCode);
				if (!flag) {
					return prepareSeqMap.get(moduleCode).remove(0);
				}
			}
			// 自动生成并缓存
			Sequence sequence = seqService.findSequence(moduleCode);
			List<String> seqList = genSeq(flag, sequence);
			prepareSeqMap.put(moduleCode, seqList);
			return prepareSeqMap.get(moduleCode).remove(0);
		} finally {
			preLock.unlock();
		}
	}

	/**
	 * 
	 * @Title: genSeq @Description: 生成流水集合 @param @param flag @param @param
	 * sequence @param @return 参数 @return List<String> 返回类型 @throws
	 */
	private List<String> genSeq(boolean flag, Sequence sequence) {
		lock.lock();
		try {
			int icreNum = sequence.getIcreNum();
			int preMaxNum = sequence.getPreMaxNum();
			String templet = sequence.getTemplet().trim();
			Long maxSerial = Long.valueOf(sequence.getMaxSerial()); // curVal 当前流水号序号最大值
			maxSerial = flag ? maxSerial - preMaxNum + 1 : maxSerial;
			int seqLength = sequence.getSeqLength();
			List<String> seqList = new ArrayList<String>(preMaxNum);
			int curValLength = interpretTemplate(templet, seqLength);

			String patten = "%0" + curValLength + "d";
			String date = DateUtil.getDate8();
			String time = DateUtil.getTime6();
			for (int i = 0; i < preMaxNum; i++) {
				// 生成流水并缓存
				if (maxSerial < maxByLength(curValLength)) {
					maxSerial += icreNum;
				} else {
					maxSerial = (long) icreNum;
				}
				String seq = templet.replace("${date}", date).replace("${time}", time).replace("${curVal}",
						String.format(patten, maxSerial));
				seqList.add(seq);
			}
			seqService.save(String.format(patten, maxSerial), sequence.getModuleCode());
			return seqList;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 
	 * @Title: interpretTemplate @Description: 解析模板类型 @param @param
	 * templet @param @param seqLength @param @return 参数 @return int 返回类型 @throws
	 */
	private int interpretTemplate(String templet, int seqLength) {
		// 判断是否含日期、时间
		boolean isContainsDate = templet.contains("date") ? true : false;
		boolean isContainsTime = templet.contains("time") ? true : false;
		// 获取curVal长度
		int curValIndex = templet.indexOf("${curVal}");
		int curValLength = 0;
		if (isContainsDate) {
			curValLength = isContainsTime ? seqLength - curValIndex : seqLength - curValIndex - 1;
		} else {
			curValLength = isContainsTime ? seqLength - curValIndex + 1 : seqLength - curValIndex;
		}
		Assert.state(curValLength > 0, "模板参数错误,流水长度应大于时间参数长度");
		return curValLength;
	}

	/**
	 * 
	 * @Title: maxByLength @Description: 获取n位整数的最大值 @param @param
	 * length @param @return 参数 @return long 返回类型 @throws
	 */
	private long maxByLength(int length) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append("9");
		}
		return Long.valueOf(sb.toString());
	}

	/**
	 * 
	 * @Title: isResetSeq @Description: 根据当前日期和时间判断是否流水重置 @param @param
	 * seq @param @param moduleCode @param @return 参数 @return boolean 返回类型 @throws
	 */
	private boolean isResetSeq(String seq, String moduleCode) {
		boolean isContainsDate = seqService.getTemplet(moduleCode).contains("date") ? true : false;
		boolean isContainsTime = seqService.getTemplet(moduleCode).contains("time") ? true : false;
		return isContainsTime ? true : (isContainsDate ? (seq.contains(DateUtil.getDate8()) ? false : true) : false);
	}

}
