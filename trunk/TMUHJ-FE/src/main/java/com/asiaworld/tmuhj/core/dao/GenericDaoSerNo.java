package com.asiaworld.tmuhj.core.dao;

import org.apache.log4j.Logger;

import com.asiaworld.tmuhj.core.entity.GenericEntitySerNo;

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
