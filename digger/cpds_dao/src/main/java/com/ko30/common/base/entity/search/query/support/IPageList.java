package com.ko30.common.base.entity.search.query.support;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ko30.entity.common.exception.BusinessException;


@Service
@Transactional(rollbackFor={Exception.class,RuntimeException.class,BusinessException.class})
public abstract interface IPageList extends Serializable {
	public abstract List getResult();

	public abstract void setQuery(IQuery paramIQuery);

	public abstract int getPages();

	public abstract int getRowCount();

	public abstract int getCurrentPage();

	public abstract int getPageSize();

	public abstract void doList(int paramInt1, int paramInt2,
			String paramString1, String paramString2);

	public abstract void doList(int paramInt1, int paramInt2,
			String paramString1, String paramString2, Map paramMap);
}