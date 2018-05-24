package com.ko30.quartz.servlet;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.ko30.common.base.entity.quartz.QuartzParamInfo;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.SpringUtils;
import com.ko30.constant.enums.quartz.QuartzHandlerType;
import com.ko30.entity.model.po.quartz.Quartz;
import com.ko30.facade.quartz.QuartzFacadeService;
import com.ko30.quartz.core.handler.QuartzHandlerFactory;


/**
 * 
* @ClassName: GrabJingwaiLotteryNewDataInitQuartzJob 
* @Description: 初始化定时获取境外彩 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月1日 下午2:25:22 
*
 */
@Service
public class GrabJingwaiLotteryNewDataInitQuartzJob implements InitializingBean {

	private static Logger logger = Logger.getLogger(GrabJingwaiLotteryNewDataInitQuartzJob.class);
	
	private String METHOD_SUFFIX="InitQuartzJob";
	
	private QuartzFacadeService quartzService ;
	public GrabJingwaiLotteryNewDataInitQuartzJob() {
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
		logger.debug("初始化境外彩定时任务记录："+methods.length);
	}
	
	//***************  获取最新各彩种数据  ************************
	
	/**
	 * 
	* @Title: grabAozhouxingyun5_DataInitQuartzJob 
	* @Description: 澳洲幸运5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabAozhouxingyun5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.AO_ZHOU_XING_YUN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.AO_ZHOU_XING_YUN_5.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.AO_ZHOU_XING_YUN_5, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.AO_ZHOU_XING_YUN_5.getType());
		}
	}
	
	/**
	 * 
	* @Title: grabAozhouxingyun5_DataInitQuartzJob 
	* @Description: 澳洲幸运8 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabAozhouxingyun8_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.AO_ZHOU_XING_YUN_8.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.AO_ZHOU_XING_YUN_8.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.AO_ZHOU_XING_YUN_8, info);
		}
	}
	
	/**
	 * 
	* @Title: grabAozhouxingyun5_DataInitQuartzJob 
	* @Description: 澳洲幸运10 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabAozhouxingyun10_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.AO_ZHOU_XING_YUN_10.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.AO_ZHOU_XING_YUN_10.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.AO_ZHOU_XING_YUN_10, info);
		}
	}
	
	/**
	 * 
	* @Title: grabAozhouxingyun5_DataInitQuartzJob 
	* @Description: 澳洲幸运20 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabAozhouxingyun20_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.AO_ZHOU_XING_YUN_20.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.AO_ZHOU_XING_YUN_20.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.AO_ZHOU_XING_YUN_20, info);
		}
	}
	
	/**
	 * 
	* @Title: grabAozhouxingyun5_DataInitQuartzJob 
	* @Description: 台湾宾果 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabTaiwanbinguo_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.TAI_WANG_BIN_GUO.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.TAI_WANG_BIN_GUO.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.TAI_WANG_BIN_GUO, info);
		}
	}

}
