package com.ko30.service.lotStatistics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang.StringUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;


public class TrendChartData {
	
	public enum LotType{
		//快3
		GUANGXI_KUAI_3("广西快3","GX_K3","10026",1,LotField.KUAI_SAN_ATTR),
		JILIN_KUAI_3("吉林快3","JL_K3","10027",1,LotField.KUAI_SAN_ATTR),
		HEBEI_KUAI_3("河北快3","HEB_K3","10028",1,LotField.KUAI_SAN_ATTR),
		NEMENGGU_KUAI_3("内蒙古快3","NMG_K3","10029",1,LotField.KUAI_SAN_ATTR),
		ANHUI_KUAI_3("安徽快3","AH_K3","10030",1,LotField.KUAI_SAN_ATTR),
		FUJIAN_KUAI_3("福建快3","FJ_K3","10031",1,LotField.KUAI_SAN_ATTR),
		HUBEI_KUAI_3("湖北快3","HUB_K3","10032",1,LotField.KUAI_SAN_ATTR),
		BEIJING("北京快3","BJ_K3","10033",1,LotField.KUAI_SAN_ATTR),
		JIANGSHU_KUAI_3("江苏快3","JS_K3","10007",1,LotField.KUAI_SAN_ATTR),
		//11选5
		JIANGXI_11_5("江西11选5","JX_11C5","10015",2,LotField.ELEVEN_FIVE_ATTR),
		JIANGSHU_11_5("江苏11选5","JS_11C5","10016",2,LotField.ELEVEN_FIVE_ATTR),
		ANHUI_11_5("安徽11选5","AH_11C5","10017",2,LotField.ELEVEN_FIVE_ATTR),
		SHANGHAI_11_5("上海11选5","SH_11C5","10018",2,LotField.ELEVEN_FIVE_ATTR),
		LIAOLIN_11_5("辽宁11选5","LN_11C5","10019",2,LotField.ELEVEN_FIVE_ATTR),
		HUBEI_11_5("湖北11选5","HB_11C5","10020",2,LotField.ELEVEN_FIVE_ATTR),
		GUANGXI_11_5("广西11选5","GX_11C5","10022",2,LotField.ELEVEN_FIVE_ATTR),
		JILIN_11_5("吉林11选5","JL_11C5","10023",2,LotField.ELEVEN_FIVE_ATTR),
		NEIMENGGU_11_5("内蒙古11选5","NMG_11C5","10024",2,LotField.ELEVEN_FIVE_ATTR),
		ZHEJIANG_11_5("浙江11选5","ZJ_11C5","10025",2,LotField.ELEVEN_FIVE_ATTR),
		GUANGDONG_11_5("广东11选5","GD_11C5","10006",2,LotField.ELEVEN_FIVE_ATTR),
		SHANDONG_11_5("山东11选5","TC_11C5","10008",2,LotField.ELEVEN_FIVE_ATTR),
		//时时彩
		CHONGQING_SSC("重庆时时彩","CQ_SSC","10002",3,LotField.SHI_SHI_ATTR),
		TIANJIN_SSC("天津时时彩","TJ_SSC","10003",3,LotField.SHI_SHI_ATTR),
		XINJIANG_SSC("新疆时时彩","XJ_SSC","10004",3,LotField.SHI_SHI_ATTR),
		//快乐十分
		GUANGXI_KUAILE_10("广西快乐十分","GX_H10","10038",12,LotField.KUAI_LE_ATTR),
		GUANGDONG_KUAILE_10("广东快乐十分","GD_H10","10005",4,LotField.KUAI_LE_ATTR),
		TIANJIN_KUAILE_10("天津快乐十分","TJ_H10","10034",4,LotField.KUAI_LE_ATTR),
		CHONGQING_KUAILE_10("重庆快乐十分","CQ_HF","10009",4,LotField.KUAI_LE_ATTR),
		
		//低频彩
		SHUANGSEQIU("双色球","CN_F2B","10039",5,LotField.SHUANGSEQIU_ATTR),
		DALETOU("大乐透","SU_DLT","10040",6,LotField.DALETOU_ATTR),
		FUCAI3D("福彩3D","CN_F3D","10041",7,LotField.FUCAISAND_ATTR),
		QIXINGCAI("七星彩","TC_7XC","10045",8,LotField.QIXINGCAI_ATTR),
		QILECAI("七乐彩","CN_7LC","10042",9,LotField.QILECAI_ATTR),
		PAILIE3("排列3","CN_PL3","10043",10,LotField.PAILIETHIRD_ATTR),
		PAILIE5("排列5","CN_PL5","10044",11,LotField.PAILIEFIVE_ATTR),
		
		;
		
