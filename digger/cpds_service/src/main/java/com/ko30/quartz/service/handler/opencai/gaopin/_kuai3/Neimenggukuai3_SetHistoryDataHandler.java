package com.ko30.quartz.service.handler.opencai.gaopin._kuai3;

import org.springframework.stereotype.Service;

import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.quartz.service.handler.OpencaiSetLotHistoryDataHandler;
import com.ko30.quartz.service.handler.opencai.FixedOneIntervalSetLotHitoryData;

/**
 * 
* @ClassName: Neimenggukuai3_SetHistoryDataHandler 
* @Description: 内蒙古快三
* @author A18ccms a18ccms_gmail_com 
* @date 2017年12月4日 上午10:13:54 
*
 */
@Service
public class Neimenggukuai3_SetHistoryDataHandler extends FixedOneIntervalSetLotHitoryData implements OpencaiSetLotHistoryDataHandler {

	/**
	 * 每 10 分钟开一次奖 09:46:51  -->>	21:58:51   73期
	 */
	@Override
	public AppLotHistory handler(AppLotHistory targetLot,TShowApiLotType showApiType) {
		return super.setData(targetLot, showApiType, 10);
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.NEI_MENG_GU_KUAI_3;
	}

}
