package com.ko30.facade.lotteryInfo;

import com.ko30.common.service.BaseFacadeService;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TbLotLatestBonus;


/**
 * 
* @ClassName: TbLotLatestBonusFacadeService 
* @Description: 彩种预测中奖金额累积接口
* @author A18ccms a18ccms_gmail_com 
* @date 2017年10月11日 下午2:25:38 
*
 */
public interface TbLotLatestBonusFacadeService extends
		BaseFacadeService<TbLotLatestBonus, Long> {

	/**
	 * 
	* @Title: updateLatestBonusAmount 
	* @Description: 根据指定彩种编码，修改最新的预测中奖金额
	* @param @param lot
	* @param @return    设定文件 
	* @return TbLotLatestBonus    返回类型 
	* @throws
	 */
	TbLotLatestBonus updateLatestBonusAmount(AppLotHistory lot);
}
