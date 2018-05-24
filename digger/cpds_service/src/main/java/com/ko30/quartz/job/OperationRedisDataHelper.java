package com.ko30.quartz.job;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ko30.cache.RedisCacheUtil;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.JsonUtil;
import com.ko30.common.util.SpringUtils;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;


/**
 * 
* @ClassName: OperationRedisDataHelper 
* @Description: 操作redis数据工具辅助类 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月21日 下午12:28:17 
*
 */
@SuppressWarnings("unchecked")
public class OperationRedisDataHelper {

	private final static  Logger logger=Logger.getLogger(OperationRedisDataHelper.class);
	
	private static RedisCacheUtil<Object> redisCacheUtil;
	
	static{
		redisCacheUtil = SpringUtils.getBean(RedisCacheUtil.class);
	}


	/**
	 * 
	* @Title: getMapData 
	* @Description: 获取指定key值的值，并以map形式返回 
	* @param @param key
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	public static Map<String, Object> getMapData(String key) {

		// 数据保存格式
		//{"seatSeq_1":{"2017103":{"hitCnt":1,"hitNum":[24],"termNo":2017103,"killRedNum":[11,23,24]}}}
		Map<String, Object> map = Maps.newConcurrentMap();
		String dataStr=null;
		try {
			dataStr = redisCacheUtil.getCacheObject(key);
			if (AssertValue.isNotNullAndNotEmpty(dataStr)) {
				map = JsonUtil.parseObject(dataStr, HashMap.class);
			}
		} catch (Exception e) {
			logger.info("redis数据转json异常------>>>",e);
			logger.info("发生错误的key:"+key);
		}

		return map;
	}
	
	
	/**
	 * 
	* @Title: setLotDvnResult 
	* @Description: 将开奖结果与测序结果作对比，给出最终预测正确率,杀红球(即为开奖号码不会出现的号)【不分红球与蓝球的情况】
	* @param @param dataKey redis中存储的key
	* @param @param lot     当前的开奖信息
	* @return void    返回类型 
	* @throws
	 */
	public static void setLotkillNumDvnResult(String dataKey,AppLotHistory lot){

		Map<String, Object> map=null;
		try {
			// 存在正确的开奖号码
			if (AssertValue.isNotNull(lot) && AssertValue.isNotNullAndNotEmpty(lot.getPreDrawCode())) {
				map = getMapData(dataKey);
				// 如果没有数据
				if (!AssertValue.isNotNullAndNotEmpty(map)) {
					return;
				}
				
				// {"2017103":{"hitCnt":1,"hitNum":[24],"termNo":2017103,"killRedNum":[11,23,24]}} (专家预测结果)
				// 得到存储值中的每一个席位（专家）
				for (Entry<String, Object> e : map.entrySet()) {
					keepRecodLen(map);// 检查历史记录长度
					
					String key=e.getKey();// 得到当前专家（key）
					// 得到每个专家指定期数据的预测结果
					String devResultsStr = JSON.toJSONString(e.getValue());
					if (!AssertValue.isNotNullAndNotEmpty(devResultsStr)) {
						continue;
					}
					Map<String, Map<String, Object>> devResultsMap = JsonUtil.parseObject(devResultsStr, HashMap.class);
					changeDvnHistoryData(dataKey, lot, map, key,devResultsMap);
				}
				// 重新保存进redis中
				redisCacheUtil.setCacheObject(dataKey,JSON.toJSONString(map),96);
			}
		} catch (Exception e) {
			logger.info("将开奖结果与测序结果作对比，给出最终预测正确率异常",e);
		}
		
	}
	
