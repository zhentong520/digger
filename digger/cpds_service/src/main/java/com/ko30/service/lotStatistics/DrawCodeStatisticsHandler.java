package com.ko30.service.lotStatistics;

import java.util.List;
import java.util.Map;

import com.ko30.constant.enums.quartz.QuartzHandlerType;


/**
 * 
* @ClassName: DrawCodeStatisticsHandler 
* @Description:开奖最新记录统计处理器
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月9日 下午3:49:32 
*
 */
public interface DrawCodeStatisticsHandler {
	
	/**
	 * 
	* @Title: setDrawCodeStatistics 
	* @Description: 设置统计信息到缓存中
	* @param @param lotCode    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void setDrawCodeStatistics(Integer lotCode);
	
	/**
	 * 
	* @Title: getDrawCodeStatistics 
	* @Description: 根据条件获取指定的统计信息
	* @param @param lotCode
	* @param @param params
	* @param @return    设定文件 
	* @return List<Map<String,Object>>    返回类型 
	* @throws
	 */
	List<Map<String, Object>> getDrawCodeStatistics(Integer lotCode,Object ...params);
	
	/**
	 * 
	* @Title: getType 
	* @Description: 设置对应类型 
	* @param @return    设定文件 
	* @return QuartzHandlerType    返回类型 
	* @throws
	 */
	QuartzHandlerType getType();

}
