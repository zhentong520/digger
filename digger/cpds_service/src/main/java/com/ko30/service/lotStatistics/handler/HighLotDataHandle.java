package com.ko30.service.lotStatistics.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.ko30.cache.RedisCacheUtil;
import com.ko30.common.util.SpringUtils;
import com.ko30.constant.enums.quartz.Constants.FastThreeNumberType;
import com.ko30.constant.enums.quartz.Constants.LeaveOutType;
import com.ko30.constant.enums.quartz.Constants.LeaveType;
import com.ko30.constant.enums.quartz.Constants.LotCodeNumber;
import com.ko30.constant.enums.quartz.Constants.SeriesType;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.service.lotteryInfo.LotHistoryService;

/**
 * 高频彩数据处理类
 * @author Administrator
 *
 */
@Service
public class HighLotDataHandle {
	@Autowired
	private RedisCacheUtil<Object>  redisUtil;
	
	private LotHistoryService lotHistoryService;
	
	public HighLotDataHandle(){
		lotHistoryService=SpringUtils.getBean(LotHistoryService.class);
	}
	
	public void ChartDataHandle(Integer lotCode){
		List<AppLotHistory> lots_currYear=lotHistoryService.getNewListByCountAndLotCode(lotCode, 100,Calendar.DATE);
		QuartzHandlerType lotteryType=QuartzHandlerType.getByCode(lotCode);
		if(lots_currYear.size()>1){
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			AppLotHistory prelot=lots_currYear.get(1);  //倒数第二位  用来取上期的遗漏值
			AppLotHistory clot=lots_currYear.get(0);  //倒数第一位  最新开奖数据用来更新redis
			//把最新的开奖数据存入redis
			String preLeave=redisUtil.getCacheMapValue(lotteryType.getType(), prelot.getPreDrawIssue());//取出上期redis遗漏值
			Map<String,Object>  map=Maps.newConcurrentMap();
			map.put("preDrawCode", clot.getPreDrawCode());
			map.put("preDrawIssue", clot.getPreDrawIssue());
			map.put("sumNum", clot.getSumNum());
			list.add(map);
			Map<String,Object> temMap=null;
			if(!Objects.isNull(preLeave)){
				temMap=JSON.parseObject(preLeave,Map.class);
			}
			LeaveOutValHandle(list, lotCode.toString(), temMap, lotteryType.getType());
			
			//根据数据库取的的数据  查出redis的遗漏值
			AppLotHistory  temlot=null;
			List<String> redisList_100=new ArrayList<String>();
			List<String> redisList_50=new ArrayList<String>();
			List<String> redisList_20=new ArrayList<String>();
			for(int i=0;i<lots_currYear.size();i++){  //从redis取出list对应遗漏数据
				temlot=lots_currYear.get(i);
				String leaveData=redisUtil.getCacheMapValue(lotteryType.getType(), temlot.getPreDrawIssue());
				if(i<20){
					redisList_20.add(leaveData);
				}
				if(i<50){
					redisList_50.add(leaveData);
				}
				redisList_100.add(leaveData);
			}
			redisUtil.setCacheObject(lotteryType.getType().concat("_20"), JSON.toJSONString(chartDataCount(redisList_20, lotCode.toString())), -1);
			redisUtil.setCacheObject(lotteryType.getType().concat("_50"), JSON.toJSONString(chartDataCount(redisList_50, lotCode.toString())), -1);
			redisUtil.setCacheObject(lotteryType.getType().concat("_100"),JSON.toJSONString(chartDataCount(redisList_100, lotCode.toString())), -1);
		}
	}
		
	
	/**
	 * 遗漏值
	 * @param list      数据库历史开奖数据
	 * @param lotCode   彩票编码
	 * @param map		上期遗漏值
	 * @param redisKey	redis key
	 * @return
	 */
	