	/**
	 * 
	 * @Title: setNewestKillNumDvnResult
	 * @Description: 设置最新一期开奖对应的预测信息中的开奖结果
	 * @param @param dataKey
	 * @param @param lot 设定文件
	 * @param @param lot 特殊号码长度，从末位开始
	 * @return void 返回类型
	 * @throws
	 */
	public static void setNewestOpenLotKillNumDvnResult(String dataKey,AppLotHistory lot,Integer specNumLen){
		
		Map<String, Object> map=null;
		try {
			// 存在正确的开奖号码
			if (AssertValue.isNotNull(lot) && AssertValue.isNotNullAndNotEmpty(lot.getPreDrawCode())) {
				//{
				//	"lotShortName":"体彩排列三","planName":"时光十位杀二码",
				//	"lotCode":"CN_PL3","planCode":"POS2_KILL02","issueNo":"17266",
				//	"issueTime":"2017-09-28 20:30:00","lotDvnCont":[ {"seatSeq":1,"killRedNum":[0,2],"hot":48,"percent":0.24}]
				//}
				dataKey=dataKey+"_"+lot.getPreDrawIssue();// 取得对应的杀码信息
				map = getMapData(dataKey);
				if (!AssertValue.isNotNullAndNotEmpty(map)) {// 如果没有数据
					return;
				}
				
				// 得到专家预测列表
				List<Map<String, Object>> lotDvnContList=(List<Map<String, Object>>) map.get("lotDvnCont");
				Iterator<Map<String, Object>> dvnContIt=lotDvnContList.iterator();
				while (dvnContIt.hasNext()) {
					Map<String, Object> tempMap = dvnContIt.next();
					// 修改指定记录的预测结果信息
					if (null == specNumLen || specNumLen == 0) {
						changeNewestDvnKillNumInfo4OnePos(dataKey, tempMap, lot);
					} else {
						if (specNumLen == -1) {
							changeNewestDvnKillNumInfo4MorePos(dataKey, tempMap, lot,0);;// 有两种颜色的球，但是仍要统计的情况（七乐彩）
						}else {
							changeNewestDvnKillNumInfo4MorePos(dataKey, tempMap, lot,specNumLen);
						}
					}
				}
				map.put("lotDvnCont", lotDvnContList);
				// 重新保存进redis中
				redisCacheUtil.setCacheObject(dataKey, JsonUtil.toJSONString(map), 24*10);
			}
		} catch (Exception e) {
			logger.info("设置最新一期开奖对应的预测信息中的开奖结果异常",e);
			logger.info("异常时获取到的redis中的数据------->>>");
			logger.info(map);
		}
	}
	
