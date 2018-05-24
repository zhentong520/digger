package com.ko30.quartz.job;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 低频彩推荐专家数据处理
 * @author Administrator
 *
 */
public class LowLotExpertHandleUtil {
	
	public enum  LowLotPlan{
		//福彩双色球
		CN_F2B_POS2_KILL05("10039","CN_F2B","POS2_KILL05","CN_F2B_POS2_KILL05_HISTORY","稳赚蓝球杀五码",5,PlanType.ALL_Ok),
		CN_F2B_POS1_KILL03("10039","CN_F2B","POS1_KILL03","CN_F2B_POS1_KILL03_HISTORY","稳赚红球杀三码",3,PlanType.ALL_Ok),
		CN_F2B_POS1_KILL06("10039","CN_F2B","POS1_KILL06","CN_F2B_POS1_KILL06_HISTORY","稳赚红球杀六码",6,PlanType.WRONG_ONE),
		CN_F2B_POS1_KILL10("10039","CN_F2B","POS1_KILL10","CN_F2B_POS1_KILL10_HISTORY","稳赚红球杀十码",10,PlanType.WRONG_TWO),
		
		//超级大乐透
		SU_DLT_POS2_KILL05("10040","SU_DLT","POS2_KILL05","SU_DLT_POS2_KILL05_HISTORY","稳赚后区杀五码",5,PlanType.ALL_Ok),
		SU_DLT_POS1_KILL03("10040","SU_DLT","POS1_KILL03","SU_DLT_POS1_KILL03_HISTORY","稳赚前区杀三码",3,PlanType.ALL_Ok),
		SU_DLT_POS1_KILL06("10040","SU_DLT","POS1_KILL06","SU_DLT_POS1_KILL06_HISTORY","稳赚前区杀六码",6,PlanType.WRONG_ONE),
		SU_DLT_POS1_KILL10("10040","SU_DLT","POS1_KILL10","SU_DLT_POS1_KILL10_HISTORY","稳赚前区杀十码",10,PlanType.WRONG_TWO),
		
		//福彩3D
		CN_F3D_POS1_KILL02("10041","CN_F3D","POS1_KILL02","CN_F3D_POS1_KILL02_HISTORY","时光个位杀二码",2,PlanType.ALL_Ok),
		CN_F3D_POS2_KILL02("10041","CN_F3D","POS2_KILL02","CN_F3D_POS2_KILL02_HISTORY","时光十位杀二码",2,PlanType.ALL_Ok),
		CN_F3D_POS3_KILL02("10041","CN_F3D","POS3_KILL02","CN_F3D_POS3_KILL02_HISTORY","时光百位杀二码",2,PlanType.ALL_Ok),
		
		//福彩七乐彩
		CN_7LC_POS1_KILL03("10042","CN_7LC","POS1_KILL03","CN_7LC_POS1_KILL03_HISTORY","稳赚杀三码",3,PlanType.ALL_Ok),
		CN_7LC_POS1_KILL06("10042","CN_7LC","POS1_KILL06","CN_7LC_POS1_KILL06_HISTORY","稳赚杀六码",6,PlanType.WRONG_ONE),
		CN_7LC_POS1_KILL10("10042","CN_7LC","POS1_KILL10","CN_7LC_POS1_KILL10_HISTORY","稳赚杀十码",10,PlanType.WRONG_TWO),
		
		//体彩排列3
		CN_PL3_POS1_KILL02("10043","CN_PL3","POS1_KILL02","CN_PL3_POS1_KILL02_HISTORY","时光个位杀二码",2,PlanType.ALL_Ok),
		CN_PL3_POS2_KILL02("10043","CN_PL3","POS2_KILL02","CN_PL3_POS2_KILL02_HISTORY","时光十位杀二码",2,PlanType.ALL_Ok),
		CN_PL3_POS3_KILL02("10043","CN_PL3","POS3_KILL02","CN_PL3_POS3_KILL02_HISTORY","时光百位杀二码",2,PlanType.ALL_Ok),
		
		//体彩排列5
		CN_PL5_POS1_KILL02("10044","CN_PL5","POS1_KILL02","CN_PL5_POS1_KILL02_HISTORY","奇妙个位杀二码",2,PlanType.ALL_Ok),
		CN_PL5_POS2_KILL02("10044","CN_PL5","POS2_KILL02","CN_PL5_POS2_KILL02_HISTORY","奇妙十位杀二码",2,PlanType.ALL_Ok),
		CN_PL5_POS3_KILL02("10044","CN_PL5","POS3_KILL02","CN_PL5_POS3_KILL02_HISTORY","奇妙百位杀二码",2,PlanType.ALL_Ok),
		CN_PL5_POS4_KILL02("10044","CN_PL5","POS4_KILL02","CN_PL5_POS4_KILL02_HISTORY","奇妙千位杀二码",2,PlanType.ALL_Ok),
		CN_PL5_POS5_KILL02("10044","CN_PL5","POS5_KILL02","CN_PL5_POS5_KILL02_HISTORY","奇妙万位杀二码",2,PlanType.ALL_Ok),
		
