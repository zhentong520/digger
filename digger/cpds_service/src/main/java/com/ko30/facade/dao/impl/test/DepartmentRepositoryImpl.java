package com.ko30.facade.dao.impl.test;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Maps;
import com.ko30.common.util.BeanUtils;
import com.ko30.entity.common.JsonPageResponse;
import com.ko30.entity.common.JsonStatus;
import com.ko30.entity.common.PageInfo;

/**
 * 部分持久仓库实现
 * @author carr
 *
 */
public class DepartmentRepositoryImpl {

	@PersistenceContext
	private EntityManager em;
	
	public JsonPageResponse<Map<String, Object>> query4Page(PageInfo pageInfo){
		
		// 获取总记录数
		StringBuilder countQueryCount=new StringBuilder();
		countQueryCount.append("SELECT count(1) from sssp_employee e,  ");
		countQueryCount.append("sssp_department d where e.department_id=d.id	    ");
		Object count=em.createNativeQuery(countQueryCount.toString()).getSingleResult();
//		Long count = (Long) em.createNativeQuery(countQueryCount.toString()).getSingleResult();
		
		// 获取分页记录
//		SELECT e.*,d.department_name from sssp_employee e,sssp_department d where e.department_id=d.id
		StringBuilder pageQuerySb=new StringBuilder();
		pageQuerySb.append("SELECT e.*,d.department_name from sssp_employee e,  ");
		pageQuerySb.append("sssp_department d where e.department_id=d.id	    ");
		pageQuerySb.append("limit "+pageInfo.getCurrPage()*pageInfo.getPageSize()+", "+pageInfo.getPageSize());
		@SuppressWarnings("unchecked")
		List<Object> dataObjs=em.createNativeQuery(pageQuerySb.toString()).getResultList();
		
		JsonPageResponse<Map<String, Object>> page=new JsonPageResponse<Map<String, Object>>();
		BeanUtils.copy(pageInfo, page);
		
		page.setData(JSONArray.toJSON(dataObjs));
		page.setTotal(((BigInteger)count).intValue());
		page.setMsg("操作成功");
		page.setStatus(JsonStatus.SUCCESS);
		return page;
	}
}
