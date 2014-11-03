package j.index.core.service;

import j.index.core.entity.Entity;
import j.index.core.model.DataSet;
import j.index.core.security.accountNumber.entity.AccountNumber;

/**
 * Service
 * @author David Hsu
 * @version 2014/3/11
 */
public interface Service<T extends Entity> {

	public T save(T entity, AccountNumber user)  throws Exception;
	
	public T getBySerNo(Long serNo) throws Exception;
	
	public DataSet<T> getByRestrictions(DataSet<T> ds) throws Exception;
	
	public T update(T entity, AccountNumber user) throws Exception;
	
	public T update(T entity, AccountNumber user, String... ignoreProperties) throws Exception;
	
	public void deleteBySerNo(Long serNo) throws Exception;
	
}
