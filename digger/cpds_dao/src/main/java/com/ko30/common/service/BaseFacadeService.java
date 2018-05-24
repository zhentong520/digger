package com.ko30.common.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.ko30.common.base.entity.search.Searchable;
import com.ko30.entity.model.AbstractEntity;

/**
 * 
 * @author carr
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseFacadeService<T extends AbstractEntity<ID>, ID extends Serializable> {

	/**
	 * 保存单个实体
	 *
	 * @param m
	 *            实体
	 * @return 返回保存的实体
	 */
	public boolean save(T m);

	public Iterable<T> save(Iterable<T> entities);

	public T saveObj(T m);

	public T saveAndFlush(T m);

	/**
	 * 更新单个实体
	 *
	 * @param m
	 *            实体
	 * @return 返回更新的实体, update操作返回false；add操作返回true；
	 */
	public boolean update(T m);

	/**
	 * 根据主键删除相应实体
	 *
	 * @param id
	 *            主键
	 */
	public boolean delete(ID id);

	/**
	 * 删除实体
	 *
	 * @param m
	 *            实体
	 */
	public void delete(T m);

	/**
	 * 根据主键删除相应实体
	 *
	 * @param ids
	 *            实体
	 */
	public void delete(ID[] ids);

	/**
	 * 按照主键查询
	 *
	 * @param id
	 *            主键
	 * @return 返回id对应的实体
	 */
	public T findOne(ID id);

	/**
	 * 实体是否存在
	 *
	 * @param id
	 *            主键
	 * @return 存在 返回true，否则false
	 */
	public boolean exists(ID id);

	/**
	 * 统计实体总数
	 *
	 * @return 实体总数
	 */
	public long count();

	public long count(String definedSql);

	/**
	 * 查询所有实体
	 *
	 * @return
	 */
	public List<T> findAll();

	/**
	 * 按照顺序查询所有实体
	 *
	 * @param sort
	 * @return
	 */
	public List<T> findAll(Sort sort);

	/**
	 * 分页及排序查询实体
	 *
	 * @param pageable
	 *            分页及排序数据
	 * @return
	 */
	public Page<T> findAll(Pageable pageable);

	/**
	 * 按条件分页并排序查询实体
	 *
	 * @param searchable
	 *            条件
	 * @return
	 */
	public Page<T> findAll(Searchable searchable);

	public Page<T> findAll(String definedSql, Searchable searchable);

	/**
	 * 组装自定义sql
	 * 
	 * @return
	 */
	public List<T> findAll(String definedSql);

	/**
	 * 按条件不分页不排序查询实体
	 *
	 * @param searchable
	 *            条件
	 * @return
	 */
	public List<T> findAllWithNoPageNoSort(Searchable searchable);

	/**
	 * 按条件排序查询实体(不分页)
	 *
	 * @param searchable
	 *            条件
	 * @return
	 */
	public List<T> findAllWithSort(Searchable searchable);

	/**
	 * 按条件分页并排序统计实体数量
	 *
	 * @param searchable
	 *            条件
	 * @return
	 */
	public Long count(Searchable searchable);

	public List<T> findByPropertyAndCondition(Map<String, String> params,
			String sqlCondition);

	public List<T> findByProperty(Map<String, String> params);

	public List<T> query(String query, Map params, int begin, int max);

	public T getObjByProperty(Class<T> clazz, String propertyName, String value);

	public T getObjByProperty(String propertyName, String value);

	public T getObjById(ID id);

	public T getBy(String propertyName, Object value);
}
