package com.ko30.quartz.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.BeanUtils;
import com.ko30.common.util.CommUtil;
import com.ko30.common.util.ConfigInfo;
import com.ko30.common.util.HttpClientUtil;
import com.ko30.common.util.JsonUtil;
import com.ko30.common.util.SpringUtils;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.entity.model.vo.winningInfo.LotTypeVo;
import com.ko30.quartz.service.handler.OpencaiSetLotHistoryDataHandler;
import com.ko30.service.lotteryInfo.LotHistoryService;
import com.ko30.service.lotteryInfo.LotTypeService;
import com.ko30.service.lotteryInfo.ShowApiLotTypeService;

/**
 * 
* @ClassName: GrabOpenResultQuartzJobService 
* @Description: 从开彩网数据获取的最新开奖数据
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月22日 上午9:53:28 
*
 */
@Service
public class _3_OpenCai_GrabOpenResultService {

	private static Logger logger = Logger.getLogger(_3_OpenCai_GrabOpenResultService.class);
	
	private LotTypeService lotTypeService;
	
	private LotHistoryService lotHistoryService;
	
	private ShowApiLotTypeService showApiService;
	
	private List<OpencaiSetLotHistoryDataHandler> setDateHandlers;

	public _3_OpenCai_GrabOpenResultService() {
		lotTypeService = SpringUtils.getBean(LotTypeService.class);
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		showApiService = SpringUtils.getBean(ShowApiLotTypeService.class);
		setDateHandlers=SpringUtils.getBeans(OpencaiSetLotHistoryDataHandler.class);
	}
	
	//http://route.showapi.com/44-1?showapi_appid=47648&showapi_sign=45ff00974d12497a9e1a37bb81361c92&code=ssq&
//	private static  String APPID=ConfigInfo.get("showapi.appId");
	private  static String OPENCAI_TOKEN=ConfigInfo.get("opencai.token");// 开彩网账户
	private static String OPENCAI_URL = ConfigInfo.get("opencai.data.url");//开彩网数据请求地址（单条获取）
	private static String GET_ALL_OPENCAI_URL = ConfigInfo.get("opencai.newest.data.url");//开彩网数据请求地址（全部获取）
	
	
	/**
	 * 
	 * @Title: batchGetOpencaiOpenResultData
	 * @Description: 一次性获取开奖网全部彩种列表
	 * @param @param lotCode
	 * @param @return 设定文件
	 * @return JSONArray 返回类型
	 * @throws
	 */
	private synchronized JSONArray batchGetOpencaiOpenResultData(Integer lotCode) {
		
		try {
			Thread.sleep(3000);// 休眠3秒
		} catch (InterruptedException e1) {
			logger.info("线程休眠发生异常",e1);
		}
		// 获取 opencai 接口的彩种类型信息
		TShowApiLotType showApiType=showApiService.getOneByLotCode(lotCode);
		if (!AssertValue.isNotNull(showApiType)) {
			return new JSONArray();
		}
		
		String tempCode=showApiType.getLotAlias();
		JSONArray dataJsonArr = null;
		String contentStr=null;// 请求到的结果内容
		//示例:http://c.apiplus.net/newly.do?rows=1&format=json&code=dlt&token=tf6914e918505b538k
		String url=GET_ALL_OPENCAI_URL+"&token="+OPENCAI_TOKEN;
		try {
			contentStr = HttpClientUtil.get(url);
			if (AssertValue.isNotNullAndNotEmpty(contentStr)) {
				JSONObject contentjObj = JSON.parseObject(contentStr);
				JSONArray resultJObj = (JSONArray) contentjObj.get("data");// 目标数据
				List<Map<String, Object>> dataList=JsonUtil.parseObject(resultJObj.toJSONString(), List.class);
				if (dataList.size() > 0) {
					// 获取单条数据
					//Map<String, Object> dataMap=dataList.get(0);
					//dataJsonArr=this.convert2AppLotHistoryData(dataMap, lotCode);
					// 获取多条数据
					//dataJsonArr=convert2AppLotHistoryData(dataList, lotCode);
					dataJsonArr=convert2AppLotHistoryData(showApiType,dataList);
				}
			}
		} catch (Exception e) {
			logger.info("转换实体异常：",e);
			String info="";
			if (!"全国".equals(showApiType.getArea())) {
				info=showApiType.getArea()+showApiType.getLotName();
			}else {
				info=showApiType.getLotName();
			}
			logger.info(String.format("获取得到的内容是：%s", contentStr));
			logger.error("抓取<开彩网>中 "+info+" 数据异常" ,e);
		}
		return dataJsonArr;
	}
	
