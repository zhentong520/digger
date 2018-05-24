package com.ko30.service.lotStatistics;

import java.util.ArrayList;
import java.util.List;

import com.ko30.service.lotStatistics.TrendChartData.HFLotChartPlan;


public class TrendChartCount {
	
	public enum CountType{
		//出现次数
		OCCURRENCE_NUMBER("occurrenceNumber","出现次数"),
		//平均遗漏
		AVG_LEAVE("avgLeave","平均遗漏"),
		//最大遗漏
		MAX_LEAVE("maxLeave","最大遗漏"),
		//最大连出
		MAX_CONTINU("maxContinu","最大连出"),;
		
		private String name;
		private String remark;
		private CountType(String name,String remark){
			this.name=name;
			this.remark=remark;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		
	}
		
	
	/**
	 * 前端请求参数 不同参数对应不同的遗漏处理
	 * @author Administrator
	 *
	 */
	public enum ParmType{
		//快3系列
		KUAI_SAN_PARM_DATA(1,DetailType.KUAI_SAN_ALL_DATA), //基础数据
		
		//11选5系列
		ELEVEN_FIVE_PARM_DATA(2,DetailType.ELEVEN_FVIE_ALL_DATA),  //基础数据
		
		//时时彩系列
		SHI_SHI_PARM_DATA(3,DetailType.SHI_SHI_ALL_DATA),//基础数据
		
		//快乐十分系列
		KUAI_LE_PARM_DATA(4,DetailType.KUAI_LE_ALL_DATA),//基础数据
		
		//广西快乐十分系列
		GUANGXI_KUAI_LE_PARM_DATA(12,DetailType.GUANGXI_KUAI_LE_ALL_DATA),//基础数据
		
		//双色球
		SHUANGSEQIU_PARM_FIRST(5,DetailType.SHUANGSEQIU_ALL_DATA),//基础数据
		
		//大乐透
		DALETOU_PARM_FIRST(6,DetailType.DALETOU_ALL_DATA),//基础数据
		
		//福彩3D
		FUCAI3D_PARM_FIRST(7,DetailType.FUCAI3D_ALL_DATA),//基础数据
		
		//七星彩
		QIXINGCAI_PARM_FIRST(8,DetailType.QIXINGCAI_ALL_DATA),//基础数据
		
		//七乐彩
		QILECAI_PARM_FIRST(9,DetailType.QILECAI_ALL_DATA),//基础数据
				
		//排列3
		PAILIE3_PARM_FIRST(10,DetailType.PAILIE3_ALL_DATA),//基础数据
				
		//排列5
		PAILIE5_PARM_FIRST(11,DetailType.PAILIE5_ALL_DATA),//基础数据
		
		;
		private Integer series;//对应系列
		private DetailType detailType;//对应遗漏处理
		
		private ParmType(Integer series,DetailType detailType){
			this.series=series;
			this.detailType=detailType;
		}
		public Integer getSeries() {
			return series;
		}
		public void setSeries(Integer series) {
			this.series = series;
		}
		public DetailType getDetailType() {
			return detailType;
		}
		public void setDetailType(DetailType detailType) {
			this.detailType = detailType;
		}
		
		public static ParmType getBySeries(Integer series){
			ParmType [] parmTypes=ParmType.values();
			for(ParmType type :parmTypes){
				if(type.getSeries()==series){
					return type;
				}
			}
			return null;
		}
		
	}
	
	public enum DetailType{
		/**
		 * 快3各遗漏
		 */
		
