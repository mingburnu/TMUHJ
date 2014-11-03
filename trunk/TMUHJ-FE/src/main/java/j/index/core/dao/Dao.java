package j.index.core.dao;

import j.index.core.entity.Entity;
import j.index.core.model.DataSet;

import java.util.List;

/**
 * Dao
 * 
 * @author Roderick
 * @version 2014/9/29
 */
public interface Dao<T extends Entity> {

	/**
	 * Find entity by the primary key
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public T findBySerNo(Long serNo) throws Exception;

	/**
	 * Find list by conditions
	 * 
	 * @param restrictions
	 * @return
	 * @throws Exception
	 */
	public List<T> findByRestrictions(IiiRestrictions restrictions)
			throws Exception;

	/**
	 * Find list by page conditions
	 * 
	 * @param restrictions
	 * @return
	 * @throws Exception
	 */
	public DataSet<T> findByRestrictions(IiiRestrictions restrictions,
			DataSet<T> ds) throws Exception;

	/**
	 * Save the entity to database
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public T save(T entity) throws Exception;

	/**
	 * Update the entity from database
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void update(T entity) throws Exception;

	/**
	 * Delete the entity from database
	 * 
	 * @param entity
	 * @throws Exception
	 */
	public void delete(T entity) throws Exception;

	/**
	 * Delete the entity by primary id
	 * 
	 * @param id
	 * @throws Exception
	 */
	public void deleteBySerNo(Long serNo) throws Exception;

	/**
	 * Use the Query Language
	 * 
	 * @param iQL
	 * @return
	 */
	public List<?> findByQL(IiiQueryLanguage iQL);

}