	/**
	 * 
	 * @Title: getOpenResultData
	 * @Description: 按每种彩种获取数据 (JSONArray 结果集),访问时间间隔不能低于3秒
	 * @param @return 设定文件
	 * @return JSONArray 返回类型
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	private  JSONArray getOpencaiOpenResultData(Integer lotCode) {
		
		try {
			Thread.sleep(3000);// 休眠3秒
		} catch (InterruptedException e1) {
			logger.info("线程休眠发生异常",e1);
		}
		// 获取 opencai 接口的彩种类型信息
		TShowApiLotType showApiType=showApiService.getOneByLotCode(lotCode);
		if (!AssertValue.isNotNull(showApiType)) {
			return new JSONArray();
		}
		
		String tempCode=showApiType.getLotAlias();
		JSONArray dataJsonArr = null;
		String contentStr=null;// 请求到的结果内容
		//示例:http://c.apiplus.net/newly.do?rows=1&format=json&code=dlt&token=tf6914e918505b538k
		String url=OPENCAI_URL+"code="+tempCode+"&token="+OPENCAI_TOKEN;
		try {
			contentStr = HttpClientUtil.get(url);
			if (AssertValue.isNotNullAndNotEmpty(contentStr)) {
				JSONObject contentjObj = JsonUtil.parseObject(contentStr);
				JSONArray resultJObj = (JSONArray) contentjObj.get("data");// 目标数据
				List<Map<String, Object>> dataList=JsonUtil.parseObject(resultJObj.toJSONString(), List.class);
				if (dataList.size() > 0) {
					// 获取单条数据
					//Map<String, Object> dataMap=dataList.get(0);
					//dataJsonArr=this.convert2AppLotHistoryData(dataMap, lotCode);
					// 获取多条数据
					//dataJsonArr=convert2AppLotHistoryData(dataList, lotCode);
					dataJsonArr=convert2AppLotHistoryData(showApiType,dataList);
				}
			}
		} catch (Exception e) {
			String info="";
			if (!"全国".equals(showApiType.getArea())) {
				info=showApiType.getArea()+showApiType.getLotName();
			}else {
				info=showApiType.getLotName();
			}
			logger.info(String.format("获取得到的内容是：%s", contentStr));
			logger.error("抓取<开彩网>中 "+info+" 数据异常" ,e);
		}
		return dataJsonArr;
	}
	


	/**
	 * 
	 * @Title: convert2AppLotHistoryData
	 * @Description: 将开奖结果数据转换成目标格式数据
	 * @param @param sourceMap
	 * @param @param lotCode
	 * @param @return 设定文件
	 * @return JSONArray 返回类型
	 * @throws
	 */
	@SuppressWarnings("unused")
	private JSONArray convert2AppLotHistoryData(Map<String, Object> sourceMap,int lotCode){
		
		LotTypeVo queryParam = new LotTypeVo();
		queryParam.setLotCode(lotCode);
		List<LotTypeVo> types = lotTypeService.queryByCondition(queryParam);
		if (!AssertValue.isNotNullAndNotEmpty(types)) {
			return new JSONArray();
		}
		LotTypeVo typeVo = types.get(0);
		typeVo.setId(null);
		
		List<AppLotHistory> lotList=Lists.newArrayList();
		AppLotHistory targetLot=new AppLotHistory();
		BeanUtils.copy(typeVo, targetLot);
		
		// 得到目标格式的实体
		AppLotHistory tempLot=initLotHistoryData(targetLot, sourceMap);
		if (AssertValue.isNotNull(tempLot)) {
			lotList.add(tempLot);
		}
		return JsonUtil.parseArray(JsonUtil.toJSON(lotList).toString());
	}
	
	
	/**
	 * 
	 * @Title: convert2AppLotHistoryData
	 * @Description: 将开奖结果数据转换成目标格式数据(获取多条时)
	 * @param @param showApiType 易源、开彩网开奖类型实体
	 * @param @param sourceMaps
	 * @param @param lotCode
	 * @param @return 设定文件
	 * @return JSONArray 返回类型
	 * @throws
	 */
	private JSONArray convert2AppLotHistoryData(TShowApiLotType showApiType,List<Map<String, Object>> sourceMaps){
		
		LotTypeVo queryParam = new LotTypeVo();
		queryParam.setLotCode(showApiType.getLotCode().intValue());
		List<LotTypeVo> types = lotTypeService.queryByCondition(queryParam);
		if (!AssertValue.isNotNullAndNotEmpty(types)) {
			return new JSONArray();
		}
		LotTypeVo typeVo = types.get(0);
		typeVo.setId(null);
		List<AppLotHistory> lotList=Lists.newArrayList();
		// 设置开奖号码，期数，及开奖时间  源数据格式 ：{"timestamp":,"expect":"","time":"","name":"","code":"","openCode":""}
		int drawCount=0;//获取连续5期时，设置正确的期数
		for (Map<String, Object> sourceMap : sourceMaps) {
			int index=sourceMaps.indexOf(sourceMap);// 当前map在集合中的位置
			AppLotHistory targetLot=new AppLotHistory();
			BeanUtils.copy(typeVo, targetLot);
			AppLotHistory tempLot=initLotHistoryData(targetLot, sourceMap);
			if (AssertValue.isNotNull(tempLot)) {
				// 批量查询的时候（一次查出所有彩种）
				if (sourceMap.containsKey("code")) {
					String tempLotAlias=sourceMap.get("code")+"";
					if (showApiType.getLotAlias().equals(tempLotAlias)) {
						tempLot=setLotHistoryOtherInfo(tempLot, showApiType);// 设置其它相关信息
						if (AssertValue.isNotNull(tempLot.getDrawCount())) {
							if (index == 0) {
								drawCount = tempLot.getDrawCount();
							} else {
								drawCount--;
								tempLot.setDrawCount(drawCount);
							}
						}
						lotList.add(tempLot);
					}
				}else {
					tempLot=setLotHistoryOtherInfo(tempLot, showApiType);// 设置其它相关信息
					if (AssertValue.isNotNull(tempLot.getDrawCount())) {
						if (index == 0) {
							drawCount = tempLot.getDrawCount();
						} else {
							drawCount--;
							tempLot.setDrawCount(drawCount);
						}
					}
					lotList.add(tempLot);
				}
			}
			
		}
		return JSONArray.parseArray(JSON.toJSONString(lotList));
	}

