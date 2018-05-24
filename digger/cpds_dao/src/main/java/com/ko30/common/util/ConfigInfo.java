package com.ko30.common.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 * 从配置文件读数据到 Map 
 * 
 * @author JGH
 *
 */
public class ConfigInfo {
	//public static final String configFileName = "cf.properties";
	public static final String configFileName = "config/serverConfig.properties";
	public static final Map<String, String> configMap = new HashMap<String, String>();
	
	static {
		//从配置文件读
		Properties properties = new Properties();
		FileInputStream fis = null;
		InputStreamReader reader = null;
		try {
			fis = new FileInputStream(ConfigInfo.class.getClassLoader().getResource(configFileName).getPath());
			reader = new InputStreamReader(fis,"UTF-8"); //最后的"UTF-8"根据文件属性而定，如果不行，改成"GBK"试试 
			properties.load(reader);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {try { reader.close();} catch (IOException e) { e.printStackTrace();}}
			if (fis != null) {try { fis.close();} catch (IOException e) { e.printStackTrace();}}
		}
		
		// 返回Properties中包含的key-value的Set视图  
        Set<Entry<Object, Object>> set = properties.entrySet();
        // 返回在此Set中的元素上进行迭代的迭代器  
        Iterator<Map.Entry<Object, Object>> it = set.iterator();  
        String key = null, value = null;  
        // 循环取出key-value
        while (it.hasNext()) {  
            Entry<Object, Object> entry = it.next();  
            key = String.valueOf(entry.getKey());  
            value = String.valueOf(entry.getValue());  
            key = key == null ? key : key.trim();  
            value = value == null ? value : value.trim();  
            ConfigInfo.configMap.put(key, value);
        }  		
	}
	
	public static String get(String propertyName){
		return ConfigInfo.configMap.get(propertyName);
	}
}
