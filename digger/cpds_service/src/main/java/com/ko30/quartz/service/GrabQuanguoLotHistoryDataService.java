package com.ko30.quartz.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.SpringUtils;

/**
 * 
* @ClassName: GrabQuanguoLotHistoryDataService 
* @Description: 获取全国彩往期历史 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月31日 上午9:57:05 
*
 */
@Service
public class GrabQuanguoLotHistoryDataService {

	
	private GrabLotteryHistoryService grabLotteryHistoryService;

	private List<Map<String, Object>> urlList=null;
	
	public GrabQuanguoLotHistoryDataService() {
		grabLotteryHistoryService = SpringUtils.getBean(GrabLotteryHistoryService.class);
		this.initUrlList();
	}
	
	
	/**
	 * 
	* @Title: initUrlList 
	* @Description: 初始化请求列表
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void initUrlList(){
		urlList=Lists.newArrayList();
		Map<String, Object> map=Maps.newConcurrentMap();
		
		// 热门彩部分 请求路径    groupCode=2
		
		// http://api.1680210.com/QuanGuoCai/getHistoryLotteryInfo.do?date=2017-08-22&lotCode=10039 // 双色球  完成
		// http://api.1680210.com/QuanGuoCai/getLotteryInfoList.do?date=2017-08-22&lotCode=10041 // 福彩3D 完成
		
		// http://api.1680210.com/QuanGuoCai/getHistoryLotteryInfo.do?date=2017-08-15&lotCode=10040 // 超级大乐透
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/QuanGuoCai/getHistoryLotteryInfo.do?lotCode=10040");
		map.put("lotName", "超级大乐透");
		map.put("lotCode", 10040);
		//urlList.add(map);
		
	}
	
	/**
	 * 
	* @Title: grabQuanguoLotteryHistory 
	* @Description: 获取全国彩历史数据 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void grabQuanguoLotteryHistory(){
		Iterator<Map<String, Object>> it = urlList.iterator();
		while (it.hasNext()) {
			Map<String, Object> map = it.next();
			String url = map.get("url").toString();
			String lotName = map.get("lotName").toString();
			Integer lotCode =Integer.parseInt(map.get("lotCode")+"");
			grabLotteryHistoryService.saveRemenLotHistory(url, 2, null, lotName,lotCode);
		}
	}
	
	/**
	 * 
	* @Title: getUrlMaps 
	* @Description:返回请求地址集合 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map<String, Object>> getUrlMaps() {
		return  AssertValue.isNotNullAndNotEmpty(this.urlList)?this.urlList:Lists.newArrayList();
	}
	
	/**
	 * 
	* @Title: grabQuanguoLotteryHistory 
	* @Description: 按指定的路径参数获取开奖历史数据 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void grabQuanguoLotteryHistory(Map<String, Object> param) {

		if (!AssertValue.isNotNull(param)) {
			return;
		}
		String url = param.get("url").toString();
		String lotName = param.get("lotName").toString();
		Integer lotCode = Integer.parseInt(param.get("lotCode") + "");
		grabLotteryHistoryService.saveRemenLotHistory(url, 2, null, lotName,lotCode);
	}
}