	/**
	 * 
	 * @Title: changeNewestDvnKillNumInfo
	 * @Description: 修改对应记录操作，填入最终的预测结果（与开奖号码对比结果）,指定杀某个位置的开奖号码
	 * @param @param paramMap
	 * @param @param lot
	 * @param @return 设定文件
	 * @return Map<String,Object> 返回类型
	 * @throws
	 */
	private static Map<String, Object> changeNewestDvnKillNumInfo4OnePos(String dataKey,Map<String, Object> paramMap,AppLotHistory lot){
		
		String drawCode=lot.getPreDrawCode();
		// 得到指定位置的开奖号码
		String targetCodeStr=getOneTargetLotDrawCodeByKey(dataKey, drawCode);
		// 得到杀的号码
		List<Integer> dvnCodeList=null;
		if (paramMap.containsKey("killRedNum")) {
			dvnCodeList = (List<Integer>) paramMap.get("killRedNum");
		} else if (paramMap.containsKey("killNum")) {
			dvnCodeList = (List<Integer>) paramMap.get("killNum");
		}else if (paramMap.containsKey("killBlueNum")) {
			dvnCodeList = (List<Integer>) paramMap.get("killBlueNum");
		}
		
		int hitCnt = dvnCodeList.size();// 杀中的号码个数
		List<Integer> hitNum=Lists.newArrayList();
		Integer targetCode = Integer.parseInt(targetCodeStr);
		
		// 默认测中全部
		hitNum.addAll(dvnCodeList);
		if (dvnCodeList.contains(targetCode)) {
			hitCnt--;
			hitNum.remove(targetCode);
		}
		paramMap.put("hitCnt", hitCnt);
		paramMap.put("hitNum", hitNum);
		paramMap.put("drawCode", drawCode);
		
		return paramMap;
	}
	
	
	/**
	 * 
	 * @Title: changeNewestDvnKillNumInfo4MorePos
	 * @Description: 获取
	 * @param @param dataKey
	 * @param @param paramMap
	 * @param @param lot
	 * @param @param specNumLen
	 * @param @return 设定文件
	 * @return Map<String,Object> 返回类型
	 * @throws
	 */
	private static Map<String, Object> changeNewestDvnKillNumInfo4MorePos(String dataKey,Map<String, Object> paramMap,AppLotHistory lot,int specNumLen){
		
		// 得到预测位数的号码
		String drawCode = lot.getPreDrawCode();// 开奖号码
		// 得到指定位置的开奖号码
		List<String> targetCodeStrs=getTargetLotDrawCodesByKey(dataKey, drawCode,specNumLen);
		// 得到杀的号码
		List<Integer> dvnCodeList=Lists.newArrayList();
		if (paramMap.containsKey("killRedNum")) {
			dvnCodeList = (List<Integer>) paramMap.get("killRedNum");
		} else if (paramMap.containsKey("killNum")) {
			dvnCodeList = (List<Integer>) paramMap.get("killNum");
		}else if (paramMap.containsKey("killBlueNum")) {
			dvnCodeList = (List<Integer>) paramMap.get("killBlueNum");
		}
		
		int hitCnt = dvnCodeList.size();// 杀中的号码个数
		List<Integer> hitNum = Lists.newArrayList();
		hitNum.addAll(dvnCodeList);// 设置默认全中
		for (String tempTargetCodeStr : targetCodeStrs) {
			Integer tempTargetCode = Integer.parseInt(tempTargetCodeStr);
			// 包含开奖号码 ，表示杀错码
			if (dvnCodeList.contains(tempTargetCode)) {
				hitCnt--;
				hitNum.remove(tempTargetCode);
			}
		}

		paramMap.put("hitCnt", hitCnt);
		paramMap.put("hitNum", hitNum);
		paramMap.put("drawCode", drawCode);

		return paramMap;
	}
	
	
	/**
	 * 
	 * @Title: setLotLuckNumDvnResult
	 * @Description: 设置幸运号码中的开奖结果
	 * @param @param dataKey
	 * @param @param lot 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public static void setLotLuckNumDvnResult(String dataKey,AppLotHistory lot){

		// 存在正确的开奖号码
		if (AssertValue.isNotNull(lot)&& AssertValue.isNotNullAndNotEmpty(lot.getPreDrawCode())) {
			Map<String, Object> map = getMapData(dataKey);
			// 如果没有数据
			if (!AssertValue.isNotNullAndNotEmpty(map)) {
				return;
			}
			map.put("drawCode", lot.getPreDrawCode());// 设置开奖结果
			// 重新保存进redis中
			redisCacheUtil.setCacheObject(dataKey, JsonUtil.toJSONString(map), 24*10);
		}
	}

	/**
	 * 
	* @Title: changDvnHistoryData 
	* @Description: 为专家预测结果填充开奖信息
	* @param @param dataKey redis中存储的key名
	* @param @param lot 当前开奖信息
	* @param @param map 指定预测类型的历史
	* @param @param key map中专家席位
	* @param @param devResultsMap    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private static void changeDvnHistoryData(String dataKey, AppLotHistory lot,
			Map<String, Object> map, String key,
			Map<String, Map<String, Object>> devResultsMap) {
		
		// 结果：{"hitCnt":1,"hitNum":[24],"termNo":2017103,"killRedNum":[11,23,24]}
		// 结果：{"hitCnt":1,"hitNum":[24],"termNo":2017103,"killNum":[11,23,24]}  // 修改了杀号数组名称
		String preDrawIssue = lot.getPreDrawIssue();// 当前开奖期号
		Map<String, Object> devResultMap = devResultsMap.get(preDrawIssue);
		if (!AssertValue.isNotNullAndNotEmpty(devResultMap)) {// 没有找到对应存储的值
			return;
		}
		
		// 日志出
		logger.info("修改前专家预测历史， 期数："+preDrawIssue);
		
		
		// 得到被杀的号码
		List<Integer> dvnNumbers=null;
		if (devResultMap.containsKey("killRedNum")) {
			dvnNumbers=JSONObject.parseArray(devResultMap.get("killRedNum").toString(), Integer.class);
		}else if (devResultMap.containsKey("killNum")) {
			dvnNumbers=JSONObject.parseArray(devResultMap.get("killNum").toString(), Integer.class);
		}
		
		// 得到预测位数的号码
		String drawCode = lot.getPreDrawCode();// 开奖号码
		String targetPositionCode=getOneTargetLotDrawCodeByKey(dataKey, drawCode);
		int hitCnt = dvnNumbers.size();// 测中个数
		List<Integer> hitNum = Lists.newArrayList();// 测中数字
		hitNum.addAll(dvnNumbers);// 默认全部测中
		
		// 因为数字7 与开奖号07区别，故不能字符串比较
		Integer tempCode=Integer.valueOf(targetPositionCode);
		if (dvnNumbers.contains(tempCode)) {
			hitCnt--;
			hitNum.remove(tempCode);// 移除测中的
		}
		
		
		// 更新对应的预测结果
		devResultMap.put("hitCnt", hitCnt);
		devResultMap.put("hitNum", hitNum);
		devResultMap.put("drawCode", drawCode);// 设置开奖号码
		
		//修改后日志输出
		logger.info("修改后专家预测历史， 期数："+preDrawIssue);
		// 保存更新后的结果
		devResultsMap.put(preDrawIssue, devResultMap);
		// 将预测结果历史倒序，按开奖期数由近到远。
		SortedMap<String, Object> sortMap = new TreeMap<String, Object>(
				new Comparator<String>() {
					@Override
					public int compare(String arg1, String arg2) {
						return arg2.compareTo(arg1);
					}
				});
		
		sortMap.putAll(devResultsMap);
		map.put(key, sortMap);
	}


	/**
	 * 
	* @Title: keepRecodLen 
	* @Description: 保持map长度 
	* @param @param map
	* @param @return    设定文件 
	* @return Map<String,Object>    返回类型 
	* @throws
	 */
	private static Map<String, Object> keepRecodLen(Map<String, Object> map) {

		// 检查历史长度，暂且保持其长度100
		SortedMap<String, Object> sortMap = new TreeMap<String, Object>(map);
		if (map.size() > 100) {
			Set<String> keySet = sortMap.keySet();
			Iterator<String> it = keySet.iterator();
			while (it.hasNext()) {
				if (map.size() <= 100) {
					break;
				}
				String key = it.next();
				map.remove(key);
			}
		}
		return map;
	}
	
	
	private static final String KEY_SEPARATER="_";// 指定的分隔符
	private static final String KEY_POSITION_CHAR="POS";// 指定位数的符串
	
