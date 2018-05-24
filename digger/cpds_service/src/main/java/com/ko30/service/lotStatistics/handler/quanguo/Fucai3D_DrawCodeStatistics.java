package com.ko30.service.lotStatistics.handler.quanguo;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
* @Description: 福彩3D 开奖号码统计
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月5日 上午10:57:32 
*
 */
@Service
public class Fucai3D_DrawCodeStatistics implements DrawCodeStatisticsHandler {

	private static final Logger logger=Logger.getLogger(Fucai3D_DrawCodeStatistics.class);
	private DrawCodeStatisticsHelper statisticHandler;
	private LotHistoryService lotHistoryService;
	private LotDrawCodeStatisticFacadeService lotDrawCodeStatisticService;

	public Fucai3D_DrawCodeStatistics() {
		statisticHandler = SpringUtils.getBean(DrawCodeStatisticsHelper.class);
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
		List<AppLotHistory> lots_currYear=lotHistoryService.getNewListByCountAndLotCode(QuartzHandlerType.FU_CAI_3D.getCode(), null,Calendar.YEAR);
		// 前100条
		List<AppLotHistory> lots_100=lotHistoryService.getNewListByCountAndLotCode(QuartzHandlerType.FU_CAI_3D.getCode(), 100,Calendar.YEAR);
		
		// 得到最后一条记录的相关信息
		int currentRows=0;
		List<AppLotHistory> statisticLots=null;
		AppLotHistory lot = null;
		if (lots_currYear.size() >= lots_100.size()) {
			lot = lots_currYear.get(lots_currYear.size() - 1);
			statisticLots=lots_currYear;
		} else {
			lot = lots_100.get(lots_100.size() - 1);
			statisticLots=lots_100;
			currentRows=100;
		}
		
		Map<Integer, Integer> firstNumberMissCountMap=null;// 百位初始统计
		
		// 将所有的初始记录保存在数组中
		List<Map<Integer, Integer>> paramMaps=Lists.newArrayList();
		if (AssertValue.isNotNull(lot)) {
			LotDrawCodeStatistic statistic=lotDrawCodeStatisticService.queryByLotCodeAndIssue(lot);
			if (AssertValue.isNotNull(statistic)) {
				// 百位初始统计
				firstNumberMissCountMap= JSONObject.parseObject(statistic.getFirstArea(), HashMap.class) ;
				paramMaps.add(firstNumberMissCountMap);
				
				// 十位初始统计
				Map<Integer, Integer> secondNumberMissCountMap= JSONObject.parseObject(statistic.getSecondArea(), HashMap.class) ;
				paramMaps.add(secondNumberMissCountMap);
				// 个位初始统计
				Map<Integer, Integer> thirdNumberMissCountMap= JSONObject.parseObject(statistic.getThirdArea(), HashMap.class) ;
				paramMaps.add(thirdNumberMissCountMap);
				
				// 所有开奖号码并列统计
				Map<Integer, Integer> allNumberMissCountMap= JSONObject.parseObject(statistic.getFourthArea(), HashMap.class) ;
				paramMaps.add(allNumberMissCountMap);
			}else {
				//  初始化4个区位参数
				for (int i = 0; i < 4; i++) {
					paramMaps.add(Maps.newConcurrentMap());
				}
			}
		}
		
		//保存统计结果
		List<LotDrawCodeStatistic> statisticList = this.setFucai3DStatistic(statisticLots, currentRows, paramMaps);
		if (!AssertValue.isNotNullAndNotEmpty(statisticList)) {
			logger.info(lotCode+" --->>>福彩3d走势图统计结果列表数量："+statisticList.size());
			return;
		}
		
		//List<LotDrawCodeStatistic> statisticList=this.setDaletouStatistic(statisticLots, frontNumberMissCountMap,behindNumberMissCountMap,currentRows);
		// 如果先前的记录不为空，则只保存最后一条
		if (AssertValue.isNotNullAndNotEmpty(firstNumberMissCountMap)) {
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
		this.setFucai3DStatistic(statisticLots, 20, paramMaps);
		this.setFucai3DStatistic(statisticLots, 50, paramMaps);
		if (currentRows==100) {
			//this.setFucai3DStatistic(statisticLots, 0, paramMaps);
			this.setFucai3DStatistic(lots_currYear, 0, paramMaps);
		}else {
			this.setFucai3DStatistic(statisticLots, 100, paramMaps);
		}
		
	}
	
	/**
	 * 
	* @Title: setShuangseqiuStatistic 
	* @Description: 设置 福彩3d  遗漏统计数据
	* @param @param list
	* @param @param maxNumber
	* @param @param specialNumberLen
	* @param @param numberMissCountMap
	* @param @return    设定文件 
	* @return List<LotDrawCodeStatistic>    返回类型 
	* @throws
	 */
	private List<LotDrawCodeStatistic> setFucai3DStatistic(List<AppLotHistory> list,int rows,List<Map<Integer, Integer>> params) {

		// 得到彩种类型
		String name=QuartzHandlerType.FU_CAI_3D.name();
		
		//得到各个字段的统计列表
		Map<String, List<LotDrawCodeStatistic>> dataMaps=Maps.newConcurrentMap();
		int beginPos=1;
		int groupIndex=1;
		for (int i = 0; i < params.size(); i++) {
			List<LotDrawCodeStatistic> data=null;
			if (i<3) {
				data=statisticHandler.setStatisticCommonArea(name, list, 0, 9, beginPos, 1, groupIndex, params.get(i),rows);
			}else {// 全部号码统计
				data=statisticHandler.setStatisticCommonArea(name, list, 0, 9, 1, 3, groupIndex, params.get(i),rows);
			}
			dataMaps.put("data_"+i, data);
			beginPos++;
			groupIndex++;
		}
		
		List<LotDrawCodeStatistic> result = Lists.newArrayList();
		rows = rows > 0 ? rows : list.size();// 遍历次数
		rows = rows > list.size() ? list.size():rows;// 查询记录大于了总条数
		for (int i = 0; i < rows; i++) {
			LotDrawCodeStatistic _original=null;// 最初始的对象
			if (AssertValue.isNotNullAndNotEmpty(dataMaps)) {
				Iterator<String> it=dataMaps.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					List<LotDrawCodeStatistic> drawCodeList = dataMaps.get(key);
					LotDrawCodeStatistic tempNew = drawCodeList.get(i);
					if (AssertValue.isNotNull(_original)) {
						BeanUtils.copy(tempNew, _original);
					} else {
						_original = tempNew;
					}
				}
				result.add(_original);
			}
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
		return QuartzHandlerType.FU_CAI_3D;
	}
}
