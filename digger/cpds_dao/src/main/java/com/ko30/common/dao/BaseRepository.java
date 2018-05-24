package com.ko30.common.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.ko30.common.base.entity.search.Searchable;
import com.ko30.common.base.entity.search.query.support.IPageList;
import com.ko30.common.base.entity.search.query.support.IQueryObject;

/**
 * <p>抽象DAO层基类 提供一些简便方法<br/>
 * 具体使用请参考测试用例：{@see com.sinosoft.arch.core.common.repository.UserRepository}
 * <p/>
 * 想要使用该接口需要在spring配置文件的jpa:repositories中添加
 * factory-class="com.sinosoft.arch.core.common.repository.support.SimpleBaseRepositoryFactoryBean"
 * <p/>
 * <p>泛型 ： M 表示实体类型；ID表示主键类型
 * <p>User: pengxinxin
 * <p>Date: 13-1-12 下午4:46
 * <p>Version: 1.0  JpaRepository  CrudRepository
 */
@NoRepositoryBean
public interface BaseRepository<M, ID extends Serializable> extends JpaRepository<M, ID>,Serializable {

    /**
     * 根据主键删除
     *
     * @param ids
     */
    public void delete(ID[] ids);

    /*
   * (non-Javadoc)
   * @see org.springframework.data.repository.CrudRepository#findAll()
   */
    List<M> findAll();

    /*
     * (non-Javadoc)
     * @see org.springframework.data.repository.PagingAndSortingRepository#findAll(org.springframework.data.domain.Sort)
     */
    List<M> findAll(Sort sort);


    /**
     * Returns a {@link Page} of entities meeting the paging restriction provided in the {@code Pageable} object.
     *
     * @param pageable
     * @return a page of entities
     */
    Page<M> findAll(Pageable pageable);

    /**
     * 根据条件查询所有
     * 条件 + 分页 + 排序
     *
     * @param searchable
     * @return
     */
    public Page<M> findAll(Searchable searchable);
    
    public Page<M> findAll(String definedSql,Searchable searchable);
    
    /**
     * 自定义sql
     * 根据条件查询所有
     * @param searchable
     * @return
     */
    public List<M> findAll(String definedSql);


    /**
     * 根据条件统计所有记录数
     *
     * @param searchable
     * @return
     */
    public long count(Searchable searchable);

	public List<M> query(String query, Map params, int begin, int max);

	public M getBy(Class<M> clazz, final String propertyName,
			final Object value);

	public M getBy(String propertyName, String value);

	public IPageList list(IQueryObject properties);

	public List find(String condition, Map params, int begin, int max);

	public M getBy(String propertyName, Object value);

	public List<M> findByProperty(Map<String,String> params);
	
	public long count(String definedSql);
	
	public List<M> findByPropertyAndCondition(Map<String,String> params,String sqlCondition);

	public void flush();
	
}
