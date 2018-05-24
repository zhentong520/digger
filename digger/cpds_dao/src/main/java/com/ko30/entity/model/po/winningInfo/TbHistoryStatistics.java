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

@Entity
@Table(name="tb_history_statistics")
public class TbHistoryStatistics extends AbstractEntity<Long>{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;

	private Date createTime=new Date();

	private String lotCode;
	
	private String drawCode;
	
	private String drawIssue;
	
	private String subDrawIssue;

	private Date drawTime=new Date();

	private String missingData;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="lot_code")
	public String getLotCode() {
		return lotCode;
	}
	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}
	
	@Column(name="draw_code")
	public String getDrawCode() {
		return drawCode;
	}
	public void setDrawCode(String drawCode) {
		this.drawCode = drawCode;
	}
	
	@Column(name="draw_issue")
	public String getDrawIssue() {
		return drawIssue;
	}
	public void setDrawIssue(String drawIssue) {
		this.drawIssue = drawIssue;
	}
	
	@Column(name="sub_draw_issue")
	public String getSubDrawIssue() {
		return subDrawIssue;
	}
	public void setSubDrawIssue(String subDrawIssue) {
		this.subDrawIssue = subDrawIssue;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="draw_time")
	public Date getDrawTime() {
		return drawTime;
	}
	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}
	
	@Column(name="missing_data")
	public String getMissingData() {
		return missingData;
	}
	public void setMissingData(String missingData) {
		this.missingData = missingData;
	}
	
	
	
}
