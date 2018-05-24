package com.ko30.quartz.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;



public class HighFrequencyLotDvnHelper {
	
	public enum LotType{
		//11选5系列  每期从01-11开出5个号码作为中奖号码。
		JIANG_XI_11_5("江西11选5","JX_11C5",10015,1),
		JIANG_SU_11_5("江苏11选5","JS_11C5",10016,1),
		AN_HUI_11_5("安徽11选5","AH_11C5",10017,1),
		SHANG_HAI_11_5("上海11选5","SH_11C5",10018,1),
		LIAO_LIN_11_5("辽宁11选5","LN_11C5",10019,1),
		HU_BEI_11_5("湖北11选5","HB_11C5",10020,1),
		GUANG_XI_11_5("广西11选5","GX_11C5",10022,1),
		JI_LIN_11_5("吉林11选5","JL_11C5",10023,1),
		NEI_MENG_11_5("内蒙古11选5","NMG_11C5",10024,1),
		ZHE_JIANG_11_5("浙江11选5","ZJ_11C5",10025,1),
		GUANG_DONG_11_5("广东11选5","GD_11C5",10006,1),
		SHAN_DONG_11_5("山东11选5","TC_11C5",10008,1),
		
		//时时彩系列   每期开出一个5位数作为中奖号码，万，千，百，十，个位每位号码为0-9
		CHONG_QING_SSC("重庆时时彩","CQ_SSC",10002,2),
		TIAN_JING_SSC("天津时时彩","TJ_SSC",10003,2),
		XING_JIANG_SSC("新疆时时彩","XJ_SSC",10004,2),
		
		//快3系列   每期从01-06开出3个号码作为中奖号码
		GUANG_XI_KUAI_3("广西快3","GX_K3",10026,3),
		JI_LIN_KUAI_3("吉林快3","JL_K3",10027,3),
		HE_BEI_KUAI_3("河北快3","HEB_K3",10028,3),
		NEI_MENG_KUAI_3("内蒙古快3","NMG_K3",10029,3),
		AN_HUI_KUAI_3("安徽快3","AH_K3",10030,3),
		FU_JIAN_KUAI_3("福建快3","FJ_K3",10031,3),
		HU_BEI_KUAI_3("湖北快3","HUB_K3",10032,3),
		BEI_JING_KUAI3("北京快3","BJ_K3",10033,3),
		JIANG_SU_KUAI_3("江苏快3","JS_K3",10007,3),
		
		//快乐十分系列
		TIAN_JING_10_FEN("天津快乐十分","TJ_H10",10034,4),
		GUANG_DONG_10_FEN("广东快乐十分","GD_H10",10005,4),
		CHONG_QING_10_FEN("重庆快乐十分","CQ_HF",10009,4),
		GUANG_XI_10_FEN("广西快乐十分","GX_H10",10038,4);
		
		private String name;   //彩票名称
		private String enCode;//彩票简称
		private Integer numCode;//彩票编码
		private Integer series;//彩票系列
	
		private LotType(String name,String enCode,Integer numCode,int series){
			this.name=name;
			this.enCode=enCode;
			this.numCode=numCode;
			this.series=series;
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
		public Integer getNumCode() {
			return numCode;
		}
		public void setNumCode(Integer numCode) {
			this.numCode = numCode;
		}
		public Integer getSeries() {
			return series;
		}
		public void setSeries(Integer series) {
			this.series = series;
		}
		public static LotType getByCode(Integer numCode) {
			LotType[] types = LotType.values();
			for (LotType type : types) {
				if (type.getNumCode()==numCode.intValue()) {
					return type;
				}
			}
			return null;
		}
		public static LotType getByCode(String enCode) {
			LotType[] types = LotType.values();
			for (LotType type : types) {
				if (type.getEnCode().equals(enCode)) {
					return type;
				}
			}
			return null;
		}
	}
	
