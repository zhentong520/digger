package com.ko30.quartz.service;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ko30.cache.RedisCacheUtil;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.CommUtil;
import com.ko30.common.util.ConfigInfo;
import com.ko30.common.util.HttpClientUtil;
import com.ko30.common.util.JsonUtil;
import com.ko30.common.util.SpringUtils;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.AppLotType;
import com.ko30.quartz.job.HighFrequencyLotDvnHelper;
import com.ko30.quartz.job.HighFrequencyLotDvnHelper.HandleType;
import com.ko30.quartz.job.HighFrequencyLotDvnHelper.LotType;
import com.ko30.quartz.job.HighFrequencyLotDvnHelper.PlayType;
import com.ko30.service.lotQuartzHelper.GrabLotDataQuartzJobHelper;
import com.ko30.service.lotStatistics.handler.TrendChartService;
import com.ko30.service.lotteryInfo.LotHistoryService;
import com.ko30.service.lotteryInfo.LotTypeService;

/**
 * 
* @ClassName: GrabOpenResultQuartzJobService 
* @Description: 获取第三方彩票数据服务
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月22日 上午9:53:28 
*
 */
@Service
//@Transactional(rollbackFor = { Exception.class, RuntimeException.class, BusinessException.class })
public class GrabOpenResultService<T> {

	private static Logger logger = Logger.getLogger(GrabOpenResultService.class);

	private static String CPYZJ_DATA_CHANG_IP = ConfigInfo.get("cpyzj.change.userExpt.data.ip");//cpyzj所在ip
	private static String CPYZJ_DATA_CHANG_URL = ConfigInfo.get("cpyzj.change.userExpt.data.url");//cpyzj请求所在url
	
	private LotTypeService lotTypeService;
	
	private LotHistoryService lotHistoryService;
	
	/****** 获取最近5条历史开奖记录 ******/
	private GrabLotDataQuartzJobHelper grabLotDataQuartzJobHelper;
	
	// 易源数据
	private _2_ShowApi_GrabOpenResultService _2_service;
	
	// 开彩网数据服务
	private _3_OpenCai_GrabOpenResultService _3_service;
	
	//走势图数据处理
	private TrendChartService trendChartService;

	private static RedisCacheUtil<Object> redisCacheUtil;

	public GrabOpenResultService() {
		lotTypeService = SpringUtils.getBean(LotTypeService.class);
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		// 获取当前彩种最近5期开奖历史
		grabLotDataQuartzJobHelper = SpringUtils.getBean(GrabLotDataQuartzJobHelper.class);
		
		_2_service = SpringUtils.getBean(_2_ShowApi_GrabOpenResultService.class);
		_3_service = SpringUtils.getBean(_3_OpenCai_GrabOpenResultService.class);
		// 注入redis服务
		redisCacheUtil = SpringUtils.getBean(RedisCacheUtil.class);
		trendChartService = SpringUtils.getBean(TrendChartService.class);
	}
	
	// 旧的获取数据地址
	private final String OPENLOTTERY_URL_OLD = "http://www.c16008.com/draw/getData.html";
	private final String HONGKONG_LOTTERY_URL = "http://1680660.com/smallSix/findSmallSixInfo.do?lotCode=10048";// 香港六合彩地址
//	http://api.1680210.com/lottery/getLotteryListByCategory.do?category=1&isContainsHot=0&isUse=1&name=&lotCode=&pageNo=&pageSize=100
	// 新的获取数据地址
	private final String OPENLOTTERY_URL_NEW = ConfigInfo.get("api168.data.url");
	
	
	/**
	 * 
	* @Title: getOpenResultData 
	* @Description: 获取数据 (JSONArray 结果集)
	* @param @return    设定文件 
	* @return JSONArray    返回类型 
	* @throws
	 */
	private JSONArray getOpenResultData() {
		
		logger.info("------ 开始获取168网站数据 -------------------->>>>");
		JSONArray dataJsonArr = null;
		try {
			String contentStr = HttpClientUtil.get(OPENLOTTERY_URL_NEW);
			JSONObject contentJsonObj = JsonUtil.parseObject(contentStr);
			// 取不到对应key值的时候，为获取数据异常
			JSONObject resultjsonObj = contentJsonObj.getJSONObject("result");
			if (AssertValue.isNotNullAndNotEmpty(resultjsonObj.getString("data"))) {
				dataJsonArr = resultjsonObj.getJSONArray("data");
			}
		} catch (Exception e) {
			logger.info("抓取数据转化实体异常：" + e.getMessage());
			dataJsonArr = null;
		}
		
		return dataJsonArr;
	}
	
	
	/**
	 * 
	* @Title: getOpenResultData 
	* @Description: 根据请求地址得到数据集
	* @param @param url
	* @param @return    设定文件 
	* @return JSONArray    返回类型 
	* @throws
	 */
	private int tryCount=0;
	private synchronized JSONArray getOpenResultData(String url) {
		tryCount++;
		JSONArray dataJsonArr=new JSONArray();
		try {
			String contentStr = HttpClientUtil.get(url);
			JSONObject contentJsonObj = JSONObject.parseObject(contentStr);
			JSONObject resultjsonObj = contentJsonObj.getJSONObject("result");
			dataJsonArr = resultjsonObj.getJSONArray("data");
		} catch (Exception e) {
			if (tryCount <= 3) {
				this.getOpenResultData(url);
			} else {
				return dataJsonArr;
			}
		}

		return dataJsonArr;
	}
	
