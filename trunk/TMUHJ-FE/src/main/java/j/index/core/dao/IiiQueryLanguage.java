package j.index.core.dao;

import java.io.Serializable;
import java.util.Map;

/**
 * IiiQueryLanguage
 * @author David Hsu
 * @version 2014/3/12
 */
public interface IiiQueryLanguage extends Serializable {

	public String getSql();
	
	public void setSql(String sql);
	
	public Map<String, Object> getParameters();
	
	public IiiQueryLanguage addParameter(String name, Object value);
	
}
