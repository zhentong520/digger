package com.ko30.common.base.entity.search.query;

import java.io.Serializable;

import com.ko30.common.base.entity.search.query.support.IQuery;

public class GenericPageList<M, ID extends Serializable> extends PageList {
	private static final long serialVersionUID = 6730593239674387757L;
	protected String scope;
	protected String clsName;

	public GenericPageList(){
	}
	
	public GenericPageList(String clsName,
			String scope, IQuery query) {
		this.clsName = clsName;
		this.scope = scope;
		setQuery(query);
	}

	public void doList(int currentPage, int pageSize) {
		String totalSql = "select COUNT(obj) from " + this.clsName
				+ " obj where " + this.scope;
		super.doList(pageSize, currentPage, totalSql, this.scope);
	}
}