package com.ko30.service.lotStatistics;


/**
 * 
* @ClassName: DrawCodeStatisticsFactory 
* @Description: 历史开奖号码统计工厂
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月5日 下午5:01:35 
*
 */
public interface DrawCodeStatisticsFactory {

	/**
	 * 
	* @Title: setDrawCodeStatistics 
	* @Description: 保存统计信息
	* @param @param lotCode    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void setDrawCodeStatistics(Integer lotCode);
	
}
