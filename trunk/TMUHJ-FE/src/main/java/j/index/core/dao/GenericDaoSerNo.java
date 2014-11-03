package j.index.core.dao;

import j.index.core.entity.GenericEntitySerNo;

import org.apache.log4j.Logger;

/**
 * GenericDaoSerNo
 * 
 * @author Roderick
 * @version 2014/10/15
 */
public abstract class GenericDaoSerNo<T extends GenericEntitySerNo> implements
		Dao<T> {

	protected final transient Logger log = Logger.getLogger(getClass());

}
