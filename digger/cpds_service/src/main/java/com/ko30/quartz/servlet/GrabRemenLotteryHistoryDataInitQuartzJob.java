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
 * @ClassName: GrabRemenLotteryHistoryDataInitQuartzJob
 * @Description: 获取热门彩最新数据初始化任务
 * @author A18ccms a18ccms_gmail_com
 * @date 2017年8月26日 下午12:17:14
 *
 */
@Service
public class GrabRemenLotteryHistoryDataInitQuartzJob implements InitializingBean {

	private static Logger logger = Logger.getLogger(GrabRemenLotteryHistoryDataInitQuartzJob.class);

	private QuartzFacadeService quartzService;

	public GrabRemenLotteryHistoryDataInitQuartzJob() {
		quartzService = SpringUtils.getBean(QuartzFacadeService.class);
	}

	// 固定方法各的后缀
	private String METHOD_SUFFIX = "InitQuartzJob";

	@Override
	public void afterPropertiesSet() throws Exception {
		//quartzService.deleteAll();// 清空旧的记录
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
		logger.info("热门彩初始化记录："+methods.length);
	}

	// ********************** 获取最新各彩种数据 *******************************
	/**
	 * 
	* @Title: getBjpk10DataInitQuartzJob 
	* @Description: 获取北京PK10 最新开奖记录 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getBjpk10DataInitQuartzJob() {
		String executeKey = QuartzHandlerType.BEI_JING_PK_SHI.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.BEI_JING_PK_SHI.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.BEI_JING_PK_SHI, info);// 立即执行
		}
	}
	
	/**
	 * 
	* @Title: getCqshishicaiDataInitQuartzJob 
	* @Description: 获取 重庆时时彩  最新开奖记录 
	*	 00：05：50起到 01：55：50止 每5分钟一次 ， 10：00：45 起到  次日 00:00:45，每10分钟一次 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getCqshishicaiDataInitQuartzJob() {
		String executeKey = QuartzHandlerType.CHONG_QING_SHI_SHI_CAI.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.CHONG_QING_SHI_SHI_CAI.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.CHONG_QING_SHI_SHI_CAI, info);// 立即执行
		}
	}
	
	
	/**
	 * 
	* @Title: getCqxingyunnongchangDataInitQuartzJob 
	* @Description: 重庆幸运10分 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getCqxingyunnongchangDataInitQuartzJob() {
		
		String executeKey = QuartzHandlerType.CHONG_QING_XING_YUN_NONG_CHANG.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.CHONG_QING_XING_YUN_NONG_CHANG.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.CHONG_QING_XING_YUN_NONG_CHANG, info);// 立即执行
		}
	}
	
	
	/**
	 * 
	* @Title: getGdkl10fenDataInitQuartzJob 
	* @Description: 广东快乐10分   09:11:20 开始   23:01:20 结束 每隔10分钟
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getGdkl10fenDataInitQuartzJob() {
		
		String executeKey = QuartzHandlerType.GUANG_DONG_KUAI_LE_10FEN.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.GUANG_DONG_KUAI_LE_10FEN.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.GUANG_DONG_KUAI_LE_10FEN, info);// 立即执行
		}
	}
	
	
	/**
	 * 
	* @Title: getGxkl10fenDataInitQuartzJob 
	* @Description: 广西快乐10分
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getGxkl10fenDataInitQuartzJob() {
		
		String executeKey = QuartzHandlerType.GUANG_XI_KUAI_LE_10FEN.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.GUANG_XI_KUAI_LE_10FEN.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.GUANG_XI_KUAI_LE_10FEN, info);// 立即执行
		}
	}
	
	/**
	 * 
	* @Title: getJssaicheDataInitQuartzJob 
	* @Description: 极速赛车
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getJssaicheDataInitQuartzJob() {
		
		String executeKey = QuartzHandlerType.JI_SU_SAI_CHE.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.JI_SU_SAI_CHE.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.JI_SU_SAI_CHE, info);// 指定时间执行
		}
	}

	
	/**
	 * 
	* @Title: getJssscDataInitQuartzJob 
	* @Description: 极速时时彩  
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getJssscDataInitQuartzJob() {
		String executeKey = QuartzHandlerType.JI_SU_SHI_SHI_CAI.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.JI_SU_SHI_SHI_CAI.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.JI_SU_SHI_SHI_CAI, info);// 指定时间执行
		}
	}
	
	/**
	 * 
	* @Title: getPCddxingyun28DataInitQuartzJob 
	* @Description: PC蛋蛋幸运28
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getPCddxingyun28DataInitQuartzJob() {
		String executeKey = QuartzHandlerType.PC_DAN_DAN_XING_YUN_28.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.PC_DAN_DAN_XING_YUN_28.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.PC_DAN_DAN_XING_YUN_28, info);// 指定时间执行
		}
	}
	
	/**
	 * 
	* @Title: getSyyunduojinDataInitQuartzJob 
	* @Description: 11运夺金
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getSyyunduojinDataInitQuartzJob() {
		String executeKey = QuartzHandlerType.SHI_YI_YUN_DUO_JIN.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.SHI_YI_YUN_DUO_JIN.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.SHI_YI_YUN_DUO_JIN, info);// 指定时间执行
		}
	}
	
	/**
	 * 
	* @Title: getTianjinshishicaiDataInitQuartzJob 
	* @Description:天津时时彩
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getTianjinshishicaiDataInitQuartzJob() {
		String executeKey = QuartzHandlerType.TIAN_JIN_SHI_SHI_CAI.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.TIAN_JIN_SHI_SHI_CAI.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.TIAN_JIN_SHI_SHI_CAI, info);// 指定时间执行
		}
	}
	
	/**
	 * 
	* @Title: getXinjiangshishicaiDataInitQuartzJob 
	* @Description:新疆时时彩 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void getXinjiangshishicaiDataInitQuartzJob() {
		String executeKey = QuartzHandlerType.XIN_JIANG_SHI_SHI_CAI.getType();
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.XIN_JIANG_SHI_SHI_CAI.getType()));
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			QuartzParamInfo info = new QuartzParamInfo();
			info.setParamObj(Maps.newConcurrentMap());
			info.setExecuteKey(executeKey);
			factory.executeHandler(QuartzHandlerType.XIN_JIANG_SHI_SHI_CAI, info);// 指定时间执行
		}
	}
	
	
}
