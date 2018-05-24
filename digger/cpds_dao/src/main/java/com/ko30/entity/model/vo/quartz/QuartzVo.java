package com.ko30.entity.model.vo.quartz;

import java.util.Date;

import com.ko30.common.base.entity.quartz.QuartzParamInfo;

public class QuartzVo {
	private Long id;
	
	private String name;
	
	private String ukey;
	
	private String type;
	
	private String params;
	
	private Date createDate;
	
	private Date activeDate;
	
	private String state;
	
	private QuartzParamInfo info;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUkey() {
		return ukey;
	}

	public void setUkey(String ukey) {
		this.ukey = ukey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getActiveDate() {
		return activeDate;
	}

	public void setActiveDate(Date activeDate) {
		this.activeDate = activeDate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public QuartzParamInfo getInfo() {
		return info;
	}

	public void setInfo(QuartzParamInfo info) {
		this.info = info;
	}


}
