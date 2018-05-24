package com.ko30.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONArray;
import com.ko30.quartz.service._2_ShowApi_GrabOpenResultService;
import com.ko30.quartz.service._3_OpenCai_GrabOpenResultService;
import com.ko30.web.controller.base.BaseController;

@Controller
@RequestMapping("/test")
public class TestController extends BaseController {

	@Autowired
	private _2_ShowApi_GrabOpenResultService _2grabService;
	
	@Autowired
	private _3_OpenCai_GrabOpenResultService _3grabService;
	
	public Object test_1() {
		JSONArray objList = _2grabService.getDataByLotCode(10039);
		return objList;
	}
	
	public Object test_2() {
		JSONArray objList = _3grabService.getDataByLotCode(10039);
		return objList;
	}
}
