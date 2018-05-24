package com.ko30.entity.model.vo.winningInfo;

import java.util.Date;

public class LotHistoryVo {
	

	private Long id;

	private Date createTime;

	private Integer drawCount;

	private String drawIssue;

	private Date drawTime;

	private String frequency;

	private String lotAlias;

	private Integer lotCode;

	private Integer lotGroupCode;

	private String lotName;

	private String preDrawCode;

	private Date preDrawDate;

	private String preDrawIssue;

	private Date preDrawTime;

	private Integer sumBigSmall;

	private Integer sumNum;

	private Integer sumSingleDouble;

	private Integer totalCount;

	private Date updateTime;
	
	
	/**** 服务器时间  ***/
	private Date serviceTime;
	
	/**** 倒计时 时间  ***/
	private Long countDown;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDrawCount() {
		return this.drawCount;
	}

	public void setDrawCount(Integer drawCount) {
		this.drawCount = drawCount;
	}

	public String getDrawIssue() {
		return this.drawIssue;
	}

	public void setDrawIssue(String drawIssue) {
		this.drawIssue = drawIssue;
	}

	public Date getDrawTime() {
		return this.drawTime;
	}

	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getLotAlias() {
		return this.lotAlias;
	}

	public void setLotAlias(String lotAlias) {
		this.lotAlias = lotAlias;
	}

	public Integer getLotCode() {
		return this.lotCode;
	}

	public void setLotCode(Integer lotCode) {
		this.lotCode = lotCode;
	}

	public Integer getLotGroupCode() {
		return this.lotGroupCode;
	}

	public void setLotGroupCode(Integer lotGroupCode) {
		this.lotGroupCode = lotGroupCode;
	}

	public String getLotName() {
		return this.lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public String getPreDrawCode() {
		return this.preDrawCode;
	}

	public void setPreDrawCode(String preDrawCode) {
		this.preDrawCode = preDrawCode;
	}

	public Date getPreDrawDate() {
		return this.preDrawDate;
	}

	public void setPreDrawDate(Date preDrawDate) {
		this.preDrawDate = preDrawDate;
	}

	public String getPreDrawIssue() {
		return this.preDrawIssue;
	}

	public void setPreDrawIssue(String preDrawIssue) {
		this.preDrawIssue = preDrawIssue;
	}

	public Date getPreDrawTime() {
		return this.preDrawTime;
	}

	public void setPreDrawTime(Date preDrawTime) {
		this.preDrawTime = preDrawTime;
	}

	public Integer getSumBigSmall() {
		return this.sumBigSmall;
	}

	public void setSumBigSmall(Integer sumBigSmall) {
		this.sumBigSmall = sumBigSmall;
	}

	public Integer getSumNum() {
		return this.sumNum;
	}

	public void setSumNum(Integer sumNum) {
		this.sumNum = sumNum;
	}

	public Integer getSumSingleDouble() {
		return this.sumSingleDouble;
	}

	public void setSumSingleDouble(Integer sumSingleDouble) {
		this.sumSingleDouble = sumSingleDouble;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getServiceTime() {
		serviceTime=new Date();
		return serviceTime;
	}

	public void setServiceTime(Date serviceTime) {
		this.serviceTime = serviceTime;
	}

	public Long getCountDown() {
		return countDown;
	}

	public void setCountDown(Long countDown) {
		this.countDown = countDown;
	}
	


}
