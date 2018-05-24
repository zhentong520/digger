package com.ko30.facade.lotteryInfo;

import java.util.List;
import java.util.Map;

import com.ko30.common.service.BaseFacadeService;
import com.ko30.entity.model.po.winningInfo.RecommendExpert;

public interface RecommendExpertFacadeService extends BaseFacadeService<RecommendExpert, Long>{
	
	void saveRecommendExpert(RecommendExpert re);
	
	void removeRecommendExpert(String lotCode,String planCode);
	
	List<Map<String,String>> getExpertID(String lotCode,String planCode,String seatseq);
	
	

}
