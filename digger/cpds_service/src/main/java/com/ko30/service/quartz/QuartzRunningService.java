package com.ko30.service.quartz;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.ko30.common.base.entity.search.SearchOperator;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.service.impl.BaseService;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.BeanUtils;
import com.ko30.common.util.CommUtil;
import com.ko30.entity.common.PageInfo;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.entity.model.po.quartz.QuartzRunning;
import com.ko30.entity.model.vo.quartz.QuartzRunningVo;
import com.ko30.facade.quartz.QuartzRunningFacadeService;

/**
 * 
* @ClassName: QuartzRunningService 
* @Description: 运行任务记录服务实现 
* @author A18ccms a18ccms_gmail_com 
* @date 2016年9月27日 下午5:05:12 
*
 */
@Service
@Transactional(rollbackFor = { Exception.class, RuntimeException.class, BusinessException.class })
public class QuartzRunningService extends BaseService<QuartzRunning, Long> implements QuartzRunningFacadeService {

	/**
	 * 按条件查询列表
	 */
	@Override
	public List<QuartzRunning> queryForListByConditions(QuartzRunningVo param) {

		Searchable search = Searchable.newSearchable();
		if (AssertValue.isNotNullAndNotEmpty(param.getDescr())) {
			search.addSearchFilter("descr", SearchOperator.like, "%" + param.getDescr() + "%");
		}
		if (AssertValue.isNotNullAndNotEmpty(param.getType())) {
			search.addSearchFilter("type", SearchOperator.eq, param.getType());
		}
		if (AssertValue.isNotNullAndNotEmpty(param.getState())) {
			search.addSearchFilter("state", SearchOperator.eq, param.getState());
		}
		List<QuartzRunning> quartzRunnings = this.findAllWithSort(search);

		return quartzRunnings;
	}

	/**
	 * 按条件查询分页
	 */
	@Override
	public Page<QuartzRunning> queryForPageByConditions(QuartzRunningVo param, PageInfo pageInfo) {

		Searchable search = Searchable.newSearchable();
		if (AssertValue.isNotNullAndNotEmpty(param.getDescr())) {
			search.addSearchFilter("descr", SearchOperator.like, "%" + param.getDescr() + "%");
		}
		if (AssertValue.isNotNullAndNotEmpty(param.getType())) {
			search.addSearchFilter("type", SearchOperator.eq, param.getType());
		}
		if (AssertValue.isNotNullAndNotEmpty(param.getState())) {
			search.addSearchFilter("state", SearchOperator.eq, param.getState());
		}

		// 获取分页信息
		int pageNo = CommUtil.getDefault(pageInfo.getCurrPage(), 0);
		int pageSize = CommUtil.getDefault(pageInfo.getPageSize(), 1000);
		search.setPage(pageNo, pageSize);
		Page<QuartzRunning> page = this.findAll(search);
		return page;
	}

	/**
	 * 保存编辑实体
	 */
	@Override
	public QuartzRunningVo edit(QuartzRunningVo param) {

		Long quartzRunningId = param.getId();
		QuartzRunning quartzRunning = this.findOne(quartzRunningId);
		if (AssertValue.isNotNull(quartzRunning)) {
			BeanUtils.copy(param, quartzRunning);
			Map<String, Object> paramMap = param.getParamsMap();
			if (AssertValue.isNotNullAndNotEmpty(paramMap)) {
				// 将所得到的map转成json串
				quartzRunning.setParams(JSONObject.toJSONString(paramMap));
			}
			this.saveObj(quartzRunning);
		} else {
			throw new BusinessException("要修改的记录不存在");
		}

		BeanUtils.copy(quartzRunning, param);
		return param;
	}
	
}
