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
 * The persistent class for the lot_draw_code_statistics database table.
 * 
 */
@Entity
@Table(name="lot_draw_code_statistics")
public class LotDrawCodeStatistic extends AbstractEntity<Long> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="lot_code")
	private Integer lotCode;
	
	@Column(name="pre_draw_issue")
	private String preDrawIssue;
	
	@Column(name="pre_draw_code")
	private String preDrawCode;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="pre_draw_time")
	private Date preDrawTime;
	
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="fifth_area")
	private String fifthArea;

	@Column(name="first_area")
	private String firstArea;

	@Column(name="fourth_area")
	private String fourthArea;

	private String remark;

	@Column(name="second_area")
	private String secondArea;

	@Column(name="seventh_area")
	private String seventhArea;

	@Column(name = "eighth_area")
	private String eighthArea;

	@Column(name = "ninth_area")
	private String ninthArea;

	@Column(name="sixth_area")
	private String sixthArea;

	@Column(name="third_area")
	private String thirdArea;

	public LotDrawCodeStatistic() {
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getFifthArea() {
		return this.fifthArea;
	}

	public void setFifthArea(String fifthArea) {
		this.fifthArea = fifthArea;
	}

	public String getFirstArea() {
		return this.firstArea;
	}

	public void setFirstArea(String firstArea) {
		this.firstArea = firstArea;
	}

	public String getFourthArea() {
		return this.fourthArea;
	}

	public void setFourthArea(String fourthArea) {
		this.fourthArea = fourthArea;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSecondArea() {
		return this.secondArea;
	}

	public void setSecondArea(String secondArea) {
		this.secondArea = secondArea;
	}

	public String getSeventhArea() {
		return this.seventhArea;
	}

	public void setSeventhArea(String seventhArea) {
		this.seventhArea = seventhArea;
	}

	public String getSixthArea() {
		return this.sixthArea;
	}

	public void setSixthArea(String sixthArea) {
		this.sixthArea = sixthArea;
	}

	public String getThirdArea() {
		return this.thirdArea;
	}

	public void setThirdArea(String thirdArea) {
		this.thirdArea = thirdArea;
	}

	public Integer getLotCode() {
		return lotCode;
	}

	public void setLotCode(Integer lotCode) {
		this.lotCode = lotCode;
	}

	public String getPreDrawIssue() {
		return preDrawIssue;
	}

	public void setPreDrawIssue(String preDrawIssue) {
		this.preDrawIssue = preDrawIssue;
	}

	public String getPreDrawCode() {
		return preDrawCode;
	}

	public void setPreDrawCode(String preDrawCode) {
		this.preDrawCode = preDrawCode;
	}

	public Date getPreDrawTime() {
		return preDrawTime;
	}

	public void setPreDrawTime(Date preDrawTime) {
		this.preDrawTime = preDrawTime;
	}

	public String getEighthArea() {
		return eighthArea;
	}

	public void setEighthArea(String eighthArea) {
		this.eighthArea = eighthArea;
	}

	public String getNinthArea() {
		return ninthArea;
	}

	public void setNinthArea(String ninthArea) {
		this.ninthArea = ninthArea;
	}
	
	
}