package com.ko30.entity.common;

/**
 * 请求响应体
 * @author carr
 *
 */
public class JsonResponse {

	private Integer status;

	private String msg;

	protected Object data;

	public JsonResponse() {
		this.status = JsonStatus.SUCCESS;
	}

	public JsonResponse(Integer status) {
		this.status = status;
	}

	public JsonResponse(int status) {
		this.status = status;
	}

	public JsonResponse(String status, String msg) {
		this.status = Integer.parseInt(status);
		this.msg = msg;
	}

	public JsonResponse(Integer status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
