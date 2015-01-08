package com.asiaworld.tmuhj.module.apply.customer.service;

import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
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
		String keywords = getRequest().getParameter("keywords");

		getRequest().setAttribute("keywords", keywords);
		getRequest().setAttribute("query", "apply.customer.query.action");

		String recordPerPage = getRequest().getParameter("recordPerPage");
		String recordPoint = getRequest().getParameter("recordPoint");
		DataSet<Customer> ds = initDataSet();
		Pager pager = ds.getPager();

		if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0 && recordPoint != null
				&& NumberUtils.isDigits(recordPoint)
				&& Integer.parseInt(recordPoint) >= 0) {
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			pager.setCurrentPage(Integer.parseInt(recordPoint)
					/ Integer.parseInt(recordPerPage) + 1);
			pager.setOffset(Integer.parseInt(recordPerPage)
					* (pager.getCurrentPage() - 1));
			pager.setRecordPoint(Integer.parseInt(recordPoint));
			ds.setPager(pager);
		} else if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0 && recordPoint == null) {
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			pager.setRecordPoint(pager.getOffset());
			ds.setPager(pager);
		} else {
			pager.setRecordPoint(pager.getOffset());
			ds.setPager(pager);
		}

		ds = customerService.getBySql(ds,keywords);
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
