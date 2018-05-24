package com.ko30.common.base.entity.search.query;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.ko30.common.base.entity.search.query.support.IQueryObject;
import com.ko30.common.base.entity.search.virtaul.SysMap;

public class QueryObject implements IQueryObject {
	protected Integer pageSize = Integer.valueOf(12);

	protected Integer currentPage = Integer.valueOf(0);
	protected String orderBy;
	protected String orderType;
	protected Map params = new HashMap();

	protected String queryString = "1=1";

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	protected void setParams(Map params) {
		this.params = params;
	}

	public String getOrderType() {
		return this.orderType;
	}

	public Integer getCurrentPage() {
		if (this.currentPage == null) {
			this.currentPage = Integer.valueOf(-1);
		}
		return this.currentPage;
	}

	public String getOrder() {
		return this.orderType;
	}

	public String getOrderBy() {
		return this.orderBy;
	}

	public Integer getPageSize() {
		if (this.pageSize == null) {
			this.pageSize = Integer.valueOf(-1);
		}
		return this.pageSize;
	}

	public QueryObject() {
	}


	public PageObject getPageObj() {
		PageObject pageObj = new PageObject();
		pageObj.setCurrentPage(getCurrentPage());
		pageObj.setPageSize(getPageSize());
		if ((this.currentPage == null) || (this.currentPage.intValue() <= 0)) {
			pageObj.setCurrentPage(Integer.valueOf(1));
		}
		return pageObj;
	}

	public String getQuery() {
		customizeQuery();
		return this.queryString + orderString();
	}

	protected String orderString() {
		String orderString = " ";
		if ((getOrderBy() != null) && (!"".equals(getOrderBy()))) {
			orderString = orderString + " order by obj." + getOrderBy();
		}
		if ((getOrderType() != null) && (!"".equals(getOrderType()))) {
			orderString = orderString + " " + getOrderType();
		}
		return orderString;
	}

	public Map getParameters() {
		return this.params;
	}

	@SuppressWarnings("unchecked")
	public IQueryObject addQuery(String field, SysMap para, String expression) {
		if ((field != null) && (para != null)) {
			this.queryString = (this.queryString + " and " + field + " "
					+ handleExpression(expression) + ":" + para.getKey()
					.toString());
			this.params.put(para.getKey(), para.getValue());
		}
		return this;
	}

	public IQueryObject addQuery(String field, SysMap para, String expression,
			String logic) {
		if ((field != null) && (para != null)) {
			this.queryString = (this.queryString + " " + logic + " " + field
					+ " " + handleExpression(expression) + ":" + para.getKey()
					.toString());
			this.params.put(para.getKey(), para.getValue());
		}
		return this;
	}

	public IQueryObject addQuery(String scope, Map paras) {
		if (scope != null) {
			if ((scope.trim().indexOf("and") == 0)
					|| (scope.trim().indexOf("or") == 0))
				this.queryString = (this.queryString + " " + scope);
			else
				this.queryString = (this.queryString + " and " + scope);
			if ((paras != null) && (paras.size() > 0)) {
				for (Iterator localIterator = paras.keySet().iterator(); localIterator
						.hasNext();) {
					Object key = localIterator.next();
					this.params.put(key, paras.get(key));
				}
			}
		}
		return this;
	}

	public IQueryObject addQuery(String sql) {
		this.queryString = this.queryString + sql;
		return this;
	}

	
	public IQueryObject addQuery(String para, Object obj, String field,
			String expression) {
		if ((field != null) && (para != null)) {
			this.queryString = (this.queryString + " and :" + para + " "
					+ expression + " " + field);
			this.params.put(para, obj);
		}
		return this;
	}

	public IQueryObject addQuery(String para, Object obj, String field,
			String expression, String logic) {
		if ((field != null) && (para != null)) {
			this.queryString = (this.queryString + " " + logic + " :" + para
					+ " " + expression + " " + field);
			this.params.put(para, obj);
		}
		return this;
	}

	private String handleExpression(String expression) {
		if (expression == null) {
			return "=";
		}
		return expression;
	}

	public void customizeQuery() {
	}
}