		KUAI_SAN_ALL_DATA{

			@Override
			public List<HFLotChartPlan> getplans() {
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.NUM_FIRST_YILOU_K3);
				list.add(HFLotChartPlan.NUM_SECOND_YILOU_K3);
				list.add(HFLotChartPlan.NUM_THIRD_YILOU_K3);
				list.add(HFLotChartPlan.NUM_TOTAL_YILOU_K3);
				list.add(HFLotChartPlan.NUM_FIRST_JIOU_YILOU_K3);
				list.add(HFLotChartPlan.NUM_SECOND_JIOU_YILOU_K3);
				list.add(HFLotChartPlan.NUM_THIRD_JIOU_YILOU_K3);
				list.add(HFLotChartPlan.NUM_SUM_YILOU_K3);
				list.add(HFLotChartPlan.NUM_SUM_DAXIAO_JIOU_YILOU_K3);
				list.add(HFLotChartPlan.NUM_SUM_WEI_YILOU_K3);
				list.add(HFLotChartPlan.NUM_XINGTAI_YILOU_K3);
				list.add(HFLotChartPlan.NUM_JIOUBI_YILOU_K3);
				list.add(HFLotChartPlan.NUM_JISHU_NUM_YILOU_K3);
				list.add(HFLotChartPlan.NUM_OUSHU_NUM_YILOU_K3);
				list.add(HFLotChartPlan.NUM_SHANBUTONG_YILOU_K3);
				return list;
			}
			
		},
		
		
		/**
		 * 11选5
		 * @return
		 */
		ELEVEN_FVIE_ALL_DATA{
			@Override
			public List<HFLotChartPlan> getplans() {
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.NUM_FIRST_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_SECOND_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_THIRD_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_FOUR_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_FIVE_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_TATOL_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_SUM_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_FIRST_DAXIAO_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_SECOND_DAXIAO_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_THIRD_DAXIAO_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_FOUR_DAXIAO_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_FIVE_DAXIAO_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_FIRST_JIOU_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_SECOND_JIOU_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_THIRD_JIOU_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_FOUR_JIOU_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_FIVE_JIOU_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_DAXIAOBI_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_JIOUBI_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_JISHU_NUM_YILOU_11_5);
				list.add(HFLotChartPlan.NUM_OUSHU_NUM_YILOU_11_5);
				return list;
			}
			
		},
		
		
		
