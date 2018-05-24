package com.ko30.service.lotStatistics.handler.gaopin._shishicai;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.service.lotStatistics.DrawCodeStatisticsHandler;
import com.ko30.service.lotStatistics.handler._Shishicai_DrawCodeStatisticsHelper;


/**
 * 
* @ClassName: Chongqing_shishicai_DrawCodeStatistics 
* @Description: 重庆时时彩 开奖号码统计
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月5日 上午10:57:32 
*
 */
@Service
public class Chongqing_shishicai_DrawCodeStatistics extends _Shishicai_DrawCodeStatisticsHelper  implements DrawCodeStatisticsHandler {

	
	/**
	 * 保存统计结果
	 */
	@Override
	public void setDrawCodeStatistics(Integer lotCode){
		super.setDrawCodeStatistics(QuartzHandlerType.CHONG_QING_SHI_SHI_CAI.getCode());
	}	
	
	@Override
	public List<Map<String, Object>> getDrawCodeStatistics(Integer lotCode,Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.CHONG_QING_SHI_SHI_CAI;
	}
}