		private String name;
		private String enCode;
		private String lotCode;
		private Integer series;
		private LotField lotField;
		private LotType(String name,String enCode,String lotCode,Integer series,LotField lotField){
			this.name=name;
			this.enCode=enCode;
			this.lotCode=lotCode;
			this.series=series;
			this.lotField=lotField;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEnCode() {
			return enCode;
		}
		public void setEnCode(String enCode) {
			this.enCode = enCode;
		}
		public String getLotCode() {
			return lotCode;
		}
		public void setLotCode(String lotCode) {
			this.lotCode = lotCode;
		}
		public Integer getSeries() {
			return series;
		}
		public void setSeries(Integer series) {
			this.series = series;
		}
		public LotField getLotField() {
			return lotField;
		}
		public void setLotField(LotField lotField) {
			this.lotField = lotField;
		}
		public static LotType getLotTypeByCode(String lotCode){
			LotType []lotTypes=LotType.values();
			for(LotType type : lotTypes){
				if(type.getLotCode().equals(lotCode)){
					return type;
				}
			}
			return null;
		}
		
		public static LotType getByCode(String lotCode) {
			LotType[] types = LotType.values();
			for (LotType type : types) {
				if (type.getLotCode().equals(lotCode)) {
					return type;
				}
			}
			return null;
		}
		
	}
	
	
	//重新整理走势图数据
	public enum HFLotChartPlan{
		//快3 DataConvert
		NUM_FIRST_YILOU_K3("开奖号码第一位遗漏","firstArea",1,DataRange.FOUR_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_YILOU_K3("开奖号码第二位遗漏","secondArea",1,DataRange.FOUR_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_YILOU_K3("开奖号码第三位遗漏","thirdArea",1,DataRange.FOUR_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_TOTAL_YILOU_K3("开奖号码分布遗漏","fourArea",1,DataRange.FOUR_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL), 
		NUM_FIRST_JIOU_YILOU_K3("开奖号码第一位奇偶遗漏","fiveArea",1,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_JIOU_YILOU_K3("开奖号码第二位奇偶遗漏","sixArea",1,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_JIOU_YILOU_K3("开奖号码第三位奇偶遗漏","sevenArea",1,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_SUM_YILOU_K3("开奖号码和值遗漏","eightArea",1,DataRange.FIRST_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SUM_NUM),
		NUM_SUM_DAXIAO_JIOU_YILOU_K3("开奖号码和值大小奇偶遗漏","nineArea",1,DataRange.THIRD_RANGE,LeaveValueHandle.DAXIAOJIOU_YILOU,LotDrwawValue.SUM_NUM),//小奇，小偶，大奇，大偶
		NUM_SUM_WEI_YILOU_K3("开奖号码和值尾号遗漏","tenArea",1,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SUM_WEI_NUM),
		NUM_XINGTAI_YILOU_K3("开奖号码形态遗漏","elevenArea",1,DataRange.FIVE_RANGE,LeaveValueHandle.NUM_XINGTAI_YILOU,LotDrwawValue.DRAW_ALL),//三不同 二不同 三连号  三同号
		NUM_JIOUBI_YILOU_K3("开奖号码奇偶比遗漏","twelveArea",1,DataRange.EIGHT_RANGE,LeaveValueHandle.NUM_JIOUBI_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_JISHU_NUM_YILOU_K3("开奖号码奇数个数遗漏","thirdteenArea",1,DataRange.NINE_RANGE,LeaveValueHandle.NUM_JISHU_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_OUSHU_NUM_YILOU_K3("开奖号码偶数个数遗漏","fourteenArea",1,DataRange.NINE_RANGE,LeaveValueHandle.NUM_OUSHU_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_SHANBUTONG_YILOU_K3("开奖号码三不同遗漏","fiveteenArea",1,DataRange.SIX_RANGE,LeaveValueHandle.NUM_UNSAME_YILOU,LotDrwawValue.DRAW_ALL),
		
		
		//11选5
		NUM_FIRST_YILOU_11_5("开奖号码第一位遗漏","firstArea",2,DataRange.ELEVEN_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_YILOU_11_5("开奖号码第二位遗漏","secondArea",2,DataRange.ELEVEN_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_YILOU_11_5("开奖号码第三位遗漏","thirdArea",2,DataRange.ELEVEN_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_FOUR_YILOU_11_5("开奖号码第四位遗漏","fourArea",2,DataRange.ELEVEN_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FOUR_NUM),
		NUM_FIVE_YILOU_11_5("开奖号码第五位遗漏","fiveArea",2,DataRange.ELEVEN_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIVE_NUM),
		NUM_TATOL_YILOU_11_5("开奖号码分布遗漏","sixArea",2,DataRange.ELEVEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL), 
		NUM_SUM_YILOU_11_5("开奖号码和值遗漏","sevenArea",2,DataRange.TEN_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SUM_NUM), 
		NUM_FIRST_DAXIAO_YILOU_11_5("开奖号码第一位大小遗漏","eightArea",2,DataRange.SEVENTY_SIX_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_DAXIAO_YILOU_11_5("开奖号码第二位大小遗漏","nineArea",2,DataRange.SEVENTY_SIX_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_DAXIAO_YILOU_11_5("开奖号码第三位大小遗漏","tenArea",2,DataRange.SEVENTY_SIX_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_FOUR_DAXIAO_YILOU_11_5("开奖号码第四位大小遗漏","elevenArea",2,DataRange.SEVENTY_SIX_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FOUR_NUM),
		NUM_FIVE_DAXIAO_YILOU_11_5("开奖号码第五位大小遗漏","twelveArea",2,DataRange.SEVENTY_SIX_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FIVE_NUM),
		NUM_FIRST_JIOU_YILOU_11_5("开奖号码第一位奇偶遗漏","thirdteenArea",2,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_JIOU_YILOU_11_5("开奖号码第二位奇偶遗漏","fourteenArea",2,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_JIOU_YILOU_11_5("开奖号码第三位奇偶遗漏","fiveteenArea",2,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_FOUR_JIOU_YILOU_11_5("开奖号码第四位奇偶遗漏","sixteenArea",2,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FOUR_NUM),
		NUM_FIVE_JIOU_YILOU_11_5("开奖号码第五位奇偶遗漏","seventeenArea",2,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FIVE_NUM),
		NUM_DAXIAOBI_YILOU_11_5("开奖号码大小比遗漏","eighteenArea",2,DataRange.EIGHTY_FIVE_RANGE,LeaveValueHandle.NUM_DAXIAOBI_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_JIOUBI_YILOU_11_5("开奖号码奇偶比遗漏","nineteenArea",2,DataRange.THIRDTEEN_RANGE,LeaveValueHandle.NUM_JIOUBI_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_JISHU_NUM_YILOU_11_5("开奖号码奇数个数遗漏","twentyArea",2,DataRange.SEVENTY_FIVE_RANGE,LeaveValueHandle.NUM_JISHU_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_OUSHU_NUM_YILOU_11_5("开奖号码偶数个数遗漏","twentyOneArea",2,DataRange.SEVENTY_FIVE_RANGE,LeaveValueHandle.NUM_OUSHU_YILOU,LotDrwawValue.DRAW_ALL),
		
		
		//时时彩
		NUM_FIRST_YILOU_SSC("开奖号码第一位遗漏","firstArea",3,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM), 
		NUM_SECOND_YILOU_SSC("开奖号码第二位遗漏","secondArea",3,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM), 
		NUM_THIRD_YILOU_SSC("开奖号码第三位遗漏","thirdArea",3,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM), 
		NUM_FOUR_YILOU_SSC("开奖号码第四位遗漏","fourArea",3,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FOUR_NUM), 
		NUM_FIVE_YILOU_SSC("开奖号码第五位遗漏","fiveArea",3,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIVE_NUM), 
		NUM_TATOL_YILOU_SSC("开奖号码分布遗漏","sixArea",3,DataRange.SECOND_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL), 
		NUM_AFTER_3_YILOU_SSC("开奖号码后三位分布遗漏","sevenArea",3,DataRange.SECOND_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_AFTER_THIRD),
		NUM_FIRST_DAXIAO_JIOU_YILOU_SSC("开奖号码第一位大小奇偶遗漏","eightArea",3,DataRange.EIGHTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_JIOU_YILOU,LotDrwawValue.FIRST_NUM), 
		NUM_SECOND_DAXIAO_JIOU_YILOU_SSC("开奖号码第二位大小奇偶遗漏","nineArea",3,DataRange.EIGHTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_JIOU_YILOU,LotDrwawValue.SECOND_NUM), 
		NUM_THIRD_DAXIAO_JIOU_YILOU_SSC("开奖号码第三位大小奇偶遗漏","tenArea",3,DataRange.EIGHTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_JIOU_YILOU,LotDrwawValue.THIRD_NUM), 
		NUM_FOUR_DAXIAO_JIOU_YILOU_SSC("开奖号码第四位大小奇偶遗漏","elevenArea",3,DataRange.EIGHTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_JIOU_YILOU,LotDrwawValue.FOUR_NUM), 
		NUM_FIVE_DAXIAO_JIOU_YILOU_SSC("开奖号码第五位大小奇偶遗漏","twelveArea",3,DataRange.EIGHTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_JIOU_YILOU,LotDrwawValue.FIVE_NUM), 
		NUM_FIRST_SHENGPINGJIANG_YILOU_SSC("开奖号码第一位升平降遗漏","thirdteenArea",3,DataRange.FIVETEEN_RANGE,LeaveValueHandle.NUM_SHENG_PING_JIANG_YILOU,LotDrwawValue.FIRST_NUM), 
		NUM_SECOND_SHENGPINGJIANG_YILOU_SSC("开奖号码第二位升平降遗漏","fourteenArea",3,DataRange.FIVETEEN_RANGE,LeaveValueHandle.NUM_SHENG_PING_JIANG_YILOU,LotDrwawValue.SECOND_NUM), 
		NUM_THIRD_SHENGPINGJIANG_YILOU_SSC("开奖号码第三位升平降遗漏","fiveteenArea",3,DataRange.FIVETEEN_RANGE,LeaveValueHandle.NUM_SHENG_PING_JIANG_YILOU,LotDrwawValue.THIRD_NUM), 
		NUM_FOUR_SHENGPINGJIANG_YILOU_SSC("开奖号码第四位升平降遗漏","sixteenArea",3,DataRange.FIVETEEN_RANGE,LeaveValueHandle.NUM_SHENG_PING_JIANG_YILOU,LotDrwawValue.FOUR_NUM), 
		NUM_FIVE_SHENGPINGJIANG_YILOU_SSC("开奖号码第五位升平降遗漏","seventeenArea",3,DataRange.FIVETEEN_RANGE,LeaveValueHandle.NUM_SHENG_PING_JIANG_YILOU,LotDrwawValue.FIVE_NUM),
		
		
		//快乐十分
		NUM_FIRST_YILOU_KUAILE_10FEN("开奖号码第一位遗漏","firstArea",4,DataRange.SEVENTY_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM), 
		NUM_SECOND_YILOU_KUAILE_10FEN("开奖号码第二位遗漏","secondArea",4,DataRange.SEVENTY_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM), 
		NUM_THIRD_YILOU_KUAILE_10FEN("开奖号码第三位遗漏","thirdArea",4,DataRange.SEVENTY_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM), 
		NUM_FOUR_YILOU_KUAILE_10FEN("开奖号码第四位遗漏","fourArea",4,DataRange.SEVENTY_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FOUR_NUM), 
		NUM_FIVE_YILOU_KUAILE_10FEN("开奖号码第五位遗漏","fiveArea",4,DataRange.SEVENTY_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIVE_NUM), 
		NUM_SIX_YILOU_KUAILE_10FEN("开奖号码第六位遗漏","sixArea",4,DataRange.SEVENTY_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SIX_NUM), 
		NUM_SEVEN_YILOU_KUAILE_10FEN("开奖号码第七位遗漏","sevenArea",4,DataRange.SEVENTY_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SEVEN_NUM), 
		NUM_EIGHT_YILOU_KUAILE_10FEN("开奖号码第八位遗漏","eightArea",4,DataRange.SEVENTY_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.EIGHT_NUM), 
		NUM_TATOL_YILOU_KUAILE_10FEN("开奖号码分布遗漏","nineArea",4,DataRange.SEVENTY_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL), 
		NUM_FIRST_JIOU_YILOU_KUAILE_10FEN("开奖号码第一位奇偶遗漏","tenArea",4,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FIRST_NUM), 
		NUM_SECOND_JIOU_YILOU_KUAILE_10FEN("开奖号码第二位奇偶遗漏","elevenArea",4,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.SECOND_NUM), 
		NUM_THIRD_JIOU_YILOU_KUAILE_10FEN("开奖号码第三位奇偶遗漏","twelveArea",4,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.THIRD_NUM), 
		NUM_FOUR_JIOU_YILOU_KUAILE_10FEN("开奖号码第四位奇偶遗漏","thirdteenArea",4,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FOUR_NUM), 
		NUM_FIVE_JIOU_YILOU_KUAILE_10FEN("开奖号码第五位奇偶遗漏","fourteenArea",4,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FIVE_NUM), 
		NUM_SIX_JIOU_YILOU_KUAILE_10FEN("开奖号码第六位奇偶遗漏","fiveteenArea",4,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.SIX_NUM), 
		NUM_SEVEN_JIOU_YILOU_KUAILE_10FEN("开奖号码第七位奇偶遗漏","sixteenArea",4,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.SEVEN_NUM), 
		NUM_EIGHT_JIOU_YILOU_KUAILE_10FEN("开奖号码第八位奇偶遗漏","seventeenArea",4,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.EIGHT_NUM),
		NUM_FIRST_DAXIAO_YILOU_KUAILE_10FEN("开奖号码第一位大小遗漏","eighteenArea",4,DataRange.SEVENTY_EIGHT_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FIRST_NUM), 
		NUM_SECOND_DAXIAO_YILOU_KUAILE_10FEN("开奖号码第二位大小遗漏","nineteenArea",4,DataRange.SEVENTY_EIGHT_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.SECOND_NUM), 
		NUM_THIRD_DAXIAO_YILOU_KUAILE_10FEN("开奖号码第三位大小遗漏","twentyOneArea",4,DataRange.SEVENTY_EIGHT_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.THIRD_NUM), 
		NUM_FOUR_DAXIAO_YILOU_KUAILE_10FEN("开奖号码第四位大小遗漏","twentyTwoArea",4,DataRange.SEVENTY_EIGHT_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FOUR_NUM), 
		NUM_FIVE_DAXIAO_YILOU_KUAILE_10FEN("开奖号码第五位大小遗漏","twentyThirdArea",4,DataRange.SEVENTY_EIGHT_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FIVE_NUM), 
		NUM_SIX_DAXIAO_YILOU_KUAILE_10FEN("开奖号码第六位大小遗漏","twentyFourArea",4,DataRange.SEVENTY_EIGHT_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.SIX_NUM), 
		NUM_SEVEN_DAXIAO_YILOU_KUAILE_10FEN("开奖号码第七位大小遗漏","twentyFiveArea",4,DataRange.SEVENTY_EIGHT_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.SEVEN_NUM), 
		NUM_EIGHT_DAXIAO_YILOU_KUAILE_10FEN("开奖号码第八位大小遗漏","twentySixArea",4,DataRange.SEVENTY_EIGHT_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.EIGHT_NUM),
		
		//广西快乐十分
		NUM_FIRST_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第一位遗漏","firstArea",4,DataRange.SEVENTY_ONE_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM), 
		NUM_SECOND_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第二位遗漏","secondArea",4,DataRange.SEVENTY_ONE_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM), 
		NUM_THIRD_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第三位遗漏","thirdArea",4,DataRange.SEVENTY_ONE_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM), 
		NUM_FOUR_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第四位遗漏","fourArea",4,DataRange.SEVENTY_ONE_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FOUR_NUM), 
		NUM_FIVE_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第五位遗漏","fiveArea",4,DataRange.SEVENTY_ONE_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIVE_NUM), 
		NUM_TATOL_YILOU_GUANGXI_KUAILE_10FEN("开奖号码分布遗漏","sixArea",12,DataRange.SEVENTY_ONE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_FIRST_DAXIAO_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第一位大小遗漏","sevenArea",12,DataRange.SEVENTY_NINE_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FIRST_NUM), 
		NUM_SECOND_DAXIAO_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第二位大小遗漏","eightArea",12,DataRange.SEVENTY_NINE_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.SECOND_NUM), 
		NUM_THIRD_DAXIAO_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第三位大小遗漏","nineArea",12,DataRange.SEVENTY_NINE_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.THIRD_NUM), 
		NUM_FOUR_DAXIAO_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第四位大小遗漏","tenArea",12,DataRange.SEVENTY_NINE_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FOUR_NUM), 
		NUM_FIVE_DAXIAO_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第五位大小遗漏","elevenArea",12,DataRange.SEVENTY_NINE_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FIVE_NUM), 
		NUM_FIRST_JIOU_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第一位奇偶遗漏","twelveArea",12,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FIRST_NUM), 
		NUM_SECOND_JIOU_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第二位奇偶遗漏","thirdteenArea",12,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.SECOND_NUM), 
		NUM_THIRD_JIOU_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第三位奇偶遗漏","fourteenArea",12,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.THIRD_NUM), 
		NUM_FOUR_JIOU_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第四位奇偶遗漏","fiveteenArea",12,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FOUR_NUM), 
		NUM_FIVE_JIOU_YILOU_GUANGXI_KUAILE_10FEN("开奖号码第五位奇偶遗漏","sixteenArea",12,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FIVE_NUM), 
		
		
		
		//双色球
		RED_NUM_TATOL_FC_SSQ("开奖号码红球分布遗漏","firstArea",5,DataRange.SEVENTY_THIRD_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		BLUE_NUM_TATOL_FC_SSQ("开奖号码蓝球分布遗漏","secondArea",5,DataRange.SEVENTY_FOUR_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_AFTER_ONE),
		//第一种红球分区
		RED_NUM_FIRST_FC_SSQ("开奖号码红球一区遗漏","thirdArea",5,DataRange.ELEVEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		RED_NUM_SECOND_FC_SSQ("开奖号码红球二区遗漏","fourArea",5,DataRange.SIXTEEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		RED_NUM_THIRD_FC_SSQ("开奖号码红球三区遗漏","fiveArea",5,DataRange.SEVENTEEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		//第二种红球分区
		RED_NUM_FOUR_FC_SSQ("开奖号码红球四区遗漏","sixArea",5,DataRange.EIGHTEEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		RED_NUM_FIVE_FC_SSQ("开奖号码红球五区遗漏","sevenArea",5,DataRange.NINETEEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		RED_NUM_SIX_FC_SSQ("开奖号码红球六区遗漏","eightArea",5,DataRange.TWENTRY_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		RED_NUM_SEVEN_FC_SSQ("开奖号码红球七区遗漏","nineArea",5,DataRange.TWENTRY_ONE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		//第三种红球分区
		RED_NUM_EIGHT_FC_SSQ("开奖号码红球八区遗漏","tenArea",5,DataRange.TWENTY_TWO_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		RED_NUM_NINE_FC_SSQ("开奖号码红球九区遗漏","elevenArea",5,DataRange.TWENTY_THREE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		RED_NUM_TEN_FC_SSQ("开奖号码红球十区遗漏","twelveArea",5,DataRange.TWENTY_FOUR_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		RED_NUM_ELEVEN_FC_SSQ("开奖号码红球十一区遗漏","thirdteenArea",5,DataRange.TWENTY_FIVE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		RED_NUM_TWELVE_FC_SSQ("开奖号码红球十二区遗漏","fourteenArea",5,DataRange.TWENTY_SIX_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		RED_NUM_THIRTEEN_FC_SSQ("开奖号码红球十三区遗漏","fiveteenArea",5,DataRange.TWENTY_SEVEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		RED_NUM_FOURTEEN_FC_SSQ("开奖号码红球十四区遗漏","sixteenArea",5,DataRange.TWENTY_EIGHT_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_SIX),
		//蓝球分区
		BLUE_NUM_FIRST_FC_SSQ("开奖号码蓝球一区遗漏","seventeenArea",5,DataRange.TWENTY_NINE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_AFTER_ONE),
		BLUE_NUM_SECOND_FC_SSQ("开奖号码蓝球二区遗漏","eighteenArea",5,DataRange.THIRTY_ONE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_AFTER_ONE),
		BLUE_NUM_THIRD_FC_SSQ("开奖号码蓝球三区遗漏","nineteenArea",5,DataRange.THIRTY_TWO_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_AFTER_ONE),
		BLUE_NUM_FOUR_FC_SSQ("开奖号码蓝球四区遗漏","twentyArea",5,DataRange.THIRTY_THREE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_AFTER_ONE),
		BLUE_NUM_DAXIAO_FC_SSQ("开奖号码蓝球大小遗漏","twentyOneArea",5,DataRange.EIGHTY_ONE_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.DRAW_AFTER_ONE),
		BLUE_NUM_JIOU_FC_SSQ("开奖号码蓝球奇偶遗漏","twentyTwoArea",5,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.DRAW_AFTER_ONE),
		BLUE_NUM_TOTAL_FOUR_FC_SSQ("开奖号码蓝球四区分布遗漏","twentyThirdArea",5,DataRange.EIGHTY_TWO_RANGE,LeaveValueHandle.LOT_BLUE_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_AFTER_ONE),
		BLUE_NUM_WEI_SHU_FC_SSQ("开奖号码蓝球尾数遗漏","twentyFourArea",5,DataRange.EIGHTY_THIRD_RANGE,LeaveValueHandle.LOT_BLUE_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_AFTER_ONE_WEI),
		
		
		
		//大乐透
		NUM_BEFORE_YILOU_DLT("开奖号码前区分布遗漏","firstArea",6,DataRange.SEVENTY_TWO_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		NUM_AFTER_YILOU_DLT("开奖号码后区分布遗漏","secondArea",6,DataRange.THIRTY_FIVE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_AFTER_TWO),
		//第一种红球分区
		RED_NUM_FIRST_YILOU_DLT("开奖号码红球第一区分布遗漏","thirdArea",6,DataRange.THIRTY_FIVE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_SECOND_YILOU_DLT("开奖号码红球第二区分布遗漏","fourArea",6,DataRange.THIRTY_SIX_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_THIRD_YILOU_DLT("开奖号码红球第三区分布遗漏","fiveArea",6,DataRange.THIRTY_SEVEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		//第二种红球分区
		RED_NUM_FOUR_YILOU_DLT("开奖号码红球第一区分布遗漏","sixArea",6,DataRange.THIRTY_EIGHT_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_FIVE_YILOU_DLT("开奖号码红球第二区分布遗漏","sevenArea",6,DataRange.THIRTY_NINE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_SIX_YILOU_DLT("开奖号码红球第三区分布遗漏","eightArea",6,DataRange.FORTY_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_SEVEN_YILOU_DLT("开奖号码红球第四区分布遗漏","nineArea",6,DataRange.FORTY_ONE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		//第三种红球分区
		RED_NUM_EIGHT_YILOU_DLT("开奖号码红球第一区分布遗漏","tenArea",6,DataRange.FORTY_TWO_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_NINE_YILOU_DLT("开奖号码红球第二区分布遗漏","elevenArea",6,DataRange.FORTY_THREE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_TEN_YILOU_DLT("开奖号码红球第三区分布遗漏","twelveArea",6,DataRange.FORTY_FOUR_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_ELEVEN_YILOU_DLT("开奖号码红球第四区分布遗漏","thirdteenArea",6,DataRange.FORTY_FIVE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_TWELVE_YILOU_DLT("开奖号码红球第五区分布遗漏","fourteenArea",6,DataRange.FORTY_SIX_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		//第四种红球分区
		RED_NUM_THIRTEEN_YILOU_DLT("开奖号码红球第一区分布遗漏","fiveteenArea",6,DataRange.TWENTY_TWO_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_FOURTEEN_YILOU_DLT("开奖号码红球第二区分布遗漏","sixteenArea",6,DataRange.TWENTY_THREE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_FIFTEEN_YILOU_DLT("开奖号码红球第三区分布遗漏","seventeenArea",6,DataRange.TWENTY_FOUR_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_SIXTEEN_YILOU_DLT("开奖号码红球第四区分布遗漏","eighteenArea",6,DataRange.TWENTY_FIVE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_SEVENTEEN_YILOU_DLT("开奖号码红球第五区分布遗漏","nineteenArea",6,DataRange.TWENTY_SIX_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_EIGHTEEN_YILOU_DLT("开奖号码红球第六区分布遗漏","twentyArea",6,DataRange.TWENTY_SEVEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		RED_NUM_NINETEEN_YILOU_DLT("开奖号码红球第七区分布遗漏","twentyOneArea",6,DataRange.FORTY_SEVEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_BEFORE_FIVE),
		BLUE_NUM_SPAN_YILOU_DLT("开奖号码后区蓝球跨度遗漏","twentyTwoArea",6,DataRange.ELEVEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_AFTER_TWO_DIFFERENCE),
		BLUE_NUM_DAOXIAO_YILOU_DLT("开奖号码后区蓝球大小形态遗漏","twentyThirdArea",6,DataRange.FORTY_EIGHT_RANGE,LeaveValueHandle.LOT_TWONUM_DAXIAO_YILOU,LotDrwawValue.DRAW_AFTER_TWO),
		BLUE_NUM_JIOU_YILOU_DLT("开奖号码后区蓝球奇偶形态遗漏","twentyFourArea",6,DataRange.FORTY_NINE_RANGE,LeaveValueHandle.LOT_TWONUM_JIOU_YILOU,LotDrwawValue.DRAW_AFTER_TWO),
		
		
		//福彩3D
		NUM_FIRST_FC_3D("开奖号码第一位遗漏","firstArea",7,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_FC_3D("开奖号码第二位遗漏","secondArea",7,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_FC_3D("开奖号码第三位遗漏","thirdArea",7,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_TOTAL_FC_3D("开奖号码分布遗漏","fourArea",7,DataRange.SECOND_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_SUM_FC_3D("开奖号码和值遗漏","fiveArea",7,DataRange.FIFTY_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SUM_NUM),
		NUM_SUM_WEI_FC_3D("开奖号码和值尾数遗漏","sixArea",7,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SUM_WEI_NUM),
		NUM_FIRST_DA_FC_3D("开奖号码第一位大号遗漏","sevenArea",7,DataRange.FIFTY_ONE_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_FIRST_XIAO_FC_3D("开奖号码第一位小号遗漏","eightArea",7,DataRange.FIFTY_TWO_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_DA_FC_3D("开奖号码第二位大号遗漏","nineArea",7,DataRange.FIFTY_ONE_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_SECOND_XIAO_FC_3D("开奖号码第二位小号遗漏","tenArea",7,DataRange.FIFTY_TWO_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_DA_FC_3D("开奖号码第三位大号遗漏","elevenArea",7,DataRange.FIFTY_ONE_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_THIRD_XIAO_FC_3D("开奖号码第三位小号遗漏","twelveArea",7,DataRange.FIFTY_TWO_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_FIRST_JI_FC_3D("开奖号码第一位奇号遗漏","thirdteenArea",7,DataRange.FIFTY_THREE_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_FIRST_OU_FC_3D("开奖号码第一位偶号遗漏","fourteenArea",7,DataRange.FIFTY_FOUR_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_JI_FC_3D("开奖号码第二位奇号遗漏","fiveteenArea",7,DataRange.FIFTY_THREE_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_SECOND_OU_FC_3D("开奖号码第二位偶号遗漏","sixteenArea",7,DataRange.FIFTY_FOUR_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_JI_FC_3D("开奖号码第三位奇号遗漏","seventeenArea",7,DataRange.FIFTY_THREE_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_THIRD_OU_FC_3D("开奖号码第三位偶号遗漏","eighteenArea",7,DataRange.FIFTY_FOUR_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.THIRD_NUM),
		
		
		//七星彩
		NUM_FIRST_YILOU_QXC("开奖号码第一位遗漏","firstArea",8,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_YILOU_QXC("开奖号码第二位遗漏","secondArea",8,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_YILOU_QXC("开奖号码第三位遗漏","thirdArea",8,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_FOUR_YILOU_QXC("开奖号码第四位遗漏","fourArea",8,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FOUR_NUM),
		NUM_FIVE_YILOU_QXC("开奖号码第五位遗漏","fiveArea",8,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIVE_NUM),
		NUM_SIX_YILOU_QXC("开奖号码第六位遗漏","sixArea",8,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SIX_NUM),
		NUM_SEVEN_YILOU_QXC("开奖号码第七位遗漏","sevenArea",8,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SEVEN_NUM),
		NUM_SUM_YILOU_QXC("开奖号码和值遗漏","eightArea",8,DataRange.FIFTY_FIVE_RANGE,LeaveValueHandle.STRING_NUM_STRING_YILOU,LotDrwawValue.SUM_NUM),
		NUM_SUM_WEI_YILOU_QXC("开奖号码和尾遗漏","nineArea",8,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SUM_WEI_NUM),
		NUM_FIRST_DAXIAO_YILOU_QXC("开奖号码第一位大小遗漏","tenArea",8,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_DAXIAO_YILOU_QXC("开奖号码第二位大小遗漏","elevenArea",8,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_DAXIAO_YILOU_QXC("开奖号码第三位大小遗漏","twelveArea",8,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_FOUR_DAXIAO_YILOU_QXC("开奖号码第四位大小遗漏","thirdteenArea",8,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FOUR_NUM),
		NUM_FIVE_DAXIAO_YILOU_QXC("开奖号码第五位大小遗漏","fourteenArea",8,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FIVE_NUM),
		NUM_SIX_DAXIAO_YILOU_QXC("开奖号码第六位大小遗漏","fiveteenArea",8,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.SIX_NUM),
		NUM_SEVEN_DAXIAO_YILOU_QXC("开奖号码第七位大小遗漏","sixteenArea",8,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.SEVEN_NUM),
		NUM_FIRST_JIOU_YILOU_QXC("开奖号码第一位奇偶遗漏","seventeenArea",8,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_JIOU_YILOU_QXC("开奖号码第二位奇偶遗漏","eighteenArea",8,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_JIOU_YILOU_QXC("开奖号码第三位奇偶遗漏","nineteenArea",8,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_FOUR_JIOU_YILOU_QXC("开奖号码第四位奇偶遗漏","twentyArea",8,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FOUR_NUM),
		NUM_FIVE_JIOU_YILOU_QXC("开奖号码第五位奇偶遗漏","twentyOneArea",8,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FIVE_NUM),
		NUM_SIX_JIOU_YILOU_QXC("开奖号码第六位奇偶遗漏","twentyTwoArea",8,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.SIX_NUM),
		NUM_SEVEN_JIOU_YILOU_QXC("开奖号码第七位奇偶遗漏","twentyThirdArea",8,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.SEVEN_NUM),
		NUM_DAXIAOBI_YILOU_QXC("开奖号码大小比遗漏","twentyFourArea",8,DataRange.EIGHTY_SIX_RANGE,LeaveValueHandle.NUM_DAXIAOBI_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_JIOUBI_YILOU_QXC("开奖号码奇偶比遗漏","twentyFiveArea",8,DataRange.FIFTY_SIX_RANGE,LeaveValueHandle.NUM_JIOUBI_YILOU,LotDrwawValue.DRAW_ALL),
		
		
		//七乐彩
		//第一种分区
		NUM_FIRST_YILOU_QLC("开奖号码第一区分布遗漏","firstArea",9,DataRange.FIFTY_SEVEN_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_SECOND_YILOU_QLC("开奖号码第二区分布遗漏","secondArea",9,DataRange.FIFTY_EIGHT_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_THIRD_YILOU_QLC("开奖号码第三区分布遗漏","thirdArea",9,DataRange.FIFTY_NINE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL),
		//第二种分区
		NUM_FOUR_YILOU_QLC("开奖号码第一区分布遗漏","fourArea",9,DataRange.SIXTY_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_FIVE_YILOU_QLC("开奖号码第二区分布遗漏","fiveArea",9,DataRange.SIXTY_ONE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_SIX_YILOU_QLC("开奖号码第三区分布遗漏","sixArea",9,DataRange.SIXTY_TWO_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_SEVEN_YILOU_QLC("开奖号码第四区分布遗漏","sevenArea",9,DataRange.SIXTY_THREE_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_EIGHT_YILOU_QLC("开奖号码第五区分布遗漏","eightArea",9,DataRange.SIXTY_FOUR_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL),
		
		NUM_XIAOJI_YILOU_QLC("开奖号码小奇分布遗漏","nineArea",9,DataRange.SIXTY_FIVE_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_XIAOOU_YILOU_QLC("开奖号码小偶分布遗漏","tenArea",9,DataRange.SIXTY_SIX_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_DAJI_YILOU_QLC("开奖号码大奇分布遗漏","elevenArea",9,DataRange.SIXTY_EIGHT_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_DAOU_YILOU_QLC("开奖号码大偶分布遗漏","twelveArea",9,DataRange.SIXTY_SEVEN_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.DRAW_ALL),
		
		
		
		//排列3
		NUM_FIRST_YILOU_PL3("开奖号码第一位遗漏","firstArea",10,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_YILOU_PL3("开奖号码第二位遗漏","secondArea",10,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_YILOU_PL3("开奖号码第三位遗漏","thirdArea",10,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_TOTAL_YILOU_PL3("开奖号码分布遗漏","fourArea",10,DataRange.SECOND_RANGE,LeaveValueHandle.LOT_DISTRIBUTE_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_SUM_YILOU_PL3("开奖号码和值遗漏","fiveArea",10,DataRange.FIFTY_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SUM_NUM),
		NUM_SUM_WEI_YILOU_PL3("开奖号码和尾遗漏","sixArea",10,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SUM_WEI_NUM),
		NUM_FIRST_DA_YILOU_PL3("开奖号码第一位大号遗漏","sevenArea",10,DataRange.FIFTY_ONE_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_FIRST_XIAO_YILOU_PL3("开奖号码第一位小号遗漏","eightArea",10,DataRange.FIFTY_TWO_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_DA_YILOU_PL3("开奖号码第二位大号遗漏","nineArea",10,DataRange.FIFTY_ONE_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_SECOND_XIAO_YILOU_PL3("开奖号码第二位小号遗漏","tenArea",10,DataRange.FIFTY_TWO_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_DA_YILOU_PL3("开奖号码第三位大号遗漏","elevenArea",10,DataRange.FIFTY_ONE_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_THIRD_XIAO_YILOU_PL3("开奖号码第三位小号遗漏","twelveArea",10,DataRange.FIFTY_TWO_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_FIRST_JI_YILOU_PL3("开奖号码第一位奇号遗漏","thirdteenArea",10,DataRange.FIFTY_THREE_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_FIRST_OU_YILOU_PL3("开奖号码第一位偶号遗漏","fourteenArea",10,DataRange.FIFTY_FOUR_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_JI_YILOU_PL3("开奖号码第二位奇号遗漏","fiveteenArea",10,DataRange.FIFTY_THREE_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_SECOND_OU_YILOU_PL3("开奖号码第二位偶号遗漏","sixteenArea",10,DataRange.FIFTY_FOUR_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_JI_YILOU_PL3("开奖号码第三位奇号遗漏","seventeenArea",10,DataRange.FIFTY_THREE_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_THIRD_OU_YILOU_PL3("开奖号码第三位偶号遗漏","eighteenArea",10,DataRange.FIFTY_FOUR_RANGE,LeaveValueHandle.NUM_JIOU_SHU_YILOU,LotDrwawValue.THIRD_NUM),
		
		
		//排列5
		NUM_FIRST_YILOU_PL5("开奖号码第一位遗漏","firstArea",11,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_YILOU_PL5("开奖号码第二位遗漏","secondArea",11,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_YILOU_PL5("开奖号码第三位遗漏","thirdArea",11,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_FOUR_YILOU_PL5("开奖号码第四位遗漏","fourArea",11,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FOUR_NUM),
		NUM_FIVE_YILOU_PL5("开奖号码第五位遗漏","fiveArea",11,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.FIVE_NUM),
		NUM_SUM_YILOU_PL5("开奖号码和值遗漏","sixArea",11,DataRange.SIXTY_NINE_RANGE,LeaveValueHandle.STRING_NUM_STRING_YILOU,LotDrwawValue.SUM_NUM),
		NUM_SUM_WEI_YILOU_PL5("开奖号码和尾遗漏","sevenArea",11,DataRange.SECOND_RANGE,LeaveValueHandle.NUM_YILOU,LotDrwawValue.SUM_WEI_NUM),
		NUM_FIRST_DAXIAO_YILOU_PL5("开奖号码第一位大小遗漏","eightArea",11,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_DAXIAO_YILOU_PL5("开奖号码第二位大小遗漏","nineArea",11,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_DAXIAO_YILOU_PL5("开奖号码第三位大小遗漏","tenArea",11,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_FOUR_DAXIAO_YILOU_PL5("开奖号码第四位大小遗漏","elevenArea",11,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FOUR_NUM),
		NUM_FIVE_DAXIAO_YILOU_PL5("开奖号码第五位大小遗漏","twelveArea",11,DataRange.SEVENTY_SEVEN_RANGE,LeaveValueHandle.DAXIAO_YILOU,LotDrwawValue.FIVE_NUM),
		NUM_FIRST_JIOU_YILOU_PL5("开奖号码第一位奇偶遗漏","thirdteenArea",11,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FIRST_NUM),
		NUM_SECOND_JIOU_YILOU_PL5("开奖号码第二位奇偶遗漏","fourteenArea",11,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.SECOND_NUM),
		NUM_THIRD_JIOU_YILOU_PL5("开奖号码第三位奇偶遗漏","fiveteenArea",11,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.THIRD_NUM),
		NUM_FOUR_JIOU_YILOU_PL5("开奖号码第四位奇偶遗漏","sixteenArea",11,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FOUR_NUM),
		NUM_FIVE_JIOU_YILOU_PL5("开奖号码第五位奇偶遗漏","seventeenArea",11,DataRange.SEVEN_RANGE,LeaveValueHandle.JIOU_YILOU,LotDrwawValue.FIVE_NUM),
		NUM_DAXIAOBI_YILOU_PL5("开奖号码大小比遗漏","eighteenArea",11,DataRange.EIGHTY_FOUR_RANGE,LeaveValueHandle.NUM_DAXIAOBI_YILOU,LotDrwawValue.DRAW_ALL),
		NUM_JIOUBI_YILOU_PL5("开奖号码奇偶比遗漏","nineteenArea",11,DataRange.THIRDTEEN_RANGE,LeaveValueHandle.NUM_JIOUBI_YILOU,LotDrwawValue.DRAW_ALL),
		
		;
		
		private String remark;
		private String key;
		private Integer series;
		private DataRange dataRange;
		private LeaveValueHandle leaveValueHandle;
		private LotDrwawValue lotDrwawValue;
		
		private HFLotChartPlan(String remark,String key,Integer series,DataRange dataRange,LeaveValueHandle leaveValueHandle,LotDrwawValue lotDrwawValue){
			this.remark=remark;
			this.key=key;
			this.series=series;
			this.dataRange=dataRange;
			this.leaveValueHandle=leaveValueHandle;
			this.lotDrwawValue=lotDrwawValue;
		}
		public Integer getSeries() {
			return series;
		}
		public void setSeries(Integer series) {
			this.series = series;
		}
		public String getRemark() {
			return remark;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public LeaveValueHandle getLeaveValueHandle() {
			return leaveValueHandle;
		}
		public void setLeaveValueHandle(LeaveValueHandle leaveValueHandle) {
			this.leaveValueHandle = leaveValueHandle;
		}
		public LotDrwawValue getLotDrwawValue() {
			return lotDrwawValue;
		}
		public void setLotDrwawValue(LotDrwawValue lotDrwawValue) {
			this.lotDrwawValue = lotDrwawValue;
		}
		public DataRange getDataRange() {
			return dataRange;
		}
		public void setDataRange(DataRange dataRange) {
			this.dataRange = dataRange;
		}
		
		public static List<HFLotChartPlan> getBySeries(Integer series) {
			HFLotChartPlan[] types = HFLotChartPlan.values();
			List<HFLotChartPlan> list=new ArrayList<HFLotChartPlan>();
			for (HFLotChartPlan type : types) {
				if (type.getSeries()==series) {
					list.add(type);
				}
			}
			return list;
		}
	}
	
	
	//走势图各种遗漏值统一处理
	public enum LeaveValueHandle{
		LOT_DISTRIBUTE_YILOU{//开奖号码分布遗漏处理
			public Map<String,Object> leaveHandle(String value,Map<String,Object> preleave,String valRange){
				String [] range=valRange.split(",");
				String [] values=value.split(",");
				Integer startNum=Integer.parseInt(range[0]);
				Integer endNum=Integer.parseInt(range[1]);
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=startNum;i<=endNum;i++){
						preleave.put(String.valueOf(i), 0);
					}
				}
				Map<String,Object> map=new LinkedHashMap<String,Object>();//返回本期遗漏值
				Integer temNum,leaveVal;//temNum 本期开奖号码在取值范围中每位的出现次数    leaveVal 取值范围中每位的遗漏值
				for(int i=startNum;i<=endNum;i++){
					String key=String.valueOf(i);
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					temNum=0;
					for(int j=0;j<values.length;j++){
						Integer curVal=Integer.parseInt(values[j]);//本期开奖号码
						if(curVal==i){
							temNum++;
						}
					}
					if(temNum>1){//中奖有重号情况
						leaveVal=preVal>0?-temNum*100000:(preVal%100000-1)-temNum*100000;
					}else if(temNum==1){//中奖没有重号
						leaveVal=preVal>0?0:(preVal%100000-1);
					}else{//没中奖
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		LOT_BLUE_DISTRIBUTE_YILOU{//双色球蓝球四分区遗漏  取值范围为数字  头显示为字符串  0-1:0,01&2-3:02,03&4-5:04,05&6-7:06,07&8-9:08,09
			public Map<String,Object> leaveHandle(String value,Map<String,Object> preleave,String valRange){
				String []rray=valRange.split("&");
				Integer num=Integer.parseInt(value);
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<rray.length;i++){
						String tem=rray[i];//0-1:0,01
						int sub=rray[i].lastIndexOf(":");
						String key=tem.substring(0, sub);
						preleave.put(key, 0);
					}	
				}
				
				Map<String,Object> map=new LinkedHashMap<String,Object>();
				Integer leaveVal;
				for(int i=0;i<rray.length;i++){
					String tem=rray[i];//0-1:0,01
					String [] keyVal=tem.split(":");
					String key=keyVal[0];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					int sub=keyVal[1].lastIndexOf(",");
					Integer startNum=Integer.parseInt(keyVal[1].substring(0, sub));
					Integer endNum=Integer.parseInt(keyVal[1].substring(sub+1, keyVal[1].length()));
					if(num>=startNum&&num<=endNum){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}	
				return map;
			}
		},
		LOT_TWONUM_DAXIAO_YILOU{//小小，小大，大大 两位数大小形态遗漏处理 格式："小小,小大,大大,01-06,07-12"
			public Map<String,Object> leaveHandle(String value,Map<String,Object> preleave,String valRange){
				String [] range=valRange.split("&");
				String [] temData=range[0].split(",");
				String [] range_xiao=range[1].split(",");
				String [] range_da=range[2].split(",");
				String [] values=value.split(",");
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<temData.length;i++){
						preleave.put(temData[i], 0);
					}
				}
				StringBuffer sb=new StringBuffer();
				Integer xiaoStart=Integer.parseInt(range_xiao[0]);
				Integer xiaoEnd=Integer.parseInt(range_xiao[1]);
				Integer daStart=Integer.parseInt(range_da[0]);
				Integer daEnd=Integer.parseInt(range_da[1]);
				for(int i=0;i<values.length;i++){
					Integer tem=Integer.parseInt(values[i]);
					if(tem>=xiaoStart&&tem<=xiaoEnd){
						sb.append("小");
					}
					if(tem>=daStart&&tem<=daEnd){
						sb.append("大");
					}
				}
				Map<String,Object> map=new LinkedHashMap<String,Object>();
				Integer leaveVal;
				for(int i=0;i<temData.length;i++){
					String key=temData[i];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(key.equals(sb.toString())){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		LOT_TWONUM_JIOU_YILOU{//奇奇，奇偶，偶奇，偶偶， 两位数奇偶形态遗漏处理 格式："奇奇，奇偶，偶奇，偶偶"
			public Map<String,Object> leaveHandle(String value,Map<String,Object> preleave,String valRange){
				String [] range=valRange.split(",");
				String [] values=value.split(",");
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<range.length;i++){
						preleave.put(range[i], 0);
					}
				}
				StringBuffer sb=new StringBuffer();
				for(int i=0;i<values.length;i++){
					Integer tem=Integer.parseInt(values[i]);
					if(tem%2==0){
						sb.append("偶");
					}else{
						sb.append("奇");
					}
				}
				Map<String,Object> map=new LinkedHashMap<String,Object>();
				Integer leaveVal;
				for(int i=0;i<range.length;i++){
					String key=range[i];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(key.equals(sb.toString())){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		NUM_YILOU{//开奖号码一位、和值等一位数字遗漏
			public Map<String,Object> leaveHandle(String value,Map<String,Object> preleave,String valRange){
				String [] range=valRange.split(",");
				Integer startNum=Integer.parseInt(range[0]);
				Integer endNum=Integer.parseInt(range[1]);
				Integer num=Integer.parseInt(value);
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=startNum;i<=endNum;i++){
						preleave.put(String.valueOf(i), 0);
					}
				}
				
				Map<String,Object> map=new LinkedHashMap<String,Object>();
				Integer leaveVal;
				for(int i=startNum;i<=endNum;i++){
					String key=String.valueOf(i);
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(num==i){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		DAXIAO_YILOU{//一位数的大小遗漏  "大:06,11&小:01,05"
			public Map<String,Object> leaveHandle(String value,Map<String,Object> preleave,String valRange){
				String [] range=new String[]{"大","小"};
				Integer num=Integer.parseInt(value);
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<range.length;i++){
						preleave.put(range[i], 0);
					}
				}
				String curVal="";
				String [] parmRange=valRange.split("&");
				for(int i=0;i<parmRange.length;i++){
					String []temRray=parmRange[i].split(":");//大    06,11
//					String temKey=temRray[0];//大
					String temVal=temRray[1];//String 06,11
					String [] temRange=temVal.split(",");//Arrays 06,11
					Integer startNum=Integer.parseInt(temRange[0]);
					Integer endNum=Integer.parseInt(temRange[1]);
					if(num>=startNum&&num<=endNum){
						curVal=temRray[0];//大
					}
				}
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
				Integer leaveVal;
				for(int i=0;i<range.length;i++){
					String key=range[i];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(curVal.equals(key)){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		JIOU_YILOU{//开奖号码一位的奇偶遗漏    奇,偶
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				String []range=valRange.split(",");
				Integer num=Integer.parseInt(value);
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<range.length;i++){
						preleave.put(range[i], 0);
					}
				}
				String curVal="";
				if(num%2==0){
					curVal="偶";
				}else{
					curVal="奇";
				}
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
				Integer leaveVal;
				for(int i=0;i<range.length;i++){
					String key=range[i];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(curVal.equals(key)){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		DAXIAOJIOU_YILOU{//和值小奇，小偶，大奇，大偶遗漏   快3和值专用
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				String [] range=valRange.split(",");
				Integer num=Integer.parseInt(value);
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<range.length;i++){
						preleave.put(range[i], 0);
					}
				}
				StringBuffer curVal=new StringBuffer();
				if(num>=3&&num<=10){
					curVal.append("小");
				}
				if(num>=11&&num<=18){
					curVal.append("大");
				}
				if(num%2==0){
					curVal.append("偶");
				}else{
					curVal.append("奇");
				}
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
				Integer leaveVal;
				for(int i=0;i<range.length;i++){
					String key=range[i];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(curVal.toString().equals(key)){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		DAXIAO_JIOU_YILOU{//一个数 大小奇偶  横向多选   时时彩专用
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				String [] range=valRange.split(",");  //大,小,奇,偶
				Integer num=Integer.parseInt(value);
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<range.length;i++){
						preleave.put(range[i], 0);
					}
				}
				StringBuffer curVal=new StringBuffer();
				if(num>=0&&num<=4){
					curVal.append("小");
				}
				if(num>=5&&num<=9){
					curVal.append("大");
				}
				if(num%2==0){
					curVal.append("偶");
				}else{
					curVal.append("奇");
				}
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
//				JSONObject map = new JSONObject(true);
//				JSONObject jsonObject = new JSONObject(true);
//				jsonObject.put(“key”, 你的map对象);
				Integer leaveVal;
				for(int i=0;i<range.length;i++){
					String key=range[i];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(curVal.toString().contains(key)){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		NUM_JIOU_SHU_YILOU{//取值范围跳跃式奇偶数遗漏1，3，5，7，9      2，4，6等
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				String [] range=valRange.split(",");  //1，3，5，7，9
//				Integer num=Integer.parseInt(value);
				String []values=value.split(",");//开奖号码
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<range.length;i++){
						Integer tem=Integer.parseInt(range[i]);
						preleave.put(tem.toString(), 0);
					}
				}
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
				Integer leaveVal;
				for(int i=0;i<range.length;i++){
					Integer key=Integer.parseInt(range[i]);
					Integer preVal=Integer.parseInt(preleave.get(key.toString()).toString());//取出上期对应遗漏值s
					Integer a=0;
					for(int j=0;j<values.length;j++){
						if(key==Integer.parseInt(values[j])){
							a++;
						}
					}
					if(a>0){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key.toString(), leaveVal);
				}
				return map;
			}
		},
		NUM_XINGTAI_YILOU{//开奖号码形态遗漏  三不同 二不同 三连号  三同号遗漏
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				String [] range=valRange.split(",");  //三不同 二不同 三连号  三同号
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<range.length;i++){
						preleave.put(range[i], 0);
					}
				}
				String temStr=TrendChartData.NumForm(value);
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
				Integer leaveVal;
				for(int i=0;i<range.length;i++){
					String key=range[i];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(temStr.contains(key)){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		NUM_JIOUBI_YILOU{//开奖号码奇偶比遗漏
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				String [] range=valRange.split(",");  //3:0,2:1,1:2,0:3
				String [] rray=value.split(",");
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<range.length;i++){
						preleave.put(range[i], 0);
					}
				}
				Integer ji=0,ou=0;
				for(int i=0;i<rray.length;i++){
					Integer a=Integer.parseInt(rray[i]);
					if(a%2==0){
						ou++;
					}else{
						ji++;
					}
				}
				String temStr=ji.toString().concat(":").concat(ou.toString());
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
				Integer leaveVal;
				for(int i=0;i<range.length;i++){
					String key=range[i];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(temStr.contains(key)){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		NUM_DAXIAOBI_YILOU{//开奖号码大小比遗漏   0:5,1:4,2:3,3:2,4:1,5:0&06,11&01,05
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				String [] total=valRange.split("&");
				String [] range=total[0].split(",");  // 0:5,1:4,2:3,3:2,4:1,5:0
				String []daRange=total[1].split(",");
				String []xiaoRange=total[2].split(",");
				String [] rray=value.split(",");
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<range.length;i++){
						preleave.put(range[i], 0);
					}
				}
				Integer a=Integer.parseInt(daRange[0]);
				Integer b=Integer.parseInt(daRange[1]);
				Integer c=Integer.parseInt(xiaoRange[0]);
				Integer d=Integer.parseInt(xiaoRange[1]);
				Integer da=0,xiao=0;
				for(int i=0;i<rray.length;i++){
					Integer e=Integer.parseInt(rray[i]);
					if(e>=a&&e<=b){
						da++;
					}
					if(e>=c&&e<=d){
						xiao++;
					}
				}
				String temStr=da.toString().concat(":").concat(xiao.toString());
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
				Integer leaveVal;
				for(int i=0;i<range.length;i++){
					String key=range[i];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(temStr.contains(key)){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		NUM_JISHU_YILOU{//开奖号码奇数个数遗漏
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				String [] range=valRange.split(",");  // 0,5
				Integer startNum=Integer.parseInt(range[0]);
				Integer endNum=Integer.parseInt(range[1]);
				String [] rray=value.split(",");
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=startNum;i<=endNum;i++){
						preleave.put(String.valueOf(i), 0);
					}
				}
				Integer ji=0;
				for(int i=0;i<rray.length;i++){
					Integer num=Integer.parseInt(rray[i]);
					if(num%2!=0){
						ji++;
					}
				}
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
				Integer leaveVal;
				for(int i=startNum;i<=endNum;i++){
					String key=String.valueOf(i);
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(ji==i){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		NUM_OUSHU_YILOU{//开奖号码偶数个数遗漏
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				String [] range=valRange.split(",");  // 0,5
				Integer startNum=Integer.parseInt(range[0]);
				Integer endNum=Integer.parseInt(range[1]);
				String [] rray=value.split(",");
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=startNum;i<=endNum;i++){
						preleave.put(String.valueOf(i), 0);
					}
				}
				Integer ou=0;
				for(int i=0;i<rray.length;i++){
					Integer num=Integer.parseInt(rray[i]);
					if(num%2==0){
						ou++;
					}
				}
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
				Integer leaveVal;
				for(int i=startNum;i<=endNum;i++){
					String key=String.valueOf(i);
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(ou==i){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		NUM_UNSAME_YILOU{//开奖号码三不同遗漏
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				String [] range=valRange.split(",");  // 123，234，345
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<range.length;i++){
						preleave.put(range[i], 0);
					}
				}
				String temStr=value.replaceAll(",", "");
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
				Integer leaveVal;
				for(int i=0;i<range.length;i++){
					String key=range[i];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(key.equals(temStr)){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		NUM_SHENG_PING_JIANG_YILOU{//开奖号码升平降遗漏
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				String [] range=valRange.split(",");  // 升，平，降
				String [] rray=value.split(",");
				Integer preNum=Integer.parseInt(rray[0]);
				Integer subNum=Integer.parseInt(rray[1]);
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					for(int i=0;i<range.length;i++){
						preleave.put(range[i], 0);
					}
				}
				Map<String,Object> map=new LinkedHashMap<String,Object>();
				String temStr="";
				if(preNum<subNum){
					temStr="升";
				}
				if(preNum==subNum){
					temStr="平";
				}
				if(preNum>subNum){
					temStr="降";
				}
				Integer leaveVal=0;
				for(int i=0;i<range.length;i++){
					String key=range[i];
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期对应遗漏值
					if(key.equals(temStr)){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				return map;
			}
		},
		STRING_NUM_STRING_YILOU{//七星彩"0-21&22,38&39-63"分三段和值遗漏中间段展开显示
			@Override
			public Map<String, Object> leaveHandle(String value,Map<String,Object> preleave,String valRange) {
				Integer sum=Integer.parseInt(value);
				String [] range=valRange.split("&");  // "0-21&22,38&39-63"
				String perRange=range[0];
				String [] midRange=range[1].split(",");
				String subRange=range[2];
				Integer startNum=Integer.parseInt(midRange[0]);
				Integer endNum=Integer.parseInt(midRange[1]);
				if(Objects.isNull(preleave)){//上期遗漏值为null时  全部补0
					preleave=new LinkedHashMap<String, Object>();
					preleave.put(perRange, 0);
					for(int i=startNum;i<=endNum;i++){
						preleave.put(String.valueOf(i), 0);
					}
					preleave.put(subRange, 0);
				}
				
				Map<String,Object> map=new LinkedHashMap<String,Object>(); 
				Integer leaveVal;
				String []preRray=perRange.split("-");
				String []subRray=subRange.split("-");
				Integer prestartNum=Integer.parseInt(preRray[0]);
				Integer preendNum=Integer.parseInt(preRray[1]);
				Integer substartNum=Integer.parseInt(subRray[0]);
				Integer subendNum=Integer.parseInt(subRray[1]);
				Integer preleaveVal=Integer.parseInt(preleave.get(perRange).toString());//取出上期前面一个字符串对应遗漏值
				if(sum>=prestartNum&&sum<=preendNum){
					leaveVal=preleaveVal>0?0:preleaveVal-1;
				}else{
					leaveVal=preleaveVal>0?preleaveVal+1:1;
				}
				map.put(perRange, leaveVal);
				for(int i=startNum;i<=endNum;i++){
					String key=String.valueOf(i);
					Integer preVal=Integer.parseInt(preleave.get(key).toString());//取出上期中间一段对应遗漏值
					if(sum==i){
						leaveVal=preVal>0?0:preVal-1;
					}else{
						leaveVal=preVal>0?preVal+1:1;
					}
					map.put(key, leaveVal);
				}
				Integer subleaveVal=Integer.parseInt(preleave.get(subRange).toString());//取出上期后面一个字符串对应遗漏值
				if(sum>=substartNum&&sum<=subendNum){
					leaveVal=subleaveVal>0?0:subleaveVal-1;
				}else{
					leaveVal=subleaveVal>0?subleaveVal+1:1;
				}
				map.put(subRange, leaveVal);
				return map;
			}
		}
		
		;
		/**
		 * drawCode 开奖号码
		 * preleave 上期遗漏
		 * valRange 遗漏取值范围
		 */
		public abstract Map<String,Object> leaveHandle(String value,Map<String,Object> preleave,String valRange);
	}
	
	//各种取值范围
	public enum DataRange{
		FIRST_RANGE("3,18"),
		SECOND_RANGE("0,9"),
		THIRD_RANGE("小奇,小偶,大奇,大偶"),
		FOUR_RANGE("1,6"),
		FIVE_RANGE("三不同,二不同,三同号,三连号"),
		SIX_RANGE("123,124,125,126,134,135,136,145,146,156,234,235,236,245,246,256,345,346,356,456"),
		SEVEN_RANGE("奇,偶"),
		EIGHT_RANGE("3:0,2:1,1:2,0:3"),
		NINE_RANGE("0,3"),
		TEN_RANGE("15,45"),
		ELEVEN_RANGE("01,11"),
		TWELVE_RANGE("大,小"),
		THIRDTEEN_RANGE("0:5,1:4,2:3,3:2,4:1,5:0"),
		FOURTEEN_RANGE("大,小,奇,偶"),
		FIVETEEN_RANGE("升,平,降"),
		SIXTEEN_RANGE("12,22"),
		SEVENTEEN_RANGE("23,33"),
		EIGHTEEN_RANGE("01,08"),
		NINETEEN_RANGE("09,17"),
		TWENTRY_RANGE("18,25"),
		TWENTRY_ONE_RANGE("26,33"),
		TWENTY_TWO_RANGE("01,05"),
		TWENTY_THREE_RANGE("06,10"),
		TWENTY_FOUR_RANGE("11,15"),
		TWENTY_FIVE_RANGE("16,20"),
		TWENTY_SIX_RANGE("21,25"),
		TWENTY_SEVEN_RANGE("26,30"),
		TWENTY_EIGHT_RANGE("31,33"),
		TWENTY_NINE_RANGE("01,04"),
		THIRTY_ONE_RANGE("05,08"),
		THIRTY_TWO_RANGE("09,12"),
		THIRTY_THREE_RANGE("13,16"),
		THIRTY_FOUR_RANGE("一区,二区,三区,四区"),
		THIRTY_FIVE_RANGE("01,12"),
		THIRTY_SIX_RANGE("13,24"),
		THIRTY_SEVEN_RANGE("25,35"),
		THIRTY_EIGHT_RANGE("01,09"),
		THIRTY_NINE_RANGE("10,18"),
		FORTY_RANGE("19,27"),
		FORTY_ONE_RANGE("28,35"),
		FORTY_TWO_RANGE("01,07"),
		FORTY_THREE_RANGE("08,14"),
		FORTY_FOUR_RANGE("15,21"),
		FORTY_FIVE_RANGE("22,28"),
		FORTY_SIX_RANGE("29,35"),
		FORTY_SEVEN_RANGE("31,35"),
		FORTY_EIGHT_RANGE("小小,小大,大大&01,06&07,12"),
		FORTY_NINE_RANGE("奇奇,奇偶,偶奇,偶偶"),
		FIFTY_RANGE("0,27"),
		FIFTY_ONE_RANGE("5,9"),
		FIFTY_TWO_RANGE("0,4"),
		FIFTY_THREE_RANGE("1,3,5,7,9"),
		FIFTY_FOUR_RANGE("0,2,4,6,8"),
		FIFTY_FIVE_RANGE("0-21&22,38&39-63"),
		FIFTY_SIX_RANGE("7:0,6:1,5:2,4:3,3:4,2:5,1:6,0:7"),
		FIFTY_SEVEN_RANGE("01,10"),
		FIFTY_EIGHT_RANGE("11,20"),
		FIFTY_NINE_RANGE("21,30"),
		SIXTY_RANGE("01,06"),
		SIXTY_ONE_RANGE("07,12"),
		SIXTY_TWO_RANGE("13,18"),
		SIXTY_THREE_RANGE("19,24"),
		SIXTY_FOUR_RANGE("25,30"),
		SIXTY_FIVE_RANGE("01,03,05,07,09,11,13,15"),
		SIXTY_SIX_RANGE("02,04,06,08,10,12,14"),
		SIXTY_SEVEN_RANGE("16,18,20,22,24,26,28,30"),
		SIXTY_EIGHT_RANGE("17,19,21,23,25,27,29"),
		SIXTY_NINE_RANGE("0-11&12,33&34-45"),
		SEVENTY_RANGE("01,20"),
		SEVENTY_ONE_RANGE("01,21"),
		SEVENTY_TWO_RANGE("01,35"),
		SEVENTY_THIRD_RANGE("01,33"),
		SEVENTY_FOUR_RANGE("01,16"),
		SEVENTY_FIVE_RANGE("0,5"),
		SEVENTY_SIX_RANGE("大:06,11&小:01,05"),
		SEVENTY_SEVEN_RANGE("大:5,9&小:0,4"),
		SEVENTY_EIGHT_RANGE("大:11,20&小:01,10"),
		SEVENTY_NINE_RANGE("大:11,21&小:01,10"),
		EIGHTY_ONE_RANGE("大:09,16&小:01,08"),
		EIGHTY_TWO_RANGE("一区:01,04&二区:05,08&三区:09,12&四区:13,16"),
		EIGHTY_THIRD_RANGE("0-1:0,01&2-3:02,03&4-5:04,05&6-7:06,07&8-9:08,09"),
		EIGHTY_FOUR_RANGE("0:5,1:4,2:3,3:2,4:1,5:0&5,9&0,4"),
		EIGHTY_FIVE_RANGE("0:5,1:4,2:3,3:2,4:1,5:0&06,11&01,05"),
		EIGHTY_SIX_RANGE("7:0,6:1,5:2,4:3,3:4,2:5,1:6,0:7&5,9&0,4"),
		EIGHTY_SEVEN_RANGE("大,小,奇,偶"),
		;
		
		private String range;
		private DataRange(String range){
			this.range=range;
		}
		public String getRange() {
			return range;
		}
		public void setRange(String range) {
			this.range = range;
		}
		
		
	}
	
	//走势图各种遗漏对应的要处理的数据
	public enum LotDrwawValue{
		FIRST_NUM{//开奖号码第一位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					if(temArray.length>=1){
						return temArray[0];
					}
				}
				return null;
			}
		},
		SECOND_NUM{//开奖号码第二位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					if(temArray.length>=2){
						return temArray[1];
					}
				}
				return null;
			}
		},
		THIRD_NUM{//开奖号码第三位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					if(temArray.length>=3){
						return temArray[2];
					}
				}
				return null;
			}
			
		},
		FOUR_NUM{//开奖号码第四位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					if(temArray.length>=4){
						return temArray[3];
					}
				}
				return null;
			}
			
		},
		FIVE_NUM{//开奖号码第五位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					if(temArray.length>=5){
						return temArray[4];
					}
				}
				return null;
			}
			
		},
		SIX_NUM{//开奖号码第六位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					if(temArray.length>=6){
						return temArray[5];
					}
				}
				return null;
			}
			
		},
		SEVEN_NUM{//开奖号码第七位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					if(temArray.length>=7){
						return temArray[6];
					}
				}
				return null;
			}
			
		},
		EIGHT_NUM{//开奖号码第八位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					if(temArray.length>=8){
						return temArray[7];
					}
				}
				return null;
			}
		},
		SUM_NUM{
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					Integer sum=0;
					for(int i=0;i<temArray.length;i++){
						sum+=Integer.parseInt(temArray[i]);
					}
					return sum.toString();
				}
				return null;
			}
		},
		SUM_WEI_NUM{//和值尾号
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					Integer sum=0;
					for(int i=0;i<temArray.length;i++){
						sum+=Integer.parseInt(temArray[i]);
					}
					return String.valueOf(sum%10);
				}
				return null;
			}
		},
		DRAW_ALL{
			@Override
			public String convert(String value) {
				return value;
			}
		},
		DRAW_AFTER_THIRD{
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					StringBuffer sb=new StringBuffer();
					int tem=temArray.length;
					if(tem>=3){
						for(int i=tem-3;i<tem;i++){
							sb.append(temArray[i]);
							if(i<tem-1){
								sb.append(",");
							}
						}
						return sb.toString();
					}
				}
				return null;
			}
		},
		DRAW_AFTER_TWO{//开奖号码后两位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					StringBuffer sb=new StringBuffer();
					int tem=temArray.length;
					if(tem>=2){
						for(int i=tem-2;i<tem;i++){
							sb.append(temArray[i]);
							if(i<tem-1){
								sb.append(",");
							}
						}
						return sb.toString();
					}
				}
				return null;
			}
		},
		DRAW_AFTER_TWO_DIFFERENCE{//开奖号码后两位差
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					int tem=temArray.length;
					if(tem>=2){
						Integer diff=0;
						for(int i=tem-2;i<tem;i++){
							diff=Math.abs(diff-Integer.parseInt(temArray[i]));
						}
						return diff.toString();
					}
				}
				return null;
			}
		},
		DRAW_BEFORE_FIVE{//开奖号码前五位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					StringBuffer sb=new StringBuffer();
					int tem=temArray.length;
					if(tem>=5){
						for(int i=0;i<tem;i++){
							sb.append(temArray[i]);
							if(i==4){break;}
							if(i<tem-1){
								sb.append(",");
							}
						}
						return sb.toString();
					}
				}
				return null;
			}
		},
		DRAW_AFTER_ONE{//开奖号码后一位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					return temArray[temArray.length-1];
				}
				return null;
			}
		},
		DRAW_AFTER_ONE_WEI{//开奖号码后一位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					String num=temArray[temArray.length-1];
					return num.substring(num.length()-1, num.length());
				}
				return null;
			}
		},
		DRAW_BEFORE_SIX{//开奖号码前六位
			@Override
			public String convert(String value) {
				if(StringUtils.isNotEmpty(value)){
					String [] temArray=value.split(",");
					StringBuffer sb=new StringBuffer();
					int tem=temArray.length;
					if(tem>=6){
						for(int i=0;i<tem;i++){
							sb.append(temArray[i]);
							if(i==5){break;}
							if(i<tem-1){
								sb.append(",");
							}
						}
						return sb.toString();
					}
				}
				return null;
			}
		},
		;
		/**
		 * 
		 * @param value 开奖号码
		 * @return
		 */
		public abstract String convert(String value);
	}
	
	public enum LotField{
		/**
		 * 快3走势图所需字段
		 */
		KUAI_SAN_ATTR{
			@Override
			public Map<String, Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr) {
				Map<String,Object> map=Maps.newConcurrentMap();
				map.put("preDrawIssue", issue);//期号
				map.put("lotCode", lotCodeStr);//彩种编码
				map.put("numberSum", countSum(drawCode));//和值
				map.put("sumOddEven", sumOddEven(sum));//和值奇偶
				map.put("preDrawCode", drawCode);//开奖号码
				map.put("spanVal", numSpan(drawCode));//跨度
				map.put("parityForm", parityForm(drawCode)); //每位号码的奇偶形态
				map.put("singleDoubleRatio", singleDoubleRatio(drawCode));//奇偶比
				Map<String,String> m=new LinkedHashMap<String,String>();
				m.put("大", "4,6");
				m.put("小", "1,3");
				map.put("bigSmallRatio", areaRatio(drawCode,m));//大小比
				map.put("numberForm", NumForm(drawCode));//号码形态  三不同  三连号
				return map;
			}
		},
		/**
		 * 11选5走势图所需字段
		 */
		ELEVEN_FIVE_ATTR{
			@Override
			public Map<String, Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr) {
				Map<String,Object> map=Maps.newConcurrentMap();
				map.put("preDrawIssue", issue);//期号
				map.put("lotCode", lotCodeStr);//彩种编码
				map.put("numberSum", countSum(drawCode));//和值
				map.put("sumMantissa", sumMantissa(sum));//和值尾数
				map.put("preDrawCode", drawCode);//开奖号码
				map.put("spanVal", numSpan(drawCode));//跨度
				map.put("singleDoubleRatio", singleDoubleRatio(drawCode));//奇偶比
				Map<String,String> m=new LinkedHashMap<String,String>();
				m.put("大", "6,11");
				m.put("小", "1,5");
				map.put("bigSmallRatio", areaRatio(drawCode,m));
				return map;
			}
		},
		/**
		 * 时时彩走势图所需字段
		 */
		SHI_SHI_ATTR{
			@Override
			public Map<String, Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr) {
				Map<String,Object> map=Maps.newConcurrentMap();
				map.put("preDrawIssue", issue);//期号
				map.put("lotCode", lotCodeStr);//彩种编码
				map.put("preDrawCode", drawCode);//开奖号码
				return map;
			}
		},
		/**
		 * 快乐十分走势图所需字段
		 */
		KUAI_LE_ATTR{
			@Override
			public Map<String, Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr) {
				Map<String,Object> map=Maps.newConcurrentMap();
				map.put("preDrawIssue", issue);//期号
				map.put("lotCode", lotCodeStr);//彩种编码
				map.put("numberSum", countSum(drawCode));//和值
				map.put("preDrawCode", drawCode);//开奖号码
				map.put("spanVal", numSpan(drawCode));//跨度
				return map;
			}
		},
		/**
		 * 双色球走势图所需字段
		 */
		//TODO 走势图无遗漏值字段
		SHUANGSEQIU_ATTR{
			@Override
			public Map<String, Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr) {
				String redcode=subStr(drawCode, 6);
				Map<String,Object> map=Maps.newConcurrentMap();
				map.put("preDrawIssue", issue);//期号
				map.put("lotCode", lotCodeStr);//彩种编码
				map.put("preDrawCode", drawCode);//开奖号码
				map.put("spanVal", numSpan(redcode));//跨度
				map.put("numberSum", countSum(redcode));//和值
				map.put("singleDoubleRatio", singleDoubleRatio(redcode));//红球奇偶比
				map.put("blueMantissa", blueMantissa(drawCode));//蓝球尾数
				
				Map<String,String> m=new LinkedHashMap<String,String>();
				m.put("一区", "1,11");
				m.put("二区", "12,22");
				m.put("三区", "23,33");
				map.put("thirdAreaRatio", areaRatio(redcode,m));//三区区间比
				Map<String,String> m1=new LinkedHashMap<String,String>();
				m1.put("一区", "1,8");
				m1.put("二区", "9,17");
				m1.put("三区", "18,25");
				m1.put("四区", "26,33");
				map.put("fourIntervalNumber", areaNumber(redcode,m1));//四区号码个数
				Map<String,String> m2=new LinkedHashMap<String,String>();
				m2.put("一区", "1,5");
				m2.put("二区", "6,10");
				m2.put("三区", "11,15");
				m2.put("四区", "16,20");
				m2.put("五区", "21,25");
				m2.put("六区", "26,30");
				m2.put("七区", "31,33");
				map.put("sevenIntervalNumber", areaNumber(redcode,m2));//七区号码个数
				return map;
			}
		},
		/**
		 * 大乐透走势图所需字段
		 */
		DALETOU_ATTR{
			@Override
			public Map<String, Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr) {
				String redcode=subStr(drawCode, 5);
				Map<String,Object> map=Maps.newConcurrentMap();
				map.put("preDrawIssue", issue);//期号
				map.put("lotCode", lotCodeStr);//彩种编码
				map.put("preDrawCode", drawCode);//开奖号码
				map.put("spanVal", numSpan(redcode));//跨度
				map.put("blueSpanVal", blueNumSpan(drawCode));//蓝球跨度
				map.put("numberSum", countSum(redcode));//和值
				
				Map<String,String> m=new LinkedHashMap<String,String>();
				m.put("一区", "1,12");
				m.put("二区", "13,24");
				m.put("三区", "25,35");
				map.put("thirdAreaRatio", areaRatio(redcode,m)); //三区区间比
				Map<String,String> m1=new LinkedHashMap<String,String>();
				m1.put("一区", "1,9");
				m1.put("二区", "10,18");
				m1.put("三区", "19,27");
				m1.put("四区", "28,35");
				map.put("fourAreaRatio", areaRatio(redcode,m1));//四区区间比
				Map<String,String> m2=new LinkedHashMap<String,String>();
				m2.put("一区", "1,7");
				m2.put("二区", "8,14");
				m2.put("三区", "15,21");
				m2.put("四区", "22,28");
				m2.put("五区", "29,35");
				map.put("fiveAreaRatio", areaRatio(redcode,m2));//五区区间比
				Map<String,String> m3=new LinkedHashMap<String,String>();
				m3.put("一区", "1,5");
				m3.put("二区", "6,10");
				m3.put("三区", "11,15");
				m3.put("四区", "16,20");
				m3.put("五区", "21,25");
				m3.put("六区", "26,30");
				m3.put("七区", "31,35");
				map.put("sevenAreaRatio", areaRatio(redcode,m3));//七区区间比
//				map.put("singleDoubleRatio", singleDoubleRatio(drawCode));
				return map;
			}
		},
		/**
		 * 福彩3D走势图所需字段
		 */
		FUCAISAND_ATTR{
			@Override
			public Map<String, Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr) {
				Object countSum=countSum(drawCode);
				Map<String,Object> map=Maps.newConcurrentMap();
				map.put("preDrawIssue", issue);//期号
				map.put("lotCode", lotCodeStr);//彩种编码
				map.put("preDrawCode", drawCode);//开奖号码
				map.put("numberSum", countSum);//和值
				map.put("sumMantissa", sumMantissa(countSum));//和值尾数
				Map<String,Object> mmp=Maps.newConcurrentMap();
				mmp.put("groupSix", zuSix(drawCode));
				mmp.put("groupThree", zuThird(drawCode));
				map.put("groupThreeOrSix", mmp);
//				map.put("zuThird", zuThird(drawCode));//组三
//				map.put("zuSix", zuSix(drawCode));//组六
				return map;
			}
		},
		/**
		 * 七星彩走势图所需字段
		 */
		QIXINGCAI_ATTR{
			@Override
			public Map<String, Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr) {
				Object countSum=countSum(drawCode);
				Map<String,Object> map=Maps.newConcurrentMap();
				map.put("preDrawIssue", issue);//期号
				map.put("lotCode", lotCodeStr);//彩种编码
				map.put("preDrawCode", drawCode);//开奖号码
				map.put("numberSum", countSum);//和值
				map.put("sumMantissa", sumMantissa(countSum));//和值尾数
				map.put("oddEvenForm", parityForm(drawCode));//多号奇偶形态
				map.put("singleDoubleRatio", singleDoubleRatio(drawCode));//奇偶比
				map.put("sizeForm", sizeForm(drawCode,new Integer[]{5,9},new Integer[]{0,4})); //大小形态
				Map<String,String> m=new LinkedHashMap<String,String>();
				m.put("大", "5,9");
				m.put("小", "0,4");
				map.put("bigSmallRatio", areaRatio(drawCode,m));//大小比
				return map;
			}
		},
		/** 
		 * 七乐彩走势图所需字段
		 */
		QILECAI_ATTR{
			@Override
			public Map<String, Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr) {
				Map<String,Object> map=Maps.newConcurrentMap();
				map.put("preDrawIssue", issue);//期号
				map.put("lotCode", lotCodeStr);//彩种编码
				map.put("preDrawCode", drawCode);//开奖号码
				Integer index=drawCode.lastIndexOf(",");
				String code=drawCode.substring(0, index);
				map.put("numberSum", countSum(code));//和值
				map.put("spanVal", numSpan(code));//跨度
				Map<String,String> mmp=new LinkedHashMap<String,String>();
				mmp.put("一区", "1,10");
				mmp.put("二区", "11,20");
				mmp.put("三区", "21,30");
				map.put("thirdAreaRatio", areaRatio(code,mmp));//三区区间比
				Map<String,String> mp=new LinkedHashMap<String,String>();
				mp.put("一区", "1,6");
				mp.put("二区", "7,12");
				mp.put("三区", "13,18");
				mp.put("四区", "19,24");
				mp.put("五区", "25,30");
				map.put("fiveAreaRatio", areaRatio(code,mp));//五区区间比
				map.put("fiveAreaNumber", areaNumber(code,mp));//五区个数
				map.put("singleDoubleRatio", singleDoubleRatio(code));//奇偶比
				Map<String,String> m=new LinkedHashMap<String,String>();
				m.put("大", "16,30");
				m.put("小", "1,15");
				map.put("bigSmallRatio", areaRatio(code,m));//大小比
				return map;
			}
		},
		/**
		 * 排列3走势图所需字段
		 */
		PAILIETHIRD_ATTR{
			@Override
			public Map<String, Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr) {
				Object countSum=countSum(drawCode);
				Map<String,Object> map=Maps.newConcurrentMap();
				map.put("preDrawIssue", issue);//期号
				map.put("lotCode", lotCodeStr);//彩种编码
				map.put("preDrawCode", drawCode);//开奖号码
				map.put("numberSum", countSum);//和值
				map.put("sumMantissa", sumMantissa(countSum));//和值尾数
//				map.put("zuThird", zuThird(drawCode));
//				map.put("zuSix", zuSix(drawCode));
				Map<String,Object> mmp=Maps.newConcurrentMap();
				mmp.put("groupSix", zuSix(drawCode));
				mmp.put("groupThree", zuThird(drawCode));
				map.put("groupThreeOrSix", mmp);
				return map;
			}
		},
		/**
		 * 排列5走势图所需字段
		 */
		PAILIEFIVE_ATTR{
			@Override
			public Map<String, Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr) {
				Object countSum=countSum(drawCode);
				Map<String,Object> map=Maps.newConcurrentMap();
				map.put("preDrawIssue", issue);//期号
				map.put("lotCode", lotCodeStr);//彩种编码
				map.put("preDrawCode", drawCode);//开奖号码
				map.put("numberSum", countSum);//和值
				map.put("sizeForm", sizeForm(drawCode,new Integer[]{5,9},new Integer[]{0,4}));//大小形态
				Map<String,String> mmp=new LinkedHashMap<String,String>();
				mmp.put("大", "5,9");
				mmp.put("小", "0,4");
				map.put("bigSmallRatio", areaRatio(drawCode,mmp));//大小比
				map.put("oddEvenForm", parityForm(drawCode));//多号奇偶形态
				map.put("singleDoubleRatio", singleDoubleRatio(drawCode));//奇偶比
				return map;
			}
		},
		;
		
		public abstract Map<String,Object> Attributes(String drawCode,Object sum,String issue,String lotCodeStr);
	}
	//开奖号码形态
	public static String NumForm(String drawCode){
		StringBuffer sb=new StringBuffer();
		String [] rray=drawCode.split(",");
		Integer one=Integer.parseInt(rray[0]);
		Integer two=Integer.parseInt(rray[1]);
		Integer three=Integer.parseInt(rray[2]);
		if(one==two&&one==three){
			sb.append("三同号").append(",");
		}
		if(one!=two&&two!=three&&one!=three){
			sb.append("三不同").append(",");
		}
		if(one!=two||two!=three||one!=three){
			sb.append("二不同").append(",");
		}
		if(one-two==two-three&&Math.abs(one-two)==1){
			sb.append("三连号").append(",");
		}
		return sb.deleteCharAt(sb.length()-1).toString();
	}
	
	//号码奇偶形态  奇偶奇  多号码
	public static String parityForm(String drawCode){
		StringBuffer str=new StringBuffer();
		String [] rray=drawCode.split(",");
		String temStr="";
		for(int i=0;i<rray.length;i++){
			Integer num=Integer.parseInt(rray[i]);
			if(num%2==0){
				temStr="偶";
			}else{
				temStr="奇";
			}
			str.append(temStr);
		}
		return str.toString();
	}
	
	//号码奇偶比  去掉后面N个号码
	public static String singleDoubleRatioExt(String drawCode,Integer num){
		String [] rray=drawCode.split(",");
		StringBuffer buf=new StringBuffer();
		Integer len=rray.length-num;
		for(int i=0;i<len;i++){
			buf.append(rray[i]);
			if(i!=len-1){
				buf.append(",");
			}
		}
		return singleDoubleRatio(buf.toString());
	}
	//号码奇偶比  
	public static String singleDoubleRatio(String drawCode){
		String [] rray=drawCode.split(",");
		Integer oddNum=0,evenNum=0;
		for(int i=0;i<rray.length;i++){
			Integer num=Integer.parseInt(rray[i]);
			if(num%2==0){
				evenNum++;
			}else{
				oddNum++;
			}
		}
		return oddNum.toString().concat(":").concat(evenNum.toString());
	}
	
	
	//开奖号码跨度
	public static Integer numSpan(String drawCode){
		String [] rray=drawCode.split(",");
		Integer max=null,min=null;
		for(int i=0;i<rray.length;i++){
			Integer num=Integer.parseInt(rray[i]);
			if(Objects.isNull(max)&&Objects.isNull(min)){
				max=num;
				min=num;
			}else{
				if(num>max){
					max=num;
				}
				if(num<min){
					min=num;
				}
			}
		}
		return max-min;
	}
	//开奖号码跨度
	public static Integer blueNumSpan(String drawCode){
		String [] rray=drawCode.split(",");
		Integer len=rray.length;
		StringBuffer buf=new StringBuffer();
		buf.append(rray[len-2]).append(",").append(rray[len-1]);
		return numSpan(buf.toString());
	}
	
	//去掉后面N个号码的区间比 双色球去一位  大乐透去两位
	public static String areaRatioExt(String drawCode,Map<String,String> map,Integer num){
		String [] rray=drawCode.split(",");
		StringBuffer buf=new StringBuffer();
		Integer len=rray.length-num;
		for(int i=0;i<len;i++){
			buf.append(rray[i]);
			if(i!=len-1){
				buf.append(",");
			}
		}
		return areaRatio(buf.toString(), map);
	}
	
	
	//开奖号码大小比 区间比
	public static String areaRatio(String drawCode,Map<String,String> map){
		String [] rray=drawCode.split(",");
		StringBuffer buf=new StringBuffer();
		for(Map.Entry<String, String> entry:map.entrySet()){
			String value=entry.getValue();
			String []range=value.split(",");
			Integer start=Integer.parseInt(range[0]);
			Integer end=Integer.parseInt(range[1]);
			Integer count=0;
			for(int i=0;i<rray.length;i++){
				Integer num=Integer.parseInt(rray[i]);
				if(num>=start&&num<=end){
					count++;
				}
			}
			buf.append(count.toString()).append(":");
		}
		return buf.deleteCharAt(buf.length()-1).toString();
	}
	//大小形态
	public static String sizeForm(String drawCode,Integer [] big, Integer [] small){
		String [] rray=drawCode.split(",");
		StringBuffer buf=new StringBuffer();
		for(int i=0;i<rray.length;i++){
			Integer num=Integer.parseInt(rray[i]);
			if(num>=big[0]&&num<=big[1]){
				buf.append("大");
			}
			if(num>=small[0]&&num<=small[1]){
				buf.append("小");
			}
		}
		return buf.toString();
	}
	
	//开奖号码区间个数
	public static String areaNumberExt(String drawCode,Map<String,String> map,Integer num){
		String [] rray=drawCode.split(",");
		StringBuffer buf=new StringBuffer();
		Integer len=rray.length-num;
		for(int i=0;i<len;i++){
			buf.append(rray[i]);
			if(i!=len-1){
				buf.append(",");
			}
		}
		return areaNumber(buf.toString(), map);
	}
	//开奖号码区间个数
	public static String areaNumber(String drawCode,Map<String,String> map){
		String [] rray=drawCode.split(",");
		Map<String,Integer> mmp=new LinkedHashMap<String, Integer>();
		for(Map.Entry<String, String> entry:map.entrySet()){
			String value=entry.getValue();//区间范围 1,4
			String []range=value.split(",");
			Integer start=Integer.parseInt(range[0]);
			Integer end=Integer.parseInt(range[1]);
			Integer count=0;
			for(int i=0;i<rray.length;i++){
				Integer num=Integer.parseInt(rray[i]);
				if(num>=start&&num<=end){
					count++;
				}
			}
			mmp.put(entry.getKey(), count);
		}
		return JSON.toJSONString(mmp);
	}
	
	//开奖号码篮球尾数
	public static String blueMantissa(String drawCode){
		Integer len=drawCode.length();
		String num=drawCode.substring(len-1, len);
		return num;
	}
	
	//组六
	public static Integer zuSix(String drawCode){
		String [] rray=drawCode.split(",");
		if(!rray[0].equals(rray[1])&&!rray[1].equals(rray[2])){
			return 1;
		}else{
			return 0;
		}
	}
	
	//组三
	public static Integer zuThird(String drawCode){
		String [] rray=drawCode.split(",");
		if(!rray[0].equals(rray[1])&&!rray[1].equals(rray[2])){
			return 0;
		}
		if(rray[0].equals(rray[1])&&rray[1].equals(rray[2])){
			return 0;
		}
		return 1;
	}
	
	//和尾
	public static char sumMantissa(Object sum){
		return sum.toString().charAt(sum.toString().length()-1);
	} 
	
	//和值奇偶
	public static String sumOddEven(Object sum){
		Integer num=Integer.parseInt(sum.toString());
		if(num%2==0){
			return "偶";
		}
		return "奇";
	} 
	//开奖号码和值
	public static Integer countSum(String drawCode){
		Integer sum=0;
		if(StringUtils.isNotEmpty(drawCode)){
			String rray[]=drawCode.split(",");
			for(int i=0;i<rray.length;i++){
				if(StringUtils.isNotEmpty(rray[i])){
					sum+=Integer.parseInt(rray[i]);
				}
			}
		}
		return sum;
	}
	
	//取开奖号码前N位
	public static String subStr(String drawCode,Integer num){
		String str="";
		if(StringUtils.isNotEmpty(drawCode)){
			String [] rray=drawCode.split(",");
			str=StringUtils.join(Arrays.copyOfRange(rray, 0, num), ",");
		}
		return str;
	}
	
	//开奖号码重号个数 上 期开奖号码和本期开奖号码重号个数
	public static Integer doubleSignCount(String preDrawCode,String drawCode){
		Integer count=0;
		if(StringUtils.isNotEmpty(preDrawCode)&&StringUtils.isNotEmpty(drawCode)){
			String rray2[]=drawCode.split(",");
			String rray[]=preDrawCode.split(",");
			for(int i=0;i<rray.length;i++){
				Integer a=Integer.parseInt(rray[i]);
				for(int j=0;j<rray2.length;j++){
					Integer b=Integer.parseInt(rray2[j]);
					if(a.equals(b)){
						count++;
						break;
					}
				}
			}
		}
		return count;
	}

}