	/**
	 * 
	* @Title: getDataByLotteryType 
	* @Description: 根据彩票类型编码，获取数据列表 
	* @param @param lotteryType
	* @param @return    设定文件 
	* @return List<CommonOpenInfo>    返回类型 
	* @throws
	 */
	public List<T> getDataByLotteryCode(String lotteryCode, Class<T> clazz) {

		JSONArray dataJsonObjs = this.getOpenResultData();
		List<T> datas = null;// 保存目标类型彩票
		if (null!=dataJsonObjs && dataJsonObjs.size()>0) {
			datas = Lists.newArrayList();
			for (Object obj : dataJsonObjs) {
				// 将得到的彩票汉字名称转首字每大写
				JSONObject jsonObj = JSONObject.parseObject(obj.toString());
				String objLotName = jsonObj.getString("lotName");
				objLotName=objLotName.replace('⑥', '六');// 替换其它特殊符号
				String templotteryCode = CommUtil.getPinYinHeadChar(objLotName).toLowerCase();
				if (templotteryCode.equalsIgnoreCase(lotteryCode)) {
					T t = JSONObject.parseObject(obj.toString(), clazz);
					datas.add(t);
				}
			}
		}
		return datas;
	}
	

	/**
	 * 
	* @Title: getDataByLotCode 
	* @Description: 根据彩票类型码，获取记录列表 
	* @param @param lotCode
	* @param @param clazz
	* @param @return    设定文件 
	* @return List<JSONObject>    返回类型 
	* @throws
	 */
	public List<JSONObject> getDataByLotCode(Integer lotCode) {

		JSONArray dataJsonObjs = this.getOpenResultData();
		List<JSONObject> datas = null;// 保存目标类型彩票
		if (null!=dataJsonObjs && dataJsonObjs.size()>0) {
			datas = Lists.newArrayList();
			for (Object obj : dataJsonObjs) {
				// 将得到的彩票汉字名称转首字每大写
				JSONObject jsonObj = JSONObject.parseObject(obj.toString());
				if (lotCode == jsonObj.getInteger("lotCode")) {
					String objLotName = jsonObj.getString("lotName");
					objLotName=objLotName.replace('⑥', '六');// 替换其它特殊符号
					String tempLotAlias = CommUtil.getPinYinHeadChar(objLotName).toLowerCase();
					// 设置彩票别名
					jsonObj.put("lotAlias", tempLotAlias);
					datas.add(jsonObj);
				}
			}
		}
		return datas;
	}

	
	/**
	 * 
	 * @Title: getLotHistoryByLotCode
	 * @Description: 根据彩票类型码，以及分组码获取最新彩票列表
	 * @param @param lotCode
	 * @param @return 设定文件
	 * @return List<AppLotHistory> 返回类型
	 * @throws
	 */
	private static int lackDataCount=0;// 缺少数据的请求次数
	public List<AppLotHistory> getLotHistoryByLotCodeWithGroupId(Integer lotCode, Integer groupId) {

		JSONArray objs=null;
		/*
		//JSONArray objs = this.getOpenResultData();// 原始网址获取数据 168开奖网
		//JSONArray objs = _3_service.getDataByLotCode(lotCode);// 从 开彩网获取数据
		if (!AssertValue.isNotNullAndNotEmpty(objs)) {
			// 最新记录存在的情况下，追加最近5条（在168开奖网数据情况下）
			objs = this.getOpenResultData();// 原始网址获取数据 168开奖网
			//List<AppLotHistory> moreLots=grabLotDataQuartzJobHelper.getMoreLotHistoryData(lotCode,groupId);
			//if (AssertValue.isNotNullAndNotEmpty(moreLots)) {
				//	JSONArray mortLotsJson=JSON.parseArray(JSON.toJSONString(moreLots));
				//	objs.addAll(mortLotsJson);
				//	logger.info("------------->>>>追加5历史成功，现在的集合长度是："+mortLotsJson.size());
				//}
			//objs = _2_service.getDataByLotCode(lotCode);// 从 易源数据获取
			//objs = _3_service.getDataByLotCode(lotCode);// 从 开彩网获取数据
		} 
		 */
		
		// 少数指定彩种去168获取
		switch (lotCode) {
		// case 10010:// 澳洲幸运5
		// case 10011:// 澳洲幸运8
		// case 10012:// 澳洲幸运10
		// case 10013:// 澳洲幸运20
		// case 10009:// 重庆幸运农场
		case 10036:// 极速时时彩
		case 10037:// 极速赛车
		case 10046:// pc蛋蛋幸运28
		case 10047:// 台湾宾果
		// case 10048:// 香港彩
			// 最新记况下，追加最近5条（在168开奖网数据情况下）
			objs = this.getOpenResultData();// 原始网址获取数据 168开奖网
			List<AppLotHistory> moreLots = grabLotDataQuartzJobHelper.getMoreLotHistoryData(lotCode, groupId);
			if (AssertValue.isNotNullAndNotEmpty(moreLots)) {
				JSONArray mortLotsJson = JsonUtil.parseArray(JsonUtil.toJSONString(moreLots));
				objs.addAll(mortLotsJson);
//				logger.info(String.format("------------->>>>追加5历史成功，现在的集合长度是：%d", mortLotsJson.size()));
			}
			break;

		default:
			objs = _3_service.getDataByLotCode(lotCode);// 从 开彩网获取数据
			break;
		}
		
		
		if (!AssertValue.isNotNullAndNotEmpty(objs)) {
			return Lists.newArrayList();
		}
		
		// 得到目标集合
		return this.getTargetData(lotCode, groupId, objs);
	}

