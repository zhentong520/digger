package com.ko30.web.controller.lotteryInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ko30.quartz.service.GrabLotteryHistoryService;
import com.ko30.web.controller.base.BaseController;

/**
 * 
* @ClassName: LotHistory 
* @Description: 抓取历史开奖控制器 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月30日 上午9:13:56 
*
 */
@Controller
@RequestMapping(value = "/lotHistory")
public class LotHistoryController extends BaseController {

	
	/****** 热门彩 type=groupCode=4  ******/	
	//	#### PK拾
	//	http://api.1680210.com/pks/getPksHistoryList.do?date=2017-08-14&lotCode=10001
	//	#### 极速赛车
	//	http://api.1680210.com/pks/getPksHistoryList.do?date=2017-08-01&lotCode=10037
	//	重庆时时彩
	//	http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?date=2017-08-02&lotCode=10002
	//	天津时时彩
	//	http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?date=2017-08-08&lotCode=10003
	//	新疆时时彩
	//	http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?date=2017-08-16&lotCode=10004
	//	极速时时彩
	//	http://api.1680210.com/CQShiCai/getBaseCQShiCaiList.do?date=2017-08-07&lotCode=10036
	//	十一运夺金
	//	http://api.1680210.com/ElevenFive/getElevenFiveList.do?date=2017-08-23&lotCode=10008
	//	广东快乐10分
	//	http://api.1680210.com/klsf/getHistoryLotteryInfo.do?date=2017-08-23&lotCode=10005
	//	重庆幸运农场
	//	http://api.1680210.com/klsf/getHistoryLotteryInfo.do?date=2017-08-16&lotCode=10009
	//	广西快乐10分
	//	http://api.1680210.com/gxklsf/getHistoryLotteryInfo.do?date=2017-08-08&lotCode=10038
	//	pc蛋蛋幸运28
	//	http://api.1680210.com/LuckTwenty/getPcLucky28List.do?date=2017
		
	@Autowired
	private GrabLotteryHistoryService grabLotteryHistoryService;
	
	
	/***
	 * 
	* @Title: grabLotHistory 
	* @Description: 获取不同请求的历史记录 
	* @param @return    设定文件 
	* @return Object    返回类型 
	* @throws
	 */
	public Object grabLotHistory(){
		grabLotteryHistoryService.doSaveRemenLotterys();
		return "";
	}
}
