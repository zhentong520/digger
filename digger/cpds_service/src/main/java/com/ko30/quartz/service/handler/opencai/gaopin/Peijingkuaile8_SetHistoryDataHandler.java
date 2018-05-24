package com.ko30.quartz.service.handler.opencai.gaopin;

import org.springframework.stereotype.Service;

import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.quartz.service.handler.OpencaiSetLotHistoryDataHandler;
import com.ko30.quartz.service.handler.opencai.FixedOneIntervalSetLotHitoryData;

/**
 * 
 * @ClassName: Peijingkuaile8_SetHistoryDataHandler
 * @Description: 北京快乐8
 * @author A18ccms a18ccms_gmail_com
 * @date 2017年12月1日 下午4:40:55
 *
 */
@Service
public class Peijingkuaile8_SetHistoryDataHandler extends FixedOneIntervalSetLotHitoryData implements
		OpencaiSetLotHistoryDataHandler {

	/**
	 * 每 5 分钟开一次奖 09:05:01  -->>	23:55:01    共 179 期  
	 * 每天179期，09:05起每5分钟一期
	 */
	@Override
	public AppLotHistory handler(AppLotHistory targetLot,TShowApiLotType showApiType) {
		return this.setData(targetLot, showApiType, 5);
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.BEI_JING_KUAI_LE_8;
	}

}
