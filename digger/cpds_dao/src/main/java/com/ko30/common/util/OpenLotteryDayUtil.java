package com.ko30.common.util;

import java.util.Calendar;


/**
 * 
* @ClassName: OpenLotteryDayUtil 
* @Description: 指定下一个开奖日期工具类
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月25日 下午2:21:58 
*
 */
public class OpenLotteryDayUtil {

	/**
	 * 
	* @Title: setShuangseqiuLotteryNewWeekDay 
	* @Description: 设置 双色球 当前星期数对应的下一个执行日期   2\4\0 21点30
	* @param @param cal
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public static int setShuangseqiuLotteryNewWeekDay(Calendar cal) {
		// 得到在本地中的星期数
		int weekDay = CommUtil.getLocalDayInWeek();
		switch (weekDay) {
		case 0:
		case 2:
		case 5:
			cal.add(Calendar.DATE, 2);// 到星期二，四，日
			break;
		case 1:
		case 3:
		case 6:
			cal.add(Calendar.DATE, 1);// 到星期二，四
			break;
		case 4:
			cal.add(Calendar.DATE, 3);// 到星期二，四，日
			break;
		default:
			break;
		}
		return weekDay;
	}
	
	
	/**
	 * 
	* @Title: setQilecaiLotteryNewWeekDay 
	* @Description:  设置七乐彩 下一个开奖日期 (每周一、三、五21:30:00开奖)
	* @param @param cal
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public static int setQilecaiLotteryNewWeekDay(Calendar cal) {
		// 得到在本地中的星期数
		int weekDay = CommUtil.getLocalDayInWeek();
		switch (weekDay) {
		case 1:
		case 3:
		case 6:
			cal.add(Calendar.DATE, 2);// 到星期一，三，五
			break;
		case 0:
		case 2:
		case 4:
			cal.add(Calendar.DATE, 1);// 到星期二，四，日
		case 5:
			cal.add(Calendar.DATE, 3);// 到星期二，四，日
			break;
		default:
			break;
		}
		return weekDay;
	}
	
	/**
	 * 
	* @Title: setDaletouLotteryNewWeekDay 
	* @Description:  设置大乐透 下一次的开奖时间 （ 每周一、三、六20:30:00开奖） 
	* @param @param cal
	* @param @return    设定文件 
	* @return int    返回类型 
	* @throws
	 */
	public static int setDaletouLotteryNewWeekDay(Calendar cal){
		int weekDay = CommUtil.getLocalDayInWeek();
		switch (weekDay) {
		case 1:
		case 4:
		case 6:
			cal.add(Calendar.DATE, 2);// 到星期一，三，
			break;
		case 0:
		case 2:
		case 5:
			cal.add(Calendar.DATE, 1);// 到星期一，三，六
			break;
		case 3:
			cal.add(Calendar.DATE, 3);// 到星期一，三，六
			break;
		default:
			break;
		}
		return weekDay;
	}
	
	/**
	 * 
	 * @Title: setQixingcaiLotteryNewWeekDay
	 * @Description: 设置 七星彩的下一个开奖时间 （七星彩 每周二、五、日20:30:00开奖）
	 * @param @param cal
	 * @param @return 设定文件
	 * @return int 返回类型
	 * @throws
	 */
	public static int setQixingcaiLotteryNewWeekDay(Calendar cal) {
		int weekDay = CommUtil.getLocalDayInWeek();
		switch (weekDay) {
		case 1:
		case 4:
		case 6:
			cal.add(Calendar.DATE, 1);// 到星期二，五，日，
			break;
		case 0:
		case 3:
		case 5:
			cal.add(Calendar.DATE, 2);// 到星期二，五，日
			break;
		case 2:
			cal.add(Calendar.DATE, 3);// 到星期五
			break;
		default:
			break;
		}
		return weekDay;
	}
}
