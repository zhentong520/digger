package com.ko30.service.lotStatistics.handler;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ko30.cache.RedisCacheUtil;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.SpringUtils;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.LotDrawCodeStatistic;

@Service
public class DrawCodeStatisticsHelper {
	
	private Logger logger=Logger.getLogger(DrawCodeStatisticsHelper.class);
	
	private final Integer INIT_MISS_NUMBER_COUNT=-10000;
	
	/***** 同一期中连续出现2 次 连续遗漏值  -20000 *****/
	private final String NUMBER_SHOW_2_TIMES_COUNT="-20000";
	
	/***** 同一期中连续出现3 次 连续遗漏值  -30000 *****/
	private final String NUMBER_SHOW_3_TIMES_COUNT="-30000";
	
	private RedisCacheUtil<Object> redisCacheUtil;
	
	public DrawCodeStatisticsHelper(){
		redisCacheUtil=SpringUtils.getBean(RedisCacheUtil.class);
	}
	
	/**
	 * 最小开奖号码
	 */
	private int OPEN_MIN_NUMBER=1;
	
	/**
	 * 
	* @Title: setStatisticFirstArea 
	* @Description: 设置前区统计 
	* @param @param list
	* @param @param minNumber
	* @param @param maxNumber
	* @param @param specialNumberLen
	* @param @param numberMissCountMap
	* @param @return    设定文件 
	* @return List<LotDrawCodeStatistic>    返回类型 
	* @throws
	 */
	public List<LotDrawCodeStatistic> setStatisticFirstArea(String lotType,
			List<AppLotHistory> list, int minNumber, int maxNumber,
			int specialNumberLen, Map<Integer, Integer> numberMissCountMap,
			int rows) {

		this.OPEN_MIN_NUMBER = minNumber;// 设置最小开奖号码
		return this.setStatisticFirstArea(lotType,list, maxNumber, specialNumberLen,numberMissCountMap,rows);
	}
	
