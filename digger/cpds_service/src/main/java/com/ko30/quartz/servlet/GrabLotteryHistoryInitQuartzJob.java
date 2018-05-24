package com.ko30.quartz.servlet;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.google.common.collect.Maps;
import com.ko30.common.base.entity.quartz.QuartzParamInfo;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.SpringUtils;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.quartz.Quartz;
import com.ko30.facade.quartz.QuartzFacadeService;
import com.ko30.quartz.core.handler.QuartzHandlerFactory;
import com.ko30.quartz.service.GrabGaopinLotteryHistoryDataService;
import com.ko30.quartz.service.GrabJingwaiLotteryHistoryDataService;
import com.ko30.quartz.service.GrabLotteryHistoryService;

/**
 * 
* @ClassName: TestDemoInitQuartzJob 
* @Description: 初始化加载任务 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月18日 上午9:11:58 
*
 */
//@Service
public class GrabLotteryHistoryInitQuartzJob implements InitializingBean {

	private String METHOD_SUFFIX="InitQuartzJob";
	
	private QuartzFacadeService quartzService;

	public GrabLotteryHistoryInitQuartzJob() {
		quartzService = SpringUtils.getBean(QuartzFacadeService.class);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {

		//quartzService.deleteAll();// 清空旧的记录
		// 测试代码执行++++++++++++++++++
		// 得到当前类中所要执行的方法
		Method[] methods = this.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().endsWith(METHOD_SUFFIX)) {
				Parameter[] params = method.getParameters();
				Object[] args = new Object[params.length];
				for (int i = 0; i < args.length; i++) {
					args[i] = params[i];
				}
				// 执行该方法
				method.invoke(this, args);
			}
		}
		 
	}
	
	//************************  初始化加载定时任务的方法以 InitQuartzJob 结尾  *******************************
	
	
	/**
	 * 
	* @Title: grabLotteryQuanguoDataInitQuartzJob 
	* @Description: 获取  全国彩    的历史记录，到2005-01-01. 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabLotteryQuanguoDataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.GRAB_QUAN_GUO_HISTORY.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.GRAB_QUAN_GUO_HISTORY.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			// 当前立即执行
			Calendar cal=Calendar.getInstance();
			cal.add(Calendar.MINUTE, 5);// 5分钟后执行
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			//factory.executeHandler(QuartzHandlerType.GRAB_QUAN_GUO_HISTORY, info,cal.getTime());
		}
	}
	
	/**
	 * 
	* @Title: grabLotteryRemenDataInitQuartzJob 
	* @Description: 初始获取  热门彩   历史数据 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabLotteryRemenDataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.GRAB_RE_MEN_HISTORY.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.GRAB_RE_MEN_HISTORY.getType()));

		GrabLotteryHistoryService grabLotteryHistoryService = SpringUtils.getBean(GrabLotteryHistoryService.class);
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			Map<String, Object> map=Maps.newConcurrentMap();
			List<Map<String, Object>> urls=grabLotteryHistoryService.getUrls();
			if (AssertValue.isNotNullAndNotEmpty(urls)) {
				map=urls.get(0);
			}
			info.setParamObj(map);
			// 当前立即执行
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 3);// 3分钟后执行
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.GRAB_RE_MEN_HISTORY,info, cal.getTime());
		}
	}
	
	/**
	 * 
	* @Title: grabLotteryJingwaiDataInitQuartzJob 
	* @Description: 初始获取  境外彩   历史数据  
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabLotteryJingwaiDataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.GRAB_JING_WAI_HISTORY.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.GRAB_JING_WAI_HISTORY.getType()));

		GrabJingwaiLotteryHistoryDataService grabHistoryService = SpringUtils.getBean(GrabJingwaiLotteryHistoryDataService.class);
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			
			Map<String, Object> map=Maps.newConcurrentMap();
			List<Map<String, Object>> urls=grabHistoryService.getUrls();
			if (AssertValue.isNotNullAndNotEmpty(urls)) {
				map=urls.get(0);
			}
			info.setParamObj(map);
			
			// 当前立即执行
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.MINUTE, 1);// 2
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.GRAB_JING_WAI_HISTORY,info, cal.getTime());
		}
	}
	
	/**
	 * 
	* @Title: grabLotteryGaopinDataInitQuartzJob 
	* @Description: 初始获取  高频彩   历史数据   
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabLotteryGaopinDataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.GRAB_GAO_PIN_HISTORY.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.GRAB_GAO_PIN_HISTORY.getType()));

		GrabGaopinLotteryHistoryDataService grabHistoryService = SpringUtils.getBean(GrabGaopinLotteryHistoryDataService.class);
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			
			Map<String, Object> map=Maps.newConcurrentMap();
			List<Map<String, Object>> urls=grabHistoryService.getUrls();
			if (AssertValue.isNotNullAndNotEmpty(urls)) {
				map=urls.get(0);
			}
			info.setParamObj(map);
			// 当前立即执行
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.HOUR_OF_DAY, 3);// 3
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.GRAB_GAO_PIN_HISTORY,info, cal.getTime());
		}
	}

}
