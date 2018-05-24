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
* @ClassName: GrabGaopinLotteryHistoryDataService 
* @Description: 获取 高频彩 服务类
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月29日 下午6:29:02 
*
 */
@Service
public class GrabGaopinLotteryHistoryDataService {

	private final Logger logger = Logger.getLogger(GrabGaopinLotteryHistoryDataService.class);
	
	private GrabLotteryHistoryService grabLotteryHistoryService;
	
	private LotHistoryService lotHistoryService;
	
	// 保存不同请求
	private static List<Map<String, Object>> urls=null; 

	private Map<String, Object> map=null;
	public GrabGaopinLotteryHistoryDataService() {
		grabLotteryHistoryService = SpringUtils.getBean(GrabLotteryHistoryService.class);
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		
		urls=Lists.newArrayList();
		
		// 高频彩部分 请求路径    groupCode=1
//		广东11选5 http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-16&lotCode=10006
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10006");
		map.put("lotName", "广东11选5");
		map.put("lotCode", 10006);
		urls.add(map);
//		上海11选5 http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-23&lotCode=10018
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10018");
		map.put("lotName", "上海11选5");
		map.put("lotCode", 10018);
		urls.add(map);
//		安徽11选5 http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-23&lotCode=10017
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10017");
		map.put("lotName", "安徽11选5");
		map.put("lotCode", 10017);
		urls.add(map);
//		江西11选5 http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-23&lotCode=10015
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10015");
		map.put("lotName", "江西11选5");
		map.put("lotCode", 10015);
		urls.add(map);
//		吉林11选5 http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-22&lotCode=10023
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10023");
		map.put("lotName", "吉林11选5");
		map.put("lotCode", 10023);
		urls.add(map);
//		广西11选5 http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-22&lotCode=10022
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10022");
		map.put("lotName", "广西11选5");
		map.put("lotCode", 10022);
		urls.add(map);
//		湖北11选5 http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-22&lotCode=10020
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10022");
		map.put("lotName", "广西11选5");
		map.put("lotCode", 10022);
		urls.add(map);
//		辽宁11选5 http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-22&lotCode=10019
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10019");
		map.put("lotName", "辽宁11选5");
		map.put("lotCode", 10019);
		urls.add(map);
//		江苏11选5 http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-22&lotCode=10016
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10016");
		map.put("lotName", "江苏11选5");
		map.put("lotCode", 10016);
		urls.add(map);
//		浙江11选5 http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-22&lotCode=10025
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10025");
		map.put("lotName", "浙江11选5");
		map.put("lotCode", 10025);
		urls.add(map);
//		内蒙古11选5 http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-23&lotCode=10024
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/ElevenFive/getElevenFiveList.do?lotCode=10024");
		map.put("lotName", "内蒙古11选5");
		map.put("lotCode", 10024);
		urls.add(map);
		
//		江苏快3 http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?date=2017-08-16&lotCode=10007
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?lotCode=10007");
		map.put("lotName", "江苏快3");
		map.put("lotCode", 10007);
		urls.add(map);
//		广西快3 http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?date=2017-08-16&lotCode=10026
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?lotCode=10026");
		map.put("lotName", "广西快3");
		map.put("lotCode", 10026);
		urls.add(map);
//		吉林快3 http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?date=2017-08-01&lotCode=10027
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?lotCode=10027");
		map.put("lotName", "吉林快3");
		map.put("lotCode", 10027);
		urls.add(map);
//		河北快3 http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?date=2017-08-08&lotCode=10028
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?lotCode=10028");
		map.put("lotName", "河北快3");
		map.put("lotCode", 10028);
		urls.add(map);
//		安徽快3 http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?date=2017-08-24&lotCode=10030
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?lotCode=10030");
		map.put("lotName", "安徽快3");
		map.put("lotCode", 10030);
		urls.add(map);
//		内蒙古快3 http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?date=2017-08-24&lotCode=10029
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?lotCode=10029");
		map.put("lotName", "内蒙古快3");
		map.put("lotCode", 10029);
		urls.add(map);
//		福建快3 http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?date=2017-07-31&lotCode=10031
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?lotCode=10031");
		map.put("lotName", "福建快3");
		map.put("lotCode", 10031);
		urls.add(map);
//		湖北快3 http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?date=2017-08-17&lotCode=10032
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?lotCode=10032");
		map.put("lotName", "湖北快3");
		map.put("lotCode", 10032);
		urls.add(map);
//		北京快3 http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?date=2017-08-23&lotCode=10033
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/lotteryJSFastThree/getJSFastThreeList.do?lotCode=10033");
		map.put("lotName", "北京快3");
		map.put("lotCode", 10033);
		urls.add(map);
	
//		北京快乐8 http://api.1680210.com/LuckTwenty/getBaseLuckTwentyList.do?date=2017-08-23&lotCode=10014
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/LuckTwenty/getBaseLuckTwentyList.do?lotCode=10014");
		map.put("lotName", "北京快乐8");
		map.put("lotCode", 10014);
		urls.add(map);

//		天津快乐十分 http://api.1680210.com/klsf/getHistoryLotteryInfo.do?date=2017-08-24&lotCode=10034
		map=Maps.newConcurrentMap();
		map.put("url", "http://api.1680210.com/klsf/getHistoryLotteryInfo.do?lotCode=10034");
		map.put("lotName", "天津快乐十分");
		map.put("lotCode", 10034);
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
	* @Title: grabGaopinLotteryHistory 
	* @Description: 获取 高频彩  1
	* @param @param param    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void grabGaopinLotteryHistory(Map<String, Object> param) {
		if (!AssertValue.isNotNull(param)) {
			return;
		}
		
		String url = param.get("url").toString();
		String lotName = param.get("lotName").toString();
		Integer lotCode = Integer.parseInt(param.get("lotCode") + "");
		
		// 得到日期排后的一条
		AppLotHistory lot=lotHistoryService.getTheLastOneByLotCode(lotCode);
		String beginTimeStr=CommUtil.formatShortDate(lot.getPreDrawTime());
		grabLotteryHistoryService.saveRemenLotHistory(url, 1, null, lotName,lotCode,beginTimeStr);
	}
}