	public void LeaveOutValHandle(List<Map<String,Object>> list,String lotCode,Map<String,Object> map,String redisKey){
		HashOperations hashOperations = redisUtil.getRedisTemplateBean().opsForHash();  //redis 操作map
		if(Objects.isNull(map)){    //判断上期遗漏值map是否为空  如果为空就新建一个
			 map=new HashMap<String, Object>();   
		}
		SeriesType seriesType=SeriesType.getByCode(lotCode);  //根据采票编码  取得走势图类型枚举
		Map<String, String> temMap=Maps.newConcurrentMap();   //新建map 存放每个彩种的每种要处理遗漏的类型和数据
		
		Map<String,Object> lot=null;  		 //存放每条数据库记录
		String drawCode=null,issue=null;     //开奖号码和期数
		Integer sumNum=0;					 //开奖号码和值
		for(int i=list.size()-1;i>=0;i--){		
			lot=list.get(i);
			drawCode=(String) lot.get("preDrawCode");			  //开奖号码
			issue=(String) lot.get("preDrawIssue");				  //开奖期数
			sumNum=(Integer) lot.get("sumNum");					  //开奖号码和值
			if(StringUtils.isNotEmpty(drawCode)){                 //判断开奖号码不为空
					String cOddEven=compareValueOddEven(drawCode);//开奖号码奇偶个数比
					String sumOddEven=getValueOddEven(sumNum);    //和值奇偶
					Integer spanVal=getLotNumSpan(drawCode);      //开奖号码跨度
					temMap.put("firstArea", drawCode);	
					temMap.put("secondArea", drawCode);
					temMap.put("thirdArea", sumNum.toString());
					temMap.put("fourArea", sumOddEven);
					temMap.put("eightArea", spanVal.toString());
					temMap.put("nineArea", drawCode);
					switch (seriesType.getValue()) {  //1为快乐十分  2为快3  3为广西快乐十分
						case 2:
							String sumBigSmall=getValueBigSmall(sumNum,2);  //和值大小
							String sumForm=getNumberForm(drawCode);	      //快3的和值形态
							temMap.put("fiveArea", sumBigSmall);
							temMap.put("sevenArea", sumForm);
							break;
						default:
							temMap.put("sixArea", drawCode);
							break;
					}
				    
					for(Map.Entry<String, String> entry:temMap.entrySet()){
						String temKey=entry.getKey();
						LeaveOutType leaveType=LeaveOutType.getByCode(temKey);  //根据key取出遗漏类型枚举
						Object mp=map.get(temKey);
						Map<String, Object> preLeaveMap=oneLeaveHandle(seriesType,entry.getValue(), (Map<String, Object>)mp,leaveType);  //处理遗漏
						map.put(temKey, preLeaveMap);//把遗漏放出map下条记录使用
					}
					map.put("spanVal", getLotNumSpan(drawCode));
					map.put("singleDoubleRatio", cOddEven);
					map.put("lotCode", lotCode);
					map.put("numberSum", sumNum);
					map.put("preDrawIssue", issue);
					map.put("preDrawCode", drawCode);
					switch (seriesType.getValue()) {
						case 2:
							map.put("sumOddEven", sumOddEven);
							map.put("numberForm", getNumberForm(drawCode));
							break;
						default:
							map.put("numOddEven", getNumOddEven(drawCode));
							break;
					}
					hashOperations.put(redisKey, issue, JSON.toJSONString(map));
			}
		}
	}
	
