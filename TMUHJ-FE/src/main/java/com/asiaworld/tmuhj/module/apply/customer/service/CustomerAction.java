package com.asiaworld.tmuhj.module.apply.customer.service;

import java.util.List;

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
		getRequest().setAttribute("keywords",
				getRequest().getParameter("keywords"));
		getRequest().setAttribute("query", "apply.customer.query.action");

		DataSet<Customer> ds = customerService.getBySql(initDataSet());
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
	public String list() throws Exception {
		// TODO Auto-generated method stub
		return null;
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
