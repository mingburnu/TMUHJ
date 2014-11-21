package com.asiaworld.tmuhj.core.dao;

import org.apache.log4j.Logger;

import com.asiaworld.tmuhj.core.entity.GenericEntityFull;

/**
 * GenericDao
 * @author Roderick
 * @version 2014/9/29
 */
public abstract class GenericDaoFull<T extends GenericEntityFull> implements Dao<T> {
	
	protected final transient Logger log = Logger.getLogger(getClass());

}
