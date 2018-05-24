package com.ko30.service.common;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.ko30.common.util.BeanUtils;
import com.ko30.entity.common.JsonContext;
import com.ko30.facade.common.JsonWrapper;

/**
 * JSON包装器，将对象转包装Map
 */
@Component("jsonWrapper")
public class MapJsonWrapper implements JsonWrapper {

	/*** 忽略的类型：不需要包装对象内部的属性 **/
	private final static Class<?>[] IGNORE_TYPES = new Class<?>[] { String.class, Date.class, BigDecimal.class };

	protected Log log = LogFactory.getLog(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public Object wrap(Object obj) {
		// 集合数组转换成Map
		if (obj instanceof Collection || obj.getClass().isArray()) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("data", obj);
			obj = map;
		}

		JsonContext ctx = new JsonContext();
		Map<String, Object> rs = (Map<String, Object>) doWrap(obj, ctx);
		rs.putAll(ctx);

		return rs;
	}

	/**
	 * 转换对象类型，Collection/Array->List, Map->Map, Object->Map
	 */
	private Object doWrap(Object obj, JsonContext ctx) {
		if (obj instanceof Collection || obj.getClass().isArray()) {
			Collection<?> list = (Collection<?>) obj;
			List<Object> targetList = new ArrayList<Object>();
			for (Object o : list) {
				Object target = doWrap(o, ctx);
				targetList.add(target);
			}

			return targetList;
		} else if (obj instanceof Map) {
			Map<?, ?> map = (Map<?, ?>) obj;
			Map<Object, Object> targetMap = new LinkedHashMap<Object, Object>();
			for (Map.Entry<?, ?> entry : map.entrySet()) {
				Object key = entry.getKey();
				Object value = entry.getValue();

				if (null != value && needWrap(value.getClass())) {
					value = doWrap(value, ctx);
				}
				
				targetMap.put(key, value);
			}

			return targetMap;
		} else {
			Map<String, Object> map = new LinkedHashMap<String, Object>();

			for (Field field : BeanUtils.getFields(obj.getClass())) {
				try {
					String name = field.getName();
					Class<?> type = field.getType();
					field.setAccessible(true);
					Object value = field.get(obj);

					if (null != value && needWrap(type)) {
						value = doWrap(value, ctx);
					}

					value = wrapField(field, value, ctx);

					map.put(name, value);
				} catch (Exception e) {
					log.error(e.getMessage(), e);
				}
			}

			return map;
		}
	}

	/**
	 * 判断是否需要处理对象的属性
	 */
	private boolean needWrap(Class<?> type) {
		if (BeanUtils.isPrimitiveType(type) || BeanUtils.isWrapType(type)) {
			return false;
		}

		for (Class<?> clazz : IGNORE_TYPES) {
			if (type.isAssignableFrom(clazz)) {
				return false;
			}
		}

		return true;
	}

	/**
	 * 包装对象的属性
	 */
	protected Object wrapField(Field field, Object value, JsonContext ctx) {
		return value;
	}

}
