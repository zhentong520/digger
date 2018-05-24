package com.ko30.quartz.job.lottery.quanguo;

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
import com.ko30.constant.enums.LotteryDvnHistoryKeys;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.AppLotNew;
import com.ko30.quartz.core.handler.QuartzHandler;
import com.ko30.quartz.core.handler.QuartzHandlerFactory;
import com.ko30.quartz.job.LowLotExpertHandleService;
import com.ko30.quartz.job.OperationRedisDataHelper;
import com.ko30.quartz.service.GrabOpenResultService;
import com.ko30.service.lotStatistics.DrawCodeStatisticsFactory;
import com.ko30.service.lotteryInfo.LotHistoryService;
import com.ko30.service.lotteryInfo.LotNewService;
import com.ko30.service.lotteryInfo.TbLotLatestBonusService;

/**
 * 
* @ClassName: QixingcaiQuartzHandler 
* @Description: 七星彩获取最新开奖记录 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月25日 下午5:04:01 
*
 */
@Service
public class QixingcaiQuartzHandler implements QuartzHandler {


	private Logger logger = Logger.getLogger(QixingcaiQuartzHandler.class);
	private LotHistoryService lotHistoryService;
	private LotNewService lotNewService;
	private GrabOpenResultService<?> grabResult;
	private DrawCodeStatisticsFactory drawCodeStatisticsFactory;
	private TbLotLatestBonusService lotLatestBonusService;
	private LowLotExpertHandleService  lowLotExpertHandleService; 

	public QixingcaiQuartzHandler() {
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		lotNewService = SpringUtils.getBean(LotNewService.class);
		grabResult = SpringUtils.getBean(GrabOpenResultService.class);
		drawCodeStatisticsFactory = SpringUtils.getBean(DrawCodeStatisticsFactory.class);
		//更新预测累计金额
		lotLatestBonusService = SpringUtils.getBean(TbLotLatestBonusService.class);
		lowLotExpertHandleService = SpringUtils.getBean(LowLotExpertHandleService.class);
	}
	

	@Override
	public Map<String, String> handler(JSONObject data) {
		
		Date execDate=setNormalExecDate();// 默认给本期开奖时间
		AppLotHistory lot = new AppLotHistory();
		
		try {
			// 10045  七星彩 彩票代码
			List<AppLotHistory> lotList=grabResult.getLotHistoryByLotCode(QuartzHandlerType.QI_XING_CAI.getCode());
			// 有新数据
			if (AssertValue.isNotNullAndNotEmpty(lotList)) {
				lot = lotList.get(0);
				// 非168开奖网数据时
				if (!AssertValue.isNotNull(lot.getDrawTime())) {
					lot.setDrawTime(execDate);
				}
				if (AssertValue.isNotNull(lot.getDrawTime()) && lot.getDrawTime().after(new Date())) {// 在当前时间 之后
					execDate=lot.getDrawTime();// 本期开奖时间
				}else {// 不在当前时间 
					execDate=setUnnormalExecDate();// 稍后再次执行
				}
				
				lotHistoryService.save(lotList);
				
				// 更新最新记录
				AppLotNew lotNew = new AppLotNew();
				BeanUtils.copy(lot, lotNew);
				lotNewService.update2New(lotNew);
				
				// 设置预测结果中的开奖信息
				this.setDvnHistoryData(lot);
				logger.info("获取 七星彩  新数据成功，下次执行时间：" + CommUtil.formatLongDate(execDate)+" 期数："+lot.getPreDrawIssue());
				// 新增成功，重新统计
				drawCodeStatisticsFactory.setDrawCodeStatistics(QuartzHandlerType.QI_XING_CAI.getCode());
				//开奖时更新推荐专家数据到数据库
				lowLotExpertHandleService.recommendExpertHandle(QuartzHandlerType.QI_XING_CAI.getCode());
			}else {
				execDate=setUnnormalExecDate();// 稍后再次执行
			}
		} catch (Exception e) {
			logger.info("获取 七星彩  新数据异常，下次执行时间：" + CommUtil.formatLongDate(execDate));
			e.printStackTrace();
		}finally{
			// 为下一次执行作准备
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(lot);
			info.setExecuteKey(QuartzHandlerType.QI_XING_CAI.getType());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.QI_XING_CAI, info, execDate);
		}
		
		return null;
	}

	
	
	/**
	 * 
	 * @Title: setDvnHistoryData
	 * @Description: 设置预测历史数据中的开奖结果
	 * @param @param lot 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void setDvnHistoryData(AppLotHistory lot){

		// 杀码历史预测
//		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS1_KILL02_HISTORY.getValue(), lot);
//		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS2_KILL02_HISTORY.getValue(), lot);
//		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS3_KILL02_HISTORY.getValue(), lot);
//		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS4_KILL02_HISTORY.getValue(), lot);
//		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS5_KILL02_HISTORY.getValue(), lot);
//		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS6_KILL02_HISTORY.getValue(), lot);
//		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS7_KILL02_HISTORY.getValue(), lot);
		
		// 幸运号码中设置开奖结果
		String key=LotteryDvnHistoryKeys.TC_7XC_LUCK.getValue()+"_"+lot.getPreDrawIssue();
		OperationRedisDataHelper.setLotLuckNumDvnResult(key, lot);
		
		//--------  设置杀码预测结果  ------------
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS1_KILL02.getValue(), lot,null);
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS2_KILL02.getValue(), lot,null);
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS3_KILL02.getValue(), lot,null);
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS4_KILL02.getValue(), lot,null);
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS5_KILL02.getValue(), lot,null);
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS6_KILL02.getValue(), lot,null);
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TC_7XC_POS7_KILL02.getValue(), lot,null);
		
		// 更新预测中奖金额
		lotLatestBonusService.updateLatestBonusAmount(lot);
	}
	
	
	/**
	 * 
	* @Title: setNormalExecDate 
	* @Description: 设置下次执行时间(正常情况下)  每周二、五、日20:30:00开奖
	* @param @return    设定文件 
	* @return Calendar    返回类型 
	* @throws
	 */
	private Date setNormalExecDate() {
		
		// 设置下次执行时间
		Calendar cal = Calendar.getInstance();
		
		int currWeekday=cal.get(Calendar.DAY_OF_WEEK)-1;
		boolean needAddDay=true;
		if (currWeekday == 2 || currWeekday == 5 || currWeekday == 0) {
			// 当前时间在开奖期间之前 ，不需要加天数
			Date currentTime=new Date();
			String currentDateStr=CommUtil.formatShortDate(currentTime);
			currentDateStr=currentDateStr+" 20:31:00";
			Date openLotTime=CommUtil.formatDate(currentDateStr,"yyyy-MM-dd HH:mm:ss");
			if (currentTime.before(openLotTime)) {
				needAddDay = false;
			}
		}
		
		if (needAddDay) {
			// 非开奖日期的时候
			while (true) {
				cal.add(Calendar.DATE, 1);
				int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
				if (weekDay == 2 || weekDay == 5 || weekDay == 0) {
					break;
				}
			}
		}
		
		
		cal.set(Calendar.HOUR_OF_DAY, 20);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 10);
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
		cal.add(Calendar.MINUTE, 1);// 1分钟后再次执行
		return cal.getTime();
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.QI_XING_CAI;
	}

	@Override
	public String getDescription() {
		return QuartzHandlerType.QI_XING_CAI.getName();
	}



}
