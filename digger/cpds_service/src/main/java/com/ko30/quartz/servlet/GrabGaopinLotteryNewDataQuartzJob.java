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
* @ClassName: GrabGaopinLotteryNewDataQuartzJob 
* @Description: 初始化获取高频彩最新开奖记录定时任务  
* @author A18ccms a18ccms_gmail_com 
* @date 2017年9月1日 下午2:33:12 
*
 */
@Service
public class GrabGaopinLotteryNewDataQuartzJob implements InitializingBean {

	private static Logger logger = Logger.getLogger(GrabGaopinLotteryNewDataQuartzJob.class);
	
	private String METHOD_SUFFIX="InitQuartzJob";
	
	private QuartzFacadeService quartzService ;
	
	public GrabGaopinLotteryNewDataQuartzJob() {
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
		logger.debug("初始化高频彩定时任务记录："+methods.length);
	}
	
	//***************  获取最新各彩种数据  ************************
	
	/**
	 * 
	 * 1
	 * 
	* @Title: grabAozhouxingyun5_DataInitQuartzJob 
	* @Description: 广东11选5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabGuangong11xuan5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.GUANG_DONG_11_XUAN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.GUANG_DONG_11_XUAN_5.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.GUANG_DONG_11_XUAN_5, info);
			
		}
	}
	
	/**
	 * 2
	 * 
	* @Title: grabShanghai11xuan5_DataInitQuartzJob 
	* @Description: 上海11选 5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabShanghai11xuan5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.SHANG_HAI_11_XUAN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.SHANG_HAI_11_XUAN_5.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.SHANG_HAI_11_XUAN_5, info);
			
		}
	}
	
	/**
	 * 3
	 * 
	* @Title: grabAnhui11xuan5_DataInitQuartzJob 
	* @Description: 安徽11选 5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabAnhui11xuan5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.AN_HUI_11_XUAN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.AN_HUI_11_XUAN_5.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.AN_HUI_11_XUAN_5, info);
			
		}
	}
	
	/**
	 * 4
	 * 
	* @Title: grabJiangxi11xuan5_DataInitQuartzJob 
	* @Description: 江西11选 5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabJiangxi11xuan5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.JIANG_XI_11_XUAN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.JIANG_XI_11_XUAN_5.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.JIANG_XI_11_XUAN_5, info);
			
		}
	}
	
	/**
	 * 5
	 * 
	* @Title: grabShanghai11xuan5_DataInitQuartzJob 
	* @Description: 吉林11选 5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabJilin11xuan5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.JI_LIN_11_XUAN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.JI_LIN_11_XUAN_5.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.JI_LIN_11_XUAN_5, info);
			
		}
	}
	
	/**
	 * 6
	 * 
	* @Title: grabShanghai11xuan5_DataInitQuartzJob 
	* @Description: 广西11选 5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabGuangxi11xuan5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.GUANG_XI_11_XUAN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.GUANG_XI_11_XUAN_5.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.GUANG_XI_11_XUAN_5, info);
			
		}
	}
	
	/**
	 * 7
	 * 
	* @Title: grabShanghai11xuan5_DataInitQuartzJob 
	* @Description: 湖北11选 5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabHubei11xuan5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.HU_BEI_11_XUAN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.HU_BEI_11_XUAN_5.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.HU_BEI_11_XUAN_5, info);
			
		}
	}
	
	/**
	 * 8
	 * 
	* @Title: grabShanghai11xuan5_DataInitQuartzJob 
	* @Description: 辽宁11选 5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabLiaoning11xuan5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.LIAO_NING_11_XUAN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.LIAO_NING_11_XUAN_5.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.LIAO_NING_11_XUAN_5, info);
			
		}
	}
	
	/**
	 * 9
	 * 
	* @Title: grabShanghai11xuan5_DataInitQuartzJob 
	* @Description: 江苏11选 5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabJiangsu11xuan5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.JIANG_SU_11_XUAN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.JIANG_SU_11_XUAN_5.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.JIANG_SU_11_XUAN_5, info);
			
		}
	}
	
	/**
	 * 10
	 * 
	* @Title: grabShanghai11xuan5_DataInitQuartzJob 
	* @Description: 浙江11选 5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabZhejiang11xuan5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.ZHE_JIANG_11_XUAN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.ZHE_JIANG_11_XUAN_5.getType()));

		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.ZHE_JIANG_11_XUAN_5, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.ZHE_JIANG_11_XUAN_5.getType());
		}
	}
	
	/**
	 * 11
	 * 
	* @Title: grabZhejiang11xuan5_DataInitQuartzJob 
	* @Description: 内蒙古11选5 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabNeimenggu11xuan5_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.NEI_MENG_GU_11_XUAN_5.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.NEI_MENG_GU_11_XUAN_5.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.NEI_MENG_GU_11_XUAN_5, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.NEI_MENG_GU_11_XUAN_5.getType());
		}
	}
	
	
	/****************  快乐3部分    ******************/
	
