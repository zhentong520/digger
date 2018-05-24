package com.ko30.entity.model.vo.grabresult;

/**
 * 
 * @ClassName: LotGrabInfo
 * @Description: 请求彩种实体
 * @author A18ccms a18ccms_gmail_com
 * @date 2017年11月14日 下午5:27:48
 *
 */
public class LotGrabInfo {
	private String lotName;
	private String url;
	private Integer lotCode;
	public String getLotName() {
		return lotName;
	}
	public void setLotName(String lotName) {
		this.lotName = lotName;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getLotCode() {
		return lotCode;
	}
	public void setLotCode(Integer lotCode) {
		this.lotCode = lotCode;
	}
}
