package com.ko30.facade.lotteryInfo;

import com.ko30.common.service.BaseFacadeService;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.LotDrawCodeStatistic;

public interface LotDrawCodeStatisticFacadeService extends BaseFacadeService<LotDrawCodeStatistic, Long> {

	
	/**
	 * 
	* @Title: saveEntity 
	* @Description: 新增实体
	* @param @param e    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void saveEntity(LotDrawCodeStatistic e);
	
	/**
	 * 
	* @Title: queryByLotCodeAndIssue 
	* @Description: 根据指定参数获取记录 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return LotDrawCodeStatistic    返回类型 
	* @throws
	 */
	LotDrawCodeStatistic queryByLotCodeAndIssue(AppLotHistory queryParam);
}
