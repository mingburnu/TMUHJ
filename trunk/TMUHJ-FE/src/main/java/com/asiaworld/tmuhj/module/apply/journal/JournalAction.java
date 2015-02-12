package com.asiaworld.tmuhj.module.apply.journal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyers;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyersService;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

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
		String keywords = getRequest().getParameter("keywords");

		getRequest().setAttribute("keywords", keywords);
		getRequest().setAttribute("query", "apply.journal.query.action");
		DataSet<Journal> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));

		ds = journalService.getBySql(ds, keywords);
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

		resourcesBuyers = resourcesBuyersService.getBySerNo(resourcesUnion
				.getResSerNo());

		List<?> journalResourcesUnionList = resourcesUnionService
				.getByJouSerNo(Long.parseLong(getRequest()
						.getParameter("serNo")));

		List<String> ownerNameList = new ArrayList<String>();

		Iterator<?> iterator = journalResourcesUnionList.iterator();

		while (iterator.hasNext()) {
			ResourcesUnion jouResourcesUnion = (ResourcesUnion) iterator.next();
			customer = customerService.getBySerNo(jouResourcesUnion
					.getCusSerNo());
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

	public String owner() throws Exception {
		long cusSerNo = 0;
		if (NumberUtils.isDigits(getRequest().getParameter("cusSerNo"))
				&& Long.parseLong(getRequest().getParameter("cusSerNo")) > 0) {
			cusSerNo = Long.parseLong(getRequest().getParameter("cusSerNo"));
		}

		getRequest().setAttribute("cusSerNo", cusSerNo);
		getRequest().setAttribute("owner", "apply.journal.owner.action");

		DataSet<Journal> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds = journalService.getByCusSerNo(ds, cusSerNo);
		setDs(ds);

		return "journal";
	}

	public String focus() throws Exception {
		String option = getRequest().getParameter("option");
		String keywords = getRequest().getParameter("keywords");

		getRequest().setAttribute("keywords", keywords);
		getRequest().setAttribute("option", option);
		getRequest().setAttribute("focus", "apply.journal.focus.action");

		getEntity().setOption(option);
		getEntity().setKeywords(keywords);
		
		DataSet<Journal> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds = journalService.getByRestrictions(ds);
		setDs(ds);
		return "journal";
	}
}
