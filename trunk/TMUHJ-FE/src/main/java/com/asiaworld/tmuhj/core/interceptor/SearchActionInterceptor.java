package com.asiaworld.tmuhj.core.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.asiaworld.tmuhj.core.apply.feLogs.FeLogs;
import com.asiaworld.tmuhj.core.apply.feLogs.FeLogsService;
import com.asiaworld.tmuhj.core.enums.Act;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 儲存檢索log
 * 
 * @author Roderick
 * @version 2015/1/20
 */
@SuppressWarnings("serial")
public class SearchActionInterceptor extends AbstractInterceptor {

	Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private AccountNumber accountNumber;

	@Autowired
	private FeLogs feLogs;

	@Autowired
	private FeLogsService feLogsService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String method = invocation.getProxy().getMethod();
		String result = invocation.invoke();

		if (method.equals("query")) {

			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			accountNumber = (AccountNumber) session.get("login");

			if (request.getParameter("recordPerPage") == null
					&& request.getParameter("recordPoint") == null) {
				feLogsService.save(new FeLogs(Act.綜合查詢, request.getParameter("keywords"),
						accountNumber.getCusSerNo(), accountNumber.getSerNo(),
						0, 0, 0), accountNumber);
			}
		}
		

		if (method.equals("focus")) {

			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			HttpServletRequest request = ServletActionContext.getRequest();
			accountNumber = (AccountNumber) session.get("login");

			if (request.getParameter("recordPerPage") == null
					&& request.getParameter("recordPoint") == null) {
				feLogsService.save(new FeLogs(Act.項目查詢, request.getParameter("keywords"),
						accountNumber.getCusSerNo(), accountNumber.getSerNo(),
						0, 0, 0), accountNumber);
			}
		}

		return result;
	}

}
