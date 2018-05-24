package com.ko30.quartz.job.lottery.grabdata;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ko30.common.base.entity.quartz.QuartzParamInfo;
import com.ko30.common.util.SpringUtils;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.quartz.core.handler.QuartzHandler;
import com.ko30.quartz.core.handler.QuartzHandlerFactory;
import com.ko30.quartz.service.GrabQuanguoLotHistoryDataService;

@Service
public class GrabQuanguoLotteryQuartzHandler implements QuartzHandler {


	private Logger logger = Logger.getLogger(GrabQuanguoLotteryQuartzHandler.class);
	private GrabQuanguoLotHistoryDataService grabQuanguoHistoryDataService;
//	private GrabOpenResultService<?> grabResult;

	
	public GrabQuanguoLotteryQuartzHandler() {
		grabQuanguoHistoryDataService = SpringUtils.getBean(GrabQuanguoLotHistoryDataService.class);
//		grabResult = SpringUtils.getBean(GrabOpenResultService.class);
		
	}
	

	private static int  urlIndex=0;
	@Override
	public Map<String, String> handler(JSONObject data) {
		//grabResult.getQuanguoLotteryHistory(null, null);
		Map<String, Object> map=null;
		List<Map<String, Object>> urls=grabQuanguoHistoryDataService.getUrlMaps();
		if (urlIndex <= urls.size() - 1) {
			map=urls.get(urlIndex);
			grabQuanguoHistoryDataService.grabQuanguoLotteryHistory(map);
			
			if (urlIndex == urls.size() - 1) {
				return null;
			}
			// 5分钟后再次执行
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(QuartzHandlerType.GRAB_QUAN_GUO_HISTORY.getType());
			map.clear();
			map.put("url", urls.get(urlIndex+1));
			info.setParamObj(map);
			
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.MINUTE, 5);// 5分钟后执行
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.GRAB_QUAN_GUO_HISTORY, info,cal.getTime());
			urlIndex++;
		}
		
		return null;
	}


	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.GRAB_QUAN_GUO_HISTORY;
	}

	@Override
	public String getDescription() {
		return QuartzHandlerType.GRAB_QUAN_GUO_HISTORY.getName();
	}

}
