package com.asiaworld.tmuhj.module.apply.ebook.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import com.asiaworld.tmuhj.module.apply.ebook.entity.Ebook;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.entity.ResourcesBuyers;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.service.ResourcesBuyersService;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.entity.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EbookAction extends GenericCRUDActionFull<Ebook> {

	private String[] checkItem;

	private String[] cusSerNo;

	@Autowired
	private Ebook ebook;

	@Autowired
	private EbookService ebookService;

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
			ebook = ebookService.getBySerNo(getEntity().getSerNo());
			Iterator<?> iterator = resourcesUnionService
					.getResourcesUnionsByObj(getEntity(), Ebook.class)
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
			ebook.setCustomers(customers);
			setEntity(ebook);
		} else {
			List<Customer> customers = new ArrayList<Customer>();
			ebook.setCustomers(customers);
			setEntity(ebook);
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		getRequest()
				.setAttribute("option", getRequest().getParameter("option"));
		DataSet<Ebook> ds = ebookService.getByRestrictions(initDataSet());
		List<Ebook> results = ds.getResults();

		int i = 0;
		while (i < results.size()) {
			results.get(i).setResourcesBuyers(
					resourcesBuyersService.getBySerNo(resourcesUnionService
							.getByObjSerNo(results.get(i).getSerNo(),
									Ebook.class).getResSerNo()));
			i++;
		}

		ds.setResults(results);
		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		if (getEntity().getBookName().trim().equals("")
				|| getEntity().getBookName() == null) {
			addActionError("書名不得空白");
		}

		if (getEntity().getIsbn() <= 0) {
			addActionError("ISBN必須填寫");
		} else {
			if (getEntity().getIsbn() >= 9780000000000l
					&& getEntity().getIsbn() < 9800000000000l) {
				String isbn = "" + getEntity().getIsbn();
				int sum = Integer.parseInt(isbn.substring(0, 1)) * 1
						+ Integer.parseInt(isbn.substring(1, 2)) * 3
						+ Integer.parseInt(isbn.substring(2, 3)) * 1
						+ Integer.parseInt(isbn.substring(3, 4)) * 3
						+ Integer.parseInt(isbn.substring(4, 5)) * 1
						+ Integer.parseInt(isbn.substring(5, 6)) * 3
						+ Integer.parseInt(isbn.substring(6, 7)) * 1
						+ Integer.parseInt(isbn.substring(7, 8)) * 3
						+ Integer.parseInt(isbn.substring(8, 9)) * 1
						+ Integer.parseInt(isbn.substring(9, 10)) * 3
						+ Integer.parseInt(isbn.substring(10, 11)) * 1
						+ Integer.parseInt(isbn.substring(11, 12)) * 3;

				int remainder = sum % 10;
				int num = 10 - remainder;

				if (num == 10) {
					if (Integer.parseInt(isbn.substring(12)) != 0) {
						addActionError("ISBN不正確");
					}
				} else {
					if (Integer.parseInt(isbn.substring(12)) != num) {
						addActionError("ISBN不正確");
					}
				}

			} else {
				addActionError("前三碼必須是978或979");
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
			ebook = ebookService.save(getEntity(), getLoginUser());

			resourcesBuyers = resourcesBuyersService
					.save(new ResourcesBuyers(getRequest().getParameter(
							"resourcesBuyers.startDate"), getRequest()
							.getParameter("resourcesBuyers.maturityDate"),
							RCategory.valueOf(getRequest().getParameter(
									"resourcesBuyers.rCategory")), RType
									.valueOf(getRequest().getParameter(
											"resourcesBuyers.rType")),
							getRequest().getParameter(
									"resourcesBuyers.dbChtTitle"), getRequest()
									.getParameter("resourcesBuyers.dbEngTitle")),
							getLoginUser());

			int i = 0;
			while (i < cusSerNo.length) {
				resourcesUnionService.save(
						new ResourcesUnion(Long.parseLong(cusSerNo[i]),
								resourcesBuyers.getSerNo(), ebook.getSerNo(),
								0, 0), getLoginUser());

				i++;
			}

			resourcesUnion = resourcesUnionService.getByObjSerNo(
					ebook.getSerNo(), Ebook.class);
			ebook.setResourcesBuyers(resourcesBuyersService
					.getBySerNo(resourcesUnion.getResSerNo()));

			List<?> resourceUnions = resourcesUnionService
					.getResourcesUnionsByObj(ebook, Ebook.class);
			List<Customer> customers = new ArrayList<Customer>();

			Iterator<?> iterator = resourceUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = (ResourcesUnion) iterator.next();
				customers.add(customerService.getBySerNo(resourcesUnion
						.getCusSerNo()));
			}

			ebook.setCustomers(customers);
			setEntity(ebook);
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

			ebook = getEntity();
			ebook.setCustomers(customers);
			setEntity(ebook);
			return EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		if (getEntity().getBookName().trim().equals("")
				|| getEntity().getBookName() == null) {
			addActionError("書名不得空白");
		}

		if (getEntity().getIsbn() <= 0) {
			addActionError("ISBN必須填寫");
		} else {
			if (getEntity().getIsbn() >= 9780000000002l
					&& getEntity().getIsbn() <= 9799999999990l) {
				String isbn = "" + getEntity().getIsbn();
				int sum = Integer.parseInt(isbn.substring(0, 1)) * 1
						+ Integer.parseInt(isbn.substring(1, 2)) * 3
						+ Integer.parseInt(isbn.substring(2, 3)) * 1
						+ Integer.parseInt(isbn.substring(3, 4)) * 3
						+ Integer.parseInt(isbn.substring(4, 5)) * 1
						+ Integer.parseInt(isbn.substring(5, 6)) * 3
						+ Integer.parseInt(isbn.substring(6, 7)) * 1
						+ Integer.parseInt(isbn.substring(7, 8)) * 3
						+ Integer.parseInt(isbn.substring(8, 9)) * 1
						+ Integer.parseInt(isbn.substring(9, 10)) * 3
						+ Integer.parseInt(isbn.substring(10, 11)) * 1
						+ Integer.parseInt(isbn.substring(11, 12)) * 3;

				int remainder = sum % 10;
				int num = 10 - remainder;

				if (num == 10) {
					if (Integer.parseInt(isbn.substring(12)) != 0) {
						addActionError("ISBN不正確");
					}
				} else {
					if (Integer.parseInt(isbn.substring(12)) != num) {
						addActionError("ISBN不正確");
					}
				}

			} else {
				addActionError("ISBN區間為9780000000002~9799999999990");
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
			ebook = ebookService.update(getEntity(), getLoginUser());

			resourcesBuyers = resourcesBuyersService
					.getBySerNo(resourcesUnionService.getByObjSerNo(
							ebook.getSerNo(), Ebook.class).getResSerNo());
			resourcesBuyers.setStartDate(getRequest().getParameter(
					"resourcesBuyers.startDate"));
			resourcesBuyers.setMaturityDate(getRequest().getParameter(
					"resourcesBuyers.maturityDate"));
			resourcesBuyers.setrCategory(RCategory.valueOf(getRequest()
					.getParameter("resourcesBuyers.rCategory")));
			resourcesBuyers.setrType(RType.valueOf(getRequest().getParameter(
					"resourcesBuyers.rType")));
			resourcesBuyers.setDbChtTitle(getRequest().getParameter(
					"resourcesBuyers.dbChtTitle"));
			resourcesBuyers.setDbEngTitle(getRequest().getParameter(
					"resourcesBuyers.dbEngTitle"));
			resourcesBuyersService.update(resourcesBuyers, getLoginUser());

			List<?> resourcesUnions = resourcesUnionService
					.getResourcesUnionsByObj(ebook, Ebook.class);

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
				if (!resourcesUnionService.isExist(ebook, Ebook.class,
						Long.parseLong(cusSerNo[i]))) {
					resourcesUnionService.save(
							new ResourcesUnion(Long.parseLong(cusSerNo[i]),
									resourcesBuyers.getSerNo(), ebook
											.getSerNo(), 0, 0), getLoginUser());
				}

				i++;
			}

			resourcesUnion = resourcesUnionService.getByObjSerNo(
					ebook.getSerNo(), Ebook.class);
			ebook.setResourcesBuyers(resourcesBuyersService
					.getBySerNo(resourcesUnion.getResSerNo()));

			List<?> resourceUnions = resourcesUnionService
					.getResourcesUnionsByObj(ebook, Ebook.class);
			List<Customer> customers = new ArrayList<Customer>();

			iterator = resourceUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = (ResourcesUnion) iterator.next();
				customers.add(customerService.getBySerNo(resourcesUnion
						.getCusSerNo()));
			}

			ebook.setCustomers(customers);
			setEntity(ebook);
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

			ebook = getEntity();
			ebook.setCustomers(customers);
			setEntity(ebook);
			return EDIT;
		}

	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String view() throws NumberFormatException, Exception {
		ebook = ebookService.getBySerNo(Long.parseLong(getRequest()
				.getParameter("viewSerNo")));
		resourcesUnion = resourcesUnionService.getByObjSerNo(ebook.getSerNo(),
				Ebook.class);
		ebook.setResourcesBuyers(resourcesBuyersService
				.getBySerNo(resourcesUnion.getResSerNo()));

		List<?> resourceUnions = resourcesUnionService.getResourcesUnionsByObj(
				ebook, Ebook.class);
		List<Customer> customers = new ArrayList<Customer>();

		Iterator<?> iterator = resourceUnions.iterator();
		while (iterator.hasNext()) {
			resourcesUnion = (ResourcesUnion) iterator.next();
			customers.add(customerService.getBySerNo(resourcesUnion
					.getCusSerNo()));
		}

		ebook.setCustomers(customers);
		setEntity(ebook);
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
				if (ebookService.getBySerNo(Long.parseLong(checkItem[j])) != null) {
					List<?> resourcesUnions = resourcesUnionService
							.getResourcesUnionsByObj(ebookService
									.getBySerNo(Long.parseLong(checkItem[j])),
									Ebook.class);
					resourcesUnion = (ResourcesUnion) resourcesUnions.get(0);
					resourcesBuyersService.deleteBySerNo(resourcesUnion
							.getResSerNo());

					Iterator<?> iterator = resourcesUnions.iterator();
					while (iterator.hasNext()) {
						resourcesUnion = (ResourcesUnion) iterator.next();
						resourcesUnionService.deleteBySerNo(resourcesUnion
								.getSerNo());
					}

					ebookService.deleteBySerNo(Long.parseLong(checkItem[j]));
				}
				j++;
			}

			DataSet<Ebook> ds = ebookService.getByRestrictions(initDataSet());
			List<Ebook> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setResourcesBuyers(
						resourcesBuyersService.getBySerNo(resourcesUnionService
								.getByObjSerNo(results.get(i).getSerNo(),
										Ebook.class).getResSerNo()));
				i++;
			}

			setDs(ds);
			addActionMessage("刪除成功");
			return LIST;
		} else {
			DataSet<Ebook> ds = ebookService.getByRestrictions(initDataSet());
			List<Ebook> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setResourcesBuyers(
						resourcesBuyersService.getBySerNo(resourcesUnionService
								.getByObjSerNo(results.get(i).getSerNo(),
										Ebook.class).getResSerNo()));
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
