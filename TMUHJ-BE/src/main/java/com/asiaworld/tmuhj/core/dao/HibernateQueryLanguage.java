package com.asiaworld.tmuhj.core.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * HQL
 * @author Roderick
 * @version 2014/11/6
 */
public class HibernateQueryLanguage implements DsQueryLanguage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 456532219336289896L;

	private String sql;
	
	private Map<String, Object> parameters = new HashMap<String, Object>();
	
	@Override
	public String getSql() {
		return sql;
	}
	
	@Override
	public void setSql(String sql) {
		this.sql = sql;
	}

	@Override
	public Map<String, Object> getParameters() {
		return parameters;
	}

	@Override
	public DsQueryLanguage addParameter(String name, Object value) {
		parameters.put(name, value);
		return this;
	}

}
