package com.asiaworld.tmuhj.core.security.accountNumber.service;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.enums.Status;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.security.accountNumber.entity.AccountNumber;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.customer.service.CustomerService;

/**
 * 使用者
 * 
 * @author Roderick
 * @version 2014/9/29
 */
@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AccountNumberAction extends GenericCRUDActionFull<AccountNumber> {

	private String[] checkItem;

	@Autowired
	AccountNumber accountNumber;

	@Autowired
	AccountNumberService accountNumberService;

	@Autowired
	Customer customer;

	@Autowired
	CustomerService customerService;

	@Autowired
	DataSet<Customer> dsCustomer;

	@Override
	public void validateSave() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateUpdate() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void validateDelete() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String query() throws Exception {
		dsCustomer.setEntity(customer);
		dsCustomer = customerService.getByRestrictions(dsCustomer);
		if (getEntity().getSerNo() != null) {
			accountNumber = accountNumberService.getBySerNo(getEntity()
					.getSerNo());
			setEntity(accountNumber);
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		DataSet<AccountNumber> ds = accountNumberService
				.getByRestrictions(initDataSet());
		List<AccountNumber> results = ds.getResults();

		int i = 0;
		while (i < results.size()) {
			results.get(i).setCustomer(
					customerService.getBySerNo(results.get(i).getCusSerNo()));
			i++;
		}

		ds.setResults(results);

		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		if (getEntity().getUserId() == null
				|| getEntity().getUserId().trim().equals("")) {
			addActionError("用戶代碼不得空白");
		}

		if (getEntity().getUserPw() == null
				|| getEntity().getUserPw().trim().equals("")) {
			addActionError("用戶密碼不得空白");
		}

		if (getEntity().getUserName() == null
				|| getEntity().getUserName().trim().equals("")) {
			addActionError("用戶姓名不得空白");
		}

		if (getEntity().getCusSerNo() < 1) {
			addActionError("用戶名稱必選");
		}

		if (getEntity().getUserId() != null
				&& !getEntity().getUserId().trim().equals("")) {
			if (accountNumberService.userIdIsExist(getEntity())) {
				addActionError("用戶代碼已存在");
			}

			if (getEntity().getUserId().trim().equals("guest")) {
				addActionError("不可使用guest作為用戶代碼");
			}
		}

		if (!hasActionErrors()) {
			accountNumber = accountNumberService.save(getEntity(),
					getLoginUser());
			accountNumber.setCustomer(customerService.getBySerNo(accountNumber
					.getCusSerNo()));
			setEntity(accountNumber);
			return VIEW;
		} else {
			return EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		if (getEntity().getUserPw() == null
				|| getEntity().getUserPw().trim().equals("")) {
			addActionError("用戶密碼不得空白");
		}

		if (getEntity().getUserName() == null
				|| getEntity().getUserName().trim().equals("")) {
			addActionError("用戶姓名不得空白");
		}

		if (getEntity().getCusSerNo() < 1) {
			addActionError("用戶名稱必選");
		}
		if (!hasActionErrors()) {
			if (getEntity().getUserPw() == null
					|| getEntity().getUserPw().trim().equals("")) {
				accountNumber = accountNumberService.update(getEntity(),
						getLoginUser(), "userId", "userPw");
			} else {
				accountNumber = accountNumberService.update(getEntity(),
						getLoginUser(), "userId");
			}

			setEntity(accountNumber);
			addActionMessage("修改成功");
			return VIEW;
		} else {
			return EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		accountNumberService.deleteBySerNo(getEntity().getSerNo());
		disableAllInput();
		addActionMessage("檔案已刪除");
		return DELETE;
	}

	public String invalidChecked() throws Exception {
		if (checkItem == null || checkItem.length == 0) {
			addActionError("請選擇一筆或一筆以上的資料");
		} else {
			int i = 0;
			while (i < checkItem.length) {
				if (!NumberUtils.isDigits(String.valueOf(checkItem[i]))
						|| Long.parseLong(checkItem[i]) < 1) {
					addActionError(checkItem[i] + "為不可利用的流水號");
				}
				i++;
			}
		}

		if (!hasActionErrors()) {
			int j = 0;
			while (j < checkItem.length) {
				if (accountNumberService.getBySerNo(Long
						.parseLong(checkItem[j])) != null) {
					accountNumber = accountNumberService.getBySerNo(Long
							.parseLong(checkItem[j]));
					accountNumber.setStatus(Status.不生效);
					accountNumberService.update(accountNumberService
							.getBySerNo(Long.parseLong(checkItem[j])),
							accountNumber);
				}
				j++;
			}
			DataSet<AccountNumber> ds = accountNumberService
					.getByRestrictions(initDataSet());
			List<AccountNumber> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setCustomer(
						customerService
								.getBySerNo(results.get(i).getCusSerNo()));
				i++;
			}

			ds.setResults(results);

			setDs(ds);
			addActionMessage("更新成功");
			return LIST;
		} else {
			DataSet<AccountNumber> ds = accountNumberService
					.getByRestrictions(initDataSet());
			List<AccountNumber> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setCustomer(
						customerService
								.getBySerNo(results.get(i).getCusSerNo()));
				i++;
			}

			ds.setResults(results);

			setDs(ds);
			return LIST;
		}
	}

	public String validChecked() throws Exception {
		if (checkItem == null || checkItem.length == 0) {
			addActionError("請選擇一筆或一筆以上的資料");
		} else {
			int i = 0;
			while (i < checkItem.length) {
				if (!NumberUtils.isDigits(String.valueOf(checkItem[i]))
						|| Long.parseLong(checkItem[i]) < 1) {
					addActionError(checkItem[i] + "為不可利用的流水號");
				}
				i++;
			}
		}

		if (!hasActionErrors()) {
			int j = 0;
			while (j < checkItem.length) {
				if (accountNumberService.getBySerNo(Long
						.parseLong(checkItem[j])) != null) {
					accountNumber = accountNumberService.getBySerNo(Long
							.parseLong(checkItem[j]));
					accountNumber.setStatus(Status.生效);
					accountNumberService.update(accountNumberService
							.getBySerNo(Long.parseLong(checkItem[j])),
							accountNumber);
				}
				j++;
			}
			DataSet<AccountNumber> ds = accountNumberService
					.getByRestrictions(initDataSet());
			List<AccountNumber> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setCustomer(
						customerService
								.getBySerNo(results.get(i).getCusSerNo()));
				i++;
			}

			ds.setResults(results);

			setDs(ds);
			addActionMessage("更新成功");
			return LIST;
		} else {
			DataSet<AccountNumber> ds = accountNumberService
					.getByRestrictions(initDataSet());
			List<AccountNumber> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setCustomer(
						customerService
								.getBySerNo(results.get(i).getCusSerNo()));
				i++;
			}

			ds.setResults(results);

			setDs(ds);
			return LIST;
		}
	}

	public String view() throws Exception {
		accountNumber = accountNumberService.getBySerNo(Long
				.parseLong(getRequest().getParameter("viewSerNo")));
		accountNumber.setCustomer(customerService.getBySerNo(accountNumber
				.getCusSerNo()));
		setEntity(accountNumber);
		return VIEW;
	}

	/**
	 * @return the dsCustomer
	 */
	public DataSet<Customer> getDsCustomer() {
		return dsCustomer;
	}

	/**
	 * @param dsCustomer
	 *            the dsCustomer to set
	 */
	public void setDsCustomer(DataSet<Customer> dsCustomer) {
		this.dsCustomer = dsCustomer;
	}

	/**
	 * @return the checkItem
	 */
	public String[] getCheckItem() {
		return checkItem;
	}

	/**
	 * @param checkItem
	 *            the checkItem to set
	 */
	public void setCheckItem(String[] checkItem) {
		this.checkItem = checkItem;
	}

}
