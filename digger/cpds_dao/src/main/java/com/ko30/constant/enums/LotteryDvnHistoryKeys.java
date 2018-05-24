package com.ko30.constant.enums;

/**
 * 彩票预测历史数据redis中保存的key名
 * 
 * @author carr
 *
 */
public enum LotteryDvnHistoryKeys {
	
	// ------------------- 福彩双色球 -------------------------
	/** 福彩双色球 ***/
	CN_F2B_LUCK("CN_F2B_LUCK", "福彩双色球幸运号"),
	

	/** 福彩双色球 蓝球热号历史 ***/
	CN_F2B_POS2_HOT_HISTORY("CN_F2B_POS2_HOT_HISTORY", "福彩双色球蓝球热号历史"),
	/** 福彩双色球 红球热号历史 ***/
	CN_F2B_POS1_HOT_HISTORY("CN_F2B_POS1_HOT_HISTORY", "福彩双色球红球热号历史"),
	
	/** 福彩双色球 蓝球杀3码 ***/
	CN_F2B_POS1_KILL03_HISTORY("CN_F2B_POS1_KILL03_HISTORY", "福彩双色球红球杀3码"),
	/** 福彩双色球 蓝球杀6码 ***/
	CN_F2B_POS1_KILL06_HISTORY("CN_F2B_POS1_KILL06_HISTORY", "福彩双色球红球杀6码"),
	/** 福彩双色球 蓝球杀10码 ***/
	CN_F2B_POS1_KILL10_HISTORY("CN_F2B_POS1_KILL10_HISTORY", "福彩双色球红球杀10码"),
	
	//----- 最新杀码 ------
	/** 福彩双色球 蓝球杀3码 ***/
	CN_F2B_POS1_KILL03("CN_F2B_POS1_KILL03", "福彩双色球红球杀3码"),
	/** 福彩双色球 蓝球杀6码 ***/
	CN_F2B_POS1_KILL06("CN_F2B_POS1_KILL06", "福彩双色球红球杀6码"),
	/** 福彩双色球 蓝球杀10码 ***/
	CN_F2B_POS1_KILL10("CN_F2B_POS1_KILL10", "福彩双色球红球杀10码"),

	/** 福彩双色球 红球杀5码 ***/
	CN_F2B_POS2_KILL05_HISTORY("CN_F2B_POS2_KILL05_HISTORY", "福彩双色球蓝球杀5码"),
	CN_F2B_POS2_KILL05("CN_F2B_POS2_KILL05", "福彩双色球蓝球杀5码"),
	
	/** 福彩双色球 历史幸运号码 ***/
	CN_F2B_LUCK_HISTORY("CN_F2B_LUCK_HISTORY", "福彩双色球历史幸运号码"),
	
	
	
	// ------------------- 福彩七乐彩 -------------------------
	/** 福彩七乐彩 幸运号码 ***/
	CN_7LC_LUCK("CN_7LC_LUCK", "福彩七乐彩幸运号"),
	

	/** 福彩七乐彩 蓝球热号历史 ***/
	CN_7LC_POS2_HOT_HISTORY("CN_7LC_POS2_HOT_HISTORY", "福彩七乐彩蓝球热号历史"),
	/** 福彩七乐彩 红球热号历史 ***/
	CN_7LC_POS1_HOT_HISTORY("CN_7LC_POS1_HOT_HISTORY", "福彩七乐彩红球热号历史"),
	
	/** 福彩七乐彩 红球杀3码 ***/
	CN_7LC_POS1_KILL03_HISTORY("CN_7LC_POS1_KILL03_HISTORY", "福彩七乐彩红球杀3码"),
	/** 福彩七乐彩 红球杀6码 ***/
	CN_7LC_POS1_KILL06_HISTORY("CN_7LC_POS1_KILL06_HISTORY", "福彩七乐彩红球杀6码"),
	/** 福彩七乐彩 红球杀10码 ***/
	CN_7LC_POS1_KILL10_HISTORY("CN_7LC_POS1_KILL10_HISTORY", "福彩七乐彩红球杀10码"),

