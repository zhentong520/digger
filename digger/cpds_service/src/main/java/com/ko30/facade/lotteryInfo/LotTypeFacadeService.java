package com.ko30.facade.lotteryInfo;

import java.util.List;

import com.ko30.common.service.BaseFacadeService;
import com.ko30.entity.model.po.winningInfo.AppLotType;
import com.ko30.entity.model.vo.winningInfo.LotTypeVo;

public interface LotTypeFacadeService extends
		BaseFacadeService<AppLotType, Integer> {

	AppLotType addEntity(AppLotType entity);
	
	AppLotType updateEntity(AppLotType entity);
	
	List<LotTypeVo> queryByCondition(LotTypeVo queryParam);
	
}
