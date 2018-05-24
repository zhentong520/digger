package com.ko30.service.lotStatistics.handler.quanguo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ko30.cache.RedisCacheUtil;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.BeanUtils;
import com.ko30.common.util.SpringUtils;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.LotDrawCodeStatistic;
import com.ko30.facade.lotteryInfo.LotDrawCodeStatisticFacadeService;
import com.ko30.service.lotStatistics.DrawCodeStatisticsHandler;
import com.ko30.service.lotStatistics.handler.DrawCodeStatisticsHelper;
import com.ko30.service.lotteryInfo.LotHistoryService;


/**
 * 
* @ClassName: ShuangseqiuDrawCodeStatistics 
* @Description: 双色球开奖号码统计
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月5日 上午10:57:32 
*
 */
@Service
public class Shuangseqiu_DrawCodeStatistics implements DrawCodeStatisticsHandler {

	private static final Logger logger=Logger.getLogger(Shuangseqiu_DrawCodeStatistics.class);
	private DrawCodeStatisticsHelper statisticHandler;
	private RedisCacheUtil<Object> redisCacheUtil;
	private LotHistoryService lotHistoryService;
	private LotDrawCodeStatisticFacadeService lotDrawCodeStatisticService;

	public Shuangseqiu_DrawCodeStatistics() {
		statisticHandler = SpringUtils.getBean(DrawCodeStatisticsHelper.class);
		redisCacheUtil = SpringUtils.getBean(RedisCacheUtil.class);
		lotHistoryService = SpringUtils.getBean(LotHistoryService.class);
		lotDrawCodeStatisticService = SpringUtils.getBean(LotDrawCodeStatisticFacadeService.class);
	}
	
	/**
	 * 保存统计结果
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setDrawCodeStatistics(Integer lotCode){

		//当年所有记录
		List<AppLotHistory> lots_currYear=lotHistoryService.getNewListByCountAndLotCode(QuartzHandlerType.SHUANG_SE_QIU.getCode(), null,Calendar.YEAR);
		// 前100条
		List<AppLotHistory> lots_100=lotHistoryService.getNewListByCountAndLotCode(QuartzHandlerType.SHUANG_SE_QIU.getCode(), 100,Calendar.YEAR);
		
		// 得到最后一条记录的相关信息
		List<AppLotHistory> statisticLots=null;
		
		int currentRows=0;// 代表当年，或当天，即查询条件中不按该字段查询 
		AppLotHistory lot = null;
		if (lots_currYear.size() >= lots_100.size()) {
			lot = lots_currYear.get(lots_currYear.size() - 1);
			statisticLots=lots_currYear;
		} else {
			lot = lots_100.get(lots_100.size() - 1);
			statisticLots=lots_100;
			currentRows=100;
		}
		
		Map<Integer, Integer> frontNumberMissCountMap=null;// 前区初始统计
		Map<Integer, Integer> behindNumberMissCountMap=null;// 前区初始统计
		if (AssertValue.isNotNull(lot)) {
			LotDrawCodeStatistic statistic=lotDrawCodeStatisticService.queryByLotCodeAndIssue(lot);
			if (AssertValue.isNotNull(statistic)) {
				frontNumberMissCountMap= JSONObject.parseObject(statistic.getFirstArea(), HashMap.class) ;
				behindNumberMissCountMap= JSONObject.parseObject(statistic.getSecondArea(), HashMap.class) ;
			}
		}
		
		//保存统计结果
		List<LotDrawCodeStatistic> statisticList=this.setShuangseqiuStatistic(statisticLots, frontNumberMissCountMap,behindNumberMissCountMap,currentRows);
		if (!AssertValue.isNotNullAndNotEmpty(statisticList)) {
			logger.info(lotCode+" --->>>双色球走势图统计结果列表数量："+statisticList.size());
			return;
		}
		
		// 如果先前的记录不为空，则只保存最后一条
		if (AssertValue.isNotNullAndNotEmpty(frontNumberMissCountMap)) {
			LotDrawCodeStatistic ls=statisticList.get(statisticList.size() - 1);
			// 检查该条记录是否已经 存在
			AppLotHistory tempLot=new AppLotHistory();
			BeanUtils.copy(ls, tempLot);
			LotDrawCodeStatistic s = lotDrawCodeStatisticService.queryByLotCodeAndIssue(tempLot);
			if (!AssertValue.isNotNull(s)) {
				lotDrawCodeStatisticService.save(ls);
			}
		} else {
			lotDrawCodeStatisticService.save(statisticList);
		}
		
		// 获取其它列表统计20，50
		this.setShuangseqiuStatistic(statisticLots, frontNumberMissCountMap,behindNumberMissCountMap,20);
		this.setShuangseqiuStatistic(statisticLots, frontNumberMissCountMap,behindNumberMissCountMap,50);
		if (currentRows==100) {
			//this.setShuangseqiuStatistic(statisticLots, frontNumberMissCountMap,behindNumberMissCountMap,0);	
			this.setShuangseqiuStatistic(lots_currYear, frontNumberMissCountMap,behindNumberMissCountMap,0);	
		}else {
			this.setShuangseqiuStatistic(statisticLots, frontNumberMissCountMap,behindNumberMissCountMap,100);	
			
		}
	}
	
	/**
	 * 
	* @Title: setShuangseqiuStatistic 
	* @Description: 设置双色球遗漏统计数据
	* @param @param list
	* @param @param maxNumber
	* @param @param specialNumberLen
	* @param @param numberMissCountMap
	* @param @return    设定文件 
	* @return List<LotDrawCodeStatistic>    返回类型 
	* @throws
	 */
	private  List<LotDrawCodeStatistic> setShuangseqiuStatistic(List<AppLotHistory> list,Map<Integer, Integer> first_numberMissCountMap,Map<Integer, Integer> second_numberMissCountMap,int rows){
		
		// 得到彩种类型
		String name=QuartzHandlerType.SHUANG_SE_QIU.name();
		List<LotDrawCodeStatistic> firstData = statisticHandler.setStatisticFirstArea(name,list, 33, 1, first_numberMissCountMap,rows);
		List<LotDrawCodeStatistic> secondData = statisticHandler.setStatisticSecondArea(name,list, 16, 1, second_numberMissCountMap,rows);
		
		
		List<LotDrawCodeStatistic> result = Lists.newArrayList();
		rows = rows > 0 ? rows : list.size();// 遍历次数
		rows = rows > list.size() ? list.size():rows;// 查询记录大于了总条数
		for (int i = 0; i < rows; i++) {
			LotDrawCodeStatistic _first=firstData.get(i);
			LotDrawCodeStatistic _seconde=secondData.get(i);
			
			BeanUtils.copy(_first, _seconde);
			result.add(_seconde);
		}
		
		return result;
	}

	@Override
	public List<Map<String, Object>> getDrawCodeStatistics(Integer lotCode,Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.SHUANG_SE_QIU;
	}
}