	/**
	 * 
	* @Title: getOneTargetLotDrawCodeByKey 
	* @Description: 根据key名，获取指定位置的开奖号码(每个位数上的一个号码)  范例：FC3D_SHIWEI_HOT_2017258
	* @param @param key
	* @param @param preDrawCode
	* @param @return    设定文件 
	* @return String    返回类型 
	* @throws
	 */
	public static String getOneTargetLotDrawCodeByKey(String key,String preDrawCode) {
		
		if (!AssertValue.isNotNullAndNotEmpty(preDrawCode)) {
			return "";
		}
		
		String[] codeStrs=preDrawCode.split(",");
		String targetPositionValue=null;// 开奖号码的位置
		if (AssertValue.isNotNullAndNotEmpty(key) && key.contains(KEY_SEPARATER)) {
			String[] keyStrs = key.split(KEY_SEPARATER);
			if (!AssertValue.isNotNullAndNotEmpty(keyStrs)) {
				return "";
			}
			
			int codeIndex=-1;
			for (String keyStr : keyStrs) {
				if (keyStr.contains(KEY_POSITION_CHAR)) {
					String codeIndexStr=keyStr.replace(KEY_POSITION_CHAR, "");
					codeIndex= Integer.parseInt(codeIndexStr);
					if (key.contains("TC_7XC")) {// 七星彩不用反转顺序
						// 与 百、十、个 对应的顺序
						codeIndex=codeIndex-1;//POS1即是指：开奖的第一位
					}else {
						codeIndex=codeStrs.length-codeIndex;
					}
					break;
				}
			}
			targetPositionValue = codeStrs[codeIndex];
			// 得到目标位数的开奖号码 1 2 3 4
			/**
			int codeStrsLen=codeStrs.length;
			switch (targetKey) {
			case "POS01":// 个位
				targetPositionValue = codeStrs[codeStrsLen - 1];
				break;
			case "POS02":// 十位
				targetPositionValue = codeStrs[codeStrsLen - 2];
				break;
			case "POS03":// 百位
				targetPositionValue = codeStrs[codeStrsLen - 3];
				break;
			}
			*/
		}
		return targetPositionValue;
	}
	
	
	
