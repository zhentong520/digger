package com.ko30.common.dao.support ;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.ko30.common.base.entity.plugin.LogicDeleteable;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.base.entity.search.annotation.QueryJoin;
import com.ko30.common.base.entity.search.callback.SearchCallback;
import com.ko30.common.base.entity.search.query.GenericPageList;
import com.ko30.common.base.entity.search.query.GenericQuery;
import com.ko30.common.base.entity.search.query.PageObject;
import com.ko30.common.base.entity.search.query.support.IPageList;
import com.ko30.common.base.entity.search.query.support.IQuery;
import com.ko30.common.base.entity.search.query.support.IQueryObject;
import com.ko30.common.dao.BaseRepository;
import com.ko30.common.dao.RepositoryHelper;
import com.ko30.entity.common.exception.BusinessException;

/**
 * <p>抽象基础Custom Repository 实现</p>
 * <p>User: pengxinxin
 * <p>Date: 13-1-15 下午7:33
 * <p>Version: 1.0
 */
public class SimpleBaseRepository<M, ID extends Serializable> extends SimpleJpaRepository<M, ID>
        implements BaseRepository<M, ID> {

    public static final String LOGIC_DELETE_ALL_QUERY_STRING = "update %s x set x.deleted=true where x in (?1)";
    public static final String DELETE_ALL_QUERY_STRING = "delete from %s x where x in (?1)";
    public static final String FIND_QUERY_STRING = "select x from %s x ";
    public static final String COUNT_QUERY_STRING = "select count(x) from %s x ";

    private final EntityManager em;
    private final JpaEntityInformation<M, ID> entityInformation;

    private final RepositoryHelper repositoryHelper;

    private Class<M> entityClass;
    private String entityName;
    private String idName;
    
    private Logger logger = LoggerFactory.getLogger(SimpleBaseRepository.class);


    /**
     * 查询所有的QL
     */
    private String findAllQL;
    /**
     * 统计QL
     */
    private String countAllQL;

    private QueryJoin[] joins;

    private SearchCallback searchCallback = SearchCallback.DEFAULT;

    public SimpleBaseRepository(JpaEntityInformation<M, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);

        this.entityInformation = entityInformation;
        this.entityClass = this.entityInformation.getJavaType();
        this.entityName = this.entityInformation.getEntityName();
        this.idName = this.entityInformation.getIdAttributeNames().iterator().next();
        this.em = entityManager;
        //fix by zoo

        repositoryHelper = new RepositoryHelper(entityManager,entityClass);

        findAllQL = String.format(FIND_QUERY_STRING, entityName);
        countAllQL = String.format(COUNT_QUERY_STRING, entityName);
    }


    /**
     * Configures a custom {@link org.springframework.data.jpa.repository.support.LockMetadataProvider} to be used to detect {@link javax.persistence.LockModeType}s to be applied to
     * queries.
     *
     * @param lockMetadataProvider
     */