	/**
	 * 
	 * @Title: setLotHistoryOtherInfo
	 * @Description: 设置开奖记录的其它信息
	 * @param @param lotParam
	 * @param @param lotType
	 * @param @return 设定文件
	 * @return AppLotHistory 返回类型
	 * @throws
	 */
	private AppLotHistory setLotHistoryOtherInfo(AppLotHistory lotParam,TShowApiLotType showApiType){
		
		if (AssertValue.isNotNullAndNotEmpty(setDateHandlers)) {
			for (OpencaiSetLotHistoryDataHandler doHandler : setDateHandlers) {
				if (AssertValue.isNotNull(doHandler) && doHandler.getType().getCode().intValue()==lotParam.getLotCode()) {
					lotParam=doHandler.handler(lotParam, showApiType);
				}
			}
		}
		return lotParam;
	}
	

	/**
	 * 
	 * @Title: initLotHistoryData
	 * @Description: 组装目标实体集合
	 * @param @param lotList
	 * @param @param targetLot
	 * @param @param sourceMap 设定文件
	 * @return AppLotHistory 返回类型
	 * @throws
	 */
	private AppLotHistory initLotHistoryData(AppLotHistory targetLot, Map<String, Object> sourceMap) {
		
		
		if (AssertValue.isNotNullAndNotEmpty(sourceMap)) {
			// 得到开奖期号
			String preDrawIssue=sourceMap.get("expect").toString().trim();
			targetLot.setPreDrawIssue(preDrawIssue);
			Long issueNo=Long.parseLong(preDrawIssue);
			targetLot.setDrawIssue(String.valueOf(issueNo+1));//下一期开奖期号
			
			// 设置本期开奖时间
			String preDrawTimeStr=sourceMap.get("opentime").toString();
			Date preDrawTime=CommUtil.formatDate(preDrawTimeStr,"yyyy-MM-dd HH:mm:ss");
			targetLot.setPreDrawTime(preDrawTime);
			
			// 设置开奖号码
			String preDrawCode=sourceMap.get("opencode").toString();
			preDrawCode=preDrawCode.replace("+", ",");// 替换开奖号码中的加号
			targetLot.setPreDrawCode(preDrawCode);
			
			// 计算和值
			int sum=0;
			String[] codeStrs=preDrawCode.split(",");
			for (int i = 0; i < codeStrs.length; i++) {
				String codeStr = codeStrs[i];
				sum += Integer.parseInt(codeStr);
			}
			targetLot.setSumNum(sum);
			targetLot.setSumSingleDouble(sum % 2 == 0 ? 1 : 0);// 设置和的单双
			targetLot.setCreateTime(new Date());
			//targetLot.setSumSingleDouble(sum % 2 == 0 ? 1 : 0);// 设置和的大小
			//lotList.add(targetLot);
			return targetLot;
		}
		return null;
	}
	
	
	
