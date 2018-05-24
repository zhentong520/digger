package com.ko30.common.util;

import java.util.List;

/**
 * 
 * 封装查询结构的对象 resultList 结构集对象 totalRecord 总记录数
 * @author lmk 
 */
@SuppressWarnings("all")
public class QueryResult<T> {

	/**
	 * 查询结果集
	 */
	private List resultList;

	/**
	 * 总记录数
	 */
	private Long totalRecord;

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public Long getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Long totalRecord) {
		this.totalRecord = totalRecord;
	}
}
