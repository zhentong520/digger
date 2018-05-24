package com.ko30.service.lotteryInfo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ko30.common.service.impl.BaseService;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.entity.model.po.winningInfo.TbHistoryStatistics;
import com.ko30.facade.dao.lotteryInfo.IHistoryStatisticsRepository;
import com.ko30.facade.lotteryInfo.IHistoryStatistic;

@Service
@Transactional(rollbackFor = { Exception.class, RuntimeException.class, BusinessException.class })
public class HistoryStatisticService extends BaseService<TbHistoryStatistics, Long>  implements IHistoryStatistic{

	
	private IHistoryStatisticsRepository getRepository(){
		return (IHistoryStatisticsRepository)super.repository;
	}
	public void init() {
		super.init();
	}
	
	@Override
	public List<Map<String, Object>> queryPreData(String lotCode, String issue) {
		return getRepository().queryPreData(lotCode, issue);
	}
	
	@Override
	public List<Map<String, Object>> queryNumList(String lotCode) {
		return getRepository().queryNumList(lotCode);
	}
	


}
