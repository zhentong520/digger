package com.ko30.entity.model.vo.admin;

public class UpdUserVo {
	private Long updateTime;
	private String nickname;
	private String password;
	private String personUrl;
	
	public Long getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}
	public String getNickname() {
		return (nickname == null ? "" : nickname);
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPassword() {
		return (password == null ? "" : password);
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPersonUrl() {
		return (personUrl == null ? "" : personUrl);
	}
	public void setPersonUrl(String personUrl) {
		this.personUrl = personUrl;
	}
	
	
}
