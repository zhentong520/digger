package com.ko30.common.base.entity.search.filter;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * or 条件
 * <p>User: pengxinxin
 * <p>Date: 13-5-24 下午2:51
 * <p>Version: 1.0
 */
public class OrCondition implements SearchFilter {

    private List<SearchFilter> orFilters = Lists.newArrayList();

    OrCondition() {
    }

    public OrCondition add(SearchFilter filter) {
        this.orFilters.add(filter);
        return this;
    }

    public List<SearchFilter> getOrFilters() {
        return orFilters;
    }

    @Override
    public String toString() {
        return "OrCondition{" + orFilters + '}';
    }
}
