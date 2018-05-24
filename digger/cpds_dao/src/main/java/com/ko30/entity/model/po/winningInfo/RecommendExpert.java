package com.ko30.entity.model.po.winningInfo;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.ko30.entity.model.AbstractEntity;

@Entity
@Table(name="tb_recommend_expert")
public class RecommendExpert extends AbstractEntity<Long>{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	/***** 设置默认创建时间 ****/
	@Temporal(TemporalType.TIMESTAMP)
	private Date updataTime=new Date();

	private Integer exptId;

	private String lotCode;

	private String enCode;

	private String planCode;

	private String planRemark;

	private Integer killNum;

	private Integer exptSeat;
	
	private String lotName;
	
	private String lotShortName;
	
	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Date getUpdataTime() {
		return updataTime;
	}

	public void setUpdataTime(Date updataTime) {
		this.updataTime = updataTime;
	}

	public Integer getExptId() {
		return exptId;
	}

	public void setExptId(Integer exptId) {
		this.exptId = exptId;
	}

	public String getLotCode() {
		return lotCode;
	}

	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}

	public String getEnCode() {
		return enCode;
	}

	public void setEnCode(String enCode) {
		this.enCode = enCode;
	}

	public String getPlanCode() {
		return planCode;
	}

	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}

	public String getPlanRemark() {
		return planRemark;
	}

	public void setPlanRemark(String planRemark) {
		this.planRemark = planRemark;
	}

	public Integer getKillNum() {
		return killNum;
	}

	public void setKillNum(Integer killNum) {
		this.killNum = killNum;
	}

	public Integer getExptSeat() {
		return exptSeat;
	}

	public void setExptSeat(Integer exptSeat) {
		this.exptSeat = exptSeat;
	}

	public String getLotName() {
		return lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public String getLotShortName() {
		return lotShortName;
	}

	public void setLotShortName(String lotShortName) {
		this.lotShortName = lotShortName;
	}

	
}
