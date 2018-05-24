package com.ko30.facade.quartz;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ko30.common.service.BaseFacadeService;
import com.ko30.entity.common.PageInfo;
import com.ko30.entity.model.po.quartz.QuartzRunning;
import com.ko30.entity.model.vo.quartz.QuartzRunningVo;

/**
 * 
* @ClassName: QuartzRunningFacadeService 
* @Description: 任务运行记录服务 
* @author A18ccms a18ccms_gmail_com 
* @date 2016年9月27日 下午4:53:49 
*
 */
public interface QuartzRunningFacadeService extends BaseFacadeService<QuartzRunning, Long> {

	/**
	 * 
	* @Title: queryForListByConditions 
	* @Description: 按条件查询列表 
	* @param @param param
	* @param @return    设定文件 
	* @return List<QuartzRunning>    返回类型 
	* @throws
	 */
	List<QuartzRunning> queryForListByConditions(QuartzRunningVo param); 
	

	/**
	 * 
	* @Title: queryForPageByConditions 
	* @Description: 按条件查询分页
	* @param @param param
	* @param @param pageInfo
	* @param @return    设定文件 
	* @return Page<QuartzRunning>    返回类型 
	* @throws
	 */
	Page<QuartzRunning> queryForPageByConditions(QuartzRunningVo param,PageInfo pageInfo); 
	
	/**
	 * 
	* @Title: edit 
	* @Description: 编辑 
	* @param @param param
	* @param @return    设定文件 
	* @return QuartzRunningVo    返回类型 
	* @throws
	 */
	QuartzRunningVo edit(QuartzRunningVo param);
}
