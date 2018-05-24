package com.ko30.facade.lotteryInfo;

import com.ko30.common.service.BaseFacadeService;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;


/**
 * 
 * @ClassName: ShowApiLotTypeFacadeService
 * @Description: showapi彩种类型数据接口
 * @author A18ccms a18ccms_gmail_com
 * @date 2017年10月19日 上午11:55:02
 *
 */
public interface ShowApiLotTypeFacadeService extends
		BaseFacadeService<TShowApiLotType, Long> {

	/**
	 * 
	 * @Title: getOneByLotCode
	 * @Description: 根据彩种类型码（lotCode）获取单个类型实体
	 * @param @param lotCode
	 * @param @return 设定文件
	 * @return TShowApiLotType 返回类型
	 * @throws
	 */
	TShowApiLotType getOneByLotCode(int lotCode);
}
