package com.ko30.facade.dao.impl.lotteryInfo;

import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.apache.log4j.Logger;
import org.hibernate.transform.Transformers;


public class RecommendExpertRepositoryImpl {

	@PersistenceContext
	private EntityManager em;
	private Logger logger=Logger.getLogger(RecommendExpertRepositoryImpl.class);
	
	public void removeRecommendExpert(String lotCode,String planCode){
		
		StringBuilder querySb=new StringBuilder();
		querySb.append("delete from RecommendExpert where ");
		querySb.append("lotCode='").append(lotCode).append("'");
		querySb.append(" and ");
		querySb.append("planCode='").append(planCode).append("'");
		logger.info("将执行sql："+querySb.toString());
		Query query=em.createQuery(querySb.toString());
		query.executeUpdate();
	}
	
	public List<Map<String,String>> getExpertID(String lotCode,String planCode,String seatseq){
		StringBuilder querySb=new StringBuilder();
		querySb.append(" select c.exptId exptId,a.lotName lotName,a.lotShortName lotShortName from tblot a,tblotdvnplan b,tblotdvnplanexpt c where ");
		querySb.append(" a.lotNumbers=").append(lotCode);
		querySb.append(" and a.lotId=b.lotId  ");
		querySb.append(" and b.planCode='").append(planCode).append("'");
		querySb.append(" and b.planId=c.planId  ");
		querySb.append(" and c.seatSeq=").append(seatseq);
		logger.info("将执行sql："+querySb.toString());
		Query query=em.createNativeQuery(querySb.toString());
		 query.unwrap(org.hibernate.SQLQuery.class)
         .setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		return query.getResultList();
	}
}
