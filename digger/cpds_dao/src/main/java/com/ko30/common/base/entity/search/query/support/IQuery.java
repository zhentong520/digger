package com.ko30.common.base.entity.search.query.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract interface IQuery<M, ID extends Serializable> extends Serializable{
	public abstract int getRows(String paramString);

	public abstract List getResult(String paramString);

	public abstract void setFirstResult(int paramInt);

	public abstract void setMaxResults(int paramInt);

	public abstract void setParaValues(Map paramMap);
	
	public abstract Map getParaValues();

	public abstract List getResult(String paramString, int paramInt1,
			int paramInt2);
}