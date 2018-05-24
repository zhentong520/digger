package com.ko30.common.util;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class MyUitls {
	
	/**
     * 获取map中第一个数据值
     *
     * @param <K> Key的类型
     * @param <V> Value的类型
     * @param map 数据源
     * @return 返回的值
     */
    public static <K, V> V getFirstOrNull(Map<K, V> map) {
        V obj = null;
        for (Entry<K, V> entry : map.entrySet()) {
            obj = entry.getValue();
            if (obj != null) {
                break;
            }
        }
        return obj;
    }
    public static int pages(int total,int rows){
		int page = (int) (total%rows==0?total/rows:total/rows+1);
		return page;
	}
    
    /*
     * 截取列表
     */
    public static <T> List<T> getSubListPage(List<T> list, int skip,
            int pageSize) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        int startIndex = skip;
        int endIndex = skip + pageSize;
        if (startIndex > endIndex || startIndex > list.size()) {
            return null;
        }
        if (endIndex > list.size()) {
            endIndex = list.size();
        }
        return list.subList(startIndex, endIndex);
    }
	
}
