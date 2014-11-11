package j.index.module.apply.customer.service;

import j.index.core.model.DataSet;
import j.index.core.web.GenericCRUDAction;
import j.index.module.apply.customer.entity.Customer;
import j.index.module.apply.resourcesUnion.service.ResourcesUnionService;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerAction extends GenericCRUDAction<Customer> {

	private Long[] checkItem;

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
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		DataSet<Customer> ds = customerService.getByRestrictions(initDataSet());
		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update() throws Exception {
		customer = customerService.update(getEntity(), getLoginUser());
		setEntity(customer);
		return LIST;
	}

	@Override
	public String delete() throws Exception {
		int i = 0;
		while (i < checkItem.length) {
			if (!checkItem[i].equals(null)) {
				customerService.deleteBySerNo(customerService.getBySerNo(
						checkItem[i]).getSerNo());
			

			System.out.println("sserNo= " + checkItem[i]);}
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
}
