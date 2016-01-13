package com.asiaworld.tmuhj.core.interceptor;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumberService;
import com.asiaworld.tmuhj.core.apply.enums.Act;
import com.asiaworld.tmuhj.core.apply.feLogs.FeLogs;
import com.asiaworld.tmuhj.core.apply.feLogs.FeLogsService;
import com.opensymphony.xwork2.ActionInvocation;

public class LoginLogInterceptor extends RootInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5641049812365809693L;

	@Autowired
	private AccountNumber accountNumber;

	@Autowired
	private AccountNumberService accountNumberService;

	@Autowired
	private FeLogsService feLogsService;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		removeErrorParameters(invocation);

		Map<String, Object> session = invocation.getInvocationContext()
				.getSession();
		String method = invocation.getProxy().getMethod();

		if (method.equals("logout")) {
			accountNumber = (AccountNumber) session.get("login");
			if (accountNumber != null
					&& accountNumber.hasSerNo()
					&& accountNumberService
							.getBySerNo(accountNumber.getSerNo()) != null) {
				feLogsService
						.save(new FeLogs(Act.登出, null, accountNumber
								.getCustomer(), accountNumber, null, null, null),
								accountNumber);
			} else {
				session.clear();
			}
		}

		String result = invocation.invoke();

		if (method.equals("login")) {
			accountNumber = (AccountNumber) session.get("login");
			if (accountNumber != null
					&& accountNumber.hasSerNo()
					&& accountNumberService
							.getBySerNo(accountNumber.getSerNo()) != null
					&& StringUtils.isEmpty((String) session.get("record"))) {
				feLogsService
						.save(new FeLogs(Act.登入, null, accountNumber
								.getCustomer(), accountNumber, null, null, null),
								accountNumber);
			}
		}

		return result;
	}
}
