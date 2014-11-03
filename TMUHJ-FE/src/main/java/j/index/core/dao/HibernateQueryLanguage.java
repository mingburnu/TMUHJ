package j.index.core.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * HQL
 * @author David Hsu
 * @version 2014/3/12
 */
public class HibernateQueryLanguage implements IiiQueryLanguage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 456532219336289896L;

	private String sql;
	
	private Map<String, Object> parameters = new HashMap<>();
	
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
	public IiiQueryLanguage addParameter(String name, Object value) {
		parameters.put(name, value);
		return this;
	}

}
