package com.asiaworld.tmuhj.module.apply.feLogs.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;
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
import com.asiaworld.tmuhj.core.web.GenericCRUDActionLog;
import com.asiaworld.tmuhj.module.apply.customer.service.CustomerService;
import com.asiaworld.tmuhj.module.apply.feLogs.entity.FeLogs;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FeLogsAction extends GenericCRUDActionLog<FeLogs> {

	@Autowired
	private FeLogsService feLogsService;

	@Autowired
	private FeLogs feLogs;

	@Autowired
	private CustomerService customerService;

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
		String cusSerNo = getRequest().getParameter("cusSerNo");

		if (cusSerNo == null || !NumberUtils.isDigits(cusSerNo)) {
			addActionError("請正確填寫機構名稱");
		}

		if (!hasActionErrors()) {
			if (startDate != null && !startDate.isEmpty() && isDate(startDate)) {
				getEntity().setStart(LocalDateTime.parse(startDate));
				getRequest().setAttribute("startDate", startDate);
			} else {
				getEntity().setStart(LocalDateTime.parse("2015-01-01"));
				getRequest().setAttribute("startDate", "2015-01-01");
			}

			if (endDate != null && !endDate.isEmpty() && isDate(endDate)) {
				getEntity().setEnd(LocalDateTime.parse(endDate));
			}

			DataSet<FeLogs> ds = initDataSet();

			getEntity().setCusSerNo(Long.parseLong(cusSerNo));

			ds.setPager(Pager.getChangedPager(
					getRequest().getParameter("recordPerPage"), getRequest()
							.getParameter("recordPoint"), ds.getPager()));

			ds = feLogsService.getRanks(ds);

			getRequest().setAttribute("endDate", endDate);

			if (Long.parseLong(cusSerNo) > 0) {
				getRequest().setAttribute("customer",
						customerService.getBySerNo(Long.parseLong(cusSerNo)).getName());
			}
			getRequest().setAttribute("cusSerNo", cusSerNo);

			ds = feLogsService.getRanks(ds);

			setDs(ds);

			return LIST;
		} else {
			getRequest().setAttribute("startDate", startDate);
			getRequest().setAttribute("endDate", endDate);
			getRequest().setAttribute("customer", customerName);
			getRequest().setAttribute("cusSerNo", cusSerNo);
			return LIST;
		}
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

	public String exports() throws Exception {
		String startDate = getRequest().getParameter("start");
		String endDate = getRequest().getParameter("end");
		String customerName = getRequest().getParameter("customer");
		String cusSerNo = getRequest().getParameter("cusSerNo");

		if (cusSerNo == null || !NumberUtils.isDigits(cusSerNo)
				|| Long.parseLong(cusSerNo) == 0) {
			addActionError("請正確填寫機構名稱");
		}

		if (!hasActionErrors()) {
			if (startDate != null && !startDate.isEmpty() && isDate(startDate)) {
				getEntity().setStart(LocalDateTime.parse(startDate));
			} else {
				getEntity().setStart(LocalDateTime.parse("2015-01-01"));
			}

			if (endDate != null && !endDate.isEmpty() && isDate(endDate)) {
				getEntity().setEnd(LocalDateTime.parse(endDate));
			}

			DataSet<FeLogs> ds = initDataSet();

			getEntity().setCusSerNo(Long.parseLong(cusSerNo));

			Pager pager = ds.getPager();
			pager.setRecordPerPage(Integer.MAX_VALUE);
			ds.setPager(pager);

			ds = feLogsService.getRanks(ds);

			List<FeLogs> ranks = ds.getResults();

			reportFile = "feLogs.xlsx";

			// Create blank workbook
			XSSFWorkbook workbook = new XSSFWorkbook();
			// Create a blank sheet
			XSSFSheet spreadsheet = workbook.createSheet("keyword statics");
			// Create row object
			XSSFRow row;
			// This data needs to be written (Object[])
			Map<String, Object[]> empinfo = new LinkedHashMap<String, Object[]>();
			empinfo.put("1", new Object[] { "年月", "名次", "關鍵字", "次數" });

			int i = 0;
			while (i < ranks.size()) {
				empinfo.put(String.valueOf(i + 2),
						new Object[] { startDate + "~" + endDate,
								String.valueOf(ranks.get(i).getRank()),
								ranks.get(i).getKeyword(),
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
		} else {
			getRequest().setAttribute("startDate", startDate);
			getRequest().setAttribute("endDate", endDate);
			getRequest().setAttribute("customer", customerName);
			getRequest().setAttribute("cusSerNo", cusSerNo);
			return LIST;
		}
	}

	public boolean isDate(String date) {
		Pattern pattern = Pattern.compile("((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])");
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
