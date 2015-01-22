package com.asiaworld.tmuhj.core.interceptor;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiaworld.tmuhj.core.security.accountNumber.entity.AccountNumber;
import com.asiaworld.tmuhj.module.apply.beLogs.entity.BeLogs;
import com.asiaworld.tmuhj.module.apply.beLogs.service.BeLogsService;
import com.asiaworld.tmuhj.module.enums.Act;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 儲存登入log
 * 
 * @author Roderick
 * @version 2015/1/20
 */
@SuppressWarnings("serial")
public class LoginActionInterceptor extends AbstractInterceptor {

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
				beLogs = new BeLogs(Act.登入, accountNumber.getSerNo(),
						accountNumber.getCusSerNo());

				beLogsService.save(beLogs, accountNumber);
			}
		}

		return result;
	}

}
