package com.asiaworld.tmuhj.module.apply.journal.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.enums.RCategory;
import com.asiaworld.tmuhj.core.enums.RType;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.ExcelWorkSheet;
import com.asiaworld.tmuhj.core.model.Pager;
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

	private String[] checkItem;

	private String[] cusSerNo;

	private File file;

	private String fileFileName;

	private String fileContentType;

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

	// private String destPath = "C:/tmuhj/";

	private ExcelWorkSheet<Journal> excelWorkSheet;

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
			journal = journalService.getBySerNo(getEntity().getSerNo());
			Iterator<?> iterator = resourcesUnionService
					.getResourcesUnionsByObj(getEntity(), Journal.class)
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
			journal.setCustomers(customers);
			setEntity(journal);
		} else if (getRequest().getParameter("goImport") != null
				&& getRequest().getParameter("goImport").equals("yes")) {
			getRequest().setAttribute("goImport",
					getRequest().getParameter("goImport"));
		} else {
			List<Customer> customers = new ArrayList<Customer>();
			journal.setCustomers(customers);
			setEntity(journal);
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		getRequest()
				.setAttribute("option", getRequest().getParameter("option"));

		String recordPerPage = getRequest().getParameter("recordPerPage");
		String recordPoint = getRequest().getParameter("recordPoint");
		DataSet<Journal> ds = initDataSet();
		Pager pager = ds.getPager();

		if (pager != null) {
			if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
					&& Integer.parseInt(recordPerPage) > 0
					&& recordPoint != null && NumberUtils.isDigits(recordPoint)
					&& Integer.parseInt(recordPoint) >= 0) {
				pager.setRecordPerPage(Integer.parseInt(recordPerPage));
				pager.setCurrentPage(Integer.parseInt(recordPoint)
						/ Integer.parseInt(recordPerPage) + 1);
				pager.setOffset(Integer.parseInt(recordPerPage)
						* (pager.getCurrentPage() - 1));
				pager.setRecordPoint(Integer.parseInt(recordPoint));
				ds.setPager(pager);
			} else if (recordPerPage != null
					&& NumberUtils.isDigits(recordPerPage)
					&& Integer.parseInt(recordPerPage) > 0
					&& recordPoint == null) {
				pager.setRecordPerPage(Integer.parseInt(recordPerPage));
				pager.setRecordPoint(pager.getOffset());
				ds.setPager(pager);
			} else {
				pager.setRecordPoint(pager.getOffset());
				ds.setPager(pager);
			}
		}

		ds = journalService.getByRestrictions(ds);
		List<Journal> results = ds.getResults();

		int i = 0;
		while (i < results.size()) {
			results.get(i).setResourcesBuyers(
					resourcesBuyersService.getBySerNo(resourcesUnionService
							.getByObjSerNo(results.get(i).getSerNo(),
									Journal.class).getResSerNo()));
			i++;
		}

		ds.setResults(results);
		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		if (getEntity().getEnglishTitle() == null
				|| getEntity().getEnglishTitle().trim().equals("")) {
			addActionError("英文刊名不得空白");
		}

		if (getEntity().getIssn() == null
				|| getEntity().getIssn().trim().equals("")) {
			addActionError("ISSN不得空白");
		} else {
			if (!isIssn(getEntity().getIssn())) {
				addActionError("ISSN不正確");
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
			journal = getEntity();
			journal.setIssn(getEntity().getIssn().toUpperCase());
			journal = journalService.save(journal, getLoginUser());

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
								resourcesBuyers.getSerNo(), 0, 0, journal
										.getSerNo()), getLoginUser());

				i++;
			}

			resourcesUnion = resourcesUnionService.getByObjSerNo(
					journal.getSerNo(), Journal.class);
			journal.setResourcesBuyers(resourcesBuyersService
					.getBySerNo(resourcesUnion.getResSerNo()));

			List<?> resourceUnions = resourcesUnionService
					.getResourcesUnionsByObj(journal, Journal.class);
			List<Customer> customers = new ArrayList<Customer>();

			Iterator<?> iterator = resourceUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = (ResourcesUnion) iterator.next();
				customers.add(customerService.getBySerNo(resourcesUnion
						.getCusSerNo()));
			}

			journal.setCustomers(customers);
			setEntity(journal);
			addActionMessage("新增成功");
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

			journal = getEntity();
			journal.setCustomers(customers);
			setEntity(journal);
			return EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		if (getEntity().getEnglishTitle() == null
				|| getEntity().getEnglishTitle().trim().equals("")) {
			addActionError("英文刊名不得空白");
		}

		if (getEntity().getIssn() == null
				|| getEntity().getIssn().trim().equals("")) {
			addActionError("ISSN不得空白");
		} else {
			if (!isIssn(getEntity().getIssn())) {
				addActionError("ISSN不正確");
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
			journal = getEntity();
			journal.setIssn(getEntity().getIssn().toUpperCase());
			journal = journalService.update(journal, getLoginUser());

			resourcesBuyers = resourcesBuyersService
					.getBySerNo(resourcesUnionService.getByObjSerNo(
							journal.getSerNo(), Journal.class).getResSerNo());
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
					.getResourcesUnionsByObj(journal, Journal.class);

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
				if (!resourcesUnionService.isExist(journal, Journal.class,
						Long.parseLong(cusSerNo[i]))) {
					resourcesUnionService.save(
							new ResourcesUnion(Long.parseLong(cusSerNo[i]),
									resourcesBuyers.getSerNo(), 0, 0, journal
											.getSerNo()), getLoginUser());
				}

				i++;
			}

			resourcesUnion = resourcesUnionService.getByObjSerNo(
					journal.getSerNo(), Journal.class);
			journal.setResourcesBuyers(resourcesBuyersService
					.getBySerNo(resourcesUnion.getResSerNo()));

			List<?> resourceUnions = resourcesUnionService
					.getResourcesUnionsByObj(journal, Journal.class);
			List<Customer> customers = new ArrayList<Customer>();

			iterator = resourceUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = (ResourcesUnion) iterator.next();
				customers.add(customerService.getBySerNo(resourcesUnion
						.getCusSerNo()));
			}

			journal.setCustomers(customers);
			setEntity(journal);
			addActionMessage("修改成功");
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

			journal = getEntity();
			journal.setCustomers(customers);
			setEntity(journal);
			return EDIT;
		}

	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String view() throws NumberFormatException, Exception {
		journal = journalService.getBySerNo(Long.parseLong(getRequest()
				.getParameter("viewSerNo")));
		resourcesUnion = resourcesUnionService.getByObjSerNo(
				journal.getSerNo(), Journal.class);
		journal.setResourcesBuyers(resourcesBuyersService
				.getBySerNo(resourcesUnion.getResSerNo()));

		List<?> resourceUnions = resourcesUnionService.getResourcesUnionsByObj(
				journal, Journal.class);
		List<Customer> customers = new ArrayList<Customer>();

		Iterator<?> iterator = resourceUnions.iterator();
		while (iterator.hasNext()) {
			resourcesUnion = (ResourcesUnion) iterator.next();
			customers.add(customerService.getBySerNo(resourcesUnion
					.getCusSerNo()));
		}

		journal.setCustomers(customers);
		setEntity(journal);
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
				if (journalService.getBySerNo(Long.parseLong(checkItem[j])) != null) {
					List<?> resourcesUnions = resourcesUnionService
							.getResourcesUnionsByObj(journalService
									.getBySerNo(Long.parseLong(checkItem[j])),
									Journal.class);
					resourcesUnion = (ResourcesUnion) resourcesUnions.get(0);
					resourcesBuyersService.deleteBySerNo(resourcesUnion
							.getResSerNo());

					Iterator<?> iterator = resourcesUnions.iterator();
					while (iterator.hasNext()) {
						resourcesUnion = (ResourcesUnion) iterator.next();
						resourcesUnionService.deleteBySerNo(resourcesUnion
								.getSerNo());
					}

					journalService.deleteBySerNo(Long.parseLong(checkItem[j]));
				}
				j++;
			}

			DataSet<Journal> ds = journalService
					.getByRestrictions(initDataSet());
			List<Journal> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setResourcesBuyers(
						resourcesBuyersService.getBySerNo(resourcesUnionService
								.getByObjSerNo(results.get(i).getSerNo(),
										Journal.class).getResSerNo()));
				i++;
			}

			setDs(ds);
			addActionMessage("刪除成功");
			return LIST;
		} else {
			DataSet<Journal> ds = journalService
					.getByRestrictions(initDataSet());
			List<Journal> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setResourcesBuyers(
						resourcesBuyersService.getBySerNo(resourcesUnionService
								.getByObjSerNo(results.get(i).getSerNo(),
										Journal.class).getResSerNo()));
				i++;
			}

			setDs(ds);
			return LIST;
		}
	}

	public String imports() throws Exception {
		if (file == null || !file.isFile()) {
			addActionError("請選擇檔案");
		} else {
			if (createWorkBook(new FileInputStream(file)) == null) {
				addActionError("檔案格式錯誤");
			}
		}

		if (!hasActionErrors()) {
			Workbook book = createWorkBook(new FileInputStream(file));
			// book.getNumberOfSheets(); 判斷Excel文件有多少個sheet
			Sheet sheet = book.getSheetAt(0);
			excelWorkSheet = new ExcelWorkSheet<Journal>();

			// 保存工作單名稱
			Row firstRow = sheet.getRow(0);
			Iterator<Cell> iterator = firstRow.iterator();

			// 保存列名
			List<String> cellNames = new ArrayList<String>();
			while (iterator.hasNext()) {
				cellNames.add(iterator.next().getStringCellValue());
			}
			excelWorkSheet.setColumns(cellNames);
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				String category = row.getCell(11).getStringCellValue();
				if (category == null || category.trim().equals("")) {
					category = "未註明";
				}

				resourcesBuyers = new ResourcesBuyers(row.getCell(9)
						.getStringCellValue(), row.getCell(10)
						.getStringCellValue(), RCategory.valueOf(category),
						RType.valueOf(row.getCell(12).getStringCellValue()
								.trim()), row.getCell(13).getStringCellValue(),
						row.getCell(14).getStringCellValue());

				journal = new Journal(row.getCell(0).getStringCellValue(), row
						.getCell(1).getStringCellValue(), row.getCell(2)
						.getStringCellValue(), "", row.getCell(3)
						.getStringCellValue(), row.getCell(4)
						.getStringCellValue(), row.getCell(5)
						.getStringCellValue(), row.getCell(6)
						.getStringCellValue(), "", "", "", row.getCell(7)
						.getStringCellValue(), row.getCell(8)
						.getStringCellValue(), 0, resourcesBuyers, null, "");

				customer = new Customer();
				customer.setName(row.getCell(15).getStringCellValue());
				customer.setEngName(row.getCell(16).getStringCellValue());

				List<Customer> customers = new ArrayList<Customer>();
				customers.add(customer);
				journal.setCustomers(customers);

				String issn = row.getCell(3).getStringCellValue().trim();
				String[] issnSplit = issn.split("-");

				issn = "";
				int j = 0;
				while (j < issnSplit.length) {
					issn = issn + issnSplit[j];
					j++;
				}

				if (isIssn(issn)) {
					long jouSerNo = journalService.getJouSerNoByIssn(issn
							.toUpperCase());
					long cusSerNo = customerService.getCusSerNoByName(row
							.getCell(15).getStringCellValue().trim(), row
							.getCell(16).getStringCellValue().trim());
					if (cusSerNo != 0) {
						if (jouSerNo != 0) {
							if (resourcesUnionService.isExist(
									journalService.getBySerNo(jouSerNo),
									Journal.class, cusSerNo)) {

								journal.setExistStatus("已存在");
							} else {
								journal.setExistStatus("正常");
							}
						} else {
							journal.setExistStatus("正常");
						}
					} else {
						journal.setExistStatus("資料錯誤");
					}
				} else {
					journal.setExistStatus("資料錯誤");
				}

				excelWorkSheet.getData().add(journal);
			}
			for (int k = 0; k < excelWorkSheet.getData().size(); k++) {
				journal = excelWorkSheet.getData().get(k);
				System.out.println(journal.getEnglishTitle());
			}

			return "import";
		} else {
			getRequest().setAttribute("goImport", "yes");
			return EDIT;
		}
	}

	public boolean isIssn(String issn) {
		String regex = "\\d{7}[\\dX]";
		Pattern pattern = Pattern.compile(regex);

		Matcher matcher = pattern.matcher(issn.toUpperCase());
		if (matcher.matches()) {
			int sum = Integer.parseInt(issn.substring(0, 1)) * 8
					+ Integer.parseInt(issn.substring(1, 2)) * 7
					+ Integer.parseInt(issn.substring(2, 3)) * 6
					+ Integer.parseInt(issn.substring(3, 4)) * 5
					+ Integer.parseInt(issn.substring(4, 5)) * 4
					+ Integer.parseInt(issn.substring(5, 6)) * 3
					+ Integer.parseInt(issn.substring(6, 7)) * 2;

			int remainder = sum % 11;

			if (remainder == 0) {
				if (!issn.substring(7).equals("0")) {
					return false;
				}
			} else {
				if (11 - remainder == 10) {
					if (!issn.substring(7).toUpperCase().equals("X")) {
						return false;
					}
				} else {
					if (issn.substring(7).equals("X")
							|| issn.substring(7).equals("x")
							|| Integer.parseInt(issn.substring(7)) != 11 - remainder) {
						return false;
					}
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
	public ExcelWorkSheet<Journal> getExcelWorkSheet() {
		return excelWorkSheet;
	}

	/**
	 * @param excelWorkSheet
	 *            the excelWorkSheet to set
	 */
	public void setExcelWorkSheet(ExcelWorkSheet<Journal> excelWorkSheet) {
		this.excelWorkSheet = excelWorkSheet;
	}

}
