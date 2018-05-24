package com.ko30.service.quartz;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ko30.common.base.entity.search.SearchOperator;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.service.impl.BaseService;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.BeanUtils;
import com.ko30.constant.enums.quartz.QuartzStatus;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.entity.model.po.quartz.Quartz;
import com.ko30.entity.model.vo.quartz.QuartzVo;
import com.ko30.facade.dao.quartz.QuartzRepository;
import com.ko30.facade.quartz.QuartzFacadeService;

@Service
@Transactional(rollbackFor={Exception.class,RuntimeException.class,BusinessException.class})
public class QuartzService extends BaseService<Quartz, Long> implements QuartzFacadeService{

	@Override
	public Quartz addQuarz(QuartzVo qv) {
		Quartz q = new Quartz();
		if(AssertValue.isNotNull(qv)) {
			qv.setCreateDate(new Date());
			// MQMessage对象转换为String
//			JSONObject paramsObj = JSONObject.fromObject(qv.getInfo());
			String info=JSONObject.toJSONString(qv.getInfo());
			
			qv.setParams(info);
			if(AssertValue.isNotNull(qv.getInfo())) {
				qv.setUkey(qv.getInfo().getExecuteKey());
			}
			
			BeanUtils.copy(qv, q);
			this.save(q);
		}
		return q;
	}
	
	private QuartzRepository getRepository() {
		return (QuartzRepository) this.repository;
	}

	@Override
	public List<Quartz> queryTopOfUnCompleteForUpdate(int nums) {
		return getRepository().queryTopOfUnCompleteForUpdate(nums);
	}
	
	
	public List<Quartz> queryQuartzByUkey(String uKey, List<String> t) {
		List<Quartz> quartzList = Lists.newArrayList();
		if(AssertValue.isNotNullAndNotEmpty(t)) {
			for(String type : t) {
				Searchable searchable = Searchable.newSearchable();
				searchable.addSearchFilter("ukey", SearchOperator.eq, uKey);
				searchable.addSearchFilter("type", SearchOperator.eq, type);
				searchable.addSearchFilter("state", SearchOperator.eq, QuartzStatus.NONE.getValue());
				searchable.addSort(Direction.DESC, "createTime");
				
				Page<Quartz> qPage = this.findAll(searchable);
				if(qPage.getNumberOfElements() > 0) {
					List<Quartz> qList = qPage.getContent();
					if(AssertValue.isNotNullAndNotEmpty(qList)) {
						quartzList.add(qList.get(0));
					}
				}
			}
		}
		return quartzList;
	}
	
	public Boolean isHaveQuartz(Quartz q) {
		Searchable searchable = Searchable.newSearchable();
		if (AssertValue.isNotNull(q.getUkey())) {
			searchable.addSearchFilter("ukey", SearchOperator.eq, q.getUkey());
		}
		if (AssertValue.isNotNull(q.getType())) {
			searchable.addSearchFilter("type", SearchOperator.eq, q.getType());
		}
		Long count = this.count(searchable);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 避免重复添加不必要的定时任务，再添加前将无效的定时任务置为以完成
	 * @param ukey
	 * @param type
	 */
	public void updateInvalidRecordToComplete(String ukey, String type) {
		Quartz quarz = new Quartz();
		quarz.setUkey(ukey);
		quarz.setType(type);
		if(this.isHaveQuartz(quarz)) {
			this.getRepository().updateInvalidRecordToComplete(type, ukey);
		}
	}

	/**
	 * 清空所有记录
	 */
	@Override
	public void deleteAll() {
		this.getRepository().deleteAll();
	}

}
