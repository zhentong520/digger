package com.ko30.service.lotQuartzHelper;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.HttpClientUtil;
import com.ko30.common.util.SpringUtils;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.vo.grabresult.LotGrabInfo;
import com.ko30.service.lotteryInfo.LotHistoryService;

/**
 * 
 * @ClassName: GrabLotDataQuartzJobHelper
 * @Description: 定时获取开奖数据帮助类
 * @author A18ccms a18ccms_gmail_com
 * @date 2017年11月14日 下午5:13:53
 *
 */
@Service
public class GrabLotDataQuartzJobHelper {
	
	private final Logger logger=Logger.getLogger(GrabLotDataQuartzJobHelper.class);
	
	private LotHistoryService lotHistoryService;
	private Map<Integer,LotGrabInfo> lotReqUrlMap;
	public GrabLotDataQuartzJobHelper(){
		lotReqUrlMap=Maps.newConcurrentMap();
		lotReqUrlMap.putAll(getQuanGuoLot());
		// 注入开奖历史服务
		lotHistoryService=SpringUtils.getBean(LotHistoryService.class);
	}
	
	/**
	 * 
	 * @Title: getQuanGuoLot
	 * @Description: 全国彩地址
	 * @param @return 设定文件
	 * @return Map<Integer,LotGrabInfo> 返回类型
	 * @throws
	 */
	private Map<Integer, LotGrabInfo> getQuanGuoLot(){
		
		Map<Integer, LotGrabInfo>lotReqUrlMap=Maps.newConcurrentMap();
		LotGrabInfo info=new LotGrabInfo();
		info.setLotCode(10039);
		info.setLotName("福彩双色球");
		info.setUrl("http://api.api68.com/QuanGuoCai/getHistoryLotteryInfo.do?lotCode=10039");
		lotReqUrlMap.put(10039, info);
		
		info=new LotGrabInfo();
		info.setLotCode(10040);
		info.setLotName("超级大乐透");
		info.setUrl("http://api.api68.com/QuanGuoCai/getHistoryLotteryInfo.do?lotCode=10040");
		lotReqUrlMap.put(10040, info);
		
		info=new LotGrabInfo();
		info.setLotCode(10041);
		info.setLotName("福彩3D");
		info.setUrl("http://api.api68.com/QuanGuoCai/getLotteryInfoList.do?lotCode=10041");
		lotReqUrlMap.put(10041, info);
		
		info=new LotGrabInfo();
		info.setLotCode(10042);
		info.setLotName("福彩七乐彩");
		info.setUrl("http://api.api68.com/QuanGuoCai/getHistoryLotteryInfo.do?lotCode=10042");
		lotReqUrlMap.put(10042, info);
		
		info=new LotGrabInfo();
		info.setLotCode(10043);
		info.setLotName("体彩排列3");
		info.setUrl("http://api.api68.com/QuanGuoCai/getLotteryInfoList.do?lotCode=10043");
		lotReqUrlMap.put(10043, info);
		
		info=new LotGrabInfo();
		info.setLotCode(10044);
		info.setLotName("体彩排列5");
		info.setUrl("http://api.api68.com/QuanGuoCai/getHistoryLotteryInfo.do?lotCode=10044");
		lotReqUrlMap.put(10044, info);
		
		info=new LotGrabInfo();
		info.setLotCode(10045);
		info.setLotName("体彩七星彩");
		info.setUrl("http://api.api68.com/QuanGuoCai/getHistoryLotteryInfo.do?lotCode=10045");
		lotReqUrlMap.put(10045, info);
		return lotReqUrlMap;
	}
	
	
	/**
	 * 
	 * @Title: getMoreLotHistoryData
	 * @Description: 得到历史记录中的前5条，将转换成实体
	 * @param @param lotCode   彩种码
	 * @param @param groupCode 彩种分组码
	 * @param @return 设定文件
	 * @return List<AppLotHistory> 返回类型
	 * @throws
	 */
	public List<AppLotHistory> getMoreLotHistoryData(Integer lotCode,Integer groupCode){

		List<AppLotHistory> lots=Lists.newArrayList();
		try {
			// 在不同分组下的不同请求地址
			LotGrabInfo lotReqInfo=null;
			switch (groupCode) {
			case 1:
				break;
			case 2:// 全国彩时
				lotReqInfo = this.lotReqUrlMap.get(lotCode);
				break;
			case 3:

				break;
			case 4:

				break;

			default:
				break;
			}
			
			// 还没有获取历史记录的情况
			if (!AssertValue.isNotNull(lotReqInfo)) {
				logger.info("======>>>>>>>>>没有对应的请求地址信息，获取 "+lotCode+" 更多5条记录失败");
				return lots;
			}
			
			String contentStr = HttpClientUtil.get(lotReqInfo.getUrl());
			JSONObject contentJsonObj = JSONObject.parseObject(contentStr);
			// 取不到对应key值的时候，为获取数据异常
			JSONObject resultjsonObj = contentJsonObj.getJSONObject("result");
			if (AssertValue.isNotNullAndNotEmpty(resultjsonObj.getString("data"))) {
				// 得到是默认100条，现只取最前面5条(不包含第一条，因在最新开奖中已经显示出来)
				JSONArray tempDataJsonArr = resultjsonObj.getJSONArray("data");
				// 将jsonArray转成实体集合
				for (int i = 1; i < tempDataJsonArr.size(); i++) {
					if (i>=6) {
						break;
					}
					Object tempData=tempDataJsonArr.get(i);
					// 转换成对应实体,并设置对应彩种名称及编码信息
					AppLotHistory tempLot=JSON.parseObject(JSON.toJSONString(tempData), AppLotHistory.class);
					tempLot.setLotName(lotReqInfo.getLotName());
					tempLot.setLotCode(lotReqInfo.getLotCode());
					// 得到最新一期期号
					Long drawIssue=Long.parseLong(tempLot.getPreDrawIssue())+1;
					tempLot.setDrawIssue(drawIssue+"");
					if (!lotHistoryService.checkisExist(tempLot)) {// 检查获得的记录是否已经被保存
						lots.add(tempLot);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("======>>>>>>>>>获取彩种： "+lotCode+" 更多历史异常:",e);
		}
		return lots;
	}
	
	
}
