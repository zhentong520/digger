package com.ko30.service.lotteryInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ko30.common.service.impl.BaseService;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.entity.model.po.winningInfo.RecommendExpert;
import com.ko30.facade.dao.lotteryInfo.RecommendExpertRepository;
import com.ko30.facade.lotteryInfo.RecommendExpertFacadeService;

@Service
@Transactional(rollbackFor = { Exception.class, RuntimeException.class, BusinessException.class })
public class RecommendExpertService extends BaseService<RecommendExpert, Long>  implements RecommendExpertFacadeService{

	private RecommendExpertRepository getRepository(){
		return (RecommendExpertRepository)super.repository;
	}
	public void init() {
		super.init();
	}
	
	@Override
	public void removeRecommendExpert(String lotCode,String planCode){
		if(StringUtils.isNotEmpty(lotCode)&&StringUtils.isNotEmpty(planCode)){
			this.getRepository().removeRecommendExpert(lotCode,planCode);
		}
	}
	
	@Override
	public void saveRecommendExpert(RecommendExpert re){
		if(!Objects.isNull(re)){
			this.saveAndFlush(re);
		}
	}
	@Override
	public List<Map<String,String>> getExpertID(String lotCode, String planCode,
			String seatseq) {
		return this.getRepository().getExpertID(lotCode, planCode, seatseq);
	}



	
}