	public enum PlayType{
		//11选五玩法
		QIAN_1_11_5("FRONT1_PLAY","前一直选","所选号码与开奖号码的第一位相同",1,HandleType.BEFORE_POSITION_ZHI,1),
		QIAN_2_11_5("FRONT2_PLAY","前二直选","所选号码与开奖号码的前2位相同且顺序一致",2,HandleType.BEFORE_POSITION_ZHI,1),
		QIAN_2_WILL_11_5("FRONT2_GROUP_PLAY","前二组选","所选号码与开奖号码的前2位相同(顺序不限)",2,HandleType.BEFORE_POSITION_TONG,1),
		QIAN_3_11_5("FRONT3_PLAY","前三直选","所选号码与开奖号码的前3位相同且顺序一致",3,HandleType.BEFORE_POSITION_ZHI,1),
		QIAN_3_WILL_11_5("FRONT3_GROUP_PLAY","前三组选","所选号码与开奖号码的前3位相同(顺序不限)",3,HandleType.BEFORE_POSITION_TONG,1),
		WILL_2_11_5("CHO2_OPTIONAL_PLAY","任选二","所选号码与5个开奖号码中的任意2个相同",2,HandleType.ALL_SAME,1),
		WILL_3_11_5("CHO3_OPTIONAL_PLAY","任选三","所选号码与5个开奖号码中的任意3个相同",3,HandleType.ALL_SAME,1),
		WILL_4_11_5("CHO4_OPTIONAL_PLAY","任选四","所选号码与5个开奖号码中的任意4个相同",4,HandleType.ALL_SAME,1),
		WILL_5_11_5("CHO5_OPTIONAL_PLAY","任选五","所选号码与5个开奖号码中的任意5个相同",5,HandleType.ALL_SAME,1),
		WILL_6_11_5("CHO6_OPTIONAL_PLAY","任选六","所选号码与5个开奖号码中的任意5个相同",6,HandleType.ALL_SAME,1),
		WILL_7_11_5("CHO7_OPTIONAL_PLAY","任选七","所选号码与5个开奖号码中的任意5个相同",7,HandleType.ALL_SAME,1),
		WILL_8_11_5("CHO8_OPTIONAL_PLAY","任选八","所选号码与5个开奖号码中的任意5个相同",8,HandleType.ALL_SAME,1),
		
		//时时彩玩法
		SSC_5("STAR5_PLAY","五星直选","选5个号码，与开奖号码完全按位全部相符",5,HandleType.BEFORE_POSITION_ZHI,2),
		SSC_WILL_5("STAR5_ALL_PLAY","五星通选","5个号码与开奖号码全按位全部相符或与开奖号码前三位或后三位按位相符或与开奖号码前二位或后二位按位相符",5,HandleType.WU_XING_TONG_XUAN,2),
		SSC_3("STAR3_PLAY","三星直选","选3个号码，与开奖号码连续后三位按位相符",3,HandleType.AFTER_POSITION_ZHI,2),
		SSC_2("STAR2_PLAY","二星直选","选2个号码，与开奖号码连续后二位按位相符",2,HandleType.AFTER_POSITION_ZHI,2),
		SSC_WILL_2("STAR2_GROUP_PLAY","二星组选","选2个号码，与开奖号码连续 后二位相符",2,HandleType.AFTER_POSITION_TONG,2),
		SSC_1("STAR1_PLAY","一星直选","选1个号码，与开奖号码个位相符",1,HandleType.AFTER_POSITION_ZHI,2),
		SSC_BS_OE("DSSD_PLAY","大小单双","与开奖号码后两位数字属性按位相符",0,HandleType.BIG_SMALL_ODD_EVEN,2),//大号码为5-9；小号码为0-4；单数为：13579；双数为：02468
		
		//快3玩法
		KUAI_3_SUM("SUML_PLAY","和值","112",1,HandleType.ALL_SUM,2),
		KUAI_3_SAME3_SINGLE("SAME3_PLAY","三同号单选","111、222、333、444、555、666",3,HandleType.BEFORE_POSITION_ZHI,3),
		KUAI_3_UNSAME3("UNSAME3_PLAY","三不同号","123、124、125、126",3,HandleType.BEFORE_POSITION_ZHI,3),
		KUAI_3_UNSAME2("UNSAME2_PLAY","二不同号","12*、13*、14*、15*",2,HandleType.BEFORE_POSITION_ZHI,3),
		KUAI_3_SAME3_ALL("SAME3_ALL_PLAY","三同号通选","111、222、333、444、555、666",3,HandleType.SAN_TONG_HAO,3),
		KUAI_3_CONTINUOUS3_ALL("CONCAT3_PLAY","三连号通选","123、234、345、456",3,HandleType.SAN_LIAN_HAO,3),
		
