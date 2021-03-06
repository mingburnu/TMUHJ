package com.asiaworld.tmuhj.module.apply.ebook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.converter.EnumConverter;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.web.GenericWebActionFull;
import com.asiaworld.tmuhj.module.apply.enums.Category;
import com.asiaworld.tmuhj.module.apply.enums.Type;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyers;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyersService;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.ActionContext;

@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EbookAction extends GenericWebActionFull<Ebook> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9166237220863961574L;

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

	@Autowired
	private EnumConverter enumConverter;

	@Override
	protected void validateSave() throws Exception {
		if (StringUtils.isBlank(getEntity().getBookName())) {
			errorMessages.add("書名不得空白");
		}

		if (getEntity().getIsbn() != null) {
			if (!ISBN_Validator.isIsbn(getEntity().getIsbn())) {
				errorMessages.add("ISBN不正確");
			} else {
				if (ebookService.getEbkSerNoByIsbn(getEntity().getIsbn()) != 0) {
					errorMessages.add("ISBN不可重複");
				}
			}
		} else {
			if (StringUtils.isBlank(getRequest().getParameter("entity.isbn"))) {
				errorMessages.add("ISBN必須填寫");
			} else {
				if (!ISBN_Validator.isIsbn(getRequest().getParameter(
						"entity.isbn"))) {
					errorMessages.add("ISBN不正確");
				} else {
					if (ebookService.getEbkSerNoByIsbn(Long
							.parseLong(getRequest().getParameter("entity.isbn")
									.trim().replace("-", ""))) != 0) {
						errorMessages.add("ISBN不可重複");
					}
				}
			}
		}

		if (StringUtils.isNotEmpty(getEntity().getCnClassBzStr())) {
			if (!NumberUtils.isDigits(getEntity().getCnClassBzStr())
					|| getEntity().getCnClassBzStr().length() != 3) {
				errorMessages.add("中國圖書分類碼不正確");
			}
		}

		if (StringUtils.isNotEmpty(getEntity().getBookInfoIntegral())) {
			if (!NumberUtils.isDigits(getEntity().getBookInfoIntegral())
					|| getEntity().getBookInfoIntegral().length() != 3) {
				errorMessages.add("美國國家圖書館類碼不正確");
			}
		}

		if (ArrayUtils.isEmpty(getEntity().getCusSerNo())) {
			errorMessages.add("至少選擇一筆以上購買單位");
		} else {
			Set<Long> deRepeatSet = new HashSet<Long>(Arrays.asList(getEntity()
					.getCusSerNo()));
			getEntity().setCusSerNo(
					deRepeatSet.toArray(new Long[deRepeatSet.size()]));

			int i = 0;
			while (i < getEntity().getCusSerNo().length) {
				if (getEntity().getCusSerNo()[i] == null
						|| getEntity().getCusSerNo()[i] < 1
						|| customerService
								.getBySerNo(getEntity().getCusSerNo()[i]) == null) {
					errorMessages.add(getEntity().getCusSerNo()[i]
							+ "為不可利用的流水號");
					getEntity().getCusSerNo()[i] = null;
				}
				i++;
			}
		}

		if (getEntity().getResourcesBuyers().getCategory() == null
				|| getEntity().getResourcesBuyers().getCategory()
						.equals(Category.不明)) {
			errorMessages.add("資源類型錯誤");
			getEntity().getResourcesBuyers().setCategory(Category.未註明);
		}

		if (getEntity().getResourcesBuyers().getType() == null) {
			errorMessages.add("資源種類錯誤");
			getEntity().getResourcesBuyers().setType(Type.電子書);
		}
	}

	@Override
	protected void validateUpdate() throws Exception {
		if (!hasEntity()) {
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		} else {
			if (StringUtils.isBlank(getEntity().getBookName())) {
				errorMessages.add("書名不得空白");
			}

			if (getEntity().getIsbn() != null) {
				if (!ISBN_Validator.isIsbn(getEntity().getIsbn())) {
					errorMessages.add("ISBN不正確");
				} else {
					long ebkSerNo = ebookService.getEbkSerNoByIsbn(getEntity()
							.getIsbn());
					if (ebkSerNo != 0 && ebkSerNo != getEntity().getSerNo()) {
						errorMessages.add("ISBN不可重複");
					}
				}
			} else {
				if (StringUtils.isBlank(getRequest()
						.getParameter("entity.isbn"))) {
					errorMessages.add("ISBN必須填寫");
				} else {
					if (!ISBN_Validator.isIsbn(getRequest().getParameter(
							"entity.isbn"))) {
						errorMessages.add("ISBN不正確");
					} else {
						long ebkSerNo = ebookService.getEbkSerNoByIsbn(Long
								.parseLong(getRequest()
										.getParameter("entity.isbn").trim()
										.replace("-", "")));
						if (ebkSerNo != 0 && ebkSerNo != getEntity().getSerNo()) {
							errorMessages.add("ISBN不可重複");
						}
					}
				}
			}

			if (StringUtils.isNotEmpty(getEntity().getCnClassBzStr())) {
				if (!NumberUtils.isDigits(getEntity().getCnClassBzStr())
						|| getEntity().getCnClassBzStr().length() != 3) {
					errorMessages.add("中國圖書分類碼不正確");
				}
			}

			if (StringUtils.isNotEmpty(getEntity().getBookInfoIntegral())) {
				if (!NumberUtils.isDigits(getEntity().getBookInfoIntegral())
						|| getEntity().getBookInfoIntegral().length() != 3) {
					errorMessages.add("美國國家圖書館類碼不正確");
				}
			}

			if (ArrayUtils.isEmpty(getEntity().getCusSerNo())) {
				errorMessages.add("至少選擇一筆以上購買單位");
			} else {
				Set<Long> deRepeatSet = new HashSet<Long>(
						Arrays.asList(getEntity().getCusSerNo()));
				getEntity().setCusSerNo(
						deRepeatSet.toArray(new Long[deRepeatSet.size()]));

				int i = 0;
				while (i < getEntity().getCusSerNo().length) {
					if (getEntity().getCusSerNo()[i] == null
							|| getEntity().getCusSerNo()[i] < 1
							|| customerService.getBySerNo(getEntity()
									.getCusSerNo()[i]) == null) {
						errorMessages.add(getEntity().getCusSerNo()[i]
								+ "為不可利用的流水號");
						getEntity().getCusSerNo()[i] = null;
					}
					i++;
				}
			}

			if (getEntity().getResourcesBuyers().getCategory() == null
					|| getEntity().getResourcesBuyers().getCategory()
							.equals(Category.不明)) {
				errorMessages.add("資源類型錯誤");
				getEntity().getResourcesBuyers().setCategory(Category.未註明);
			}

			if (getEntity().getResourcesBuyers().getType() == null) {
				errorMessages.add("資源種類錯誤");
				getEntity().getResourcesBuyers().setType(Type.電子書);
			}
		}
	}

	@Override
	protected void validateDelete() throws Exception {
		if (ArrayUtils.isEmpty(getEntity().getCheckItem())) {
			errorMessages.add("請選擇一筆或一筆以上的資料");
		} else {
			Set<Long> deRepeatSet = new HashSet<Long>(Arrays.asList(getEntity()
					.getCheckItem()));
			getEntity().setCheckItem(
					deRepeatSet.toArray(new Long[deRepeatSet.size()]));

			int i = 0;
			while (i < getEntity().getCheckItem().length) {
				if (getEntity().getCheckItem()[i] == null
						|| getEntity().getCheckItem()[i] < 1
						|| ebookService
								.getBySerNo(getEntity().getCheckItem()[i]) == null) {
					addActionError("有錯誤流水號");
					break;
				}
				i++;
			}
		}
	}

	@Override
	public String add() throws Exception {
		setCategoryList();

		List<Customer> customers = new ArrayList<Customer>();
		ebook.setCustomers(customers);
		getRequest().setAttribute("uncheckCustomers",
				customerService.getUncheckCustomers(customers));
		ebook.getResourcesBuyers().setCategory(Category.未註明);
		ebook.getResourcesBuyers().setType(Type.電子書);
		setEntity(ebook);

		return ADD;
	}

	@Override
	public String edit() throws Exception {
		if (hasEntity()) {
			setCategoryList();
			getRequest().setAttribute("allCustomers",
					customerService.getAllCustomers());

			Iterator<ResourcesUnion> iterator = resourcesUnionService
					.getResourcesUnionsByObj(getEntity(), Ebook.class)
					.iterator();

			List<Customer> customers = new ArrayList<Customer>();

			while (iterator.hasNext()) {
				resourcesUnion = iterator.next();
				customer = resourcesUnion.getCustomer();
				if (customer != null) {
					customers.add(customer);
				}
			}

			resourcesBuyers = resourcesUnion.getResourcesBuyers();
			ebook.setResourcesBuyers(resourcesBuyers);
			ebook.setCustomers(customers);
			getRequest().setAttribute("uncheckCustomers",
					customerService.getUncheckCustomers(customers));
			setEntity(ebook);
		} else {
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		if (StringUtils.isNotBlank(getEntity().getOption())) {
			if (!getEntity().getOption().equals("entity.bookName")
					&& !getEntity().getOption().equals("entity.isbn")) {
				getEntity().setOption("entity.bookName");
			}
		} else {
			getEntity().setOption("entity.bookName");
		}

		if (getEntity().getOption().equals("entity.isbn")) {
			if (getEntity().getIsbn() == null) {
				if (StringUtils.isNotEmpty(getRequest().getParameter(
						"entity.isbn"))) {
					if (ISBN_Validator.isIsbn(getRequest().getParameter(
							"entity.isbn").trim())) {
						getEntity().setIsbn(
								Long.parseLong(getRequest()
										.getParameter("entity.isbn").trim()
										.replaceAll("-", "")));
					} else {
						getEntity().setIsbn(Long.MIN_VALUE);
					}
				}
			}
		}

		DataSet<Ebook> ds = ebookService.getByRestrictions(initDataSet());

		if (ds.getResults().size() == 0 && ds.getPager().getCurrentPage() > 1) {
			Double lastPage = Math.ceil(ds.getPager().getTotalRecord()
					.doubleValue()
					/ ds.getPager().getRecordPerPage().doubleValue());
			ds.getPager().setCurrentPage(lastPage.intValue());
			ds = ebookService.getByRestrictions(ds);
		}

		int i = 0;
		while (i < ds.getResults().size()) {
			ds.getResults()
					.get(i)
					.setResourcesBuyers(
							resourcesUnionService.getByObjSerNo(
									ds.getResults().get(i).getSerNo(),
									Ebook.class).getResourcesBuyers());
			i++;
		}

		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		validateSave();
		setActionErrors(errorMessages);

		if (!hasActionErrors()) {
			getEntity().setIsbn(
					Long.parseLong(getRequest().getParameter("entity.isbn")
							.trim().replace("-", "")));
			ebook = ebookService.save(getEntity(), getLoginUser());

			resourcesBuyers = resourcesBuyersService.save(getEntity()
					.getResourcesBuyers(), getLoginUser());

			int i = 0;
			while (i < getEntity().getCusSerNo().length) {
				resourcesUnionService.save(
						new ResourcesUnion(customerService
								.getBySerNo(getEntity().getCusSerNo()[i]),
								resourcesBuyers, ebook.getSerNo(), 0L, 0L),
						getLoginUser());

				i++;
			}

			resourcesUnion = resourcesUnionService.getByObjSerNo(
					ebook.getSerNo(), Ebook.class);
			ebook.setResourcesBuyers(resourcesUnion.getResourcesBuyers());

			List<ResourcesUnion> resourceUnions = resourcesUnionService
					.getResourcesUnionsByObj(ebook, Ebook.class);
			List<Customer> customers = new ArrayList<Customer>();

			Iterator<ResourcesUnion> iterator = resourceUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = iterator.next();
				customers.add(resourcesUnion.getCustomer());
			}

			ebook.setCustomers(customers);
			setEntity(ebook);
			addActionMessage("新增成功");
			return VIEW;
		} else {
			setCategoryList();

			List<Customer> customers = new ArrayList<Customer>();
			if (ArrayUtils.isNotEmpty(getEntity().getCusSerNo())) {
				int i = 0;
				while (i < getEntity().getCusSerNo().length) {
					if (getEntity().getCusSerNo()[i] != null) {
						customers.add(customerService.getBySerNo(getEntity()
								.getCusSerNo()[i]));
					}
					i++;
				}
			}

			ebook = getEntity();
			ebook.setCustomers(customers);
			getRequest().setAttribute("uncheckCustomers",
					customerService.getUncheckCustomers(customers));
			setEntity(ebook);
			return ADD;
		}
	}

	@Override
	public String update() throws Exception {
		validateUpdate();
		setActionErrors(errorMessages);

		if (!hasActionErrors()) {
			getEntity().setIsbn(
					Long.parseLong(getRequest().getParameter("entity.isbn")
							.trim().replace("-", "")));
			ebook = ebookService.update(getEntity(), getLoginUser());

			resourcesBuyers = new ResourcesBuyers(getEntity()
					.getResourcesBuyers().getStartDate(), getEntity()
					.getResourcesBuyers().getMaturityDate(), getEntity()
					.getResourcesBuyers().getCategory(), getEntity()
					.getResourcesBuyers().getType(), getEntity()
					.getResourcesBuyers().getDbChtTitle(), getEntity()
					.getResourcesBuyers().getDbEngTitle());
			resourcesBuyers.setSerNo(resourcesUnionService
					.getByObjSerNo(ebook.getSerNo(), Ebook.class)
					.getResourcesBuyers().getSerNo());
			resourcesBuyersService.update(resourcesBuyers, getLoginUser());

			List<ResourcesUnion> resourcesUnions = resourcesUnionService
					.getResourcesUnionsByObj(ebook, Ebook.class);

			for (int j = 0; j < getEntity().getCusSerNo().length; j++) {
				for (int i = 0; i < resourcesUnions.size(); i++) {
					resourcesUnion = resourcesUnions.get(i);
					if (resourcesUnion.getCustomer().getSerNo() == getEntity()
							.getCusSerNo()[j]) {
						resourcesUnions.remove(i);
					}
				}
			}

			Iterator<ResourcesUnion> iterator = resourcesUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = (ResourcesUnion) iterator.next();
				resourcesUnionService.deleteBySerNo(resourcesUnion.getSerNo());
			}

			int i = 0;
			while (i < getEntity().getCusSerNo().length) {
				if (!resourcesUnionService.isExist(ebook, Ebook.class,
						getEntity().getCusSerNo()[i])) {
					resourcesUnionService.save(
							new ResourcesUnion(customerService
									.getBySerNo(getEntity().getCusSerNo()[i]),
									resourcesBuyers, ebook.getSerNo(), 0L, 0L),
							getLoginUser());
				}

				i++;
			}

			resourcesUnion = resourcesUnionService.getByObjSerNo(
					ebook.getSerNo(), Ebook.class);
			ebook.setResourcesBuyers(resourcesUnion.getResourcesBuyers());

			List<ResourcesUnion> resourceUnions = resourcesUnionService
					.getResourcesUnionsByObj(ebook, Ebook.class);
			List<Customer> customers = new ArrayList<Customer>();

			iterator = resourceUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = iterator.next();
				customers.add(resourcesUnion.getCustomer());
			}

			ebook.setCustomers(customers);
			setEntity(ebook);
			addActionMessage("修改成功");
			return VIEW;
		} else {
			setCategoryList();

			List<Customer> customers = new ArrayList<Customer>();
			if (ArrayUtils.isNotEmpty(getEntity().getCusSerNo())) {
				int i = 0;
				while (i < getEntity().getCusSerNo().length) {
					if (getEntity().getCusSerNo()[i] != null) {
						customers.add(customerService.getBySerNo(getEntity()
								.getCusSerNo()[i]));
					}
					i++;
				}
			}

			ebook = getEntity();
			ebook.setCustomers(customers);
			getRequest().setAttribute("uncheckCustomers",
					customerService.getUncheckCustomers(customers));
			setEntity(ebook);
			return EDIT;
		}

	}

	@Override
	public String delete() throws Exception {
		validateDelete();
		setActionErrors(errorMessages);

		if (!hasActionErrors()) {
			int j = 0;
			while (j < getEntity().getCheckItem().length) {
				List<ResourcesUnion> resourcesUnions = resourcesUnionService
						.getResourcesUnionsByObj(ebookService
								.getBySerNo(getEntity().getCheckItem()[j]),
								Ebook.class);
				resourcesUnion = resourcesUnions.get(0);

				Iterator<ResourcesUnion> iterator = resourcesUnions.iterator();
				while (iterator.hasNext()) {
					resourcesUnion = iterator.next();
					resourcesUnionService.deleteBySerNo(resourcesUnion
							.getSerNo());
				}
				resourcesBuyersService.deleteBySerNo(resourcesUnion
						.getResourcesBuyers().getSerNo());
				ebookService.deleteBySerNo(getEntity().getCheckItem()[j]);

				j++;
			}

			list();
			addActionMessage("刪除成功");
			return LIST;
		} else {
			list();
			return LIST;
		}
	}

	public String view() throws NumberFormatException, Exception {
		if (hasEntity()) {
			resourcesUnion = resourcesUnionService.getByObjSerNo(
					ebook.getSerNo(), Ebook.class);

			ebook.setResourcesBuyers(resourcesUnion.getResourcesBuyers());

			List<ResourcesUnion> resourceUnions = resourcesUnionService
					.getResourcesUnionsByObj(ebook, Ebook.class);
			List<Customer> customers = new ArrayList<Customer>();

			Iterator<ResourcesUnion> iterator = resourceUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = iterator.next();
				customers.add(resourcesUnion.getCustomer());
			}

			ebook.setCustomers(customers);
			getRequest().setAttribute("viewSerNo", getEntity().getSerNo());
			setEntity(ebook);
		} else {
			getResponse().sendError(HttpServletResponse.SC_NOT_FOUND);
		}

		return VIEW;
	}

	public String imports() {
		return IMPORT;
	}

	public String queue() throws Exception {
		if (ArrayUtils.isEmpty(getEntity().getFile())
				|| !getEntity().getFile()[0].isFile()) {
			addActionError("請選擇檔案");
		} else {
			if (createWorkBook(new FileInputStream(getEntity().getFile()[0])) == null) {
				addActionError("檔案格式錯誤");
			}
		}

		if (!hasActionErrors()) {
			Workbook book = createWorkBook(new FileInputStream(getEntity()
					.getFile()[0]));
			Sheet sheet = book.createSheet();
			if (book.getNumberOfSheets() != 0) {
				sheet = book.getSheetAt(0);
			}

			Row firstRow = sheet.getRow(0);
			if (firstRow == null) {
				firstRow = sheet.createRow(0);
			}

			// 保存列名
			List<String> cellNames = new ArrayList<String>();
			String[] rowTitles = new String[19];
			int n = 0;
			while (n < rowTitles.length) {
				if (firstRow.getCell(n) == null) {
					rowTitles[n] = "";
				} else {
					int typeInt = firstRow.getCell(n).getCellType();
					switch (typeInt) {
					case 0:
						String tempNumeric = "";
						tempNumeric = tempNumeric
								+ firstRow.getCell(n).getNumericCellValue();
						rowTitles[n] = tempNumeric;
						break;

					case 1:
						rowTitles[n] = firstRow.getCell(n).getStringCellValue();
						break;

					case 2:
						rowTitles[n] = firstRow.getCell(n).getCellFormula();
						break;

					case 3:
						rowTitles[n] = "";
						break;

					case 4:
						String tempBoolean = "";
						tempBoolean = ""
								+ firstRow.getCell(n).getBooleanCellValue();
						rowTitles[n] = tempBoolean;
						break;

					case 5:
						String tempByte = "";
						tempByte = tempByte
								+ firstRow.getCell(n).getErrorCellValue();
						rowTitles[n] = tempByte;
						break;
					}

				}

				cellNames.add(rowTitles[n]);
				n++;
			}

			LinkedHashSet<Ebook> originalData = new LinkedHashSet<Ebook>();
			Map<String, Ebook> checkRepeatRow = new LinkedHashMap<String, Ebook>();

			int normal = 0;

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}

				String[] rowValues = new String[19];
				int k = 0;
				while (k < rowValues.length) {
					if (row.getCell(k) == null) {
						rowValues[k] = "";
					} else {
						int typeInt = row.getCell(k).getCellType();
						switch (typeInt) {
						case 0:
							String tempNumeric = "";
							tempNumeric = tempNumeric
									+ row.getCell(k).getNumericCellValue();
							rowValues[k] = tempNumeric;
							break;

						case 1:
							rowValues[k] = row.getCell(k).getStringCellValue();
							break;

						case 2:
							rowValues[k] = row.getCell(k).getCellFormula();
							break;

						case 3:
							rowValues[k] = "";
							break;

						case 4:
							String tempBoolean = "";
							tempBoolean = ""
									+ row.getCell(k).getBooleanCellValue();
							rowValues[k] = tempBoolean;
							break;

						case 5:
							String tempByte = "";
							tempByte = tempByte
									+ row.getCell(k).getErrorCellValue();
							rowValues[k] = tempByte;
							break;
						}

					}
					k++;
				}

				Category category = null;
				Object objCategory = getEnum(
						new String[] { rowValues[13].trim() }, Category.class);
				if (objCategory != null) {
					category = (Category) objCategory;
				} else {
					if (StringUtils.isBlank(rowValues[13])) {
						category = Category.未註明;
					} else {
						category = Category.不明;
					}
				}

				Type type = null;
				Object objType = getEnum(new String[] { rowValues[14].trim() },
						Type.class);
				if (objType != null) {
					type = (Type) objType;
				} else {
					type = Type.電子書;
				}

				resourcesBuyers = new ResourcesBuyers(rowValues[11],
						rowValues[12], category, type, rowValues[15],
						rowValues[16]);

				String isbn = rowValues[1].trim();

				Integer version = null;
				if (NumberUtils.isNumber(rowValues[8])) {
					double d = Double.parseDouble(rowValues[8]);
					version = (int) d;
				}

				customer = new Customer();
				customer.setName(rowValues[17].trim());
				customer.setEngName(rowValues[18].trim());

				List<Customer> customers = new ArrayList<Customer>();
				customers.add(customer);

				if (NumberUtils.isDigits(isbn)) {
					ebook = new Ebook(rowValues[0], Long.parseLong(isbn),
							rowValues[2], rowValues[3], rowValues[4],
							rowValues[5], rowValues[6], rowValues[7], version,
							rowValues[9], rowValues[10], "", "", "");
				} else {
					ebook = new Ebook(rowValues[0], null, rowValues[2],
							rowValues[3], rowValues[4], rowValues[5],
							rowValues[6], rowValues[7], version, rowValues[9],
							rowValues[10], "", "", "");
					customers.get(0).setMemo(isbn);
				}

				ebook.setResourcesBuyers(resourcesBuyers);
				ebook.setCustomers(customers);
				List<String> errorList = Lists.newArrayList();

				if (ebook.getIsbn() != null) {
					long cusSerNo = customerService
							.getCusSerNoByName(rowValues[17].trim());

					if (cusSerNo == 0) {
						errorList.add("無此客戶");
					}

					if (!ISBN_Validator.isIsbn(Long.parseLong(isbn))) {
						errorList.add("ISBN異常");
					}

					if (ISBN_Validator.isIsbn(Long.parseLong(isbn))) {
						long ebkSerNo = ebookService.getEbkSerNoByIsbn(Long
								.parseLong(isbn));
						if (cusSerNo != 0) {
							customers.get(0).setSerNo(cusSerNo);
							if (ebkSerNo != 0) {
								if (resourcesUnionService.isExist(
										ebookService.getBySerNo(ebkSerNo),
										Ebook.class, cusSerNo)) {
									errorList.add("已存在");
								}
							}
						}
					}
				} else {
					long cusSerNo = customerService
							.getCusSerNoByName(rowValues[17].trim());

					if (cusSerNo == 0) {
						errorList.add("無此客戶");
					}

					if (!ISBN_Validator.isIsbn(isbn)) {
						errorList.add("ISBN異常");
					}

					if (ISBN_Validator.isIsbn(isbn)) {
						long ebkSerNo = ebookService.getEbkSerNoByIsbn(Long
								.parseLong(isbn.replace("-", "")));

						if (cusSerNo != 0) {
							customers.get(0).setSerNo(cusSerNo);
							if (ebkSerNo != 0) {
								if (resourcesUnionService.isExist(
										ebookService.getBySerNo(ebkSerNo),
										Ebook.class, cusSerNo)) {
									errorList.add("已存在");
								}
							}
						}
					}
				}

				if (ebook.getResourcesBuyers().getCategory()
						.equals(Category.不明)) {
					errorList.add("資源類型不明");
				}

				if (StringUtils.isNotEmpty(ebook.getCnClassBzStr())) {
					if (!NumberUtils.isDigits(ebook.getCnClassBzStr())
							|| ebook.getCnClassBzStr().length() != 3) {
						errorList.add("CLC錯誤");
					}
				}

				if (StringUtils.isNotEmpty(ebook.getBookInfoIntegral())) {
					if (!NumberUtils.isDigits(ebook.getBookInfoIntegral())
							|| ebook.getBookInfoIntegral().length() != 3) {
						errorList.add("DDC錯誤");
					}
				}

				if (errorList.size() != 0) {
					ebook.setDataStatus(errorList.toString().replace("[", "")
							.replace("]", ""));
				} else {
					ebook.setDataStatus("正常");
				}

				if (ebook.getDataStatus().equals("正常")
						&& !originalData.contains(ebook)) {

					if (checkRepeatRow.containsKey(ebook.getIsbn()
							+ customer.getName())) {
						ebook.setDataStatus("資料重複");

					} else {
						checkRepeatRow.put(
								ebook.getIsbn() + customer.getName(), ebook);
						++normal;
					}
				}

				originalData.add(ebook);
			}

			List<Ebook> excelData = new ArrayList<Ebook>(originalData);

			DataSet<Ebook> ds = initDataSet();
			ds.getPager().setTotalRecord((long) excelData.size());

			if (excelData.size() < ds.getPager().getRecordPerPage()) {
				int i = 0;
				while (i < excelData.size()) {
					ds.getResults().add(excelData.get(i));
					i++;
				}
			} else {
				int i = 0;
				while (i < ds.getPager().getRecordPerPage()) {
					ds.getResults().add(excelData.get(i));
					i++;
				}
			}

			getSession().put("cellNames", cellNames);
			getSession().put("importList", excelData);
			getSession().put("total", excelData.size());
			getSession().put("normal", normal);
			getSession().put("clazz", this.getClass());

			return QUEUE;
		} else {
			return IMPORT;
		}
	}

	public String paginate() throws Exception {
		List<?> importList = (List<?>) getSession().get("importList");
		if (importList == null) {
			return IMPORT;
		}

		clearCheckedItem();

		DataSet<Ebook> ds = initDataSet();
		ds.getPager().setTotalRecord((long) importList.size());

		int first = ds.getPager().getOffset();
		int last = first + ds.getPager().getRecordPerPage();

		int index = first;
		while (index >= first && index < last) {
			if (index < importList.size()) {
				ds.getResults().add((Ebook) importList.get(index));
			} else {
				break;
			}
			index++;
		}

		if (ds.getResults().size() == 0 && ds.getPager().getCurrentPage() > 1) {
			Double lastPage = Math.ceil(ds.getPager().getTotalRecord()
					.doubleValue()
					/ ds.getPager().getRecordPerPage().doubleValue());
			ds.getPager().setCurrentPage(lastPage.intValue());
			first = ds.getPager().getOffset();
			last = first + ds.getPager().getRecordPerPage();

			index = first;
			while (index >= first && index < last) {
				if (index < importList.size()) {
					ds.getResults().add((Ebook) importList.get(index));
				} else {
					break;
				}
				index++;
			}
		}

		setDs(ds);
		return QUEUE;
	}

	@SuppressWarnings("unchecked")
	public String getCheckedItem() {
		List<?> importList = (List<?>) getSession().get("importList");
		if (importList == null) {
			return IMPORT;
		}

		Set<Integer> checkItemSet = new TreeSet<Integer>();
		if (getSession().containsKey("checkItemSet")) {
			checkItemSet = (Set<Integer>) getSession().get("checkItemSet");
		}

		if (ArrayUtils.isNotEmpty(getEntity().getImportItem())) {
			if (getEntity().getImportItem()[0] != null
					&& getEntity().getImportItem()[0] >= 0
					&& getEntity().getImportItem()[0] < importList.size()) {
				if (!checkItemSet.contains(getEntity().getImportItem()[0])) {
					if (((Ebook) importList.get(getEntity().getImportItem()[0]))
							.getDataStatus().equals("正常")) {
						checkItemSet.add(getEntity().getImportItem()[0]);
					}
				} else {
					checkItemSet.remove(getEntity().getImportItem()[0]);
				}
			}
		}

		getSession().put("checkItemSet", checkItemSet);
		return QUEUE;
	}

	public String allCheckedItem() {
		List<?> importList = (List<?>) getSession().get("importList");
		if (importList == null) {
			return IMPORT;
		}

		Set<Integer> checkItemSet = new TreeSet<Integer>();

		if (ArrayUtils.isNotEmpty(getEntity().getImportItem())) {
			Set<Integer> deRepeatSet = new HashSet<Integer>(
					Arrays.asList(getEntity().getImportItem()));
			getEntity().setImportItem(
					deRepeatSet.toArray(new Integer[deRepeatSet.size()]));

			int i = 0;
			while (i < getEntity().getImportItem().length) {
				if (getEntity().getImportItem()[i] != null
						&& getEntity().getImportItem()[i] >= 0
						&& getEntity().getImportItem()[i] < importList.size()) {
					if (((Ebook) importList.get(getEntity().getImportItem()[i]))
							.getDataStatus().equals("正常")) {
						checkItemSet.add(getEntity().getImportItem()[i]);
					}

					if (checkItemSet.size() == importList.size()) {
						break;
					}
				}
				i++;
			}
		}

		getSession().put("checkItemSet", checkItemSet);
		return QUEUE;
	}

	public String clearCheckedItem() {
		if (getSession().get("importList") == null) {
			return IMPORT;
		}

		Set<Integer> checkItemSet = new TreeSet<Integer>();
		getSession().put("checkItemSet", checkItemSet);
		return null;
	}

	public String importData() throws Exception {
		List<?> importList = (List<?>) getSession().get("importList");

		if (importList == null) {
			return IMPORT;
		}

		Set<?> checkItemSet = (Set<?>) getSession().get("checkItemSet");

		if (CollectionUtils.isEmpty(checkItemSet)) {
			addActionError("請選擇一筆或一筆以上的資料");
		}

		if (!hasActionErrors()) {
			Iterator<?> iterator = checkItemSet.iterator();
			int successCount = 0;
			while (iterator.hasNext()) {
				int index = (Integer) iterator.next();
				ebook = (Ebook) importList.get(index);

				long ebkSerNo = 0;
				if (ebook.getIsbn() != null) {
					ebkSerNo = ebookService.getEbkSerNoByIsbn(ebook.getIsbn());
				} else {
					ebook.setIsbn(Long.parseLong(ebook.getCustomers().get(0)
							.getMemo().replace("-", "")));
					ebkSerNo = ebookService.getEbkSerNoByIsbn(ebook.getIsbn());
				}

				if (ebkSerNo == 0) {
					resourcesBuyers = resourcesBuyersService.save(
							ebook.getResourcesBuyers(), getLoginUser());
					ebook = ebookService.save(ebook, getLoginUser());
					resourcesUnionService.save(
							new ResourcesUnion(ebook.getCustomers().get(0),
									resourcesBuyers, ebook.getSerNo(), 0L, 0L),
							getLoginUser());
				} else {
					resourcesUnion = resourcesUnionService.getByObjSerNo(
							ebkSerNo, Ebook.class);
					resourcesUnionService.save(
							new ResourcesUnion(ebook.getCustomers().get(0),
									resourcesUnion.getResourcesBuyers(),
									ebkSerNo, 0L, 0L), getLoginUser());

				}

				ebook.setDataStatus("已匯入");
				++successCount;

			}

			getRequest().setAttribute("successCount", successCount);
			return VIEW;
		} else {
			paginate();
			return QUEUE;
		}
	}

	public String example() throws Exception {
		getEntity().setReportFile("ebook_sample.xlsx");

		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("ebook");
		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		Map<String, Object[]> empinfo = new LinkedHashMap<String, Object[]>();
		empinfo.put("1", new Object[] { "書名", "ISBN/13碼", "出版社", "作者",
				"authers/第二第三作者等", "uppername/系列叢書名", "電子書出版日期", "語文", "版本",
				"cnclassbzstr/中國圖書分類碼", "美國國家圖書館分類號", "startdate/起始日",
				"maturitydate/到期日", "Rcategory/資源類型", "Rtype/資源種類",
				"Dbchttitle/資料庫中文題名", "Dbengtitle/資料庫英文題名", "購買單位名稱",
				"購買單位英文名稱" });

		empinfo.put("2", new Object[] { "Ophthalmic Clinical Procedures",
				"9780080449784", "Elsevier(ClinicalKey)",
				"Frank Eperjesi & Hannah Bartlett & Mark Dunne", "", "",
				"2008/2/7", "eng", "", "001", "001", "", "", "", "電子書", "", "",
				"衛生福利部基隆醫院", "" });
		empinfo.put("3", new Object[] { "Ophthalmic Clinical Procedures",
				"9780080449784", "Elsevier(ClinicalKey)",
				"Frank Eperjesi & Hannah Bartlett & Mark Dunne", "", "",
				"2008/2/7", "eng", "", "011", "011", "", "", "", "電子書", "", "",
				"衛生福利部臺北醫院", "" });

		// Iterate over data and write to sheet
		Set<String> keyid = empinfo.keySet();
		int rowid = 0;
		for (String key : keyid) {
			row = spreadsheet.createRow(rowid++);
			Object[] objectArr = empinfo.get(key);
			int cellid = 0;
			for (Object obj : objectArr) {
				Cell cell = row.createCell(cellid++);
				cell.setCellValue((String) obj);
			}
		}

		ByteArrayOutputStream boas = new ByteArrayOutputStream();
		workbook.write(boas);
		getEntity()
				.setInputStream(new ByteArrayInputStream(boas.toByteArray()));

		return XLSX;
	}

	protected void setCategoryList() {
		List<Category> categoryList = new ArrayList<Category>(
				Arrays.asList(Category.values()));
		categoryList.remove(categoryList.size() - 1);
		ActionContext.getContext().getValueStack()
				.set("categoryList", categoryList);
	}

	protected boolean hasEntity() throws Exception {
		if (getEntity().getSerNo() == null) {
			getEntity().setSerNo(-1L);
			return false;
		}

		ebook = ebookService.getBySerNo(getEntity().getSerNo());
		if (ebook == null) {
			return false;
		}

		return true;
	}

	// 判斷文件類型
	protected Workbook createWorkBook(InputStream is) throws IOException {
		try {
			return WorkbookFactory.create(is);
		} catch (InvalidFormatException e) {
			return null;
		} catch (IllegalArgumentException e) {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	protected Object getEnum(String[] values, Class toClass) {
		return enumConverter.convertFromString(null, values, toClass);
	}
}
