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
* @ClassName: Syyunduojin_QuartzHandler 
* @Description: 十一运夺金 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月29日 下午2:23:22 
*
 */
@Service
public class Syyunduojin_QuartzHandler implements QuartzHandler {


	private Logger logger = Logger.getLogger(Syyunduojin_QuartzHandler.class);
	private LotHistoryService lotHistoryService;
	private LotNewService lotNewService;
	private GrabOpenResultService<?> grabResult;
	private TbLotLatestBonusService lotLatestBonusService;

	public Syyunduojin_QuartzHandler() {
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		lotNewService = SpringUtils.getBean(LotNewService.class);
		grabResult = SpringUtils.getBean(GrabOpenResultService.class);
		lotLatestBonusService = SpringUtils.getBean(TbLotLatestBonusService.class);
	}
	

	@Override
	public Map<String, String> handler(JSONObject data) {
		
		Date execDate=setNormalExecDate();// 默认给本期开奖时间
		AppLotHistory lot = new AppLotHistory();
		
		try {
			// 10008  十一运夺金   彩票代码
			List<AppLotHistory> lotList=grabResult.getRemenLotHistoryByLotCode(QuartzHandlerType.SHI_YI_YUN_DUO_JIN.getCode());
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
				// 修改预测的累计中奖金额
				lotLatestBonusService.updateLatestBonusAmount(lot);
			}else {
				execDate=setUnnormalExecDate();// 稍后再次执行
			}
			logger.debug("获取 十一运夺金  新数据成功，下次执行时间："+CommUtil.formatLongDate(execDate));
		} catch (Exception e) {
			logger.debug("获取 十一运夺金  新数据异常，下次执行时间："+CommUtil.formatLongDate(execDate));
			e.printStackTrace();
		}finally{
			// 为下一次执行作准备
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(lot);
			info.setExecuteKey(QuartzHandlerType.SHI_YI_YUN_DUO_JIN.getType());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.SHI_YI_YUN_DUO_JIN, info, execDate);
		}

		return null;
	}

	/**
	 * 
	* @Title: setNormalExecDate 
	* @Description: 设置下次执行时间(正常情况下)
	*  	每10分钟  从 09:05:01  到    23:55:01  结束
	* @param @return    设定文件 
	* @return Calendar    返回类型 
	* @throws
	 */
	private Date setNormalExecDate() {
		
		//得到当前时分
		Calendar cal = Calendar.getInstance();
		int hour=cal.get(Calendar.HOUR_OF_DAY);
		int minute = cal.get(Calendar.MINUTE);
		// 在 23:55:01　分之前
		if (hour < 23 || (hour == 23 && minute < 55)) {
			cal.add(Calendar.MINUTE, 5);// 每75秒钟
		} else if (hour == 23 && minute == 55) {
			// 设置下一个时间点执行 次日 09:05:01
			cal.add(Calendar.DATE, 1);
			cal.set(Calendar.HOUR_OF_DAY, 9);
			cal.set(Calendar.MINUTE, 05);
			cal.set(Calendar.SECOND, 01);
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
		return QuartzHandlerType.SHI_YI_YUN_DUO_JIN;
	}

	@Override
	public String getDescription() {
		return QuartzHandlerType.SHI_YI_YUN_DUO_JIN.getName();
	}

}
