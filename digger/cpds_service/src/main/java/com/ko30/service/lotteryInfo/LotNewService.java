package com.ko30.service.lotteryInfo;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ko30.common.base.entity.search.SearchOperator;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.service.impl.BaseService;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.BeanUtils;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.entity.model.po.winningInfo.AppLotNew;
import com.ko30.facade.lotteryInfo.LotNewFacadeService;

@Service
@Transactional(rollbackFor = { Exception.class, RuntimeException.class, BusinessException.class })
public class LotNewService extends BaseService<AppLotNew, Long> implements
		LotNewFacadeService {
	
	private Logger logger=Logger.getLogger(LotNewService.class);
	
	@Override
	public AppLotNew saveEntity(AppLotNew entity) {
		entity.setCreateTime( new Date());
		return this.saveAndFlush(entity);
	}

	
	/**
	 * 根据条件获取指定记录
	 */
	@Override
	public AppLotNew queryByCondition(AppLotNew param) {

		Searchable search = Searchable.newSearchable();
		search.addSearchFilter("lotCode", SearchOperator.eq, param.getLotCode());
		// 分组发生改变时不可用
		//search.addSearchFilter("lotGroupCode", SearchOperator.eq,param.getLotGroupCode());
		// 如果名字发生改变时不可用
		//search.addSearchFilter("lotAlias", SearchOperator.eq,param.getLotAlias());
		List<AppLotNew> list = this.findAllWithNoPageNoSort(search);

		return AssertValue.isNotNullAndNotEmpty(list) ? list.get(0) : null;
	}


	/**
	 * 更新最新记录
	 */
	@Override
	public AppLotNew update2New(AppLotNew newParam) {

		// 得到旧的实体
		newParam.setId(null);
		AppLotNew old = this.queryByCondition(newParam);
		if (AssertValue.isNotNull(old)) {
			BeanUtils.copy(newParam, old);
			old.setUpdateTime(new Date());
		} else {
			newParam.setCreateTime(new Date());
			old = newParam;
		}
		return this.saveObj(old);
	}
}