	/** 福彩七乐彩蓝球杀5码 ***/
	CN_7LC_POS2_KILL05_HISTORY("CN_7LC_POS2_KILL05_HISTORY", "福彩七乐彩蓝球杀5码"),
	
	//----- 最新杀码 -----
	/** 福彩七乐彩 蓝球杀3码 ***/
	CN_7LC_POS1_KILL03("CN_7LC_POS1_KILL03", "福彩七乐彩红球杀3码"),
	/** 福彩七乐彩 蓝球杀6码 ***/
	CN_7LC_POS1_KILL06("CN_7LC_POS1_KILL06", "福彩七乐彩红球杀6码"),
	/** 福彩七乐彩 蓝球杀10码 ***/
	CN_7LC_POS1_KILL10("CN_7LC_POS1_KILL10", "福彩七乐彩红球杀10码"),

	/** 福彩七乐彩 红球杀5码 ***/
	CN_7LC_POS2_KILL05("CN_7LC_POS2_KILL05", "福彩七乐彩蓝球杀5码"),	
	
	
	/** 福彩七乐彩 历史幸运号码 ***/
	CN_7LC_LUCK_HISTORY("CN_7LC_LUCK_HISTORY", "福彩七乐彩历史幸运号码"),
	
	
	// ------------------- 大乐透 -------------------------
	/** 大乐透 ***/
	SU_DLT_LUCK("SU_DLT_LUCK", "大乐透幸运号"),


	/** 大乐透 蓝球热号历史 ***/
	SU_DLT_POS2_HOT_HISTORY("SU_DLT_POS2_HOT_HISTORY", "大乐透蓝球热号历史"),
	/** 大乐透 红球热号历史 ***/
	SU_DLT_POS1_HOT_HISTORY("SU_DLT_POS1_HOT_HISTORY", "大乐透红球热号历史"),

	/** 大乐透 蓝球杀3码 ***/
	SU_DLT_POS1_KILL03_HISTORY("SU_DLT_POS1_KILL03_HISTORY", "大乐透红球杀3码"),
	/** 大乐透 蓝球杀6码 ***/
	SU_DLT_POS1_KILL06_HISTORY("SU_DLT_POS1_KILL06_HISTORY", "大乐透红球杀6码"),
	/** 大乐透 蓝球杀10码 ***/
	SU_DLT_POS1_KILL10_HISTORY("SU_DLT_POS1_KILL10_HISTORY", "大乐透红球杀10码"),

	/** 大乐透 红球杀5码 ***/
	SU_DLT_POS2_KILL05_HISTORY("SU_DLT_POS2_KILL05_HISTORY", "大乐透蓝球杀5码"),
	
	//----- 最新杀码 ------
	/** 大乐透 蓝球杀3码 ***/
	SU_DLT_POS1_KILL03("SU_DLT_POS1_KILL03", "大乐透红球杀3码"),
	/** 大乐透 蓝球杀6码 ***/
	SU_DLT_POS1_KILL06("SU_DLT_POS1_KILL06", "大乐透红球杀6码"),
	/** 大乐透 蓝球杀10码 ***/
	SU_DLT_POS1_KILL10("SU_DLT_POS1_KILL10", "大乐透红球杀10码"),

	/** 大乐透 红球杀5码 ***/
	SU_DLT_POS2_KILL05("SU_DLT_POS2_KILL05", "大乐透蓝球杀5码"),

	/** 大乐透 历史幸运号码 ***/
	SU_DLT_LUCK_HISTORY("SU_DLT_LUCK_HISTORY", "大乐透历史幸运号码"),
	
	
	// ------------------- 福彩3D -------------------------
	/** 福彩3D 幸运号 ***/
	CN_F3D_LUCK("CN_F3D_LUCK", "福彩3D幸运号"),

	
	/** 福彩3D 百位热号历史 ***/
	CN_F3D_POS3_HOT_HISTORY("CN_F3D_POS3_HOT_HISTORY", "福彩3D百位热号历史"),
	/** 福彩3D 十位热号历史 ***/
	CN_F3D_POS2_HOT_HISTORY("CN_F3D_POS2_HOT_HISTORY", "福彩3D十位热号历史"),
	/** 福彩3D 个位热号历史 ***/
	CN_F3D_POS1_HOT_HISTORY("CN_F3D_POS1_HOT_HISTORY", "福彩3D个位热号历史"),
	
