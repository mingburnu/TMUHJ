package com.asiaworld.tmuhj.module.security.web;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.security.accountNumber.entity.AccountNumber;
import com.asiaworld.tmuhj.core.security.accountNumber.service.AccountNumberService;
import com.asiaworld.tmuhj.core.web.GenericWebActionFull;
import com.asiaworld.tmuhj.module.apply.beLogs.entity.BeLogs;
import com.asiaworld.tmuhj.module.apply.beLogs.service.BeLogsService;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.customer.service.CustomerService;

/**
 * LoginAction
 * 
 * @author Roderick
 * @version 2015/01/19
 */
@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AuthorizationAction extends GenericWebActionFull<AccountNumber> {

	@Autowired
	private AccountNumberService userService;

	@Autowired
	private AccountNumber user;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private Customer customer;

	@Autowired
	private DataSet<AccountNumber> ds;

	@Autowired
	private BeLogsService beLogsService;

	@Autowired
	private BeLogs beLogs;

	public void validateLogin() throws Exception {
		boolean checkLogin = true;
		if (StringUtils.isEmpty(user.getUserId())
				|| StringUtils.isEmpty(user.getUserPw())) {
			getRequest().setAttribute("idPwNull", "請輸入帳號和密碼。");
			addActionError("請輸入帳號和密碼。");
			checkLogin = false;
		}

		if (checkLogin) { // 帳號密碼皆有輸入時才進行檢核
			boolean isTrueUserId = false;
			boolean isTrueUserPw = false;
			try {
				isTrueUserId = userService.checkUserId(user);
				isTrueUserPw = userService.checkUserPw(user);

			} catch (Exception e) {
				log.error(ExceptionUtils.getStackTrace(e));
				throw new Exception(e);
			}

			if (isTrueUserId) {
				if (!isTrueUserPw) {// 使用者存在才進行密碼檢核
					getRequest().setAttribute("error", "您輸入的密碼不正確，請重新輸入。");
					addActionError("您輸入的密碼不正確，請重新輸入。");
				}
			} else {
				getRequest().setAttribute("error", "您輸入的帳號名稱不正確，請重新輸入。");
				addActionError("您輸入的帳號名稱不正確，請重新輸入。");
			}
		}
	}

	/**
	 * 登入
	 * 
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		try {
			ds.setEntity(user);
			ds = userService.getByRestrictions(ds);

		} catch (Exception e) {
			log.error(ExceptionUtils.getStackTrace(e));
			throw new Exception(e);
		}

		AccountNumber loginUser = ds.getResults().get(0);
		loginUser.setCustomer(customerService.getBySerNo(loginUser
				.getCusSerNo()));

		getSession().put(LOGIN, loginUser);
		return INDEX;
	}

	/**
	 * 登出
	 * 
	 * @return
	 * @throws Exception
	 */
	public String logout() throws Exception {
		if (getSession().get(LOGIN) != null) {
			getSession().put(LOGIN, null);
		}

		return LOGIN;
	}

	public AccountNumber getUser() {
		return user;
	}

	public void setUser(AccountNumber user) {
		this.user = user;
	}

	public DataSet<AccountNumber> getDs() {
		return ds;
	}

	public void setDs(DataSet<AccountNumber> ds) {
		this.ds = ds;
	}

}
