package com.ko30.constant.enums.quartz;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 
* @ClassName: QuartzHandlerType 
* @Description: 定时任务枚举类型 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月18日 上午9:21:57 
*
 */
public enum QuartzHandlerType {
	
	
	/** 抓取全国彩历史 **/
	GRAB_QUAN_GUO_HISTORY("grab_quan_guo_history", "抓取全国彩历史",-1,2),
	/** 抓取热门彩历史 **/
	GRAB_RE_MEN_HISTORY("grab_re_men_history", "抓取热门彩历史",-1,4),
	/** 抓取高频彩历史 **/
	GRAB_GAO_PIN_HISTORY("grab_gao_pin_history", "抓取高频彩历史",-1,1),
	/** 抓取境外彩历史 **/
	GRAB_JING_WAI_HISTORY("GRAB_JING_WAI_HISTORY", "抓取境外彩历史",-1,3),
	
	//====================== 全国彩  =======================
	// 10039 福彩双色球,
	// 10040 超级大乐透,
	// 10041 福彩3D,
	// 10042 福彩七乐彩 ,
	// 10043 体彩排列3,
	// 10044 体彩排列5,
	// 10045 体彩七星彩

	/*** 双色球 **/
	SHUANG_SE_QIU("shuang_se_qiu", "双色球",10039,2),
	/*** 大乐透 **/
	DA_LE_TOU("da_le_tou", "大乐透",10040,2),
	/*** 福彩3D **/
	FU_CAI_3D("fu_cai_3D", "福彩3D",10041,2),
	/*** 七乐彩 **/
	QI_LE_CAI("qi_le_cai", "七乐彩",10042,2),
	/*** 排列3 **/
	PAI_LIE_3("pai_lie_3", "排列3",10043,2),
	/*** 排列5 **/
	PAI_LIE_5("pai_lie_5", "排列5",10044,2),
	/*** 七星彩 **/
	QI_XING_CAI("qi_xing_cai", "七星彩",10045,2), 
	
	// =================  热门彩 部分  ====================
	// 10001 北京PK拾
	// 10002 重庆时时彩
	// 10003 天津时时彩
	// 10004 新疆时时彩
	// 10005 广东快乐十分
	// 10006 广东11选5 //旧的分组4 调频彩 分组1
	// 10007 江苏快3 //旧的分组4 调频彩 分组1
	// 10008 十一运夺金
	// 10009 重庆幸运农场
	// 10036 极速时时彩
	// 10037 极速赛车
	// 10038 广西快乐十分
	// 10046 pc蛋蛋幸运28

	
	/*** 北京PK拾 **/
	BEI_JING_PK_SHI("bei_jing_PK_shi", "北京PK拾", 10001,4),
	/*** 重庆时时彩 **/
	CHONG_QING_SHI_SHI_CAI("chong_qing_shi_shi_cai", "重庆时时彩", 10002,4),
	/*** 天津时时彩 **/
	TIAN_JIN_SHI_SHI_CAI("tian_jin_shi_shi_cai", "天津时时彩", 10003,4),
	/*** 新疆时时彩 **/
	XIN_JIANG_SHI_SHI_CAI("xin_jiang_shi_shi_cai", "新疆时时彩", 10004,4),
	
	/*** 广东快乐十分 **/
	GUANG_DONG_KUAI_LE_10FEN("guang_dong_kuai_le_10fen", "广东快乐十分", 10005,4),
	/*** 十一运夺金 **/
	SHI_YI_YUN_DUO_JIN("shi_yi_yun_duo_jin", "十一运夺金", 10008,4),
	/*** 重庆幸运农场 **/
	CHONG_QING_XING_YUN_NONG_CHANG("chong_qing_xing_yun_nong_chang", "重庆幸运农场",10009,4),
	/*** 极速时时彩 **/
	JI_SU_SHI_SHI_CAI("ji_su_shi_shi_cai", "极速时时彩", 10036,4),
	/*** 极速赛车 **/
	JI_SU_SAI_CHE("ji_su_sai_che", "极速赛车", 10037,4),
	/*** 广西快乐十分 **/
	GUANG_XI_KUAI_LE_10FEN("guang_xi_kuai_le_10fen", "广西快乐十分", 10038,4),
	/*** pc蛋蛋幸运28 **/
	PC_DAN_DAN_XING_YUN_28("pc_dan_dan_xing_yun_28", "pc蛋蛋幸运28", 10046,4),
	
	
	// =================  高频彩  分组1 部分  ====================
	
