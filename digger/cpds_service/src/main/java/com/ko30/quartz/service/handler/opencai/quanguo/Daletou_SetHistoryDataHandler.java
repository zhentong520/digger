package com.ko30.quartz.service.handler.opencai.quanguo;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.ko30.common.util.AssertValue;
import com.ko30.common.util.CommUtil;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.quartz.service.handler.OpencaiSetLotHistoryDataHandler;
import com.ko30.quartz.service.handler.opencai.FixedOneIntervalSetLotHitoryData;

/**
 * 
* @ClassName: Daletou_SetHistoryDataHandler 
* @Description: 大乐透
* @author A18ccms a18ccms_gmail_com 
* @date 2017年12月4日 上午10:59:28 
*
 */
@Service
public class Daletou_SetHistoryDataHandler extends FixedOneIntervalSetLotHitoryData implements OpencaiSetLotHistoryDataHandler {

	/**
	 * 每周一、 三、 六  20:30:00 开奖  （20:35:30[开彩网开奖时间]）
	 */
	@Override
	public AppLotHistory handler(AppLotHistory targetLot,TShowApiLotType showApiType) {
		
		// 得到新一期的开奖时间
		Calendar cal = Calendar.getInstance();
		int currWeekday=cal.get(Calendar.DAY_OF_WEEK) - 1;// 得到当天星期
		int currDate=cal.get(Calendar.DAY_OF_YEAR);//得到开奖日期，当年中的天数
		
		// 检查当前时间是否是开奖日期，再检查是否是在开奖时间之后
		String currentOpenTimeStr=CommUtil.formatShortDate(cal.getTime())+" 20:35:30";
		Date currentOpenTime=CommUtil.formatDate(currentOpenTimeStr, "yyyy-MM-dd HH:mm:ss");
		
		// 检查最新得到的开奖时间是否小于等于当前（日期/星期）
		Calendar drawCal = Calendar.getInstance();
		drawCal.setTime(targetLot.getPreDrawTime());
		int drawdate=drawCal.get(Calendar.DAY_OF_YEAR);// 得到开奖的当年中的日期
		
		//(currWeekday == 1 || currWeekday == 3 || currWeekday == 6) && currentOpenTime.after(currentTime)
		if ((currWeekday == 1 || currWeekday == 3 || currWeekday == 6)&&(drawdate<currDate)) {
			targetLot.setDrawTime(currentOpenTime);
		}else {
			// 获取下一个执行周期
			while (true) {
				cal.add(Calendar.DATE, 1);// 日期加一天
				currWeekday = cal.get(Calendar.DAY_OF_WEEK) - 1;// 得到当天星期
				if (currWeekday == 1 || currWeekday == 3 || currWeekday == 6) {
					// 设置开奖的具体时间
					cal.set(Calendar.HOUR_OF_DAY, 20);
					cal.set(Calendar.MINUTE, 35);
					cal.set(Calendar.SECOND, 30);
					targetLot.setDrawTime(cal.getTime());
					break;
				}
			}
		}
		setLFData(targetLot, cal);
		
		return targetLot;
	}

	/**
	 * 
	 * @Title: setLFData
	 * @Description: 设置低频彩的开奖期数信息
	 * @param @param targetLot
	 * @param @param cal 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void setLFData1(AppLotHistory targetLot, Calendar cal) {
		// 设置下一期的开奖期号
		String preDrawIssueStr=targetLot.getPreDrawIssue();
		Long preDrawIssue=Long.parseLong(preDrawIssueStr);
		targetLot.setDrawIssue((preDrawIssue+1)+"");//(默认情况下期号加1)
		if (AssertValue.isNotNull(targetLot.getPreDrawTime())) {
			// 跨年的情况
			int newShortYear =cal.get(Calendar.YEAR);// 新一期的年份
			cal=Calendar.getInstance();// 再次初始，得到当前年份
			int oldShortYear=cal.get(Calendar.YEAR);
			if (newShortYear>oldShortYear) {
				String tempIssueStr=preDrawIssueStr.replace(String.valueOf(oldShortYear), "");// 检查期号中除去年月日，最后的长度
				if (AssertValue.isNotNullAndNotEmpty(tempIssueStr)) {
					StringBuilder tempIssueNo = new StringBuilder("1");
					for (int i = 0; i < tempIssueStr.length() - 1; i++) {
						tempIssueNo.insert(0, 0);
					}
					
					String newIssueNo = newShortYear+tempIssueNo.toString();
					/*
					String newIssueNo =null;
					if (tempIssueStr.length() == 2) {
						newIssueNo = newShortYear + "01";
					} else if (tempIssueStr.length() == 3) {
						newIssueNo = newShortYear + "001";
					}
					*/
					targetLot.setDrawIssue(newIssueNo);
				}
			}
		}
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.DA_LE_TOU;
	}

}
