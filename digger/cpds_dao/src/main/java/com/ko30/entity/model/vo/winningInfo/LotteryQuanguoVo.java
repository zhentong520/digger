package com.ko30.entity.model.vo.winningInfo;

import java.util.Date;


/**
 * 
* @ClassName: AppLotteryQuanguo 
* @Description: 全国类型的彩票实体 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月22日 上午11:21:47 
*
 */
public class LotteryQuanguoVo {

	private Long id;

	private Date createTime;

	private String drawIssue;

	private Date drawTime;

	private String frequencyDetail;
	
	/*** 彩票类型码  **/
	private String lotCode;

	private String lotName;

	/***** 彩票编码 关联彩票类型表 ******/
	private String lotteryAlias;

	private String lotteryType;

	private String preDrawCode;

	private String preDrawIssue;

	private Date preDrawTime;

	private String sjh;

	private int sumNum;

	private int sumSingleDouble;

	private int totalCount;

	private Date updateTime;

	public LotteryQuanguoVo() {
	}

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

	public String getFrequencyDetail() {
		return this.frequencyDetail;
	}

	public void setFrequencyDetail(String frequencyDetail) {
		this.frequencyDetail = frequencyDetail;
	}

	public String getLotName() {
		return this.lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public String getLotteryType() {
		return this.lotteryType;
	}

	public void setLotteryType(String lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getPreDrawCode() {
		return this.preDrawCode;
	}

	public void setPreDrawCode(String preDrawCode) {
		this.preDrawCode = preDrawCode;
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

	public String getSjh() {
		return this.sjh;
	}

	public void setSjh(String sjh) {
		this.sjh = sjh;
	}

	public int getSumNum() {
		return this.sumNum;
	}

	public void setSumNum(int sumNum) {
		this.sumNum = sumNum;
	}

	public int getSumSingleDouble() {
		return this.sumSingleDouble;
	}

	public void setSumSingleDouble(int sumSingleDouble) {
		this.sumSingleDouble = sumSingleDouble;
	}

	public int getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getLotCode() {
		return lotCode;
	}

	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}

	public String getLotteryAlias() {
		return lotteryAlias;
	}

	public void setLotteryAlias(String lotteryAlias) {
		this.lotteryAlias = lotteryAlias;
	}

	
}