	/**
	 * 
	* @Title: getDataByLotCode 
	* @Description: 根据彩票类型码，获取记录列表 
	* @param @param lotCode
	* @param @return    设定文件 
	* @return JSONArray    返回类型 
	* @throws
	 */
	public JSONArray getDataByLotCode(Integer lotCode) {

		logger.info("---------- 开始获取开彩网数据 -------------------->>>>");
		JSONArray dataJsonObjs = this.getOpencaiOpenResultData(lotCode);
		//JSONArray dataJsonObjs = this.batchGetOpencaiOpenResultData(lotCode);
		JSONArray result = new JSONArray();// 保存目标类型彩票
		if (null!=dataJsonObjs && dataJsonObjs.size()>0) {
			List<JSONObject> datas = Lists.newArrayList();
			for (Object obj : dataJsonObjs) {
				// 将得到的彩票汉字名称转首字每大写
				JSONObject jsonObj = JSONObject.parseObject(obj.toString());
				datas.add(jsonObj);
				/*
				if (lotCode.intValue() == jsonObj.getInteger("lotCode")) {
					String objLotName = jsonObj.getString("lotName");
					objLotName=objLotName.replace('⑥', '六');// 替换其它特殊符号
					String tempLotAlias = CommUtil.getPinYinHeadChar(objLotName).toLowerCase();
					// 设置彩票别名
					jsonObj.put("lotAlias", tempLotAlias);
				}
				*/
			}
			if (AssertValue.isNotNullAndNotEmpty(datas)) {
				result = JSON.parseArray(JSON.toJSONString(datas));
			}
		}
		return result;
	}

	
	
}
