package com.ko30.facade.dao.lotteryInfo;



import java.util.List;
import java.util.Map;

import com.ko30.common.dao.BaseRepository;
import com.ko30.entity.model.po.winningInfo.RecommendExpert;

public interface RecommendExpertRepository extends BaseRepository<RecommendExpert, Long> {
	
	void removeRecommendExpert(String lotCode,String planCode);
	
	List<Map<String,String>> getExpertID(String lotCode,String planCode,String seatseq);

}
