package com.asiaworld.tmuhj.core.web;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.asiaworld.tmuhj.core.entity.Entity;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;

/**
 * GenericAction
 * 
 * @author Roderick
 * @version 2014/11/21
 */
public abstract class GenericAction<T extends Entity> extends ActionSupport
		implements Action<T> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8461510694567060010L;

	protected final transient Logger log = Logger.getLogger(getClass());

	protected final transient Set<String> errorMessages = new HashSet<String>();

	@Autowired
	private T entity;

	@Autowired
	private DataSet<T> ds;

	@Autowired
	private Pager pager;

	/**
	 * Get Http Session
	 * 
	 * @return
	 */
	protected Map<String, Object> getSession() {
		return ActionContext.getContext().getSession();
	}

	/**
	 * Get Http Servlet Request
	 * 
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	/**
	 * Get Http Servlet Response
	 * 
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	/**
	 * 取得登入者
	 * 
	 * @return
	 */
	protected AccountNumber getLoginUser() {
		return (AccountNumber) getSession().get(LOGIN);
	}

	/**
	 * entity資料to DataSet
	 * 
	 * @param entity
	 * @return
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	protected DataSet<T> initDataSet() {
		ds.setEntity(entity);
		ds.setPager(Pager.getChangedPager(getPager().getRecordPerPage(),
				getPager().getRecordPoint(), getPager()));
		return ds;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public DataSet<T> getDs() {
		return ds;
	}

	public void setDs(DataSet<T> ds) {
		this.ds = ds;
	}

	public Pager getPager() {
		return pager;
	}

	public void setPager(Pager pager) {
		this.pager = pager;
	}
}
