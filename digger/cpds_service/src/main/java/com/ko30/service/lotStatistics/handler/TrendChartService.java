package com.ko30.service.lotStatistics.handler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.ko30.cache.RedisCacheUtil;
import com.ko30.common.base.entity.search.SearchOperator;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.util.SpringUtils;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TbHistoryStatistics;
import com.ko30.facade.lotteryInfo.IHistoryStatistic;
import com.ko30.service.lotStatistics.TrendChartCount.CountType;
import com.ko30.service.lotStatistics.TrendChartCount.ParmType;
import com.ko30.service.lotStatistics.TrendChartData;
import com.ko30.service.lotStatistics.TrendChartData.DataRange;
import com.ko30.service.lotStatistics.TrendChartData.HFLotChartPlan;
import com.ko30.service.lotStatistics.TrendChartData.LotDrwawValue;
import com.ko30.service.lotStatistics.TrendChartData.LotType;
import com.ko30.service.lotteryInfo.HistoryStatisticService;
import com.ko30.service.lotteryInfo.LotHistoryService;


@Service
public class TrendChartService{
	
	private static final Logger logger=Logger.getLogger(TrendChartService.class);
	
	private RedisCacheUtil<Object>  redisUtil;
	
	private LotHistoryService lotHistoryService;
	
	private IHistoryStatistic historyStatistic;
	
	@SuppressWarnings("unchecked")
	public  TrendChartService(){
		redisUtil=SpringUtils.getBean(RedisCacheUtil.class);
		lotHistoryService=SpringUtils.getBean(LotHistoryService.class);
		historyStatistic=SpringUtils.getBean(HistoryStatisticService.class);
	}

	
	@SuppressWarnings("unchecked")
	public void queryLotHistoryList(AppLotHistory lot){
		
		try {
			Integer lotCode=lot.getLotCode();
			LotType lotType=LotType.getLotTypeByCode(lotCode.toString());  //获取彩种系列
			if(Objects.isNull(lotType)){
				return;
			}
			Map<String,String> map=redisUtil.getCacheMap("HISTORY_STATISTIC_INIT_FLAG");
			List<AppLotHistory> lots_currYear=null;
			if(Objects.isNull(map)){//没初始化
				map=Maps.newConcurrentMap();
			}
			if(Objects.isNull(map.get(lotCode.toString()))){
				lots_currYear=lotHistoryService.getNewListByCountAndLotCode(lotCode, 100,Calendar.DATE);//查出最新100条开奖记录
				LeaveDataHandle(lots_currYear, lotCode, null,null);//初始化100条记录 存入tb_history_statistics 遗漏表
				map.put(lotCode.toString(), "inited");
				redisUtil.getRedisTemplateBean().opsForHash().putAll("HISTORY_STATISTIC_INIT_FLAG", map);//设初始化标示
			}
//			else{
//				lots_currYear=lotHistoryService.getNewListByCountAndLotCode(lotCode, 1,Calendar.DATE);//查出最新1条开奖记录
//				if(lots_currYear.size()>0){
//					AppLotHistory clot=lots_currYear.get(0);  //最新的一条开奖记录
					String preIssue=lot.getPreDrawIssue();
					List<Map<String,Object>> preHistory=historyStatistic.queryPreData(lotCode.toString(), preIssue);
					if(preHistory.size()>0){
						Map<String,Object> m=null;
						Object missingData=preHistory.get(0).get("missingData");
						if(Objects.nonNull(missingData)){
							m=JSON.parseObject(missingData.toString(), Map.class);
						}
						lots_currYear=new ArrayList<AppLotHistory>();
						lots_currYear.add(lot);
						LeaveDataHandle(lots_currYear, lotCode, m,preHistory.get(0).get("drawCode"));//初始化100条记录 存入tb_history_statistics 遗漏表
					}
//				}
//			}
			QuartzHandlerType lotteryType=QuartzHandlerType.getByCode(lotCode);
			List<Map<String,Object>> historylist=historyStatistic.queryNumList(lotCode.toString());
			List<Map<String,Object>> list20=new ArrayList<Map<String,Object>>(historylist.subList(0, 20));
			List<Map<String,Object>> list50=new ArrayList<Map<String,Object>>(historylist.subList(0, 50));
			Collections.reverse(list20);//倒排
			Collections.reverse(list50);
			Collections.reverse(historylist);
			Map<String,Object> map20=LeaveDataCountHandle(list20, lotCode.toString());
			Map<String,Object> map50=LeaveDataCountHandle(list50, lotCode.toString());
			Map<String,Object> map100=LeaveDataCountHandle(historylist, lotCode.toString());
			redisUtil.setCacheObject(lotteryType.name().concat("_NEW_").concat("_20"), JSON.toJSONString(map20), -1);
			redisUtil.setCacheObject(lotteryType.name().concat("_NEW_").concat("_50"), JSON.toJSONString(map50), -1);
			redisUtil.setCacheObject(lotteryType.name().concat("_NEW_").concat("_100"), JSON.toJSONString(map100), -1);
		
		} catch (Exception e) {
			String lotName=lot.getLotName();
			logger.info("----------->>> "+lotName+" 封装走势图数据发生异常",e);
		}
	}
	
