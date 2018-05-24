package com.ko30.common.util;

import java.util.Date;

/**
 * 超级简单的编号生成器
 * 
 * @author liming
 *
 */
public class NumberUtil {

	private static final String USERS = "110";
	private static final String DEPT = "120";
	private static final String ROLE = "130";
	private static final String PERILS = "140";
	private static final String DEVICE = "150";
	private static final String JURISDICTION = "160";
	private static final String PLACE = "170";
	private static final String PROBLEM = "180";
	private static final String REGULATIONS = "190";
	private static final String TASKRANK = "200";
	private static final String TASKS = "210";
	private static final String SMAILTASKS = "220";

	public static String setNumber(String param) {

		param += new Date().getTime();
		return param;
	}

	public static String getUsers() {
		return USERS;
	}

	public static String getDept() {
		return DEPT;
	}

	public static String getRole() {
		return ROLE;
	}

	public static String getPerils() {
		return PERILS;
	}

	public static String getDevice() {
		return DEVICE;
	}

	public static String getJurisdiction() {
		return JURISDICTION;
	}

	public static String getPlace() {
		return PLACE;
	}

	public static String getProblem() {
		return PROBLEM;
	}

	public static String getRegulations() {
		return REGULATIONS;
	}

	public static String getTaskrank() {
		return TASKRANK;
	}

	public static String getTasks() {
		return TASKS;
	}

	public static String getSmailtasks() {
		return SMAILTASKS;
	}

}
