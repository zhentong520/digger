package com.ko30.constant.enums.quartz;


public class Constants {

	/**
	 * 快3开奖号码形态
	 * @author Administrator
	 *
	 */
	public enum FastThreeNumberType {
		
		THREE_DIFFERENT("1","三不同"),
		THREE_SAME("2","三同号"),
		THREE_CONTINUOUS("3","三连号"),
		TWO_SAME("4","二同号");
		
		private String value;
		private String  key;
		private FastThreeNumberType(String value,String  key){
			this.value=value;
			this.key=key;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
	}
	
	public enum LeaveOutType {
		
		FIRST_LEAVE_OUT(1,"开奖号码按位遗漏","firstArea",LeaveType.MANY_VALUE_ONE_LEAVE,1),   
		SECOND_LEAVE_OUT(2,"开奖号码遗漏","secondArea",LeaveType.MANY_VALUE_LEAVE,1),
		THIRD_LEAVE_OUT(3,"开奖号码和值遗漏","thirdArea",LeaveType.ONE_VALUE_LEAVE,2),   
		FOUR_LEAVE_OUT(4,"开奖号码和值奇偶遗漏","fourArea",LeaveType.ONE_VALUE_LEAVE,3),
		FIVE_LEAVE_OUT(5,"开奖号码和值大小遗漏","fiveArea",LeaveType.ONE_VALUE_LEAVE,5),  //快3
		SIX_LEAVE_OUT(6,"开奖号码按位大小遗漏","sixArea",LeaveType.MANY_VALUE_ONE_LEAVE,5),	//快乐十分
		SEVEN_LEAVE_OUT(7,"开奖号码类型遗漏","sevenArea",LeaveType.ONE_VALUE_LEAVE,4), //三同 三不同 三连号 二同号    //快3
		EIGHT_LEAVE_OUT(8,"开奖号码跨度遗漏","eightArea",LeaveType.ONE_VALUE_LEAVE,1),
		NINE_TYPE_LEAVE_OUT(9,"开奖号码按位奇偶遗漏","nineArea",LeaveType.MANY_VALUE_ONE_LEAVE,3);
		
		private Integer key;
		private String text;
		private String value; //放入map时的key值
		private LeaveType leaveType; 
		private Integer rang;  //SeriesType 类型的几个属性  1 digitRang;按位遗漏值范围  2sumRang  和值遗漏值范围  3oddEven;  奇偶范围  4leaveForm;快3形态范围  三不同 二不同   5bigSmall; 
		private LeaveOutType(Integer key,String text,String value,LeaveType leaveType,Integer rang){
			this.key=key;
			this.text=text;
			this.value=value;
			this.leaveType=leaveType;
			this.rang=rang;
		}
		public Integer getKey() {
			return key;
		}
		public void setKey(Integer key) {
			this.key = key;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public LeaveType getLeaveType() {
			return leaveType;
		}
		public void setLeaveType(LeaveType leaveType) {
			this.leaveType = leaveType;
		}
		public Integer getRang() {
			return rang;
		}
		public void setRang(Integer rang) {
			this.rang = rang;
		}
		public static LeaveOutType getByCode(String value) {
			LeaveOutType[] types = LeaveOutType.values();
			for (LeaveOutType type : types) {
				if (type.getValue().equals(value)) {
					return type;
				}
			}
			return null;
		}
	}
	/**
	 * 遗漏处理类型
	 */
	public enum LeaveType{
		ONE_VALUE_LEAVE(1,"一个值的遗漏"),
		MANY_VALUE_LEAVE(2,"多个值的遗漏"),
		MANY_VALUE_ONE_LEAVE(3,"多个值的按位遗漏");
		
		private Integer key;
		private String text;
		private LeaveType(Integer key,String text){
			this.key=key;
			this.text=text;
		}
		public Integer getKey() {
			return key;
		}
		public void setKey(Integer key) {
			this.key = key;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
	}
	