		//体彩七星彩
		TC_7XC_POS1_KILL02("10045","TC_7XC","POS1_KILL02","TC_7XC_POS1_KILL02_HISTORY","天使一号杀二码",2,PlanType.ALL_Ok),
		TC_7XC_POS2_KILL02("10045","TC_7XC","POS2_KILL02","TC_7XC_POS2_KILL02_HISTORY","天使二号杀二码",2,PlanType.ALL_Ok),
		TC_7XC_POS3_KILL02("10045","TC_7XC","POS3_KILL02","TC_7XC_POS3_KILL02_HISTORY","天使三号杀二码",2,PlanType.ALL_Ok),
		TC_7XC_POS4_KILL02("10045","TC_7XC","POS4_KILL02","TC_7XC_POS4_KILL02_HISTORY","天使四号杀二码",2,PlanType.ALL_Ok),
		TC_7XC_POS5_KILL02("10045","TC_7XC","POS5_KILL02","TC_7XC_POS5_KILL02_HISTORY","天使五号杀二码",2,PlanType.ALL_Ok),
		TC_7XC_POS6_KILL02("10045","TC_7XC","POS6_KILL02","TC_7XC_POS6_KILL02_HISTORY","天使六号杀二码",2,PlanType.ALL_Ok),
		TC_7XC_POS7_KILL02("10045","TC_7XC","POS7_KILL02","TC_7XC_POS7_KILL02_HISTORY","天使七号杀二码",2,PlanType.ALL_Ok),;
		
		private String lotCode;
		private String enCode;
		private String planCode;
		private String key;
		private String name;
		private Integer killNum;
		private PlanType planType;
		
		private LowLotPlan(String lotCode,String enCode,String planCode,String key,String name,Integer killNum,PlanType planType){
			this.lotCode=lotCode;
			this.enCode=enCode;
			this.planCode=planCode;
			this.key=key;
			this.name=name;
			this.killNum=killNum;
			this.planType=planType;
		}

		public String getLotCode() {
			return lotCode;
		}
		public void setLotCode(String lotCode) {
			this.lotCode = lotCode;
		}
		public String getEnCode() {
			return enCode;
		}
		public void setEnCode(String enCode) {
			this.enCode = enCode;
		}
		public String getPlanCode() {
			return planCode;
		}
		public void setPlanCode(String planCode) {
			this.planCode = planCode;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public Integer getKillNum() {
			return killNum;
		}
		public void setKillNum(Integer killNum) {
			this.killNum = killNum;
		}
		public PlanType getPlanType() {
			return planType;
		}
		public void setPlanType(PlanType planType) {
			this.planType = planType;
		}
		
		public static List<LowLotPlan> getByCode(Integer lotCode){
			LowLotPlan [] plans=LowLotPlan.values();
			List<LowLotPlan> list=new ArrayList<LowLotPlan>();
			LowLotPlan plan;
			for(int i=0;i<plans.length;i++){
				plan=plans[i];
				if(plan.getLotCode().equals(lotCode.toString())){
					list.add(plan);
				}
			}
			return list;
		}
		
	}
	
	public enum PlanType{
		//全部杀对才对
		ALL_Ok{
			public boolean killCode(Integer killNum,Integer hitCntNum){
				if(!Objects.isNull(killNum)&&!Objects.isNull(hitCntNum)){
					if(killNum.equals(hitCntNum)){
						return true;
					}
				}
				return false;
			}
		},
		//杀错一个算对
		WRONG_ONE{
			public boolean killCode(Integer killNum,Integer hitCntNum){
				if(Objects.isNull(killNum)&&Objects.isNull(hitCntNum)){
					Integer temNUM=killNum-hitCntNum;
					if(temNUM>1){
						return false;
					}
					return true;
				}
				return false;
			}
		},
		//杀错两个算对
		WRONG_TWO{
			public boolean killCode(Integer killNum,Integer hitCntNum){
				if(Objects.isNull(killNum)&&Objects.isNull(hitCntNum)){
					Integer temNUM=killNum-hitCntNum;
					if(temNUM>2){
						return false;
					}
					return true;
				}
				return false;
			}
		},
		;
		
		public abstract boolean killCode(Integer killNum,Integer hitCntNum);
		
	}

}
