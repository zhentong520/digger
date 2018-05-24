package com.ko30.facade.dao.quartz;

import java.util.List;

import com.ko30.common.dao.BaseRepository;
import com.ko30.entity.model.po.quartz.Quartz;


public interface QuartzRepository extends BaseRepository<Quartz, Long>{

	List<Quartz> queryTopOfUnCompleteForUpdate(int nums);
	
	void updateInvalidRecordToComplete(String type, String ukey);
	
	/**
	 * 删除所有记录
	 */
	void deleteAll();
}
