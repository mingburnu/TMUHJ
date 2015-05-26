package com.asiaworld.tmuhj.core.interceptor;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * CRUD Action Interceptor
 * 
 * @author Roderick
 * @version 2015/1/20
 */
@SuppressWarnings("serial")
public class CrudActionInterceptor extends AbstractInterceptor {
	@Autowired
	private AccountNumber accountNumber;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		if (!invocation.getAction().toString().contains("beLogs")
				&& !invocation.getAction().toString().contains("feLogs")) {

			String method = invocation.getProxy().getMethod();
			HttpServletRequest request = ServletActionContext.getRequest();

			List<String> methodList = Arrays.asList("queue", "paginate",
					"getCheckedItem", "allCheckedItem", "clearCheckedItem",
					"importData");

			if (!methodList.contains(method)) {
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

		if (invocation.getAction().toString().contains("ipRange")) {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			accountNumber = (AccountNumber) session.get("login");

			if (accountNumber.getRole().getRole().equals("管理員")) {
				HttpServletResponse response = ServletActionContext
						.getResponse();
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			}
		}

		if (invocation.getAction().toString().contains("customer")) {
			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			accountNumber = (AccountNumber) session.get("login");

			if (accountNumber.getRole().getRole().equals("管理員")) {
				String method = invocation.getProxy().getMethod();

				if (!method.equals("json") && !method.equals("ajax")) {
					HttpServletResponse response = ServletActionContext
							.getResponse();
					response.sendError(HttpServletResponse.SC_FORBIDDEN);
				}
			}

		}
		return invocation.invoke();
	}
}
