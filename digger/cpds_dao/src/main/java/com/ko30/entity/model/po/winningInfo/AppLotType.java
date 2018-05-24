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
 * The persistent class for the app_lot_type database table.
 * 彩票类型
 */
@Entity
@Table(name="app_lot_type")
public class AppLotType extends AbstractEntity<Integer>  {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="day_num")
	private Integer dayNum;

	private String frequency;

	@Column(name="lot_alias")
	private String lotAlias;

	@Column(name="lot_code")
	private Integer lotCode;

	@Column(name="lot_day")
	private String lotDay;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="lot_end_time")
	private Date lotEndTime;

	@Column(name="lot_group_code")
	private Integer lotGroupCode;

	@Column(name="lot_name")
	private String lotName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="lot_time")
	private Date lotTime;

	@Column(name="total_count")
	private Integer totalCount;

	public AppLotType() {
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getDayNum() {
		return this.dayNum;
	}

	public void setDayNum(Integer dayNum) {
		this.dayNum = dayNum;
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

	public String getLotDay() {
		return this.lotDay;
	}

	public void setLotDay(String lotDay) {
		this.lotDay = lotDay;
	}

	public Date getLotEndTime() {
		return this.lotEndTime;
	}

	public void setLotEndTime(Date lotEndTime) {
		this.lotEndTime = lotEndTime;
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

	public Date getLotTime() {
		return this.lotTime;
	}

	public void setLotTime(Date lotTime) {
		this.lotTime = lotTime;
	}

	public Integer getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

}