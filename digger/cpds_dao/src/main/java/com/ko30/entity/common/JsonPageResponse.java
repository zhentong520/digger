package com.ko30.entity.common;

import java.util.List;

public class JsonPageResponse<T> extends JsonResponse {


	private int currPage;

	private int pageSize;

	private long total;

	public JsonPageResponse() {
		this.setStatus(JsonStatus.SUCCESS);
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

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	@SuppressWarnings("unchecked")
	public List<T> getData() {
		return (List<T>) this.data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}



}
