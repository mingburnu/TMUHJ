package com.asiaworld.tmuhj.module.apply.customer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
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
		customer = getEntity();
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

			// DataSet<Customer> ds =
			// customerService.getEditedData(initDataSet(),
			// customer.getSerNo());
			// setDs(ds);
			getRequest().setAttribute("entity", null);
			getRequest().setAttribute("title", "用戶-新增");
			getRequest().setAttribute("customer", customer);
			getRequest().setAttribute("success", "新增成功");
			getRequest().setAttribute("displayShow", "display: block;");
			getRequest().setAttribute("alertShow", "display: block;");
			return LIST;
		}
	}

	@Override
	public String update() throws Exception {
		customer = customerService.update(getEntity(), getLoginUser());
		setEntity(customer);

		System.out.println("java version" + System.getProperty("java.version"));
		System.out.println("lasturl: "
				+ getRequest()
						.getAttribute("javax.servlet.forward.request_uri"));
		getRequest().setAttribute("last",
				getRequest().getAttribute("javax.servlet.forward.request_uri"));

		// DataSet<Customer> ds = customerService.getEditedData(initDataSet(),
		// customer.getSerNo());
		// setDs(ds);
		getRequest().setAttribute("success", "修改成功");
		getRequest().setAttribute("show", "display: block;");
		return LIST;
	}

	
	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
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
