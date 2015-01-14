package com.asiaworld.tmuhj.core.security.accountNumber.service;

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

import com.asiaworld.tmuhj.core.enums.Role;
import com.asiaworld.tmuhj.core.enums.Status;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.ExcelWorkSheet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.security.accountNumber.entity.AccountNumber;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.customer.service.CustomerService;

/**
 * 使用者
 * 
 * @author Roderick
 * @version 2014/9/29
 */
@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AccountNumberAction extends GenericCRUDActionFull<AccountNumber> {

	private String[] checkItem;

	private File file;

	private String fileFileName;

	private String fileContentType;

	@Autowired
	AccountNumber accountNumber;

	@Autowired
	AccountNumberService accountNumberService;

	@Autowired
	Customer customer;

	@Autowired
	CustomerService customerService;

	@Autowired
	DataSet<Customer> dsCustomer;

	private ExcelWorkSheet<AccountNumber> excelWorkSheet;

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
		dsCustomer.setEntity(customer);
		dsCustomer = customerService.getByRestrictions(dsCustomer);
		if (getEntity().getSerNo() != null) {
			accountNumber = accountNumberService.getBySerNo(getEntity()
					.getSerNo());
			setEntity(accountNumber);
		} else if (getRequest().getParameter("goQueue") != null
				&& getRequest().getParameter("goQueue").equals("yes")) {
			getRequest().setAttribute("goQueue",
					getRequest().getParameter("goQueue"));
		}

		return EDIT;
	}

	@Override
	public String list() throws Exception {
		DataSet<AccountNumber> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds = accountNumberService.getByRestrictions(ds);

		List<AccountNumber> results = ds.getResults();

		int i = 0;
		while (i < results.size()) {
			results.get(i).setCustomer(
					customerService.getBySerNo(results.get(i).getCusSerNo()));
			i++;
		}

		ds.setResults(results);

		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		if (getEntity().getUserId() == null
				|| getEntity().getUserId().trim().equals("")) {
			addActionError("用戶代碼不得空白");
		}

		if (getEntity().getUserPw() == null
				|| getEntity().getUserPw().trim().equals("")) {
			addActionError("用戶密碼不得空白");
		}

		if (getEntity().getUserName() == null
				|| getEntity().getUserName().trim().equals("")) {
			addActionError("用戶姓名不得空白");
		}

		if (getEntity().getCusSerNo() < 1) {
			addActionError("用戶名稱必選");
		}

		if (getEntity().getUserId() != null
				&& !getEntity().getUserId().trim().equals("")) {
			if (accountNumberService.userIdIsExist(getEntity())) {
				addActionError("用戶代碼已存在");
			}

			if (getEntity().getUserId().trim().equals("guest")) {
				addActionError("不可使用guest作為用戶代碼");
			}
		}

		if (!hasActionErrors()) {
			accountNumber = accountNumberService.save(getEntity(),
					getLoginUser());
			accountNumber.setCustomer(customerService.getBySerNo(accountNumber
					.getCusSerNo()));
			setEntity(accountNumber);
			return VIEW;
		} else {
			return EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		if (getEntity().getUserPw() == null
				|| getEntity().getUserPw().trim().equals("")) {
			addActionError("用戶密碼不得空白");
		}

		if (getEntity().getUserName() == null
				|| getEntity().getUserName().trim().equals("")) {
			addActionError("用戶姓名不得空白");
		}

		if (getEntity().getCusSerNo() < 1) {
			addActionError("用戶名稱必選");
		}
		if (!hasActionErrors()) {
			if (getEntity().getUserPw() == null
					|| getEntity().getUserPw().trim().equals("")) {
				accountNumber = accountNumberService.update(getEntity(),
						getLoginUser(), "userId", "userPw");
			} else {
				accountNumber = accountNumberService.update(getEntity(),
						getLoginUser(), "userId");
			}

			setEntity(accountNumber);
			addActionMessage("修改成功");
			return VIEW;
		} else {
			return EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public String invalidChecked() throws Exception {
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
				if (accountNumberService.getBySerNo(Long
						.parseLong(checkItem[j])) != null) {
					accountNumber = accountNumberService.getBySerNo(Long
							.parseLong(checkItem[j]));
					accountNumber.setStatus(Status.不生效);
					accountNumberService.update(accountNumberService
							.getBySerNo(Long.parseLong(checkItem[j])),
							accountNumber);
				}
				j++;
			}
			DataSet<AccountNumber> ds = accountNumberService
					.getByRestrictions(initDataSet());
			List<AccountNumber> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setCustomer(
						customerService
								.getBySerNo(results.get(i).getCusSerNo()));
				i++;
			}

			ds.setResults(results);

			setDs(ds);
			addActionMessage("更新成功");
			return LIST;
		} else {
			DataSet<AccountNumber> ds = accountNumberService
					.getByRestrictions(initDataSet());
			List<AccountNumber> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setCustomer(
						customerService
								.getBySerNo(results.get(i).getCusSerNo()));
				i++;
			}

			ds.setResults(results);

			setDs(ds);
			return LIST;
		}
	}

	public String validChecked() throws Exception {
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
				if (accountNumberService.getBySerNo(Long
						.parseLong(checkItem[j])) != null) {
					accountNumber = accountNumberService.getBySerNo(Long
							.parseLong(checkItem[j]));
					accountNumber.setStatus(Status.生效);
					accountNumberService.update(accountNumberService
							.getBySerNo(Long.parseLong(checkItem[j])),
							accountNumber);
				}
				j++;
			}
			DataSet<AccountNumber> ds = initDataSet();
			ds.setPager(Pager.getChangedPager(
					getRequest().getParameter("recordPerPage"), getRequest()
							.getParameter("recordPoint"), ds.getPager()));
			ds = accountNumberService.getByRestrictions(ds);
			List<AccountNumber> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setCustomer(
						customerService
								.getBySerNo(results.get(i).getCusSerNo()));
				i++;
			}

			ds.setResults(results);

			setDs(ds);
			addActionMessage("更新成功");
			return LIST;
		} else {
			DataSet<AccountNumber> ds = accountNumberService
					.getByRestrictions(initDataSet());
			List<AccountNumber> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setCustomer(
						customerService
								.getBySerNo(results.get(i).getCusSerNo()));
				i++;
			}

			ds.setResults(results);

			setDs(ds);
			return LIST;
		}
	}

	public String view() throws Exception {
		accountNumber = accountNumberService.getBySerNo(Long
				.parseLong(getRequest().getParameter("viewSerNo")));
		accountNumber.setCustomer(customerService.getBySerNo(accountNumber
				.getCusSerNo()));
		setEntity(accountNumber);
		return VIEW;
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
			excelWorkSheet = new ExcelWorkSheet<AccountNumber>();

			// 保存工作單名稱
			Row firstRow = sheet.getRow(0);

			// 保存列名
			List<String> cellNames = new ArrayList<String>();
			String[] rowTitles = new String[5];
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

			LinkedHashSet<AccountNumber> originalData = new LinkedHashSet<AccountNumber>();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				String[] rowValues = new String[5];
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

				String role = "";
				if (rowValues[4].trim().equals("")) {
					role = "不明";
				} else if (rowValues[4].trim().equals("系統管理員")) {
					role = "系統管理員";
				} else if (rowValues[4].trim().equals("維護人員")) {
					role = "維護人員";
				} else if (rowValues[4].trim().equals("管理員")) {
					role = "管理員";
				} else if (rowValues[4].trim().equals("使用者")) {
					role = "使用者";
				} else {
					role = "不明";
				}

				accountNumber = new AccountNumber(
						customerService.getCusSerNoByName(rowValues[3].trim()),
						rowValues[0].trim(), rowValues[1].trim(),
						rowValues[2].trim(), Role.valueOf(role), Status.審核中,
						null, "");

				long serNo = accountNumberService
						.getSerNoByUserId(accountNumber.getUserId());
				if (serNo != 0) {
					customer = new Customer();
					customer.setName(rowValues[3].trim());
					accountNumber.setCustomer(customer);
					accountNumber.setExistStatus("已存在");
				} else {
					if (accountNumber.getUserId().isEmpty()
							|| accountNumber.getUserPw().isEmpty()
							|| accountNumber.getCusSerNo() == 0
							|| accountNumber.getRole().equals(Role.不明)) {
						customer = new Customer();
						customer.setName(rowValues[3].trim());
						accountNumber.setCustomer(customer);
						accountNumber.setExistStatus("資料錯誤");
					}else{
						customer = new Customer();
						customer.setName(rowValues[3].trim());
						accountNumber.setCustomer(customer);
						accountNumber.setExistStatus("正常");
					}
				}

				originalData.add(accountNumber);

			}

			Iterator<AccountNumber> setIterator = originalData.iterator();
			int normal = 0;
			while (setIterator.hasNext()) {
				accountNumber = setIterator.next();
				excelWorkSheet.getData().add(accountNumber);
				if (accountNumber.getExistStatus().equals("正常")) {
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
		List<AccountNumber> accountNumbers = (List<AccountNumber>) getSession()
				.get("importList");

		List<String> cellNames = (List<String>) getSession().get("cellNames");

		Map<String, Object> checkItemMap = (TreeMap<String, Object>) getSession()
				.get("checkItemMap");

		if (checkItemMap == null || checkItemMap.size() == 0) {
			addActionError("請選擇一筆或一筆以上的資料");
		}

		if (!hasActionErrors()) {
			Iterator<?> it = checkItemMap.values().iterator();
			List<AccountNumber> importList = new ArrayList<AccountNumber>();
			while (it.hasNext()) {
				String index = it.next().toString();
				importList.add(accountNumbers.get(Integer.parseInt(index)));
			}

			for (int i = 0; i < importList.size(); i++) {
				accountNumberService.save(importList.get(i), getLoginUser());
			}
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

			excelWorkSheet = new ExcelWorkSheet<AccountNumber>();
			excelWorkSheet.setColumns(cellNames);
			excelWorkSheet.setData(accountNumbers);
			return QUEUE;
		}
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
	 * @return the dsCustomer
	 */
	public DataSet<Customer> getDsCustomer() {
		return dsCustomer;
	}

	/**
	 * @param dsCustomer
	 *            the dsCustomer to set
	 */
	public void setDsCustomer(DataSet<Customer> dsCustomer) {
		this.dsCustomer = dsCustomer;
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
	public ExcelWorkSheet<AccountNumber> getExcelWorkSheet() {
		return excelWorkSheet;
	}

	/**
	 * @param excelWorkSheet
	 *            the excelWorkSheet to set
	 */
	public void setExcelWorkSheet(ExcelWorkSheet<AccountNumber> excelWorkSheet) {
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
