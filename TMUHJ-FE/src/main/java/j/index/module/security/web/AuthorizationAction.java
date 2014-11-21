package j.index.module.security.web;

import java.util.ArrayList;

import j.index.core.model.DataSet;
import j.index.core.security.accountNumber.entity.AccountNumber;
import j.index.core.security.accountNumber.service.AccountNumberService;
import j.index.core.web.GenericWebActionFull;
import j.index.module.apply.customer.entity.Customer;
import j.index.module.apply.customer.service.CustomerService;
import j.index.module.apply.ipRange.entity.IpRange;
import j.index.module.apply.ipRange.service.IpRangeService;

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
	private IpRangeService ipRangeService;

	@Autowired
	private DataSet<AccountNumber> ds;

	public void validateLogin() throws Exception {
		boolean checkLogin = true;
		if (StringUtils.isEmpty(user.getUserId())) {
			addActionError("請輸入帳號");
			checkLogin = false;
		}

		if (StringUtils.isEmpty(user.getUserPw())) {
			addActionError("請輸入密碼");
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
				addActionError("帳號密碼錯誤，請重新輸入");
			}
		}
	}

	public boolean validateIp(String ip, ArrayList<IpRange> ipList) {
		String[] ipNum = ip.split("\\.");
		for (int i = 0; i < ipList.size(); i++) {
			String[] start = ipList.get(i).getIpRangeStart().split("\\.");
			String[] end = ipList.get(i).getIpRangeEnd().split("\\.");

			if (ipNum[0].equals(start[0]) && ipNum[1].equals(start[1])
					&& ipNum[2].equals(start[2])) {
				if (Integer.parseInt(ipNum[3]) >= Integer.parseInt(start[3])
						&& Integer.parseInt(ipNum[3]) <= Integer
								.parseInt(end[3])) {
					return true;
				}
			}
		}
		return false;
	}

	public long getMatchCusSerNo(String ip, ArrayList<IpRange> ipList) {
		String[] ipNum = ip.split("\\.");
		for (int i = 0; i < ipList.size(); i++) {
			String[] start = ipList.get(i).getIpRangeStart().split("\\.");
			String[] end = ipList.get(i).getIpRangeEnd().split("\\.");

			if (ipNum[0].equals(start[0]) && ipNum[1].equals(start[1])
					&& ipNum[2].equals(start[2])) {
				if (Integer.parseInt(ipNum[3]) >= Integer.parseInt(start[3])
						&& Integer.parseInt(ipNum[3]) <= Integer
								.parseInt(end[3])) {
					return ipList.get(i).getCusSerNo();
				}
			}
		}
		return 0L;
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

		getSession().put(LOGIN, ds.getResults().get(0));
		return INDEX;
	}

	/**
	 * 訪客進入
	 * 
	 * @return
	 * @throws Exception
	 */
	public String userEntry() throws Exception {
		String ip = getRequest().getRemoteAddr();
		ArrayList<IpRange> ipList = ipRangeService.getIpList();

		if (validateIp(ip, ipList) && getLoginUser() == null) {
			try {
				user = new AccountNumber();
				user.setUserId("guest");
				user.setCusSerNo(getMatchCusSerNo(ip, ipList));
				ds.setEntity(user);
				ds = userService.getByRestrictions(ds);
			} catch (Exception e) {
				log.error(ExceptionUtils.getStackTrace(e));
				throw new Exception(e);
			}
			customer = customerService.getBySerNo(user.getCusSerNo());
			customer.setContactUserName("訪客");
			user.setCustomer(customer);
			getSession().put(LOGIN, user);
			return INDEX;
		} else if (getLoginUser() != null) {
			return INDEX;
		} else {
			return LOGIN;
		}

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
		String ip = getRequest().getRemoteAddr();
		ArrayList<IpRange> ipList = ipRangeService.getIpList();
		if (validateIp(ip, ipList) && getLoginUser() == null) {
			try {
				user = new AccountNumber();
				user.setUserId("guest");
				user.setCusSerNo(getMatchCusSerNo(ip, ipList));
				ds.setEntity(user);
				ds = userService.getByRestrictions(ds);
			} catch (Exception e) {
				log.error(ExceptionUtils.getStackTrace(e));
				throw new Exception(e);
			}
			customer = customerService.getBySerNo(user.getCusSerNo());
			customer.setContactUserName("訪客");
			user.setCustomer(customer);
			getSession().put(LOGIN, user);
			return INDEX;
		} else {
			return LOGIN;
		}
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
