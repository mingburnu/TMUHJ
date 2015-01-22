package com.asiaworld.tmuhj.module.apply.beLogs.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

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
import com.asiaworld.tmuhj.core.web.GenericCRUDActioncDTime;
import com.asiaworld.tmuhj.module.apply.beLogs.entity.BeLogs;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.customer.service.CustomerService;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class BeLogsAction extends GenericCRUDActioncDTime<BeLogs> {

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

		List<BeLogs> ranks = new ArrayList<BeLogs>();
		List<BeLogs> results = new ArrayList<BeLogs>();

		DataSet<BeLogs> ds = initDataSet();

		long cusSerNo = customerService.getCusSerNoByName(customerName);
		getEntity().setCusSerNo(cusSerNo);

		if (startDate != null && !startDate.isEmpty()) {
			getEntity().setStart(LocalDateTime.parse(startDate));
		}

		if (endDate != null && !endDate.isEmpty()) {
			getEntity().setEnd(LocalDateTime.parse(endDate));
		}

		ranks = beLogsService.getRanks(ds);

		int i = 0;
		while (i < ranks.size()) {
			accountNumber = accountNumberService.getBySerNo(ranks.get(i)
					.getUserSerNo());
			customer = customerService.getBySerNo(ranks.get(i).getCusSerNo());

			ranks.get(i).setAccountNumber(accountNumber);
			ranks.get(i).setCustomer(customer);

			i++;
		}

		if (customerName != null && !customerName.isEmpty()) {
			int k = 0;
			while (k < ranks.size()) {
				customer = ranks.get(k).getCustomer();
				if (customer.getName().contains(customerName)) {

				} else {
					ranks.remove(k);
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

		getRequest().setAttribute("start", startDate);
		getRequest().setAttribute("end", endDate);
		getSession().put("ranks", ranks);

		ds.setResults(results);
		setDs(ds);

		return LIST;
	}

	@SuppressWarnings("unchecked")
	public String pagination() throws Exception {
		String startDate = getRequest().getParameter("start");
		String endDate = getRequest().getParameter("end");
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
		getRequest().setAttribute("start", startDate);
		getRequest().setAttribute("end", endDate);
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
		return LIST;
	}

	@SuppressWarnings("unchecked")
	public void exports() throws Exception {
		String startDate = getRequest().getParameter("start");
		String endDate = getRequest().getParameter("end");

		getSession().get("ranks");
		List<BeLogs> ranks = (List<BeLogs>) getSession().get("ranks");

		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("login statics");
		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		Map<String, Object[]> empinfo = new TreeMap<String, Object[]>();
		empinfo.put("1", new Object[] { "年月", "名次", "帳號", "用戶姓名", "用戶身分",
				"客戶名稱", "狀態", "次數" });

		int i = 0;
		while (i < ranks.size()) {
			empinfo.put(
					String.valueOf(i + 1),
					new Object[] {
							startDate + "~" + endDate,
							String.valueOf(ranks.get(i).getRank()),
							ranks.get(i).getAccountNumber().getUserId(),
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
		// Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File("c:\\login statics.xlsx"));
		workbook.write(out);
		out.close();
		System.out.println("Writesheet.xlsx written successfully");
	}
}
