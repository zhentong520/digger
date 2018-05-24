package com.ko30.web.controller.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ko30.cache.RedisCacheUtil;
import com.ko30.common.util.AssertValue;
import com.ko30.entity.common.JsonResponse;
import com.ko30.entity.common.JsonStatus;
import com.ko30.entity.common.PageInfo;
import com.ko30.entity.common.exception.BusinessException;
import com.ko30.facade.common.JsonWrapper;

public class BaseController {

	protected Logger log = Logger.getLogger(this.getClass());

	/**
	 * 缓存工具
	 */
	@Resource
	protected RedisCacheUtil<Object> redisCache;
	
	@Autowired
	protected JsonWrapper jsonWrapper;

	@ResponseBody
	@RequestMapping("/{methodName}")
	public Object doJsonService(@RequestBody String jsonStr,
			@PathVariable String methodName, HttpServletRequest req,
			HttpServletResponse resp) {
		
		JSONObject json = JSON.parseObject(jsonStr);
		Object pageInfoJson = json.get("pageInfo");
		Object dataJson = json.get("data");
		
		// 请求分发，调用方法名为methodName的方法处理请求
		Object result = null;
		try {
			Method method = getMethodWithoutParam(methodName);
			Parameter[] params = method.getParameters();
			Object[] args = new Object[params.length];
			for (int i = 0; i < params.length; i++) {
				Parameter param = params[i];
				Class<?> paramType = param.getType();

				if (paramType == HttpServletRequest.class) {
					args[i] = req;
				} else if (paramType == HttpServletResponse.class) {
					args[i] = resp;
				} else if (paramType == JSONObject.class) {
					args[i] = json;
				} else if (paramType == PageInfo.class) {
					// 解析PageInfo
					if (null != pageInfoJson) {
						PageInfo pageInfo = JSON.parseObject(pageInfoJson.toString(), PageInfo.class);
						if (pageInfo.getCurrPage() < 0) {
							pageInfo.setCurrPage(0);
						} else if (pageInfo.getCurrPage() > 1) {
							// 前端默认从1页开始传
							pageInfo.setCurrPage(pageInfo.getCurrPage() - 1);
						}
						if (pageInfo.getPageSize() < 1) {
							// pageInfo.setPageSize(Integer.MAX_VALUE);
							// 默认查100条
							pageInfo.setPageSize(100);
						}
						args[i] = pageInfo;
					} else {
						args[i] = new PageInfo(0, Integer.MAX_VALUE);
					}
				} else {
					// 解析data
					if (AssertValue.isNotNull(dataJson) ) {
						args[i] = JSON.parseObject(dataJson.toString(),paramType);
					}
				}
			}
			result = method.invoke(this, args);
		} catch (Exception e) {
			log.error(e.getMessage(), e);

			if (e instanceof InvocationTargetException) {
				Throwable th = ((InvocationTargetException) e).getTargetException();
				if (th instanceof BusinessException) {
					BusinessException be = (BusinessException) th;
					String status = StringUtils.defaultIfEmpty(be.getErrCode(),
							JsonStatus.ERROR+"");
					return new JsonResponse(status, be.getMessage());
				}
			}

			return new JsonResponse(JsonStatus.ERROR, "系统异常");
		}

		return jsonWrapper.wrap(result);
	}

	/**
	 * 
	* @Title: getMethodWithoutParam 
	* @Description: 根据方法 名，得到方法对象 
	* @param @param methodName
	* @param @return    设定文件 
	* @return Method    返回类型 
	* @throws
	 */
	private Method getMethodWithoutParam(String methodName) {
		Method method = null;
		for (Method m : this.getClass().getDeclaredMethods()) {
			if (!Modifier.isStatic(m.getModifiers())
					&& m.getName().equals(methodName)) {
				method = m;
				break;
			}
		}

		return method;
	}
	

}
