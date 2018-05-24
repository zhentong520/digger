package com.ko30.facade.quartz;

import java.util.List;

import com.ko30.common.service.BaseFacadeService;
import com.ko30.entity.model.po.quartz.Quartz;
import com.ko30.entity.model.vo.quartz.QuartzVo;

public interface QuartzFacadeService extends BaseFacadeService<Quartz, Long>{
	
	Quartz addQuarz(QuartzVo qv);
	
	List<Quartz> queryTopOfUnCompleteForUpdate(int nums);
	
	List<Quartz> queryQuartzByUkey(String uKey, List<String> t);

	Boolean isHaveQuartz(Quartz q);
	
	/**
	 * 
	 * @Title: deleteAll
	 * @Description: 删除所有记录
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	void deleteAll();
}
