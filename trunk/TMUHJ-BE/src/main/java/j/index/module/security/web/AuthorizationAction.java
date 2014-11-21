package j.index.module.security.web;

import java.util.HashSet;
import j.index.core.model.DataSet;
import j.index.core.security.accountNumber.entity.AccountNumber;
import j.index.core.security.accountNumber.service.AccountNumberService;
import j.index.core.web.GenericWebActionFull;
import j.index.module.apply.customer.entity.Customer;
import j.index.module.apply.customer.service.CustomerService;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * LoginAction
 * 
 * @author Roderick
 * @version 2014/10/2
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

	public void validateLogin() throws Exception {
		boolean checkLogin = true;
		if (StringUtils.isEmpty(user.getUserId())
				|| StringUtils.isEmpty(user.getUserPw())) {
			getRequest().setAttribute("idPwNull", "請輸入帳號和密碼");
			addActionError("請輸入帳號和密碼");
			checkLogin = false;
		}

		if (checkLogin) { // 帳號密碼皆有輸入時才進行檢核
			boolean isValidUser = false;
			try {
				isValidUser = userService.checkUser(user);
			} catch (Exception e) {
				log.error(ExceptionUtils.getStackTrace(e));
				throw new Exception(e);
			}

			if (!isValidUser) {
				getRequest().setAttribute("error", "帳號或密碼不正確");
				addActionError("帳號密碼錯誤，請重新輸入");
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

		ds.getResults()
				.get(0)
				.setCustomer(
						customerService.getBySerNo(ds.getResults().get(0)
								.getCusSerNo()));

		HashSet<String> allcUid=userService.getAllcUid();
		
		getSession().put(LOGIN, ds.getResults().get(0));
		getRequest().setAttribute("allcUid", allcUid);

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