	/**
	 * 
	 * @Title: getTargetData
	 * @Description: 转换成目标实体
	 * @param @param lotCode
	 * @param @param groupId
	 * @param @param objs
	 * @param @return 设定文件
	 * @return List<AppLotHistory> 返回类型
	 * @throws
	 */
	private List<AppLotHistory> getTargetData(Integer lotCode, Integer groupId,
			JSONArray objs) {
		List<AppLotHistory> datas = null;// 保存对应的结果实体
		if (null!=objs && objs.size()>0) {
			List<AppLotHistory> list = JSONArray.parseArray(objs.toString(),AppLotHistory.class);
			if (AssertValue.isNotNullAndNotEmpty(list)) {
				Iterator<AppLotHistory> it = list.iterator();
				datas = Lists.newArrayList();
				while (it.hasNext()) {
					AppLotHistory lot = it.next();
					if (lotCode.intValue() == lot.getLotCode().intValue()) {
						String name = lot.getLotName().replace('⑥', '六');// 替换其它特殊符号
						String tempLotAlias = CommUtil.getPinYinHeadChar(name).toLowerCase();
						lot.setLotAlias(tempLotAlias);// 设置彩票别名
						lot.setLotGroupCode(groupId);
						// 修改开奖期数格式
						//this.changeLotIssueFormat(lot);
						
						// 检查获得的记录是否已经被保存
						if (!lotHistoryService.checkisExist(lot)) {
							lackDataCount=0;
							datas.add(lot);
							//break; // 允许保存多个
							
							if(Objects.nonNull(lot)){ //走势图
								trendChartService.queryLotHistoryList(lot);
							}
						}
					}
					
					// TODO  修改预测数据（2018-03-15新增）
					this.setHighFrequencyDvnResult(lot);
					
				}
			}
			
			/*
			// 如果没有获取到新的内容 ，再次请求,但不能超过4次
			if (!AssertValue.isNotNullAndNotEmpty(datas) && lackDataCount<=4) {
				lackDataCount++;
				this.getLotHistoryByLotCodeWithGroupId(lotCode,groupId);
			}
			*/
		}
		return datas;
	}
	
	
	/**
	 * 
	 * @Title: setHighFrequencyDvnResult
	 * @Description: 设置高频彩预测结果
	 * @param @param lot 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void setHighFrequencyDvnResult(AppLotHistory lot){
		
		try {
			if (AssertValue.isNotNull(lot)) {
				if (!Objects.isNull(lot.getLotGroupCode())&& 2!=lot.getLotGroupCode()) {
					// 取得当前彩种对应的英文彩种名
					LotType lotType=HighFrequencyLotDvnHelper.LotType.getByCode(lot.getLotCode());
					if (AssertValue.isNotNull(lotType)) {
						// 取得所有玩法列表
						List<PlayType> playTypes=HighFrequencyLotDvnHelper.PlayType.getBySeries(lotType.getSeries());
						Iterator<PlayType> it=playTypes.iterator();
						while (it.hasNext()) {
							PlayType tempPlayType=it.next();
							// 取得对应玩法的redis数据
							String lotEnCode=lotType.getEnCode().toUpperCase();// 彩种
							
							//1、 设置指定期数各个玩法的预测结果
							this.setDvnResultByLotIssue(lot, lotEnCode, tempPlayType);
							//2、 设置各个玩法历史记录中的预测结果
							this.setDvnHistoryResult(lot, lotEnCode, tempPlayType);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.info("------->>>开奖时设置高频彩的预测结果发生异常，",e);
		}
		
		try {
			String  changeDataUrl=CPYZJ_DATA_CHANG_IP+CPYZJ_DATA_CHANG_URL;
//			logger.info("------->>>请求修改的地址："+changeDataUrl);
			// 修改cpyzj中注册用户的预测结果（2018-03-22）
			
			String lotStr=JsonUtil.toJSON(lot).toString();
			Map<String, Object> lotMap=JsonUtil.parseObject(lotStr, HashMap.class);
			Map<String, String> newLotMap=new HashMap<String, String>();
			for (Iterator<String> keyIt=lotMap.keySet().iterator();keyIt.hasNext();) {
				String key=keyIt.next();
				newLotMap.put(key, lotMap.get(key)+"");
			}
			HttpClientUtil.post(changeDataUrl,newLotMap);
		} catch (Exception e) {
			logger.info("------->>>开奖时修改cpyzj中专家预测结果异常，",e);
		}
	}
	
	/**
	 * 
	 * @Title: setDvnResultByIssue
	 * @Description:  按期数设置各个玩法的预测结果 (add 2018-03-16)
	 * @param @param lot
	 * @param @param lotEnCode
	 * @param @param playType 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private void setDvnResultByLotIssue(AppLotHistory lot,String lotEnCode,PlayType playType){
		
		String planCode=playType.getCode().toUpperCase();//玩法
		String issue=lot.getPreDrawIssue();//期数
		String key=lotEnCode.concat("_").concat(planCode);
		key=key.concat("_").concat(issue);
		String dataStr = redisCacheUtil.getCacheObject(key);
//		logger.info("现在要修改高频彩预测数据的 key------>>"+key);
		
		// 将redis数据转换成map
		Map<String, Object> dataMap=new HashMap<String, Object>();
		if (AssertValue.isNotNullAndNotEmpty(dataStr)) {
			dataMap = JsonUtil.parseObject(dataStr, HashMap.class);
			String drawCode=lot.getPreDrawCode();
			dataMap.put("drawCode", drawCode);
			
			// 得到专家预测列表
			List<Map<String, Object>> lotDvnContList=(List<Map<String, Object>>) dataMap.get("lotDvnCont");
			if (!AssertValue.isNotNullAndNotEmpty(lotDvnContList)) {
				return;
			}
			
			// 迭代每一个席位的预测数据
			Iterator<Map<String, Object>> lotDvnContListIt=lotDvnContList.iterator();
			while (lotDvnContListIt.hasNext()) {
				// 每一个专家的预测结果
				Map<String, Object> seatSeqDataMap=lotDvnContListIt.next();
				// 得到预测的号码
				List<String> dvnNumbers=null;
				if (seatSeqDataMap.containsKey("killRedNum")) {
					dvnNumbers=JSONObject.parseArray(seatSeqDataMap.get("killRedNum").toString(), String.class);
				}else if (seatSeqDataMap.containsKey("killNum")) {
					dvnNumbers=JSONObject.parseArray(seatSeqDataMap.get("killNum").toString(), String.class);
				}
				
				HandleType handler=playType.getHandleType();
				Map<String, Object> newDataMap=handler.getData(lot.getPreDrawCode(), planCode, dvnNumbers, playType.getCodeNum());
				seatSeqDataMap.put("hitCnt", newDataMap.get("hitCnt"));
				seatSeqDataMap.put("hitNum", newDataMap.get("hitNum"));
			}
			// 重新设置修改后的结果
			dataMap.put("lotDvnCont",lotDvnContList);
			
			dataStr=JsonUtil.toJSON(dataMap).toString();
			// 重新保存进redis中
			redisCacheUtil.setCacheObject(key, dataStr, 24*10);
//			logger.info("修改指定 key　的预测数据 "+key+" 保存成功。");
		}
	}
	
	
	/**
	 * 
	 * @Title: setDvnHistoryResult
	 * @Description: 设置各种玩法历史中的预测结果
	 * @param @param lot
	 * @param @param lotEnCode
	 * @param @param playType 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private void setDvnHistoryResult(AppLotHistory lot,String lotEnCode,PlayType playType){
		String planCode=playType.getCode().toUpperCase();//玩法
		String key=lotEnCode.concat("_").concat(planCode);
		key=key.concat("_HISTORY");// 获取历史记录中的数据
		String dataStr = redisCacheUtil.getCacheObject(key);
//		logger.info("现在要修改高频彩预测数据的 key------>>"+key);
		String currentIssue=lot.getPreDrawIssue();//当前开奖的期数
		
		if (AssertValue.isNotNullAndNotEmpty(dataStr)) {
			Map<String, Map<String, Object>> dataMap=JsonUtil.parseObject(dataStr, HashMap.class);
			// 迭代所有的专家席位
			Iterator<String> seatSeqKeyIt = dataMap.keySet().iterator();
			while (seatSeqKeyIt.hasNext()) {
				String seatSeqKey = seatSeqKeyIt.next();
				// 当前专家所有的预测数据
				Map<String, Object> seatSeqDvnMap=dataMap.get(seatSeqKey);
				this.keepRecodLen(seatSeqDvnMap);// 确保长度
				
				// 将预测结果历史倒序，按开奖期数由近到远(期数由大到小)。
				SortedMap<String, Object> sortMap = new TreeMap<String, Object>(
						new Comparator<String>() {
							@Override
							public int compare(String k1, String k2) {
								return k2.compareTo(k1);
							}
						});
				sortMap.putAll(seatSeqDvnMap);
				
				//迭代指定专家的所有预测基数
				Iterator<String> dvnHisKeyIt = sortMap.keySet().iterator();
				while (dvnHisKeyIt.hasNext()) {
					String dvnHisKey=dvnHisKeyIt.next();
					if (currentIssue.equals(dvnHisKey)) {
						Map<String, Object> data=(Map<String, Object>) sortMap.get(dvnHisKey);
						
						// 得到预测的号码
						List<String> dvnNumbers=null;
						if (data.containsKey("killRedNum")) {
							dvnNumbers=JSONObject.parseArray(data.get("killRedNum").toString(), String.class);
						}else if (data.containsKey("killNum")) {
							dvnNumbers=JSONObject.parseArray(data.get("killNum").toString(), String.class);
						}
						HandleType handler=playType.getHandleType();
						Map<String, Object> newDataMap=handler.getData(lot.getPreDrawCode(), planCode, dvnNumbers, playType.getCodeNum());
						data.put("hitCnt", newDataMap.get("hitCnt"));
						data.put("hitNum", newDataMap.get("hitNum"));
						sortMap.put(dvnHisKey, data);// 设置修改后的值
						break;
					}
				}
				
				dataMap.put(seatSeqKey, sortMap);// 重新设置修改后的值
			}
			
			dataStr=JsonUtil.toJSON(dataMap).toString();
			// 重新保存进redis中
			redisCacheUtil.setCacheObject(key, dataStr, 24*10);
//			logger.info("修改高频彩预测数据的 key------>>"+key+" ，并保存成功。");
		}
		
		
	}
	
	/**
	 * 
	 * @Title: keepRecodLen
	 * @Description: 保持预测历史记录长度
	 * @param @param map
	 * @param @return 设定文件
	 * @return Map<String,Object> 返回类型
	 * @throws
	 */
	private Map<String, Object> keepRecodLen(Map<String, Object> map) {

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

	/**
	 * 
	 * @Title: changeLotIssueFormat
	 * @Description: 修改开奖记录期数格式
	 * @param @param lot 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@SuppressWarnings("unused")
	private void changeLotIssueFormat(AppLotHistory lot) {
		// 设置期数包含完整的 "2017"
		Calendar cal=Calendar.getInstance();
		String currentYear=cal.get(Calendar.YEAR)+"";
		String shortYear=currentYear.substring(2);
		String fullPreDrawIssue=lot.getPreDrawIssue();
		if (fullPreDrawIssue.contains(currentYear)) {
			fullPreDrawIssue=fullPreDrawIssue.replace(currentYear, shortYear);
			lot.setPreDrawIssue(fullPreDrawIssue);
		}
		// 修改本期期数长度
		if (AssertValue.isNotNullAndNotEmpty(lot.getDrawIssue())) {
			String drawFullPreDrawIssue=lot.getDrawIssue();
			if (drawFullPreDrawIssue.contains(currentYear)) {
				drawFullPreDrawIssue=drawFullPreDrawIssue.replace(currentYear, shortYear);
				lot.setDrawIssue(drawFullPreDrawIssue);
			}
		}
	}
	
	
	// http://api.1680210.com/lottery/getLotteryListByType.do?&type=1（高频彩）
	// http://api.1680210.com/lottery/getLotteryListByType.do?&type=2（全国彩）
	// http://api.1680210.com/lottery/getLotteryListByType.do?&type=3（境外彩）
	// http://api.1680210.com/lottery/getLotteryListByType.do?&type=4（热门彩）
	// http://api.1680210.com/lottery/getLotteryListByType.do?&type=5（香港彩）
	
	/**
	 * 
	* @Title: getLotHistoryByLotCode 
	* @Description: 全国彩最新记录 
	* @param @param lotCode
	* @param @return    设定文件 
	* @return List<AppLotHistory>    返回类型 
	* @throws
	 */
	public List<AppLotHistory> getLotHistoryByLotCode(Integer lotCode){
		return this.getLotHistoryByLotCodeWithGroupId(lotCode,2);// 分组2 为全国彩
	}
	
	
	/**
	 * 
	* @Title: getJingwaiLotHistoryByLotCode 
	* @Description: 境外彩最新记录 
	* @param @param lotCode
	* @param @return    设定文件 
	* @return List<AppLotHistory>    返回类型 
	* @throws
	 */
	public List<AppLotHistory> getJingwaiLotHistoryByLotCode(Integer lotCode){
		return this.getLotHistoryByLotCodeWithGroupId(lotCode,3);// 分组 3  为境外彩
	}
	
	/**
	 * 
	* @Title: getGaopinLotHistoryByLotCode 
	* @Description: 高频彩最新记录 
	* @param @param lotCode
	* @param @return    设定文件 
	* @return List<AppLotHistory>    返回类型 
	* @throws
	 */
	public List<AppLotHistory> getGaopinLotHistoryByLotCode(Integer lotCode){
		return this.getLotHistoryByLotCodeWithGroupId(lotCode,1);// 分组 1  为高频彩
	}
	
	
	
	/**
	 * 
	* @Title: getRemenLotHistoryByLotCode 
	* @Description: 根据彩票类型代码获取最新数据
	* @param @param lotCode
	* @param @return    设定文件 
	* @return List<AppLotHistory>    返回类型 
	* @throws
	 */
	public List<AppLotHistory> getRemenLotHistoryByLotCode(Integer lotCode){
		return this.getLotHistoryByLotCodeWithGroupId(lotCode,4);// 分组4 为热门彩
	}
	
	
	
	// 新的全国彩历史数据地址
	private String DATE_URL_1 = "http://api.1680210.com/QuanGuoCai/getHistoryLotteryInfo.do?";
	
	//10041,10043
	private String DATE_URL_2 = "http://api.1680210.com/QuanGuoCai/getLotteryInfoList.do?";
	private String params = "date=s% &lotCode=d%";// date=2016-08-20&lotCode=10041
	
	/**
	 * 
	* @Title: getQuanguoLotteryHistory 
	* @Description: 获取全国彩历史记录/彩票开奖历史
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getQuanguoLotteryHistory(Integer lotCode,String queryDateStr){
		Object[] param=new Object[2];
		String dateParam = null;
		String requestUlr="";
		dateParam = CommUtil.formatShortDate(new Date());
		if (null == lotCode) {
			param[1] = lotCode = 10039;// 彩票代码
		} else {
			param[1] = lotCode;// 彩票代码
		}
		if (null==queryDateStr) {
			param[0] = queryDateStr = dateParam;// 终止日期
		}else {
			param[0] = queryDateStr;// 终止日期
		}

		if (lotCode == 10041 || lotCode == 10043) {
			requestUlr = DATE_URL_2;
		} else {
			requestUlr = DATE_URL_1;
		}
		
		params="date="+param[0]+"&lotCode="+param[1];
		String url = requestUlr + params;
//		logger.info("新的请求：param[0]：" + param[0] + "  param[1]:" + param[1]);
//		logger.info("新的请求：url：" + url);
		
		// 请求第三方数据
		String contentStr = HttpClientUtil.get(url);
		JSONObject contentJsonObj = JSONObject.parseObject(contentStr);
		JSONObject resultjsonObj = contentJsonObj.getJSONObject("result");
		JSONArray dataJsonArr = resultjsonObj.getJSONArray("data");
		
		// 将得到的彩票汉字名称转首字每大写
		//Date endTime = CommUtil.formatDate("2016-10-01","yyyy-MM-dd");
		Date endTime = CommUtil.formatDate("2005-01-01","yyyy-MM-dd");
//		List<AppLotteryQuanguo> list=JSONArray.parseArray(dataJsonArr.toJSONString(), AppLotteryQuanguo.class);
		List<AppLotHistory> list=JSONArray.parseArray(dataJsonArr.toJSONString(), AppLotHistory.class);
		
		
		if (AssertValue.isNotNullAndNotEmpty(list)) {
			for (AppLotHistory lot : list) {
				String tempLotName = this.getQuanguoLotteryMap().get(lotCode).toString();
				lot.setLotName(tempLotName);
				if (tempLotName.contains("⑥")) {
					tempLotName = tempLotName.replace('⑥', '六');// 替换其它特殊符号
				}
				String tempLotteryAlias = CommUtil.getPinYinHeadChar(tempLotName).toLowerCase();
				lot.setLotAlias(tempLotteryAlias);
				lot.setLotCode(lotCode);
				lot.setLotGroupCode(2);// 全国彩
				// 检查各个位数和
				if (!AssertValue.isNotNull(lot.getSumNum()) && AssertValue.isNotNullAndNotEmpty(lot.getPreDrawCode())) {
					String[] codes=lot.getPreDrawCode().split(",");
					int sum = 0;
					for (String str : codes) {
						sum = sum + Integer.parseInt(str);
					}
					lot.setSumNum(sum);
				}
				
				lot.setCreateTime(new Date());
				// 如果是列表中的最后一条，抓取下一页，或是抓取另一种彩票类型列表
				if (list.indexOf(lot) == list.size() - 1) {
					Calendar cal=Calendar.getInstance();
					cal.setTime(lot.getPreDrawTime());
					// 时间往后一天
					cal.add(Calendar.DATE, -1);
					queryDateStr = CommUtil.formatShortDate(cal.getTime());
					// 查到 16年1月1日止,开始抓取下一种彩票类型
					if (!lot.getPreDrawTime().after(endTime)) {
						queryDateStr = null;
						param[1] = lotCode = (int) (param[1]) + 1;
					}
				}
			}

			/***
			for (AppLotteryQuanguo lot : list) {
				String tempLotName = this.getQuanguoLotteryMap().get(lotCode).toString();
				lot.setLotName(tempLotName);
				if (tempLotName.contains("⑥")) {
					tempLotName = tempLotName.replace('⑥', '六');// 替换其它特殊符号
				}
				String tempLotteryAlias = CommUtil.getPinYinHeadChar(tempLotName).toLowerCase();
				lot.setLotteryAlias(tempLotteryAlias);
				lot.setLotCode(lotCode+"");
				lot.setCreateTime(new Date());
				// 如果是列表中的最后一条，抓取下一页，或是抓取另一种彩票类型列表
				if (list.indexOf(lot) == list.size() - 1) {
					Calendar cal=Calendar.getInstance();
					cal.setTime(lot.getPreDrawTime());
					// 时间往后一天
					cal.add(Calendar.DATE, -1);
					queryDateStr = CommUtil.formatShortDate(cal.getTime());
					// 查到 16年1月1日止,开始抓取下一种彩票类型
					if (!lot.getPreDrawTime().after(endTime)) {
						queryDateStr = null;
						param[1] = lotCode = (int) (param[1]) + 1;
					}
				}
			}
			this.lotteryQuanguoService.save(list);
			*/
			this.lotHistoryService.save(list);
		}else {
			queryDateStr = null;
			param[1] = lotCode = (int) (param[1]) + 1;
		}
//		logger.info("保存记录："+list.size()+" 条 ");
		