	/**
	 * 
	 * @Title: setStatisticFirstArea
	 * @Description: 获取列表前区统计
	 * @param @param list
	 * @param @param maxNumber
	 * @param @param specialNumberLen
	 * @param @param numberMissCountMap
	 * @param @return 设定文件
	 * @return List<LotDrawCodeStatistic> 返回类型
	 * @throws
	 */
	public List<LotDrawCodeStatistic> setStatisticFirstArea(String lotType,
			List<AppLotHistory> list, int maxNumber, int specialNumberLen,
			Map<Integer, Integer> numberMissCountMap, int rows) {

		List<LotDrawCodeStatistic>	result = Lists.newArrayList();
		
		// 1、遍历集合中每期中奖记录
		Map<Integer, Integer> numberShowCountMap = Maps.newConcurrentMap(); // 每个号码出现次数
		Map<Integer, Integer> maxMissingCount = Maps.newConcurrentMap(); // 每个号码最大遗漏次数
		Map<Integer, Integer> maxEvenCount = Maps.newConcurrentMap(); // 每个号码最大连出次数
		
		// 初始的开奖号码遗漏次数
		if (!AssertValue.isNotNullAndNotEmpty(numberMissCountMap)) {
			numberMissCountMap = Maps.newConcurrentMap();// 每个号码连续遗漏次数
		}
		// 初始化所有的开奖号码
		List<Integer> allOpenNumbers = Lists.newArrayList();
		for (int k = OPEN_MIN_NUMBER; k <= maxNumber; k++) {
			if (!AssertValue.isNotNull(numberMissCountMap.get(k))) {
				numberMissCountMap.put(k, INIT_MISS_NUMBER_COUNT);// 初始化所有数据数量
			}
			numberShowCountMap.put(k, 0);// 初始化对应数字出现
			maxMissingCount.put(k, 0);// 默认遗漏次数0
			maxEvenCount.put(k, 0);// 默认最大连出次数0
			allOpenNumbers.add(k);
		}
		
		// Collections.reverse(list);// 将得到的集合反转，时间从远到近
		List<AppLotHistory> targetLenLots = getTargetLenList(list, rows);
		List<AppLotHistory> newStatisticLots = Lists.newArrayList();
		for (int i = (targetLenLots.size() - 1); i >= 0; i--) {
			newStatisticLots.add(targetLenLots.get(i));
		}
		
		List<Map<String, Object>> lotList = Lists.newArrayList();
		for (AppLotHistory lot : newStatisticLots) {
			List<Integer> tempAllOpenNumbers=Lists.newArrayList();
			tempAllOpenNumbers.addAll(allOpenNumbers);
			
			// 得到当前开奖记录中的期数信息
			Map<String, Object> lotMap = Maps.newConcurrentMap();
			lotMap.put("preDrawCode", lot.getPreDrawCode());
			lotMap.put("lotCode", lot.getLotCode());
			lotMap.put("preDrawIssue", lot.getPreDrawIssue());
			lotMap.put("preDrawTime", lot.getPreDrawTime());

			// 设置其它统计信息(跨度，大小 比，区间比)
			this.getKuaduAndJiouDetail(lot.getLotCode(), lot.getPreDrawCode(),lotMap);
			
			// 2、遍历每期中奖数字
			// 获取当前开奖号码
			String[] codeStrs =lot.getPreDrawCode().split(",");
			// 出现过的数字
			for (int i = 0; i < codeStrs.length-specialNumberLen; i++) {
				int code = Integer.parseInt(codeStrs[i]);
				// 检查和开奖数字是否相等,相等：表示出现过;不相等:未出现次数累加
				int count = Integer.parseInt(numberMissCountMap.get(code).toString());
				// 得到已经保存的最大遗漏次数
				if (count > 0) {
					int missCount = maxMissingCount.get(code);
					if (count > missCount) {
						maxMissingCount.put(code, count);
					}
				}
				
				if (count == INIT_MISS_NUMBER_COUNT || count > 0) {// 初始化后的第一次出现
					numberMissCountMap.put(code, 0);
				} else if (count <= 0) {
					numberMissCountMap.put(code, count - 1);
				}
				// 移除已经 存在的数字
				tempAllOpenNumbers.remove(new Integer(code));
				
				// 得到已经出现次数
				int numberShowCount = Integer.parseInt(numberShowCountMap.get(code).toString());
				numberShowCountMap.put(code, numberShowCount+1);
				
				// 得到已经保存的最大连出次数
				count = Integer.parseInt(numberMissCountMap.get(code).toString());
				int missCount = maxEvenCount.get(code);
				if ((0 - count) + 1 > missCount) {
					maxEvenCount.put(code, (0 - count) + 1);
				}
			}

			
			// 未出现过的数字
			for (int j = 0; j < tempAllOpenNumbers.size(); j++) {
				int keyNumber = tempAllOpenNumbers.get(j);
				// 得到连续未出现次数
				int count = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				
				// 在改变值前,得到已经保存的最大连出次数
				int evenCount = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				int oldEvenCount = maxEvenCount.get(keyNumber);
				evenCount = evenCount == INIT_MISS_NUMBER_COUNT ? 1 : evenCount;
				if ((0 - evenCount) + 1 > oldEvenCount) {
					maxEvenCount.put(keyNumber, (0 - evenCount) + 1);
				}

				if (count <= 0) {// 上一次出现，这一次未出现
					numberMissCountMap.put(keyNumber, 1);
				} else {// 连续多次未出现
					numberMissCountMap.put(keyNumber, count + 1);
				}
				
				count = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				// 得到已经保存的遗漏次数
				int missCount = maxMissingCount.get(keyNumber);
				if (count >= missCount) {
					maxMissingCount.put(keyNumber, count); // 设置连出次数
				}
			}
			
			// 保存开奖号码统计
			Map<Integer, Integer> tempNumberMissCountMap=Maps.newConcurrentMap();
			tempNumberMissCountMap.putAll(numberMissCountMap);
			lotMap.put("codeDetail", tempNumberMissCountMap);
			lotList.add(lotMap);
		}
		
		// 设置其它统计信息，并放入缓存
		Map<String, Object> dataMap=doLotOtherStatisticsData(lotType, list, maxNumber, numberShowCountMap,maxMissingCount, maxEvenCount, lotList);
		Map<String, Object>  currMap=Maps.newConcurrentMap();
		currMap.put("firstArea", dataMap);
		//redisCacheUtil.setCacheMap(lotType, currMap, 72);// 设置72小时有效
		redisCacheUtil.setCacheObject(lotType+"_"+rows, JSONObject.toJSONString(currMap), 72);
		
		Iterator<Map<String, Object>> it=lotList.iterator();
		while (it.hasNext()) {
			Map<String, Object> map=it.next();
			if (AssertValue.isNotNullAndNotEmpty(map)) {
				// 跳过其它统计
				if (!map.containsKey("lotCode")) {
					continue;
				}
				LotDrawCodeStatistic statistic=new LotDrawCodeStatistic();
				statistic.setCreateTime(new Date());
				statistic.setLotCode(Integer.parseInt(map.get("lotCode")+""));
				statistic.setPreDrawCode(map.get("preDrawCode")+"");
				statistic.setPreDrawIssue(map.get("preDrawIssue")+"");
				statistic.setPreDrawTime((Date)map.get("preDrawTime"));
				// 遗漏统计map
				@SuppressWarnings("unchecked")
				Map<Integer, Integer> statisticMap=(Map<Integer, Integer>) map.get("codeDetail");
				statistic.setFirstArea(JSONObject.toJSONString(statisticMap));
				result.add(statistic);
			}
		}
		
		return result;
	}

	
	/**
	 * 
	* @Title: setStatisticCommonArea 
	* @Description: 设置公共统计开奖号码 ，指定开奖数字的位置，和长度，及指定所属区域
	* @param @param lotType
	* @param @param list
	* @param @param maxNumber
	* @param @param specialNumberLen
	* @param @param numberMissCountMap
	* @param @return    设定文件 
	* @return List<LotDrawCodeStatistic>    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<LotDrawCodeStatistic> setStatisticCommonArea(String lotType,
			List<AppLotHistory> list, int minNumber, int maxNumber,
			int beginPos, int codeLen, int targeArea,
			Map<Integer, Integer> numberMissCountMap,int rows) {
		
		List<LotDrawCodeStatistic>	result = Lists.newArrayList();
		
		// 1、遍历集合中每期中奖记录
		Map<Integer, Integer> numberShowCountMap = Maps.newConcurrentMap(); // 每个号码出现次数
		Map<Integer, Integer> maxMissingCount = Maps.newConcurrentMap(); // 每个号码最大遗漏次数
		Map<Integer, Integer> maxEvenCount = Maps.newConcurrentMap(); // 每个号码最大连出次数
		Map<String, Object> groupThreeOrSix = null; // 保存组三组六信息(针对开奖号码是3个的情况)
		
		// 初始的开奖号码遗漏次数
		if (!AssertValue.isNotNullAndNotEmpty(numberMissCountMap)) {
			numberMissCountMap = Maps.newConcurrentMap();// 每个号码连续遗漏次数
		}
		// 初始化所有的开奖号码
		List<Integer> allOpenNumbers = Lists.newArrayList();
		for (int k = minNumber; k <= maxNumber; k++) {
			if (!AssertValue.isNotNull(numberMissCountMap.get(k))) {
				numberMissCountMap.put(k, INIT_MISS_NUMBER_COUNT);// 初始化所有数据数量
			}
			numberShowCountMap.put(k, 0);// 初始化对应数字出现
			maxMissingCount.put(k, 0);// 默认遗漏次数0
			maxEvenCount.put(k, 0);// 默认最大连出次数0
			allOpenNumbers.add(k);
		}
		
		// 从最长的集合中得到目标长度集合
		List<AppLotHistory> targetLenLots=getTargetLenList(list, rows);
//		Collections.reverse(list);// 将得到的集合反转，时间从远到近
		List<AppLotHistory> newStatisticLots = Lists.newArrayList();
		for (int i = (targetLenLots.size() - 1); i >= 0; i--) {
			newStatisticLots.add(targetLenLots.get(i));
		}
		
		List<Map<String, Object>> lotList = Lists.newArrayList();
		for (AppLotHistory lot : newStatisticLots) {
			List<Integer> tempAllOpenNumbers=Lists.newArrayList();
			tempAllOpenNumbers.addAll(allOpenNumbers);
			
			// 得到当前开奖记录中的期数信息
			Map<String, Object> lotMap = Maps.newConcurrentMap();
			lotMap.put("preDrawCode", lot.getPreDrawCode());
			lotMap.put("lotCode", lot.getLotCode());
			lotMap.put("preDrawIssue", lot.getPreDrawIssue());
			lotMap.put("preDrawTime", lot.getPreDrawTime());
			lotMap.put("numberSum", AssertValue.isNotNull(lot.getSumNum())?lot.getSumNum():"" );// 开奖数字和值
//			CommUtil.formatDate(lot.getPreDrawTime(),"yyyy-MM-dd HH:mm:ss")
			
			// 2、遍历每期中奖数字
			// 获取当前开奖号码
			String openCodeStr=lot.getPreDrawCode();
			if (AssertValue.isNotNullAndNotEmpty(openCodeStr))// 检查开奖号码不为空
			{
				String[] codeStrs = openCodeStr.split(",");
				// 检查开奖号码是否是3个,封装组3组六信息,确保只执行一次
				if (codeStrs.length == 3 && targeArea == 1) {
					lotMap = setGroupThreeAndSixData(groupThreeOrSix, lot,codeStrs, lotMap);
				}
				// 设置其它统计信息
				this.getKuaduAndJiouDetail(lot.getLotCode(), lot.getPreDrawCode(),lotMap);
				int targetLen=(beginPos-1)+codeLen;// 检查指定的开奖长度是否合理
				if (targetLen>codeStrs.length) {
					targetLen=codeStrs.length;
				}
				Map<Integer, Integer> doubleShowNumMap=Maps.newConcurrentMap();	// 保存一期中重复出现号码，及次数
				// 出现过的数字
				for (int i = (beginPos-1); i <targetLen ; i++) {
					
					// 得到开奖号码，与其上一次的遗漏次数
					int code = Integer.parseInt(codeStrs[i]);
					
					// 防止重复移除（允许相同号码的彩种）
					if (tempAllOpenNumbers.contains(new Integer(code))) {
						tempAllOpenNumbers.remove(new Integer(code));// 移除已经 存在即开奖的数字
					}
					
					// 统计结果中选别数字说明：
					// -10000：该号码无统计初始数据
					// -20000*: 本期连续出现2次，*号为历史连续出现次数，实际显示按（0-*）+1；
					// -30000*：本期中出现3次，*号为历史连续出现次数，实际显示按（0-*）+1；
					// 获得当前号码在本期中出现的次数(针对开奖号码会重复出现的彩种)
					Integer doubleShowNum = doubleShowNumMap.get(code);
					if (AssertValue.isNotNull(doubleShowNum)) {
						doubleShowNum = doubleShowNum + 1;
					} else {
						doubleShowNum = 1;
					}
					doubleShowNumMap.put(code, doubleShowNum);
					
					// 在重复出现的情况下，连续遗漏统计只进行一次
					doubleShowNum = doubleShowNumMap.get(code);// 保存后的当前号码次数
					if (doubleShowNum > 1) // 重复出现2次，3次
					{
						// 当前最新的遗漏次数
						int count = Integer.parseInt(numberMissCountMap.get(code).toString());
						String countStr=String.valueOf(count);
						String newCountStr=null;
						// 上期中连续且重复出现
						if (countStr.startsWith(NUMBER_SHOW_2_TIMES_COUNT)) {
							newCountStr = countStr.replace(NUMBER_SHOW_2_TIMES_COUNT, "");
						} else if (countStr.startsWith(NUMBER_SHOW_3_TIMES_COUNT)) {
							newCountStr = countStr.replace(NUMBER_SHOW_3_TIMES_COUNT, "");
						}
						if (AssertValue.isNotNullAndNotEmpty(newCountStr)) {
							count = 0 - Integer.parseInt(newCountStr);
						}
						
						String tempMissCountStr=null;//以字符串形式保存新的连出信息
						if (doubleShowNum==2) {
							// 以字符串的形式拼接
							tempMissCountStr=NUMBER_SHOW_2_TIMES_COUNT+Math.abs(count);
						}else {
							tempMissCountStr=NUMBER_SHOW_3_TIMES_COUNT+Math.abs(count);
						}
						numberMissCountMap.put(code, Integer.parseInt(tempMissCountStr));
					} else // 统计不重复出现号码
					{
						this.statisticsNorepeatCode(numberMissCountMap,numberShowCountMap, maxMissingCount, maxEvenCount,code);
					}
					
				}

				
				// 未出现过的数字
				for (int j = 0; j < tempAllOpenNumbers.size(); j++) {
					int keyNumber = tempAllOpenNumbers.get(j);
					// 得到连续未出现次数
					int count = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
					// 在改变值前,得到已经保存的最大连出次数
					int evenCount = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
					// 将特殊标识移除"-20000*" 或 "-30000*"
					String evenCountStr=String.valueOf(evenCount);
					if (evenCountStr.contains(NUMBER_SHOW_2_TIMES_COUNT)) {
						evenCountStr = evenCountStr.replace(NUMBER_SHOW_2_TIMES_COUNT, "");
					} else if (evenCountStr.contains(NUMBER_SHOW_3_TIMES_COUNT)) {
						evenCountStr = evenCountStr.replace(NUMBER_SHOW_3_TIMES_COUNT, "");
					}
					
					
					// 在改变值前,得到已经保存的最大连出次数
					evenCount = Integer.parseInt(evenCountStr);
					int oldEvenCount = maxEvenCount.get(keyNumber);
					evenCount = evenCount == INIT_MISS_NUMBER_COUNT ? 1 : evenCount;
					if ((0 - evenCount) + 1 > oldEvenCount) {
						maxEvenCount.put(keyNumber, (0 - evenCount) + 1);
					}

					if (count <= 0) {// 上一次出现，这一次未出现
						numberMissCountMap.put(keyNumber, 1);
					} else {// 连续多次未出现
						numberMissCountMap.put(keyNumber, count + 1);
					}
					
					count = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
					// 得到已经保存的遗漏次数
					int missCount = maxMissingCount.get(keyNumber);
					if (count >= missCount) {
						maxMissingCount.put(keyNumber, count); // 设置连出次数
					}
				}
				
				// 设置开奖号重复出现统计
				lotMap=this.setDoubleSignCount(numberMissCountMap, lotMap);
				
				// 保存开奖号码统计
				Map<Integer, Integer> tempNumberMissCountMap=Maps.newConcurrentMap();
				tempNumberMissCountMap.putAll(numberMissCountMap);
				lotMap.put("codeDetail", tempNumberMissCountMap);
				lotList.add(lotMap);
			}
			
		}
		
		// 检查缓存中是否有对应的key, 设置其它统计信息，并放入缓存
//		Map<String, Object>  oldDataMap=redisCacheUtil.getCacheMap(lotType);
		String oldDataMapStr=redisCacheUtil.getCacheObject(lotType+"_"+rows);
		Map<String, Object> oldDataMap=Maps.newConcurrentMap();
		if (AssertValue.isNotNullAndNotEmpty(oldDataMapStr)) {
			oldDataMap=JSONObject.parseObject(oldDataMapStr, HashMap.class);
		}
		//Map<String, Object> dataMap=doLotOtherStatisticsData(lotType, list, minNumber,maxNumber, numberShowCountMap,maxMissingCount, maxEvenCount, lotList);
		Map<String, Object> dataMap=doLotOtherStatisticsData(lotType, targetLenLots, minNumber,maxNumber, numberShowCountMap,maxMissingCount, maxEvenCount, lotList);
		
		// 放入指定字段
		String targetFiledName=this.getTargetName(targeArea);
		oldDataMap.put(targetFiledName, dataMap);
		//redisCacheUtil.setCacheMap(lotType, oldDataMap, 72);// 设置72小时有效
		redisCacheUtil.setCacheObject(lotType+"_"+rows, JSONObject.toJSONString(oldDataMap), 72);
		
		Iterator<Map<String, Object>> it=lotList.iterator();
		while (it.hasNext()) {
			Map<String, Object> map=it.next();
			if (AssertValue.isNotNullAndNotEmpty(map)) {
				// 跳过其它统计
				if (!map.containsKey("lotCode")) {
					continue;
				}
				
				LotDrawCodeStatistic statistic=new LotDrawCodeStatistic();
				statistic.setCreateTime(new Date());
				statistic.setLotCode(Integer.parseInt(map.get("lotCode")+""));
				statistic.setPreDrawCode(map.get("preDrawCode")+"");
				statistic.setPreDrawIssue(map.get("preDrawIssue")+"");
				statistic.setPreDrawTime((Date)map.get("preDrawTime"));
				// 遗漏统计map
				Map<Integer, Integer> statisticMap=(Map<Integer, Integer>) map.get("codeDetail");
				// 将值放入指定字段中
				this.setFiledValue(statistic, targetFiledName, JSONObject.toJSONString(statisticMap));
				result.add(statistic);
			}
		}
		
		return result;
	}

	/**
	 * 
	* @Title: statisticsNorepeatCode 
	* @Description: 统计不重复出现号码
	* @param @param numberMissCountMap
	* @param @param numberShowCountMap
	* @param @param maxMissingCount
	* @param @param maxEvenCount
	* @param @param code    当前开奖号码
	* @return void    返回类型 
	* @throws
	 */
	private void statisticsNorepeatCode(
			Map<Integer, Integer> numberMissCountMap,
			Map<Integer, Integer> numberShowCountMap,
			Map<Integer, Integer> maxMissingCount,
			Map<Integer, Integer> maxEvenCount, int code) {
		
		int count = Integer.parseInt(numberMissCountMap.get(code).toString());
		// 得到已经保存的最大遗漏次数
		if (count > 0) {
			int missCount = maxMissingCount.get(code);
			if (count > missCount) {
				maxMissingCount.put(code, count);
			}
		}
		
		
		// 统计结果中选别数字说明：（以字符串显示拼接而成的数字）
		// -10000：该号码无统计初始数据
		// -20000*: 本期连续出现2次，*号为历史连续出现次数，实际显示按（0-*）+1；
		// -30000*：本期中出现3次，*号为历史连续出现次数，实际显示按（0-*）+1；
		if (count == INIT_MISS_NUMBER_COUNT || count > 0)// 初始化后的第一次出现,或者在存在遗漏后出现
		{
			numberMissCountMap.put(code, 0);
		} else if (count <= 0) //当前为连续出现的情况下
		{
			String countStr=String.valueOf(count);
			String newCountStr=null;
			// 上期中连续且重复出现
			if (countStr.startsWith(NUMBER_SHOW_2_TIMES_COUNT)) {
				newCountStr = countStr.replace(NUMBER_SHOW_2_TIMES_COUNT, "");
			} else if (countStr.startsWith(NUMBER_SHOW_3_TIMES_COUNT)) {
				newCountStr = countStr.replace(NUMBER_SHOW_3_TIMES_COUNT, "");
			}
			if (AssertValue.isNotNullAndNotEmpty(newCountStr)) {
				count = 0 - Integer.parseInt(newCountStr);
			}
			numberMissCountMap.put(code, count - 1);
		}

		// 得到已经出现次数
		int numberShowCount = Integer.parseInt(numberShowCountMap.get(code).toString());
		numberShowCountMap.put(code, numberShowCount+1);
		
		// 得到已经保存的最大连出次数（在此次完成统计之后）
		count = Integer.parseInt(numberMissCountMap.get(code).toString());
		/*
		String countStr = count + "";
		// 将特殊标识移除"-20000*" 或 "-30000*"
		if (countStr.contains(NUMBER_SHOW_2_TIMES_COUNT)) {
			countStr = countStr.replace(NUMBER_SHOW_2_TIMES_COUNT, "");
		} else if (countStr.contains(NUMBER_SHOW_3_TIMES_COUNT)) {
			countStr = countStr.replace(NUMBER_SHOW_3_TIMES_COUNT, "");
		}
		count = Integer.parseInt(countStr);
		*/
		int evenCount = maxEvenCount.get(code);// 得到旧的最大连出次数
		int newEvenCount = (0 - count) + 1;// 得到当前的连出次数
		if (newEvenCount > evenCount) {
			maxEvenCount.put(code, newEvenCount);
		}
	}