	/*** 广东11选5  10006 **/
	GUANG_DONG_11_XUAN_5("guang_dong_11_xuan_5", "广东11选5", 10006,1),
	/*** 上海11选5 10018 **/
	SHANG_HAI_11_XUAN_5("shang_hai_11_xuan_5", "上海11选5", 10018,1),
	/*** 安徽11选5 10017 **/
	AN_HUI_11_XUAN_5("an_hui_11_xuan_5", "安徽11选5", 10017,1),
	/*** 江西11选5 10015 **/
	JIANG_XI_11_XUAN_5("jiang_xi_11_xuan_5", "江西11选5", 10015,1),
	/*** 吉林11选5  10023 **/
	JI_LIN_11_XUAN_5("ji_lin_11_xuan_5", "吉林11选5", 10023,1),
	/*** 广西11选5  10022 **/
	GUANG_XI_11_XUAN_5("guang_xi_11_xuan_5", "广西11选5", 10022,1),
	/*** 湖北11选5  10020 **/
	HU_BEI_11_XUAN_5("hu_bei_11_xuan_5", "湖北11选5", 10020,1),
	/*** 辽宁11选5  10019 **/
	LIAO_NING_11_XUAN_5("liao_ning_11_xuan_5", "辽宁11选5", 10019,1),
	/*** 江苏11选5  10016 **/
	JIANG_SU_11_XUAN_5("jiang_su_11_xuan_5", "江苏11选5", 10016,1),
	/*** 浙江11选5  10025 **/
	ZHE_JIANG_11_XUAN_5("zhe_jiang_11_xuan_5", "浙江11选5", 10025,1),
	/*** 内蒙古11选5  10024 **/
	NEI_MENG_GU_11_XUAN_5("nei_meng_gu_11_xuan_5", "内蒙古11选5", 10024,1),
	/*** 江苏快3  10007 **/
	JIANG_SU_KUAI_3("jiang_su_kuai_3", "江苏快3", 10007,1),
	/*** 广西快3  10026 **/
	GUANG_XI_KUAI_3("guang_xi_kuai_3", "广西快3", 10026,1),
	/*** 吉林快3  10027 **/
	JI_LIN_KUAI_3("ji_lin_kuai_3", "吉林快3", 10027,1),
	/*** 河北快3  10028 **/
	HE_BEI_KUAI_3("he_bei_kuai_3", "河北快3", 10028,1),
	/*** 安徽快3  10030 **/
	AN_HUI_KUAI_3("an_hui_kuai_3", "安徽快3", 10030,1),

	/*** 内蒙古快3  10029 **/
	NEI_MENG_GU_KUAI_3("nei_meng_gu_kuai_3", "内蒙古快3", 10029,1),
	/*** 福建快3  10031 **/
	FU_JIAN_KUAI_3("fu_jian_kuai_3", "福建快3", 10031,1),
	/*** 湖北快3  10032 **/
	HU_BEI_KUAI_3("hu_bei_kuai_3", "湖北快3", 10032,1),
	/*** 北京快3  10033 **/
	BEI_JING_KUAI_3("bei_jing_kuai_3", "北京快3", 10033,1),
	
	/*** 北京快乐8  10014 **/
	BEI_JING_KUAI_LE_8("bei_jing_kuai_le_8", "北京快乐8", 10014,1),
	
	/*** 天津快乐10分  10034 **/
	TIAN_JIN_KUAI_LE_10FEN("tian_jin_kuai_le_10fen", "天津快乐10分", 10034,1),
	
	
	
	// =================  境外彩  分组3   部分  ====================
	/*** 澳洲幸运5 **/
	AO_ZHOU_XING_YUN_5("ao_zhou_xing_yun_5", "澳洲幸运5", 10010,3),
	
	/*** 澳洲幸运8 **/
	AO_ZHOU_XING_YUN_8("ao_zhou_xing_yun_8", "澳洲幸运8", 10011,3),
	
	/*** 澳洲幸运10 **/
	AO_ZHOU_XING_YUN_10("ao_zhou_xing_yun_10", "澳洲幸运10", 10012,3),
	
	/*** 澳洲幸运20 **/
	AO_ZHOU_XING_YUN_20("ao_zhou_xing_yun_20", "澳洲幸运20", 10013,3),
	
	/*** 台湾宾果 **/
	TAI_WANG_BIN_GUO("tai_wang_bin_guo", "台湾宾果", 10047,3),
	;
	
	/** 彩票类型  **/
	private String type;
	
	/** 彩票中文名称   **/
	private String name;
	
	/** 彩票类型码  **/
	private Integer code;
	
	/** 分组码  **/
	private Integer groupCode;
	
	private QuartzHandlerType(String type, String name, Integer code,Integer groupCode) {
		this.type = type;
		this.name = name;
		this.code = code;
		this.groupCode = groupCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	
	public Integer getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(Integer groupCode) {
		this.groupCode = groupCode;
	}

	/**
	 * 
	* @Title: getByCode 
	* @Description: 根据code 码 取得 对应的枚举类 
	* @param @param code
	* @param @return    设定文件 
	* @return QuartzHandlerType    返回类型 
	* @throws
	 */
	public static QuartzHandlerType getByCode(Integer code) {
		QuartzHandlerType[] types = QuartzHandlerType.values();
		for (QuartzHandlerType type : types) {
			if (type.getCode() == code.intValue()) {
				return type;
			}
		}
		return null;
	}

	/**
	 * 
	* @Title: getByGroupCode 
	* @Description: 按分组码，获取所有的彩票类型枚举
	* @param @param groupCode
	* @param @return    设定文件 
	* @return List<QuartzHandlerType>    返回类型 
	* @throws
	 */
	public static List<QuartzHandlerType> getByGroupCode(Integer groupCode) {
		QuartzHandlerType[] types = QuartzHandlerType.values();
		List<QuartzHandlerType> list = Lists.newArrayList();
		for (QuartzHandlerType type : types) {
			if (type.getGroupCode() == groupCode) {
				list.add(type);
			}
		}
		return list;
	}
	
	/**
	 * 
	* @Title: getByLotCode 
	* @Description: 根据名称 
	* @param @param groupCode
	* @param @return    设定文件 
	* @return QuartzHandlerType    返回类型 
	* @throws
	 */
	public static QuartzHandlerType getByLotCode(Integer lotCode) {
		QuartzHandlerType[] types = QuartzHandlerType.values();
		List<QuartzHandlerType> list = Lists.newArrayList();
		for (QuartzHandlerType type : types) {
			if (type.getCode() == lotCode) {
				return type;
			}
		}
		return null;
	}
	
}
