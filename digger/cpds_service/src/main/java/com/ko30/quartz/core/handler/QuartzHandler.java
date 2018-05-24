package com.ko30.quartz.core.handler;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.ko30.constant.enums.quartz.QuartzHandlerType;

public interface QuartzHandler {

	public Map<String, String> handler(JSONObject data);

	public QuartzHandlerType getType();

	public String getDescription();

}