	/** 福彩3D 百位杀2码 ***/
	CN_F3D_POS3_KILL02_HISTORY("CN_F3D_POS3_KILL02_HISTORY", "福彩3D百位杀2码"),
	/** 福彩3D 十位杀2码 ***/
	CN_F3D_POS2_KILL02_HISTORY("CN_F3D_POS2_KILL02_HISTORY", "福彩3D十位杀2码"),
	/** 福彩3D 个位杀2码 ***/
	CN_F3D_POS1_KILL02_HISTORY("CN_F3D_POS1_KILL02_HISTORY", "福彩3D个位杀2码"),
	
	//----- 最新杀码 -------- 
	/** 福彩3D 百位杀2码 ***/
	CN_F3D_POS3_KILL02("CN_F3D_POS3_KILL02", "福彩3D百位杀2码"),
	/** 福彩3D 十位杀2码 ***/
	CN_F3D_POS2_KILL02("CN_F3D_POS2_KILL02", "福彩3D十位杀2码"),
	/** 福彩3D 个位杀2码 ***/
	CN_F3D_POS1_KILL02("CN_F3D_POS1_KILL02", "福彩3D个位杀2码"),
	
	
	/** 福彩3D 历史幸运号码 ***/
	CN_F3D_LUCK_HISTORY("CN_F3D_LUCK_HISTORY", "福彩3D历史幸运号码"),
	
	
	// ------------------- 排列3 -------------------------

	/** 体彩排列3 幸运号 ***/
	CN_PL3_LUCK("CN_PL3_LUCK", "体彩排列3幸运号"),
	
	
	/** 体彩排列3 百位热号 ***/
	CN_PL3_POS3_HOT_HISTORY("CN_PL3_POS3_HOT_HISTORY", "体彩排列3百位热号"),
	/** 体彩排列3 十位热号 ***/
	CN_PL3_POS2_HOT_HISTORY("CN_PL3_POS2_HOT_HISTORY", "体彩排列3十位热号"),
	/** 体彩排列3 个位热号 ***/
	CN_PL3_POS1_HOT_HISTORY("CN_PL3_POS1_HOT_HISTORY", "体彩排列3个位热号"),
	
	/** 体彩排列3 百位杀2码 ***/
	CN_PL3_POS3_KILL02_HISTORY("CN_PL3_POS3_KILL02_HISTORY", "体彩排列3百位杀2码"),
	/** 体彩排列3 十位杀2码 ***/
	CN_PL3_POS2_KILL02_HISTORY("CN_PL3_POS2_KILL02_HISTORY", "体彩排列3十位杀2码"),
	/** 体彩排列3 个位杀2码 ***/
	CN_PL3_POS1_KILL02_HISTORY("CN_PL3_POS1_KILL02_HISTORY", "体彩排列3个位杀2码"),
	
	//----- 最新杀码  ------- 
	/** 体彩排列3 百位杀2码 ***/
	CN_PL3_POS3_KILL02("CN_PL3_POS3_KILL02", "体彩排列3百位杀2码"),
	/** 体彩排列3 十位杀2码 ***/
	CN_PL3_POS2_KILL02("CN_PL3_POS2_KILL02", "体彩排列3十位杀2码"),
	/** 体彩排列3 个位杀2码 ***/
	CN_PL3_POS1_KILL02("CN_PL3_POS1_KILL02", "体彩排列3个位杀2码"),
	
	/** 体彩排列3 历史幸运号码 ***/
	CN_PL3_LUCK_HISTORY("CN_PL3_LUCK_HISTORY", "体彩排列3历史幸运号码"),
	
	
	// ------------------- 排列5 -------------------------

	/** 体彩排列5 幸运号 ***/
	CN_PL5_LUCK("CN_PL5_LUCK", "体彩排列5幸运号"),
	
