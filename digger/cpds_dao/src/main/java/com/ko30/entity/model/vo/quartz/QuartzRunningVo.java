package com.ko30.entity.model.vo.quartz;

import java.util.Map;

public class QuartzRunningVo {

	private Long id;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 所运行的类名
	 */
	private String className;
	
	/**
	 * 描述
	 */
	private String descr;
	
	/**
	 * 参数
	 */
	private String params;
	
	/**
	 * 状态
	 */
	private String state;
	
	/**
	 * 参数map
	 */
	private Map<String, Object> paramsMap;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Map<String, Object> getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}
	
}
