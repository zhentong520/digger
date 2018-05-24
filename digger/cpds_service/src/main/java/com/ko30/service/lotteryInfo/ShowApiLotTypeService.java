package com.ko30.service.lotteryInfo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ko30.common.base.entity.search.SearchOperator;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.service.impl.BaseService;
import com.ko30.common.util.AssertValue;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.facade.lotteryInfo.ShowApiLotTypeFacadeService;

@Service
@Transactional(rollbackFor={BusinessException.class,RuntimeException.class,Exception.class})
public class ShowApiLotTypeService  extends BaseService<TShowApiLotType, Long> implements ShowApiLotTypeFacadeService {

	/**
	 * 按彩票类型码，获取showapi对应的彩种信息
	 */
	
	@Override
	public TShowApiLotType getOneByLotCode(int lotCode) {

		Searchable search = Searchable.newSearchable();
		search.addSearchFilter("lotCode", SearchOperator.eq, lotCode);
		List<TShowApiLotType> types = this.findAllWithNoPageNoSort(search);
		
		if (AssertValue.isNotNullAndNotEmpty(types)) {
			return types.get(0);
		}
		return null;
	}

}
