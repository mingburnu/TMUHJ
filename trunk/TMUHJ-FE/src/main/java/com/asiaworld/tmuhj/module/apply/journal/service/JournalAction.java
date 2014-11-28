package com.asiaworld.tmuhj.module.apply.journal.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.customer.service.CustomerService;
import com.asiaworld.tmuhj.module.apply.journal.entity.Journal;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.entity.ResourcesBuyers;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.service.ResourcesBuyersService;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.entity.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class JournalAction extends GenericCRUDActionFull<Journal> {

	@Autowired
	private Journal journal;

	@Autowired
	private JournalService journalService;

	@Autowired
	private ResourcesUnion resourcesUnion;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Autowired
	private ResourcesBuyers resourcesBuyers;

	@Autowired
	private ResourcesBuyersService resourcesBuyersService;

	@Autowired
	private Customer customer;

	@Autowired
	private CustomerService customerService;

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

		DataSet<Journal> ds = journalService.getBySql(initDataSet());
		setDs(ds);
		return "journal";
	}

	@Override
	public String list() throws Exception {
		journal = journalService.getBySerNo(Long.parseLong(getRequest()
				.getParameter("serNo")));

		resourcesUnion = resourcesUnionService.getByObjSerNo(
				Long.parseLong(getRequest().getParameter("serNo")),
				journal.getClass());

		// resourcesBuyers=resourcesBuyersService.getBySerNo(resourcesUnion.getResSerNo());
		resourcesBuyers = resourcesBuyersService.getBySerNo(3212L);

		List<?> journalResourcesUnionList = resourcesUnionService
				.getByJouSerNo(Long.parseLong(getRequest()
						.getParameter("serNo")));
		
		System.out.println("journalResourcesUnionList: "
				+ journalResourcesUnionList.size());
		List<String> ownerNameList = new ArrayList<String>();

		Iterator<?> iterator = journalResourcesUnionList.iterator();

		while (iterator.hasNext()) {
			ResourcesUnion jouResourcesUnion = (ResourcesUnion) iterator.next();
			customer = customerService.getBySerNo(jouResourcesUnion.getCusSerNo());
			ownerNameList.add(customer.getName());
		}

		String ownerNames = ownerNameList.toString().replace("[", "")
				.replace("]", "");

		getRequest().setAttribute("journal", journal);
		getRequest().setAttribute("resourcesBuyers", resourcesBuyers);
		getRequest().setAttribute("ownerNames", ownerNames);
		return "j-detail";
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

	public String ownerJournal() throws Exception {
		getRequest().setAttribute("cusSerNo",
				getRequest().getParameter("cusSerNo"));
		DataSet<Journal> ds = journalService.getByCusSerNo(initDataSet());
		setDs(ds);

		return "journal";
	}

	public String focus() throws Exception {
		getRequest().setAttribute("keywords",
				getRequest().getParameter("keywords"));
		getRequest()
				.setAttribute("option", getRequest().getParameter("option"));
		DataSet<Journal> ds = journalService.getByRestrictions(initDataSet());
		setDs(ds);
		return "journal";
	}
}
