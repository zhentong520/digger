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
* @ClassName: Fucai3DQuartzHandler 
* @Description: 获取福彩3D最新开奖
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月25日 上午10:32:51 
*
 */
@Service
public class Fucai3DQuartzHandler implements QuartzHandler {


	private Logger logger = Logger.getLogger(Fucai3DQuartzHandler.class);
	private LotHistoryService lotHistoryService;
	private LotNewService lotNewService;
	private GrabOpenResultService<?> grabResult;
	private DrawCodeStatisticsFactory drawCodeStatisticsFactory;
	private TbLotLatestBonusService lotLatestBonusService;
	private LowLotExpertHandleService  lowLotExpertHandleService; 

	public Fucai3DQuartzHandler() {
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
			// 10041 为福彩3D彩票代码
			List<AppLotHistory> lotList=grabResult.getLotHistoryByLotCode(QuartzHandlerType.FU_CAI_3D.getCode());
			// 有新数据
			if (AssertValue.isNotNullAndNotEmpty(lotList)) {
				lot = lotList.get(0);
				// 如果有空的，填入下次开奖时间（非168开奖网数据时）
				if (!AssertValue.isNotNull(lot.getDrawTime())) {
					lot.setDrawTime(execDate);
				}
				if (AssertValue.isNotNull(lot.getDrawTime()) && lot.getDrawTime().after(new Date())) {// 在当前时间 之后
					execDate = lot.getDrawTime();// 本期开奖时间
				} else {// 不在当前时间
					execDate = setUnnormalExecDate();// 稍后再次执行
				}
				lotHistoryService.save(lotList);
				
				// 更新最新记录
				AppLotNew lotNew = new AppLotNew();
				BeanUtils.copy(lot, lotNew);
				lotNewService.update2New(lotNew);
				
				// 设置预测开奖的开奖结果
				this.setDvnHistoryData(lot);
				logger.info("获取 福彩3D 新数据成功，下次执行时间：" + CommUtil.formatLongDate(execDate)+" 期数："+lot.getPreDrawIssue());
				// 新增成功，重新统计
				drawCodeStatisticsFactory.setDrawCodeStatistics(QuartzHandlerType.FU_CAI_3D.getCode());
				//开奖时更新推荐专家数据到数据库
				lowLotExpertHandleService.recommendExpertHandle(QuartzHandlerType.FU_CAI_3D.getCode());
			}else {
				execDate=setUnnormalExecDate();// 稍后再次执行
			}
		} catch (Exception e) {
			logger.info("获取 福彩3D 新数据失败，下次执行时间：" + CommUtil.formatLongDate(execDate));
			e.printStackTrace();
		}finally{
			// 为下一次执行作准备
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(lot);
			info.setExecuteKey(QuartzHandlerType.FU_CAI_3D.getType());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.FU_CAI_3D, info, execDate);
		}
		
		return null;
	}

	/**
	 * 
	* @Title: setNormalExecDate 
	* @Description: 设置下次执行时间(正常情况下) 每日21:15:00开奖
	* @param @return    设定文件 
	* @return Calendar    返回类型 
	* @throws
	 */
	private Date setNormalExecDate() {
		
		// 设置下次执行时间
		Calendar cal = Calendar.getInstance();
		boolean needAddDay = true;
		// 如果当前时间在21:15:00之前，21:15执行，否则在明天的21:15:00执行
		Date currentTime = new Date();
		String currentDateStr = CommUtil.formatShortDate(currentTime);
		currentDateStr = currentDateStr + " 21:16:00";
		Date openLotTime = CommUtil.formatDate(currentDateStr,"yyyy-MM-dd HH:mm:ss");
		if (currentTime.before(openLotTime)) {
			needAddDay = false;
		}
		
		if (needAddDay) {
			// 非开奖日期的时候
			cal.add(Calendar.DATE, 1);
		}
		
		cal.set(Calendar.HOUR_OF_DAY, 21);
		cal.set(Calendar.MINUTE, 15);
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
		cal.add(Calendar.MINUTE, 1);//1分钟后再次执行
		return cal.getTime();
	}

	
	/**
	 * 
	* @Title: setDvnHistoryData 
	* @Description: 设置预测历史数据中的开奖结果
	* @param @param lot    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	private void setDvnHistoryData(AppLotHistory lot){

		// 设置百位杀 2 码开奖结果（第一位）
		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.CN_F3D_POS1_KILL02_HISTORY.getValue(), lot);
		// 设置十位杀 2 码开奖结果（第二位）
		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.CN_F3D_POS2_KILL02_HISTORY.getValue(), lot);
		// 设置个位杀 2 码开奖结果（第三位）
		OperationRedisDataHelper.setLotkillNumDvnResult(LotteryDvnHistoryKeys.CN_F3D_POS3_KILL02_HISTORY.getValue(), lot);
		
		// 幸运号码中设置开奖结果
		String key=LotteryDvnHistoryKeys.CN_F3D_LUCK.getValue()+"_"+lot.getPreDrawIssue();
		OperationRedisDataHelper.setLotLuckNumDvnResult(key, lot);
		
		// 更新当前期数的杀码结果
		// 设置百位杀 2 码开奖结果（第一位）
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.CN_F3D_POS1_KILL02.getValue(), lot,null);
		// 设置十位杀 2 码开奖结果（第二位）
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.CN_F3D_POS2_KILL02.getValue(), lot,null);
		// 设置个位杀 2 码开奖结果（第三位）
		OperationRedisDataHelper.setNewestOpenLotKillNumDvnResult(LotteryDvnHistoryKeys.CN_F3D_POS3_KILL02.getValue(), lot,null);
		
		// 更新预测累计金额
		lotLatestBonusService.updateLatestBonusAmount(lot);
	}
	
	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.FU_CAI_3D;
	}

	@Override
	public String getDescription() {
		return QuartzHandlerType.FU_CAI_3D.getName();
	}

}
