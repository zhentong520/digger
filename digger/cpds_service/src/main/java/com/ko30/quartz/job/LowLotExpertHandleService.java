package com.ko30.quartz.job;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSON;
import com.ko30.cache.RedisCacheUtil;
import com.ko30.entity.model.po.winningInfo.RecommendExpert;
import com.ko30.quartz.job.LowLotExpertHandleUtil.LowLotPlan;
import com.ko30.service.lotteryInfo.RecommendExpertService;

@Transactional
@Service
public class LowLotExpertHandleService {
	@Autowired
	private  RedisCacheUtil<Object> redisCacheUtil;
	@Autowired
	private RecommendExpertService recommendExpertService;
	
	public void recommendExpertHandle(Integer code){
		Object onoff=redisCacheUtil.getCacheObject("EXPERT_ON_OFF");//推荐专家自动生成开关  从reids取
		if(Objects.isNull(onoff)){
			redisCacheUtil.setCacheObject("EXPERT_ON_OFF", "ON", -1);
			onoff="ON";
		}
		if(!Objects.isNull(onoff)&&"ON".equals(onoff.toString())){
				List<LowLotPlan> list=LowLotPlan.getByCode(code);
				LowLotPlan plan;
				RecommendExpert re;
				for(int i=0;i<list.size();i++){//当前彩种多个玩法
					plan=list.get(i);
					String lotCode=plan.getLotCode();//彩种编码
					String planCode=plan.getPlanCode();//彩种玩法
					Integer killNum=plan.getKillNum();  //杀码个数
					String redisKey=plan.getKey();//redis key值
					Object value=redisCacheUtil.getCacheObject(redisKey);
					if(!Objects.isNull(value)){
						Map<String,Object> seatMap=JSON.parseObject(value.toString(),Map.class);
						int maxKillOk=0;//取当前玩法杀码最准的专家数
						String seatSeq="0";//专家席位
						for(Map.Entry<String, Object> entry:seatMap.entrySet()){//循环多个专家
			//				entry.getKey();//专家席位号
							Map<String,Object> detailMap=JSON.parseObject(entry.getValue().toString(),Map.class);//专家最近10条的预测数据
							int seatKillOK=0;//专家杀中次数统计
							for(Map.Entry<String,Object> childEntry:detailMap.entrySet()){//循环每个专家的最近10条数据
								childEntry.getKey();//当前专家的某期期数
								Map<String,Object> map=JSON.parseObject(childEntry.getValue().toString(),Map.class);//某期的预测数据
								Integer hitCnt=Integer.parseInt(map.get("hitCnt").toString());
								boolean b=plan.getPlanType().killCode(killNum, hitCnt);
								if(b){
									seatKillOK++;
								}
							}
							if(seatKillOK>maxKillOk){
								maxKillOk=seatKillOK;
								seatSeq=entry.getKey().replace("seatSeq_", "");//取专家席位号
							}
						}
						recommendExpertService.removeRecommendExpert(lotCode, planCode);//删除上期开奖统计的数据
						List<Map<String,String>> expertList=recommendExpertService.getExpertID(lotCode, planCode, seatSeq);//根据当前彩种 玩法  专家席位  查出专家的id 简介  跳转地址
						re=new RecommendExpert();
						re.setExptSeat(Integer.parseInt(seatSeq));
						re.setEnCode(plan.getEnCode());
						re.setKillNum(maxKillOk);
						re.setLotCode(plan.getLotCode());
						re.setPlanCode(plan.getPlanCode());
						re.setPlanRemark(plan.getName());
						if(expertList.size()>0){
							Map<String,String> map=expertList.get(0);
							re.setExptId(Integer.parseInt(map.get("exptId")));
							re.setLotName(map.get("lotName"));
							re.setLotShortName(map.get("lotShortName"));
							recommendExpertService.saveRecommendExpert(re);//保存当期开奖统计的
						}
						
					}	
				}
		}		
		
	}
	

}