	/**
	 * 12
	 * 
	* @Title: grabZhejiang11xuan5_DataInitQuartzJob 
	* @Description: 江苏快3 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabJiangsukuai3_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.JIANG_SU_KUAI_3.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.JIANG_SU_KUAI_3.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.JIANG_SU_KUAI_3, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.JIANG_SU_KUAI_3.getType());
		}
	}
	
	/**
	 * 13
	 * 
	* @Title: grabGuangxikuai3_DataInitQuartzJob 
	* @Description: 广西快3 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabGuangxikuai3_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.GUANG_XI_KUAI_3.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.GUANG_XI_KUAI_3.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.GUANG_XI_KUAI_3, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.GUANG_XI_KUAI_3.getType());
		}
	}
	
	/**
	 * 14
	 * 
	* @Title: grabJilinkuai3_DataInitQuartzJob 
	* @Description:吉林快3 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabJilinkuai3_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.JI_LIN_KUAI_3.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.JI_LIN_KUAI_3.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.JI_LIN_KUAI_3, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.JI_LIN_KUAI_3.getType());
		}
	}
	
	/**
	 * 15
	 * 
	* @Title: grabHebeikuai3_DataInitQuartzJob 
	* @Description: 河北快3 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabHebeikuai3_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.HE_BEI_KUAI_3.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.HE_BEI_KUAI_3.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.HE_BEI_KUAI_3, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.HE_BEI_KUAI_3.getType());
		}
	}
	
	/**
	 * 16
	 * 
	* @Title: grabAnhuikuai3_DataInitQuartzJob 
	* @Description: 安徽快3 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabAnhuikuai3_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.AN_HUI_KUAI_3.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.AN_HUI_KUAI_3.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.AN_HUI_KUAI_3, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.AN_HUI_KUAI_3.getType());
		}
	}
	
	/**
	 * 17
	 * 
	* @Title: grabNeimenggukuai3_DataInitQuartzJob 
	* @Description: 内蒙古快3 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabNeimenggukuai3_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.NEI_MENG_GU_KUAI_3.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.NEI_MENG_GU_KUAI_3.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.NEI_MENG_GU_KUAI_3, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.NEI_MENG_GU_KUAI_3.getType());
		}
	}
	
	/**
	 * 18
	 * 
	* @Title: grabFujiankuai3_DataInitQuartzJob 
	* @Description: 河北快3 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabFujiankuai3_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.FU_JIAN_KUAI_3.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.FU_JIAN_KUAI_3.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.FU_JIAN_KUAI_3, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.FU_JIAN_KUAI_3.getType());
		}
	}
	
	/**
	 * 19
	 * 
	* @Title: grabHubeikuai3_DataInitQuartzJob 
	* @Description: 湖北快3 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabHubeikuai3_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.HU_BEI_KUAI_3.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.HU_BEI_KUAI_3.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.HU_BEI_KUAI_3, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.HU_BEI_KUAI_3.getType());
		}
	}
	
	/**
	 * 20
	 * 
	* @Title: grabBeijingkuai3_DataInitQuartzJob 
	* @Description: 北京快3 
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabBeijingkuai3_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.BEI_JING_KUAI_3.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.BEI_JING_KUAI_3.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.BEI_JING_KUAI_3, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.BEI_JING_KUAI_3.getType());
		}
	}
	
	/**
	 * 21
	 * 
	* @Title: grabBeijingkuaile8_DataInitQuartzJob 
	* @Description: 北京快乐8
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabBeijingkuaile8_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.BEI_JING_KUAI_LE_8.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.BEI_JING_KUAI_LE_8.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.BEI_JING_KUAI_LE_8, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.BEI_JING_KUAI_LE_8.getType());
		}
	}
	
	/**
	 * 22
	 * 
	* @Title: grabTianjingkuaile10fen_DataInitQuartzJob 
	* @Description: 天津快乐10
	* @param     设定文件 
	* @return void    返回类型 
	* @throws
	 */
	void grabTianjingkuaile10fen_DataInitQuartzJob() {
		
		String executeKey =QuartzHandlerType.TIAN_JIN_KUAI_LE_10FEN.getType() ;
		
		List<Quartz> quartzs = quartzService.queryQuartzByUkey(executeKey,Collections.singletonList(QuartzHandlerType.TIAN_JIN_KUAI_LE_10FEN.getType()));
		
		if (!AssertValue.isNotNullAndNotEmpty(quartzs)) {
			QuartzParamInfo info = new QuartzParamInfo();
			info.setExecuteKey(executeKey);
			info.setParamObj(Maps.newConcurrentMap());
			QuartzHandlerFactory factory = SpringUtils.getBean(QuartzHandlerFactory.class);
			factory.executeHandler(QuartzHandlerType.TIAN_JIN_KUAI_LE_10FEN, info);
			
			logger.debug("初始化定时任务记录："+QuartzHandlerType.TIAN_JIN_KUAI_LE_10FEN.getType());
		}
	}

}
