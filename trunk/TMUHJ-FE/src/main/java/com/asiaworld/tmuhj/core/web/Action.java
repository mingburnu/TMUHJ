package com.asiaworld.tmuhj.core.web;

import com.asiaworld.tmuhj.core.entity.Entity;

/**
 * Action
 * @author Roderick
 * @version 2014/3/14
 */
public interface Action<T extends Entity> {

	public final static String INDEX = "index";

	public final static String SECTION = "section";
	
	public final static String EDIT = "edit";
	
	public final static String DELETE = "delete";
	
	public final static String LIST = "list";

}
