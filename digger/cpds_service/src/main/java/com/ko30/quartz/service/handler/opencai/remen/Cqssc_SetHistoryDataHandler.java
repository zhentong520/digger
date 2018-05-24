package com.ko30.quartz.service.handler.opencai.remen;

import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ko30.common.util.AssertValue;
import com.ko30.common.util.CommUtil;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.quartz.service.handler.OpencaiSetLotHistoryDataHandler;

/**
 * 
* @ClassName: Cqssc_SetHistoryDataHandler 
* @Description: 重庆时时彩,分两种间隔时间5---》10 ----》5 且跨天
* @author A18ccms a18ccms_gmail_com 
* @date 2017年12月4日 上午10:59:28 
*
 */
@Service
public class Cqssc_SetHistoryDataHandler implements
		OpencaiSetLotHistoryDataHandler {

	private final Logger logger=Logger.getLogger(Cqssc_SetHistoryDataHandler.class);
	
	/**
	 * 每5,10,5分钟  开一次奖 00:05:50  -->>(5)	01:55:50  | 10:00:45 -->>(10)   22:00:45  |    22:05:45 -->>(5)   00:01:57共120期
	 */
	@Override
	public AppLotHistory handler(AppLotHistory targetLot,TShowApiLotType showApiType) {
		
		//return this.setData(targetLot, showApiType, 10);
		// 1、设置开奖信息中的总开奖期数
		if (AssertValue.isNotNull(showApiType.getTotalCount())) {
			//Date beginTime = showApiType.getBeginTime();//当前彩种的开奖时间
			//Date endTime = showApiType.getEndTime();//当前彩种的开奖时间
			// 当前时间对应的时分秒,通过时间差，得到期数
			Calendar cal = Calendar.getInstance();
			cal.setTime(targetLot.getPreDrawTime());// 上期开奖时间
			String currentTimeStr = CommUtil.formatTime("HH:mm:ss",cal.getTime());
			Date currentTime = CommUtil.formatDate(currentTimeStr,"HH:mm:ss");
			
			// 得到期数
			String preIssueStr=targetLot.getPreDrawIssue();
			String issueStr=preIssueStr.substring(preIssueStr.length()-3, preIssueStr.length());
			int currCount=Integer.parseInt(issueStr);
			targetLot.setDrawCount(currCount+ 1);// 当前己开奖次数
			// 设置下一期的开奖期号
			Long preDrawIssue=Long.parseLong(preIssueStr)+1;
			targetLot.setDrawIssue(preDrawIssue.toString());
			
			Date sub5_beginTime=CommUtil.formatDate("00:05:50", "HH:mm:ss");// 第一个5分钟间隔的开始时间
			Date sub10_beginTime=CommUtil.formatDate("10:00:45", "HH:mm:ss");// 10分钟间隔的开始时间
			
			Date sub5_EndTime=CommUtil.formatDate("01:55:50", "HH:mm:ss");// 5分钟间隔结束时间
			Date sub10_EndTime=CommUtil.formatDate("22:00:45", "HH:mm:ss");// 10分钟间隔结束时间
			Date sub5_EndTime2=CommUtil.formatDate("00:01:57", "HH:mm:ss");// 第二个5分钟间隔结束时间（次日)
			
			// 检查是否已经开到最后一期
			if (currCount<targetLot.getTotalCount()) {
				if (currentTime.getTime()>=sub5_beginTime.getTime() &&currentTime.getTime()<sub5_EndTime.getTime()) {//第一个5分钟区间
					cal.add(Calendar.MINUTE, 5);
				}else if(currentTime.getTime()>=sub5_EndTime.getTime() && currentTime.getTime()<sub10_beginTime.getTime()) {
					// 10分钟的间隔开始时间 10:00:45
					cal.set(Calendar.HOUR_OF_DAY, 10);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 45);
				}else if (currentTime.getTime()>=sub10_beginTime.getTime()&&currentTime.getTime()<sub10_EndTime.getTime()) {// 10分钟区间
					cal.add(Calendar.MINUTE, 10);
				}else if (currentTime.getTime()>=sub10_EndTime.getTime() || currentTime.getTime()<sub5_EndTime2.getTime()) {// 第二个5分钟区域
					cal.add(Calendar.MINUTE, 5);
				}
			}else{
				// 另一个开奖周期的开始时间 00:05:50
				cal.set(Calendar.HOUR_OF_DAY, 00);
				cal.set(Calendar.MINUTE, 05);
				cal.set(Calendar.SECOND, 50);
				
				// 当下一次开奖在下一个开奖周期中时的开奖期号
				String drawIssue = CommUtil.formatTime("yyyyMMdd",cal.getTime());
				StringBuilder tempIssueNo = new StringBuilder("1");
				for (int i = 0; i < 2; i++) {
					tempIssueNo.insert(0, "0");
				}
				drawIssue = drawIssue + tempIssueNo.toString();
				targetLot.setDrawIssue(drawIssue);
			}
			targetLot.setDrawTime(cal.getTime());
			
		}
		return targetLot;
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.CHONG_QING_SHI_SHI_CAI;
	}

}
