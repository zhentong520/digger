package com.ko30.common.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.dao.BaseRepository;
import com.ko30.entity.model.AbstractEntity;

/**
 * 所有服务类的基类
 * 
 * @author carr
 *
 * @param <T>
 * @param <ID>
 */
@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
public abstract class BaseService<T extends AbstractEntity<ID>, ID extends Serializable> {

	// 初始化注入
	protected boolean hasInit = false;
	protected BaseRepository<T, ID> repository;

	@Autowired
	public void setGenericRepository(BaseRepository<T, ID> repository) {
		this.repository = repository;
	}

	// 初始化依赖注入
	public void init() {
		this.hasInit = true;
	}

	/**
	 * 保存单个实体
	 *
	 * @param m
	 *            实体
	 * @return 返回保存的实体
	 */
	public boolean save(T m) {
		T m1 = repository.save(m);
		if (m1 != null) {
			return true;
		}
		return false;
	}

	public Iterable<T> save(Iterable<T> entities) {
		Iterable<T> m1 = repository.save(entities);
		return m1;
	}

	public T saveObj(T m) {
		m = repository.save(m);
		if (m != null) {
			return m;
		}
		return null;
	}

	public T saveAndFlush(T m) {
		m = repository.save(m);
		 repository.flush();
		return m;
	}

	/**
	 * 更新单个实体
	 *
	 * @param m
	 *            实体
	 * @return 返回更新的实体, update操作返回false；add操作返回true；
	 */
	public boolean update(T m) {
		T m1 = repository.save(m);
		if (m1.equals(m)) {
			return false;
		}
		return true;
	}

	/**
	 * 根据主键删除相应实体
	 *
	 * @param id
	 *            主键
	 */
	@Transactional(rollbackFor = { Exception.class, RuntimeException.class })
	public boolean delete(ID id) {
		repository.delete(id);
		return true;
	}

	/**
	 * 删除实体
	 *
	 * @param m
	 *            实体
	 */
	public void delete(T m) {
		repository.delete(m);
	}

	/**
	 * 根据主键删除相应实体
	 *
	 * @param ids
	 *            实体
	 */
	public void delete(ID[] ids) {
		repository.delete(ids);
	}

	/**
	 * 按照主键查询
	 *
	 * @param id
	 *            主键
	 * @return 返回id对应的实体
	 */
	public T findOne(ID id) {
		T m = repository.findOne(id);
		return m;
	}

	/**
	 * 实体是否存在
	 *
	 * @param id
	 *            主键
	 * @return 存在 返回true，否则false
	 */
	public boolean exists(ID id) {
		return repository.exists(id);
	}

	/**
	 * 统计实体总数
	 *
	 * @return 实体总数
	 */
	public long count() {
		return repository.count();
	}

	public long count(String definedSql) {
		return repository.count(definedSql);
	}

	/**
	 * 查询所有实体
	 *
	 * @return
	 */
	public List<T> findAll() {
		return repository.findAll();
	}

	/**
	 * 按照顺序查询所有实体
	 *
	 * @param sort
	 * @return
	 */
	public List<T> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	/**
	 * 分页及排序查询实体
	 *
	 * @param pageable
	 *            分页及排序数据
	 * @return
	 */
	public Page<T> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	/**
	 * 按条件分页并排序查询实体
	 *
	 * @param searchable
	 *            条件
	 * @return
	 */
	public Page<T> findAll(Searchable searchable) {
		return repository.findAll(searchable);
	}

	public Page<T> findAll(String definedSql, Searchable searchable) {
		return repository.findAll(definedSql, searchable);
	}

	/**
	 * 组装自定义sql
	 * 
	 * @return
	 */
	public List<T> findAll(String definedSql) {
		return repository.findAll(definedSql);
	}

	/**
	 * 按条件不分页不排序查询实体
	 *
	 * @param searchable
	 *            条件
	 * @return
	 */
	public List<T> findAllWithNoPageNoSort(Searchable searchable) {
		searchable.removePageable();
		searchable.removeSort();
		return Lists.newArrayList(repository.findAll(searchable)
				.getContent());
	}

	/**
	 * 按条件排序查询实体(不分页)
	 *
	 * @param searchable
	 *            条件
	 * @return
	 */
	public List<T> findAllWithSort(Searchable searchable) {
		searchable.removePageable();
		return Lists.newArrayList(repository.findAll(searchable)
				.getContent());
	}

	/**
	 * 按条件分页并排序统计实体数量
	 *
	 * @param searchable
	 *            条件
	 * @return
	 */
	public Long count(Searchable searchable) {
		return repository.count(searchable);
	}

	public List<T> findByPropertyAndCondition(Map<String, String> params,
			String sqlCondition) {
		return repository.findByPropertyAndCondition(params,
				sqlCondition);
	}

	public List<T> findByProperty(Map<String, String> params) {
		return repository.findByProperty(params);
	}

	public List<T> query(String query, Map params, int begin, int max) {
		return this.repository.query(query, params, begin, max);
	}

	public T getObjByProperty(Class<T> clazz, String propertyName, String value) {
		return this.repository.getBy(clazz, propertyName, value);
	}

	public T getObjByProperty(String propertyName, String value) {
		return this.repository.getBy(propertyName, value);
	}

	public T getObjById(ID id) {
		return this.repository.findOne(id);
	}

	public T getBy(String propertyName, Object value) {
		return this.repository.getBy(propertyName, value);
	}

	public boolean isHasInit() {
		return hasInit;
	}

	public void setHasInit(boolean hasInit) {
		this.hasInit = hasInit;
	}

	public void setHasInit() {
		this.hasInit = true;
	}
}