	/** 体彩排列5 万位热号 ***/
	CN_PL5_POS5_HOT_HISTORY("CN_PL5_POS5_HOT_HISTORY", "体彩排列5万位热号"),
	/** 体彩排列5 千位热号 ***/
	CN_PL5_POS4_HOT_HISTORY("CN_PL5_POS4_HOT_HISTORY", "体彩排列5千位热号"),
	/** 体彩排列5 百位热号 ***/
	CN_PL5_POS3_HOT_HISTORY("CN_PL5_POS3_HOT_HISTORY", "体彩排列5百位热号"),
	/** 体彩排列5 十位热号 ***/
	CN_PL5_POS2_HOT_HISTORY("CN_PL5_POS2_HOT_HISTORY", "体彩排列5十位热号"),
	/** 体彩排列5 个位热号 ***/
	CN_PL5_POS1_HOT_HISTORY("CN_PL5_POS1_HOT_HISTORY", "体彩排列5个位热号"),
	
	/** 体彩排列5 万位杀2码 ***/
	CN_PL5_POS5_KILL02_HISTORY("CN_PL5_POS5_KILL02_HISTORY", "体彩排列5万位杀2码"),
	/** 体彩排列5 千位杀2码 ***/
	CN_PL5_POS4_KILL02_HISTORY("CN_PL5_POS4_KILL02_HISTORY", "体彩排列5千位杀2码"),
	/** 体彩排列5 百位杀2码 ***/
	CN_PL5_POS3_KILL02_HISTORY("CN_PL5_POS3_KILL02_HISTORY", "体彩排列5百位杀2码"),
	/** 体彩排列5 十位杀2码 ***/
	CN_PL5_POS2_KILL02_HISTORY("CN_PL5_POS2_KILL02_HISTORY", "体彩排列5十位杀2码"),
	/** 体彩排列5 个位杀2码 ***/
	CN_PL5_POS1_KILL02_HISTORY("CN_PL5_POS1_KILL02_HISTORY", "体彩排列5个位杀2码"),
	
	//----- 最新杀码  ------- 
	/** 体彩排列5 万位杀2码 ***/
	CN_PL5_POS5_KILL02("CN_PL5_POS5_KILL02", "体彩排列5万位杀2码"),
	/** 体彩排列5 千位杀2码 ***/
	CN_PL5_POS4_KILL02("CN_PL5_POS4_KILL02", "体彩排列5千位杀2码"),
	/** 体彩排列5 百位杀2码 ***/
	CN_PL5_POS3_KILL02("CN_PL5_POS3_KILL02", "体彩排列5百位杀2码"),
	/** 体彩排列5 十位杀2码 ***/
	CN_PL5_POS2_KILL02("CN_PL5_POS2_KILL02", "体彩排列5十位杀2码"),
	/** 体彩排列5 个位杀2码 ***/
	CN_PL5_POS1_KILL02("CN_PL5_POS1_KILL02", "体彩排列5个位杀2码"),
	
	/** 体彩排列5 历史幸运号码 ***/
	CN_PL5_LUCK_HISTORY("CN_PL5_LUCK_HISTORY", "体彩排列5历史幸运号码"),
	
	
	// ------------------- 重庆时时彩 -------------------------

	/** 重庆时时彩 幸运号 ***/
	CQ_SSC_LUCK("CQ_SSC_LUCK", "重庆时时彩幸运号"),
	
	/** 重庆时时彩 万位热号 ***/
	CQ_SSC_POS5_HOT_HISTORY("CQ_SSC_POS5_HOT_HISTORY", "重庆时时彩万位热号"),
	/** 重庆时时彩 千位热号 ***/
	CQ_SSC_POS4_HOT_HISTORY("CQ_SSC_POS4_HOT_HISTORY", "重庆时时彩千位热号"),
	/** 重庆时时彩 百位热号 ***/
	CQ_SSC_POS3_HOT_HISTORY("CQ_SSC_POS3_HOT_HISTORY", "重庆时时彩百位热号"),
	/** 重庆时时彩 十位热号 ***/
	CQ_SSC_POS2_HOT_HISTORY("CQ_SSC_POS2_HOT_HISTORY", "重庆时时彩十位热号"),
	/** 重庆时时彩 个位热号 ***/
	CQ_SSC_POS1_HOT_HISTORY("CQ_SSC_POS1_HOT_HISTORY", "重庆时时彩个位热号"),
	
