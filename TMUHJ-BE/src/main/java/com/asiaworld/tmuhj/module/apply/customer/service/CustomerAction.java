package com.asiaworld.tmuhj.module.apply.customer.service;

import java.util.Iterator;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.ipRange.entity.IpRange;
import com.asiaworld.tmuhj.module.apply.ipRange.service.IpRangeService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerAction extends GenericCRUDActionFull<Customer> {

	private String[] checkItem;

	@Autowired
	private Customer customer;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private IpRange ipRange;

	@Autowired
	private IpRangeService ipRangeService;

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
		if (getEntity().getSerNo() != null) {
			customer = customerService.getBySerNo(getEntity().getSerNo());
			setEntity(customer);
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		getRequest()
				.setAttribute("option", getRequest().getParameter("option"));
		DataSet<Customer> ds = customerService.getByRestrictions(initDataSet());
		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (getEntity().getName().trim().equals("")
				|| getEntity().getName() == null) {
			addActionError("用戶名稱不可空白");
		}

		if (customerService.nameIsExist(getEntity())) {
			addActionError("用戶名稱已存在");
		}

		if (getEntity().getEmail() != null
				|| !getEntity().getEmail().equals("")) {
			if (!Pattern.compile(emailPattern).matcher(getEntity().getEmail())
					.matches()) {
				addActionError("email格式不正確");
			}
		}

		if (!hasActionErrors()) {
			customer = customerService.save(getEntity(), getLoginUser());
			setEntity(customer);

			addActionMessage("新增成功");
			return VIEW;
		} else {
			return EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (getEntity().getEmail() != null
				|| !getEntity().getEmail().equals("")) {
			if (!Pattern.compile(emailPattern).matcher(getEntity().getEmail())
					.matches()) {
				addActionError("email格式不正確");
			}
		}

		if (!hasActionErrors()) {
			customer = customerService.update(getEntity(), getLoginUser(),
					"name");
			setEntity(customer);
			addActionMessage("修改成功");
			return VIEW;
		} else {
			return EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		if (getEntity().getSerNo() <= 0) {
			addActionError("流水號不正確");
		} else if (customerService.getBySerNo(getEntity().getSerNo()) == null) {
			addActionError("沒有這個物件");
		}

		if (!hasActionErrors()) {
			Iterator<?> iterator = ipRangeService.getOwnerIpRangeByCusSerNo(
					getEntity().getSerNo()).iterator();
			while (iterator.hasNext()) {
				ipRange = (IpRange) iterator.next();
				ipRangeService.deleteBySerNo(ipRange.getSerNo());
			}

			customerService.deleteBySerNo(getEntity().getSerNo());

			DataSet<Customer> ds = customerService
					.getByRestrictions(initDataSet());
			setDs(ds);

			return LIST;
		} else {
			DataSet<Customer> ds = customerService
					.getByRestrictions(initDataSet());
			setDs(ds);
			return LIST;
		}
	}

	public String deleteChecked() throws Exception {
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
			int i = 0;
			while (i < checkItem.length) {
				if (customerService.getBySerNo(Long.parseLong(checkItem[i])) != null) {
					Iterator<?> iterator = ipRangeService
							.getOwnerIpRangeByCusSerNo(
									Long.parseLong(checkItem[i])).iterator();
					while (iterator.hasNext()) {
						ipRange = (IpRange) iterator.next();
						ipRangeService.deleteBySerNo(ipRange.getSerNo());
					}

					customerService.deleteBySerNo(Long.parseLong(checkItem[i]));
				}
				i++;
			}
			DataSet<Customer> ds = customerService
					.getByRestrictions(initDataSet());
			setDs(ds);
			addActionMessage("刪除成功");
			return LIST;
		} else {
			DataSet<Customer> ds = customerService
					.getByRestrictions(initDataSet());
			setDs(ds);
			return LIST;
		}
	}

	public String view() throws Exception {
		customer = customerService.getBySerNo(Long.parseLong(getRequest()
				.getParameter("viewSerNo")));
		setEntity(customer);
		return VIEW;
	}

	public String ajax() throws Exception {
		getRequest().setAttribute("customerUnits",
				customerService.getAllCustomers());
		return AJAX;
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
