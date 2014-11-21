package j.index.core.dao;

import j.index.core.entity.GenericEntityFull;

import org.apache.log4j.Logger;

/**
 * GenericDao
 * @author Roderick
 * @version 2014/9/29
 */
public abstract class GenericDaoFull<T extends GenericEntityFull> implements Dao<T> {
	
	protected final transient Logger log = Logger.getLogger(getClass());

}
