package com.asiaworld.tmuhj.core.apply.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

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
	public String edit() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		String keywords = getRequest().getParameter("keywords");

		getRequest().setAttribute("keywords", keywords);
		getRequest().setAttribute("list", "apply.customer.list.action");

		getEntity().setKeywords(keywords);

		DataSet<Customer> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));

		ds = customerService.getByRestrictions(ds);
		List<Customer> results = ds.getResults();
		for (int i = 0; i < results.size(); i++) {
			customer = results.get(i);
			customer.setDbAmount(resourcesUnionService.countTotalDb(customer));
			customer.setEbookAmount(resourcesUnionService
					.countTotalEbook(customer));
			customer.setJournalAmount(resourcesUnionService
					.countTotalJournal(customer));
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
