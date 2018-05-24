package com.ko30.facade.dao.lotteryInfo;

import java.util.List;

import com.ko30.common.dao.BaseRepository;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.vo.winningInfo.LotHistoryVo;

public interface LotHistoryRepository extends BaseRepository<AppLotHistory, Long> {

	/**
	 * 
	* @Title: queryByLotGroupCode 
	* @Description: 按彩票类型编码获取彩票历史列表 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<LotHistoryVo>    返回类型 
	* @throws
	 */
	List<LotHistoryVo> queryByLotGroupCode(LotHistoryVo queryParam);
	
	
	/**
	 * 根据指定彩种，获取指定的最新条数
	 */
	List<AppLotHistory> getNewListByCountAndLotCode(Integer lotCode,Integer count,int calenderType);
}
