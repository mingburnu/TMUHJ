package com.asiaworld.tmuhj.core.interceptor;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.asiaworld.tmuhj.core.apply.beLogs.BeLogs;
import com.asiaworld.tmuhj.core.apply.beLogs.BeLogsService;
import com.asiaworld.tmuhj.core.apply.enums.Act;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 儲存登入log
 * 
 * @author Roderick
 * @version 2015/1/20
 */
public class LoginActionInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5641049812365809693L;

	Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private BeLogsService beLogsService;

	@Autowired
	private BeLogs beLogs;

	@Autowired
	private AccountNumber accountNumber;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String method = invocation.getProxy().getMethod();
		String result = invocation.invoke();

		if (method.equals("login")) {

			Map<String, Object> session = ActionContext.getContext()
					.getSession();
			accountNumber = (AccountNumber) session.get("login");

			if (accountNumber != null) {
				beLogs = new BeLogs(Act.登入, accountNumber,
						accountNumber.getCustomer());

				beLogsService.save(beLogs, accountNumber);
			}
		}

		return result;
	}

}
