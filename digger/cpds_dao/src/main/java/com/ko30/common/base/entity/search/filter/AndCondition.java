package com.ko30.common.base.entity.search.filter;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * and 条件
 * <p>User: pengxinxin
 * <p>Date: 13-5-24 下午2:51
 * <p>Version: 1.0
 */
public class AndCondition implements SearchFilter {

    private List<SearchFilter> andFilters = Lists.newArrayList();

    AndCondition() {
    }

    public AndCondition add(SearchFilter filter) {
        this.andFilters.add(filter);
        return this;
    }

    public List<SearchFilter> getAndFilters() {
        return andFilters;
    }

    @Override
    public String toString() {
        return "AndCondition{" + andFilters + '}';
    }
}
