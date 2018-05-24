package com.ko30.service.lotStatistics.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ko30.common.util.SpringUtils;
import com.ko30.service.lotStatistics.DrawCodeStatisticsFactory;
import com.ko30.service.lotStatistics.DrawCodeStatisticsHandler;

/**
 * 
* @ClassName: DrawCodeStatisticsFactoryImpl 
* @Description: 根据彩种，保存最新统计工厂实现
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月9日 下午3:53:28 
*
 */
@Service
public class DrawCodeStatisticsFactoryImpl implements DrawCodeStatisticsFactory {

	private static final Logger logger = Logger.getLogger(DrawCodeStatisticsFactoryImpl.class);
	
	private List<DrawCodeStatisticsHandler> handlers;
	
	public DrawCodeStatisticsFactoryImpl(){
		handlers=SpringUtils.getBeans(DrawCodeStatisticsHandler.class);
	}
	
	@Override
	public void setDrawCodeStatistics(Integer lotCode) {
		try {
			for (DrawCodeStatisticsHandler handler : handlers) {
				if (handler.getType().getCode() == lotCode.intValue()) {
					handler.setDrawCodeStatistics(lotCode);
					logger.debug("设置 "+handler.getType().getName()+" 统计成功。");
					return;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(String.format("lotCode:%d,执行统计异常。",lotCode));
		}
		
	}

}
