package com.ko30.entity.model.po.winningInfo;

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
 * The persistent class for the app_lot_history database table.
 * 彩票开奖历史实体
 * 
 */
@Entity
@Table(name="app_lot_history")
public class AppLotHistory extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	/***** 设置默认创建时间 ****/
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime=new Date();

	@Column(name="draw_count")
	private Integer drawCount;

	@Column(name="draw_issue")
	private String drawIssue="";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="draw_time")
	private Date drawTime;

	private String frequency="";

	@Column(name="lot_alias")
	private String lotAlias="";

	@Column(name="lot_code")
	private Integer lotCode;

	@Column(name="lot_group_code")
	private Integer lotGroupCode;

	@Column(name="lot_name")
	private String lotName="";

	@Column(name="pre_draw_code")
	private String preDrawCode="";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pre_draw_date")
	private Date preDrawDate;

	@Column(name="pre_draw_issue")
	private String preDrawIssue="";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pre_draw_time")
	private Date preDrawTime;

	@Column(name="sum_big_small")
	private Integer sumBigSmall;

	@Column(name="sum_num")
	private Integer sumNum;

	@Column(name="sum_single_double")
	private Integer sumSingleDouble;

	@Column(name="total_count")
	private Integer totalCount;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="update_time")
	private Date updateTime;

	public AppLotHistory() {
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**** 设置默认创建时间 *****/
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

}