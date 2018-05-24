package com.ko30.common.base.entity.search;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;

/**
 * <p>查询操作符</p>
 */
public class SearchOperator {
//    eq("等于", "="), ne("不等于", "!="),
//    gt("大于", ">"), gte("大于等于", ">="), lt("小于", "<"), lte("小于等于", "<="),
//    prefixLike("前缀模糊匹配", "like"), prefixNotLike("前缀模糊不匹配", "not like"),
//    suffixLike("后缀模糊匹配", "like"), suffixNotLike("后缀模糊不匹配", "not like"),
//    like("模糊匹配", "like"), notLike("不匹配", "not like"),
//    isNull("空", "is null"), isNotNull("非空", "is not null"),
//    in("包含", "in"), notIn("不包含", "not in"), custom("自定义默认的", null);
    
    public final static String eq = "=";
    public final static String ne = "!=";
    public final static String gt = ">";
    public final static String gte = ">=";
    public final static String lt = "<";
    public final static String lte = "<=";
    public final static String prefixLike = "like";
    public final static String prefixNotLike = "not like";
    public final static String suffixLike = "like";
    public final static String suffixNotLike = "not like";
    public final static String like = "like";
    public final static String notLike = "not like";
    public final static String isNull = "is null";
    public final static String isNotNull = "is not null";
    public final static String in = "in";
    public final static String notIn = "not in";
    public final static String custom = "";
    private static Map<String,String> valueMap = Maps.newHashMap();
    static{
    	valueMap.put("eq", eq);
    	valueMap.put("ne", ne);
    	valueMap.put("gt", gt);
    	valueMap.put("gte", gte);
    	valueMap.put("lt", lt);
    	valueMap.put("lte", lte);
    	valueMap.put("prefixLike", prefixLike);
    	valueMap.put("prefixNotLike", prefixNotLike);
    	valueMap.put("suffixLike", suffixLike);
    	valueMap.put("suffixNotLike", suffixNotLike);
    	valueMap.put("like", like);
    	valueMap.put("notLike", notLike);
    	valueMap.put("isNull", isNull);
    	valueMap.put("isNotNull", isNotNull);
    	valueMap.put("in", in);
    	valueMap.put("notIn", notIn);
    	valueMap.put("custom", custom);
    }


    SearchOperator(){
    }

    public static String toStringAllOperator() {
//        return Arrays.toString(SearchOperator.values());
    	return JSON.toJSONString(valueMap);
    }

    /**
     * 操作符是否允许为空
     *
     * @param operator
     * @return
     */
    public static boolean isAllowBlankValue(final String operator) {
        return operator.equals(SearchOperator.isNotNull) || operator.equals(SearchOperator.isNull);
    }

    private static String formatSymbol(String symbol) {
        if (StringUtils.isBlank(symbol)) {
            return symbol;
        }
        return symbol.trim().toLowerCase().replace("  ", " ");
    }

	public static String valueOf(String string) {
		return valueMap.get(string);
	}
    
}
