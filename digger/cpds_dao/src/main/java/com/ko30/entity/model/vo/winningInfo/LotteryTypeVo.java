package com.ko30.entity.model.vo.winningInfo;

import java.util.Date;

public class LotteryTypeVo {

	private Integer id;

	private Date create_Time;

	private int day_Num;

	private String endTime;

	private String frequency;

	private String lottery_Code;

	private String lottery_Day;

	private String lottery_Name;

	private String lottery_Time;

	private String lottery_Type;

	private String status;

	private Date update_Time;

	public Date getCreate_Time() {
		return this.create_Time;
	}

	public void setCreate_Time(Date create_Time) {
		this.create_Time = create_Time;
	}

	public int getDay_Num() {
		return this.day_Num;
	}

	public void setDay_Num(int day_Num) {
		this.day_Num = day_Num;
	}

	public String getEndTime() {
		return this.endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getLottery_Code() {
		return this.lottery_Code;
	}

	public void setLottery_Code(String lottery_Code) {
		this.lottery_Code = lottery_Code;
	}

	public String getLottery_Day() {
		return this.lottery_Day;
	}

	public void setLottery_Day(String lottery_Day) {
		this.lottery_Day = lottery_Day;
	}

	public String getLottery_Name() {
		return this.lottery_Name;
	}

	public void setLottery_Name(String lottery_Name) {
		this.lottery_Name = lottery_Name;
	}

	public String getLottery_Time() {
		return this.lottery_Time;
	}

	public void setLottery_Time(String lottery_Time) {
		this.lottery_Time = lottery_Time;
	}

	public String getLottery_Type() {
		return this.lottery_Type;
	}

	public void setLottery_Type(String lottery_Type) {
		this.lottery_Type = lottery_Type;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdate_Time() {
		return this.update_Time;
	}

	public void setUpdate_Time(Date update_Time) {
		this.update_Time = update_Time;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}
