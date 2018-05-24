package com.ko30.common.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonWrite {
	
	private static final Logger LOGGER = Logger.getLogger(JsonWrite.class);
	
	/**
	 * 将对象转换成JSON字符串，并响应回前台
	 * 
	 * @param object
	 * @throws IOException
	 */
	public static void writeJson(HttpServletResponse response,Object object) {

		try {

			response.setContentType("text/html;charset=utf-8");
			String json = null;
			try {
				json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd",
						SerializerFeature.DisableCircularReferenceDetect);
			} catch (Exception e) {
				LOGGER.error("对象转换JSON字符串失败", e);
				e.printStackTrace();
			}
			response.getWriter().write(json);
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.error("json回写失败", e);
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @param object
	 */
	public static void writeObject(HttpServletResponse response, Object object) {
		try {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(object.toString());
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			LOGGER.error("对象回写失败", e);
			e.printStackTrace();
		}
	}
	
}
