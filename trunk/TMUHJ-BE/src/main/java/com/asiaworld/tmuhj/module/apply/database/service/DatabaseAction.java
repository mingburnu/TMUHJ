package com.asiaworld.tmuhj.module.apply.database.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.enums.RCategory;
import com.asiaworld.tmuhj.core.enums.RType;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.customer.service.CustomerService;
import com.asiaworld.tmuhj.module.apply.database.entity.Database;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.entity.ResourcesBuyers;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.service.ResourcesBuyersService;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.entity.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DatabaseAction extends GenericCRUDActionFull<Database> {

	private String[] checkItem;

	private String[] cusSerNo;

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
		getRequest().setAttribute("allCustomers",
				customerService.getAllCustomers());
		if (getEntity().getSerNo() != null) {
			database = databaseService.getBySerNo(getEntity().getSerNo());
			Iterator<?> iterator = resourcesUnionService
					.getResourcesUnionsByObj(getEntity(), Database.class)
					.iterator();

			List<Customer> customers = new ArrayList<Customer>();

			long resourcesBuyersSerNo = 0;
			while (iterator.hasNext()) {
				resourcesUnion = (ResourcesUnion) iterator.next();
				customers.add(customerService.getBySerNo(resourcesUnion
						.getCusSerNo()));
				resourcesBuyersSerNo = resourcesUnion.getResSerNo();
			}

			resourcesBuyers = resourcesBuyersService
					.getBySerNo(resourcesBuyersSerNo);
			database.setCustomers(customers);
			setEntity(database);
		} else {
			List<Customer> customers = new ArrayList<Customer>();
			database.setCustomers(customers);
			setEntity(database);
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		getRequest()
				.setAttribute("option", getRequest().getParameter("option"));
		DataSet<Database> ds = databaseService.getByRestrictions(initDataSet());
		List<Database> results = ds.getResults();

		int i = 0;
		while (i < results.size()) {
			results.get(i).setResourcesBuyers(
					resourcesBuyersService.getBySerNo(resourcesUnionService
							.getByObjSerNo(results.get(i).getSerNo(),
									Database.class).getResSerNo()));
			i++;
		}

		ds.setResults(results);
		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		if (getEntity().getUrl() == null
				|| getEntity().getUrl().trim().equals("")) {
			addActionError("URL必需填寫");
		} else {
			String regex = "(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?(https://)?[a-zA-Z_0-9\\-]+(\\.\\w[`~!@#$%^&*()_-{[}]|;:<>?,./a-zA-Z0-9\u0000-\uffff\\+=]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(getEntity().getUrl());
			if (!matcher.matches()) {
				addActionError("URL格式不正確");
			}
		}

		if (cusSerNo == null || cusSerNo.length == 0) {
			addActionError("至少選擇一筆以上購買單位");
		} else {
			int i = 0;
			while (i < cusSerNo.length) {
				if (!NumberUtils.isDigits(String.valueOf(cusSerNo[i]))
						|| Long.parseLong(cusSerNo[i]) < 1) {
					addActionError(cusSerNo[i] + "為不可利用的流水號");
				}
				i++;
			}
		}

		if (getRequest().getParameter("resourcesBuyers.rCategory") == null
				|| getRequest().getParameter("resourcesBuyers.rCategory")
						.equals("")) {
			addActionError("請選擇資源類型");
		}

		if (getRequest().getParameter("resourcesBuyers.rType") == null
				|| getRequest().getParameter("resourcesBuyers.rType")
						.equals("")) {
			addActionError("請選擇資源種類");
		}

		if (!hasActionErrors()) {
			database = databaseService.save(getEntity(), getLoginUser());

			resourcesBuyers = resourcesBuyersService
					.save(new ResourcesBuyers(getRequest().getParameter(
							"resourcesBuyers.startDate"), getRequest()
							.getParameter("resourcesBuyers.maturityDate"),
							RCategory.valueOf(getRequest().getParameter(
									"resourcesBuyers.rCategory")), RType
									.valueOf(getRequest().getParameter(
											"resourcesBuyers.rType")), database
									.getDbChtTitle(), database.getDbEngTitle()),
							getLoginUser());

			int i = 0;
			while (i < cusSerNo.length) {
				resourcesUnionService.save(
						new ResourcesUnion(Long.parseLong(cusSerNo[i]),
								resourcesBuyers.getSerNo(), 0, database
										.getSerNo(), 0), getLoginUser());

				i++;
			}

			resourcesUnion = resourcesUnionService.getByObjSerNo(
					database.getSerNo(), Database.class);
			database.setResourcesBuyers(resourcesBuyersService
					.getBySerNo(resourcesUnion.getResSerNo()));

			List<?> resourceUnions = resourcesUnionService
					.getResourcesUnionsByObj(database, Database.class);
			List<Customer> customers = new ArrayList<Customer>();

			Iterator<?> iterator = resourceUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = (ResourcesUnion) iterator.next();
				customers.add(customerService.getBySerNo(resourcesUnion
						.getCusSerNo()));
			}

			database.setCustomers(customers);
			setEntity(database);
			return VIEW;
		} else {
			getRequest().setAttribute("allCustomers",
					customerService.getAllCustomers());

			if (getRequest().getParameter("resourcesBuyers.rCategory") != null
					|| !getRequest().getParameter("resourcesBuyers.rCategory")
							.equals("")) {
				getRequest().setAttribute("rCategory",
						getRequest().getParameter("resourcesBuyers.rCategory"));
			}

			if (getRequest().getParameter("resourcesBuyers.rType") != null
					|| !getRequest().getParameter("resourcesBuyers.rCategory")
							.equals("")) {
				getRequest().setAttribute("rType",
						getRequest().getParameter("resourcesBuyers.rType"));
			}

			List<Customer> customers = new ArrayList<Customer>();
			if (cusSerNo != null && cusSerNo.length != 0) {
				int i = 0;
				while (i < cusSerNo.length) {
					customers.add(customerService.getBySerNo(Long
							.parseLong(cusSerNo[i])));
					i++;
				}
			}

			database = getEntity();
			database.setCustomers(customers);
			setEntity(database);
			return EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		if (getEntity().getUrl() == null
				|| getEntity().getUrl().trim().equals("")) {
			addActionError("URL必需填寫");
		} else {
			String regex = "(@)?(href=')?(HREF=')?(HREF=\")?(href=\")?(http://)?(https://)?[a-zA-Z_0-9\\-]+(\\.\\w[`~!@#$%^&*()_-{[}]|;:<>?,./a-zA-Z0-9\u0000-\uffff\\+=]+)+(/[#&\\n\\-=?\\+\\%/\\.\\w]+)?";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(getEntity().getUrl());
			if (!matcher.matches()) {
				addActionError("URL格式不正確");
			}
		}

		if (cusSerNo == null || cusSerNo.length == 0) {
			addActionError("至少選擇一筆以上購買單位");
		} else {
			int i = 0;
			while (i < cusSerNo.length) {
				if (!NumberUtils.isDigits(String.valueOf(cusSerNo[i]))
						|| Long.parseLong(cusSerNo[i]) < 1) {
					addActionError(cusSerNo[i] + "為不可利用的流水號");
				}
				i++;
			}
		}

		if (getRequest().getParameter("resourcesBuyers.rCategory") == null
				|| getRequest().getParameter("resourcesBuyers.rCategory")
						.equals("")) {
			addActionError("請選擇資源類型");
		}

		if (getRequest().getParameter("resourcesBuyers.rType") == null
				|| getRequest().getParameter("resourcesBuyers.rType")
						.equals("")) {
			addActionError("請選擇資源種類");
		}
		if (cusSerNo == null || cusSerNo.length == 0) {
			addActionError("至少選擇一筆以上購買單位");
		} else {
			int i = 0;
			while (i < cusSerNo.length) {
				if (!NumberUtils.isDigits(String.valueOf(cusSerNo[i]))
						|| Long.parseLong(cusSerNo[i]) < 1) {
					addActionError(cusSerNo[i] + "為不可利用的流水號");
				}
				i++;
			}
		}

		if (!hasActionErrors()) {
			database = databaseService.update(getEntity(), getLoginUser());

			resourcesBuyers = resourcesBuyersService
					.getBySerNo(resourcesUnionService.getByObjSerNo(
							database.getSerNo(), Database.class).getResSerNo());
			resourcesBuyers.setStartDate(getRequest().getParameter(
					"resourcesBuyers.startDate"));
			resourcesBuyers.setMaturityDate(getRequest().getParameter(
					"resourcesBuyers.maturityDate"));
			resourcesBuyers.setrCategory(RCategory.valueOf(getRequest()
					.getParameter("resourcesBuyers.rCategory")));
			resourcesBuyers.setrType(RType.valueOf(getRequest().getParameter(
					"resourcesBuyers.rType")));
			resourcesBuyers.setDbChtTitle(database.getDbChtTitle());
			resourcesBuyers.setDbEngTitle(database.getDbEngTitle());
			resourcesBuyersService.update(resourcesBuyers, getLoginUser());

			List<?> resourcesUnions = resourcesUnionService
					.getResourcesUnionsByObj(database, Database.class);

			for (int j = 0; j < cusSerNo.length; j++) {
				for (int i = 0; i < resourcesUnions.size(); i++) {
					resourcesUnion = (ResourcesUnion) resourcesUnions.get(i);
					if (resourcesUnion.getCusSerNo() == Long
							.parseLong(cusSerNo[j])) {
						resourcesUnions.remove(i);
					}
				}
			}

			Iterator<?> iterator = resourcesUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = (ResourcesUnion) iterator.next();
				resourcesUnionService.deleteBySerNo(resourcesUnion.getSerNo());
			}

			int i = 0;
			while (i < cusSerNo.length) {
				if (!resourcesUnionService.isExist(database, Database.class,
						Long.parseLong(cusSerNo[i]))) {
					resourcesUnionService.save(
							new ResourcesUnion(Long.parseLong(cusSerNo[i]),
									resourcesBuyers.getSerNo(), 0, database
											.getSerNo(), 0), getLoginUser());
				}

				i++;
			}

			resourcesUnion = resourcesUnionService.getByObjSerNo(
					database.getSerNo(), Database.class);
			database.setResourcesBuyers(resourcesBuyersService
					.getBySerNo(resourcesUnion.getResSerNo()));

			List<?> resourceUnions = resourcesUnionService
					.getResourcesUnionsByObj(database, Database.class);
			List<Customer> customers = new ArrayList<Customer>();

			iterator = resourceUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = (ResourcesUnion) iterator.next();
				customers.add(customerService.getBySerNo(resourcesUnion
						.getCusSerNo()));
			}

			database.setCustomers(customers);
			setEntity(database);
			return VIEW;
		} else {
			getRequest().setAttribute("allCustomers",
					customerService.getAllCustomers());

			if (getRequest().getParameter("resourcesBuyers.rCategory") != null
					|| !getRequest().getParameter("resourcesBuyers.rCategory")
							.equals("")) {
				getRequest().setAttribute("rCategory",
						getRequest().getParameter("resourcesBuyers.rCategory"));
			}

			if (getRequest().getParameter("resourcesBuyers.rType") != null
					|| !getRequest().getParameter("resourcesBuyers.rCategory")
							.equals("")) {
				getRequest().setAttribute("rType",
						getRequest().getParameter("resourcesBuyers.rType"));
			}

			List<Customer> customers = new ArrayList<Customer>();
			if (cusSerNo != null && cusSerNo.length != 0) {
				int i = 0;
				while (i < cusSerNo.length) {
					customers.add(customerService.getBySerNo(Long
							.parseLong(cusSerNo[i])));
					i++;
				}
			}

			database = getEntity();
			database.setCustomers(customers);
			setEntity(database);
			return EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String view() throws NumberFormatException, Exception {
		database = databaseService.getBySerNo(Long.parseLong(getRequest()
				.getParameter("viewSerNo")));
		resourcesUnion = resourcesUnionService.getByObjSerNo(
				database.getSerNo(), Database.class);
		database.setResourcesBuyers(resourcesBuyersService
				.getBySerNo(resourcesUnion.getResSerNo()));

		List<?> resourceUnions = resourcesUnionService.getResourcesUnionsByObj(
				database, Database.class);
		List<Customer> customers = new ArrayList<Customer>();

		Iterator<?> iterator = resourceUnions.iterator();
		while (iterator.hasNext()) {
			resourcesUnion = (ResourcesUnion) iterator.next();
			customers.add(customerService.getBySerNo(resourcesUnion
					.getCusSerNo()));
		}

		database.setCustomers(customers);
		setEntity(database);
		return VIEW;
	}

	public String deleteChecked() throws Exception {
		if (checkItem == null || checkItem.length == 0) {
			addActionError("請選擇一筆或一筆以上的資料");
		} else {
			int i = 0;
			while (i < checkItem.length) {
				if (!NumberUtils.isDigits(String.valueOf(checkItem[i]))
						|| Long.parseLong(checkItem[i]) < 1) {
					addActionError(checkItem[i] + "為不可利用的流水號");
				}
				i++;
			}
		}

		if (!hasActionErrors()) {
			int j = 0;
			while (j < checkItem.length) {
				if (databaseService.getBySerNo(Long.parseLong(checkItem[j])) != null) {
					List<?> resourcesUnions = resourcesUnionService
							.getResourcesUnionsByObj(databaseService
									.getBySerNo(Long.parseLong(checkItem[j])),
									Database.class);
					resourcesUnion = (ResourcesUnion) resourcesUnions.get(0);
					resourcesBuyersService.deleteBySerNo(resourcesUnion
							.getResSerNo());

					Iterator<?> iterator = resourcesUnions.iterator();
					while (iterator.hasNext()) {
						resourcesUnion = (ResourcesUnion) iterator.next();
						resourcesUnionService.deleteBySerNo(resourcesUnion
								.getSerNo());
					}

					databaseService.deleteBySerNo(Long.parseLong(checkItem[j]));
				}
				j++;
			}

			DataSet<Database> ds = databaseService
					.getByRestrictions(initDataSet());
			List<Database> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setResourcesBuyers(
						resourcesBuyersService.getBySerNo(resourcesUnionService
								.getByObjSerNo(results.get(i).getSerNo(),
										Database.class).getResSerNo()));
				i++;
			}

			setDs(ds);
			addActionMessage("刪除成功");
			return LIST;
		} else {
			DataSet<Database> ds = databaseService
					.getByRestrictions(initDataSet());
			List<Database> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setResourcesBuyers(
						resourcesBuyersService.getBySerNo(resourcesUnionService
								.getByObjSerNo(results.get(i).getSerNo(),
										Database.class).getResSerNo()));
				i++;
			}

			setDs(ds);
			return LIST;
		}
	}

	/**
	 * @return the checkItem
	 */
	public String[] getCheckItem() {
		return checkItem;
	}

	/**
	 * @param checkItem
	 *            the checkItem to set
	 */
	public void setCheckItem(String[] checkItem) {
		this.checkItem = checkItem;
	}

	/**
	 * @return the cusSerNo
	 */
	public String[] getCusSerNo() {
		return cusSerNo;
	}

	/**
	 * @param cusSerNo
	 *            the cusSerNo to set
	 */
	public void setCusSerNo(String[] cusSerNo) {
		this.cusSerNo = cusSerNo;
	}

	/**
	 * @return the resourcesBuyers
	 */
	public ResourcesBuyers getResourcesBuyers() {
		return resourcesBuyers;
	}

	/**
	 * @param resourcesBuyers
	 *            the resourcesBuyers to set
	 */
	public void setResourcesBuyers(ResourcesBuyers resourcesBuyers) {
		this.resourcesBuyers = resourcesBuyers;
	}
}
