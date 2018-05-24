package com.ko30.constant.enums.quartz;

public enum QuartzStatus {
	COMPLETE("complete"), NONE("none"), EXCEPTION("exception"), RUNNING("running");

	private String value;

	private QuartzStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static QuartzStatus getByValue(String value) {
		QuartzStatus[] types = QuartzStatus.values();
		for (QuartzStatus t : types) {
			if (t.getValue().equals(value)) {
				return t;
			}
		}
		return null;
	}



}
