package com.ko30.facade.dao.impl.lotteryInfo;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;

public class IHistoryStatisticsRepositoryImpl {
	
	@PersistenceContext
	private EntityManager em;
	private Logger logger=Logger.getLogger(IHistoryStatisticsRepositoryImpl.class);
	
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> queryPreData(String lotCode,String issue){
		
		StringBuilder querySb=new StringBuilder();
		querySb.append("select lot_code lotCode,draw_code drawCode,draw_issue drawIssue,sub_draw_issue subDrawIssue,draw_time drawTime,missing_data missingData from tb_history_statistics where ");
		querySb.append("lot_code='").append(lotCode).append("'");
		querySb.append(" order by draw_time DESC limit 0,1 ");
//		querySb.append(" and ");
//		querySb.append("sub_draw_issue='").append(issue).append("'");
		logger.info("将执行sql："+querySb.toString());
		Query query=em.createNativeQuery(querySb.toString());
		 query.unwrap(SQLQuery.class)
        .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<Map<String,String>> queryNumList(String lotCode){
		StringBuilder querySb=new StringBuilder();
		querySb.append(" select lot_code lotCode,draw_code drawCode,draw_issue drawIssue,sub_draw_issue subDrawIssue,draw_time drawTime,missing_data missingData from tb_history_statistics where ");
		querySb.append(" lot_code=").append(lotCode);
		querySb.append(" order by draw_time DESC limit 0,100  ");
		logger.info("将执行sql："+querySb.toString());
		Query query=em.createNativeQuery(querySb.toString());
		 query.unwrap(SQLQuery.class)
         .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}

}
