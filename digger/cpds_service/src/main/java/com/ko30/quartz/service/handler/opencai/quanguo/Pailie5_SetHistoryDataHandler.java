package com.ko30.quartz.service.handler.opencai.quanguo;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ko30.common.util.CommUtil;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.quartz.service.handler.OpencaiSetLotHistoryDataHandler;
import com.ko30.quartz.service.handler.opencai.FixedOneIntervalSetLotHitoryData;

/**
 * 
* @ClassName: Pailie5_SetHistoryDataHandler 
* @Description: 排列5
* @author A18ccms a18ccms_gmail_com 
* @date 2017年12月4日 上午10:59:28 
*
 */
@Service
public class Pailie5_SetHistoryDataHandler  extends FixedOneIntervalSetLotHitoryData implements
		OpencaiSetLotHistoryDataHandler {

	/**
	 * 每天 20:30:00 开奖  （20:36:50[开彩网开奖时间]）
	 */
	@Override
	public AppLotHistory handler(AppLotHistory targetLot,TShowApiLotType showApiType) {
		
		// 得到新一期的开奖时间
		Calendar cal = Calendar.getInstance();
		//int currWeekday=cal.get(Calendar.DAY_OF_WEEK) - 1;// 得到当前的星期数
		int currDate=cal.get(Calendar.DAY_OF_YEAR);//得到开奖日期，当年中的天数
		
		// 检查当前时间是否是开奖日期，再检查是否是在开奖时间之后
		String currentOpenTimeStr=CommUtil.formatShortDate(cal.getTime())+" 20:40:50";
		Date currentOpenTime=CommUtil.formatDate(currentOpenTimeStr, "yyyy-MM-dd HH:mm:ss");
		
		// 检查最新得到的开奖时间是否小于等于当前（日期/星期）
		Calendar drawCal = Calendar.getInstance();
		drawCal.setTime(targetLot.getPreDrawTime());
		int drawDate=drawCal.get(Calendar.DAY_OF_YEAR);
		
		if (drawDate<currDate) {
			targetLot.setDrawTime(currentOpenTime);
		}else {
			cal.add(Calendar.DATE, 1);// 日期加一天
			// 设置开奖的具体时间
			cal.set(Calendar.HOUR_OF_DAY, 20);
			cal.set(Calendar.MINUTE, 40);
			cal.set(Calendar.SECOND, 00);
			targetLot.setDrawTime(cal.getTime());
			
			// 设置下一期的开奖期号
			setLFData(targetLot, cal);
		}
		
		return targetLot;
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.PAI_LIE_5;
	}

}
