package com.ko30.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * 
 * @ClassName: JsonUtil
 * @Description: json工具类，初始化fastjson配置，提高序列化效率
 * 				 有fastjson中JSON对象中的全部共公属性
 * @author carr
 * @date 2018年1月18日 下午5:10:08
 *
 */
public class JsonUtil extends JSON {
	public static ParserConfig pc = new ParserConfig();
	public static SerializeConfig sc = new SerializeConfig();
}
