package com.asiaworld.tmuhj.module.apply.ebook.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.ExcelWorkSheet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.customer.service.CustomerService;
import com.asiaworld.tmuhj.module.apply.ebook.entity.Ebook;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.entity.ResourcesBuyers;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.service.ResourcesBuyersService;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.entity.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;
import com.asiaworld.tmuhj.module.enums.Category;
import com.asiaworld.tmuhj.module.enums.Type;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class EbookAction extends GenericCRUDActionFull<Ebook> {

	private String[] checkItem;

	private String[] cusSerNo;

	private File file;

	private String fileFileName;

	private String fileContentType;

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

	private ExcelWorkSheet<Ebook> excelWorkSheet;

	private String importSerNo;

	private String[] importSerNos;

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
		} else if (getRequest().getParameter("goQueue") != null
				&& getRequest().getParameter("goQueue").equals("yes")) {
			getRequest().setAttribute("goQueue",
					getRequest().getParameter("goQueue"));
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

		DataSet<Ebook> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds = ebookService.getByRestrictions(ds);

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
			if (!isIsbn(getEntity().getIsbn())) {
				addActionError("ISBN不正確");
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
							Category.valueOf(getRequest().getParameter(
									"resourcesBuyers.rCategory")), Type
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
			if (!isIsbn(getEntity().getIsbn())) {
				addActionError("ISBN不正確");
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
			resourcesBuyers.setrCategory(Category.valueOf(getRequest()
					.getParameter("resourcesBuyers.rCategory")));
			resourcesBuyers.setrType(Type.valueOf(getRequest().getParameter(
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

	public String queue() throws Exception {
		if (file == null || !file.isFile()) {
			addActionError("請選擇檔案");
		} else {
			if (createWorkBook(new FileInputStream(file)) == null) {
				addActionError("檔案格式錯誤");
			}
		}

		if (!hasActionErrors()) {
			getSession().remove("importList");
			getSession().remove("checkItemMap");
			Workbook book = createWorkBook(new FileInputStream(file));
			// book.getNumberOfSheets(); 判斷Excel文件有多少個sheet
			Sheet sheet = book.getSheetAt(0);
			excelWorkSheet = new ExcelWorkSheet<Ebook>();

			// 保存工作單名稱
			Row firstRow = sheet.getRow(0);

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
						rowTitles[n] = firstRow.getCell(n).getStringCellValue()
								.trim();
						break;

					case 2:
						rowTitles[n] = firstRow.getCell(n).getCellFormula()
								.trim();
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

			getSession().put("cellNames", cellNames);
			excelWorkSheet.setColumns(cellNames);

			LinkedHashSet<Ebook> originalData = new LinkedHashSet<Ebook>();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

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
							rowValues[k] = row.getCell(k).getStringCellValue()
									.trim();
							break;

						case 2:
							rowValues[k] = row.getCell(k).getCellFormula()
									.trim();
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

				String category = "";
				if (rowValues[11].equals("")) {
					category = "未註明";
				} else if (rowValues[11].equals("買斷")
						|| rowValues[11].contains("買斷")) {
					category = "買斷";
				} else if (rowValues[11].equals("租貸")
						|| rowValues[11].contains("租")) {
					category = "租貸";
				} else {
					category = "不明";
				}

				resourcesBuyers = new ResourcesBuyers(rowValues[11],
						rowValues[12], Category.valueOf(category), Type.電子書,
						rowValues[15], rowValues[16]);

				String isbn = rowValues[1].trim().toUpperCase();
				String[] isbnSplit = isbn.split("-");

				isbn = "";
				int j = 0;
				while (j < isbnSplit.length) {
					isbn = isbn + isbnSplit[j];
					j++;
				}

				int version = 0;
				if (NumberUtils.isNumber(rowValues[8])) {
					double d = Double.parseDouble(rowValues[8]);
					version = (int) d;
				}

				ebook = new Ebook(rowValues[0], Long.parseLong(isbn),
						rowValues[2], rowValues[3], rowValues[4], rowValues[5],
						rowValues[6], rowValues[7], version, rowValues[9],
						rowValues[10], "", "", "", resourcesBuyers, null, "");

				customer = new Customer();
				customer.setName(rowValues[17].trim());
				customer.setEngName(rowValues[18].trim());

				List<Customer> customers = new ArrayList<Customer>();
				customers.add(customer);
				ebook.setCustomers(customers);

				if (isIsbn(Long.parseLong(isbn))) {
					long ebkSerNo = ebookService.getEbkSerNoByIsbn(Long
							.parseLong(isbn));

					long cusSerNo = customerService
							.getCusSerNoByName(rowValues[17].trim());
					if (cusSerNo != 0) {
						if (ebkSerNo != 0) {
							if (resourcesUnionService.isExist(
									ebookService.getBySerNo(ebkSerNo),
									Ebook.class, cusSerNo)) {

								ebook.setExistStatus("已存在");
							} else {
								ebook.setExistStatus("正常");
							}
						} else {
							if (ebook.getResourcesBuyers().getrCategory()
									.equals(Category.不明)) {
								ebook.setExistStatus("資源類型不明");
							} else {
								ebook.setExistStatus("正常");
							}
						}
					} else {
						ebook.setExistStatus("無此客戶");
					}
				} else {
					ebook.setExistStatus("ISBN異常");
				}
				originalData.add(ebook);
			}
			Iterator<Ebook> setIterator = originalData.iterator();
			int normal = 0;
			while (setIterator.hasNext()) {
				ebook = setIterator.next();
				excelWorkSheet.getData().add(ebook);
				if (ebook.getExistStatus().equals("正常")) {
					normal = normal + 1;
				}
			}

			getSession().put("importList", excelWorkSheet.getData());
			getSession().put("total", excelWorkSheet.getData().size());
			getSession().put("normal", normal);
			getSession().put("abnormal",
					excelWorkSheet.getData().size() - normal);

			return QUEUE;
		} else {
			getRequest().setAttribute("goQueue", "yes");
			return EDIT;
		}
	}

	@SuppressWarnings("unchecked")
	public void getCheckedItem() {
		Map<String, Object> checkItemMap;
		if (getSession().containsKey("checkItemMap")) {
			checkItemMap = (TreeMap<String, Object>) getSession().get(
					"checkItemMap");
		} else {
			checkItemMap = new TreeMap<String, Object>();
		}

		if (!checkItemMap.containsKey(this.importSerNo)) {
			checkItemMap.put(this.importSerNo, this.importSerNo);
		} else {
			checkItemMap.remove(this.importSerNo);
		}
		getSession().put("checkItemMap", checkItemMap);
		System.out.println(checkItemMap);
	}

	public void allCheckedItem() {
		Map<String, Object> checkItemMap = new TreeMap<String, Object>();

		int i = 0;
		while (i < importSerNos.length) {
			checkItemMap.put(importSerNos[i], importSerNos[i]);
			i++;
		}

		getSession().put("checkItemMap", checkItemMap);
		System.out.println(checkItemMap);
	}

	public void clearCheckedItem() {
		Map<String, Object> checkItemMap = new TreeMap<String, Object>();
		getSession().put("checkItemMap", checkItemMap);
		System.out.println(checkItemMap);
	}

	@SuppressWarnings("unchecked")
	public String importData() throws Exception {
		List<Ebook> ebooks = (List<Ebook>) getSession().get("importList");

		List<String> cellNames = (List<String>) getSession().get("cellNames");

		Map<String, Object> checkItemMap = (TreeMap<String, Object>) getSession()
				.get("checkItemMap");

		if (checkItemMap == null || checkItemMap.size() == 0) {
			addActionError("請選擇一筆或一筆以上的資料");
		}

		if (!hasActionErrors()) {
			Iterator<?> it = checkItemMap.values().iterator();
			List<Ebook> importList = new ArrayList<Ebook>();
			while (it.hasNext()) {
				String index = it.next().toString();
				importList.add(ebooks.get(Integer.parseInt(index)));
			}

			for (int i = 0; i < importList.size(); i++) {
				long ebkSerNo = ebookService.getEbkSerNoByIsbn(importList
						.get(i).getIsbn());
				long cusSerNo = customerService.getCusSerNoByName(importList
						.get(i).getCustomers().get(0).getName());

				if (ebkSerNo == 0) {
					resourcesBuyers = resourcesBuyersService.save(importList
							.get(i).getResourcesBuyers(), getLoginUser());
					ebook = ebookService
							.save(importList.get(i), getLoginUser());

					resourcesUnionService.save(
							new ResourcesUnion(cusSerNo, resourcesBuyers
									.getSerNo(), ebook.getSerNo(), 0, 0),
							getLoginUser());
				} else {
					resourcesUnion = resourcesUnionService.getByObjSerNo(
							ebkSerNo, Ebook.class);
					resourcesUnionService.save(new ResourcesUnion(cusSerNo,
							resourcesUnion.getResSerNo(), ebkSerNo, 0, 0),
							getLoginUser());

				}
			}

			clearCheckedItem();
			getRequest().setAttribute("successCount", importList.size());
			return VIEW;
		} else {
			Object total = getSession().get("total");
			Object normal = getSession().get("normal");
			Object abnormal = getSession().get("abnormal");

			getRequest().setAttribute("beforeRow",
					getRequest().getParameter("beforeRow"));
			getRequest().setAttribute("beforeMaxRows",
					getRequest().getParameter("beforeMaxRows"));

			getSession().put("total", total);
			getSession().put("normal", normal);
			getSession().put("abnormal", abnormal);

			getSession().put("importList", getSession().get("importList"));

			getSession().put("cellNames", getSession().get("cellNames"));

			excelWorkSheet = new ExcelWorkSheet<Ebook>();
			excelWorkSheet.setColumns(cellNames);
			excelWorkSheet.setData(ebooks);
			return QUEUE;
		}
	}

	public boolean isIsbn(long isbnNum) {
		if (isbnNum >= 9780000000000l && isbnNum < 9800000000000l) {
			String isbn = "" + isbnNum;
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
					return false;
				}
			} else {
				if (Integer.parseInt(isbn.substring(12)) != num) {
					return false;
				}
			}

		} else {
			return false;
		}
		return true;
	}

	// 判斷文件類型
	public Workbook createWorkBook(InputStream is) throws IOException {
		if (fileFileName.toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook(is);
		}
		if (fileFileName.toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(is);
		}
		return null;
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

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the fileFileName
	 */
	public String getFileFileName() {
		return fileFileName;
	}

	/**
	 * @param fileFileName
	 *            the fileFileName to set
	 */
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * @return the fileContentType
	 */
	public String getFileContentType() {
		return fileContentType;
	}

	/**
	 * @param fileContentType
	 *            the fileContentType to set
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * @return the excelWorkSheet
	 */
	public ExcelWorkSheet<Ebook> getExcelWorkSheet() {
		return excelWorkSheet;
	}

	/**
	 * @param excelWorkSheet
	 *            the excelWorkSheet to set
	 */
	public void setExcelWorkSheet(ExcelWorkSheet<Ebook> excelWorkSheet) {
		this.excelWorkSheet = excelWorkSheet;
	}

	/**
	 * @return the importSerNo
	 */
	public String getImportSerNo() {
		return importSerNo;
	}

	/**
	 * @param importSerNo
	 *            the importSerNo to set
	 */
	public void setImportSerNo(String importSerNo) {
		this.importSerNo = importSerNo;
	}

	/**
	 * @return the importSerNos
	 */
	public String[] getImportSerNos() {
		return importSerNos;
	}

	/**
	 * @param importSerNos
	 *            the importSerNos to set
	 */
	public void setImportSerNos(String[] importSerNos) {
		this.importSerNos = importSerNos;
	}

}
