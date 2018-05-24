package com.ko30.service.lotteryInfo;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ko30.common.base.entity.search.SearchOperator;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.service.impl.BaseService;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.BeanUtils;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.entity.model.po.winningInfo.AppLotType;
import com.ko30.entity.model.vo.winningInfo.LotTypeVo;
import com.ko30.facade.lotteryInfo.LotTypeFacadeService;

@Service
@Transactional(rollbackFor = { Exception.class, RuntimeException.class, BusinessException.class })
public class LotTypeService extends BaseService<AppLotType, Integer>
		implements LotTypeFacadeService {
	
	/**
	 * 新增
	 */
	@Override
	public AppLotType addEntity(AppLotType entity) {

		entity.setCreateTime(new Date());
		entity = this.saveAndFlush(entity);
		return entity;
	}

	/**
	 * 修改
	 */
	@Override
	public AppLotType updateEntity(AppLotType entity) {
		
		entity = this.saveObj(entity);
		return entity;
	}

	/**
	 * 按条件获取列表
	 */
	@Override
	public List<LotTypeVo> queryByCondition(LotTypeVo queryParam) {

		Searchable search = Searchable.newSearchable();
		// 彩票代码
		if (AssertValue.isNotNull(queryParam.getLotCode())) {
			search.addSearchFilter("lotCode", SearchOperator.eq,queryParam.getLotCode());
		}
		// 开奖类型 彩票类型:1低频2高频3地方彩
		if (AssertValue.isNotNull(queryParam.getLotAlias())) {
			search.addSearchFilter("lotType", SearchOperator.eq,queryParam.getLotAlias());
		}
		List<AppLotType> types = this.findAllWithSort(search);
		// 以vo 类型返回查询结果
		List<LotTypeVo> result = Lists.newArrayList();
		for (AppLotType type : types) {
			LotTypeVo resultData = new LotTypeVo();
			BeanUtils.copy(type, resultData);
			result.add(resultData);
		}
		return result;
	}

}
