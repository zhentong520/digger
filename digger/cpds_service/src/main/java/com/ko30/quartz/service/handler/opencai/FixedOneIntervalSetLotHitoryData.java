package com.ko30.quartz.service.handler.opencai;

import java.math.BigInteger;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

import com.ko30.common.util.AssertValue;
import com.ko30.common.util.CommUtil;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;

/**
 * 
 * @ClassName: FixedOneIntervalSetLotHitoryData
 * @Description:公共设置开奖信息类(固定一个时间间隔)
 * @author A18ccms a18ccms_gmail_com
 * @date 2017年12月1日 下午7:13:21
 *
 */
public class FixedOneIntervalSetLotHitoryData {

	private final Logger logger=Logger.getLogger(FixedOneIntervalSetLotHitoryData.class);
	
	/**
	 * 
	 * @Title: setData
	 * @Description: 设置开彩网记录中高频其它信息
	 * @param @param targetLot 目标开奖记录
	 * @param @param showApiType
	 * @param @param intervalMinute 间隔时间
	 * @param @return 设定文件
	 * @return AppLotHistory 返回类型
	 * @throws
	 */
	public synchronized AppLotHistory  setData(AppLotHistory targetLot,TShowApiLotType showApiType,Integer intervalMinute){

		if (!AssertValue.isNotNull(intervalMinute)) {
			intervalMinute = 10;
		}

		// 1、设置开奖信息中的总开奖期数
		BigInteger drawCount=showApiType.getTotalCount();
		if (AssertValue.isNotNull(drawCount)) {
			Date beginTime = showApiType.getBeginTime();//当前彩种的开奖时间
			Date endTime = showApiType.getEndTime();//当前彩种的开奖时间
			
			// 当前时间对应的时分秒,通过时间差，得到期数
			Calendar cal = Calendar.getInstance();
			String currentTimeStr = CommUtil.formatTime("HH:mm:ss",cal.getTime());
			Date currentTime = CommUtil.formatDate(currentTimeStr, "HH:mm:ss");
			Long subtractValue = (currentTime.getTime() - beginTime.getTime())/ (60 * 1000);
			int currCount = (subtractValue.intValue() / intervalMinute) ;
			targetLot.setDrawCount(currCount+ 1);// 当前己开奖次数（不能对所有彩种适用）
			

			// 设置下一期开奖时间，及期数
			String preDrawIssueStr = targetLot.getPreDrawIssue();
			Long preDrawIssue = Long.parseLong(preDrawIssueStr);
			String drawIssue = CommUtil.formatTime("yyyyMMdd", cal.getTime());
			
			// 重新设置当前开奖时间
			String currentDayFormatStr=CommUtil.formatTime("yyyy-MM-dd",cal.getTime());
			String tempIssueStr = preDrawIssueStr.replace(drawIssue, "");// 检查期号中除去年月日，最后的长度
			if (AssertValue.isNotNull(endTime)) {
				
				// 检查期号中是否包含年份,获取到期号中的开奖次数
				if (preDrawIssueStr.contains(drawIssue)) {
					int drawIssueLen=preDrawIssueStr.length();
					// 开奖一天内次数小于100
					String currentDrawCountStr=null;
					if (drawCount.intValue() < 100) {
						currentDrawCountStr = preDrawIssueStr.substring(drawIssueLen - 2);
					} else if(drawCount.intValue() >= 100 && drawCount.intValue() < 1000) {
						currentDrawCountStr = preDrawIssueStr.substring(drawIssueLen - 3);
					}else {
						currentDrawCountStr=preDrawIssueStr.substring(8);// 日期之后的第一位
					}
					int tempIssueNum=Integer.parseInt(currentDrawCountStr);
					targetLot.setDrawCount(tempIssueNum);// 当前己开奖次数
					
					if (tempIssueNum==drawCount.intValue()) {// 最后一期
						// 下次开奖时间
						String nextDrawTime = CommUtil.formatTime("HH:mm:ss",beginTime);
						currentDayFormatStr = currentDayFormatStr + " " + nextDrawTime;
						cal.setTime(CommUtil.formatDate(currentDayFormatStr,"yyyy-MM-dd HH:mm:ss"));
						cal.add(Calendar.DATE, 1);// 每天开，所有加1天
						// 新的开奖期数的年份日期
						drawIssue = CommUtil.formatTime("yyyyMMdd", cal.getTime());
						
						StringBuilder tempIssueNo = new StringBuilder("1");
						for (int i = 0; i < tempIssueStr.length() - 1; i++) {
							tempIssueNo.insert(0, 0);
						}
						drawIssue =drawIssue+tempIssueNo.toString();
					} else if (tempIssueNum < drawCount.intValue()) {
						cal.setTime(targetLot.getPreDrawTime());
						cal.add(Calendar.MINUTE, intervalMinute);// 间隔时间为10分钟
						cal.add(Calendar.SECOND, 30);// 增加30秒
						drawIssue=(preDrawIssue+1)+"";
					}
				}else {
					
					// 包含年份不含月份的情况：201801808 ，以当年开奖的累计天数
					String currentYear=cal.get(Calendar.YEAR)+"";
					String totalDayCountStr = null;// 剩余的前三位表示当年开奖的连续天数
					Integer dayDrawCount = null;// 当天开奖次数
					String dayDrawCountStr=null;// 当天开奖期数原字符（“001|01”）
					boolean isContainsYearLotCount = false;// 是包含当年累计开奖开数
					if (preDrawIssueStr.contains(currentYear)) {
						isContainsYearLotCount = true;
						tempIssueStr = preDrawIssueStr.replace(currentYear, "");
						logger.info("--------->>>>现在截取到的累计开奖天数，及当天所开次数(tempIssueStr)："+tempIssueStr);
						totalDayCountStr = tempIssueStr.substring(0, 3);
						dayDrawCountStr = tempIssueStr.replace(totalDayCountStr, "");
						dayDrawCount = Integer.parseInt(dayDrawCountStr);
						targetLot.setDrawCount(dayDrawCount);// 当前己开奖次数
					}
					
					if (!endTime.before(currentTime)) {// 当天内的期数增加
						cal.setTime(targetLot.getPreDrawTime());
						cal.add(Calendar.MINUTE, intervalMinute);// 间隔时间为10分钟
						cal.add(Calendar.SECOND, 30);// 增加30秒
						drawIssue=(preDrawIssue+1)+"";
					} else {// 下一个开奖期数内期数增加
						// 下次开奖时间
						String nextDrawTime = CommUtil.formatTime("HH:mm:ss",beginTime);
						currentDayFormatStr = currentDayFormatStr + " "+ nextDrawTime;
						cal.setTime(CommUtil.formatDate(currentDayFormatStr,"yyyy-MM-dd HH:mm:ss"));
						cal.add(Calendar.DATE, 1);// 每天开，所有加1天
						drawIssue = (preDrawIssue + 1) + "";// 当前期数直接加1
						
						// 包含年份不含月份的情况：201801808 ，以当年开奖的累计天数
						if (isContainsYearLotCount) {
							// 跨年的情况
							Integer totalDayCount = Integer.parseInt(totalDayCountStr);
							int date = cal.get(Calendar.DATE), month = cal.get(Calendar.MONTH);
							if (date == 1 && month == 1) {// 1月1日，即下一期开奖是新的一年
								totalDayCount = 1;
							} else {
								totalDayCount = totalDayCount + 1;// 下期开奖累计开奖开数加一天。
							}
							
							StringBuilder tempTotalDayCountStr=new StringBuilder(totalDayCount+"");
							for (int i = 0; i < (3-totalDayCount.toString().length()); i++) {
								tempTotalDayCountStr.insert(0, "0");
							}
							totalDayCountStr=tempTotalDayCountStr.toString();
							
							// 期数应保持的字符串长度
							StringBuilder countStrSb=new StringBuilder("1");
							for (int i = 0; i < dayDrawCountStr.length() - 1; i++) {
								countStrSb.insert(0,"0");
							}
							// 完整的期号： 年份+当前累计开奖天数+001|01
							drawIssue = currentYear + totalDayCountStr + countStrSb.toString();
						}
						
					}
				}
				
				targetLot.setDrawTime(cal.getTime());
				targetLot.setDrawIssue(drawIssue);
			}

		}
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
	public synchronized AppLotHistory setLFData(AppLotHistory targetLot, Calendar cal) {
		
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
						tempIssueNo.insert(0, "0");
					}
					String newIssueNo = newShortYear+tempIssueNo.toString();
					targetLot.setDrawIssue(newIssueNo);
				}
			}
		}
		
		return targetLot;
	}
	
}
