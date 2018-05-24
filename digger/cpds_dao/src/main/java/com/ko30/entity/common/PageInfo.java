package com.ko30.entity.common;

import java.io.Serializable;

public class PageInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int currPage; // 起始page：0

	private int pageSize;

	public PageInfo() {
	}

	public PageInfo(int currPage, int pageSize) {
		this.currPage = currPage;
		this.pageSize = pageSize;
	}

	public int getCurrPage() {
		return currPage;
	}

	public void setCurrPage(int currPage) {
		this.currPage = currPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
