package com.ko30.constant.enums;

/**
 * 测试枚举类
 * 
 * @author carr
 *
 */
public enum TestType {

	/** 是 ***/
	YES("yes", "是"),
	/** 否 ***/
	NO("no", "否");

	private String value;

	private String desc;

	private TestType(String value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public static TestType getByValue(String value) {
		TestType[] types = TestType.values();
		for (TestType t : types) {
			if (t.getValue().equals(value)) {
				return t;
			}
		}
		return null;
	}
}