	/**
	 * 
	 * @Title: getTargetLotDrawCodesByKey
	 * @Description: 取得指定区域的开奖号
	 * @param @param key
	 * @param @param preDrawCode 开奖号码
	 * @param @param specNumLen  特殊号码长度
	 * @param @return 设定文件
	 * @return List<String> 目标开奖号码
	 * @throws
	 */
	private static List<String> getTargetLotDrawCodesByKey(String key,String preDrawCode,int specNumLen) {
		
		if (!AssertValue.isNotNullAndNotEmpty(preDrawCode)) {
			return Lists.newArrayList();
		}

		String[] codeStrs = preDrawCode.split(",");
		if (codeStrs.length <= specNumLen) {// 特殊号码长度不正常
			return Lists.newArrayList();
		}
		
		List<String> targetValues=Lists.newArrayList();// 得到的目标开奖号码
		if (AssertValue.isNotNullAndNotEmpty(key) && key.contains(KEY_SEPARATER)) {
			String[] keyStrs = key.split(KEY_SEPARATER);
			if (!AssertValue.isNotNullAndNotEmpty(keyStrs)) {
				return Lists.newArrayList();
			}
			
			int codeIndex=-1;
			for (String keyStr : keyStrs) {
				// 得到指定杀号的位置
				if (keyStr.contains(KEY_POSITION_CHAR)) {
					codeIndex= Integer.parseInt(keyStr.replace(KEY_POSITION_CHAR, ""));
					// 1：红球；2：蓝球
					if (1 == codeIndex) {
						for (int i = 0; i < codeStrs.length - specNumLen; i++) {
							targetValues.add(codeStrs[i]);
						}
					} else if (2 == codeIndex) {
						for (int i = (codeStrs.length - specNumLen); i < codeStrs.length; i++) {
							targetValues.add(codeStrs[i]);
						}
					}
					break;
				}
			}
		}
		return targetValues;
	}
	
	/**
	 * 
	 * @Title: setLotkillRedNumDvnResult
	 * @Description: 填充预测信息中的开奖结果信息(有特殊号码，蓝球长度，POS1指红球，POS2指蓝球)
	 * @param @param dataKey redis中存储的key
	 * @param @param lot 当前的开奖信息
	 * @param @param specialNumberLen 特殊号码的长度，默认指的是从最末位开始
	 * @return void 返回类型
	 * @throws
	 */
	public static void setLotkillNumDvnResult(String dataKey,AppLotHistory lot,Integer specialNumberLen){

		// 存在正确的开奖号码
		if (AssertValue.isNotNull(lot) && AssertValue.isNotNullAndNotEmpty(lot.getPreDrawCode())) {
	
			Map<String, Object> map = getMapData(dataKey);
			// 如果没有数据
			if (!AssertValue.isNotNullAndNotEmpty(map)) {
				return;
			}
			
			// {"2017103":{"hitCnt":1,"hitNum":[24],"termNo":2017103,"killRedNum":[11,23,24]}} (专家预测结果)
			// 得到存储值中的每一个席位（专家）
			for (Entry<String, Object> e : map.entrySet()) {
				keepRecodLen(map);// 检查历史记录长度
				
				String key=e.getKey();// 得到当前专家（key）
				// 得到每个专家指定期数据的预测结果
				String devResultsStr = JSON.toJSONString(e.getValue());
				if (!AssertValue.isNotNullAndNotEmpty(devResultsStr)) {
					continue;
				}
				Map<String, Map<String, Object>> devResultsMap = JSONObject.parseObject(devResultsStr, HashMap.class);
				// 指定区域的预测结果（红球，或者蓝球）
				changDvnHistoryData(dataKey, lot, map, key,devResultsMap,specialNumberLen);
			}
			// 重新保存进redis中
			redisCacheUtil.setCacheObject(dataKey,JSON.toJSONString(map),96);
		}
	}

