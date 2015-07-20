package com.asiaworld.tmuhj.core.dao;

import java.io.Serializable;
import java.util.Map;

import com.asiaworld.tmuhj.core.dao.DsQueryLanguage;

/**
 * DsQueryLanguage
 * @author Roderick
 * @version 2015/01/27
 */
public interface DsQueryLanguage extends Serializable {

	public String getSql();
	
	public void setSql(String sql);
	
	public Map<String, Object> getParameters();
	
	public DsQueryLanguage addParameter(String name, Object value);
	
}
