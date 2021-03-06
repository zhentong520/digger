package com.ko30.service.lotStatistics.handler.gaopin._11xuan5;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.service.lotStatistics.DrawCodeStatisticsHandler;
import com.ko30.service.lotStatistics.handler._11xuan5DrawCodeStatisticsHelper;


/**
 * 
* @ClassName: ShuangseqiuDrawCodeStatistics 
* @Description: 广东11选5 开奖号码统计
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月5日 上午10:57:32 
*
 */
@Service
public class Guangdong11xuan5_DrawCodeStatistics extends _11xuan5DrawCodeStatisticsHelper  implements DrawCodeStatisticsHandler {

	/**
	 * 保存统计结果
	 */
	@Override
	public void setDrawCodeStatistics(Integer lotCode){
		super.setDrawCodeStatistics(QuartzHandlerType.GUANG_DONG_11_XUAN_5.getCode());
	}	
	
	@Override
	public List<Map<String, Object>> getDrawCodeStatistics(Integer lotCode,Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public QuartzHandlerType getType() {
		return QuartzHandlerType.GUANG_DONG_11_XUAN_5;
	}
}
