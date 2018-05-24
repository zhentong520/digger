package com.ko30.service.lotStatistics.handler.gaopin._11xuan5;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.service.lotStatistics.DrawCodeStatisticsHandler;
import com.ko30.service.lotStatistics.handler._11xuan5DrawCodeStatisticsHelper;

/**
 * 
 * @ClassName: Shandong11xuan5_DrawCodeStatistics
 * @Description: 山东11选5（山东十一运夺金）走势图统计
 * @author carr
 * @date 2018年2月26日 上午10:08:32
 *
 */
@Service
public class Shandong11xuan5_DrawCodeStatistics  extends _11xuan5DrawCodeStatisticsHelper  implements DrawCodeStatisticsHandler{

	/**
	 * 保存统计结果
	 */
	@Override
	public void setDrawCodeStatistics(Integer lotCode) {
		super.setDrawCodeStatistics(QuartzHandlerType.SHI_YI_YUN_DUO_JIN.getCode());
	}

	@Override
	public List<Map<String, Object>> getDrawCodeStatistics(Integer lotCode,
			Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuartzHandlerType getType() {
		 return QuartzHandlerType.SHI_YI_YUN_DUO_JIN;
	}

}
