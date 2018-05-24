package com.ko30.facade.lotteryInfo;

import com.ko30.common.service.BaseFacadeService;
import com.ko30.entity.model.po.winningInfo.AppLotNew;


/**
 * O
* @ClassName: LotHistoryFacadeService 
* @Description: 开奖记录服务接口 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月24日 下午4:46:05 
*
 */
public interface LotNewFacadeService extends BaseFacadeService<AppLotNew, Long> {
	
	AppLotNew saveEntity(AppLotNew entity);
	
	/**
	 * 
	* @Title: queryByCondition 
	* @Description: 根据条件获取指定记录 
	* @param @param param
	* @param @return    设定文件 
	* @return AppLotNew    返回类型 
	* @throws
	 */
	AppLotNew queryByCondition(AppLotNew param);
	
	/**
	 * 
	* @Title: update2New 
	* @Description: 更新记录 
	* @param @param newParam
	* @param @return    设定文件 
	* @return AppLotNew    返回类型 
	* @throws
	 */
	AppLotNew update2New(AppLotNew newParam);
}
