package com.asiaworld.tmuhj.core.web;

import com.asiaworld.tmuhj.core.entity.Entity;

/**
 * Action
 * 
 * @author Roderick
 * @version 2014/12/11
 */
public interface Action<T extends Entity> {

	public final static String INDEX = "index";

	public final static String SECTION = "section";

	public final static String EDIT = "edit";

	public final static String DELETE = "delete";

	public final static String LIST = "list";

	public final static String VIEW = "view";

	public final static String AJAX = "ajax";

	public final static String QUEUE = "queue";
	
}