	/**
	 * 遗漏数据处理
	 * 
	 * @param list      //从数据查询出来的开奖历史数据
	 * @param lotCode	//彩种编码
	 * @param preMap  //上期遗漏数据
	 * @param preCod  //上期开奖号码
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void LeaveDataHandle(List<AppLotHistory> list,Integer lotCode,Map<String,Object> preMap,Object preCod){
		String lotCodeStr=lotCode.toString();
		LotType lotType=LotType.getLotTypeByCode(lotCodeStr);  //获取彩种系列
		if(Objects.isNull(lotType)){
			return;
		}
		ParmType parmType=ParmType.getBySeries(lotType.getSeries());
		List<HFLotChartPlan> planList=parmType.getDetailType().getplans();//根据系列拿出要处理的遗漏list
		AppLotHistory map=null;//存放每期的开奖数据  history
		JSONObject temMap =null; 
		String preCode=null; //上期开奖号码
		if(Objects.nonNull(preCod)){
			 preCode=preCod.toString();
		}
		TbHistoryStatistics tb=null;
		for(int i=list.size()-1;i>=0;i--){  //循环开奖期数
			if(Objects.isNull(preMap)){//上期遗漏值
				preMap=Maps.newConcurrentMap();
			}
			temMap=new JSONObject(true);//为true 保证子map顺序
			map=list.get(i);
			String preDrawCode=map.getPreDrawCode();
			String preDrawIssue=map.getPreDrawIssue();
			String drawIssue=map.getDrawIssue();
			Date drawTime=map.getPreDrawTime();
			Integer sum=map.getSumNum();
			if(Objects.isNull(sum)){
				Object temSum=LotDrwawValue.SUM_NUM.convert(preDrawCode);
				if(Objects.nonNull(temSum)){
					sum=Integer.parseInt(temSum.toString());
				}
			}
			HFLotChartPlan plan=null;//存放每个彩种的 遗漏处理类型
			for(int j=0;j<planList.size();j++){  //循环遗漏处理方法  一个彩种多个遗漏要处理
				plan=planList.get(j);
				String key=plan.getKey();
				Map<String,Object> preDetailMap=null; //上期遗漏中的一种遗漏
				Object ob=preMap.get(key);
				if(!Objects.isNull(ob)){
					preDetailMap=JSON.parseObject(ob.toString(), Map.class);
				}
				DataRange rangeType=plan.getDataRange();//取值范围类型
				LotDrwawValue handleType=plan.getLotDrwawValue();//开奖号码数据处理类型
				String dataSource=handleType.convert(preDrawCode);//把开奖号码处理成要算遗漏的数据格式
				String leaveRang=rangeType.getRange();//具体遗漏的取值范围
				if(rangeType==DataRange.FIVETEEN_RANGE){//升平降传进去的号码特殊处理  要把上期对应的的开奖号码传进出  第一位为上期号码  第二位为本期号码  格式 :2,3
					String preCodeSource="0";
					if(StringUtils.isNotEmpty(preCode)){
						preCodeSource=handleType.convert(preCode);
					}
					dataSource=preCodeSource.concat(",").concat(dataSource);
				}
				Map<String,Object> leaveMap=plan.getLeaveValueHandle().leaveHandle(dataSource, preDetailMap, leaveRang);//某一种遗漏处理
				preMap.put(key, JSON.toJSONString(leaveMap));
				temMap.put(key, JSON.toJSONString(leaveMap));
			}
			if(lotType.getSeries()==2){
				temMap.put("doubleSignCount", TrendChartData.doubleSignCount(preCode,preDrawCode));
			}
			temMap.put("code", preDrawCode);
			temMap.put("issue", preDrawIssue);
			temMap.put("sum", sum);
			preCode=preDrawCode;
			
			Searchable search = Searchable.newSearchable();
			search.addSearchFilter("lotCode", SearchOperator.eq, lotCode);
			search.addSearchFilter("drawIssue", SearchOperator.eq, preDrawIssue);
			List<TbHistoryStatistics> statisticList = historyStatistic.findAllWithSort(search);
			if(statisticList.size()==0){
				tb=new TbHistoryStatistics();
				tb.setDrawCode(preDrawCode);
				tb.setDrawIssue(preDrawIssue);
				tb.setLotCode(lotCode.toString());
				tb.setSubDrawIssue(drawIssue);
				tb.setDrawTime(drawTime);
				tb.setMissingData(JSON.toJSONString(temMap));
				historyStatistic.save(tb);
			}
			
		}
	}
	
	
	

	/**
	 * 遗漏数据统计
	 * @parm list  遗漏数据
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String,Object> LeaveDataCountHandle(List<Map<String,Object>> list,String lotCodeStr){
		LotType lotType=LotType.getLotTypeByCode(lotCodeStr);  //获取彩种系列
		/**
		 * 拼成返回给前端的格式
		 */
		Map<String,Object> reMap=Maps.newConcurrentMap();   //返回前端map
		
