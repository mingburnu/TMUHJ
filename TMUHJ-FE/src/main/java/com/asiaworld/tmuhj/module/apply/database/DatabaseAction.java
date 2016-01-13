package com.asiaworld.tmuhj.module.apply.database;

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
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyers;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyersService;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DatabaseAction extends GenericWebActionFull<Database> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -365109377798301323L;

	@Autowired
	private Database database;

	@Autowired
	private DatabaseService databaseService;

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
		getRequest().setAttribute(
				"list",
				getRequest().getContextPath()
						+ "/crud/apply.database.list.action");

		DataSet<Database> ds = databaseService.getByRestrictions(initDataSet());

		if (ds.getResults().size() == 0 && ds.getPager().getCurrentPage() > 1) {
			Double lastPage = Math.ceil(ds.getPager().getTotalRecord()
					.doubleValue()
					/ ds.getPager().getRecordPerPage().doubleValue());
			ds.getPager().setCurrentPage(lastPage.intValue());
			ds = databaseService.getByRestrictions(ds);
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
							+ "/crud/apply.database.owner.action");
			DataSet<Database> ds = databaseService.getByCusSerNo(initDataSet());

			if (ds.getResults().size() == 0
					&& ds.getPager().getCurrentPage() > 1) {
				Double lastPage = Math.ceil(ds.getPager().getTotalRecord()
						.doubleValue()
						/ ds.getPager().getRecordPerPage().doubleValue());
				ds.getPager().setCurrentPage(lastPage.intValue());
				ds = databaseService.getByCusSerNo(ds);
			}

			setDs(ds);
		}

		return LIST;

	}

	public String focus() throws Exception {
		getRequest().setAttribute(
				"focus",
				getRequest().getContextPath()
						+ "/crud/apply.database.focus.action");
		DataSet<Database> ds = databaseService.getByOption(initDataSet());

		if (ds.getResults().size() == 0 && ds.getPager().getCurrentPage() > 1) {
			Double lastPage = Math.ceil(ds.getPager().getTotalRecord()
					.doubleValue()
					/ ds.getPager().getRecordPerPage().doubleValue());
			ds.getPager().setCurrentPage(lastPage.intValue());
			ds = databaseService.getByOption(ds);
		}

		setDs(ds);

		return LIST;
	}

	public String view() throws Exception {
		if (hasEntity()) {
			resourcesUnion = resourcesUnionService.getByObjSerNo(getEntity()
					.getSerNo(), database.getClass());

			resourcesBuyers = resourcesUnion.getResourcesBuyers();

			List<ResourcesUnion> dbResourcesUnionList = resourcesUnionService
					.getByDatSerNo(getEntity().getSerNo());

			List<String> ownerNameList = new ArrayList<String>();

			Iterator<ResourcesUnion> iterator = dbResourcesUnionList.iterator();

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
			database.setResourcesBuyers(resourcesBuyers);
			database.setBackURL(getEntity().getBackURL());

			setDs(initDataSet());
			setEntity(database);
		} else {
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}

		return VIEW;
	}

	protected boolean hasEntity() throws Exception {
		if (!getEntity().hasSerNo()) {
			return false;
		}

		database = databaseService.getBySerNo(getEntity().getSerNo());
		if (database == null) {
			return false;
		}

		return true;
	}
}