		//快乐十分玩法
		KUAI_LE_10_FEN_1("CHO1_PLAY","直选一","所选一个号码与开奖号码中任意一个相同",1,HandleType.ALL_SAME,3),
		KUAI_LE_10_FEN_2("CHO2_PLAY","直选二","所选两个号码与开奖号码中任意两个相同",2,HandleType.ALL_SAME,3),
		KUAI_LE_10_FEN_3("CHO3_PLAY","直选三","所选三个号码与开奖号码中任意三个相同",3,HandleType.ALL_SAME,3),
		KUAI_LE_10_FEN_4("CHO4_PLAY","直选四","所选四个号码与开奖号码中任意四个相同",4,HandleType.ALL_SAME,3),
		KUAI_LE_10_FEN_5("CHO5_PLAY","直选五","所选五个号码与开奖号码全部相同",5,HandleType.ALL_SAME,3),
		
		//广西快乐十分多三个玩法
		KUAI_LE_10_FEN_TONG_3("CHO3_ALL_PLAY","通选三","所选三个号码与开奖号码中任意两个、三个相同",3,HandleType.TONG_XUAN,4),
		KUAI_LE_10_FEN_TONG_4("CHO4_ALL_PLAY","通选四","所选四个号码与开奖号码中任意两个、三个、四个相同",4,HandleType.TONG_XUAN,4),
		KUAI_LE_10_FEN_TONG_5("CHO5_ALL_PLAY","通选五","所选五个号码与开奖号码三个、四个或全部相同",5,HandleType.TONG_XUAN,4);
		
		private String code;
		private String play;
		private String remark;
		private Integer series;
		private Integer codeNum;
		private HandleType handleType;
		
		private PlayType(String code,String play,String remark,Integer codeNum,HandleType handleType,int series){
			this.code=code;
			this.play=play;
			this.remark=remark;
			this.codeNum=codeNum;
			this.series=series;
			this.handleType=handleType;
		}
		
		
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getPlay() {
			return play;
		}
		public void setPlay(String play) {
			this.play = play;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public Integer getSeries() {
			return series;
		}
		public void setSeries(Integer series) {
			this.series = series;
		}
		
		public int getCodeNum() {
			return codeNum;
		}

		public void setCodeNum(int codeNum) {
			this.codeNum = codeNum;
		}


		public HandleType getHandleType() {
			return handleType;
		}
		public void setHandleType(HandleType handleType) {
			this.handleType = handleType;
		}

		public static PlayType getByCode(String code) {
			PlayType[] types = PlayType.values();
			for (PlayType type : types) {
				if (type.getCode().equals(code)) {
					return type;
				}
			}
			return null;
		}
		
		public static List<PlayType> getBySeries(Integer  series) {
			PlayType[] types = PlayType.values();
			List<PlayType> list=new ArrayList<PlayType>();
			for (PlayType type : types) {
				if (type.getSeries()==series.intValue()) {
					list.add(type);
				}
			}
			return list;
		}
		
	}
	
	
	public enum HandleType{
		BEFORE_POSITION_ZHI{
			 public Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num){
				 Map<String,Object> map=new HashMap<String, Object>();
				 String []drawList=draw.split(",");
				 List<Integer> hitNum=new ArrayList<Integer>();
				 Integer hitCnt=0;
				 if(StringUtils.isNotEmpty(draw)){
					 for(int i=0;i<killNum.size();i++){
						 int temNum=Integer.parseInt(killNum.get(i));
						 for(int j=0;j<num;j++){
							 if(i==j){
								 int temCode=Integer.parseInt(drawList[j]);
								 if(temNum==temCode){
	//								 hitNum.add(temNum);
									 hitCnt++;
								 }
							 }
						 }
					 }
				 }else{
					 killNum=new ArrayList();
				 }	 
				 if(hitCnt>=num.intValue()){
					 map.put("hitCnt", -1);
				 }else{
					 map.put("hitCnt", 1);
				 }
				 map.put("killNum", killNum);
				 map.put("hitNum", hitNum);
				 map.put("drawCode", draw);
				 map.put("planCode", planCode);
			     return map;
			 }
		},
		