	/** 重庆时时彩 万位杀2码 ***/
	CQ_SSC_POS5_KILL02_HISTORY("CQ_SSC_POS5_KILL02_HISTORY", "重庆时时彩万位杀2码"),
	/** 重庆时时彩 千位杀2码 ***/
	CQ_SSC_POS4_KILL02_HISTORY("CQ_SSC_POS4_KILL02_HISTORY", "重庆时时彩千位杀2码"),
	/** 重庆时时彩 百位杀2码 ***/
	CQ_SSC_POS3_KILL02_HISTORY("CQ_SSC_POS3_KILL02_HISTORY", "重庆时时彩百位杀2码"),
	/** 重庆时时彩 十位杀2码 ***/
	CQ_SSC_POS2_KILL02_HISTORY("CQ_SSC_POS2_KILL02_HISTORY", "重庆时时彩十位杀2码"),
	/** 重庆时时彩 个位杀2码 ***/
	CQ_SSC_POS1_KILL02_HISTORY("CQ_SSC_POS1_KILL02_HISTORY", "重庆时时彩个位杀2码"),
	
	//----- 最新杀码  ------- 
	/** 重庆时时彩 万位杀2码 ***/
	CQ_SSC_POS5_KILL02("CQ_SSC_POS5_KILL02", "重庆时时彩万位杀2码"),
	/** 重庆时时彩 千位杀2码 ***/
	CQ_SSC_POS4_KILL02("CQ_SSC_POS4_KILL02", "重庆时时彩千位杀2码"),
	/** 重庆时时彩 百位杀2码 ***/
	CQ_SSC_POS3_KILL02("CQ_SSC_POS3_KILL02", "重庆时时彩百位杀2码"),
	/** 重庆时时彩 十位杀2码 ***/
	CQ_SSC_POS2_KILL02("CQ_SSC_POS2_KILL02", "重庆时时彩十位杀2码"),
	/** 重庆时时彩 个位杀2码 ***/
	CQ_SSC_POS1_KILL02("CQ_SSC_POS1_KILL02", "重庆时时彩个位杀2码"),
	
	/** 重庆时时彩 历史幸运号码 ***/
	CQ_SSC_LUCK_HISTORY("CQ_SSC_LUCK_HISTORY", "重庆时时彩历史幸运号码"),
	
	
	// ------------------- 天津时时彩 -------------------------

	/** 天津时时彩 幸运号 ***/
	TJ_SSC_LUCK("TJ_SSC_LUCK", "天津时时彩幸运号"),
	
	/** 天津时时彩 万位热号 ***/
	TJ_SSC_POS5_HOT_HISTORY("TJ_SSC_POS5_HOT_HISTORY", "天津时时彩万位热号"),
	/** 天津时时彩 千位热号 ***/
	TJ_SSC_POS4_HOT_HISTORY("TJ_SSC_POS4_HOT_HISTORY", "天津时时彩千位热号"),
	/** 天津时时彩 百位热号 ***/
	TJ_SSC_POS3_HOT_HISTORY("TJ_SSC_POS3_HOT_HISTORY", "天津时时彩百位热号"),
	/** 天津时时彩 十位热号 ***/
	TJ_SSC_POS2_HOT_HISTORY("TJ_SSC_POS2_HOT_HISTORY", "天津时时彩十位热号"),
	/** 天津时时彩 个位热号 ***/
	TJ_SSC_POS1_HOT_HISTORY("TJ_SSC_POS1_HOT_HISTORY", "天津时时彩个位热号"),
	
	/** 天津时时彩 万位杀2码 ***/
	TJ_SSC_POS5_KILL02_HISTORY("TJ_SSC_POS5_KILL02_HISTORY", "天津时时彩万位杀2码"),
	/** 天津时时彩 千位杀2码 ***/
	TJ_SSC_POS4_KILL02_HISTORY("TJ_SSC_POS4_KILL02_HISTORY", "天津时时彩千位杀2码"),
	/** 天津时时彩 百位杀2码 ***/
	TJ_SSC_POS3_KILL02_HISTORY("TJ_SSC_POS3_KILL02_HISTORY", "天津时时彩百位杀2码"),
	/** 天津时时彩 十位杀2码 ***/
	TJ_SSC_POS2_KILL02_HISTORY("TJ_SSC_POS2_KILL02_HISTORY", "天津时时彩十位杀2码"),
	/** 天津时时彩 个位杀2码 ***/
	TJ_SSC_POS1_KILL02_HISTORY("TJ_SSC_POS1_KILL02_HISTORY", "天津时时彩个位杀2码"),
	