	/**
	 * 一期遗漏处理
	 * @param rray   遗漏值范围  0-6 或 3-18 等
	 * @param num	  要处理的
	 * @param drawCode
	 * @param leaveMap
	 * @return
	 */
	public Map<String, Object> oneLeaveHandle(SeriesType seriesType,String drawCode,Map<String, Object> leaveMap,LeaveOutType leaveType){
		if(Objects.isNull(leaveMap)){
			leaveMap=new LinkedHashMap<String, Object>();
		}
		Map<Integer,String> map=Maps.newConcurrentMap();
		map.put(1, seriesType.getDigitRang()); //五个取值范围
		map.put(2, seriesType.getSumRang());
		map.put(3, seriesType.getOddEven());
		map.put(4, seriesType.getLeaveForm());
		map.put(5, seriesType.getBigSmall());
		LeaveType type=leaveType.getLeaveType();  //用于求遗漏的数据类型
		String [] temArray=null;boolean b=true;//遗漏值范围是数字还是字符串数组标示
		for(Map.Entry<Integer, String> entry:map.entrySet()){
			if(leaveType.getRang()==entry.getKey()){
				String value=entry.getValue();
				temArray=value.split("-");
				for(int i=0;i<temArray.length;i++){
					if(!isInteger(temArray[i])){
						b=false;
					}
				}
				if(StringUtils.isNotEmpty(value)){
					if(b){
						Integer min=Integer.parseInt(temArray[0]);
						Integer max=Integer.parseInt(temArray[temArray.length-1]);
						Map<String,Object> childMap = null;
						for(int j=min;j<=max;j++){  //数字取值范围
							String temKey=String.valueOf(j);
							Object temleaveVal=leaveMap.get(temKey);  //上期遗漏值
							String temFieldKey=temKey.length()==1?"0".concat(temKey):temKey;
							if(seriesType.getValue()==2){
								temFieldKey=temKey;
							}
								switch (type.getKey()) {
									case 1:  //一个值的遗漏
										if(Objects.isNull(temleaveVal)){
											if(drawCode.equals(temKey)){
												leaveMap.put(temKey, 0);
											}else{
												leaveMap.put(temKey, 1);
											}
										}else{
											Integer leaveVal=(Integer)temleaveVal;
											if(leaveVal>0){
												if(drawCode.equals(temKey)){
													leaveMap.put(temKey, 0);
												}else{
													leaveMap.put(temKey, ++leaveVal);
												}
											}else{
												if(drawCode.equals(temKey)){
													leaveMap.put(temKey, --leaveVal);
												}else{
													leaveMap.put(temKey, 1);
												}
											}
										}	
										break;
									case 2:  //多个值的遗漏
										String []temrray=drawCode.split(",");
										if(Objects.isNull(temleaveVal)){
											if(Arrays.asList(temrray).contains(temFieldKey)){
												leaveMap.put(temKey, 0);
											}else{
												leaveMap.put(temKey, 1);
											}
										}else{
											Integer temCount=0;
											for(int c=0;c<temrray.length;c++){
												if(temrray[c].equals(temFieldKey)){
													++temCount;
												}
											}
											Integer leaveValTwo=(Integer)temleaveVal;  //上期遗漏值
											if(leaveValTwo>0){  //上期有遗漏值  上期没开这个数
												if(temCount>1){
													leaveMap.put(temKey, -temCount*100000); 
												}else if(temCount==1){
													leaveMap.put(temKey, 0);
												}else{
													leaveMap.put(temKey, ++leaveValTwo);
												}
											}else{//上期没有遗漏值  上期也开了这个数
												if(temCount>1){
													leaveMap.put(temKey, (-temCount*100000-1)+(leaveValTwo%100000)); 
												}else if(temCount==1){
													leaveMap.put(temKey, leaveValTwo%100000-1);
												}else{
													leaveMap.put(temKey, 1);
												}
											}
										}
										break;
									case 3:  //多个值按位遗漏
//										Object temleaveVal=leaveMap.get(temKey);  //上期遗漏值
										if(Objects.isNull(childMap)){
											childMap=Maps.newConcurrentMap();
										}
//										else{
//											childMap=(Map<String,Object>)temleaveVal;
//										}
										
										String []digitArray=drawCode.split(","); //开奖号码 如:"5,6,8"
										for(int g=0;g<digitArray.length;g++){
											String key=String.valueOf(g);
											Map<String,Integer> preleaveMap=(Map<String,Integer>)leaveMap.get(key);  //上期遗漏map
											Map<String,Object> mm;
											if(Objects.isNull(childMap.get(key))){
												mm=new LinkedHashMap<String, Object>();
											}else{
												mm=(Map<String,Object>)childMap.get(key);
											}
										
											String lotNum=digitArray[g]; //开奖号码某位具体数 如:5
											Integer preLeaveVal=null;
											if(!Objects.isNull(preleaveMap)){
												 preLeaveVal=(Integer)preleaveMap.get(temKey); //上期遗漏值
											}
//											Integer preLeaveVal=(Integer)mm.get(temKey); //上期遗漏值
											if(Objects.isNull(preLeaveVal)){
												if(lotNum.equals(temFieldKey)){
													mm.put(temKey, 0);
												}else{
													mm.put(temKey, 1);
												}
											}else{
												if(preLeaveVal>0){
													if(lotNum.equals(temFieldKey)){
														mm.put(temKey, 0);
													}else{
														mm.put(temKey, ++preLeaveVal);
													}
												}else{
													if(lotNum.equals(temFieldKey)){
														mm.put(temKey, --preLeaveVal);
													}else{
														mm.put(temKey, 1);
													}
												}
											}
											childMap.put(key, mm);
										}
										break;
									default:
										break;
								}
						}
						if(!Objects.isNull(childMap)){
							leaveMap=childMap;
						}
					}else{
						Map<String,Object> childMap = null;
						for(int j=0;j<temArray.length;j++){  //字符串取值范围
							String temKey=temArray[j];
							Object temleaveVal=leaveMap.get(temKey);  //上期遗漏值
							switch (type.getKey()) {
								case 1:  //一个值的遗漏
									if(Objects.isNull(temleaveVal)){
										if(drawCode.equals(temKey)){
											leaveMap.put(temKey, 0);
										}else{
											leaveMap.put(temKey, 1);
										}
									}else{
										Integer leaveVal=(Integer)temleaveVal;
										if(leaveVal>0){
											if(drawCode.equals(temKey)){
												leaveMap.put(temKey, 0);
											}else{
												leaveMap.put(temKey, ++leaveVal);
											}
										}else{
											if(drawCode.equals(temKey)){
												leaveMap.put(temKey, --leaveVal);
											}else{
												leaveMap.put(temKey, 1);
											}
										}
									}	
									break;
								case 3:  //多个值按位遗漏
									if(Objects.isNull(childMap)){
										childMap=Maps.newConcurrentMap();
									}
//									else{
//										childMap=(Map<String,Object>)temleaveVal;
//									}
//									String temFieldKey=temKey.length()==1?"0".concat(temKey):temKey;
									String []digitArray=drawCode.split(","); //开奖号码 如:"5,6,8"
									for(int g=0;g<digitArray.length;g++){
										String key=String.valueOf(g);
										Map<String,Integer> preleaveMap=(Map<String,Integer>)leaveMap.get(key);  //上期遗漏map
										Map<String,Object> mm;
										if(Objects.isNull(childMap.get(key))){
											mm=Maps.newConcurrentMap();
										}else{
											mm=(Map<String,Object>)childMap.get(key);
										}
									
										String lotNum=digitArray[g]; //开奖号码某位具体数 如:5
										String temFieldNum="";
										if(leaveType.getRang()==5){
											temFieldNum=getValueBigSmall(Integer.parseInt(lotNum),seriesType.getValue());
										}else if(leaveType.getRang()==3){
											temFieldNum=getValueOddEven(Integer.parseInt(lotNum));
										}
										
										Integer preLeaveVal=null;
										if(!Objects.isNull(preleaveMap)){
											 preLeaveVal=(Integer)preleaveMap.get(temKey); //上期遗漏值
										}
//										Integer preLeaveVal=(Integer)mm.get(temKey); //上期遗漏值
										if(Objects.isNull(preLeaveVal)){
											if(temFieldNum.equals(temKey)){
												mm.put(temKey, 0);
											}else{
												mm.put(temKey, 1);
											}
										}else{
											if(preLeaveVal>0){
												if(temFieldNum.equals(temKey)){
													mm.put(temKey, 0);
												}else{
													mm.put(temKey, ++preLeaveVal);
												}
											}else{
												if(temFieldNum.equals(temKey)){
													mm.put(temKey, --preLeaveVal);
												}else{
													mm.put(temKey, 1);
												}
											}
										}
										childMap.put(key, mm);
									}
									break;
								default:
									break;
							}
						}
						if(!Objects.isNull(childMap)){
							leaveMap=childMap;
						}
					}
					
				}
			}
		}
		return leaveMap;
	}
	
	
	public  boolean isInteger(String str) {  
	        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");  
	        return pattern.matcher(str).matches();  
	}
	
	
	/**
	 * 开奖号码判断类型
	 * 三不同，三同号，三连号，二同号等
	 * @param no
	 * @return
	 */
	public String getNumberForm(String lotNum){
		if(StringUtils.isEmpty(lotNum)){
			return "";
		}
		String [] lotNums=lotNum.split(",");
		StringBuffer buf=new StringBuffer();
		if(lotNums[0].equals(lotNums[1].equals(lotNums[2]))){  //三同号
			if(buf.length()>0){buf.append(",");}
			buf.append(FastThreeNumberType.THREE_SAME.getKey());
		}
		if(lotNums[0].equals(lotNums[1])||lotNums[0].equals(lotNums[2])||lotNums[1].equals(lotNums[2])){  //二同号
			if(buf.length()>0){buf.append(",");}
			buf.append(FastThreeNumberType.TWO_SAME.getKey());
		}
		if(!lotNums[0].equals(lotNums[1])&&!lotNums[0].equals(lotNums[2])&&!lotNums[1].equals(lotNums[2])){  //三不同
			if(buf.length()>0){buf.append(",");}
			buf.append(FastThreeNumberType.THREE_DIFFERENT.getKey());
		}
		Integer max=Integer.parseInt(lotNums[2]);
		Integer middle=Integer.parseInt(lotNums[1]);
		Integer min=Integer.parseInt(lotNums[0]);
		if((max-middle)==1&&(middle-min)==1){  //三连号
			if(buf.length()>0){buf.append(",");}
			buf.append(FastThreeNumberType.THREE_CONTINUOUS.getKey());
		}
		return buf.toString();
	}
	