		/**
		 * 时时彩
		 * @return
		 */
		SHI_SHI_ALL_DATA{
			@Override
			public List<HFLotChartPlan> getplans() {
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.NUM_FIRST_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_SECOND_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_THIRD_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_FOUR_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_FIVE_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_TATOL_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_AFTER_3_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_FIRST_DAXIAO_JIOU_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_SECOND_DAXIAO_JIOU_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_THIRD_DAXIAO_JIOU_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_FOUR_DAXIAO_JIOU_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_FIVE_DAXIAO_JIOU_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_FIRST_SHENGPINGJIANG_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_SECOND_SHENGPINGJIANG_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_THIRD_SHENGPINGJIANG_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_FOUR_SHENGPINGJIANG_YILOU_SSC);
				list.add(HFLotChartPlan.NUM_FIVE_SHENGPINGJIANG_YILOU_SSC);
				return list;
			}
			
		},
		

		/**
		 * 快乐十分
		 * @return
		 */
		KUAI_LE_ALL_DATA{
			@Override
			public List<HFLotChartPlan> getplans() {
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.NUM_FIRST_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SECOND_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_THIRD_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FOUR_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FIVE_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SIX_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SEVEN_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_EIGHT_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FIRST_DAXIAO_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SECOND_DAXIAO_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_THIRD_DAXIAO_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FOUR_DAXIAO_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FIVE_DAXIAO_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SIX_DAXIAO_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SEVEN_DAXIAO_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_EIGHT_DAXIAO_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_TATOL_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FIRST_JIOU_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SECOND_JIOU_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_THIRD_JIOU_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FOUR_JIOU_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FIVE_JIOU_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SIX_JIOU_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SEVEN_JIOU_YILOU_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_EIGHT_JIOU_YILOU_KUAILE_10FEN);
				return list;
			}
			
		},
		
		
		/**
		 * 广西快乐十分
		 * @return
		 */
		GUANGXI_KUAI_LE_ALL_DATA{
			@Override
			public List<HFLotChartPlan> getplans() {
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.NUM_FIRST_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SECOND_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_THIRD_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FOUR_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FIVE_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FIRST_DAXIAO_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SECOND_DAXIAO_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_THIRD_DAXIAO_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FOUR_DAXIAO_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FIVE_DAXIAO_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_TATOL_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FIRST_JIOU_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_SECOND_JIOU_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_THIRD_JIOU_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FOUR_JIOU_YILOU_GUANGXI_KUAILE_10FEN);
				list.add(HFLotChartPlan.NUM_FIVE_JIOU_YILOU_GUANGXI_KUAILE_10FEN);
				return list;
			}
			
		},
		
		
		/**
		 * 双色球
		 * @return
		 */
		SHUANGSEQIU_ALL_DATA{
			@Override
			public List<HFLotChartPlan> getplans(){
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.RED_NUM_TATOL_FC_SSQ);
				list.add(HFLotChartPlan.BLUE_NUM_TATOL_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_FIRST_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_SECOND_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_THIRD_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_FOUR_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_FIVE_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_SIX_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_SEVEN_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_EIGHT_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_NINE_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_TEN_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_ELEVEN_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_TWELVE_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_THIRTEEN_FC_SSQ);
				list.add(HFLotChartPlan.RED_NUM_FOURTEEN_FC_SSQ);
				list.add(HFLotChartPlan.BLUE_NUM_FIRST_FC_SSQ);
				list.add(HFLotChartPlan.BLUE_NUM_SECOND_FC_SSQ);
				list.add(HFLotChartPlan.BLUE_NUM_THIRD_FC_SSQ);
				list.add(HFLotChartPlan.BLUE_NUM_FOUR_FC_SSQ);
				list.add(HFLotChartPlan.BLUE_NUM_DAXIAO_FC_SSQ);
				list.add(HFLotChartPlan.BLUE_NUM_JIOU_FC_SSQ);
				list.add(HFLotChartPlan.BLUE_NUM_TOTAL_FOUR_FC_SSQ);
				list.add(HFLotChartPlan.BLUE_NUM_WEI_SHU_FC_SSQ);
				return list;
			}
		},
		
		
		/**
		 * 大乐透
		 * @return
		 */
		DALETOU_ALL_DATA{
			@Override
			public List<HFLotChartPlan> getplans() {
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.NUM_BEFORE_YILOU_DLT);
				list.add(HFLotChartPlan.NUM_AFTER_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_FIRST_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_SECOND_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_THIRD_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_FOUR_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_FIVE_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_SIX_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_SEVEN_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_EIGHT_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_NINE_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_TEN_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_ELEVEN_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_TWELVE_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_THIRTEEN_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_FOURTEEN_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_FIFTEEN_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_SIXTEEN_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_SEVENTEEN_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_EIGHTEEN_YILOU_DLT);
				list.add(HFLotChartPlan.RED_NUM_NINETEEN_YILOU_DLT);
				list.add(HFLotChartPlan.BLUE_NUM_SPAN_YILOU_DLT);
				list.add(HFLotChartPlan.BLUE_NUM_DAOXIAO_YILOU_DLT);
				list.add(HFLotChartPlan.BLUE_NUM_JIOU_YILOU_DLT);
				return list;
			}
			
		},
		
		
		/**
		 * 福彩3D
		 * @return
		 */
		FUCAI3D_ALL_DATA{
			@Override
			public List<HFLotChartPlan> getplans() {
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.NUM_FIRST_FC_3D);
				list.add(HFLotChartPlan.NUM_SECOND_FC_3D);
				list.add(HFLotChartPlan.NUM_THIRD_FC_3D);
				list.add(HFLotChartPlan.NUM_TOTAL_FC_3D);
				list.add(HFLotChartPlan.NUM_SUM_FC_3D);
				list.add(HFLotChartPlan.NUM_SUM_WEI_FC_3D);
				list.add(HFLotChartPlan.NUM_FIRST_DA_FC_3D);
				list.add(HFLotChartPlan.NUM_FIRST_XIAO_FC_3D);
				list.add(HFLotChartPlan.NUM_SECOND_DA_FC_3D);
				list.add(HFLotChartPlan.NUM_SECOND_XIAO_FC_3D);
				list.add(HFLotChartPlan.NUM_THIRD_DA_FC_3D);
				list.add(HFLotChartPlan.NUM_THIRD_XIAO_FC_3D);
				list.add(HFLotChartPlan.NUM_FIRST_JI_FC_3D);
				list.add(HFLotChartPlan.NUM_FIRST_OU_FC_3D);
				list.add(HFLotChartPlan.NUM_SECOND_JI_FC_3D);
				list.add(HFLotChartPlan.NUM_SECOND_OU_FC_3D);
				list.add(HFLotChartPlan.NUM_THIRD_JI_FC_3D);
				list.add(HFLotChartPlan.NUM_THIRD_OU_FC_3D);
				return list;
			}
			
		},
		
		
		
		/**
		 * 七星彩
		 * @return
		 */
		QIXINGCAI_ALL_DATA{
			@Override
			public List<HFLotChartPlan> getplans() {
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.NUM_FIRST_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_SECOND_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_THIRD_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_FOUR_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_FIVE_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_SIX_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_SEVEN_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_SUM_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_SUM_WEI_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_FIRST_DAXIAO_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_SECOND_DAXIAO_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_THIRD_DAXIAO_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_FOUR_DAXIAO_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_FIVE_DAXIAO_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_SIX_DAXIAO_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_SEVEN_DAXIAO_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_FIRST_JIOU_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_SECOND_JIOU_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_THIRD_JIOU_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_FOUR_JIOU_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_FIVE_JIOU_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_SIX_JIOU_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_SEVEN_JIOU_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_DAXIAOBI_YILOU_QXC);
				list.add(HFLotChartPlan.NUM_JIOUBI_YILOU_QXC);
				return list;
			}
			
		},
		
		
		/**
		 * 七乐彩
		 * @return
		 */
		QILECAI_ALL_DATA{
			@Override
			public List<HFLotChartPlan> getplans() {
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.NUM_FIRST_YILOU_QLC);
				list.add(HFLotChartPlan.NUM_SECOND_YILOU_QLC);
				list.add(HFLotChartPlan.NUM_THIRD_YILOU_QLC);
				list.add(HFLotChartPlan.NUM_FOUR_YILOU_QLC);
				list.add(HFLotChartPlan.NUM_FIVE_YILOU_QLC);
				list.add(HFLotChartPlan.NUM_SIX_YILOU_QLC);
				list.add(HFLotChartPlan.NUM_SEVEN_YILOU_QLC);
				list.add(HFLotChartPlan.NUM_EIGHT_YILOU_QLC);
				list.add(HFLotChartPlan.NUM_XIAOJI_YILOU_QLC);
				list.add(HFLotChartPlan.NUM_XIAOOU_YILOU_QLC);
				list.add(HFLotChartPlan.NUM_DAJI_YILOU_QLC);
				list.add(HFLotChartPlan.NUM_DAOU_YILOU_QLC);
				return list;			}
			
		},
		
		
		/**
		 * 排列3
		 * @return
		 */
		PAILIE3_ALL_DATA{
			@Override
			public List<HFLotChartPlan> getplans() {
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.NUM_FIRST_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_SECOND_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_THIRD_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_TOTAL_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_SUM_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_SUM_WEI_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_FIRST_DA_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_FIRST_XIAO_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_SECOND_DA_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_SECOND_XIAO_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_THIRD_DA_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_THIRD_XIAO_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_FIRST_JI_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_FIRST_OU_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_SECOND_JI_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_SECOND_OU_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_THIRD_JI_YILOU_PL3);
				list.add(HFLotChartPlan.NUM_THIRD_OU_YILOU_PL3);
				return list;
			}
			
		},
		
		
		
		/**
		 *排列5
		 * @return
		 */
		PAILIE5_ALL_DATA{
			@Override
			public List<HFLotChartPlan> getplans() {
				List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
				list.add(HFLotChartPlan.NUM_FIRST_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_SECOND_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_THIRD_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_FOUR_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_FIVE_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_SUM_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_SUM_WEI_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_FIRST_DAXIAO_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_SECOND_DAXIAO_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_THIRD_DAXIAO_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_FOUR_DAXIAO_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_FIVE_DAXIAO_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_FIRST_JIOU_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_SECOND_JIOU_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_THIRD_JIOU_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_FOUR_JIOU_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_FIVE_JIOU_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_DAXIAOBI_YILOU_PL5);
				list.add(HFLotChartPlan.NUM_JIOUBI_YILOU_PL5);
				return list;
			}
			
		},
		
		;
		public abstract List<HFLotChartPlan> getplans();
	}
	
}
