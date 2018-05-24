package com.ko30.quartz.service.handler.opencai.remen;

import org.springframework.stereotype.Service;

import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.quartz.service.handler.OpencaiSetLotHistoryDataHandler;
import com.ko30.quartz.service.handler.opencai.FixedOneIntervalSetLotHitoryData;

/**
 * 
 * @ClassName: Syyunduojin_Shangdong11xuan5_SetHistoryDataHandler
 * @Description: 山东（十一运夺金）11选5
 * @author A18ccms a18ccms_gmail_com
 * @date 2017年12月1日 下午4:40:55
 *
 */
@Service
public class Syyunduojin_Shangdong11xuan5_SetHistoryDataHandler extends FixedOneIntervalSetLotHitoryData implements
		OpencaiSetLotHistoryDataHandler {

	/**
	 * 每10分钟开一次奖 08:35:40  -->>	22:55:40    共87期
	 */
	@Override
	public AppLotHistory handler(AppLotHistory targetLot,TShowApiLotType showApiType) {
		return this.setData(targetLot, showApiType, 10);
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.SHI_YI_YUN_DUO_JIN;
	}

}
