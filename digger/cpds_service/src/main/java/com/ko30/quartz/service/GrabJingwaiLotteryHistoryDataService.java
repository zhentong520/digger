package com.ko30.quartz.service;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.CommUtil;
import com.ko30.common.util.SpringUtils;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.service.lotteryInfo.LotHistoryService;


/**
 * 
* @ClassName: GrabJingwaiLotteryHistoryDataService 
* @Description: 获取 境外彩 服务类
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月29日 下午6:29:02 
*
 */
@Service
public class GrabJingwaiLotteryHistoryDataService {

	private final Logger logger = Logger.getLogger(GrabJingwaiLotteryHistoryDataService.class);
	
	private GrabLotteryHistoryService grabLotteryHistoryService;
	
	private LotHistoryService lotHistoryService;
	
	// 保存不同请求
	private static List<Map<String, Object>> urls=null; 

	private Map<String, Object> map=null;
	public GrabJingwaiLotteryHistoryDataService() {
		grabLotteryHistoryService = SpringUtils.getBean(GrabLotteryHistoryService.class);
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		
		urls=Lists.newArrayList();
		// 热门彩部分 请求路径    groupCode=1
		//澳洲幸运5 http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?date=2017-08-22&lotCode=10010
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?lotCode=10010");
		map.put("lotName", "澳洲幸运5");
		map.put("lotCode", 10010);
		urls.add(map);
		
		//澳洲幸运8 http://api.1680210.com/klsf/getHistoryLotteryInfo.do?date=&lotCode=10011
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/klsf/getHistoryLotteryInfo.do?lotCode=10011");
		map.put("lotName", "澳洲幸运8");
		map.put("lotCode", 10011);
		urls.add(map);
		
		//澳洲幸运10 http://api.1680210.com/pks/getPksHistoryList.do?date=2017-08-08&lotCode=10012
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/pks/getPksHistoryList.do?lotCode=10012");
		map.put("lotName", "澳洲幸运10");
		map.put("lotCode", 10012);
		urls.add(map);
		
		//澳洲幸运20 http://api.1680210.com/LuckTwenty/getBaseLuckTwentyList.do?date=2017-08-29&lotCode=10013
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/LuckTwenty/getBaseLuckTwentyList.do?lotCode=10013");
		map.put("lotName", "澳洲幸运20");
		map.put("lotCode", 10013);
		urls.add(map);
		
		// 台湾宾果
		// http://api.1680210.com/LuckTwenty/getBaseLuckTwentyList.do?date=2017-08-21&lotCode=10047
		map = Maps.newConcurrentMap();
		map.put("url","http://api.1680210.com/LuckTwenty/getBaseLuckTwentyList.do?lotCode=10047");
		map.put("lotName", "台湾宾果");
		map.put("lotCode", 10047);
		urls.add(map);
		
	}
	
	
	/**
	 * 
	* @Title: getUrls 
	* @Description: 获取到所有地址 
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	public List<Map<String, Object>> getUrls() {
		return AssertValue.isNotNullAndNotEmpty(urls) ? urls : Lists.newArrayList();
	}
	
	/**
	 * 
	* @Title: grabJingwaiLotteryHistory 
	* @Description: 获取境外彩  3
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void grabJingwaiLotteryHistory(Map<String, Object> param) {
		if (!AssertValue.isNotNull(param)) {
			return;
		}
		String url = param.get("url").toString();
		String lotName = param.get("lotName").toString();
		Integer lotCode = Integer.parseInt(param.get("lotCode") + "");
		
		// 得到日期排后的一条
		AppLotHistory lot=lotHistoryService.getTheLastOneByLotCode(lotCode);
		String beginTimeStr=CommUtil.formatShortDate(lot.getPreDrawTime());
		grabLotteryHistoryService.saveRemenLotHistory(url, 3, null, lotName,lotCode,beginTimeStr);
	}
}
