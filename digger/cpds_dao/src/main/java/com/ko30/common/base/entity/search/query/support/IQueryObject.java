package com.ko30.common.base.entity.search.query.support;

import java.util.Map;

import com.ko30.common.base.entity.search.query.PageObject;
import com.ko30.common.base.entity.search.virtaul.SysMap;

public abstract interface IQueryObject {
	public abstract String getQuery();

	public abstract Map getParameters();

	public abstract PageObject getPageObj();

	public abstract IQueryObject addQuery(String paramString, Map paramMap);

	public abstract IQueryObject addQuery(String paramString1,
			SysMap paramSysMap, String paramString2);

	public abstract IQueryObject addQuery(String paramString1,
			Object paramObject, String paramString2, String paramString3);
}