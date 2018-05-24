package com.ko30.facade.dao.impl.quartz;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ko30.common.util.AssertValue;
import com.ko30.common.util.CommUtil;
import com.ko30.constant.enums.quartz.QuartzStatus;
import com.ko30.entity.model.po.quartz.Quartz;


public class QuartzRepositoryImpl {
	
	@PersistenceContext
    private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Quartz> queryTopOfUnCompleteForUpdate(int nums) {
//		String sql = "select q.* from t_quartz q where q.state='none' "
//				+ "and q.activeTime<='"+
//				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"' "
//				+ " order by q.activeTime asc limit "+nums;
		
		String currentTimeStr=CommUtil.formatLongDate(new Date());
		StringBuilder querySb=new StringBuilder();
		querySb.append("select q.* from t_quartz q where q.state='none' ");
		querySb.append("and q.active_time<= '"+currentTimeStr+"' ");
		querySb.append("order by q.active_time asc limit "+nums);
		querySb.append(" ");
		
		Query query = em.createNativeQuery(querySb.toString(), Quartz.class);
		LockModeType type=LockModeType.PESSIMISTIC_WRITE;
//		query.setLockMode(type);
//		List<Quartz> list=query.setLockMode(LockModeType.PESSIMISTIC_READ).getResultList();
		List<Quartz> list = query.getResultList();
		Iterator<Quartz> it = list.iterator();
		// 悲观锁，将对象锁住，待事务提交 ，其它操作才能继续
		while (it.hasNext()) {
			Quartz q = it.next();
			em.lock(q, type);
		}
		
		return list;
	}
	
	
	public void updateInvalidRecordToComplete(String type, String ukey) {
		
		if (AssertValue.isNotNullAndNotEmpty(type)
				&& AssertValue.isNotNullAndNotEmpty(ukey)) {
			StringBuffer sql = new StringBuffer();
			sql.append("update Quartz q set q.state=' ");
			sql.append(QuartzStatus.COMPLETE.getValue()).append("' ");
			sql.append("where q.ukey='").append(ukey).append("' ");
			sql.append("and q.type='").append(type).append("' ");
			sql.append("and q.state='none'");
			em.createQuery(sql.toString()).executeUpdate();
		}
	}
	
	/**
	 * 
	 * @Title: deleteAll
	 * @Description: 清空当前表
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void deleteAll(){
		String delSql="delete from Quartz";
		em.createQuery(delSql).executeUpdate();
	}
}
