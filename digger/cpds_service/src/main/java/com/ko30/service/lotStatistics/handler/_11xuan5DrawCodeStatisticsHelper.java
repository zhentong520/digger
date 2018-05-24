package com.ko30.service.lotStatistics.handler;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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
import com.ko30.service.lotteryInfo.LotHistoryService;


/**
 * 
* @ClassName: _11xuan5DrawCodeStatisticsHelper 
* @Description: 11选5 统计辅助类 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月12日 下午7:23:16 
*
 */
public class _11xuan5DrawCodeStatisticsHelper {
	
	private static Logger logger=Logger.getLogger(_11xuan5DrawCodeStatisticsHelper.class);
	
	private final String FILED_SUFFIX="Area";// 属性名中包含的字段
	private DrawCodeStatisticsHelper statisticHandler;
	private LotDrawCodeStatisticFacadeService lotDrawCodeStatisticService;
	private LotHistoryService lotHistoryService;
	
	public _11xuan5DrawCodeStatisticsHelper(){
		statisticHandler=SpringUtils.getBean(DrawCodeStatisticsHelper.class);
		lotDrawCodeStatisticService=SpringUtils.getBean(LotDrawCodeStatisticFacadeService.class);
		lotHistoryService=SpringUtils.getBean(LotHistoryService.class);
	}
	
	
	/**
	 * 
	* @Title: getAreaFiledValue 
	* @Description:获取指定字段的值 
	* @param @param e
	* @param @param filedName
	* @param @param value    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map<Integer, Integer>> getAreaFiledValue(LotDrawCodeStatistic e, List<Map<Integer, Integer>> result) {
		
		if (!AssertValue.isNotNullAndNotEmpty(result)) {
			result=Lists.newArrayList();
		}
		try {
			// 得到实体中所有声明的属性
			Class clz = e.getClass();
			Field[] fs = clz.getDeclaredFields();
			for (int i = 0; i < fs.length; i++) {
				Field f = fs[i];
				String fName = f.getName();
				if (fName.contains(FILED_SUFFIX)) {
					f.setAccessible(true); // 设置些属性是可以访问的
					Object val = f.get(e);// 得到当前属性的值
					if (AssertValue.isNotNull(val)&& AssertValue.isNotNullAndNotEmpty(val.toString())) {
						Map<Integer, Integer>	temp= JSONObject.parseObject(val.toString(), HashMap.class) ;
						result.add(temp);
					}
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} // 得到此属性的值
		
		// 彩种为11选5的情况下， 确保长度为6个。为6个区位赋值
		for (int i = 0; i < 6 - result.size(); i++) {
			result.add(Maps.newConcurrentMap());
		}
		
		return result;
	}
	
	public void setDrawCodeStatistics(Integer lotCode) {
		//当年所有记录  高频彩部分，查询无条数时默认是按天查询
		List<AppLotHistory> lots_currYear=lotHistoryService.getNewListByCountAndLotCode(lotCode, null,Calendar.DATE);
		// 前100条
		List<AppLotHistory> lots_100=lotHistoryService.getNewListByCountAndLotCode(lotCode, 100,Calendar.DATE);
		
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
		
		// 将所有的初始记录保存在数组中
		List<Map<Integer, Integer>> paramMaps=Lists.newArrayList();
		if (AssertValue.isNotNull(lot)) {
			LotDrawCodeStatistic statistic=lotDrawCodeStatisticService.queryByLotCodeAndIssue(lot);
			if (AssertValue.isNotNull(statistic)) {
				paramMaps=this.getAreaFiledValue(statistic, paramMaps);
//				// 1区： 保存任选(全部开奖号)统计结果;
//				firstNumberMissCountMap= JSONObject.parseObject(statistic.getFirstArea(), HashMap.class) ;
//				paramMaps.add(firstNumberMissCountMap); 
//				// 2区：前二组选;
//				secondNumberMissCountMap= JSONObject.parseObject(statistic.getSecondArea(), HashMap.class) ;
//				paramMaps.add(secondNumberMissCountMap);
//				// 3区：前三组选;
//				thirdNumberMissCountMap= JSONObject.parseObject(statistic.getThirdArea(), HashMap.class) ;
//				paramMaps.add(thirdNumberMissCountMap);
//				// 4区：直选第一位;
//				thirdNumberMissCountMap= JSONObject.parseObject(statistic.getThirdArea(), HashMap.class) ;
//				paramMaps.add(thirdNumberMissCountMap);
//				// 5区：直选第二位;
//				thirdNumberMissCountMap= JSONObject.parseObject(statistic.getThirdArea(), HashMap.class) ;
//				paramMaps.add(thirdNumberMissCountMap);
//				// 6区：直选第三位;
//				thirdNumberMissCountMap= JSONObject.parseObject(statistic.getThirdArea(), HashMap.class) ;
//				paramMaps.add(thirdNumberMissCountMap);
	
			}else {
				for (int i = 0; i < 6; i++) {
					paramMaps.add(Maps.newConcurrentMap());
				}
			}
		}
	
		//保存统计结果
		List<LotDrawCodeStatistic> statisticList = this.set11Xuan5Statistic(lotCode,statisticLots, currentRows, paramMaps);
		if (!AssertValue.isNotNullAndNotEmpty(statisticList)) {
			logger.info(lotCode+" --->>>11选5系列走势图统计结果列表数量："+statisticList.size());
			return;
		}
		
		// 如果先前的记录不为空，则只保存最后一条
		if (AssertValue.isNotNullAndNotEmpty(paramMaps.get(0))) {
			LotDrawCodeStatistic ls=statisticList.get(statisticList.size() - 1);
			if (AssertValue.isNotNull(ls)) {
				// 检查该条记录是否已经 存在
				AppLotHistory tempLot=new AppLotHistory();
				BeanUtils.copy(ls, tempLot);
				LotDrawCodeStatistic s = lotDrawCodeStatisticService.queryByLotCodeAndIssue(tempLot);
				if (!AssertValue.isNotNull(s)) {
					lotDrawCodeStatisticService.save(ls);
				}
			}
		} else {
			if (AssertValue.isNotNullAndNotEmpty(statisticList)) {
				lotDrawCodeStatisticService.save(statisticList);
			}
		}
		
		// 获取其它列表统计20，50
		this.set11Xuan5Statistic(lotCode,statisticLots, 20, paramMaps);
		this.set11Xuan5Statistic(lotCode,statisticLots, 50, paramMaps);
		if (currentRows==100) {
			//this.set11Xuan5Statistic(lotCode,statisticLots, 0, paramMaps);	
			this.set11Xuan5Statistic(lotCode,lots_currYear, 0, paramMaps);	
		}else {
			this.set11Xuan5Statistic(lotCode,statisticLots, 100, paramMaps);	
		}
		
	}
	
	/**
	 * 
	* @Title: set11Xuan5Statistic 
	* @Description: 进行统计操作 
	* @param @param lotCode  彩票类型码
	* @param @param list
	* @param @param rows
	* @param @param params
	* @param @return    设定文件 
	* @return List<LotDrawCodeStatistic>    返回类型 
	* @throws
	 */
	private List<LotDrawCodeStatistic> set11Xuan5Statistic(int lotCode,
			List<AppLotHistory> list, int rows,
			List<Map<Integer, Integer>> params) {

		// 设置各个类型统计参数
		int[][] indexArr=new int[params.size()][2];// 保存起始位置和统计内容长度
		// 1区： 保存任选(全部开奖号)统计结果;
		indexArr[0][0] = 1;
		indexArr[0][1] = 5;

		// 2区：前二组选;
		indexArr[1][0] = 1;
		indexArr[1][1] = 2;

		// 3区：前三组选;
		indexArr[2][0] = 1;
		indexArr[2][1] = 3;
		
		// 4区：直选第一位;
		indexArr[3][0] = 1;
		indexArr[3][1] = 1;
		
		// 5区：直选第二位;
		indexArr[4][0] = 2;
		indexArr[4][1] = 1;
		
		// 6区：直选第三位;;
		indexArr[5][0] = 3;
		indexArr[5][1] = 1;
		
		// 得到彩种类型
		QuartzHandlerType type = QuartzHandlerType.getByCode(lotCode);
		if (!AssertValue.isNotNull(type)) {
			return Lists.newArrayList();
		}
		String name = type.name();
		
		//得到各个字段的统计列表
		Map<String, List<LotDrawCodeStatistic>> dataMaps=Maps.newConcurrentMap();
		int groupIndex=1;
		for (int i = 0; i < params.size(); i++) {
			int beginPos=indexArr[i][0];
			int codeLen=indexArr[i][1];
			List<LotDrawCodeStatistic> data=statisticHandler.setStatisticCommonArea(name, list, 01, 11, beginPos, codeLen, groupIndex, params.get(i),rows);
			dataMaps.put("data_"+i, data);
			beginPos++;
			groupIndex++;
		}

		// 将统计的所有区域存入对应统计实体
		List<LotDrawCodeStatistic> result = Lists.newArrayList();
		rows = (rows > list.size() || rows <= 0) ? list.size() : rows;
		for (int i = 0; i < rows; i++) {
			LotDrawCodeStatistic _original=null;// 最初始的对象
			if (AssertValue.isNotNullAndNotEmpty(dataMaps)) {
				Iterator<String> it=dataMaps.keySet().iterator();
				while (it.hasNext()) {
					String key = it.next();
					// 不存在统计信息
					List<LotDrawCodeStatistic> drawCodeList = dataMaps.get(key);
					if (i >= drawCodeList.size()) {
						continue;
					}
					LotDrawCodeStatistic tempNew = drawCodeList.get(i);
					if (AssertValue.isNotNull(_original)) {
						BeanUtils.copy(tempNew, _original);
					} else {
						_original = tempNew;
					}
				}
				
				// 不为空时加入集合中
				if (AssertValue.isNotNull(_original)) {
					result.add(_original);
				}
			}
		}
		return result;
	}


}
