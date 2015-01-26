package com.asiaworld.tmuhj.module.apply.feLogs.service;

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
import com.asiaworld.tmuhj.core.web.GenericCRUDActionLog;
import com.asiaworld.tmuhj.module.apply.feLogs.entity.FeLogs;

@Controller
@SuppressWarnings("serial")
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FeLogsAction extends GenericCRUDActionLog<FeLogs> {

	@Autowired
	private FeLogsService feLogsService;

	@Autowired
	private FeLogs feLogs;

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

		List<FeLogs> results = new ArrayList<FeLogs>();
		DataSet<FeLogs> ds = initDataSet();

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

		List<FeLogs> ranks = feLogsService.getRanks(ds);
		
		if (getEntity().getKeyword() != null && !getEntity().getKeyword().trim().isEmpty()) {
		List<FeLogs> temp=ranks;
		ranks=new ArrayList<FeLogs>();

			int k = 0;
			while (k < temp.size()) {
				String keyword = temp.get(k).getKeyword();
				if (keyword.contains(getEntity().getKeyword().trim())) {
				ranks.add(temp.get(k));
				} 
				k++;
			}
		}
		
		log.debug("ranks size"+ranks.size());
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
		List<FeLogs> ranks = (List<FeLogs>) getSession().get("ranks");

		DataSet<FeLogs> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));

		ds.getPager().setTotalRecord((long) ranks.size());
		int first = ds.getPager().getRecordPerPage()
				* (ds.getPager().getCurrentPage() - 1);
		int last = first + ds.getPager().getRecordPerPage();

		List<FeLogs> results = new ArrayList<FeLogs>();

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
		List<FeLogs> ranks = (List<FeLogs>) getSession().get("ranks");

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
			empinfo.put(
					String.valueOf(i + 2),
					new Object[] {
							startDate + "~" + endDate,
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