		/**
		 * 统计map存放统计的数据  最后放入reMap里面
		 */
//		Map<String,Object> countMap=Maps.newConcurrentMap();   //统计map   {first:[{出现次数}{平均遗漏}{最大遗漏}{最大连出}]}
		Map<String,Integer> mmp=new LinkedHashMap<String, Integer>();//存放比较后的所有统计数据
		CountType [] countArray=CountType.values();//四种统计方式
		
		
		Map<String,Object> map=null;  
		for(int i=0;i<list.size();i++){//多期遗漏循环统计
			map=JSON.parseObject(list.get(i).get("missingData").toString(),Map.class);  //每期遗漏
			String drawCode=map.get("code").toString();//开奖号码
			String issue=map.get("issue").toString();//开奖期号
			Object sum=map.get("sum");//开奖和值
			Object doubleSignCount=map.get("doubleSignCount");//开奖重号
			Map fieldMap=lotType.getLotField().Attributes(drawCode,sum,issue,lotCodeStr);
			for(Map.Entry<String, Object> entry:map.entrySet()){
				//返回格式组装
				String key=entry.getKey(); //key 值为区域
				if("code".equals(key)||"issue".equals(key)||"sum".equals(key)||"doubleSignCount".equals(key)){ //开奖号码  这几个字段不做后续处理 跳出当前循环
					continue;
				}
				Object data=reMap.get(key);
				if(Objects.isNull(data)){//初始化每个区域的map
					data=Maps.newConcurrentMap();//存放每个区域的map
					reMap.put(key, data);
				}
				Object rray=((Map)data).get("data");
				if(Objects.isNull(rray)){//初始化每个map里的data数组
					rray=new ArrayList<Map<String,Object>>();  //存放每个区域的data数组
					((Map)data).put("data",rray);
				}
//				Map<String,Object> 	detailMap=Maps.newConcurrentMap();//临时存放每期每个区域数组的对象
				JSONObject detailMap = new JSONObject(true);//为true 保证子map顺序
				
				//统计格式计算组装
				Map<String,Object> value=JSON.parseObject(entry.getValue().toString(), LinkedHashMap.class); //value 为某区域遗漏值
				
				detailMap.put("codeDetail", value);
				if(!Objects.isNull(doubleSignCount)){  //11选5重号字段 上期开奖号码和本期开奖号码相同号码的个数
					detailMap.put("doubleSignCount", doubleSignCount);
				}
				detailMap.putAll(fieldMap);//走势图其它字段
				((List)rray).add(detailMap);
				
				
				for(Map.Entry<String, Object> detailEntry:value.entrySet()){//具体统计处理
					String temKey=detailEntry.getKey();
					Integer val=Integer.valueOf(detailEntry.getValue().toString());
					String mapKey=key.concat("_").concat(temKey);
					for(CountType type :countArray){
						String kk=mapKey.concat("_").concat(type.getName());
						Integer temNum=mmp.get(kk);
						if(Objects.isNull(temNum)){
							temNum=0;
						}
						switch (type) {
							case OCCURRENCE_NUMBER:
								if(val<1){
									++temNum;
								}
								break;
							case AVG_LEAVE:
								if(i==list.size()-1){ 
									String occurrenceKey=mapKey.concat("_").concat("occurrenceNumber");
									Integer occurrenceNum=mmp.get(occurrenceKey);
									temNum=occurrenceNum!=0?((list.size()-occurrenceNum)/occurrenceNum):list.size();
								}
								break;	
							case MAX_LEAVE:
								if(val>temNum){
									temNum=val;
								}
								break;	
							case MAX_CONTINU:
								if(val<1){
									Integer a=val%100000;
									if(Math.abs(a)>=Math.abs(temNum)){
										temNum=Math.abs(a)+1;
									}
								}
								break;
							default:
								break;
						}
						mmp.put(kk, temNum);
					}
				}
			}
			
			if(i==list.size()-1){  //最后把统计的数据放入reMap 返回
//				Map dataMap=null;
				JSONObject dataMap=null; 
				for(Map.Entry<String, Object> entry :reMap.entrySet()){
					String key=entry.getKey();//区域 firstArea:{data}
					Map value=(Map)entry.getValue();//JSON.parseObject(entry.getValue().toString(),Map.class);//map
					List<Map> detailList=(ArrayList)value.get("data");
					for(CountType type :countArray){
//						dataMap=Maps.newConcurrentMap();
						dataMap= new JSONObject(true);//为true 保证子map顺序
						dataMap.put("preDrawIssue", type.getRemark());
						for(Map.Entry<String, Integer> centry:mmp.entrySet()){
							String groupKey=centry.getKey();
							if(groupKey.contains(key)&&groupKey.contains(type.getName())){
								Object ob=dataMap.get("codeDetail");
								if(Objects.isNull(ob)){
									ob=new LinkedHashMap<String, Object>();
									dataMap.put("codeDetail", ob);
								}
								String stepKey=groupKey.replace(key+"_", "").replace("_"+type.getName(), "");
								((Map)ob).put(stepKey, centry.getValue());
							}
						}
						detailList.add(dataMap);
					}
				}
			}
		}
		
		return reMap;
	}
}
