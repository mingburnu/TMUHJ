package com.asiaworld.tmuhj.core.apply.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericWebActionFull;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerAction extends GenericWebActionFull<Customer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2177287834510404474L;

	@Autowired
	private Customer customer;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	protected void validateSave() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validateUpdate() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validateDelete() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public String add() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String edit() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		getRequest().setAttribute("list", "apply.customer.list.action");

		DataSet<Customer> ds = customerService.getByRestrictions(initDataSet());

		if (ds.getResults().size() == 0 && ds.getPager().getCurrentPage() > 1) {
			ds.getPager().setCurrentPage(
					(int) (ds.getPager().getTotalRecord()
							/ ds.getPager().getRecordPerPage() + 1));
			ds = customerService.getByRestrictions(ds);
		}

		List<Customer> results = ds.getResults();
		for (int i = 0; i < results.size(); i++) {
			customer = results.get(i);
			customer.setDbAmount(resourcesUnionService.countTotalDb(customer
					.getSerNo()));
			customer.setEbookAmount(resourcesUnionService
					.countTotalEbook(customer.getSerNo()));
			customer.setJournalAmount(resourcesUnionService
					.countTotalJournal(customer.getSerNo()));
			results.remove(i);
			results.add(i, customer);
		}

		ds.setResults(results);
		setDs(ds);

		return "customer";
	}

	@Override
	public String save() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
}