	/**
	 * 
	* @Title: setDoubleSignCount 
	* @Description:设置重号次数 
	* @param @param numberMissCountMap
	* @param @param lotMap
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	private Map<String, Object> setDoubleSignCount(Map<Integer, Integer> numberMissCountMap,
			Map<String, Object> lotMap) {
		// 设置重号个数(与上期相同号的个数)
		int doubleSignCount = 0;
		Iterator<Integer> doubleSignCountIt = numberMissCountMap.keySet().iterator();
		while (doubleSignCountIt.hasNext()) {
			Integer key = doubleSignCountIt.next();
			int missCount = numberMissCountMap.get(key);
			if (missCount < 0) {
				doubleSignCount++;
			}
			
		}
		lotMap.put("doubleSignCount", doubleSignCount);
		return lotMap;
	}

	
	/**
	 * 
	* @Title: getTargetLenList 
	* @Description: 得到目标长度的集合
	* @param @param list
	* @param @param rows
	* @param @return    设定文件 
	* @return List<AppLotHistory>    返回类型 
	* @throws
	 */
	private List<AppLotHistory> getTargetLenList(List<AppLotHistory> list, int rows) {
		
		// 得到指定记录的集合
		rows=list.size()<rows?list.size():rows;
		List<AppLotHistory> newStatisticLots = null;
		if (rows == 0) {
			newStatisticLots = list;
		} else {
			newStatisticLots=Lists.newArrayList();
			for (int i = 0; i < rows; i++) {
				newStatisticLots.add(list.get(i));
			}
		}
		return newStatisticLots;
	}

