package com.ko30.service.lotStatistics.handler.gaopin._shishicai;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.service.lotStatistics.DrawCodeStatisticsHandler;
import com.ko30.service.lotStatistics.handler._Shishicai_DrawCodeStatisticsHelper;

/**
 * 
 * @ClassName: Tianjin_shishicai_DrawCodeStatistics
 * @Description: 天津时时彩开奖号码统计
 * @author carr
 * @date 2018年2月6日 下午4:36:04
 *
 */
@Service
public class Tianjin_shishicai_DrawCodeStatistics extends _Shishicai_DrawCodeStatisticsHelper implements DrawCodeStatisticsHandler {

	@Override
	public void setDrawCodeStatistics(Integer lotCode) {
		super.setDrawCodeStatistics(QuartzHandlerType.TIAN_JIN_SHI_SHI_CAI.getCode());
	}

	@Override
	public List<Map<String, Object>> getDrawCodeStatistics(Integer lotCode,
			Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.TIAN_JIN_SHI_SHI_CAI;
	}

}
