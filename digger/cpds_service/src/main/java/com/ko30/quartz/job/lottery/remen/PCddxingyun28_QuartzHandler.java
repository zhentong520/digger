package com.ko30.quartz.job.lottery.remen;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.ko30.common.base.entity.quartz.QuartzParamInfo;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.BeanUtils;
import com.ko30.common.util.CommUtil;
import com.ko30.common.util.SpringUtils;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.AppLotNew;
import com.ko30.quartz.core.handler.QuartzHandler;
import com.ko30.quartz.core.handler.QuartzHandlerFactory;
import com.ko30.quartz.service.GrabOpenResultService;
import com.ko30.service.lotteryInfo.LotHistoryService;
import com.ko30.service.lotteryInfo.LotNewService;


/**
 * 
* @ClassName: PCddxingyun28_QuartzHandler 
* @Description: PC 幸运蛋蛋28 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月29日 下午2:04:51 
*
 */
@Service
public class PCddxingyun28_QuartzHandler implements QuartzHandler {


	private Logger logger = Logger.getLogger(PCddxingyun28_QuartzHandler.class);
	private LotHistoryService lotHistoryService;
	private LotNewService lotNewService;
	private GrabOpenResultService<?> grabResult;

	public PCddxingyun28_QuartzHandler() {
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		lotNewService = SpringUtils.getBean(LotNewService.class);
		grabResult = SpringUtils.getBean(GrabOpenResultService.class);
	}
	

	@Override
	public Map<String, String> handler(JSONObject data) {
		
		Date execDate = setNormalExecDate();// 默认给本期开奖时间
		AppLotHistory lot = new AppLotHistory();
		
		try {
			// 10046  PC蛋蛋幸运28  彩票代码
			List<AppLotHistory> lotList=grabResult.getRemenLotHistoryByLotCode(QuartzHandlerType.PC_DAN_DAN_XING_YUN_28.getCode());
			// 有新数据
			if (AssertValue.isNotNullAndNotEmpty(lotList)) {
				lot = lotList.get(0);
				if (AssertValue.isNotNull(lot.getDrawTime()) && lot.getDrawTime().after(new Date())) {// 在当前时间 之后
					execDate=lot.getDrawTime();// 本期开奖时间
				}else if ( !lot.getDrawTime().after(new Date())) {// 不在当前时间 
					execDate=setUnnormalExecDate();// 稍后再次执行
				}
				lotHistoryService.save(lotList);
				
				// 更新最新记录
				AppLotNew lotNew = new AppLotNew();
				BeanUtils.copy(lot, lotNew);
				lotNewService.update2New(lotNew);
			}else {
				execDate=setUnnormalExecDate();// 稍后再次执行
			}
			logger.debug("获取 PC蛋蛋幸运28  新数据成功，下次执行时间："+CommUtil.formatLongDate(execDate));
		} catch (Exception e) {
			logger.debug("获取 PC蛋蛋幸运28  新数据异常，下次执行时间："+CommUtil.formatLongDate(execDate));
			e.printStackTrace();
		}finally{
			// 为下一次执行作准备
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(lot);
			info.setExecuteKey(QuartzHandlerType.PC_DAN_DAN_XING_YUN_28.getType());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.PC_DAN_DAN_XING_YUN_28, info, execDate);
		}

		return null;
	}

	/**
	 * 
	* @Title: setNormalExecDate 
	* @Description: 设置下次执行时间(正常情况下)
//	*  	每5分钟  从 09:05:01  到    23:55:01  结束
	* @param @return    设定文件 
	* @return Calendar    返回类型 
	* @throws
	 */
	private Date setNormalExecDate() {
		
		//得到当前时分
		Calendar cal = Calendar.getInstance();
		// 设置每5分钟一次
		cal.add(Calendar.MINUTE, 5);// 每5分钟
		/*
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		// 在 23:55:01　分之前
		if (hour < 23 || (hour == 23 && minute <= 59)) {
			cal.add(Calendar.MINUTE, 5);// 每5分钟
		} else if (hour == 23 && minute == 55) {
			// 设置下一个时间点执行 次日 00:00:30
			cal.add(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.set(Calendar.MINUTE, 05);
			cal.set(Calendar.SECOND, 01);
		}*/
		return cal.getTime();
	}
	

	/**
	 * 
	* @Title: setUnnormalExecDate 
	* @Description: 未抓取到数据时
	* @param @return    设定文件 
	* @return Date    返回类型 
	* @throws
	 */
	private Date setUnnormalExecDate() {
		// 设置下次执行时间
		Calendar cal = Calendar.getInstance();
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		//int minute = cal.get(Calendar.MINUTE);
		// 在 0-9点之前，因没有固定的开奖时间
		if (hour >= 0 && hour < 9) {
			cal.add(Calendar.MINUTE, 5);// 每5分钟
		}else {
			cal.add(Calendar.SECOND, 30);// 30秒后再次执行
		}
		return cal.getTime();
	}
	
	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.PC_DAN_DAN_XING_YUN_28;
	}

	@Override
	public String getDescription() {
		return QuartzHandlerType.PC_DAN_DAN_XING_YUN_28.getName();
	}

}
