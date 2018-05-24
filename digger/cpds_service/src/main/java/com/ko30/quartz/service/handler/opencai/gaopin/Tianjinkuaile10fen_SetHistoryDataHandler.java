package com.ko30.quartz.service.handler.opencai.gaopin;

import org.springframework.stereotype.Service;

import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.po.winningInfo.TShowApiLotType;
import com.ko30.quartz.service.handler.OpencaiSetLotHistoryDataHandler;
import com.ko30.quartz.service.handler.opencai.FixedOneIntervalSetLotHitoryData;

/**
 * 
 * @ClassName: Tianjinkuaile10fen_SetHistoryDataHandler
 * @Description: 天津快乐10分
 * @author A18ccms a18ccms_gmail_com
 * @date 2017年12月1日 下午4:40:55
 *
 */
@Service
public class Tianjinkuaile10fen_SetHistoryDataHandler extends FixedOneIntervalSetLotHitoryData implements OpencaiSetLotHistoryDataHandler {

	/**
	 * 每10分钟开一次奖 09:06:00  -->>	22:56:00   84期
	 */
	@Override
	public AppLotHistory handler(AppLotHistory targetLot,TShowApiLotType showApiType) {
		return super.setData(targetLot, showApiType, 10);
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.TIAN_JIN_KUAI_LE_10FEN;
	}

}
