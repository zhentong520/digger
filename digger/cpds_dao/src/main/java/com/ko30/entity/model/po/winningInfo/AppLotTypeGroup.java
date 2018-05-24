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
 * The persistent class for the app_lot_type_group database table.
 * 彩票类型分组
 */
@Entity
@Table(name="app_lot_type_group")
public class AppLotTypeGroup extends AbstractEntity<Integer> {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="create_time")
	private Date createTime;

	@Column(name="lot_group_code")
	private int lotGroupCode;

	@Column(name="lot_group_name")
	private String lotGroupName;

	public AppLotTypeGroup() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getLotGroupCode() {
		return this.lotGroupCode;
	}

	public void setLotGroupCode(int lotGroupCode) {
		this.lotGroupCode = lotGroupCode;
	}

	public String getLotGroupName() {
		return this.lotGroupName;
	}

	public void setLotGroupName(String lotGroupName) {
		this.lotGroupName = lotGroupName;
	}

}