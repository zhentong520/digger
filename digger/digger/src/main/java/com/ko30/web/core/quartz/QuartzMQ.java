package com.ko30.web.core.quartz;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.ko30.common.util.AssertValue;
import com.ko30.constant.enums.quartz.QuartzStatus;
import com.ko30.entity.model.po.quartz.Quartz;
import com.ko30.facade.quartz.QuartzFacadeService;
import com.ko30.quartz.core.handler.QuartzHandlerFactory;

public class QuartzMQ {

	private Logger logger=Logger.getLogger(QuartzMQ.class);
	
	@Autowired
	private QuartzFacadeService quartzService;
	
	@Autowired
	private QuartzHandlerFactory quartzHandlerFactory;

	public void execute() {
		
		// 暂不执行定时任务
		boolean noNeed = false;
		if (noNeed) {
			return;
		}
		
		List<Quartz> quartzList = quartzService.queryTopOfUnCompleteForUpdate(10);
		if (AssertValue.isNotNullAndNotEmpty(quartzList)) {
			for (Quartz quartz : quartzList) {
				quartz.setState(QuartzStatus.RUNNING.getValue());
				quartzService.update(quartz);
				quartzHandlerFactory.executeHandler(quartz);
			}
			logger.info("执行任务的记录：" + quartzList.size());
		}
	}
}
