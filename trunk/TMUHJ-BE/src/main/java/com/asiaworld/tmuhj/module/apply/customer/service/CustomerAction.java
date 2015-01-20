package com.asiaworld.tmuhj.module.apply.customer.service;

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
import java.util.regex.Pattern;

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
import com.asiaworld.tmuhj.module.apply.ipRange.entity.IpRange;
import com.asiaworld.tmuhj.module.apply.ipRange.service.IpRangeService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerAction extends GenericCRUDActionFull<Customer> {

	private String[] checkItem;

	private File file;

	private String fileFileName;

	private String fileContentType;

	@Autowired
	private Customer customer;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private IpRange ipRange;

	@Autowired
	private IpRangeService ipRangeService;

	@Autowired
	private ExcelWorkSheet<Customer> excelWorkSheet;

	private String importSerNo;

	private String[] importSerNos;

	private InputStream inputStream;

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
		if (getEntity().getSerNo() != null) {
			customer = customerService.getBySerNo(getEntity().getSerNo());
			setEntity(customer);
		} else if (getRequest().getParameter("goQueue") != null
				&& getRequest().getParameter("goQueue").equals("yes")) {
			getRequest().setAttribute("goQueue",
					getRequest().getParameter("goQueue"));
		}

		return EDIT;
	}

	@Override
	public String list() throws Exception {
		getRequest()
				.setAttribute("option", getRequest().getParameter("option"));

		DataSet<Customer> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds = customerService.getByRestrictions(ds);

		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (getEntity().getName().trim().equals("")
				|| getEntity().getName() == null) {
			addActionError("用戶名稱不可空白");
		}

		if (customerService.nameIsExist(getEntity())) {
			addActionError("用戶名稱已存在");
		}

		if (getEntity().getEmail() != null
				|| !getEntity().getEmail().equals("")) {
			if (!Pattern.compile(emailPattern).matcher(getEntity().getEmail())
					.matches()) {
				addActionError("email格式不正確");
			}
		}

		if (!hasActionErrors()) {
			customer = customerService.save(getEntity(), getLoginUser());
			setEntity(customer);

			addActionMessage("新增成功");
			return VIEW;
		} else {
			return EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (getEntity().getEmail() != null
				|| !getEntity().getEmail().equals("")) {
			if (!Pattern.compile(emailPattern).matcher(getEntity().getEmail())
					.matches()) {
				addActionError("email格式不正確");
			}
		}

		if (!hasActionErrors()) {
			customer = customerService.update(getEntity(), getLoginUser(),
					"name");
			setEntity(customer);
			addActionMessage("修改成功");
			return VIEW;
		} else {
			return EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		if (getEntity().getSerNo() <= 0) {
			addActionError("流水號不正確");
		} else if (customerService.getBySerNo(getEntity().getSerNo()) == null) {
			addActionError("沒有這個物件");
		}

		if (!hasActionErrors()) {
			Iterator<?> iterator = ipRangeService.getOwnerIpRangeByCusSerNo(
					getEntity().getSerNo()).iterator();
			while (iterator.hasNext()) {
				ipRange = (IpRange) iterator.next();
				ipRangeService.deleteBySerNo(ipRange.getSerNo());
			}

			customerService.deleteBySerNo(getEntity().getSerNo());

			DataSet<Customer> ds = initDataSet();
			ds.setPager(Pager.getChangedPager(
					getRequest().getParameter("recordPerPage"), getRequest()
							.getParameter("recordPoint"), ds.getPager()));
			ds = customerService.getByRestrictions(ds);

			setDs(ds);

			return LIST;
		} else {
			DataSet<Customer> ds = initDataSet();
			ds.setPager(Pager.getChangedPager(
					getRequest().getParameter("recordPerPage"), getRequest()
							.getParameter("recordPoint"), ds.getPager()));
			ds = customerService.getByRestrictions(ds);

			setDs(ds);
			return LIST;
		}
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
			int i = 0;
			while (i < checkItem.length) {
				if (customerService.getBySerNo(Long.parseLong(checkItem[i])) != null) {
					Iterator<?> iterator = ipRangeService
							.getOwnerIpRangeByCusSerNo(
									Long.parseLong(checkItem[i])).iterator();
					while (iterator.hasNext()) {
						ipRange = (IpRange) iterator.next();
						ipRangeService.deleteBySerNo(ipRange.getSerNo());
					}

					customerService.deleteBySerNo(Long.parseLong(checkItem[i]));
				}
				i++;
			}
			DataSet<Customer> ds = customerService
					.getByRestrictions(initDataSet());
			setDs(ds);
			addActionMessage("刪除成功");
			return LIST;
		} else {
			DataSet<Customer> ds = initDataSet();
			ds.setPager(Pager.getChangedPager(
					getRequest().getParameter("recordPerPage"), getRequest()
							.getParameter("recordPoint"), ds.getPager()));
			ds = customerService.getByRestrictions(ds);

			setDs(ds);
			return LIST;
		}
	}

	public String view() throws Exception {
		customer = customerService.getBySerNo(Long.parseLong(getRequest()
				.getParameter("viewSerNo")));
		setEntity(customer);
		return VIEW;
	}

	public String ajax() throws Exception {
		getRequest().setAttribute("customerUnits",
				customerService.getAllCustomers());
		return AJAX;
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
			excelWorkSheet = new ExcelWorkSheet<Customer>();

			// 保存工作單名稱
			Row firstRow = sheet.getRow(0);

			// 保存列名
			List<String> cellNames = new ArrayList<String>();
			String[] rowTitles = new String[6];
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

			LinkedHashSet<Customer> originalData = new LinkedHashSet<Customer>();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				String[] rowValues = new String[6];
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

				customer = new Customer(rowValues[0].trim(),
						rowValues[1].trim(), rowValues[2], rowValues[5],
						rowValues[4], rowValues[3], "", "");

				if (customer.getName().isEmpty()
						|| customer.getEngName().isEmpty()) {
					customer.setExistStatus("資料錯誤");
				} else {
					long cusSerNo = customerService.getCusSerNoByName(customer
							.getName());
					if (cusSerNo != 0) {
						customer.setExistStatus("已存在");
					} else {
						customer.setExistStatus("正常");
					}

				}
				originalData.add(customer);

			}

			Iterator<Customer> setIterator = originalData.iterator();
			int normal = 0;
			while (setIterator.hasNext()) {
				customer = setIterator.next();
				excelWorkSheet.getData().add(customer);
				if (customer.getExistStatus().equals("正常")) {
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
		List<Customer> customers = (List<Customer>) getSession().get(
				"importList");

		List<String> cellNames = (List<String>) getSession().get("cellNames");

		Map<String, Object> checkItemMap = (TreeMap<String, Object>) getSession()
				.get("checkItemMap");

		if (checkItemMap == null || checkItemMap.size() == 0) {
			addActionError("請選擇一筆或一筆以上的資料");
		}

		if (!hasActionErrors()) {
			Iterator<?> it = checkItemMap.values().iterator();
			List<Customer> importList = new ArrayList<Customer>();
			while (it.hasNext()) {
				String index = it.next().toString();
				importList.add(customers.get(Integer.parseInt(index)));
			}

			for (int i = 0; i < importList.size(); i++) {
				customerService.save(importList.get(i), getLoginUser());
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

			excelWorkSheet = new ExcelWorkSheet<Customer>();
			excelWorkSheet.setColumns(cellNames);
			excelWorkSheet.setData(customers);
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
	public ExcelWorkSheet<Customer> getExcelWorkSheet() {
		return excelWorkSheet;
	}

	/**
	 * @param excelWorkSheet
	 *            the excelWorkSheet to set
	 */
	public void setExcelWorkSheet(ExcelWorkSheet<Customer> excelWorkSheet) {
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

	/**
	 * @return the inputStream
	 */
	public InputStream getInputStream() {
		return inputStream;
	}

	/**
	 * @param inputStream
	 *            the inputStream to set
	 */
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
