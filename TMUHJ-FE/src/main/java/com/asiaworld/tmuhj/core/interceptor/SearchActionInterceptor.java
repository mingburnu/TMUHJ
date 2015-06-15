package com.asiaworld.tmuhj.core.interceptor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.asiaworld.tmuhj.core.apply.enums.Act;
import com.asiaworld.tmuhj.core.apply.feLogs.FeLogs;
import com.asiaworld.tmuhj.core.apply.feLogs.FeLogsService;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 儲存檢索log
 * 
 * @author Roderick
 * @version 2015/1/20
 */
public class SearchActionInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5107110044080126585L;

	Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private AccountNumber accountNumber;

	@Autowired
	private FeLogs feLogs;

	@Autowired
	private FeLogsService feLogsService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();

		Map<String, Object> session = invocation.getInvocationContext()
				.getSession();

		String method = invocation.getProxy().getMethod();

		if (method.equals("list")) {
			if (StringUtils.isBlank(request.getParameter("keywords"))) {
				request.setAttribute("type", request.getParameter("type"));
				addActionError(invocation, "．請輸入關鍵字。");
				return "query";
			}
		}

		if (method.equals("focus")) {
			if (StringUtils.isBlank(request.getParameter("keywords"))
					|| StringUtils.isBlank(request.getParameter("option"))) {
				request.setAttribute("type", request.getParameter("type"));
				request.setAttribute("option", request.getParameter("option"));
				addActionError(invocation, "．請輸入關鍵字。");

				if (StringUtils.isBlank(request.getParameter("option"))) {
					request.setAttribute("type", "database");
					request.setAttribute("option", "中文題名");
					addActionError(invocation, "．請輸入選項。");
				}

				return "adv_query";
			}
		}

		String result = invocation.invoke();

		if (method.equals("list")) {

			accountNumber = (AccountNumber) session.get("login");

			if (request.getParameter("recordPerPage") == null
					&& request.getParameter("recordPoint") == null) {
				if (accountNumber.getSerNo() != null) {
					feLogsService.save(
							new FeLogs(Act.綜合查詢, request
									.getParameter("keywords"), accountNumber
									.getCustomer(), accountNumber, 0L, 0L, 0L),
							accountNumber);
				} else {
					feLogsService.save(
							new FeLogs(Act.綜合查詢, request
									.getParameter("keywords"), accountNumber
									.getCustomer(), null, 0L, 0L, 0L),
							accountNumber);
				}

			}
		}

		if (method.equals("focus")) {

			accountNumber = (AccountNumber) session.get("login");

			if (request.getParameter("recordPerPage") == null
					&& request.getParameter("recordPoint") == null) {
				if (accountNumber.getSerNo() != null) {
					feLogsService.save(
							new FeLogs(Act.項目查詢, request
									.getParameter("keywords"), accountNumber
									.getCustomer(), accountNumber, 0L, 0L, 0L),
							accountNumber);
				} else {
					feLogsService.save(
							new FeLogs(Act.項目查詢, request
									.getParameter("keywords"), accountNumber
									.getCustomer(), null, 0L, 0L, 0L),
							accountNumber);
				}
			}
		}

		return result;
	}

	private void addActionError(ActionInvocation invocation, String message) {
		Object action = invocation.getAction();
		if (action instanceof ValidationAware) {
			((ValidationAware) action).addActionError(message);
		}
	}
}
