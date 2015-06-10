package com.asiaworld.tmuhj.core.apply.customer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.apply.enums.Role;
import com.asiaworld.tmuhj.core.apply.ipRange.IpRange;
import com.asiaworld.tmuhj.core.apply.ipRange.IpRangeService;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CustomerAction extends GenericCRUDActionFull<Customer> {

	private String[] checkItem;

	private File[] file;

	private String[] fileFileName;

	private String[] fileContentType;

	@Autowired
	private Customer customer;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private IpRange ipRange;

	@Autowired
	private IpRangeService ipRangeService;

	private String[] importSerNos;

	private InputStream inputStream;

	private String reportFile;

	private String[] jsonString;

	@Override
	protected void validateSave() throws Exception {
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (StringUtils.isBlank(getEntity().getName())) {
			errorMessages.add("用戶名稱不可空白");
		} else {
			if (getEntity().getName()
					.replaceAll("[a-zA-Z0-9\u4e00-\u9fa5]", "").length() != 0) {
				errorMessages.add("用戶名稱必須是英、數或漢字");
			} else {
				if (customerService.getCusSerNoByName(getEntity().getName()) != 0) {
					errorMessages.add("用戶名稱已存在");
				}
			}
		}

		if (StringUtils.isNotEmpty(getEntity().getEmail())) {
			if (!Pattern.compile(emailPattern).matcher(getEntity().getEmail())
					.matches()) {
				errorMessages.add("email格式不正確");
			}
		}

		if (StringUtils.isNotEmpty(getEntity().getTel())) {
			String tel = getEntity().getTel().replaceAll("[/()+-]", "")
					.replace(" ", "");
			if (!NumberUtils.isDigits(tel)) {
				errorMessages.add("電話格式不正確");
			}
		}
	}

	@Override
	protected void validateUpdate() throws Exception {
		String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

		if (!hasEntity()) {
			errorMessages.add("Target must not be null");
		}

		if (StringUtils.isNotEmpty(getEntity().getEmail())) {
			if (!Pattern.compile(emailPattern).matcher(getEntity().getEmail())
					.matches()) {
				errorMessages.add("email格式不正確");
			}
		}

		if (StringUtils.isNotEmpty(getEntity().getTel())) {
			String tel = getEntity().getTel().replaceAll("[/()+-]", "")
					.replace(" ", "");
			if (!NumberUtils.isDigits(tel)) {
				errorMessages.add("電話格式不正確");
			}
		}

	}

	@Override
	protected void validateDelete() throws Exception {
		if (getLoginUser().getRole().equals(Role.系統管理員)) {
			if (ArrayUtils.isEmpty(checkItem)) {
				errorMessages.add("請選擇一筆或一筆以上的資料");
			} else {
				int i = 0;
				while (i < checkItem.length) {
					if (!NumberUtils.isDigits(String.valueOf(checkItem[i]))
							|| Long.parseLong(checkItem[i]) < 1
							|| Long.parseLong(checkItem[i]) == 9
							|| customerService.getBySerNo(Long
									.parseLong(checkItem[i])) == null) {
						errorMessages.add(checkItem[i] + "為不可利用的流水號");
					}
					i++;
				}
			}
		} else {
			errorMessages.add("權限不足");
		}
	}

	@Override
	public String edit() throws Exception {
		if (getEntity().getSerNo() != null) {
			customer = customerService.getBySerNo(getEntity().getSerNo());
			setEntity(customer);
		}

		return EDIT;
	}

	@Override
	public String list() throws Exception {
		if (StringUtils.isNotBlank(getRequest().getParameter("option"))) {
			if (getRequest().getParameter("option").equals("entity.name")
					|| getRequest().getParameter("option").equals(
							"entity.engName")) {
				getRequest().setAttribute("option",
						getRequest().getParameter("option"));
			} else {
				getRequest().setAttribute("option", "entity.name");
			}
		} else {
			getRequest().setAttribute("option", "entity.name");
		}

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
		validateSave();
		setActionErrors(errorMessages);

		if (!hasActionErrors()) {
			customer = customerService.save(getEntity(), getLoginUser());
			setEntity(customer);

			addActionMessage("新增成功");
			return VIEW;
		} else {
			setEntity(getEntity());
			return EDIT;
		}
	}

	@Override
	public String update() throws Exception {
		validateUpdate();
		setActionErrors(errorMessages);

		if (!hasActionErrors()) {
			customer = customerService.update(getEntity(), getLoginUser(),
					"name");
			setEntity(customer);
			addActionMessage("修改成功");
			return VIEW;
		} else {
			if (hasEntity()) {
				getEntity().setName(
						customerService.getBySerNo(getEntity().getSerNo())
								.getName());
			}

			setEntity(getEntity());
			return EDIT;
		}
	}

	@Override
	public String delete() throws Exception {
		validateDelete();
		setActionErrors(errorMessages);

		if (!hasActionErrors()) {
			int i = 0;
			while (i < checkItem.length) {
				String name = customerService.getBySerNo(
						Long.parseLong(checkItem[i])).getName();
				if (customerService
						.deleteOwnerObj(Long.parseLong(checkItem[i]))) {
					customerService.deleteBySerNo(Long.parseLong(checkItem[i]));
					addActionMessage(name + "刪除成功");
				} else {
					addActionMessage(name + "資源必須先刪除");
				}

				i++;
			}

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

	public String view() throws Exception {
		getRequest().setAttribute("viewSerNo",
				getRequest().getParameter("viewSerNo"));

		if (StringUtils.isNotBlank(getRequest().getParameter("viewSerNo"))
				&& NumberUtils.isDigits(getRequest().getParameter("viewSerNo"))) {

			customer = customerService.getBySerNo(Long.parseLong(getRequest()
					.getParameter("viewSerNo")));
			if (customer != null) {
				setEntity(customer);
			}
		}
		return VIEW;
	}

	public String ajax() throws Exception {
		getRequest().setAttribute("customerUnits",
				customerService.getAllCustomers());
		return AJAX;
	}

	public String json() throws Exception {
		List<Customer> customers = customerService.getAllCustomers();
		List<JSONObject> objArray = new ArrayList<JSONObject>();

		int i = 0;
		while (i < customers.size()) {
			JSONObject obj = new JSONObject();
			customer = customers.get(i);

			obj.put("name", customer.getName());
			obj.put("value", customer.getSerNo());
			objArray.add(obj);
			i++;
		}

		jsonString = new String[1];
		jsonString[0] = objArray.toString();
		return JSON;
	}

	public String imports() {
		return IMPORT;
	}

	public String queue() throws Exception {
		if (ArrayUtils.isEmpty(file) || !file[0].isFile()) {
			addActionError("請選擇檔案");
		} else {
			if (createWorkBook(new FileInputStream(file[0])) == null) {
				addActionError("檔案格式錯誤");
			}
		}

		if (!hasActionErrors()) {
			Workbook book = createWorkBook(new FileInputStream(file[0]));
			// book.getNumberOfSheets(); 判斷Excel文件有多少個sheet
			Sheet sheet = book.getSheetAt(0);

			Row firstRow = sheet.getRow(0);
			if (firstRow == null) {
				firstRow = sheet.createRow(0);
			}

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

			LinkedHashSet<Customer> originalData = new LinkedHashSet<Customer>();
			Map<String, Customer> checkRepeatRow = new LinkedHashMap<String, Customer>();
			int normal = 0;

			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}

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

				customer = new Customer(rowValues[0], rowValues[1],
						rowValues[2], rowValues[5], rowValues[4], rowValues[3],
						"", "");

				if (StringUtils.isBlank(customer.getName())) {
					customer.setExistStatus("名稱空白");
				} else {
					if (customer.getName()
							.replaceAll("[a-zA-Z0-9\u4e00-\u9fa5]", "")
							.length() != 0) {
						customer.setExistStatus("名稱字元異常");
					} else {
						long cusSerNo = customerService
								.getCusSerNoByName(customer.getName());
						if (cusSerNo != 0) {
							customer.setExistStatus("已存在");
						}

						if (StringUtils.isNotEmpty(customer.getEmail())) {

							String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
									+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
							if (!Pattern.compile(emailPattern)
									.matcher(customer.getEmail()).matches()) {
								customer.setEmail(null);
							}
						}

						if (StringUtils.isNotEmpty(customer.getTel())) {
							String tel = customer.getTel()
									.replaceAll("[/()+-]", "").replace(" ", "");
							if (!NumberUtils.isDigits(tel)) {
								customer.setTel(null);
							}
						}
					}

				}

				if (customer.getExistStatus().equals("")) {
					customer.setExistStatus("正常");
				}

				if (customer.getExistStatus().equals("正常")
						&& !originalData.contains(customer)) {

					if (checkRepeatRow.containsKey(customer.getName())) {
						customer.setExistStatus("名稱重複");

					} else {
						checkRepeatRow.put(customer.getName(), customer);

						++normal;
					}
				}

				originalData.add(customer);
			}

			List<Customer> excelData = new ArrayList<Customer>(originalData);

			DataSet<Customer> ds = initDataSet();
			List<Customer> results = ds.getResults();

			ds.getPager().setTotalRecord((long) excelData.size());
			ds.getPager().setRecordPoint(0);

			if (excelData.size() < ds.getPager().getRecordPerPage()) {
				int i = 0;
				while (i < excelData.size()) {
					results.add(excelData.get(i));
					i++;
				}
			} else {
				int i = 0;
				while (i < ds.getPager().getRecordPerPage()) {
					results.add(excelData.get(i));
					i++;
				}
			}

			ds.setResults(results);

			getSession().put("cellNames", cellNames);
			getSession().put("importList", excelData);
			getSession().put("total", excelData.size());
			getSession().put("normal", normal);

			setDs(ds);
			return QUEUE;
		} else {
			return IMPORT;
		}
	}

	@SuppressWarnings("unchecked")
	public String paginate() throws Exception {
		List<Customer> importList = (List<Customer>) getSession().get(
				"importList");
		if (importList == null) {
			return null;
		}

		clearCheckedItem();

		DataSet<Customer> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds.getPager().setTotalRecord((long) importList.size());

		int first = ds.getPager().getRecordPerPage()
				* (ds.getPager().getCurrentPage() - 1);
		int last = first + ds.getPager().getRecordPerPage();

		List<Customer> results = new ArrayList<Customer>();

		int i = 0;
		while (i < importList.size()) {
			if (i >= first && i < last) {
				results.add(importList.get(i));
			}
			i++;
		}

		ds.setResults(results);
		setDs(ds);
		return QUEUE;
	}

	@SuppressWarnings("unchecked")
	public String getCheckedItem() {
		List<Customer> importList = (List<Customer>) getSession().get(
				"importList");
		if (importList == null) {
			return null;
		}

		Set<Integer> checkItemSet;
		if (getSession().containsKey("checkItemSet")) {
			checkItemSet = (TreeSet<Integer>) getSession().get("checkItemSet");
		} else {
			checkItemSet = new TreeSet<Integer>();
		}

		if (ArrayUtils.isNotEmpty(importSerNos)) {
			if (NumberUtils.isDigits(importSerNos[0])) {
				if (!checkItemSet.contains(Integer.parseInt(importSerNos[0]))) {
					if (importList.get(Integer.parseInt(importSerNos[0]))
							.getExistStatus().equals("正常")) {
						checkItemSet.add(Integer.parseInt(importSerNos[0]));
					}
				} else {
					checkItemSet.remove(Integer.parseInt(importSerNos[0]));
				}
			}
		}

		getSession().put("checkItemSet", checkItemSet);

		return null;
	}

	@SuppressWarnings("unchecked")
	public String allCheckedItem() {
		List<Customer> importList = (List<Customer>) getSession().get(
				"importList");
		if (importList == null) {
			return null;
		}

		Set<Integer> checkItemSet = new TreeSet<Integer>();

		if (ArrayUtils.isNotEmpty(importSerNos)) {
			int i = 0;
			while (i < importSerNos.length) {
				if (NumberUtils.isDigits(importSerNos[i])) {
					if (Long.parseLong(importSerNos[i]) < importList.size()) {
						if (importList.get(Integer.parseInt(importSerNos[i]))
								.getExistStatus().equals("正常")) {
							checkItemSet.add(Integer.parseInt(importSerNos[i]));
						}
					}

					if (checkItemSet.size() == importList.size()) {
						break;
					}
				}
				i++;
			}
		}

		getSession().put("checkItemSet", checkItemSet);
		return null;
	}

	public String clearCheckedItem() {
		if (getSession().get("importList") == null) {
			return null;
		}

		Set<Integer> checkItemSet = new TreeSet<Integer>();
		getSession().put("checkItemSet", checkItemSet);
		return null;
	}

	@SuppressWarnings("unchecked")
	public String importData() throws Exception {
		List<Customer> importList = (List<Customer>) getSession().get(
				"importList");

		if (importList == null) {
			return null;
		}

		Set<Integer> checkItemSet = (TreeSet<Integer>) getSession().get(
				"checkItemSet");

		if (CollectionUtils.isEmpty(checkItemSet)) {
			addActionError("請選擇一筆或一筆以上的資料");
		}

		if (!hasActionErrors()) {
			Iterator<Integer> iterator = checkItemSet.iterator();
			int successCount = 0;
			while (iterator.hasNext()) {
				int index = iterator.next();
				customer = importList.get(index);
				customerService.save(customer, getLoginUser());
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
		reportFile = "customer_sample.xlsx";

		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("customer");
		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		Map<String, Object[]> empinfo = new LinkedHashMap<String, Object[]>();
		empinfo.put("1", new Object[] { "name/姓名", "egName/英文姓名", "address/地址",
				"tel/電話", "contactUserName/聯絡人", "emai/l電子信箱", "memo/備註" });

		empinfo.put("2", new Object[] { "國防醫學中心", "ndmc", "台北市內湖區民權東路六段161號",
				"886-2-87923100", "總機", "ndmc@ndmc.gmail.com", "" });

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
		setInputStream(new ByteArrayInputStream(boas.toByteArray()));

		return XLSX;
	}

	// 判斷文件類型
	public Workbook createWorkBook(InputStream is) throws IOException {
		if (fileFileName[0].toLowerCase().endsWith("xls")) {
			return new HSSFWorkbook(is);
		}
		if (fileFileName[0].toLowerCase().endsWith("xlsx")) {
			return new XSSFWorkbook(is);
		}
		return null;
	}

	public boolean hasEntity() throws Exception {
		if (getEntity().getSerNo() == null) {
			getEntity().setSerNo(-1L);
			return false;
		}

		customer = customerService.getBySerNo(getEntity().getSerNo());
		if (customer == null) {
			return false;
		}

		return true;
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
	public File[] getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File[] file) {
		this.file = file;
	}

	/**
	 * @return the fileFileName
	 */
	public String[] getFileFileName() {
		return fileFileName;
	}

	/**
	 * @param fileFileName
	 *            the fileFileName to set
	 */
	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * @return the fileContentType
	 */
	public String[] getFileContentType() {
		return fileContentType;
	}

	/**
	 * @param fileContentType
	 *            the fileContentType to set
	 */
	public void setFileContentType(String[] fileContentType) {
		this.fileContentType = fileContentType;
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

	/**
	 * @return the reportFile
	 */
	public String getReportFile() {
		if (reportFile.equals("customer_sample.xlsx")) {
			return reportFile;
		} else {
			return null;
		}
	}

	/**
	 * @param reportFile
	 *            the reportFile to set
	 */
	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	/**
	 * @return the jsonString
	 */
	public String[] getJsonString() {
		return jsonString;
	}

	/**
	 * @param jsonString
	 *            the jsonString to set
	 */
	public void setJsonString(String[] jsonString) {
		this.jsonString = jsonString;
	}

}