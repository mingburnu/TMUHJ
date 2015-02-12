package com.asiaworld.tmuhj.core.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.asiaworld.tmuhj.core.entity.GenericEntityFull;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.FormModel;
import com.asiaworld.tmuhj.core.model.Pager;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * GenericAction
 * @author Roderick
 * @version 2014/11/21
 */
@SuppressWarnings("serial")
public abstract class GenericActionFull<T extends GenericEntityFull> extends ActionSupport implements Action<T> {

	protected final transient Logger log = Logger.getLogger(getClass());
	
	private FormModel formModel;
	
	@Autowired
	private T entity;
	
	@Autowired
	private DataSet<T> ds;
	
	@Autowired
	private Pager pager;
	
	/**
	 * Get Http Session
	 * @return
	 */
	protected Map<String, Object> getSession() {
		return ActionContext.getContext().getSession();
	}
	
	/**
	 * Get Http Servlet Request
	 * @return
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	
	/**
	 * Get Http Servlet Response
	 * @return
	 */
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	/**
	 * 取得登入者
	 * @return
	 */
	protected AccountNumber getLoginUser() {
		return (AccountNumber) getSession().get(LOGIN);
	}
	
	/**
	 * disable all input
	 */
	protected void disableAllInput() {
		formModel = new FormModel(true);
	}
	
	/**
	 * disable all input
	 * @param ignoreProperties
	 */
	protected void disableAllInput(String... ignoreProperties) {
		formModel = new FormModel(true, ignoreProperties);
	}
	
	/**
	 * entity資料to DataSet
	 * @param entity
	 * @return
	 */
	protected DataSet<T> initDataSet() {
		ds.setEntity(entity);
		ds.setPager(pager);
		return ds;
	}

	public FormModel getFormModel() {
		return formModel;
	}

	public void setFormModel(FormModel formModel) {
		this.formModel = formModel;
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
