package com.ko30.entity.model.po.winningInfo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ko30.entity.model.AbstractEntity;


/**
 * 
* @ClassName: TbLotLatestBonus 
* @Description: 彩种预测累计金额统计 实体
* @author A18ccms a18ccms_gmail_com 
* @date 2017年10月11日 下午2:18:28 
*
 */
@Entity
@Table(name="tb_lot_latest_bonus")
public class TbLotLatestBonus extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="base_amount")
	private BigDecimal baseAmount;

	@Column(name="begin_area")
	private int beginArea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="current_amount")
	private BigDecimal currentAmount;

	@Column(name="current_issue")
	private String currentIssue;

	@Column(name="end_area")
	private int endArea;

	@Column(name="lot_alias")
	private String lotAlias;

	@Column(name="lot_code")
	private Integer lotCode;

	@Column(name="lot_name")
	private String lotName;

	private String remark;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public TbLotLatestBonus() {
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getBaseAmount() {
		return this.baseAmount;
	}

	public void setBaseAmount(BigDecimal baseAmount) {
		this.baseAmount = baseAmount;
	}

	public int getBeginArea() {
		return this.beginArea;
	}

	public void setBeginArea(int beginArea) {
		this.beginArea = beginArea;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getCurrentAmount() {
		return this.currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}

	public String getCurrentIssue() {
		return this.currentIssue;
	}

	public void setCurrentIssue(String currentIssue) {
		this.currentIssue = currentIssue;
	}

	public int getEndArea() {
		return this.endArea;
	}

	public void setEndArea(int endArea) {
		this.endArea = endArea;
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

	public String getLotName() {
		return this.lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


}