//    public void setLockMetadataProvider(LockMetadataProvider lockMetadataProvider) {
//        super.setLockMetadataProvider(lockMetadataProvider);
//        this.lockMetadataProvider = lockMetadataProvider;
//    }

    /**
     * 设置searchCallback
     *
     * @param searchCallback
     */
    public void setSearchCallback(SearchCallback searchCallback) {
        this.searchCallback = searchCallback;
    }

    /**
     * 设置查询所有的ql
     *
     * @param findAllQL
     */
    public void setFindAllQL(String findAllQL) {
        this.findAllQL = findAllQL;
    }

    /**
     * 设置统计的ql
     *
     * @param countAllQL
     */
    public void setCountAllQL(String countAllQL) {
        this.countAllQL = countAllQL;
    }

    public void setJoins(QueryJoin[] joins) {
        this.joins = joins;
    }

    /////////////////////////////////////////////////
    ////////覆盖默认spring data jpa的实现////////////
    /////////////////////////////////////////////////

    /**
     * 根据主键删除相应实体
     *
     * @param id 主键
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class,BusinessException.class})
    @Override
    public void delete(final ID id) {
        M m = findOne(id);
        delete(m);
    }

    /**
     * 删除实体
     *
     * @param m 实体
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class,BusinessException.class})
    @Override
    public void delete(final M m) {
        if (m == null) {
            return;
        }
        if (m instanceof LogicDeleteable) {
            ((LogicDeleteable) m).markDeleted();
            save(m);
        } else {
            super.delete(m);
        }
    }


    /**
     * 根据主键删除相应实体
     *
     * @param ids 实体
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class,BusinessException.class})
    @Override
    public void delete(final ID[] ids) {
        if (ArrayUtils.isEmpty(ids)) {
            return;
        }
        List<M> models = new ArrayList<M>();
        for (ID id : ids) {
            M model = null;
            try {
                model = entityClass.newInstance();
            } catch (Exception e) {
                throw new RuntimeException("batch delete " + entityClass + " error", e);
            }
            try {
                BeanUtils.setProperty(model, idName, id);
            } catch (Exception e) {
                throw new RuntimeException("batch delete " + entityClass + " error, can not set id", e);
            }
            models.add(model);
        }
        deleteInBatch(models);
    }

    @Transactional(rollbackFor={Exception.class,RuntimeException.class,BusinessException.class})
    @Override
    public void deleteInBatch(final Iterable<M> entities) {
        Iterator<M> iter = entities.iterator();
        if (entities == null || !iter.hasNext()) {
            return;
        }

        Set models = Sets.newHashSet(iter);

        boolean logicDeleteableEntity = LogicDeleteable.class.isAssignableFrom(this.entityClass);

        if (logicDeleteableEntity) {
            String ql = String.format(LOGIC_DELETE_ALL_QUERY_STRING, entityName);
            repositoryHelper.batchUpdate(ql, models);
        } else {
            String ql = String.format(DELETE_ALL_QUERY_STRING, entityName);
            repositoryHelper.batchUpdate(ql, models);
        }
    }

    /**
     * 按照主键查询
     *
     * @param id 主键
     * @return 返回id对应的实体
     */
    @Transactional(rollbackFor={Exception.class,RuntimeException.class,BusinessException.class})
    @Override
    public M findOne(ID id) {
        if (id == null) {
            return null;
        }
        if (id instanceof Integer && ((Integer) id).intValue() == 0) {
            return null;
        }
        if (id instanceof Long && ((Long) id).longValue() == 0L) {
            return null;
        }
        repositoryHelper.applyEnableQueryCache(em);
        return super.findOne(id);
    }


    ////////根据Specification查询 直接从SimpleJpaRepository复制过来的///////////////////////////////////
    @Override
    public M findOne(Specification<M> spec) {
        try {
        	repositoryHelper.applyEnableQueryCache(em);
            return getQuery(spec, (Sort) null).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }


    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.CrudRepository#findAll(ID[])
     */
    public List<M> findAll(Iterable<ID> ids) {
    	em.setProperty("javax.persistence.cache.storeMode", "REFRESH");
        return getQuery(new Specification<M>() {
            public Predicate toPredicate(Root<M> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<?> path = root.get(entityInformation.getIdAttribute());
                return path.in(cb.parameter(Iterable.class, "ids"));
            }
        }, (Sort) null).setParameter("ids", ids).getResultList();
    }


    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(org.springframework.data.jpa.domain.Specification)
     */
    public List<M> findAll(Specification<M> spec) {
        return getQuery(spec, (Sort) null).getResultList();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(org.springframework.data.jpa.domain.Specification, org.springframework.data.domain.Pageable)
     */
    public Page<M> findAll(Specification<M> spec, Pageable pageable) {
    	repositoryHelper.applyEnableQueryCache(em);
        TypedQuery<M> query = getQuery(spec, pageable);
        return pageable == null ? new PageImpl<M>(query.getResultList()) : readPage(query, pageable, spec);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#findAll(org.springframework.data.jpa.domain.Specification, org.springframework.data.domain.Sort)
     */
    public List<M> findAll(Specification<M> spec, Sort sort) {
    	repositoryHelper.applyEnableQueryCache(em);
        return getQuery(spec, sort).getResultList();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.jpa.repository.JpaSpecificationExecutor#count(org.springframework.data.jpa.domain.Specification)
     */
    public long count(Specification<M> spec) {

        return getCountQuery(spec).getSingleResult();
    }
    ////////根据Specification查询 直接从SimpleJpaRepository复制过来的///////////////////////////////////


    ///////直接从SimpleJpaRepository复制过来的///////////////////////////////
    ///////直接从SimpleJpaRepository复制过来的///////////////////////////////


    @Override
    public List<M> findAll() {
        return repositoryHelper.findAll(findAllQL);
    }

    @Override
    public List<M> findAll(final Sort sort) {
        return repositoryHelper.findAll(findAllQL, sort);
    }

    @Override
    public Page<M> findAll(final Pageable pageable) {
        return new PageImpl<M>(
                repositoryHelper.<M>findAll(findAllQL, pageable),
                pageable,
                repositoryHelper.count(countAllQL)
        );
    }

    @Override
    public long count() {
        return repositoryHelper.count(countAllQL);
    }
    
    public long count(String definedSql) {
    	return (long) em.createNativeQuery(countAllQL+definedSql).getSingleResult();
    }


    /////////////////////////////////////////////////
    ///////////////////自定义实现////////////////////
    /////////////////////////////////////////////////

    @Override
    public Page<M> findAll(final Searchable searchable) {
        List<M> list = repositoryHelper.findAll(findAllQL, searchable, searchCallback);
        long total = searchable.hasPageable() ? count(searchable) : list.size();
        return new PageImpl<M>(
                list,
                searchable.getPage(),
                total
        );
    }
    
    @Override
    public Page<M> findAll(final String definedSql,final Searchable searchable) {
        List<M> list = repositoryHelper.findAll(findAllQL+definedSql, searchable, searchCallback);
        long total = searchable.hasPageable() ? count(searchable) : list.size();
        return new PageImpl<M>(
                list,
                searchable.getPage(),
                total
        );
    }
    
    /**
     * 自定义sql 分页查询
     * add dxn
     */
    public List<M> findAll(String definedSql) {
    	 return repositoryHelper.findAll(findAllQL+definedSql);
    }

    @Override
    public long count(final Searchable searchable) {
        return repositoryHelper.count(countAllQL, searchable, searchCallback);
    }

    /**
     * 重写默认的 这样可以走一级/二级缓存
     *
     * @param id
     * @return
     */
    @Override
    public boolean exists(ID id) {
        return findOne(id) != null;
    }


	@Override
	public List<M> query(String queryStr, Map params, int begin, int max) {
		Query query = em.createQuery(queryStr);
		if ((params != null) && (params.size() > 0)) {
			for (Iterator localIterator = params.keySet().iterator(); localIterator
					.hasNext();) {
				Object key = localIterator.next();
				query.setParameter(key.toString(), params.get(key));
			}
		}
		if ((begin >= 0) && (max > 0)) {
			query.setFirstResult(begin);
			query.setMaxResults(max);
		}
		repositoryHelper.applyEnableQueryCache(query);
		List<M> list = query.getResultList();
		if ((list != null) && (list.size() > 0)) {
			return list;
		}
		return Lists.newArrayList();
	}

	@Override
	public M getBy(Class<M> clazz, final String propertyName,
			final Object value) {
		final Class<M> claz = clazz;
		String clazzName = claz.getName();
		StringBuffer sb = new StringBuffer("select obj from ");
		sb.append(clazzName).append(" obj");
		Query query = null;
		if ((propertyName != null) && (value != null)) {
			sb.append(" where obj.").append(propertyName)
					.append(" = :value");
			query = em.createQuery(sb.toString()).setParameter("value",
					value);
		} else {
			query = em.createQuery(sb.toString());
		}
		repositoryHelper.applyEnableQueryCache(query);
		List<M> ret = query.getResultList();
		if ((ret != null) && (ret.size() == 1))
			return ret.get(0);
		if ((ret != null) && (ret.size() > 1)) {
			throw new IllegalStateException("worning  --more than one object find!! return one object,Object is :"+clazzName+" value:"+value);
//			logger.info("worning  --more than one object find!! return one object,Object is :"+clazzName+" value:"+value);
//			return ret.get(0);
		}
		return null;
	}


	@Override
	public M getBy(String propertyName, String value) {
		return this.getBy(entityClass, propertyName, value);
	}


	@Override
	public IPageList list(IQueryObject properties) {
		if (properties == null) {
			return null;
		}
		String query = properties.getQuery();
		Map params = properties.getParameters();
		IQuery iquery = new GenericQuery(this);
		iquery.setParaValues(params);
		GenericPageList pList = new GenericPageList(entityClass.getName(), query,iquery);
		if (properties != null) {
			PageObject pageObj = properties.getPageObj();
			if (pageObj != null)
				pList.doList(pageObj.getCurrentPage() == null ? 0 : pageObj
						.getCurrentPage().intValue(),
						pageObj.getPageSize() == null ? 0 : pageObj
								.getPageSize().intValue());
		} else {
			pList.doList(0, -1);
		}
		pList.setQuery(null);
		return pList;
	}

	public List<M> find(final String queryStr,
			final Map params, final int begin, final int max) {
		String clazzName = entityClass.getName();
		StringBuffer sb = new StringBuffer("select obj from ");
		sb.append(clazzName).append(" obj").append(" where ")
				.append(queryStr);
		Query query = em.createQuery(sb.toString());
		for (Iterator localIterator = params.keySet().iterator(); localIterator
				.hasNext();) {
			Object key = localIterator.next();
			query.setParameter(key.toString(), params.get(key));
		}
		if ((begin >= 0) && (max > 0)) {
			query.setFirstResult(begin);
			query.setMaxResults(max);
		}
		repositoryHelper.applyEnableQueryCache(query);
		List<M> ret = query.getResultList();
		if ((ret != null) && (ret.size() >= 0)) {
			return ret;
		}
		return new ArrayList<M>();
	}


	@Override
	public M getBy(String propertyName, Object value) {
		return this.getBy(entityClass, propertyName, value);
	}


	@Override
	public List<M> findByPropertyAndCondition(Map<String,String> params,String sqlCondition) {
		StringBuffer condition = new StringBuffer(" where 1=1 ");
		for(String key : params.keySet()){
			condition.append(" and x."+ key+"=:"+key);
		}
		Query query = em.createQuery(this.findAllQL+condition + " " + (sqlCondition == null?"":sqlCondition));
		if ((params != null) && (params.size() > 0)) {
			for (Iterator localIterator = params.keySet().iterator(); localIterator
					.hasNext();) {
				Object key = localIterator.next();
				query.setParameter(key.toString(), params.get(key));
			}
		}
		repositoryHelper.applyEnableQueryCache(query);
		List<M> list = query.getResultList();
		if ((list != null) && (list.size() > 0)) {
			return list;
		}
		return Lists.newArrayList();
	}

	@Override
	public List<M> findByProperty(Map<String,String> params) {
		return findByPropertyAndCondition(params,null);
	}
	
	public void flush(){
		em.flush();
	}
}