	/**
	 * 高频彩走势图数据  分类
	 * @author Administrator
	 *
	 */
	public enum SeriesType{
		GD_KLSF_CODE_SERIES("10005",1,"1-20","","奇-偶","","大-小"),
		CQ_KLSF_CODE_SERIES("10009",1,"1-20","","奇-偶","","大-小"),
		TJ_KLSF_CODE_SERIES("10034",1,"1-20","","奇-偶","","大-小"),
		
		
		GX_KLSF_CODE_SERIES("10038",3,"1-21","","奇-偶","","大-小"),
		
		
		JS_K3_CODE_SERIES("10007",2,"1-6","3-18","奇-偶","三不同-二同号-三同号-三连号","大-小"),
		GX_K3_CODE_SERIES("10026",2,"1-6","3-18","奇-偶","三不同-二同号-三同号-三连号","大-小"),
		JL_K3_CODE_SERIES("10027",2,"1-6","3-18","奇-偶","三不同-二同号-三同号-三连号","大-小"),
		HEB_K3_CODE_SERIES("10028",2,"1-6","3-18","奇-偶","三不同-二同号-三同号-三连号","大-小"),
		NM_K3_CODE_SERIES("10029",2,"1-6","3-18","奇-偶","三不同-二同号-三同号-三连号","大-小"),
		AH_K3_CODE_SERIES("10030",2,"1-6","3-18","奇-偶","三不同-二同号-三同号-三连号","大-小"),
		FJ_K3_CODE_SERIES("10031",2,"1-6","3-18","奇-偶","三不同-二同号-三同号-三连号","大-小"),
		HUB_K3_CODE_SERIES("10032",2,"1-6","3-18","奇-偶","三不同-二同号-三同号-三连号","大-小"),
		BJ_K3_CODE_SERIES("10033",2,"1-6","3-18","奇-偶","三不同-二同号-三同号-三连号","大-小");
		
		private String key;    //编码
		private Integer value; //自定义系列值
		private String digitRang;//按位遗漏值范围
		private String sumRang;  //和值遗漏值范围
		private String oddEven;  //奇偶范围
		private String leaveForm;//快3形态范围  三不同 二不同
		private String bigSmall; //大小范围
		private SeriesType(String key,Integer value,String digitRang,String sumRang,String oddEven,String leaveForm,String bigSmall){
			this.key=key;
			this.value=value;
			this.digitRang=digitRang;
			this.sumRang=sumRang;
			this.oddEven=oddEven;
			this.leaveForm=leaveForm;
			this.bigSmall=bigSmall;
		}
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Integer getValue() {
			return value;
		}
		public void setValue(Integer value) {
			this.value = value;
		}
		public String getDigitRang() {
			return digitRang;
		}
		public void setDigitRang(String digitRang) {
			this.digitRang = digitRang;
		}
		public String getSumRang() {
			return sumRang;
		}
		public void setSumRang(String sumRang) {
			this.sumRang = sumRang;
		}
		public String getOddEven() {
			return oddEven;
		}
		public void setOddEven(String oddEven) {
			this.oddEven = oddEven;
		}
		public String getLeaveForm() {
			return leaveForm;
		}
		public void setLeaveForm(String leaveForm) {
			this.leaveForm = leaveForm;
		}
		public String getBigSmall() {
			return bigSmall;
		}
		public void setBigSmall(String bigSmall) {
			this.bigSmall = bigSmall;
		}
		public static SeriesType getByCode(String key) {
			SeriesType[] types = SeriesType.values();
			for (SeriesType type : types) {
				if (type.getKey().equals(key)) {
					return type;
				}
			}
			return null;
		}
		
	}
	
	public enum LotCodeNumber{
		//快3
		GUANG_XI_KUAI_3(10026,"GX_K3",4),
		JI_LIN_KUAI_3(10027,"JL_K3",4),
		HE_BEI_KUAI_3(10028,"HEB_K3",4),
		NEI_MENG_KUAI_3(10029,"NMG_K3",4),
		AN_HUI_KUAI_3(10030,"AH_K3",4),
		FU_JIAN_KUAI_3(10031,"FJ_K3",4),
		HU_BEI_KUAI_3(10032,"HUB_K3",4),
		BEI_JING_KUAI_3(10033,"BJ_K3",4),
		JIANG_SHU_KUAI_3(10007,"JS_K3",4),
		
		//快乐十分
		TIAN_JING_KUAI_LE_10(10034,"TJ_H10",9),
		GUANG_DONG_KUAI_LE_10(10005,"GD_H10",9),
		CHONG_QING_KUAI_LE_10(10009,"CQ_HF",9),
		GUANG_XI_KUAI_LE_10(10038,"GX_H10",6),
		;
		
		private Integer number;
		private String  code;
		private Integer areaNum;  //用来区分数据的区域
		private LotCodeNumber(Integer number,String  code,Integer areaNum){
			this.number=number;
			this.code=code;
			this.areaNum=areaNum;
		}
		public Integer getNumber() {
			return number;
		}
		public void setNumber(Integer number) {
			this.number = number;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public Integer getAreaNum() {
			return areaNum;
		}
		public void setAreaNum(Integer areaNum) {
			this.areaNum = areaNum;
		}
		public static LotCodeNumber getByCode(Integer key) {
			LotCodeNumber[] types = LotCodeNumber.values();
			for (LotCodeNumber type : types) {
				if (type.getNumber().toString().equals(key.toString())) {
					return type;
				}
			}
			return null;
		}
	}
	
}
