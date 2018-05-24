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
import com.ko30.service.lotteryInfo.TbLotLatestBonusService;

/**
 * 
* @ClassName: Bjpk10_QuartzHandler 
* @Description: 北京PK拾 定时获取最新开奖记录
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月26日 下午12:31:53 
*
 */
@Service
public class Bjpk10_QuartzHandler implements QuartzHandler {


	private Logger logger = Logger.getLogger(Bjpk10_QuartzHandler.class);
	private LotHistoryService lotHistoryService;
	private LotNewService lotNewService;
	private TbLotLatestBonusService lotLatestBonusService;
	private GrabOpenResultService<?> grabResult;

	public Bjpk10_QuartzHandler() {
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		lotNewService = SpringUtils.getBean(LotNewService.class);
		lotLatestBonusService = SpringUtils.getBean(TbLotLatestBonusService.class);
		grabResult = SpringUtils.getBean(GrabOpenResultService.class);
	}
	

	@Override
	public Map<String, String> handler(JSONObject data) {
		
		Date execDate=setNormalExecDate();// 默认给本期开奖时间
		AppLotHistory lot = new AppLotHistory();
		
		try {
			// 10001 北京PK拾  彩票代码
			List<AppLotHistory> lotList=grabResult.getRemenLotHistoryByLotCode(QuartzHandlerType.BEI_JING_PK_SHI.getCode());
			// 有新数据
			if (AssertValue.isNotNullAndNotEmpty(lotList)) {
				lot = lotList.get(0);
				if (AssertValue.isNotNull(lot.getDrawTime()) && lot.getDrawTime().after(new Date())) {// 在当前时间 之后
					execDate=lot.getDrawTime();// 本期开奖时间
				}else{// 不在当前时间 
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
			logger.info("获取 北京pk拾 新数据成功，下次执行时间："+CommUtil.formatLongDate(execDate));
			
			// 更新预测累计金额
			lotLatestBonusService.updateLatestBonusAmount(lot);
		} catch (Exception e) {
			logger.info("获取 北京pk拾 新数据异常，下次执行时间："+CommUtil.formatLongDate(execDate));
			e.printStackTrace();
		}finally{
			// 为下一次执行作准备
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(lot);
			info.setExecuteKey(QuartzHandlerType.BEI_JING_PK_SHI.getType());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.BEI_JING_PK_SHI, info, execDate);
		}
		return null;
	}

	/**
	 * 
	* @Title: setNormalExecDate 
	* @Description: 设置下次执行时间(正常情况下)  每天 09:07:30 开始 每隔5分钟   23:57:30 结束
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
		currentDateStr = currentDateStr + " 23:59:59";
		Date openLotTime = CommUtil.formatDate(currentDateStr,"yyyy-MM-dd HH:mm:ss");
		if (currentTime.before(openLotTime)) {
			needAddDay = false;
		}
		
		if (needAddDay) {
			// 设置为明天的 09:07:32 开始
			cal.add(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.set(Calendar.MINUTE, 07);
			cal.set(Calendar.SECOND, 32);
		}else {
			cal.add(Calendar.MINUTE, 05);// 当前时间 加5分钟
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
		return QuartzHandlerType.BEI_JING_PK_SHI;
	}

	@Override
	public String getDescription() {
		return QuartzHandlerType.BEI_JING_PK_SHI.getName();
	}

}