	/**
	 * 
	* @Title: setGroupThreeAndSixData 
	* @Description: 设置组三组六信息 
	* @param @param groupThreeOrSix
	* @param @param lot
	* @param @param codeStrs
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	private Map<String, Object> setGroupThreeAndSixData(
			Map<String, Object> groupThreeOrSix, AppLotHistory lot,
			String[] codeStrs,Map<String, Object> resultMap) {
		if (!AssertValue.isNotNullAndNotEmpty(groupThreeOrSix)) {
			groupThreeOrSix = Maps.newConcurrentMap();
			groupThreeOrSix.put("groupThree", 0);
			groupThreeOrSix.put("groupSix", 0);// 默认设置都不是
		}
		int showTimes=1;// 统计号码的出现次数，2次为级三，一个都没出现为组六，三个为其它
		Map<String, Integer> groupThreeOrSixMap=Maps.newConcurrentMap();
		for (int i = 0; i < codeStrs.length; i++) {
			String tempCode = codeStrs[i];
			// 统计号码出现的次数 update when 2017-11-09
			if (groupThreeOrSixMap.containsKey(tempCode)) {
				int tempShowTimes=groupThreeOrSixMap.get(tempCode)+1;
				groupThreeOrSixMap.put(tempCode, tempShowTimes);
			}else {
				groupThreeOrSixMap.put(tempCode, 1);
			}
		}
		
		// 得到出现的最大次数
		if (AssertValue.isNotNullAndNotEmpty(groupThreeOrSixMap)) {
			Iterator<String> it = groupThreeOrSixMap.keySet().iterator();
			while (it.hasNext()) {
				String key = it.next();
				int times = groupThreeOrSixMap.get(key);
				if (times > showTimes) {
					showTimes = times;
				}
			}
		}
		
		
		// 出现两次和一次都没有出现
		if (showTimes == 2) {
			groupThreeOrSix.put("groupThree", 1);
		} else if (showTimes == 1) {
			groupThreeOrSix.put("groupSix", 1);
		}
		
		resultMap.put("groupThreeOrSix", groupThreeOrSix);
		return resultMap;
	}
	
	/**
	 * 根据指定的区域ID，得到目标名字
	 */
	private String getTargetName(int targetArea) {
		String[] names = { "", "firstArea", "secondArea", "thirdArea",
						"fourthArea", "fifthArea", "sixthArea", "seventhArea",
						"eighthArea", "ninthArea" };
		if (targetArea<names.length) {
			return names[targetArea];// 指定区域从1开始
		}
		return names[0];
	}
	
	
	/**
	 * 
	* @Title: getFiledValue 
	* @Description: 为指定字段赋值
	* @param @param e
	* @param @param columnIndex
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	@SuppressWarnings("rawtypes")
	private void setFiledValue(LotDrawCodeStatistic e, String filedName,String value) {
		try {
			Class clz = e.getClass();
			Field[] fs = clz.getDeclaredFields();

			String targetNameStr = filedName;
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				String fName = f.getName();
				if (fName.startsWith(targetNameStr)) {
					f.setAccessible(true); // 设置些属性是可以访问的
					f.set(e, value);
					// Object val = f.get(e);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} // 得到此属性的值
	}
	
	
	private Map<String, Object> doLotOtherStatisticsData(String lotType,
			List<AppLotHistory> list,int minNumber, int maxNumber,
			Map<Integer, Integer> numberShowCountMap,
			Map<Integer, Integer> maxMissingCount,
			Map<Integer, Integer> maxEvenCount,
			List<Map<String, Object>> lotList){
		this.OPEN_MIN_NUMBER=minNumber;
		return this.doLotOtherStatisticsData(lotType, list, maxNumber, numberShowCountMap, maxMissingCount, maxEvenCount, lotList);
	}
	
	/**
	 * 
	* @Title: doLotOtherStatisticsData 
	* @Description: 设置其它统计号码
	* @param @param lotType
	* @param @param list
	* @param @param maxNumber
	* @param @param numberShowCountMap
	* @param @param maxMissingCount
	* @param @param maxEvenCount
	* @param @param lotList    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private Map<String, Object> doLotOtherStatisticsData(String lotType,
			List<AppLotHistory> list, int maxNumber,
			Map<Integer, Integer> numberShowCountMap,
			Map<Integer, Integer> maxMissingCount,
			Map<Integer, Integer> maxEvenCount,
			List<Map<String, Object>> lotList) {
		// 计算平均遗漏  计算公式：平均遗漏＝统计期内的总遗漏数/(出现次数+1)
		Map<Integer, Integer> averageCountMap = Maps.newConcurrentMap();	// 每个号码最大连出次数
		for (int i = OPEN_MIN_NUMBER; i <= maxNumber; i++) {
			Integer showCount = numberShowCountMap.get(i);
			if (null == showCount) {
				showCount = 0;
			}
			int avgCount=(list.size()-showCount)/(showCount+1);
			averageCountMap.put(i, avgCount);
		}
		
		Map<String, Object> otherDataMap = Maps.newConcurrentMap();
		// 开奖数字连续出现次数
		otherDataMap.put("preDrawIssue", "出现次数");
		otherDataMap.put("codeDetail", numberShowCountMap);
		lotList.add(otherDataMap);
		
		// 平均遗漏
		otherDataMap=Maps.newConcurrentMap();
		otherDataMap.put("preDrawIssue", "平均遗漏");
		otherDataMap.put("codeDetail", averageCountMap);
		lotList.add(otherDataMap);
		
		// 最大遗漏
		otherDataMap=Maps.newConcurrentMap();
		otherDataMap.put("preDrawIssue", "最大遗漏");
		otherDataMap.put("codeDetail", maxMissingCount);
		lotList.add(otherDataMap);
		
		// 最大连出
		otherDataMap=Maps.newConcurrentMap();
		otherDataMap.put("preDrawIssue", "最大连出");
		otherDataMap.put("codeDetail", maxEvenCount);
		lotList.add(otherDataMap);
		// TODO 设置到缓存中
		Map<String, Object> dataMap=Maps.newConcurrentMap();
		dataMap.put("data", lotList);
		return dataMap;
	}
	
	
	/**
	 * 
	* @Title: setStatisticSecondArea 
	* @Description: 统计二区开奖号码
	* @param @param list
	* @param @param minNumber
	* @param @param maxNumber
	* @param @param specialNumberLen
	* @param @param numberMissCountMap
	* @param @return    设定文件 
	* @return List<LotDrawCodeStatistic>    返回类型 
	* @throws
	 */
	public List<LotDrawCodeStatistic> setStatisticSecondArea(String lotType,List<AppLotHistory> list,int minNumber, int maxNumber, int specialNumberLen,
			Map<Integer, Integer> numberMissCountMap,int rows){
		
		this.OPEN_MIN_NUMBER=minNumber;
		return this.setStatisticSecondArea(lotType,list, maxNumber, specialNumberLen, numberMissCountMap, rows);
	}
	
