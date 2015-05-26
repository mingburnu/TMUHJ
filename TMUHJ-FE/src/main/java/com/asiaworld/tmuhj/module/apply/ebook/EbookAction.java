package com.asiaworld.tmuhj.module.apply.ebook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EbookAction extends GenericCRUDActionFull<Ebook> {

	@Autowired
	private Ebook ebook;

	@Autowired
	private EbookService ebookService;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

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
		getRequest().setAttribute("query", "apply.ebook.query.action");
		DataSet<Ebook> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));

		ds = ebookService.getBySql(ds, keywords);
		setDs(ds);
		return "ebook";
	}

	@Override
	public String list() throws Exception {
		if (StringUtils.isBlank(getRequest().getParameter("serNo"))
				|| !NumberUtils.isDigits(getRequest().getParameter("serNo"))) {
			addActionError("serNo Error");
		} else {
			if (ebookService.getBySerNo(Long.parseLong(getRequest()
					.getParameter("serNo"))) == null) {
				addActionError("Object Null");
			}
		}

		if (!hasActionErrors()) {
			ebook = ebookService.getBySerNo(Long.parseLong(getRequest()
					.getParameter("serNo")));

			List<ResourcesUnion> ebookResourcesUnionList = resourcesUnionService
					.getByEbkSerNo(Long.parseLong(getRequest().getParameter(
							"serNo")));

			List<String> ownerNameList = new ArrayList<String>();

			Iterator<ResourcesUnion> iterator = ebookResourcesUnionList
					.iterator();

			while (iterator.hasNext()) {
				ResourcesUnion datResourcesUnion = iterator.next();
				customer = datResourcesUnion.getCustomer();
				if (customer != null) {
					ownerNameList.add(customer.getName());
				}
			}

			String ownerNames = ownerNameList.toString().replace("[", "")
					.replace("]", "");
			getRequest().setAttribute("ebook", ebook);
			getRequest().setAttribute("ownerNames", ownerNames);

			if (StringUtils.isNotBlank(getRequest().getParameter("currentURL"))) {
				getRequest().setAttribute(
						"backURL",
						getRequest().getParameter("currentURL")
								.replace("@@@", "?").replace("^^^", "&"));
			}
		}
		return "e-detail";
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
		getRequest().setAttribute("owner", "apply.ebook.owner.action");

		DataSet<Ebook> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds = ebookService.getByCusSerNo(ds, cusSerNo);
		setDs(ds);

		return "ebook";
	}

	public String focus() throws Exception {
		String option = getRequest().getParameter("option");
		String keywords = getRequest().getParameter("keywords");

		getRequest().setAttribute("keywords", keywords);
		getRequest().setAttribute("option", option);
		getRequest().setAttribute("focus", "apply.ebook.focus.action");

		getEntity().setOption(option);
		getEntity().setKeywords(keywords);

		DataSet<Ebook> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds = ebookService.getByRestrictions(ds);
		setDs(ds);
		return "ebook";
	}
}