	/**
	 * 判断开奖号码和值的奇偶
	 * @param value
	 * @return
	 */
	public String getValueOddEven(Integer value){
		String deVal="";
		Integer tem=value%2;
		if(tem==0){  //偶
			deVal="偶";
		}else{		 //奇
			deVal="奇";
		}
		return deVal;
	}
	/**
	 * 判断开奖号码和值的奇偶
	 * @param value
	 * @return
	 */
	public Object getNumOddEven(String value){
		Map<String,String> map=new LinkedHashMap<String, String>();
		String [] temVal=value.split(",");
		for(int i=0;i<temVal.length;i++){
			map.put(String.valueOf(i), getValueOddEven(Integer.parseInt(temVal[i])));
		}
		return map;
	}
	
	/**
	 * 判断开奖号码和值的大小
	 * @param value
	 * @param flag  彩种标示  用来区分大小的分割范围
	 * @return
	 */
	public String getValueBigSmall(Integer value,Integer flag){
		String deVal="";
		switch (flag) {
			case 2:
				if(value>10&&value<19){  //大
					deVal="大";
				}else if(value<11&&value>2){//小
					deVal="小";
				}
				break;
		default:
			if(value>10&&value<21){  //大
				deVal="大";
			}else if(value<11&&value>0){//小
				deVal="小";
			}
			break;
		}
		return deVal;
	}
	