	/**
	 * 
	* @Title: setStatisticSecondArea 
	* @Description: 设置二区统计 
	* @param @param list
	* @param @param maxNumber
	* @param @param specialNumberLen
	* @param @param numberMissCountMap
	* @param @return    设定文件 
	* @return List<LotDrawCodeStatistic>    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public List<LotDrawCodeStatistic> setStatisticSecondArea(String lotType,
			List<AppLotHistory> list, int maxNumber, int specialNumberLen,
			Map<Integer, Integer> numberMissCountMap,int rows) {
		
		List<LotDrawCodeStatistic>	result = Lists.newArrayList();
		
		// 1、遍历集合中每期中奖记录
		Map<Integer, Integer> numberShowCountMap = Maps.newConcurrentMap();	// 每个号码出现次数
		Map<Integer, Integer> maxMissingCount = Maps.newConcurrentMap();	// 每个号码最大遗漏次数
		Map<Integer, Integer> maxEvenCount = Maps.newConcurrentMap();	// 每个号码最大连出次数
		
		// 初始的开奖号码遗漏次数
		if (!AssertValue.isNotNullAndNotEmpty(numberMissCountMap)) {
			numberMissCountMap = Maps.newConcurrentMap();// 每个号码连续遗漏次数
		}
		
		List<Integer> allOpenNumbers = Lists.newArrayList();
		for (int k = OPEN_MIN_NUMBER; k <= maxNumber; k++) {
			if (!AssertValue.isNotNull(numberMissCountMap.get(k))) {
				numberMissCountMap.put(k, INIT_MISS_NUMBER_COUNT);// 初始化所有数据数量
			}
			numberShowCountMap.put(k, 0);// 初始化对应数字出现
			maxMissingCount.put(k, 0);// 默认遗漏次数0
			maxEvenCount.put(k, 0);// 默认最大连出次数0
			allOpenNumbers.add(k);
		}
		
//		Collections.reverse(list);// 将得到的集合反转
		List<AppLotHistory> targetLenLots=getTargetLenList(list, rows);
		List<AppLotHistory> newStatisticLots = Lists.newArrayList();
		for (int i = (targetLenLots.size() - 1); i >= 0; i--) {
			newStatisticLots.add(targetLenLots.get(i));
		}
		
		List<Map<String, Object>> lotList = Lists.newArrayList();
		for (AppLotHistory lot : newStatisticLots) {
			
			// 得到当前开奖记录中的期数信息
			Map<String, Object> lotMap = Maps.newConcurrentMap();
			lotMap.put("preDrawCode", lot.getPreDrawCode());
			lotMap.put("lotCode", lot.getLotCode());
			lotMap.put("preDrawIssue", lot.getPreDrawIssue());
			lotMap.put("preDrawTime", lot.getPreDrawTime());
			
			List<Integer> tempAllOpenNumbers=Lists.newArrayList();
			tempAllOpenNumbers.addAll(allOpenNumbers);
			// 2、遍历每期中奖数字
			// 获取当前开奖号码
//			String[] codeStrs = map.get("preDrawCode").toString().split(",");
			String[] codeStrs = lot.getPreDrawCode().split(",");
			// 出现过的数字
			for (int i = (codeStrs.length-specialNumberLen); i < codeStrs.length; i++) {
				int code = Integer.parseInt(codeStrs[i]);
				// 检查和开奖数字是否相等,相等：表示出现过;不相等:未出现次数累加
				int count = Integer.parseInt(numberMissCountMap.get(code).toString());
				
				// 得到已经保存的最大遗漏次数
				if (count > 0) {
					int missCount = maxMissingCount.get(code);
					if (count > missCount) {
						maxMissingCount.put(code, count);
					}
				}
				
				if (count == INIT_MISS_NUMBER_COUNT || count > 0) {// 初始化后的第一次出现
					numberMissCountMap.put(code, 0);
				} else if (count <= 0) {
					numberMissCountMap.put(code, count - 1);
				}
				// 移除已经 存在的数字
				tempAllOpenNumbers.remove(new Integer(code));
				
				// 得到已经出现次数
				int numberShowCount = Integer.parseInt(numberShowCountMap.get(code).toString());
				numberShowCountMap.put(code, numberShowCount+1);
				
				// 得到已经保存的最大连出次数
				count = Integer.parseInt(numberMissCountMap.get(code).toString());
				int missCount = maxEvenCount.get(code);
				if ((0 - count) + 1 > missCount) {
					maxEvenCount.put(code, (0 - count) + 1);
				}
			}

			
			// 未出现过的数字
			for (int j = 0; j < tempAllOpenNumbers.size(); j++) {
				int keyNumber = tempAllOpenNumbers.get(j);
				// 得到连续未出现次数
				int count = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				
				// 在改变值前,得到已经保存的最大连出次数
				int evenCount = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				int oldEvenCount = maxEvenCount.get(keyNumber);
				evenCount = evenCount == INIT_MISS_NUMBER_COUNT ? 1 : evenCount;
				if ((0 - evenCount) + 1 > oldEvenCount) {
					maxEvenCount.put(keyNumber, (0 - evenCount) + 1);
				}

				if (count <= 0) {// 上一次出现，这一次未出现
					numberMissCountMap.put(keyNumber, 1);
				} else {// 连续多次未出现
					numberMissCountMap.put(keyNumber, count + 1);
				}
				
				count = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				// 得到已经保存的遗漏次数
				int missCount = maxMissingCount.get(keyNumber);
				if (count >= missCount) {
					maxMissingCount.put(keyNumber, count); // 设置连出次数
				}
			}
			
			// 保存开奖号码统计
			Map<Integer, Integer> tempNumberMissCountMap=Maps.newConcurrentMap();
			tempNumberMissCountMap.putAll(numberMissCountMap);
			lotMap.put("codeDetail", tempNumberMissCountMap);
			lotList.add(lotMap);
		}
		
		// 设置其它统计信息，并放入缓存
		Map<String, Object> dataMap=doLotOtherStatisticsData(lotType, list, maxNumber, numberShowCountMap,maxMissingCount, maxEvenCount, lotList);
		// 先取出存入的其它区的号码
		//Map<String, Object> oldDataMap=redisCacheUtil.getCacheMap(lotType);
		String typeName=lotType+"_"+rows;
		String oldDataMapStr=redisCacheUtil.getCacheObject(typeName);
		Map<String, Object> oldDataMap=JSONObject.parseObject(oldDataMapStr, HashMap.class);
		oldDataMap.put("secondArea", dataMap);
//		redisCacheUtil.setCacheMap(lotType, oldDataMap, 72);// 设置72小时有效
		redisCacheUtil.setCacheObject(typeName, JSONObject.toJSONString(oldDataMap), 72);
		
		Iterator<Map<String, Object>> it=lotList.iterator();
		while (it.hasNext()) {
			Map<String, Object> map=it.next();
			if (AssertValue.isNotNullAndNotEmpty(map)) {
				// 跳过其它统计
				if (!map.containsKey("lotCode")) {
					continue;
				}
				LotDrawCodeStatistic statistic=new LotDrawCodeStatistic();
				statistic.setCreateTime(new Date());
				statistic.setLotCode(Integer.parseInt(map.get("lotCode")+""));
				statistic.setPreDrawCode(map.get("preDrawCode")+"");
				statistic.setPreDrawIssue(map.get("preDrawIssue")+"");
				statistic.setPreDrawTime((Date)map.get("preDrawTime"));
				// 遗漏统计map
				Map<Integer, Integer> statisticMap=(Map<Integer, Integer>) map.get("codeDetail");
				statistic.setSecondArea(JSONObject.toJSONString(statisticMap));
				result.add(statistic);
			}
		}
		
		return result;
	}
	
	
	
	
	/**
	 * 
	 * @Title: getLotteryKuaduAndJiouData
	 * @Description: 获取开奖记录的跨度及奇偶信息
	 * @param @return 设定文件
	 * @return List<Map<String,Object>> 返回类型
	 * @throws
	 */
	public List<Map<String, Object>> getLotteryKuaduAndJiouData(List<AppLotHistory> list, int maxNumber, int specialNumberLen) {

		if (!AssertValue.isNotNullAndNotEmpty(list)) {
			return Lists.newArrayList();
		}

		Map<String, Object> lotMap = null;
		List<Map<String, Object>> maps = Lists.newArrayList();
		Iterator<AppLotHistory> it = list.iterator();
		while (it.hasNext()) {
			AppLotHistory lot = it.next();
			lotMap = Maps.newConcurrentMap();
			lotMap.put("preDrawCode", lot.getPreDrawCode());// 上期开奖号码
			lotMap = this.getKuaduAndJiouDetail(lot.getLotCode(),lot.getPreDrawCode(), lotMap);
			maps.add(lotMap);
		}
		return maps;
	}
	
	
	/**
	 * 
	* @Title: getKuaduAndJiouDetail 
	* @Description: 组装 （默认蓝球是一个）
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	private Map<String, Object> getKuaduAndJiouDetail(Integer lotCode,String drawCodeStr,Map<String, Object> lotMap) {
		int specialNumLen=1;
		switch (lotCode) {
		// 11选5没有特殊号码
		case 10006:
		case 10008:// 山东11选5（山东十一运夺金）
		case 10015:
		case 10016:
		case 10017:
		case 10018:
		case 10019:
		case 10020:
		case 10022:
		case 10023:
		case 10024:
		case 10025:
		case 10045:// 10045 七星彩没有其它特殊球
			specialNumLen=0;
			break;
		case 10040:	// 10040 大乐透2个蓝球
			specialNumLen=2;
			break;

		}
		// 默认一个蓝球用的参数
		return this.getRatioInfo(lotCode,drawCodeStr,lotMap, specialNumLen);
	}

	/**
	 * 
	* @Title: getRatioInfo 
	* @Description: 得到开奖号码的比率信息 
	* @param @param lotMap
	* @param @param codeList
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	private Map<String, Object> getRatioInfo(Integer lotCode,String drawCodeStr,Map<String, Object> lotMap,int specialNumLen) {
		
		List<Integer> codeList = Lists.newArrayList();
		String[] codes = drawCodeStr.split(",");
		// 将字符串转换成数字(减去特殊号码个数)
		for (int i = 0; i < codes.length; i++) {
			if (i < (codes.length - specialNumLen)) {
				codeList.add(Integer.parseInt(codes[i]));
			}
		}
		
		if (!AssertValue.isNotNull(lotMap)) {
			lotMap = Maps.newConcurrentMap();
		}
		// 进行排序
		codeList.sort(new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if (o1.intValue() < o2.intValue()) {
					return 1;
				} else if (o1.intValue() == o2.intValue()) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		
		int sigleNumCount=0, doubleNumCount=0;// 得到开奖号码中的奇偶数
		int bigNumCount=0,smallNumCount=0;// 得到开奖号码中的大小区间的号码个数
		int FrontNumCount=0,middleNumCount=0,backNumCount=0;// 前中后区号码个数
		for (Integer code : codeList) {
			// 奇偶统计
			if (code%2==0) {
				doubleNumCount++;
			}else {
				sigleNumCount++;
			}
			
			switch (lotCode) {
			// 11选5 为1-11，大小比与时时彩不同
			case 10006:
			case 10008:// 山东11选5（山东十一运夺金）
			case 10015:
			case 10016:
			case 10017:
			case 10018:
			case 10019:
			case 10020:
			case 10022:
			case 10023:
			case 10024:
			case 10025:
				// 11选5 把0-5分为小，6-11分为大，然后统计出每个号的大小属性为当期的出号的个数比
				if (code<=5) {
					smallNumCount++;
				}else if (code<=11) {
					bigNumCount++;
				}
				break;
			default:	// 10040 大乐透2个蓝球
				// 时时彩:大小统计 把0-4分为小，5-9分为大，然后统计出每个号的大小属性为当期的出号的个数比
				if (code<=4) {
					smallNumCount++;
				}else if (code<=9) {
					bigNumCount++;
				}
				break;

			}
			
			// 区间统计 把红球分成三个区，然后统计出每个区当期的出号的个数比。
			if (code<=10) {
				FrontNumCount++;
			}else if (code<=20) {
				middleNumCount++;
			}else {
				backNumCount++;
			}
			
		}
		// 得到跨度值
		int spanVal=codeList.get(0).intValue()-codeList.get(codeList.size()-1).intValue();
		lotMap.put("spanVal", spanVal);
		lotMap.put("singleDoubleRatio", sigleNumCount+" : "+doubleNumCount);// 得到奇偶比
		lotMap.put("bigSmallRatio", bigNumCount+" : "+smallNumCount);// 大小比
		lotMap.put("areaRatio", FrontNumCount+" : "+middleNumCount+" : "+backNumCount);// 区间比
		return lotMap;
	}
	
	
	
	
	
	
	
	
	
	
	//*******************************  以下方法，暂时忽略   *******************************************
	
	/**
	 * 
	* @Title: assembleOtherFrontData 
	* @Description: 前区数据统计
	* @param @param list
	* @param @param maxNumber
	* @param @param specialNumberLen
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public  List<Map<String, Object>> assembleOtherFrontData(List<AppLotHistory> list, int maxNumber, int specialNumberLen,Map<Integer, Integer> numberMissCountMap){
		
		// 1、遍历集合中每期中奖记录
		Map<Integer, Integer> numberShowCountMap = Maps.newConcurrentMap(); // 每个号码出现次数
		Map<Integer, Integer> maxMissingCount = Maps.newConcurrentMap(); // 每个号码最大遗漏次数
		Map<Integer, Integer> maxEvenCount = Maps.newConcurrentMap(); // 每个号码最大连出次数
		
		// 初始的开奖号码遗漏次数
		if (!AssertValue.isNotNullAndNotEmpty(numberMissCountMap)) {
			numberMissCountMap = Maps.newConcurrentMap();// 每个号码连续遗漏次数
		}
		// 初始化所有的开奖号码
		List<Integer> allOpenNumbers = Lists.newArrayList();
		for (int k = OPEN_MIN_NUMBER; k <= maxNumber; k++) {
			if (!AssertValue.isNotNull(numberMissCountMap.get(k))) {
				numberMissCountMap.put(k, INIT_MISS_NUMBER_COUNT);// 初始化所有数据数量
			}
			numberShowCountMap.put(k, 0);// 初始化对应数字出现
			maxMissingCount.put(k, 0);// 默认遗漏次数0
			maxEvenCount.put(k, 0);// 默认最大连出次数0
			allOpenNumbers.add(k);
		}
		
		Collections.reverse(list);// 将得到的集合反转
		// 保存封装完成的数据
		List<Map<String, Object>> dataList = Lists.newArrayList();
		
		// 将开奖列表放入map中
		Map<String, Object> lotListMap = Maps.newConcurrentMap();
		List<Map<String, Object>> lotList = Lists.newArrayList();
		for (AppLotHistory lot : list) {
			List<Integer> tempAllOpenNumbers=Lists.newArrayList();
			tempAllOpenNumbers.addAll(allOpenNumbers);
			
			// 得到当前开奖记录中的期数信息
			Map<String, Object> lotMap=Maps.newConcurrentMap();
			lotMap.put("firstColumn", lot.getPreDrawIssue());
			
			// 2、遍历每期中奖数字
			// 获取当前开奖号码
			String[] codeStrs =lot.getPreDrawCode().split(",");
			// 出现过的数字
			for (int i = 0; i < codeStrs.length-specialNumberLen; i++) {
				int code = Integer.parseInt(codeStrs[i]);
				// 检查和开奖数字是否相等,相等：表示出现过;不相等:未出现次数累加
				int count = Integer.parseInt(numberMissCountMap.get(code).toString());
				// 得到已经保存的最大遗漏次数
				if (count > 0) {
					int missCount = maxMissingCount.get(code);
					if (count > missCount) {
						maxMissingCount.put(code, count);
					}
				}
				
				if (count == INIT_MISS_NUMBER_COUNT || count > 0) {// 初始化后的第一次出现
					numberMissCountMap.put(code, 0);
				} else if (count <= 0) {
					numberMissCountMap.put(code, count - 1);
				}
				// 移除已经 存在的数字
				tempAllOpenNumbers.remove(new Integer(code));
				
				// 得到已经出现次数
				int numberShowCount = Integer.parseInt(numberShowCountMap.get(code).toString());
				numberShowCountMap.put(code, numberShowCount+1);
				
				// 得到已经保存的最大连出次数
				count = Integer.parseInt(numberMissCountMap.get(code).toString());
				int missCount = maxEvenCount.get(code);
				if ((0 - count) + 1 > missCount) {
					maxEvenCount.put(code, (0 - count) + 1);
				}
			}

			
			// 未出现过的数字
			for (int j = 0; j < tempAllOpenNumbers.size(); j++) {
				int keyNumber = tempAllOpenNumbers.get(j);
				// 得到连续未出现次数
				int count = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				
				// 在改变值前,得到已经保存的最大连出次数
				int evenCount = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				int oldEvenCount = maxEvenCount.get(keyNumber);
				evenCount = evenCount == INIT_MISS_NUMBER_COUNT ? 1 : evenCount;
				if ((0 - evenCount) + 1 > oldEvenCount) {
					maxEvenCount.put(keyNumber, (0 - evenCount) + 1);
				}

				if (count <= 0) {// 上一次出现，这一次未出现
					numberMissCountMap.put(keyNumber, 1);
				} else {// 连续多次未出现
					numberMissCountMap.put(keyNumber, count + 1);
				}
				
				count = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				// 得到已经保存的遗漏次数
				int missCount = maxMissingCount.get(keyNumber);
				if (count >= missCount) {
					maxMissingCount.put(keyNumber, count); // 设置连出次数
				}
			}
			
			// 保存开奖号码统计
			Map<Integer, Integer> tempNumberMissCountMap=Maps.newConcurrentMap();
			tempNumberMissCountMap.putAll(numberMissCountMap);
			lotMap.put("codeDetail", tempNumberMissCountMap);
			lotList.add(lotMap);
		}
		// 将记录列表保存在集合中
		lotListMap.put("lotterys", lotList);
		dataList.add(lotListMap);
		
		// 计算平均遗漏  计算公式：平均遗漏＝统计期内的总遗漏数/(出现次数+1)
		Map<Integer, Integer> averageCountMap = Maps.newConcurrentMap();	// 每个号码最大连出次数
		for (int i = OPEN_MIN_NUMBER; i <= maxNumber; i++) {
			int showCount=numberShowCountMap.get(i);
			int avgCount=(list.size()-showCount)/(showCount+1);
			averageCountMap.put(i, avgCount);
		}
		
		Map<String, Object> otherDataMap = Maps.newConcurrentMap();
		// 开奖数字连续出现次数
		otherDataMap.put("firstColumn", "出现次数");
		otherDataMap.put("codeDetail", numberShowCountMap);
		dataList.add(otherDataMap);
		
		// 平均遗漏
		otherDataMap=Maps.newConcurrentMap();
		otherDataMap.put("firstColumn", "平均遗漏");
		otherDataMap.put("codeDetail", averageCountMap);
		dataList.add(otherDataMap);
		
		// 最大遗漏
		otherDataMap=Maps.newConcurrentMap();
		otherDataMap.put("firstColumn", "最大遗漏");
		otherDataMap.put("codeDetail", maxMissingCount);
		dataList.add(otherDataMap);
		
		// 最大连出
		otherDataMap=Maps.newConcurrentMap();
		otherDataMap.put("firstColumn", "最大连出");
		otherDataMap.put("codeDetail", maxEvenCount);
		dataList.add(otherDataMap);
	
		return dataList;
	}

	
	/**
	 * 
	* @Title: assembleOtherBehindData 
	* @Description: 后区数据统计
	* @param @param list
	* @param @param maxNumber
	* @param @param specialNumberLen
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public List<Map<String, Object>> assembleOtherBehindData(List<AppLotHistory> list, int maxNumber, int specialNumberLen,Map<Integer, Integer> numberMissCountMap) {
		
		// 1、遍历集合中每期中奖记录
		Map<Integer, Integer> numberShowCountMap = Maps.newConcurrentMap();	// 每个号码出现次数
		Map<Integer, Integer> maxMissingCount = Maps.newConcurrentMap();	// 每个号码最大遗漏次数
		Map<Integer, Integer> maxEvenCount = Maps.newConcurrentMap();	// 每个号码最大连出次数
		
		// 初始的开奖号码遗漏次数
		if (!AssertValue.isNotNullAndNotEmpty(numberMissCountMap)) {
			numberMissCountMap = Maps.newConcurrentMap();// 每个号码连续遗漏次数
		}
		
		List<Integer> allOpenNumbers = Lists.newArrayList();
		for (int k = OPEN_MIN_NUMBER; k <= maxNumber; k++) {
			if (!AssertValue.isNotNull(numberMissCountMap.get(k))) {
				numberMissCountMap.put(k, INIT_MISS_NUMBER_COUNT);// 初始化所有数据数量
			}
			numberShowCountMap.put(k, 0);// 初始化对应数字出现
			maxMissingCount.put(k, 0);// 默认遗漏次数0
			maxEvenCount.put(k, 0);// 默认最大连出次数0
			allOpenNumbers.add(k);
		}
		
		Collections.reverse(list);// 将得到的集合反转
		// 保存封装完成的数据
		List<Map<String, Object>> dataList=Lists.newArrayList();
		
		// 将开奖列表放入map中
		Map<String, Object> lotListMap = Maps.newConcurrentMap();
		List<Map<String, Object>> lotList = Lists.newArrayList();
		for (AppLotHistory lot : list) {
			
			// 得到当前开奖记录中的期数信息
			Map<String, Object> lotMap=Maps.newConcurrentMap();
			lotMap.put("firstColumn", lot.getPreDrawIssue());
			
			List<Integer> tempAllOpenNumbers=Lists.newArrayList();
			tempAllOpenNumbers.addAll(allOpenNumbers);
			// 2、遍历每期中奖数字
			// 获取当前开奖号码
//			String[] codeStrs = map.get("preDrawCode").toString().split(",");
			String[] codeStrs = lot.getPreDrawCode().split(",");
			// 出现过的数字
			for (int i = (codeStrs.length-specialNumberLen); i < codeStrs.length; i++) {
				int code = Integer.parseInt(codeStrs[i]);
				// 检查和开奖数字是否相等,相等：表示出现过;不相等:未出现次数累加
				int count = Integer.parseInt(numberMissCountMap.get(code).toString());
				
				// 得到已经保存的最大遗漏次数
				if (count > 0) {
					int missCount = maxMissingCount.get(code);
					if (count > missCount) {
						maxMissingCount.put(code, count);
					}
				}
				
				if (count == INIT_MISS_NUMBER_COUNT || count > 0) {// 初始化后的第一次出现
					numberMissCountMap.put(code, 0);
				} else if (count <= 0) {
					numberMissCountMap.put(code, count - 1);
				}
				// 移除已经 存在的数字
				tempAllOpenNumbers.remove(new Integer(code));
				
				// 得到已经出现次数
				int numberShowCount = Integer.parseInt(numberShowCountMap.get(code).toString());
				numberShowCountMap.put(code, numberShowCount+1);
				
				// 得到已经保存的最大连出次数
				count = Integer.parseInt(numberMissCountMap.get(code).toString());
				int missCount = maxEvenCount.get(code);
				if ((0 - count) + 1 > missCount) {
					maxEvenCount.put(code, (0 - count) + 1);
				}
			}

			
			// 未出现过的数字
			for (int j = 0; j < tempAllOpenNumbers.size(); j++) {
				int keyNumber = tempAllOpenNumbers.get(j);
				// 得到连续未出现次数
				int count = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				
				// 在改变值前,得到已经保存的最大连出次数
				int evenCount = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				int oldEvenCount = maxEvenCount.get(keyNumber);
				evenCount = evenCount == INIT_MISS_NUMBER_COUNT ? 1 : evenCount;
				if ((0 - evenCount) + 1 > oldEvenCount) {
					maxEvenCount.put(keyNumber, (0 - evenCount) + 1);
				}

				if (count <= 0) {// 上一次出现，这一次未出现
					numberMissCountMap.put(keyNumber, 1);
				} else {// 连续多次未出现
					numberMissCountMap.put(keyNumber, count + 1);
				}
				
				count = Integer.parseInt(numberMissCountMap.get(keyNumber).toString());
				// 得到已经保存的遗漏次数
				int missCount = maxMissingCount.get(keyNumber);
				if (count >= missCount) {
					maxMissingCount.put(keyNumber, count); // 设置连出次数
				}
			}
			
			// 保存开奖号码统计
			Map<Integer, Integer> tempNumberMissCountMap=Maps.newConcurrentMap();
			tempNumberMissCountMap.putAll(numberMissCountMap);
			lotMap.put("codeDetail", tempNumberMissCountMap);
			lotList.add(lotMap);
		}
		// 将记录列表保存在集合中
		lotListMap.put("lotterys", lotList);
		dataList.add(lotListMap);
		
		// 计算平均遗漏
		Map<Integer, Integer> averageCountMap = Maps.newConcurrentMap();	// 每个号码最大连出次数
		for (int i = OPEN_MIN_NUMBER; i <= maxNumber; i++) {
			int showCount=numberShowCountMap.get(i);
			int avgCount=(list.size()-showCount)/showCount;
			averageCountMap.put(i, avgCount);
		}
		
		Map<String, Object> otherDataMap = Maps.newConcurrentMap();
		// 开奖数字连续出现次数
		otherDataMap.put("firstColumn", "出现次数");
		otherDataMap.put("codeDetail", numberShowCountMap);
		dataList.add(otherDataMap);
		
		// 平均遗漏
		otherDataMap=Maps.newConcurrentMap();
		otherDataMap.put("firstColumn", "平均遗漏");
		otherDataMap.put("codeDetail", averageCountMap);
		dataList.add(otherDataMap);
		
		// 最大遗漏
		otherDataMap=Maps.newConcurrentMap();
		otherDataMap.put("firstColumn", "最大遗漏");
		otherDataMap.put("codeDetail", maxMissingCount);
		dataList.add(otherDataMap);
		
		// 最大连出
		otherDataMap=Maps.newConcurrentMap();
		otherDataMap.put("firstColumn", "最大连出");
		otherDataMap.put("codeDetail", maxEvenCount);
		dataList.add(otherDataMap);
		
		return dataList;
	}
	
	
	
	
	public static void main(String[] args) {
		
//		new AssembleLotterData().assembleOtherFrontData(lotDatas, 33, 1);
//		new AssembleLotterData().assembleOtherBehindData(lotDatas, 16, 1);
	}


}
