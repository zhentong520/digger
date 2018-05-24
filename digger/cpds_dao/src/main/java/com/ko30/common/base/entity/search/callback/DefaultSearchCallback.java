package com.ko30.common.base.entity.search.callback;

import javax.persistence.Query;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ko30.common.base.entity.search.SearchOperator;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.base.entity.search.filter.AndCondition;
import com.ko30.common.base.entity.search.filter.Condition;
import com.ko30.common.base.entity.search.filter.OrCondition;
import com.ko30.common.base.entity.search.filter.SearchFilter;

/**
 * <p>
 * <p>
 * Date: 13-1-16 下午4:20
 * <p>
 * Version: 1.0
 */
public class DefaultSearchCallback implements SearchCallback {

	private static final String paramPrefix = "param_";

	private String alias;
	private String aliasWithDot;

	public DefaultSearchCallback() {
		this("x");
	}

	public DefaultSearchCallback(String alias) {
		this.alias = alias;
		if (!"".equals(alias) && alias != null) {
			this.aliasWithDot = alias + ".";
		} else {
			this.aliasWithDot = "";
		}
	}

	public String getAlias() {
		return alias;
	}

	public String getAliasWithDot() {
		return aliasWithDot;
	}

	@Override
	public void prepareQL(StringBuilder ql, Searchable search) {
		String tmp = ql.toString();
		if (!search.hasSearchFilter()) {
			return;
		}

		int paramIndex = 1;
		for (SearchFilter searchFilter : search.getSearchFilters()) {
			if (searchFilter instanceof Condition) {
				Condition condition = (Condition) searchFilter;
				if (condition.getOperator() == SearchOperator.custom) {
					continue;
				}
			}
			ql.append(" and  ");
			paramIndex = genCondition(ql, paramIndex, searchFilter);
		}
		ql.delete(tmp.length(), tmp.length() + 4);
		ql.insert(tmp.length() + 1, "where");
	}

	private int genCondition(StringBuilder ql, int paramIndex, SearchFilter searchFilter) {
		boolean needAppendBracket = searchFilter instanceof OrCondition || searchFilter instanceof AndCondition;

		if (needAppendBracket) {
			ql.append("(");
		}

		if (searchFilter instanceof Condition) {
			Condition condition = (Condition) searchFilter;
			// 自定义条件
			String entityProperty = condition.getEntityProperty();
			String operatorStr = condition.getOperatorStr();
			// 实体名称
			ql.append(getAliasWithDot());
			ql.append(entityProperty);
			// 操作符
			// 1、如果是自定义查询符号，则使用SearchPropertyMappings中定义的默认的操作符
			ql.append(" ");
			ql.append(operatorStr);

			if (!condition.isUnaryFilter()) {
				ql.append(" :");
				ql.append(paramPrefix);
				ql.append(paramIndex++);
				//ql.append(condition.getSearchProperty());
				return paramIndex;
			}
		} else if (searchFilter instanceof OrCondition) {
			boolean isFirst = true;
			for (SearchFilter orSearchFilter : ((OrCondition) searchFilter).getOrFilters()) {
				if (!isFirst) {
					ql.append(" or ");
				}
				paramIndex = genCondition(ql, paramIndex, orSearchFilter);
				isFirst = false;
			}
		} else if (searchFilter instanceof AndCondition) {
			boolean isFirst = true;
			for (SearchFilter andSearchFilter : ((AndCondition) searchFilter).getAndFilters()) {
				if (!isFirst) {
					ql.append(" and ");
				}
				paramIndex = genCondition(ql, paramIndex, andSearchFilter);
				isFirst = false;
			}
		}

		if (needAppendBracket) {
			ql.append(")");
		}
		return paramIndex;
	}

	@Override
	public void setValues(Query query, Searchable search) {
		int paramIndex = 1;
		for (SearchFilter searchFilter : search.getSearchFilters()) {
			paramIndex = setValues(query, searchFilter, paramIndex);
		}
	}

	private int setValues(Query query, SearchFilter searchFilter, int paramIndex) {
		if (searchFilter instanceof Condition) {
			Condition condition = (Condition) searchFilter;
			if (condition.getOperator() == SearchOperator.custom) {
				return paramIndex;
			}
			if (condition.isUnaryFilter()) {
				return paramIndex;
			}
			// 原本的设置参数
			//query.setParameter(condition.getSearchProperty(),formtValue(condition, condition.getValue()));
			//paramIndex++;
			query.setParameter(paramPrefix + paramIndex++,formtValue(condition, condition.getValue()));
		} else if (searchFilter instanceof OrCondition) {
			for (SearchFilter orSearchFilter : ((OrCondition) searchFilter).getOrFilters()) {
				paramIndex = setValues(query, orSearchFilter, paramIndex);
			}

		} else if (searchFilter instanceof AndCondition) {
			for (SearchFilter andSearchFilter : ((AndCondition) searchFilter).getAndFilters()) {
				paramIndex = setValues(query, andSearchFilter, paramIndex);
			}
		}
		return paramIndex;
	}

	private Object formtValue(Condition condition, Object value) {
		String operator = condition.getOperator();
		if (operator.equals(SearchOperator.like) || operator.equals(SearchOperator.notLike)) {
			return "%" + value + "%";
		}
		if (operator.equals(SearchOperator.prefixLike) || operator.equals(SearchOperator.prefixNotLike)) {
			return value + "%";
		}

		if (operator.equals(SearchOperator.suffixLike) || operator.equals(SearchOperator.suffixNotLike)) {
			return "%" + value;
		}
		return value;
	}

	public void setPageable(Query query, Searchable search) {
		if (search.hasPageable()) {
			Pageable pageable = search.getPage();
			query.setFirstResult(pageable.getOffset());
			query.setMaxResults(pageable.getPageSize());
		}
	}

	public void prepareOrder(StringBuilder ql, Searchable search) {
		if (search.hashSort()) {
			ql.append(" order by ");
			for (Sort.Order order : search.getSort()) {
				ql.append(String.format("%s%s %s, ", getAliasWithDot(), order.getProperty(),
						order.getDirection().direction.toLowerCase()));
			}

			ql.delete(ql.length() - 2, ql.length());
		}
	}

}
