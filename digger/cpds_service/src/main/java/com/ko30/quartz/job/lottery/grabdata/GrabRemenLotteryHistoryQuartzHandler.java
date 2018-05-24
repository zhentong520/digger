package com.ko30.quartz.job.lottery.grabdata;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ko30.common.base.entity.quartz.QuartzParamInfo;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.SpringUtils;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.quartz.core.handler.QuartzHandler;
import com.ko30.quartz.core.handler.QuartzHandlerFactory;
import com.ko30.quartz.service.GrabLotteryHistoryService;


/**
 * 
* @ClassName: GrabRemenLotteryHistoryQuartzHandler 
* @Description: 定时获取热门彩定时任务执行类
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月31日 上午11:37:26 
*
 */
@Service
public class GrabRemenLotteryHistoryQuartzHandler implements QuartzHandler {


	private Logger logger = Logger.getLogger(GrabRemenLotteryHistoryQuartzHandler.class);
	private GrabLotteryHistoryService grabLotteryHistoryService;
//	private GrabOpenResultService<?> grabResult;

	
	public GrabRemenLotteryHistoryQuartzHandler() {
		grabLotteryHistoryService = SpringUtils.getBean(GrabLotteryHistoryService.class);
//		grabResult = SpringUtils.getBean(GrabOpenResultService.class);
		
	}
	

	@Override
	public Map<String, String> handler(JSONObject data) {
		//grabResult.getQuanguoLotteryHistory(null, null);
		Map<String, Object> map=JSONObject.parseObject(data.toString(), HashMap.class);
		logger.debug("map:"+map);
		
		try {
			if (AssertValue.isNotNull(map)) {
				grabLotteryHistoryService.grabRmenLotteryHistory(map);
				// 5分钟后再次执行
				map.clear();
				List<Map<String, Object>> urls=grabLotteryHistoryService.getUrls();
				for (int i = 0; i < urls.size(); i++) {
					String currentLotCodeStr=map.get("lotCode").toString();
					Map<String, Object> tempMap=urls.get(i);
					String nextLotCode=tempMap.get("lotCode").toString();
					if (currentLotCodeStr.equals(nextLotCode)) {
						map = urls.get(i);
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(QuartzHandlerType.GRAB_RE_MEN_HISTORY.getType());
			info.setParamObj(map);
			
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.MINUTE, 5);// 5分钟后执行
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.GRAB_RE_MEN_HISTORY, info,cal.getTime());
		}
		
		return null;
	}


	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.GRAB_RE_MEN_HISTORY;
	}

	@Override
	public String getDescription() {
		return QuartzHandlerType.GRAB_RE_MEN_HISTORY.getName();
	}

}
