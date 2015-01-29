package com.asiaworld.tmuhj.module.apply.beLogs.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.security.accountNumber.entity.AccountNumber;
import com.asiaworld.tmuhj.core.security.accountNumber.service.AccountNumberService;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionLog;
import com.asiaworld.tmuhj.module.apply.beLogs.entity.BeLogs;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.customer.service.CustomerService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BeLogsAction extends GenericCRUDActionLog<BeLogs> {

	@Autowired
	private BeLogsService beLogsService;

	@Autowired
	private BeLogs beLogs;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private Customer customer;

	@Autowired
	private AccountNumberService accountNumberService;

	@Autowired
	private AccountNumber accountNumber;

	private InputStream inputStream;

	private String reportFile;

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list() throws Exception {
		String startDate = getRequest().getParameter("start");
		String endDate = getRequest().getParameter("end");
		String customerName = getRequest().getParameter("customer");

		List<BeLogs> results = new ArrayList<BeLogs>();
		DataSet<BeLogs> ds = initDataSet();

		long cusSerNo = customerService.getCusSerNoByName(customerName);
		getEntity().setCusSerNo(cusSerNo);

		if (startDate != null && !startDate.isEmpty() && isDate(startDate)) {
			getEntity().setStart(LocalDateTime.parse(startDate));
			getSession().put("startDate", startDate);
		} else {
			getEntity().setStart(LocalDateTime.parse("2015-01-01"));
			getSession().put("startDate", "2015-01-01");
		}

		if (endDate != null && !endDate.isEmpty() && isDate(endDate)) {
			getEntity().setEnd(LocalDateTime.parse(endDate));
			getSession().put("endDate", endDate);
		} else {
			getSession().put("endDate", "");
		}

		List<BeLogs> ranks = beLogsService.getRanks(ds);
		
		int i = 0;
		while (i < ranks.size()) {
			accountNumber = accountNumberService.getBySerNo(ranks.get(i)
					.getUserSerNo());
			customer = customerService.getBySerNo(ranks.get(i).getCusSerNo());

			ranks.get(i).setAccountNumber(accountNumber);
			ranks.get(i).setCustomer(customer);

			i++;
		}

		if (customerName != null && !customerName.trim().isEmpty()) {
		List<BeLogs> temp=ranks;
		ranks=new ArrayList<BeLogs>();
			int k = 0;
			while (k < temp.size()) {
				customer = temp.get(k).getCustomer();
				if (customer.getName().contains(customerName.trim())) {
					ranks.add(temp.get(k));
				} 
				k++;
			}
		}
		ds.getPager().setTotalRecord((long) ranks.size());
		ds.getPager().setRecordPoint(0);

		if (ranks.size() < ds.getPager().getRecordPerPage()) {
			int j = 0;
			while (j < ranks.size()) {
				results.add(ranks.get(j));
				j++;
			}
		} else {
			int j = 0;
			while (j < ds.getPager().getRecordPerPage()) {
				results.add(ranks.get(j));
				j++;
			}
		}

		getSession().put("ranks", ranks);

		ds.setResults(results);
		setDs(ds);

		return LIST;
	}

	@SuppressWarnings("unchecked")
	public String paginate() throws Exception {
		List<BeLogs> ranks = (List<BeLogs>) getSession().get("ranks");

		DataSet<BeLogs> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));

		ds.getPager().setTotalRecord((long) ranks.size());
		int first = ds.getPager().getRecordPerPage()
				* (ds.getPager().getCurrentPage() - 1);
		int last = first + ds.getPager().getRecordPerPage();

		List<BeLogs> results = new ArrayList<BeLogs>();

		int i = 0;
		while (i < ranks.size()) {
			if (i >= first && i < last) {
				results.add(ranks.get(i));
			}
			i++;
		}

		ds.setResults(results);
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

	public String direct() {
		getSession().remove("ranks");
		getSession().remove("startDate");
		getSession().remove("endDate");
		return LIST;
	}

	@SuppressWarnings("unchecked")
	public String exports() throws Exception {
		String startDate = (String) getSession().get("startDate");
		String endDate = (String) getSession().get("endDate");

		getSession().get("ranks");
		List<BeLogs> ranks = (List<BeLogs>) getSession().get("ranks");

		reportFile = "beLogs.xlsx";

		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("login statics");
		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		Map<String, Object[]> empinfo = new LinkedHashMap<String, Object[]>();
		empinfo.put("1", new Object[] { "年月", "名次", "帳號", "用戶姓名", "用戶身分",
				"客戶名稱", "狀態", "次數" });

		int i = 0;
		while (i < ranks.size()) {
			empinfo.put(
					String.valueOf(i + 2),
					new Object[] {
							startDate + "~" + endDate,
							String.valueOf(ranks.get(i).getRank()),
							ranks.get(i).getAccountNumber().getUserId(),
							ranks.get(i).getAccountNumber().getUserName(),
							ranks.get(i).getAccountNumber().getRole().getRole(),
							ranks.get(i).getCustomer().getName(),
							ranks.get(i).getAccountNumber().getStatus()
									.getStatus(),
							String.valueOf(ranks.get(i).getCount()) });

			i++;
		}

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

		return SUCCESS;
	}

	public boolean isDate(String date) {
		String regex = "((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(date);
		return matcher.matches();
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
		return reportFile;
	}

	/**
	 * @param reportFile
	 *            the reportFile to set
	 */
	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}
}
