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
import com.ko30.constant.enums.LotteryDvnHistoryKeys;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.AppLotNew;
import com.ko30.quartz.core.handler.QuartzHandler;
import com.ko30.quartz.core.handler.QuartzHandlerFactory;
import com.ko30.quartz.job.OperationRedisDataHelper;
import com.ko30.quartz.service.GrabOpenResultService;
import com.ko30.service.lotStatistics.DrawCodeStatisticsFactory;
import com.ko30.service.lotteryInfo.LotHistoryService;
import com.ko30.service.lotteryInfo.LotNewService;
import com.ko30.service.lotteryInfo.TbLotLatestBonusService;

/**
 * 
* @ClassName: Xjssc_QuartzHandler 
* @Description:新疆时时彩 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月29日 下午3:09:52 
*
 */
@Service
public class Xjssc_QuartzHandler implements QuartzHandler {

	private Logger logger = Logger.getLogger(Xjssc_QuartzHandler.class);
	private LotHistoryService lotHistoryService;
	private LotNewService lotNewService;
	private GrabOpenResultService<?> grabResult;
	private TbLotLatestBonusService  lotLatestBonusService;
	private DrawCodeStatisticsFactory drawCodeStatisticsFactory;

	public Xjssc_QuartzHandler() {
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		lotNewService = SpringUtils.getBean(LotNewService.class);
		grabResult = SpringUtils.getBean(GrabOpenResultService.class);
		lotLatestBonusService = SpringUtils.getBean(TbLotLatestBonusService.class);
		drawCodeStatisticsFactory = SpringUtils.getBean(DrawCodeStatisticsFactory.class);
	}

	@Override
	public Map<String, String> handler(JSONObject data) {

		Date execDate = setNormalExecDate();// 默认给本期开奖时间
		AppLotHistory lot = new AppLotHistory();
		
		try {
			// 10004 新疆时时彩 彩票代码
			List<AppLotHistory> lotList = grabResult.getRemenLotHistoryByLotCode(QuartzHandlerType.XIN_JIANG_SHI_SHI_CAI.getCode());
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
				
				// 设置预测结果中的开奖信息
				this.setDvnHistoryData(lot);
				// 新增成功，重新统计
				drawCodeStatisticsFactory.setDrawCodeStatistics(QuartzHandlerType.XIN_JIANG_SHI_SHI_CAI.getCode());
				// 更新预测累计金额
				lotLatestBonusService.updateLatestBonusAmount(lot);
			}else {
				execDate=setUnnormalExecDate();// 稍后再次执行
			}
			logger.debug("获取 新疆时时彩  新数据成功，下次执行时间："+ CommUtil.formatLongDate(execDate));
		} catch (Exception e) {
			logger.debug("获取 新疆时时彩  新数据异常，下次执行时间："+ CommUtil.formatLongDate(execDate));
		}finally{
			// 为下一次执行作准备
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(lot);
			info.setExecuteKey(QuartzHandlerType.XIN_JIANG_SHI_SHI_CAI.getType());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.XIN_JIANG_SHI_SHI_CAI, info,execDate);
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
		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TJ_SSC_POS1_KILL02_HISTORY.getValue(), lot);
		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TJ_SSC_POS2_KILL02_HISTORY.getValue(), lot);
		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TJ_SSC_POS3_KILL02_HISTORY.getValue(), lot);
		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TJ_SSC_POS4_KILL02_HISTORY.getValue(), lot);
		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.TJ_SSC_POS5_KILL02_HISTORY.getValue(), lot);
		
		// 设置上期杀码预测开奖结果
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TJ_SSC_POS1_KILL02.getValue(), lot,null);
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TJ_SSC_POS2_KILL02.getValue(), lot,null);
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TJ_SSC_POS3_KILL02.getValue(), lot,null);
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TJ_SSC_POS4_KILL02.getValue(), lot,null);
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.TJ_SSC_POS5_KILL02.getValue(), lot,null);
		
		// 幸运号码中设置开奖结果
		String key=LotteryDvnHistoryKeys.TJ_SSC_LUCK.getValue()+"_"+lot.getPreDrawIssue();
		OperationRedisDataHelper.setLotLuckNumDvnResult(key, lot);
	}
	
	
	/**
	 * 
	 * @Title: setNormalExecDate
	 * @Description: 设置下次执行时间(正常情况下) 每10分钟 从 10:09:50 到次日 01:59:50 结束
	 * @param @return 设定文件
	 * @return Calendar 返回类型
	 * @throws
	 */
	private Date setNormalExecDate() {

		// 得到当前时分
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		
		// 在  10:09:50 　分之后到次日 01:59:50 之前 
		if (hour < 1 || (hour == 1 && minute < 59) || (hour > 10 || (hour == 10 && minute >= 9))) {
			cal.add(Calendar.MINUTE, 10);// 每10分钟
		} else if (hour == 1 && minute >= 59) {
			// 设置下一个时间点执行 次日 09:10:30
			cal.set(Calendar.HOUR_OF_DAY, 10);
			cal.set(Calendar.MINUTE, 9);
			cal.set(Calendar.SECOND, 50);
		} else if (hour > 1 && (hour < 10 || (hour == 10 && minute < 9))) {
			cal.set(Calendar.HOUR_OF_DAY, 10);
			cal.set(Calendar.MINUTE, 9);
			cal.set(Calendar.SECOND, 50);
		}
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
		cal.add(Calendar.SECOND, 25);//25秒后再次执行
		return cal.getTime();
	}
	
	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.XIN_JIANG_SHI_SHI_CAI;
	}

	@Override
	public String getDescription() {
		return QuartzHandlerType.XIN_JIANG_SHI_SHI_CAI.getName();
	}

}
