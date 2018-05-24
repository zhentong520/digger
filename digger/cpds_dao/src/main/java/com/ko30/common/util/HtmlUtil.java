package com.ko30.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.serializer.SerializerFeature;

@SuppressWarnings("all")
public class HtmlUtil {

	/**
	 * 向responseBody中直接写入json串
	 * @param response
	 * @param jsonStr
	 */
	public static void writerJson(HttpServletResponse response, String jsonStr) {
		writer(response, jsonStr);
	}

	/**
	 * 将object对象转为json串输出
	 * @param response
	 * @param object
	 */
	public static void writerJson(HttpServletResponse response, Object object) {
		try {
			response.setContentType("application/json");
			writer(response, JSON.toJSONString(object));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将有循环引用的object对象转成json串
	 * @param response
	 * @param object
	 */
	public static void writerSerializerJson(HttpServletResponse response, Object object) {
		try {
			response.setContentType("application/json");
			writer(response, JSON.toJSONString(object,SerializerFeature.DisableCircularReferenceDetect));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param response
	 * @param htmlStr
	 * @throws Exception
	 */
	public static void writerHtml(HttpServletResponse response, String htmlStr) {
		writer(response, htmlStr);
	}

	private static void writer(HttpServletResponse response, String str) {
		try {
			StringBuffer result = new StringBuffer();
			// 设置页面不缓存
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = null;
			out = response.getWriter();
			out.print(str);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
