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
import com.ko30.common.util.SpringUtils;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.entity.model.vo.winningInfo.LotTypeVo;
import com.ko30.service.lotteryInfo.LotHistoryService;
import com.ko30.service.lotteryInfo.LotTypeService;
import com.ko30.service.lotteryInfo.ShowApiLotTypeService;

/**
 * 
* @ClassName: GrabOpenResultQuartzJobService 
* @Description: 从易源数据获取的最新开奖数据
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月22日 上午9:53:28 
*
 */
@Service
public class _2_ShowApi_GrabOpenResultService {

	private static Logger logger = Logger.getLogger(_2_ShowApi_GrabOpenResultService.class);
	
	private LotTypeService lotTypeService;
	
	private LotHistoryService lotHistoryService;
	
	private ShowApiLotTypeService showApiService;

	public _2_ShowApi_GrabOpenResultService() {
		lotTypeService = SpringUtils.getBean(LotTypeService.class);
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		showApiService = SpringUtils.getBean(ShowApiLotTypeService.class);
	}
	
	//http://route.showapi.com/44-1?showapi_appid=47648&showapi_sign=45ff00974d12497a9e1a37bb81361c92&code=ssq&
	private static  String appId=ConfigInfo.get("showapi.appId");
	private  static String appKey=ConfigInfo.get("showapi.appKey");
	private static String showapi_url = ConfigInfo.get("showapi.url");//易源数据请求地址
	
	
	/**
	 * 
	* @Title: getOpenResultData 
	* @Description: 获取数据 (JSONArray 结果集)
	* @param @return    设定文件 
	* @return JSONArray    返回类型 
	* @throws
	 */
	@SuppressWarnings("unchecked")
	private JSONArray getShowApiOpenResultData(Integer lotCode) {

		
		// 获取 showapi接口的彩种类型信息
		TShowApiLotType showApiType=showApiService.getOneByLotCode(lotCode);
		if (!AssertValue.isNotNull(showApiType)) {
			return new JSONArray();
		}
		
		String tempCode=showApiType.getLotAlias();
		JSONArray dataJsonArr = null;
		//示例:http://route.showapi.com/44-1?showapi_appid=47648&showapi_sign=45ff00974d12497a9e1a37bb81361c92&code=ssq&
		String url=showapi_url+"?showapi_appid="+appId+"&showapi_sign="+appKey+"&code="+tempCode+"&";
		try {
			String contentStr = HttpClientUtil.get(url);
			if (AssertValue.isNotNullAndNotEmpty(contentStr)) {
				JSONObject contentjObj = JSON.parseObject(contentStr);
				JSONObject resultJObj = (JSONObject) contentjObj.get("showapi_res_body");
				List<Map<String, Object>> dataList=JSON.parseObject(resultJObj.getString("result"), List.class);
				if (dataList.size() > 0) {
					Map<String, Object> dataMap=dataList.get(0);
					dataJsonArr=this.convert2AppLotHistoryData(dataMap, lotCode);
				}
			}
		} catch (Exception e) {
			logger.info("抓取数据转化实体异常：" + e.getMessage());
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
		// 设置开奖号码，期数，及开奖时间  源数据格式 ：{"timestamp":,"expect":"","time":"","name":"","code":"","openCode":""}
		if (AssertValue.isNotNullAndNotEmpty(sourceMap)) {
			// 得到开奖期号
			String preDrawIssue=sourceMap.get("expect").toString().trim();
			targetLot.setPreDrawIssue(preDrawIssue);
			targetLot.setDrawIssue((Integer.parseInt(preDrawIssue)+1)+"");//下一期开奖期号
			
			// 设置本期开奖时间
			String preDrawTimeStr=sourceMap.get("time").toString();
			Date preDrawTime=CommUtil.formatDate(preDrawTimeStr,"yyyy-MM-dd HH:mm:ss");
			targetLot.setPreDrawTime(preDrawTime);
			
			// 设置开奖号码
			String preDrawCode=sourceMap.get("openCode").toString();
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
			//targetLot.setSumSingleDouble(sum % 2 == 0 ? 1 : 0);// 设置和的大小
			lotList.add(targetLot);
		}
		return JSONArray.parseArray(JSON.toJSONString(lotList));
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
	private synchronized JSONArray getOpenResultData(String url,int tryCount) {
		tryCount++;
		JSONArray dataJsonArr=new JSONArray();
		try {
			String contentStr = HttpClientUtil.get(url);
			JSONObject contentJsonObj = JSONObject.parseObject(contentStr);
			JSONObject resultjsonObj = contentJsonObj.getJSONObject("result");
			dataJsonArr = resultjsonObj.getJSONArray("data");
		} catch (Exception e) {
			if (tryCount <= 3) {
				this.getOpenResultData(url,tryCount);
			} else {
				return dataJsonArr;
			}
		}

		return dataJsonArr;
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

		logger.info("------ 开始获取易源网站数据 -------------------->>>>");
		JSONArray dataJsonObjs = this.getShowApiOpenResultData(lotCode);
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
