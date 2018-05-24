package com.ko30.quartz.core.handler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.BiConsumer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ko30.common.base.entity.quartz.QuartzParamInfo;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.SpringUtils;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.constant.enums.quartz.QuartzStatus;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.entity.model.po.quartz.Quartz;
import com.ko30.entity.model.po.quartz.QuartzRunning;
import com.ko30.facade.quartz.QuartzRunningFacadeService;
import com.ko30.facade.quartz.QuartzFacadeService;
import com.ko30.quartz.core.handler.QuartzHandler;
import com.ko30.quartz.core.handler.QuartzHandlerFactory;

@Service
@Transactional(rollbackFor = { Exception.class, RuntimeException.class, BusinessException.class })
public class QuartzHandlerFactoryImpl implements QuartzHandlerFactory{

	private static final Logger logger = Logger.getLogger(QuartzHandlerFactoryImpl.class);
//	executeHandler(QuartzHandlerType tag,QuartParamInfo obj)
	private List<QuartzHandler> handlers;
	private ThreadPoolExecutor executor;
	private QuartzFacadeService quartzService;
	private QuartzRunningFacadeService quartzRunningService;
	private List<QuartzRunning> quartzRunningList;
	
	public QuartzHandlerFactoryImpl() {
		logger.debug("初始化队列框架...");
		synchronized (QuartzHandlerFactoryImpl.class) {
			quartzService = SpringUtils.getBean(QuartzFacadeService.class);
			quartzRunningService = SpringUtils.getBean(QuartzRunningFacadeService.class);
			executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
		}
	}

	private void initQuartzRunnig() {
		if (quartzRunningList != null) {
			return;
		}
		logger.debug("初始化QuartzRunning..");
		quartzRunningList = quartzRunningService.findAll();
		if (!AssertValue.isNotNull(quartzRunningList)) {
			quartzRunningList = new ArrayList<>();
		}
		logger.debug("得到QuartzRunning:"+quartzRunningList.size());
		QuartzRunning quartzRunning = null;
		for (QuartzHandler handler : this.handlers) {
			quartzRunning = getQuartzRunning(handler.getType().name(),handler.getClass().getName().replaceAll("\\$.*", ""));
			if (!AssertValue.isNotNull(quartzRunning)) {
				logger.debug("新增QuartzRunning:"+handler.getType().name()+","+handler.getDescription());
				quartzRunning = new QuartzRunning();
				quartzRunning.setClassName(handler.getClass().getName().replaceAll("\\$.*", ""));
				quartzRunning.setDescr(handler.getDescription());
				quartzRunning.setState(QuartzStatus.NONE.getValue());
				quartzRunning.setType(handler.getType().name());
				quartzRunningService.save(quartzRunning);
			}
		}
		
	}

	private QuartzRunning getQuartzRunning(String type, String className) {
		for (QuartzRunning qr : quartzRunningList) {
			if (qr.getType().equals(type) && qr.getClassName().equals(className)) {
				return qr;
			}
		}
		return null;
	}

	
	private void init() {
		if (handlers != null) {
			return;
		}
		handlers = SpringUtils.getBeans(QuartzHandler.class);
		initQuartzRunnig();
	}

	@Override
	public void executeHandler(Quartz quartz) {
		init();
		excutorTask(new Runnable() {
			@Override
			public void run() {
				try {
					logger.debug(Thread.currentThread().getName() + ",处理消息："+quartz.getId()+":" + quartz.getType() );
					quartz.setExecuteTime(new Date());
					List<QuartzHandler> targetHs = getMQHandlers(quartz.getType());
					for (QuartzHandler handler : targetHs) {
						logger.debug("找到MQHandler:"+quartz.getType()+","+handler.getDescription());
						JSONObject params = customerHandlerParams(JSONObject.parseObject(quartz.getParams()),handler);
						if (AssertValue.isNotNull(params)) {
							logger.debug("确定传递参数："+params.toJSONString());
							handler.handler(params);
						}
					}
					quartz.setState(QuartzStatus.COMPLETE.getValue());
					quartzService.update(quartz);
				} catch (Exception e) {
					e.printStackTrace();
					quartz.setState(QuartzStatus.EXCEPTION.getValue());
					String remark = quartz.getRemark();
					if (!AssertValue.isNotEmpty(remark)) {
						remark = "";
					}
					remark += ";"+e.toString();
					quartz.setRemark(remark);
					quartzService.update(quartz);
				}
			}

			private JSONObject customerHandlerParams(JSONObject parseObject, QuartzHandler handler) {
				QuartzRunning quartzRunning = getQuartzRunning(handler.getType().name(), handler.getClass().getName().replaceAll("\\$.*", ""));
				if (AssertValue.isNotNull(quartzRunning) && !"none".equals(quartzRunning.getState())) {
					logger.warn("不执行该handler..");
					return null;
				}
				if (AssertValue.isNotNull(quartzRunning) &&
						AssertValue.isNotEmpty(quartzRunning.getParams())) {
					logger.debug("得到定制参数："+quartzRunning.getParams());
					JSONObject customerParams = JSONObject.parseObject(quartzRunning.getParams());
					customerParams.forEach(new BiConsumer<String, Object>() {
						@Override
						public void accept(String key, Object value) {
							if (AssertValue.isNotEmpty(key)) {
								logger.debug("替换参数："+key+","+value);
								parseObject.put(key, value);
							}
						}
					});
				}
				return parseObject;
			}
		});
	}

	/**
	 * 使用线程池
	 * 
	 * @param runnable
	 */
	private void excutorTask(Runnable runnable) {
		executor.execute(runnable);
	}

	private List<QuartzHandler> getMQHandlers(String tag) {
		List<QuartzHandler> targetHs = new ArrayList<QuartzHandler>();
		if (AssertValue.isNotNullAndNotEmpty(tag)) {
			QuartzHandlerType type = QuartzHandlerType.valueOf(tag);
			if (type != null) {
				for (QuartzHandler handler : handlers) {
					if (handler.getType() == type) {
						targetHs.add(handler);
					}
				}
			}
		}
		return targetHs;
	}

	@Override
	public void executeHandler(QuartzHandlerType tag, QuartzParamInfo obj) {
		this.executeHandler(tag, obj, null);
	}

	/**
	 * 在指定的时间之后执行
	 * (delayms 是毫秒)
	 */
	@Override
	public void executeHandler(QuartzHandlerType tag, QuartzParamInfo obj, long delayms) {
		executeHandler(tag, obj, new Date(System.currentTimeMillis() + delayms));
	}

	/**
	 * 在指定的时间执行
	 */
	@Override
	public void executeHandler(QuartzHandlerType tag, QuartzParamInfo obj, Date delayDate) {
		init();
		excutorTask(new Runnable() {
			@Override
			public void run() {
				String tagName = tag.getName();
				String content = null;
				content =JSONObject.toJSONString(obj.getParamObj()) ;
				Date date = new Date();
				Quartz quartz = new Quartz();
				quartz.setCreateTime(date);
				quartz.setActiveTime(date);
				quartz.setType(tag.name());
				quartz.setName(tagName);
				quartz.setParams(content);
				quartz.setState(QuartzStatus.NONE.getValue());
				quartz.setUkey(obj.getExecuteKey());
				if (delayDate != null) {
					quartz.setActiveTime(delayDate);
				}
				quartz = quartzService.saveObj(quartz);
				logger.debug("保存定时任务成功："+quartz.getId());
			}
		});
	}

}
