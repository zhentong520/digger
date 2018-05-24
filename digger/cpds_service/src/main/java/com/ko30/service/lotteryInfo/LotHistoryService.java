package com.ko30.service.lotteryInfo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ko30.common.base.entity.search.SearchOperator;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.base.entity.search.filter.SearchFilter;
import com.ko30.common.base.entity.search.filter.SearchFilterHelper;
import com.ko30.common.service.impl.BaseService;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.BeanUtils;
import com.ko30.common.util.CommUtil;
import com.ko30.entity.common.JsonPageResponse;
import com.ko30.entity.common.JsonStatus;
import com.ko30.entity.common.PageInfo;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.vo.winningInfo.LotHistoryVo;
import com.ko30.facade.dao.lotteryInfo.LotHistoryRepository;
import com.ko30.facade.lotteryInfo.LotHistoryFacadeService;


/**
 * 
* @ClassName: LotHistoryService 
* @Description: 开奖记录服务接口实现
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月24日 下午4:47:31 
*
 */
@Service
@Transactional(rollbackFor = { Exception.class, RuntimeException.class, BusinessException.class })
public class LotHistoryService extends BaseService<AppLotHistory, Long>
		implements LotHistoryFacadeService {

	private LotHistoryRepository getRepository(){
		return (LotHistoryRepository)super.repository;
	}

	public void init() {
		super.init();
	}
	
	/**
	 * 新增实体
	 */
	@Override
	public AppLotHistory saveEntity(AppLotHistory entity) {
		entity.setCreateTime(new Date());
		return this.saveAndFlush(entity);
	}

	/**
	 * 按彩票类型code获取彩票历史列表
	 */
	@Override
	public List<LotHistoryVo> queryByLotGroupCode(LotHistoryVo queryParam) {
		return this.getRepository().queryByLotGroupCode(queryParam);
	}

	
	/**
	 * 按条件分页获取开奖记录
	 */
	@Override
	public JsonPageResponse<LotHistoryVo> query4Page(LotHistoryVo queryParam,PageInfo pageInfo) {
		
		Searchable search=Searchable.newSearchable();
		if (AssertValue.isNotNull(queryParam.getLotGroupCode())) {
			search.addSearchFilter("lotGroupCode", SearchOperator.eq, queryParam.getLotGroupCode());
		}
		if (AssertValue.isNotNull(queryParam.getLotCode())) {
			search.addSearchFilter("lotCode", SearchOperator.eq, queryParam.getLotCode());	
		}
		if (AssertValue.isNotNull(queryParam.getLotAlias())) {
			search.addSearchFilter("lotAlias", SearchOperator.eq, queryParam.getLotAlias());	
		}
		
		// 获取分页信息
		int pageNo = CommUtil.getDefault(pageInfo.getCurrPage(), 0);
		int pageSize = CommUtil.getDefault(pageInfo.getPageSize(), 100);
		search.setPage(pageNo, pageSize);
		search.addSort(Direction.DESC, "preDrawTime");
		
		Page<AppLotHistory> page = this.findAll(search);
		
		// 转换结果实体类型
		JsonPageResponse<LotHistoryVo> voPage = new JsonPageResponse<LotHistoryVo>();
		if (AssertValue.isNotNullAndNotEmpty(page.getContent())) {
			List<LotHistoryVo> voList = Lists.newArrayList();
			for (AppLotHistory appLotHistory : page) {
				LotHistoryVo vo = new LotHistoryVo();
				BeanUtils.copy(appLotHistory, vo);
				Long countDown=0L;
				if (AssertValue.isNotNull(vo.getDrawTime()) && vo.getDrawTime().before(new Date())) {
					countDown=vo.getDrawTime().getTime()-System.currentTimeMillis();
				}
				vo.setCountDown(countDown);
				voList.add(vo);
			}
			voPage.setData(voList);
		}
		voPage.setCurrPage(pageNo);
		voPage.setPageSize(pageSize);
		voPage.setStatus(JsonStatus.SUCCESS);
		voPage.setTotal(page.getTotalElements());
		return voPage;
	}

	
	/**
	 * 检查对象是否存在
	 */
	@Override
	public boolean checkisExist(AppLotHistory lot) {

		Searchable search = Searchable.newSearchable();
		if (AssertValue.isNotNull(lot.getLotCode())) {
			search.addSearchFilter("lotCode", SearchOperator.eq,lot.getLotCode());
		}
		if (AssertValue.isNotNullAndNotEmpty(lot.getPreDrawIssue())) {
			//search.addSearchFilter("preDrawIssue", SearchOperator.eq,lot.getPreDrawIssue());
			// 对比最后三位或者更少位
			String preDrawIssue=lot.getPreDrawIssue();
			SearchFilter filter_1=SearchFilterHelper.newCondition("preDrawIssue", SearchOperator.eq,preDrawIssue);
			
			// 得到当前年份
			Calendar cal=Calendar.getInstance();
			String currYear=cal.get(Calendar.YEAR)+"";
			String shortCurrYear=currYear.substring(0,2);// 得到当前年份
			SearchFilter filter_2=SearchFilterHelper.newCondition("preDrawIssue", SearchOperator.eq,shortCurrYear.concat(preDrawIssue));
			
			preDrawIssue=preDrawIssue.replace(currYear, currYear.substring(currYear.length()-2));
			SearchFilter filter_3=SearchFilterHelper.newCondition("preDrawIssue", SearchOperator.eq,currYear.concat(preDrawIssue));
			
			SearchFilter filter=SearchFilterHelper.or(filter_1,filter_2,filter_3);
			search.addSearchFilter(filter);
		}
		//if (AssertValue.isNotNull(lot.getPreDrawTime())) {
			//search.addSearchFilter("preDrawTime", SearchOperator.eq,lot.getPreDrawTime());// 开奖时间
		//}
		if (AssertValue.isNotNullAndNotEmpty(lot.getLotAlias())) {
			search.addSearchFilter("lotAlias", SearchOperator.eq,lot.getLotAlias());// 别名
		}
		
		Long count = this.count(search);
		return count.intValue() > 0;
	}

	
	/**
	 * 根据彩票类型码，获取最后一条开奖历史记录
	 */
	@Override
	public AppLotHistory getTheLastOneByLotCode(Integer lotCode) {

		Searchable search = Searchable.newSearchable();
		search.addSearchFilter("lotCode", SearchOperator.eq, lotCode);
		search.addSort(Direction.ASC, "preDrawTime");
		search.setPage(0, 1);// 只查询一条
		List<AppLotHistory> list = this.findAll(search).getContent();
		if (AssertValue.isNotNullAndNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 根据指定彩种，获取指定的最新条数
	 */
	@Override
	public List<AppLotHistory> getNewListByCountAndLotCode(Integer lotCode,
			Integer count,int calenderType) {

		/*
		Searchable search = Searchable.newSearchable();
		search.addSearchFilter("lotCode", SearchOperator.eq, lotCode);
		if (AssertValue.isNotNull(count)) {
			search.setPage(0, count.intValue());
		}else {// 没有条数条件时，查询今年
			Calendar cal = Calendar.getInstance();
			if (Calendar.YEAR==calenderType) {// 按年查询
				int currYear = cal.get(Calendar.YEAR);
				String currYearBeginStr = currYear + "-01" + "-01";// ****-01-01
				search.addSearchFilter("preDrawTime", SearchOperator.gte, CommUtil.formatDatePlus(currYearBeginStr));
				search.addSearchFilter("preDrawTime", SearchOperator.lte, new Date());// 小于等于当前时间
			}else if (Calendar.DATE==calenderType) {// 按日期查询
				String dataStr=CommUtil.formatShortDate(new Date());
				Date beginTime=CommUtil.formatDate(dataStr+"00:00:00", "yyyy-MM-dd HH:mm:ss");
				Date endTime=CommUtil.formatDate(dataStr+"23:59:59", "yyyy-MM-dd HH:mm:ss");
				search.addSearchFilter("preDrawTime", SearchOperator.gte, beginTime);
				search.addSearchFilter("preDrawTime", SearchOperator.lte, endTime);// 小于等于当前时间
			}
		}
		search.addSort(Direction.DESC, "preDrawTime");
		return this.findAll(search).getContent();
		*/
		return this.getRepository().getNewListByCountAndLotCode(lotCode, count, calenderType);
		
	}
	
}
