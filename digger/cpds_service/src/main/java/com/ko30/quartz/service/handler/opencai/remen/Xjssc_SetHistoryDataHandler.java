package com.ko30.quartz.service.handler.opencai.remen;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ko30.common.util.AssertValue;
import com.ko30.common.util.CommUtil;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.quartz.service.handler.OpencaiSetLotHistoryDataHandler;

/**
 * 
* @ClassName: Xjssc_SetHistoryDataHandler 
* @Description: 新疆时时彩,固定间隔时间10钟，跨天
* @author A18ccms a18ccms_gmail_com 
* @date 2017年12月4日 上午10:59:28 
*
 */
@Service
public class Xjssc_SetHistoryDataHandler implements
		OpencaiSetLotHistoryDataHandler {

	/**
	 * 每10分钟开一次奖 10:09:50  -->>	01:59:50    共96期
	 */
	@Override
	public AppLotHistory handler(AppLotHistory targetLot,TShowApiLotType showApiType) {
		
		//return this.setData(targetLot, showApiType, 10);
		
		// 1、设置开奖信息中的总开奖期数
		BigInteger totalDrawCount=showApiType.getTotalCount();
		if (AssertValue.isNotNull(totalDrawCount)) {
			Date beginTime = showApiType.getBeginTime();//当前彩种的开奖时间
			Date endTime = showApiType.getEndTime();//当前彩种的开奖时间
			
			// 当前时间对应的时分秒,通过时间差，得到期数
			Calendar cal = Calendar.getInstance();
			cal.setTime(targetLot.getPreDrawTime());// 上期开奖时间
			//String currentTimeStr = CommUtil.formatTime("HH:mm:ss",cal.getTime());
			//Date currentTime = CommUtil.formatDate(currentTimeStr,"HH:mm:ss");
			// 跨天，期数不可以这样计算
			//Date currentMaxTime = CommUtil.formatDate("23:59:59");// 当天设置最大时间
			//Long subtractValue = (currentTime.getTime() - beginTime.getTime())/ (60 * 1000);
			//int currCount = (subtractValue.intValue() / 10) ;
			//targetLot.setDrawCount(currCount+ 1);// 当前己开奖次数
			
			
			// 设置下一期开奖时间，及期数
			String preDrawIssueStr=targetLot.getPreDrawIssue();
			// 得到当前开奖次数
			String drawCountStr=preDrawIssueStr.substring(preDrawIssueStr.length()-3);
			int alreadDrawCount=Integer.parseInt(drawCountStr);
			targetLot.setDrawCount(alreadDrawCount);
			
			Long preDrawIssue=Long.parseLong(preDrawIssueStr);
			String drawIssue=null;// 下期开奖期号
			if (AssertValue.isNotNull(endTime)) {
				if (alreadDrawCount<totalDrawCount.intValue()) {
					// 在今天之内，期数继续加1
					cal.add(Calendar.MINUTE, 10);// 间隔时间为10分钟
					cal.add(Calendar.SECOND, 30);// 增加30秒
					drawIssue=(preDrawIssue+1)+"";
				}else {
					// 下一个开始时间 10:09:50
					cal.set(Calendar.HOUR_OF_DAY, 10);
					cal.set(Calendar.MINUTE, 9);
					cal.set(Calendar.SECOND, 50);
					
					// 下一次的开奖期号
					drawIssue = CommUtil.formatTime("yyyyMMdd",cal.getTime());
					//String tempIssueStr=preDrawIssueStr.replace(drawIssue, "");// 检查期号中除去年月日，最后的长度
					// 跨天时的期数长度未变，新一天的开奖期数
					//if (AssertValue.isNotNullAndNotEmpty(tempIssueStr)) {
						// 期数应保持的字符串长度(因为当天最后天期涉及跨天，期数部分由三位数组成)
						StringBuilder countStrSb = new StringBuilder("1");
						for (int i = 0; i < 2; i++) {
							countStrSb.insert(0, "0");
						}
						drawIssue = drawIssue + countStrSb;
					//}
				}
				
				/*
				//设置今晚最后时间
				String latestTimeStr="23:59:59";
				Date latestTime=CommUtil.formatDate(latestTimeStr, "HH:mm:ss");
				if ((currentTime.getTime() <= latestTime.getTime() && currentDayStr.equals(issueYear))
					|| (endTime.getTime() > currentTime.getTime() && !currentDayStr.equals(issueYear))) {
					cal.setTime(targetLot.getPreDrawTime());
					// 在今天之内，期数继续加1
					cal.add(Calendar.MINUTE, 10);// 间隔时间为10分钟
					cal.add(Calendar.SECOND, 30);// 增加30秒
					drawIssue=(preDrawIssue+1)+"";
				}else {// 下一个执行期
					// 下一个开始时间 10:09:50
					cal.set(Calendar.HOUR_OF_DAY, 10);
					cal.set(Calendar.MINUTE, 9);
					cal.set(Calendar.SECOND, 50);
					
					// 下一次的开奖期号
					drawIssue = CommUtil.formatTime("yyyyMMdd",cal.getTime());
					String tempIssueStr=preDrawIssueStr.replace(drawIssue, "");// 检查期号中除去年月日，最后的长度
					// 跨天时的期数长度未变，新一天的开奖期数
					if (AssertValue.isNotNullAndNotEmpty(tempIssueStr)) {
						if (tempIssueStr.length()==2) {
							drawIssue += "01";
						}else if (tempIssueStr.length()==3) {
							drawIssue += "001";
						}else {
							drawIssue= (preDrawIssue+1)+"";
						}
					}
				}
				*/
				
				targetLot.setDrawTime(cal.getTime());
				targetLot.setDrawIssue(drawIssue);
			}
			
		}
		return targetLot;
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.XIN_JIANG_SHI_SHI_CAI;
	}

}