	/**
	 * 判断开奖号码奇偶比
	 * @param value
	 * @return
	 */
	public String compareValueOddEven(String lotNum){
		if(StringUtils.isEmpty(lotNum)){
			return "";
		}
		String [] lotNums=lotNum.split(",");
		Integer oddNum=0,evenNum=0,tem=0;
		for(int i=0;i<lotNums.length;i++){
			tem=Integer.parseInt(lotNums[i])%2;
			if(tem==0){  //偶
				evenNum++;
			}else{		 //奇
				oddNum++;
			}
		}
		return oddNum.toString().concat(":").concat(evenNum.toString());
	}
	
	/**
	 * 开奖号码跨度：最大值和最小值的差
	 * @param lotNum
	 * @return
	 */
	public Integer getLotNumSpan(String lotNum){
		if(StringUtils.isEmpty(lotNum)){
			return -1;
		}
		String [] lotNums=lotNum.split(",");
		Integer max=null;
		Integer min=null;
		for(int i=0;i<lotNums.length;i++){
			Integer temNum=Integer.parseInt(lotNums[i]);
			if(Objects.isNull(max)&&Objects.isNull(min)){
				max=temNum;
				min=temNum;
			}else{
				max=temNum>max?temNum:max;
				min=temNum<min?temNum:min;
			}
		}
		return max-min;
	}
	
	
	/**
	 * 走势图统计并存入redis 返回结果
	 * @param list         用于统计的数据
	 * @param lotCode	   彩票编码
	 * @param temCount	 统计标示    不为空统计期数段数据不用存入redis   为空  统计最新100，50，20期数据存入redis
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> chartDataCount(List<String> list,String lotCode){
		String [] dataArea={"firstArea","secondArea","thirdArea","fourthArea","fifthArea","sixthArea","seventhArea","eightArea","nineArea","temArea"};
		LotCodeNumber lotCodeNumber=LotCodeNumber.getByCode(Integer.parseInt(lotCode));
		SeriesType  seriesType=SeriesType.getByCode(lotCode);
		
		String [] countArray={"出现次数","平均遗漏","最大遗漏","最大连出"};
		Map<String,Object> countMap=Maps.newConcurrentMap(); //总统计map
		Map<String,Object> areaMap=null;       //存入区域map
		List<Map<String,Object>> dataList=null;//存放区域下data数组
		Map<String,Object> dataMap=null;  //存入具体数据map
		Map<String,Integer> countDetailMap=null;   //遗漏统计map
		for(int i=0;i<lotCodeNumber.getAreaNum();i++){
			Object temAreaMap=countMap.get(dataArea[i]);
			countDetailMap=new LinkedHashMap<String, Integer>();
			if(Objects.isNull(temAreaMap)){
				areaMap=Maps.newConcurrentMap();
			}
			dataList =new ArrayList<Map<String,Object>>();  
			for(int j=list.size()-1;j>=0;j--){   //list遗漏数据
				String ob=list.get(j);
				if(!Objects.isNull(ob)){
						dataMap=Maps.newConcurrentMap();
						Map<String,Object> objectMap= JSON.parseObject(ob,Map.class);
						dataMap.put("spanVal", objectMap.get("spanVal")); //跨度
						dataMap.put("singleDoubleRatio", objectMap.get("singleDoubleRatio"));//奇偶比
						dataMap.put("lotCode", objectMap.get("lotCode"));//彩票编码
						dataMap.put("numberSum", objectMap.get("numberSum"));//和值
						dataMap.put("preDrawIssue", objectMap.get("preDrawIssue"));//开奖期数
						dataMap.put("preDrawCode", objectMap.get("preDrawCode"));//开奖号码
						if(seriesType.getValue()==2){ //快3的属性
							dataMap.put("sumOddEven", objectMap.get("sumOddEven"));//和值奇偶
							dataMap.put("numberForm", objectMap.get("numberForm"));//开奖号码形态  三不同 三连号等
						}else{
							dataMap.put("numOddEven", objectMap.get("numOddEven"));//开奖号码每位奇偶
						}
						if(i==lotCodeNumber.getAreaNum()-1){
							//secondArea
							Map<String,Object> secondDataMap=JSON.parseObject(objectMap.get("secondArea").toString(),Map.class);
							dataMap.put("codeDetail", secondDataMap);
							dataList.add(dataMap);
							
							for(Map.Entry<String, Object> detailEntry:secondDataMap.entrySet()){		//开奖号码每位的具体遗漏数据
								String detailEntryKey=detailEntry.getKey();
								Integer value=(Integer)detailEntry.getValue();
								for(int a=0;a<countArray.length;a++){  //4种统计
									String countKey=countArray[a];
									String leaveKey=dataArea[i].concat("_").concat(detailEntryKey).concat(countKey);
									Integer countValue=Objects.isNull(countDetailMap.get(leaveKey))?0:countDetailMap.get(leaveKey);
									switch (countKey) {
										case "出现次数":
											countValue=value>0?countValue:++countValue;
											break;
										case "最大遗漏":
											countValue=value>countValue?value:countValue;
											break;
										case "最大连出":
											countValue=(value%100000<=-countValue)?Math.abs(value%100000-1):countValue;
											break;
										case "平均遗漏":
											Integer drawNum=countDetailMap.get(dataArea[i].concat("_").concat(detailEntryKey).concat("出现次数"));
											if(drawNum>0){
												countValue=(list.size()-drawNum)/drawNum;
											}else{
												countValue=list.size();
											}
											break;	
										default:
											break;
									}
									countDetailMap.put(leaveKey, countValue);
								}
							}
						}else{
							//firstArea
							Map<String,Object> firstDataMap=JSON.parseObject(objectMap.get("firstArea").toString(),Map.class);
							for(Map.Entry<String, Object> entry:firstDataMap.entrySet()){   //开奖号码按位遗漏的数据
								String entryKey=entry.getKey();
								if(String.valueOf(i).equals(entryKey)){
									dataMap.put("codeDetail", entry.getValue());
									dataList.add(dataMap);
									Map<String,Object> firstChildMap=JSON.parseObject(entry.getValue().toString(),Map.class);
									for(Map.Entry<String, Object> detailEntry:firstChildMap.entrySet()){		//开奖号码每位的具体遗漏数据
										String detailEntryKey=detailEntry.getKey();
										Integer value=(Integer)detailEntry.getValue();
										for(int a=0;a<countArray.length;a++){  //4种统计
											String countKey=countArray[a];
											String leaveKey=dataArea[i].concat(entryKey).concat("_").concat(detailEntryKey).concat(countKey);
											Integer countValue=Objects.isNull(countDetailMap.get(leaveKey))?0:countDetailMap.get(leaveKey);
											switch (countKey) {
												case "出现次数":
													countValue=value>0?countValue:++countValue;
													break;
												case "最大遗漏":
													countValue=value>countValue?value:countValue;
													break;
												case "最大连出":
													countValue=(value%100000<=-countValue)?Math.abs(value%100000-1):countValue;
													break;
												case "平均遗漏":
													Integer drawNum=countDetailMap.get(dataArea[i].concat(entryKey).concat("_").concat(detailEntryKey).concat("出现次数"));
													if(drawNum>0){
														countValue=(list.size()-drawNum)/drawNum;
													}else{
														countValue=list.size();
													}
													break;	
												default:
													break;
											}
											countDetailMap.put(leaveKey, countValue);
										}
									}
								}
							}//开奖号码按位  firstArea
						}
						
						
						
						if(j==0){  //把统计的数据加入data list
							Map<String,Object> map,temMap;
							for(int b=0;b<countArray.length;b++){
								map=new LinkedHashMap<String, Object>();
								temMap=new LinkedHashMap<String, Object>();
								String key=countArray[b];
								for(Map.Entry<String, Integer> en:countDetailMap.entrySet()){
									if(en.getKey().contains(key)){
										map.put(String.valueOf(map.size()+1), en.getValue());
									}
								}
								temMap.put("preDrawIssue", key);
								temMap.put("codeDetail", map);
								dataList.add(temMap);
							}
						}
				}		
			}
			areaMap.put("data", dataList);
			countMap.put(dataArea[i], areaMap);
		}
		return countMap;
	}
	
	
}