	/**
	 * 
	* @Title: changDvnHistoryData 
	* @Description: 为专家预测结果填充开奖信息
	* @param @param dataKey redis中存储的key名
	* @param @param lot 当前开奖信息
	* @param @param map 指定预测类型的历史
	* @param @param key map中专家席位
	* @param @param devResultsMap    设定文件 
	* @param @param specialNumberLen    特殊号码长度
	* @return void    返回类型 
	* @throws
	 */
	private static void changDvnHistoryData(String dataKey, AppLotHistory lot,
			Map<String, Object> map, String key,
			Map<String, Map<String, Object>> devResultsMap,int specialNumberLen) {
		
		// 结果：{"hitCnt":1,"hitNum":[24],"termNo":2017103,"killRedNum":[11,23,24]}
		// 结果：{"hitCnt":1,"hitNum":[24],"termNo":2017103,"killNum":[11,23,24]}  // 修改了杀号数组名称
		String preDrawIssue = lot.getPreDrawIssue();// 当前开奖期号
		Map<String, Object> devResultMap = devResultsMap.get(preDrawIssue);
		if (!AssertValue.isNotNullAndNotEmpty(devResultMap)) {// 没有找到对应存储的值
			return;
		}
		
		// 得到被杀的号码
		List<Integer> dvnNumbers=null;
		if (devResultMap.containsKey("killRedNum")) {
			dvnNumbers=JSONObject.parseArray(devResultMap.get("killRedNum").toString(), Integer.class);
		}else if (devResultMap.containsKey("killNum")) {
			dvnNumbers=JSONObject.parseArray(devResultMap.get("killNum").toString(), Integer.class);
		}else if (devResultMap.containsKey("killBlueNum")) {
			dvnNumbers=JSONObject.parseArray(devResultMap.get("killBlueNum").toString(), Integer.class);
		}
		
		// 得到预测位数的号码
		String drawCode = lot.getPreDrawCode();// 开奖号码
		// 预测位数的开奖号码
		List<String> targetPositionCodes=getTargetLotDrawCodesByKey(dataKey, drawCode,specialNumberLen);
		int hitCnt = dvnNumbers.size();// 测中个数
		List<Integer> hitNum = Lists.newArrayList();// 测中数字
		hitNum.addAll(dvnNumbers);// 设置默认全中
		
		for (String targetCodeStr : targetPositionCodes) {
			Integer targetCode=Integer.parseInt(targetCodeStr);
			// 检查杀码是否正确,如果包含，则表示杀错了。
			if (dvnNumbers.contains(targetCode)) {
				hitCnt--;
				hitNum.remove(targetCode);
			}
		}
		
		
		// 更新对应的预测结果
		devResultMap.put("hitCnt", hitCnt);
		devResultMap.put("hitNum", hitNum);
		devResultMap.put("drawCode", lot.getPreDrawCode());// 设置开奖号码
		
		// 保存更新后的结果
		devResultsMap.put(preDrawIssue, devResultMap);
		// 将预测结果历史倒序，按开奖期数由近到远。
		SortedMap<String, Object> sortMap = new TreeMap<String, Object>(
				new Comparator<String>() {
					@Override
					public int compare(String arg1, String arg2) {
						return arg2.compareTo(arg1);
					}
				});
		
		sortMap.putAll(devResultsMap);
		map.put(key, sortMap);
	}
	
	
}
