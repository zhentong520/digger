package com.ko30.service.lotStatistics;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ko30.common.base.entity.search.SearchOperator;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.service.impl.BaseService;
import com.ko30.common.util.AssertValue;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.LotDrawCodeStatistic;
import com.ko30.facade.lotteryInfo.LotDrawCodeStatisticFacadeService;

@Service
@Transactional(rollbackFor = { Exception.class, RuntimeException.class, BusinessException.class })
public class LotDrawCodeStatisticService extends BaseService<LotDrawCodeStatistic, Long> implements
		LotDrawCodeStatisticFacadeService {

	@Override
	public void saveEntity(LotDrawCodeStatistic e) {
		e.setCreateTime(new Date());
	}

	@Override
	public LotDrawCodeStatistic queryByLotCodeAndIssue(AppLotHistory queryParam) {
		
		Searchable search=Searchable.newSearchable();
		if (AssertValue.isNotNull(queryParam.getLotCode())) {
			search.addSearchFilter("lotCode", SearchOperator.eq, queryParam.getLotCode());
		}
		if (AssertValue.isNotNullAndNotEmpty(queryParam.getPreDrawIssue())) {
			search.addSearchFilter("preDrawIssue", SearchOperator.eq, queryParam.getPreDrawIssue());
		}
		if (AssertValue.isNotNullAndNotEmpty(queryParam.getPreDrawCode())) {
			search.addSearchFilter("preDrawCode", SearchOperator.eq, queryParam.getPreDrawCode());
		}
		if (AssertValue.isNotNull(queryParam.getPreDrawTime())) {
			search.addSearchFilter("preDrawTime", SearchOperator.eq, queryParam.getPreDrawTime());
		}
		
		List<LotDrawCodeStatistic> list=this.findAllWithNoPageNoSort(search);
		if (AssertValue.isNotNullAndNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	
}
