package com.ko30.quartz.job.lottery.jingwai;

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

/**
 * 
* @ClassName: Aozhouxingyun20_QuartzHandler 
* @Description: 定时获取澳洲幸运20 最新开奖记录执行服务类
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月1日 下午2:04:18 
*
 */
@Service
public class Aozhouxingyun20_QuartzHandler implements QuartzHandler {


	private Logger logger = Logger.getLogger(Aozhouxingyun20_QuartzHandler.class);
	private LotHistoryService lotHistoryService;
	private LotNewService lotNewService;
	private GrabOpenResultService<?> grabResult;

	public Aozhouxingyun20_QuartzHandler() {
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		lotNewService = SpringUtils.getBean(LotNewService.class);
		grabResult = SpringUtils.getBean(GrabOpenResultService.class);
	}
	

	@Override
	public Map<String, String> handler(JSONObject data) {
		
		Date execDate=this.setNormalExecDate();// 下次执行时间
		AppLotHistory lot = new AppLotHistory();
		try {
			// 10013  澳洲幸运20  彩票代码
			List<AppLotHistory> lotList=grabResult.getJingwaiLotHistoryByLotCode(QuartzHandlerType.AO_ZHOU_XING_YUN_20.getCode());
			// 有新数据
			if (AssertValue.isNotNullAndNotEmpty(lotList)) {
				lot = lotList.get(0);
				if (AssertValue.isNotNull(lot.getDrawTime()) && lot.getDrawTime().after(new Date())) {// 在当前时间 之后
					//execDate=lot.getDrawTime();// 本期开奖时间
					execDate= CommUtil.convertAozhouTime2Local(lot.getDrawTime());// 本期开奖时间
				}else if ( !lot.getDrawTime().after(new Date())) {// 不在当前时间 
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
			logger.info("获取 澳洲幸运20 新数据成功，下次执行时间：" + CommUtil.formatLongDate(execDate));
		} catch (Exception e) {
			logger.info("获取 澳洲幸运20 新数据异常，下次执行时间：" + CommUtil.formatLongDate(execDate));
		}finally{
			// 为下一次执行作准备
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(lot);
			info.setExecuteKey(QuartzHandlerType.AO_ZHOU_XING_YUN_20.getType());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.AO_ZHOU_XING_YUN_20, info, execDate);
		}
		
		return null;
	}
	
	/**
	 * 
	* @Title: setNormalExecDate 
	* @Description: 设置下次执行时间
	* @param @return    设定文件 
	* @return Date    返回类型 
	* @throws
	 */
	private Date setNormalExecDate() {
		// 设置下次执行时间
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 05);// 当前时间 加5分钟
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
		return QuartzHandlerType.AO_ZHOU_XING_YUN_20;
	}

	@Override
	public String getDescription() {
		return QuartzHandlerType.AO_ZHOU_XING_YUN_20.getName();
	}

}