		AFTER_POSITION_ZHI{
			 public Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num){
				 Map<String,Object> map=new HashMap<String, Object>();
				 String []drawList=draw.split(",");
				 List<Integer> hitNum=new ArrayList<Integer>();
				 Integer hitCnt=0;
				 int len=drawList.length-num;
				 if(StringUtils.isNotEmpty(draw)){
					 for(int i=0;i<killNum.size();i++){
						 Integer temNum=Integer.parseInt(killNum.get(i));
						 for(int j=0;j<drawList.length;j++){
							 if(j>=len){
								 Integer temCode=Integer.parseInt(drawList[j]);
								 if(temNum==temCode){
	//								 hitNum.add(temNum);
									 hitCnt++;
								 }
							 }
						 }
					 }
				 }else{
					 killNum=new ArrayList();
				 }
				 if(hitCnt>=num.intValue()){
					 map.put("hitCnt", -1);
				 }else{
					 map.put("hitCnt", 1);
				 }
				 map.put("killNum", killNum);
				 map.put("hitNum", hitNum);
				 map.put("drawCode", draw);
				 map.put("planCode", planCode);
				 return map;
			 }
		},
		BEFORE_POSITION_TONG{
			 public Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num){
				 Map<String,Object> map=new HashMap<String, Object>();
				 String []drawList=draw.split(",");
				 List<Integer> hitNum=new ArrayList<Integer>();
				 Integer hitCnt=0;
				 if(StringUtils.isNotEmpty(draw)){
					 for(int i=0;i<killNum.size();i++){
						 Integer temNum=Integer.parseInt(killNum.get(i));
							 for(int j=0;j<drawList.length;j++){
								 if(j<num){
									 Integer temCode=Integer.parseInt(drawList[j]);
									 if(temNum==temCode){
		//								 hitNum.add(temNum);
										 hitCnt++;
										 break;
									 }
								 }
							 }
					 }
				 }else{
					 killNum=new ArrayList();
				 }	 
				 if(hitCnt>=num.intValue()){
					 map.put("hitCnt", -1);
				 }else{
					 map.put("hitCnt", 1);
				 }
				 map.put("hitCnt", hitCnt);
				 map.put("killNum", killNum);
				 map.put("hitNum", hitNum);
				 map.put("drawCode", draw);
				 map.put("planCode", planCode);
				 return map;
			 }
		},
		AFTER_POSITION_TONG{
			 public Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num){
				 Map<String,Object> map=new HashMap<String, Object>();
				 String []drawList=draw.split(",");
				 List<Integer> hitNum=new ArrayList<Integer>();
				 Integer hitCnt=0;
				 int len=drawList.length-num;
				 if(StringUtils.isNotEmpty(draw)){
					 for(int i=0;i<killNum.size();i++){
						 Integer temNum=Integer.parseInt(killNum.get(i));
							 for(int j=0;j<drawList.length;j++){
								 if(j>=len){
									 Integer temCode=Integer.parseInt(drawList[j]);
									 if(temNum==temCode){
		//								hitNum.add(temNum);
									    hitCnt++;
										break;
									 }
								 }
							 }
					 }
				 }else{
					 killNum=new ArrayList();
				 }	 
				 if(hitCnt>=num.intValue()){
					 map.put("hitCnt", -1);
				 }else{
					 map.put("hitCnt", 1);
				 }
				 map.put("killNum", killNum);
				 map.put("hitNum", hitNum);
				 map.put("drawCode", draw);
				 map.put("planCode", planCode);
				 return map;
			 }
		},
		ALL_SAME{
			 public Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num){
				 Map<String,Object> map=new HashMap<String, Object>();
				 String []drawList=draw.split(",");
				 List<Integer> hitNum=new ArrayList<Integer>();
				 Integer hitCnt=0;
				 if(StringUtils.isNotEmpty(draw)){
					 for(int i=0;i<killNum.size();i++){
						 Integer temNum=Integer.parseInt(killNum.get(i));
							 for(int j=0;j<drawList.length;j++){
								 Integer temCode=Integer.parseInt(drawList[j]);
								 if(temNum==temCode){
	//								 hitNum.add(temNum);
									 hitCnt++;
									 break;
								 }
							 }
					 }
				 }else{
					 killNum=new ArrayList();
				 }	 
				 if(hitCnt>=num.intValue()){
					 map.put("hitCnt", -1);
				 }else{
					 map.put("hitCnt", 1);
				 }
				 map.put("killNum", killNum);
				 map.put("hitNum", hitNum);
				 map.put("drawCode", draw);
				 map.put("planCode", planCode);
				 return map;
			 }
		},
		WU_XING_TONG_XUAN{
			public Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num){
				 Map<String,Object> map=new HashMap<String, Object>();
				 String []drawList=draw.split(",");
				 List<Integer> hitNum=new ArrayList<Integer>();
				 map.put("hitCnt", 1);
				 if(drawList.length==5&&killNum.size()==5){
					int draw_1=Integer.parseInt(drawList[0]);
					int draw_2=Integer.parseInt(drawList[1]);
					int draw_3=Integer.parseInt(drawList[2]);
					int draw_4=Integer.parseInt(drawList[3]);
					int draw_5=Integer.parseInt(drawList[4]);
					int killNum_1=Integer.parseInt(killNum.get(0));
					int killNum_2=Integer.parseInt(killNum.get(1));
					int killNum_3=Integer.parseInt(killNum.get(2));
					int killNum_4=Integer.parseInt(killNum.get(3));
					int killNum_5=Integer.parseInt(killNum.get(4));
					if(draw_1==killNum_1&&draw_2==killNum_2){
						map.put("hitCnt", -1);
					}
					if(draw_1==killNum_1&&draw_2==killNum_2&&draw_3==killNum_3){
						map.put("hitCnt", -1);
					}
					if(draw_4==killNum_4&&draw_5==killNum_5){
						map.put("hitCnt", -1);
					}
					if(draw_3==killNum_3&&draw_4==killNum_4&&draw_5==killNum_5){
						map.put("hitCnt", -1);
					}
					if(draw_1==killNum_1&&draw_2==killNum_2&&draw_3==killNum_3&&draw_4==killNum_4&&draw_5==killNum_5){
						map.put("hitCnt", -1);
					}
				 }
				 if(StringUtils.isEmpty(draw)){
					 killNum=new ArrayList();
				 }
				 map.put("killNum", killNum);
				 map.put("hitNum", hitNum);
				 map.put("drawCode", draw);
				 map.put("planCode", planCode);
				 return map;
			 }
		},
		BIG_SMALL_ODD_EVEN{
			public Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num){
				 Map<String,Object> map=new HashMap<String, Object>();
				 String []drawList=draw.split(",");
				 List<Integer> hitNum=new ArrayList<Integer>();
				 map.put("hitCnt", 1);
				 if(drawList.length==5&&killNum.size()==2){
					Integer draw_4=Integer.parseInt(drawList[3]);
					Integer draw_5=Integer.parseInt(drawList[4]);
					String killNum_4=killNum.get(0);
					String killNum_5=killNum.get(1);
					String shi=IsBigSmallorOddEven(draw_4);
					String ge=IsBigSmallorOddEven(draw_5);
					if(killNum_4.equals(shi)&&killNum_5.equals(ge)){
						map.put("hitCnt", -1);
					}
				 }
				 if(StringUtils.isEmpty(draw)){
					 killNum=new ArrayList();
				 }
				 map.put("killNum", killNum);
				 map.put("hitNum", hitNum);
				 map.put("drawCode", draw);
				 map.put("planCode", planCode);
				 return map;
			 }
		},
		ALL_SUM{
			public Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num){
				 Map<String,Object> map=new HashMap<String, Object>();
				 String []drawList=draw.split(",");
				 List<Integer> hitNum=new ArrayList<Integer>();
				 map.put("hitCnt", 1);
				 int sum=0;
				 if(drawList.length==3&&killNum.size()==1){
					 for(int i=0;i<drawList.length;i++){
						 sum=sum+Integer.parseInt(drawList[i]);
					 }
					if(sum==Integer.parseInt(killNum.get(0))){
						map.put("hitCnt", -1);
					}
				 }
				 if(StringUtils.isEmpty(draw)){
					 killNum=new ArrayList();
				 }
				 map.put("killNum", killNum);
				 map.put("hitNum", hitNum);
				 map.put("drawCode", draw);
				 map.put("planCode", planCode);
				 return map;
			 }
		},
		SAN_TONG_HAO{
			public Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num){
				Map<String,Object> map=new HashMap<String, Object>();
				 String []drawList=draw.split(",");
				 List<Integer> hitNum=new ArrayList<Integer>();
				 map.put("hitCnt", -1);
				 int sum=0;
				 if(StringUtils.isNotEmpty(draw)){
					 for(int i=0;i<drawList.length;i++){
						Integer a=Integer.parseInt(drawList[i]);
						 if(sum==0){
							 sum=a;
						 }else{
							 if(a!=sum){
								 map.put("hitCnt", 1);
							 }
						 }
					 }
				 }else{
					 killNum=new ArrayList();
				 }
				 map.put("killNum", killNum);
				 map.put("hitNum", hitNum);
				 map.put("drawCode", draw);
				 map.put("planCode", planCode);
				 return map;
			 }
		},
		SAN_LIAN_HAO{
			public Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num){
				Map<String,Object> map=new HashMap<String, Object>();
				 String []drawList=draw.split(",");
				 List<Integer> hitNum=new ArrayList<Integer>();
				 map.put("hitCnt", 1);
				 if(StringUtils.isNotEmpty(draw)){
					 int a=Integer.parseInt(drawList[0]);
					 int b=Integer.parseInt(drawList[1]);
					 int c=Integer.parseInt(drawList[2]);
					 if((a-b)==(b-c)){
						 map.put("hitCnt", -1);
					 }
				 }else{
					 killNum=new ArrayList();
				 }
				 map.put("killNum", killNum);
				 map.put("hitNum", hitNum);
				 map.put("drawCode", draw);
				 map.put("planCode", planCode);
				 return map;
			 }
		},
		TONG_XUAN{
			/**
			 * draw:开奖号码
			 * planCode:玩法类型
			 * killNum:预测号码
			 * num:玩法中的数字
			 */
			public Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num){
				 Map<String,Object> map=new HashMap<String, Object>();
				 String []drawList=draw.split(",");
				 List<Integer> hitNum=new ArrayList<Integer>();
				 map.put("hitCnt", 1);
				 int count=0;
				 if(StringUtils.isNotEmpty(draw)){
					 for(int i=0;i<killNum.size();i++){
						Integer kill=Integer.parseInt(killNum.get(i));
							for(int j=0;j<drawList.length;j++){
								Integer drawNum=Integer.parseInt(drawList[j]);
								if(kill==drawNum){
									count++;
									break;
								}
							}
					 }
				 }else{
					 killNum=new ArrayList();
				 }
				 if(num==3&&count>=2){
					 map.put("hitCnt", -1);
				 }
				 if(num==4&&count>=2){
					 map.put("hitCnt", -1);
				 }
				 if(num==5&&count>=3){
					 map.put("hitCnt", -1);
				 }
				 map.put("killNum", killNum);
				 map.put("hitNum", hitNum);
				 map.put("drawCode", draw);
				 map.put("planCode", planCode);
				 return map;
			 }
		}
		;
		public abstract Map<String,Object> getData(String draw,String planCode,List<String> killNum,Integer num);
		
	}
	
	public static String IsBigSmallorOddEven(Integer num){
		String val="";
		if(num>=5&&num<=9){//大
			if(num%2==0){
				val="大双";
			}else{
				val="大单";
			}
		}
		if(num>=0&&num<=4){//小
			if(num%2==0){
				val="小双";
			}else{
				val="小单";
			}
		}	
		return val;
	}
}
