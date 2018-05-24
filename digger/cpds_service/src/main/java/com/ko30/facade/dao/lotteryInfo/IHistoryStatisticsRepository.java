package com.ko30.facade.dao.lotteryInfo;

import java.util.List;
import java.util.Map;

import com.ko30.common.dao.BaseRepository;
import com.ko30.entity.model.po.winningInfo.TbHistoryStatistics;

public interface IHistoryStatisticsRepository extends BaseRepository<TbHistoryStatistics, Long> {

	
	List<Map<String,Object>> queryPreData(String lotCode,String issue);
	
	List<Map<String,Object>> queryNumList(String lotCode);
	
}
