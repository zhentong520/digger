package com.ko30.entity.model.po.winningInfo;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ko30.entity.model.AbstractEntity;



/**
 * 
 * @ClassName: TShowApiLotType
 * @Description: 从 showapi 接口获取的彩种类型信息
 * @author A18ccms a18ccms_gmail_com
 * @date 2017年10月19日 上午11:52:19
 *
 */
@Entity
@Table(name="t_show_api_lot_type")
public class TShowApiLotType extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	private String area;

	@Column(name="begin_time")
	private Date beginTime;
	
	@Column(name="end_time")
	private Date endTime;

	@Column(name="is_high_frequency")
	private String isHighFrequency;

	@Column(name="is_hot")
	private String isHot;

	@Column(name="lot_alias")
	private String lotAlias;

	@Column(name="lot_code")
	private BigInteger lotCode;

	@Column(name="lot_name")
	private String lotName;

	private String remark;

	@Column(name="total_count")
	private BigInteger totalCount;

	private String type;

	public TShowApiLotType() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArea() {
		return this.area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public Date getBeginTime() {
		return this.beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIsHighFrequency() {
		return this.isHighFrequency;
	}

	public void setIsHighFrequency(String isHighFrequency) {
		this.isHighFrequency = isHighFrequency;
	}

	public String getIsHot() {
		return this.isHot;
	}

	public void setIsHot(String isHot) {
		this.isHot = isHot;
	}

	public String getLotAlias() {
		return this.lotAlias;
	}

	public void setLotAlias(String lotAlias) {
		this.lotAlias = lotAlias;
	}

	public BigInteger getLotCode() {
		return this.lotCode;
	}

	public void setLotCode(BigInteger lotCode) {
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

	public BigInteger getTotalCount() {
		return this.totalCount;
	}

	public void setTotalCount(BigInteger totalCount) {
		this.totalCount = totalCount;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}