		// 10045 为全国彩最后和中code码
		if (lotCode <= 10045) {
			// 递增调用本函数
			this.getQuanguoLotteryHistory(lotCode, queryDateStr);
//			logger.info("=============全国彩抓取完毕========>>>");
		}
	}
	
	/**
	 * 
	* @Title: getQuanguoLotteryMap 
	* @Description: 获取彩票名称 
	* @param @return    设定文件 
	* @return Map<Integer,Object>    返回类型 
	* @throws
	 */
	public Map<Integer, Object> getQuanguoLotteryMap() {

		Map<Integer, Object> nameMap = Maps.newConcurrentMap();
		nameMap.put(10039, "福彩双色球");
		nameMap.put(10040, "超级大乐透");
		nameMap.put(10042, "福彩七乐彩");
		nameMap.put(10043, "体彩排列3");
		nameMap.put(10041, "福彩3D");
		nameMap.put(10044, "体彩排列5");
		nameMap.put(10045, "体彩七星彩");
		return nameMap;
	}

	
	//**************************  根据分组，获取彩票列表 1（高频彩）,2（全国彩） ,3（境外彩） , 4（热门彩） ,5（香港彩） *******************************
	private String GRAB_DATA_ULR_GROUP="http://api.1680210.com/lottery/getLotteryListByType.do?";
	//  按彩票类型组获取彩票类型
	/**
	 * 
	* @Title: saveLotType 
	* @Description: 将json数组转为彩票类型实体，并保存
	* @param @param array    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void saveLotType(JSONArray array,int groupId){
		
		if (AssertValue.isNotNullAndNotEmpty(array)) {
			List<AppLotType> list=JSONArray.parseArray(array.toJSONString(), AppLotType.class);
			for (AppLotType lotType : list) {
				lotType.setId(null);
				lotType.setCreateTime(new Date());
				// 1. 截取频率为数字
				String frequency = lotType.getFrequency();
				if (frequency.contains("每") && frequency.endsWith("分钟")) {
					frequency = frequency.replace("每", "").replace("分钟", "");
					lotType.setFrequency(frequency);
					lotType.setLotDay("1,2,3,4,5,6,0");
				}else if (frequency.contains("每周")) {
					frequency=frequency.replace("每周", "");
					frequency=CommUtil.getWeekDayFromHanShuzi(frequency);
					lotType.setLotDay(frequency);
				}else if(frequency.contains("每日")) {
					lotType.setLotDay("1,2,3,4,5,6,0");
				}
				// 2、转汉字名首写拼音
				String tempLotName=lotType.getLotName();
				if (tempLotName.contains("⑥")) {
					tempLotName = tempLotName.replace('⑥', '六');// 替换其它特殊符号
				}
				String tempLotteryAlias = CommUtil.getPinYinHeadChar(tempLotName).toLowerCase();
				lotType.setLotAlias(tempLotteryAlias);
				
				// 3、设置当前彩票类型所属分组
				lotType.setLotGroupCode(groupId);
			}
			lotTypeService.save(list);
		}
	}
	
	/**
	 * 
	* @Title: grabDataByTypeGroup 
	* @Description: 按类型获取彩票类型json数组
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void grabDataByTypeGroup(String url,  Integer groupId) {
		if (!AssertValue.isNotNullAndNotEmpty(url)) {
			url = this.GRAB_DATA_ULR_GROUP;
			groupId = 1;
		}
		url = url + "&type="+groupId;
//		logger.info("现在的请求地址是："+url);
		JSONArray array = this.getOpenResultData(url);
		this.saveLotType(array,groupId);
		groupId=Integer.parseInt(groupId+"")+1;// 类型加1;
		if (groupId <= 5) {
			this.grabDataByTypeGroup(this.GRAB_DATA_ULR_GROUP, groupId);
		}
	}
	
	
}
