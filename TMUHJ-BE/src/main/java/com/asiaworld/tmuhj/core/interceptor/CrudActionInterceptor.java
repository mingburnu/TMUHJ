package com.asiaworld.tmuhj.core.interceptor;

import java.util.Arrays;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 儲存登入log
 * 
 * @author Roderick
 * @version 2015/1/20
 */
@SuppressWarnings("serial")
public class CrudActionInterceptor extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		if (!invocation.getAction().toString().contains("beLogs")
				&& !invocation.getAction().toString().contains("feLogs")) {

			String method = invocation.getProxy().getMethod();
			HttpServletRequest request = ServletActionContext.getRequest();

			List<String> methods = Arrays.asList("queue", "paginate",
					"getCheckedItem", "allCheckedItem", "clearCheckedItem",
					"importData");

			if (!methods.contains(method)) {
				if (request.getSession().getAttribute("importList") != null) {
					request.getSession().removeAttribute("cellNames");
					request.getSession().removeAttribute("importList");
					request.getSession().removeAttribute("total");
					request.getSession().removeAttribute("normal");
					request.getSession().removeAttribute("abnormal");
					request.getSession().removeAttribute("checkItemMap");
				}
			}
		}

		return invocation.invoke();
	}
}
