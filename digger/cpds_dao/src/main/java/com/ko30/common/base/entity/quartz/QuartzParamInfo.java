package com.ko30.common.base.entity.quartz;

/**
 * 
* @ClassName: QuartzBaseInfo 
* @Description: 定时任务基础参数接口 
* @author A18ccms a18ccms_gmail_com 
* @date 2017年8月18日 上午9:41:46 
*
 */
public class QuartzParamInfo {

	/*** 所执行的key标识**/
	private String executeKey;
	
	/**** 传入的参数实体信息 ***/
	private Object paramObj;

	public String getExecuteKey() {
		return executeKey;
	}

	public void setExecuteKey(String executeKey) {
		this.executeKey = executeKey;
	}

	public Object getParamObj() {
		return paramObj;
	}

	public void setParamObj(Object paramObj) {
		this.paramObj = paramObj;
	}
	
}
