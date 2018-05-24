package com.ko30.quartz.service.handler;

import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;

/**
 * 
 * @ClassName: OpencaiSetLotHistoryDataHandler
 * @Description: 开奖网设置开奖信息辅助类
 * @author A18ccms a18ccms_gmail_com
 * @date 2017年12月1日 下午4:32:13
 *
 */
public interface OpencaiSetLotHistoryDataHandler {

	/**
	 * 
	 * @Title: handler
	 * @Description: 根据彩种类型，及目标实体，修改及填充目标实体信息
	 * @param @param targetLot 目标实体
	 * @param @param showApiType 彩种信息
	 * @param @return 设定文件
	 * @return AppLotHistory 返回类型
	 * @throws
	 */
	public AppLotHistory handler(AppLotHistory targetLot,TShowApiLotType showApiType);

	/**
	 * 
	 * @Title: getType
	 * @Description: 彩种类型枚举
	 * @param @return 设定文件
	 * @return QuartzHandlerType 返回类型
	 * @throws
	 */
	public QuartzHandlerType getType();
}
