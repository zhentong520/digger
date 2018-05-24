package com.ko30.constant.enums.quartz;

/**
 * 
* @ClassName: QuartzHandlerType 
* @Description: 定时任务运行状态 枚举类型 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月18日 上午9:21:57 
*
 */
public enum QuartzRunningStatus {

	/*** 待执行状态 **/
	WAIT("wait", "待执行"), 
	/*** 已完成状态 **/
	COMPLETE("complete", "已完成");

	private String status;

	private String name;

	private QuartzRunningStatus(String status, String name) {
		this.status = status;
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
