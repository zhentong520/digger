package com.ko30.facade.dao.impl.lotteryInfo;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

import com.google.common.collect.Lists;
import com.ko30.common.util.AssertValue;
import com.ko30.common.util.BeanUtils;
import com.ko30.common.util.CommUtil;
import com.ko30.entity.model.po.winningInfo.AppLotHistory;
import com.ko30.entity.model.vo.winningInfo.LotHistoryVo;

/**
 * 
* @ClassName: LotHistoryRepositoryImpl 
* @Description: 彩票历史自定义持久仓库
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月24日 下午5:44:11 
*
 */
public class LotHistoryRepositoryImpl {

	
	@PersistenceContext
	private EntityManager em;
	
	private Logger logger=Logger.getLogger(LotHistoryRepositoryImpl.class);
	
	/**
	 * 
	* @Title: queryByLotCode 
	* @Description: 按彩票类型分组码获取彩票历史列表 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<LotHistoryVo>    返回类型 
	* @throws
	 */
	public List<LotHistoryVo> queryByLotGroupCode(LotHistoryVo queryParam){
		
		
		StringBuilder querySb=new StringBuilder();
		querySb.append("SELECT a.id, a.lot_group_code, a.lot_code, a.lot_alias,   ");
		querySb.append("a.pre_draw_code, a.draw_count, a.draw_issue, a.draw_time, ");
		querySb.append("a.pre_draw_time, a.pre_draw_date, a.pre_draw_issue,       ");
		querySb.append("a.sum_num, a.sum_big_small, a.sum_single_double,          ");
		querySb.append("a.frequency, a.lot_name, a.total_count, a.create_time,    ");
		querySb.append("a.update_time FROM app_lot_history AS a where 1=1         ");
		if (AssertValue.isNotNull(queryParam.getLotGroupCode())) {
			querySb.append("and a.lot_group_code=").append(queryParam.getLotGroupCode()).append(" ");
		}
		
		querySb.append("group by a.lot_code order by a.pre_draw_time desc         ");
		logger.info("将执行sql："+querySb.toString());
		@SuppressWarnings("unchecked")
		List<AppLotHistory> list=em.createNativeQuery(querySb.toString(),AppLotHistory.class).getResultList();
		List<LotHistoryVo> lotVoList=Lists.newArrayList();
		for (AppLotHistory lot : list) {
			LotHistoryVo lotVo=new LotHistoryVo();
			BeanUtils.copy(lot, lotVo);
			lotVoList.add(lotVo);
		}
		return lotVoList;
	}
	
	
	/**
	 * 根据指定彩种，获取指定的最新条数
	 */
	public List<AppLotHistory> getNewListByCountAndLotCode(Integer lotCode,Integer count,int calenderType){
	
//		select * from app_lot_history h 
//		where h.lot_code=10039 
//		and h.pre_draw_time>='2017-05-01'
//		group by h.pre_draw_issue ,h.pre_draw_time
//		order by h.pre_draw_time desc
//		limit 20
		
		
		StringBuilder querySb=new StringBuilder();
		querySb.append("select * from app_lot_history h ");
		querySb.append("where h.lot_code="+lotCode+"    ");
		// 时间条件
		if (!AssertValue.isNotNull(count)) {
			// 设置查询截止时间
			String dataStr = CommUtil.formatShortDate(new Date());
			String queryDateEndStr=dataStr+" 23:59:59";
			
			Calendar cal = Calendar.getInstance();
			if (Calendar.YEAR == calenderType) {// 按年查询
				int currYear = cal.get(Calendar.YEAR);
				String currYearBeginStr = currYear + "-01-01 00:00:00";// ****-01-01  起始为1月1日
				querySb.append("and h.pre_draw_time > STR_TO_DATE('"+currYearBeginStr+"','%Y-%m-%d %H:%i:%s') ");
				querySb.append("and h.pre_draw_time <= STR_TO_DATE('"+queryDateEndStr+"','%Y-%m-%d %H:%i:%s')");
			} else if (Calendar.DATE == calenderType) {// 按日期查询，当天。
				String queryDateBeginStr=dataStr+" 00:00:00";
				querySb.append("and h.pre_draw_time > STR_TO_DATE('"+queryDateBeginStr+"','%Y-%m-%d %H:%i:%s') ");
				querySb.append("and h.pre_draw_time <= STR_TO_DATE('"+queryDateEndStr+"','%Y-%m-%d %H:%i:%s')  ");
			}
		}
		querySb.append("group by h.pre_draw_issue ,h.pre_draw_time ");
		querySb.append("order by h.pre_draw_time desc    ");
		if (AssertValue.isNotNull(count)) {
			querySb.append("limit " + count + "  ");
		}
		List<AppLotHistory> lots=em.createNativeQuery(querySb.toString(), AppLotHistory.class).getResultList();
		
		return lots;
	}
}
