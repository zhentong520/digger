package com.ko30.quartz.core.handler;

import java.util.Date;

import com.ko30.common.base.entity.quartz.QuartzParamInfo;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.quartz.Quartz;

public interface QuartzHandlerFactory {
	
	public void executeHandler(QuartzHandlerType tag,QuartzParamInfo obj);

	public void executeHandler(QuartzHandlerType tag,QuartzParamInfo obj,long delayms);
	
	public void executeHandler(QuartzHandlerType tag,QuartzParamInfo obj,Date delayDate);
	
	public void executeHandler(Quartz quartz);
	
}
