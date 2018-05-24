package com.ko30.entity.common.exception;

public class BusinessException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3168514020411113108L;

	private String errCode;

	public BusinessException() {
	}

	public BusinessException(String msg) {
		super(msg);
		this.errCode = "500";
	}

	public BusinessException(String errCode, String msg) {
		super(msg);
		this.errCode = errCode;
	}

	public BusinessException(String msg, Throwable th) {
		super(msg, th);
	}

	public BusinessException(String errCode, String msg, Throwable th) {
		super(msg, th);
		this.errCode = errCode;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

}
