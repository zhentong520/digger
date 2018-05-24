package com.ko30.service.lotStatistics.handler.quanguo;

import java.lang.reflect.Field;
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
* @Description: 七星彩 开奖号码统计
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月5日 上午10:57:32 
*
 */
@Service
public class Qixingcai_DrawCodeStatistics implements DrawCodeStatisticsHandler {

	private static final Logger logger=Logger.getLogger(Qixingcai_DrawCodeStatistics.class);
	private final String FILED_SUFFIX="Area";// 属性名中包含的字段
	private DrawCodeStatisticsHelper statisticHandler;
	private LotHistoryService lotHistoryService;
	private LotDrawCodeStatisticFacadeService lotDrawCodeStatisticService;

	public Qixingcai_DrawCodeStatistics() {
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
		List<AppLotHistory> lots_currYear=lotHistoryService.getNewListByCountAndLotCode(QuartzHandlerType.QI_XING_CAI.getCode(), null,Calendar.YEAR);
		// 前100条
		List<AppLotHistory> lots_100=lotHistoryService.getNewListByCountAndLotCode(QuartzHandlerType.QI_XING_CAI.getCode(), 100,Calendar.YEAR);
		
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
			LotDrawCodeStatistic statistic = lotDrawCodeStatisticService.queryByLotCodeAndIssue(lot);
			if (AssertValue.isNotNull(statistic)) {
				paramMaps = this.getAreaFiledValue(statistic, paramMaps);
			} else {
				for (int i = 0; i < 7; i++) {
					paramMaps.add(Maps.newConcurrentMap());
				}
			}
		}
	
		//保存统计结果
		List<LotDrawCodeStatistic> statisticList = this.setStatistic(statisticLots, currentRows, paramMaps);
		if (!AssertValue.isNotNullAndNotEmpty(statisticList)) {
			logger.info(lotCode+" --->>>七星彩走势图统计结果列表数量："+statisticList.size());
			return;
		}
		
		// 如果先前的记录不为空，则只保存最后一条
		if (AssertValue.isNotNullAndNotEmpty(paramMaps.get(0))) {
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
		this.setStatistic(statisticLots, 20, paramMaps);
		this.setStatistic(statisticLots, 50, paramMaps);
		if (currentRows==100) {
			//this.setStatistic(statisticLots, 0, paramMaps);
			this.setStatistic(lots_currYear, 0, paramMaps);
		}else {
			this.setStatistic(statisticLots, 100, paramMaps);
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
	private List<LotDrawCodeStatistic> setStatistic(List<AppLotHistory> list,int rows,List<Map<Integer, Integer>> params) {

		// 得到彩种类型
		String name=QuartzHandlerType.QI_XING_CAI.name();
		
		//得到各个字段的统计列表
		Map<String, List<LotDrawCodeStatistic>> dataMaps=Maps.newConcurrentMap();
		int beginPos=1;
		int groupIndex=1;
		for (int i = 0; i < params.size(); i++) {
			List<LotDrawCodeStatistic> data=null;
			data=statisticHandler.setStatisticCommonArea(name, list, 0, 9, beginPos, 1, groupIndex, params.get(i),rows);
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
	
	@Override
	public List<Map<String, Object>> getDrawCodeStatistics(Integer lotCode,Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.QI_XING_CAI;
	}
}
