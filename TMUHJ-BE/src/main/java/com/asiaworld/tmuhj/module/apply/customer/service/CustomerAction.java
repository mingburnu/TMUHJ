package com.asiaworld.tmuhj.module.apply.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.ipRange.entity.IpRange;
import com.asiaworld.tmuhj.module.apply.ipRange.service.IpRangeService;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerAction extends GenericCRUDActionFull<Customer> {

	private Long[] checkItem;

	private String lastUrl;

	@Autowired
	private Customer customer;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Autowired
	private IpRange ipRange;

	@Autowired
	private IpRangeService ipRangeService;

	@Autowired
	private DataSet<IpRange> dsIpRange;

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
			getRequest().setAttribute("modifyShow", "display: block;");
			getRequest().setAttribute("customerName", customer.getName());
		} else {
			getRequest().setAttribute("addShow", "display: block;");
		}

		return LIST;
	}

	@Override
	public String list() throws Exception {
		DataSet<Customer> ds = customerService.getByRestrictions(initDataSet());
		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		if (getEntity().getName().trim().equals("")
				|| getEntity().getName() == null) {
			getRequest().setAttribute("addShow", "display: block;");
			getRequest().setAttribute("alertShow", "display: block;");
			getRequest().setAttribute("space", "用戶名稱不可空白");
			addActionError("用戶名稱不可空白");
			return LIST;
		} else if (customerService.nameIsExist(getEntity())) {
			getRequest().setAttribute("addShow", "display: block;");
			getRequest().setAttribute("alertShow", "display: block;");
			getRequest().setAttribute("nameRepeat", "用戶名稱已存在");
			addActionError("用戶名稱已存在");
			return LIST;
		} else {
			customer = customerService.save(getEntity(), getLoginUser());
			setEntity(customer);
			System.out.println("ssserNo:" + customer.getSerNo());

			// getRequest().setAttribute("entity", null);
			getRequest().setAttribute("title", "用戶-新增");
			getRequest().setAttribute("customer", customer);
			getRequest().setAttribute("success", "新增成功");
			getRequest().setAttribute("displayShow", "display: block;");
			getRequest().setAttribute("alertShow", "display: block;");
			getRequest().setAttribute("back2", "history.go(-2);");
			list();
			return LIST;
		}
	}

	@Override
	public String update() throws Exception {
		customer = customerService.update(getEntity(), getLoginUser(), "name");
		setEntity(customer);
		System.out.println("ssserNo:" + customer.getSerNo());

		// getRequest().setAttribute("entity", null);
		getRequest().setAttribute("title", "用戶-修改");
		getRequest().setAttribute("customer", customer);
		getRequest().setAttribute("success", "修改成功");
		getRequest().setAttribute("displayShow", "display: block;");
		getRequest().setAttribute("alertShow", "display: block;");
		getRequest().setAttribute("back2", "history.go(-2);");
		list();
		return LIST;

	}

	@Override
	public String delete() throws Exception {
		customerService.deleteBySerNo(getEntity().getSerNo());
		setEntity(null);
		getRequest().setAttribute("title", "用戶-刪除");
		getRequest().setAttribute("success", "刪除成功");
		getRequest().setAttribute("alertShow", "display: block;");
		getRequest().setAttribute("back2", "history.go(-2);");
		return LIST;
	}

	public String deleteChecked() throws Exception {
		int i = 0;
		while (i < checkItem.length) {
			if (checkItem[i] > 0) {
				if (customerService.getBySerNo(checkItem[i]) != null) {
					customerService.deleteBySerNo(checkItem[i]);
				}
				System.out.println("serNos= " + checkItem[i]);
			}
			i++;
		}
		return LIST;
	}

	public String view() throws Exception {
		customer = customerService.getBySerNo(Long.parseLong(getRequest()
				.getParameter("viewSerNo")));
		getRequest().setAttribute("title", "用戶-檢視");
		getRequest().setAttribute("customer", customer);
		getRequest().setAttribute("displayShow", "display: block;");
		getRequest().setAttribute("back1", "history.go(-1);");
		return LIST;
	}

	public String ipMaintain() throws NumberFormatException, Exception {
		dsIpRange.setEntity(ipRange);
		dsIpRange.setPager(getPager());

		dsIpRange=ipRangeService.getByCusSerNo(dsIpRange, Long.parseLong(getRequest().getParameter("cusSerNo")));
		
		getRequest().setAttribute("ipMaintain", "display: block;");
		getRequest().setAttribute("cusSerNo", getRequest().getParameter("cusSerNo"));
		getRequest().setAttribute("dsIpRange", dsIpRange);
		return LIST;
	}

	/**
	 * @return the checkItem
	 */
	public Long[] getCheckItem() {
		return checkItem;
	}

	/**
	 * @param checkItem
	 *            the checkItem to set
	 */
	public void setCheckItem(Long[] checkItem) {
		this.checkItem = checkItem;
	}

	/**
	 * @return the lastUrl
	 */
	public String getLastUrl() {
		return lastUrl;
	}

	/**
	 * @param lastUrl
	 *            the lastUrl to set
	 */
	public void setLastUrl(String lastUrl) {
		this.lastUrl = lastUrl;
	}

}
