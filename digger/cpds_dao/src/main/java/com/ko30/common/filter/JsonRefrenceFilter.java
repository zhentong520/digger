package com.ko30.common.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * FastJsonRefrence过滤器
 * 解决Refrence及LazyLoading引起的死循环问题
 * @author lmk
 *
 */
public class JsonRefrenceFilter {
	
	/**
	 * 过滤设置的属性
	 * @param c 待处理的对象
	 * @param property 设置要过滤的属性
	 * @return 处理后的json数据
	 */
	public static String setJsonSerialize(Object c,final String property){
		PropertyFilter filter = new PropertyFilter() {  
		    public boolean apply(Object source, String name, Object value) {  
		        if(property.equals(name)) {  
		            return false;  
		        }  
		        return true;  
		    } 
		};  
		SerializeWriter sw = new SerializeWriter();  
		JSONSerializer serializer = new JSONSerializer(sw);  
		serializer.getPropertyFilters().add(filter);  
		serializer.write(c);
		return sw.toString();
	}
	
	/**
	 * 不过滤属性，解决属性重复引用
	 * JsonRefrenceFilter.setJsonSerialize(JsonRefrenceFilter.getJson(m), "");
	 * @param o 待处理的对象
	 * @return
	 */
	public static String getJson(Object o){
		return JSON.toJSONString(o,
				SerializerFeature.DisableCircularReferenceDetect);
	}
	
}
