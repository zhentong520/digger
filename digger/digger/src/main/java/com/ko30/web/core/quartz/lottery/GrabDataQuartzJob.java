package com.ko30.web.core.quartz.lottery;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ko30.common.util.AssertValue;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.quartz.service.GrabOpenResultService;
import com.ko30.service.lotteryInfo.LotHistoryService;


/**
 * 
* @ClassName: GrabDataQuartzJob 
* @Description: 定时获取各种彩票的最新开奖 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月25日 下午12:29:41 
*
 */
public class GrabDataQuartzJob {

	@Autowired
	private LotHistoryService lotHistoryService;
	
	@Autowired
	private GrabOpenResultService<?> grabResult;
	
	
	/**
	 * 
	* @Title: getShuangseqiuLottoryData 
	* @Description: 获取双色球开奖
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getShuangseqiuLottoryData() {
		List<AppLotHistory> lotList = grabResult.getLotHistoryByLotCode(QuartzHandlerType.SHUANG_SE_QIU.getCode());
		if (AssertValue.isNotNullAndNotEmpty(lotList)) {// 有新数据
			lotHistoryService.save(lotList);
		}
	}
	
	/**
	 * 
	* @Title: getFucai3DLotteryData 
	* @Description: TODO(这里用一句话描述这个方法的作用) 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void getFucai3DLotteryData(){
		
	}
}
