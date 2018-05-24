package com.ko30.facade.lotteryInfo;

import java.util.List;
import java.util.Map;

import com.ko30.common.service.BaseFacadeService;
import com.ko30.entity.model.po.winningInfo.TbHistoryStatistics;

public interface IHistoryStatistic extends BaseFacadeService<TbHistoryStatistics, Long>{
	
	
	List<Map<String,Object>> queryPreData(String lotCode,String issue);
	
	List<Map<String,Object>> queryNumList(String lotCode);
}
