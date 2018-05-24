package com.ko30.entity.model.vo.grabresult;

import java.util.Date;

/**
 * 
* @ClassName: CommonOpenInfo 
* @Description: 开奖信息通用内容实体
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月21日 下午5:56:55 
*
 */
public class CommonOpenInfo {

	/**
	 * "preDrawCode":"3,9,0,7,8", 
	 * "drawIssue":10309205,
	 * "drawTime":"2017-08-21 16:25:30", 
	 * "preDrawTime":"2017-08-21 16:24:15",
	 * "preDrawIssue":10309204,
	 * 
	 * "drawCount":788, 
	 * "id":175968, 
	 * "status":0, 
	 * "frequency":"每75秒钟",
	 * "lotCode":10036, 
	 * "iconUrl":"", 
	 * "shelves":1, 
	 * "groupCode":2,
	 * "lotName":"极速时时彩",
	 *  "totalCount":1152, 
	 *  "serverTime":"2017-08-21 16:25:16",
	 */

	/**** 开奖期数 ****/
	private String drawIssue;

	/***** 中奖号码 *****/
	private String preDrawCode;

	/***** 开奖时间 *****/
	private Date drawTime;

	/***** 上次开奖时间 *****/
	private Date preDrawTime;

	/***** 上期期数 *****/
	private String preDrawIssue;

	/***** 当前开奖时间 *****/
	private String drawCount;

	/***** 彩种频率 *****/
	private String frequency;

	/***** 彩种名称 *****/
	private String lotName;

	/***** 总期数 *****/
	private String totalCount;

	/***** 彩票代码 *****/
	private String lotCode;

	public String getDrawIssue() {
		return drawIssue;
	}

	public void setDrawIssue(String drawIssue) {
		this.drawIssue = drawIssue;
	}

	public String getPreDrawCode() {
		return preDrawCode;
	}

	public void setPreDrawCode(String preDrawCode) {
		this.preDrawCode = preDrawCode;
	}

	public Date getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}

	public Date getPreDrawTime() {
		return preDrawTime;
	}

	public void setPreDrawTime(Date preDrawTime) {
		this.preDrawTime = preDrawTime;
	}

	public String getPreDrawIssue() {
		return preDrawIssue;
	}

	public void setPreDrawIssue(String preDrawIssue) {
		this.preDrawIssue = preDrawIssue;
	}

	public String getDrawCount() {
		return drawCount;
	}

	public void setDrawCount(String drawCount) {
		this.drawCount = drawCount;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public String getLotName() {
		return lotName;
	}

	public void setLotName(String lotName) {
		this.lotName = lotName;
	}

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getLotCode() {
		return lotCode;
	}

	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}

}
