package com.ko30.quartz.service.handler.opencai.gaopin._11xuan5;

import org.springframework.stereotype.Service;

import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.quartz.service.handler.OpencaiSetLotHistoryDataHandler;
import com.ko30.quartz.service.handler.opencai.FixedOneIntervalSetLotHitoryData;

/**
 * 
 * @ClassName: Hubei11xuan5_SetHistoryDataHandler
 * @Description: 湖北11选5
 * @author A18ccms a18ccms_gmail_com
 * @date 2017年12月1日 下午4:40:55
 *
 */
@Service
public class Hubei11xuan5_SetHistoryDataHandler extends FixedOneIntervalSetLotHitoryData implements
		OpencaiSetLotHistoryDataHandler {

	/**
	 * 每10分钟开一次奖 08:35:50  -->>	21:55:50    共81期
	 */
	@Override
	public AppLotHistory handler(AppLotHistory targetLot,TShowApiLotType showApiType) {
		return this.setData(targetLot, showApiType, 10);
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.HU_BEI_11_XUAN_5;
	}

}
