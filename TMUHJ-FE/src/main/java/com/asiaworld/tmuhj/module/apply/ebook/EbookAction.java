package com.asiaworld.tmuhj.module.apply.ebook;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericWebActionFull;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EbookAction extends GenericWebActionFull<Ebook> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2746869215531950704L;

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
		getRequest()
				.setAttribute(
						"list",
						getRequest().getContextPath()
								+ "/crud/apply.ebook.list.action");

		DataSet<Ebook> ds = ebookService.getByRestrictions(initDataSet());

		if (ds.getResults().size() == 0 && ds.getPager().getCurrentPage() > 1) {
			ds.getPager().setCurrentPage(
					(int) Math.ceil(ds.getPager().getTotalRecord()
							/ ds.getPager().getRecordPerPage()));
			ds = ebookService.getByRestrictions(ds);
		}

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String owner() throws Exception {
		if (getEntity().getCusSerNo() == null
				|| getEntity().getCusSerNo() <= 0
				|| customerService.getBySerNo(getEntity().getCusSerNo()) == null) {
			addActionError("Customer Null");
		}

		if (!hasActionErrors()) {
			getRequest().setAttribute(
					"owner",
					getRequest().getContextPath()
							+ "/crud/apply.ebook.owner.action");

			DataSet<Ebook> ds = ebookService.getByCusSerNo(initDataSet());

			if (ds.getResults().size() == 0
					&& ds.getPager().getCurrentPage() > 1) {
				ds.getPager().setCurrentPage(
						(int) Math.ceil(ds.getPager().getTotalRecord()
								/ ds.getPager().getRecordPerPage()));
				ds = ebookService.getByCusSerNo(ds);
			}

			setDs(ds);
		}

		return LIST;
	}

	public String focus() throws Exception {
		getRequest().setAttribute(
				"focus",
				getRequest().getContextPath()
						+ "/crud/apply.ebook.focus.action");

		DataSet<Ebook> ds = ebookService.getByOption(initDataSet());

		if (ds.getResults().size() == 0 && ds.getPager().getCurrentPage() > 1) {
			ds.getPager().setCurrentPage(
					(int) Math.ceil(ds.getPager().getTotalRecord()
							/ ds.getPager().getRecordPerPage()));
			ds = ebookService.getByOption(ds);
		}

		setDs(ds);

		return LIST;
	}

	public String view() throws Exception {
		if (hasEntity()) {
			List<ResourcesUnion> ebookResourcesUnionList = resourcesUnionService
					.getByEbkSerNo(getEntity().getSerNo());

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
			getRequest().setAttribute("ownerNames", ownerNames);
			ebook.setBackURL(getEntity().getBackURL());

			setDs(initDataSet());
			setEntity(ebook);
		} else {
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}

		return VIEW;
	}

	protected boolean hasEntity() throws Exception {
		if (!getEntity().hasSerNo()) {
			return false;
		}

		ebook = ebookService.getBySerNo(getEntity().getSerNo());
		if (ebook == null) {
			return false;
		}

		return true;
	}
}
