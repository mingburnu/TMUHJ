package j.index.core.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 檢查登入session
 * @author David Hsu
 * @version 2014/3/26
 */
@SuppressWarnings("serial")
public class LoginSessionInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		if(session.get(Action.LOGIN) == null) {
			return Action.INPUT;
		}
		
		return invocation.invoke();
	}

}
