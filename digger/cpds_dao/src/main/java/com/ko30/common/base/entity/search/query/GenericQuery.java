package com.ko30.common.base.entity.search.query;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.ko30.common.base.entity.search.query.support.IQuery;
import com.ko30.common.dao.BaseRepository;

public class GenericQuery<M, ID extends Serializable> implements IQuery {
	private BaseRepository genericRepository;
	private int begin;
	private int max;
	private Map params;

	public GenericQuery(BaseRepository<M, ID> genericRepository) {
		this.genericRepository = genericRepository;
	}

	public List getResult(String condition) {
		return genericRepository.find(condition, this.params, this.begin, this.max);
	}

	public List getResult(String condition, int begin, int max) {
		return this.genericRepository.find(condition, this.params, begin, max);
	}

	public int getRows(String condition) {
		int n = condition.toLowerCase().indexOf("order by");
		if (n > 0) {
			condition = condition.substring(0, n);
		}
		List ret = null;
		ret = genericRepository.query(condition, this.params, 0, 0);
		if ((ret != null) && (ret.size() > 0)) {
			if(ret.get(0) instanceof Long){
				return ((Long) ret.get(0)).intValue();
			}else {
				return ((Integer) ret.get(0)).intValue();
			}
		}
		return 0;
	}

	public void setFirstResult(int begin) {
		this.begin = begin;
	}

	public void setMaxResults(int max) {
		this.max = max;
	}

	public void setParaValues(Map params) {
		this.params = params;
	}

	@Override
	public Map getParaValues() {
		return params;
	}
}