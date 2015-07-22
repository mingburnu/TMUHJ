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

/**
 * 儲存檢索log
 * 
 * @author Roderick
 * @version 2015/1/20
 */
public class SearchActionInterceptor extends RootInterceptor {

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
			String item = request.getParameter("item");
			if (StringUtils.isBlank(request.getParameter("entity.indexTerm"))) {
				addActionError(invocation, "．請輸入關鍵字。");
			} else {
				invocation
						.getInvocationContext()
						.getValueStack()
						.set("indexTerm",
								request.getParameter("entity.indexTerm"));
			}

			if (request.getParameter("pager.recordPerPage") == null) {
				if (StringUtils.isBlank(item)) {
					invocation.getInvocationContext().getValueStack()
							.set("item", "database");
					addActionError(invocation, "item exception");
				} else if (item.equals("database") || item.equals("ebook")
						|| item.equals("journal") || item.equals("customer")) {
					invocation.getInvocationContext().getValueStack()
							.set("item", item);
				} else {
					invocation.getInvocationContext().getValueStack()
							.set("item", "database");
					addActionError(invocation, "item exception");
				}
			}

			if (hasActionErrors(invocation)) {
				return "query";
			}
		}

		if (method.equals("focus")) {
			String item = request.getParameter("item");
			if (StringUtils.isBlank(request.getParameter("entity.indexTerm"))) {
				addActionError(invocation, "．請輸入關鍵字。");
			} else {
				invocation
						.getInvocationContext()
						.getValueStack()
						.set("indexTerm",
								request.getParameter("entity.indexTerm"));
			}

			if (StringUtils.isBlank(request.getParameter("entity.option"))) {
				addActionError(invocation, "．請輸入選項。");
			} else {
				invocation.getInvocationContext().getValueStack()
						.set("option", request.getParameter("entity.option"));
			}

			if (request.getParameter("pager.recordPerPage") == null) {
				if (StringUtils.isBlank(item)) {
					invocation.getInvocationContext().getValueStack()
							.set("item", "database");
					invocation.getInvocationContext().getValueStack()
							.set("option", "中文題名");
					addActionError(invocation, "item exception");
				} else if (item.equals("database") || item.equals("ebook")
						|| item.equals("journal")) {
					invocation.getInvocationContext().getValueStack()
							.set("item", item);
				} else {
					invocation.getInvocationContext().getValueStack()
							.set("item", "database");
					invocation.getInvocationContext().getValueStack()
							.set("option", "中文題名");
					addActionError(invocation, "item exception");
				}
			}

			if (hasActionErrors(invocation)) {
				return "adv_query";
			}
		}

		String result = invocation.invoke();

		if (method.equals("list")) {

			accountNumber = (AccountNumber) session.get("login");

			if (request.getParameter("pager.recordPerPage") == null
					&& request.getParameter("pager.recordPoint") == null) {
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

			if (request.getParameter("pager.recordPerPage") == null) {
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

	private boolean hasActionErrors(ActionInvocation invocation) {
		Object action = invocation.getAction();
		return ((ValidationAware) action).hasActionErrors();
	}
}
