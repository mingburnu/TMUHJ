package com.asiaworld.tmuhj.core.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 儲存檢索log
 * 
 * @author Roderick
 * @version 2015/1/20
 */
@SuppressWarnings("serial")
public class PageActionInterceptor extends AbstractInterceptor {
	@Autowired
	private AccountNumber accountNumber;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		if (request.getSession().getAttribute("importList") != null) {
			request.getSession().removeAttribute("cellNames");
			request.getSession().removeAttribute("importList");
			request.getSession().removeAttribute("total");
			request.getSession().removeAttribute("normal");
			request.getSession().removeAttribute("abnormal");
			request.getSession().removeAttribute("checkItemMap");
		}
		
		return invocation.invoke();
	}
}
