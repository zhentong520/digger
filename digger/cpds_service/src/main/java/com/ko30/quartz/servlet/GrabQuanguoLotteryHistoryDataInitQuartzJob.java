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
* @ClassName: GrabLotteryHistoryDataInitQuartzJob 
* @Description: 获取全国彩种历史数据
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月22日 下午3:36:15 
*
 */
@Service
public class GrabQuanguoLotteryHistoryDataInitQuartzJob implements InitializingBean {

	private QuartzFacadeService quartzService;

	public GrabQuanguoLotteryHistoryDataInitQuartzJob() {
		quartzService = SpringUtils.getBean(QuartzFacadeService.class);
	}
	
	private static Logger logger = Logger.getLogger(GrabQuanguoLotteryHistoryDataInitQuartzJob.class);
	
	// 固定方法各的后缀
	private String METHOD_SUFFIX="InitQuartzJob";
	
	
	/**
	 * 执行初始化的定时任务记录
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		
		quartzService.deleteAll();// 清空旧的记录(与初始加载的执行先后有关系)
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
		logger.info("全国彩初始化记录："+methods.length);
	}
	
	//****************************************  获取最新各彩种数据  ********************************************
	
	/**
	 * 
	* @Title: getShuangseqiuDataInitQuartzJob 
	* @Description: 初始化加载获取双色球定时任务  
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getShuangseqiuDataInitQuartzJob() {
		String executeKey = QuartzHandlerType.SHUANG_SE_QIU.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.SHUANG_SE_QIU.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			// 每周二、四、日21:30:00开奖
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.SHUANG_SE_QIU, info);// 立即执行
		}
	}
	
	/**
	 * 
	* @Title: getDaletouDataInitQuartzJob 
	* @Description: 获取大乐透数据 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getDaletouDataInitQuartzJob() {
		String executeKey = QuartzHandlerType.DA_LE_TOU.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.DA_LE_TOU.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.DA_LE_TOU, info);// 立即执行
		}
	}
	
	/**
	 * 
	* @Title: getFucai3DDataInitQuartzJob 
	* @Description: 获取 福彩3D 开奖数据 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getFucai3DDataInitQuartzJob() {
		String executeKey = QuartzHandlerType.FU_CAI_3D.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.FU_CAI_3D.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.FU_CAI_3D, info);// 立即执行
		}
	}
	
	/**
	 * 
	* @Title: getPailie_3DataInitQuartzJob 
	* @Description: 获取 排列3
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getPailie_3DataInitQuartzJob() {
		String executeKey = QuartzHandlerType.PAI_LIE_3.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.PAI_LIE_3.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.PAI_LIE_3, info);// 立即执行
		}
	}
	
	/**
	 * 
	* @Title: getPailie_5DataInitQuartzJob 
	* @Description: 获取 排列5
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getPailie_5DataInitQuartzJob() {
		String executeKey = QuartzHandlerType.PAI_LIE_5.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.PAI_LIE_5.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.PAI_LIE_5, info);// 立即执行
		}
	}
	
	/**
	 * 
	* @Title: getQilecaiDataInitQuartzJob 
	* @Description: 获取七乐彩 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getQilecaiDataInitQuartzJob() {
		String executeKey = QuartzHandlerType.QI_LE_CAI.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.QI_LE_CAI.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.QI_LE_CAI, info);// 立即执行
		}
	}
	
	/**
	 * 
	* @Title: getQixingcaiDataInitQuartzJob 
	* @Description: 获取七星彩  
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getQixingcaiDataInitQuartzJob() {
		String executeKey = QuartzHandlerType.QI_XING_CAI.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.QI_XING_CAI.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.QI_XING_CAI, info);// 立即执行
		}
	}


}
