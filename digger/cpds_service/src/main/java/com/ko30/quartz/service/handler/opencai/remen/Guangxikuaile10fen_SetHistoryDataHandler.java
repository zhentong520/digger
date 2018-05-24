package com.ko30.quartz.service.handler.opencai.remen;

import org.springframework.stereotype.Service;

import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.quartz.service.handler.OpencaiSetLotHistoryDataHandler;
import com.ko30.quartz.service.handler.opencai.FixedOneIntervalSetLotHitoryData;

/**
 * 
* @ClassName: Guangxikuaile10fen_SetHistoryDataHandler 
* @Description: 广西快乐10分 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年12月4日 上午10:59:28 
*
 */
@Service
public class Guangxikuaile10fen_SetHistoryDataHandler extends FixedOneIntervalSetLotHitoryData implements
		OpencaiSetLotHistoryDataHandler {

	/**
	 * 每15 分钟开一次奖 09:12:35  -->>	21:27:35    共50 期
	 */
	@Override
	public AppLotHistory handler(AppLotHistory targetLot,TShowApiLotType showApiType) {
		return this.setData(targetLot, showApiType, 15);
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.GUANG_XI_KUAI_LE_10FEN;
	}

}
