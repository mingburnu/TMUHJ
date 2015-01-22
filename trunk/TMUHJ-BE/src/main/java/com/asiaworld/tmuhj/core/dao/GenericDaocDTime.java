package com.asiaworld.tmuhj.core.dao;

import org.apache.log4j.Logger;

import com.asiaworld.tmuhj.core.entity.GenericEntitycDTime;

/**
 * GenericDao
 * 
 * @author Roderick
 * @version 2015/01/19
 */
public abstract class GenericDaocDTime<T extends GenericEntitycDTime>
		implements Dao<T> {

	protected final transient Logger log = Logger.getLogger(getClass());

}