	//----- 最新杀码  ------- 
	/** 天津时时彩 万位杀2码 ***/
	TJ_SSC_POS5_KILL02("TJ_SSC_POS5_KILL02", "天津时时彩万位杀2码"),
	/** 天津时时彩 千位杀2码 ***/
	TJ_SSC_POS4_KILL02("TJ_SSC_POS4_KILL02", "天津时时彩千位杀2码"),
	/** 天津时时彩 百位杀2码 ***/
	TJ_SSC_POS3_KILL02("TJ_SSC_POS3_KILL02", "天津时时彩百位杀2码"),
	/** 天津时时彩 十位杀2码 ***/
	TJ_SSC_POS2_KILL02("TJ_SSC_POS2_KILL02", "天津时时彩十位杀2码"),
	/** 天津时时彩 个位杀2码 ***/
	TJ_SSC_POS1_KILL02("TJ_SSC_POS1_KILL02", "天津时时彩个位杀2码"),
	
	/** 天津时时彩 历史幸运号码 ***/
	TJ_SSC_LUCK_HISTORY("TJ_SSC_LUCK_HISTORY", "天津时时彩历史幸运号码"),
	
	// ------------------- 体彩七星彩 -------------------------
	

	/** 幸运号 ***/
	TC_7XC_LUCK("TC_7XC_LUCK", "七星彩彩幸运号"),
	/** 杀码历史 ***/
	TC_7XC_POS1_KILL02_HISTORY("TC_7XC_POS1_KILL02_HISTORY","七星彩一号杀二码"),
	TC_7XC_POS2_KILL02_HISTORY("TC_7XC_POS2_KILL02_HISTORY","七星彩二号杀二码"),
	TC_7XC_POS3_KILL02_HISTORY("TC_7XC_POS3_KILL02_HISTORY","七星彩三号杀二码"),
	TC_7XC_POS4_KILL02_HISTORY("TC_7XC_POS4_KILL02_HISTORY","七星彩四号杀二码"),
	TC_7XC_POS5_KILL02_HISTORY("TC_7XC_POS5_KILL02_HISTORY","七星彩五号杀二码"),
	TC_7XC_POS6_KILL02_HISTORY("TC_7XC_POS6_KILL02_HISTORY","七星彩六号杀二码"),
	TC_7XC_POS7_KILL02_HISTORY("TC_7XC_POS7_KILL02_HISTORY","七星彩七号杀二码"),
	
	// ----- 杀码 ----
	TC_7XC_POS1_KILL02("TC_7XC_POS1_KILL02","七星彩一号杀二码"),
	TC_7XC_POS2_KILL02("TC_7XC_POS2_KILL02","七星彩二号杀二码"),
	TC_7XC_POS3_KILL02("TC_7XC_POS3_KILL02","七星彩三号杀二码"),
	TC_7XC_POS4_KILL02("TC_7XC_POS4_KILL02","七星彩四号杀二码"),
	TC_7XC_POS5_KILL02("TC_7XC_POS5_KILL02","七星彩五号杀二码"),
	TC_7XC_POS6_KILL02("TC_7XC_POS6_KILL02","七星彩六号杀二码"),
	TC_7XC_POS7_KILL02("TC_7XC_POS7_KILL02","七星彩七号杀二码"),
	
	;

	private String value;

	private String desc;

	private LotteryDvnHistoryKeys(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static LotteryDvnHistoryKeys getByValue(String value) {
		LotteryDvnHistoryKeys[] types = LotteryDvnHistoryKeys.values();
		for (LotteryDvnHistoryKeys t : types) {
			if (t.getValue().equals(value)) {
				return t;
			}
		}
		return null;
	}
	
}
