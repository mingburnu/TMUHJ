package com.asiaworld.tmuhj.module.apply.journal;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.ExcelWorkSheet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.web.GenericCRUDActionFull;
import com.asiaworld.tmuhj.module.apply.enums.Category;
import com.asiaworld.tmuhj.module.apply.enums.Type;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyers;
import com.asiaworld.tmuhj.module.apply.resourcesBuyers.ResourcesBuyersService;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

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

	private ExcelWorkSheet<Journal> excelWorkSheet;

	private String importSerNo;

	private String[] importSerNos;

	private InputStream inputStream;

	private String reportFile;
	
	private List<String> actionErrors;

	@Override
	public void validateSave() throws Exception {
		actionErrors = new ArrayList<String>();
		
		List<Category> categoryList=new ArrayList<Category>(Arrays.asList(Category.values()));
		categoryList.remove(categoryList.size()-1);
				
		List<Type> typeList=new ArrayList<Type>(Arrays.asList(Type.values()));
						
		if (StringUtils.isBlank(getEntity().getEnglishTitle())) {
			actionErrors.add("英文刊名不得空白");
		}

		if (StringUtils.isBlank(getEntity().getIssn())) {
			actionErrors.add("ISSN不得空白");
		} else {
			if (!isIssn(getEntity().getIssn())) {
				actionErrors.add("ISSN不正確");
			} else {
				if (journalService.getJouSerNoByIssn(getEntity().getIssn()) != 0 ){
					actionErrors.add("ISSN不可重複");
				}
			}
		}

		if (cusSerNo == null || cusSerNo.length == 0) {
			actionErrors.add("至少選擇一筆以上購買單位");
		} else {
			int i = 0;
			while (i < cusSerNo.length) {
				if (!NumberUtils.isDigits(String.valueOf(cusSerNo[i]))
						|| Long.parseLong(cusSerNo[i]) < 1
						|| customerService.getBySerNo(Long.parseLong(cusSerNo[i])) == null) {
					actionErrors.add(cusSerNo[i] + "為不可利用的流水號");
				}
				i++;
			}
		}
		
		boolean isLegalCategory=false;
		for (int i=0; i < categoryList.size(); i++){
			if(getRequest().getParameter("rCategory") != null
					&& getRequest().getParameter("rCategory").equals(categoryList.get(i).getCategory())){
				isLegalCategory=true;
			}
		}
		
		if(isLegalCategory){
			getRequest().setAttribute("rType", getRequest().getParameter("rCategory"));
		} else{
			actionErrors.add("資源類型錯誤");
		}
		
		boolean isLegalType=false;
		for (int i=0; i < categoryList.size(); i++){
			if(getRequest().getParameter("rType") != null
					&& getRequest().getParameter("rType").equals(typeList.get(i).getType())){
				isLegalType=true;
			}
		}
		
		if(isLegalType){
			getRequest().setAttribute("rType", getRequest().getParameter("rType"));
		} else {
			actionErrors.add("資源種類錯誤");
		}
	}

	@Override
	public void validateUpdate() throws Exception {
		actionErrors = new ArrayList<String>();
		
		List<Category> categoryList=new ArrayList<Category>(Arrays.asList(Category.values()));
		categoryList.remove(categoryList.size()-1);
				
		List<Type> typeList=new ArrayList<Type>(Arrays.asList(Type.values()));
						
		if (StringUtils.isBlank(getEntity().getEnglishTitle())) {
			actionErrors.add("英文刊名不得空白");
		}

		if (StringUtils.isBlank(getEntity().getIssn())) {
			actionErrors.add("ISSN不得空白");
		} else {
			if (!isIssn(getEntity().getIssn())) {
				actionErrors.add("ISSN不正確");
			} else {
				if (journalService.getJouSerNoByIssn(getEntity().getIssn()) != 0 ){
					actionErrors.add("ISSN不可重複");
				}
			}
		}

		if (cusSerNo == null || cusSerNo.length == 0) {
			actionErrors.add("至少選擇一筆以上購買單位");
		} else {
			int i = 0;
			while (i < cusSerNo.length) {
				if (!NumberUtils.isDigits(String.valueOf(cusSerNo[i]))
						|| Long.parseLong(cusSerNo[i]) < 1
						|| customerService.getBySerNo(Long.parseLong(cusSerNo[i])) == null) {
					actionErrors.add(cusSerNo[i] + "為不可利用的流水號");
				}
				i++;
			}
		}
		
		boolean isLegalCategory=false;
		for (int i=0; i < categoryList.size(); i++){
			if(getRequest().getParameter("rCategory") != null
					&& getRequest().getParameter("rCategory").equals(categoryList.get(i).getCategory())){
				isLegalCategory=true;
			}
		}
		
		if(isLegalCategory){
			getRequest().setAttribute("rType", getRequest().getParameter("rCategory"));
		} else{
			actionErrors.add("資源類型錯誤");
		}
		
		boolean isLegalType=false;
		for (int i=0; i < categoryList.size(); i++){
			if(getRequest().getParameter("rType") != null
					&& getRequest().getParameter("rType").equals(typeList.get(i).getType())){
				isLegalType=true;
			}
		}
		
		if(isLegalType){
			getRequest().setAttribute("rType", getRequest().getParameter("rType"));
		} else {
			actionErrors.add("資源種類錯誤");
		}
	}

	@Override
	public void validateDelete() throws Exception {
		actionErrors = new ArrayList<String>();
		
		if (checkItem == null || checkItem.length == 0) {
			actionErrors.add("請選擇一筆或一筆以上的資料");
		} else {
			int i = 0;
			while (i < checkItem.length) {
				if (!NumberUtils.isDigits(String.valueOf(checkItem[i]))
						|| Long.parseLong(checkItem[i]) < 1) {
					actionErrors.add(checkItem[i] + "為不可利用的流水號");
				}
				i++;
			}
		}
	}

	@Override
	public String query() throws Exception {
		List<Category> categoryList=new ArrayList<Category>(Arrays.asList(Category.values()));
		categoryList.remove(categoryList.size()-1);
		getRequest().setAttribute("categoryList", categoryList);
		
		List<Type> typeList=new ArrayList<Type>(Arrays.asList(Type.values()));
		getRequest().setAttribute("typeList", typeList);
		
		getRequest().setAttribute("allCustomers",
				customerService.getAllCustomers());
		if (getEntity().getSerNo() != null) {
			journal = journalService.getBySerNo(getEntity().getSerNo());
			
			if (journal != null){
			Iterator<ResourcesUnion> iterator = resourcesUnionService
					.getResourcesUnionsByObj(getEntity(), Journal.class)
					.iterator();

			List<Customer> customers = new ArrayList<Customer>();

			while (iterator.hasNext()) {
				resourcesUnion = iterator.next();
				customer = resourcesUnion.getCustomer();
				if (customer != null){
				customers.add(customer);
				}
			}

			resourcesBuyers = resourcesUnion.getResourcesBuyers();
			getRequest().setAttribute("rCategory", resourcesBuyers.getrCategory().getCategory());
			getRequest().setAttribute("rType", resourcesBuyers.getrType().getType());
			journal.setCustomers(customers);
			
			}
			setEntity(journal);
			
		} else if (getRequest().getParameter("goQueue") != null
				&& getRequest().getParameter("goQueue").equals("yes")) {
			getRequest().setAttribute("goQueue",
					getRequest().getParameter("goQueue"));
		} else {
			List<Customer> customers = new ArrayList<Customer>();
			journal.setCustomers(customers);
			setEntity(journal);
		}
		return EDIT;
	}

	@Override
	public String list() throws Exception {
		if (StringUtils.isNotEmpty(getRequest().getParameter("option"))) {
			if (getRequest().getParameter("option").equals("entity.chineseTitle") ||
					getRequest().getParameter("option").equals("entity.englishTitle") ||
					getRequest().getParameter("option").equals("entity.issn")) {
				getRequest().setAttribute("option", getRequest().getParameter("option"));
				
			} else {
					getRequest().setAttribute("option", "entity.chineseTitle");
			}
			
		} else {
				getRequest().setAttribute("option", "entity.chineseTitle");
				
		}

		DataSet<Journal> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));
		ds = journalService.getByRestrictions(ds);

		List<Journal> results = ds.getResults();

		int i = 0;
		while (i < results.size()) {
			results.get(i).setResourcesBuyers(
					resourcesUnionService.getByObjSerNo(results.get(i).getSerNo(),
							Journal.class).getResourcesBuyers());
			i++;
		}

		ds.setResults(results);
		setDs(ds);
		return LIST;
	}

	@Override
	public String save() throws Exception {
		List<Category> categoryList=new ArrayList<Category>(Arrays.asList(Category.values()));
		categoryList.remove(categoryList.size()-1);
				
		List<Type> typeList=new ArrayList<Type>(Arrays.asList(Type.values()));
		
		validateSave();
		Iterator<String> iteratorMsg = actionErrors.iterator();
		while(iteratorMsg.hasNext()){
			addActionError(iteratorMsg.next());
		}
		
		if (!hasActionErrors()) {
			journal = getEntity();
			journal.setIssn(getEntity().getIssn().toUpperCase());
			journal = journalService.save(journal, getLoginUser());

			resourcesBuyers = resourcesBuyersService.save(new ResourcesBuyers(getRequest().getParameter(
							"resourcesBuyers.startDate"), getRequest()
							.getParameter("resourcesBuyers.maturityDate"),
							Category.valueOf(getRequest().getParameter(
									"rCategory")), Type
									.valueOf(getRequest().getParameter(
											"rType")),
							getRequest().getParameter(
									"resourcesBuyers.dbChtTitle"), getRequest()
									.getParameter("resourcesBuyers.dbEngTitle")),
							getLoginUser());

			int i = 0;
			while (i < cusSerNo.length) {
				resourcesUnionService.save(new ResourcesUnion(customerService.getBySerNo(Long.parseLong(cusSerNo[i])),
								resourcesBuyers, 0L, 0L, journal
										.getSerNo()), getLoginUser());

				i++;
			}

			resourcesUnion = resourcesUnionService.getByObjSerNo(
					journal.getSerNo(), Journal.class);
			journal.setResourcesBuyers(resourcesUnion.getResourcesBuyers());

			List<ResourcesUnion> resourceUnions = resourcesUnionService
					.getResourcesUnionsByObj(journal, Journal.class);
			List<Customer> customers = new ArrayList<Customer>();

			Iterator<ResourcesUnion> iterator = resourceUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = iterator.next();
				customers.add(resourcesUnion.getCustomer());
			}

			journal.setCustomers(customers);
			setEntity(journal);
			addActionMessage("新增成功");
			return VIEW;
		} else {
			getRequest().setAttribute("categoryList", categoryList);
			getRequest().setAttribute("typeList", typeList);
			getRequest().setAttribute("allCustomers",
					customerService.getAllCustomers());

			List<Customer> customers = new ArrayList<Customer>();
			if (cusSerNo != null && cusSerNo.length != 0) {
				int i = 0;
				while (i < cusSerNo.length) {
					if (cusSerNo[i] != null 
							&& NumberUtils.isDigits(cusSerNo[i])){
						if(customerService.getBySerNo(Long.parseLong(cusSerNo[i])) != null){
							customers.add(customerService.getBySerNo(Long
									.parseLong(cusSerNo[i])));
						}
					}
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
		List<Category> categoryList=new ArrayList<Category>(Arrays.asList(Category.values()));
		categoryList.remove(categoryList.size()-1);
				
		List<Type> typeList=new ArrayList<Type>(Arrays.asList(Type.values()));
		
		boolean isLegalCategory=false;
		for (int i=0; i < categoryList.size(); i++){
			if(getRequest().getParameter("rCategory") != null
					&& getRequest().getParameter("rCategory").equals(categoryList.get(i).getCategory())){
				isLegalCategory=true;
			}
		}
		
		if(isLegalCategory){
			getRequest().setAttribute("rCategory", getRequest().getParameter("rCategory"));
		}
		
		boolean isLegalType=false;
		for (int i=0; i < categoryList.size(); i++){
			if(getRequest().getParameter("rType") != null
					&& getRequest().getParameter("rType").equals(typeList.get(i).getType())){
				isLegalType=true;
			}
		}
		
		if(isLegalType){
			getRequest().setAttribute("rType", getRequest().getParameter("rType"));
		}
		
		validateSave();
		Iterator<String> iteratorMsg = actionErrors.iterator();
		while(iteratorMsg.hasNext()){
			addActionError(iteratorMsg.next());
		}

		if (!hasActionErrors()) {
			journal = getEntity();
			journal.setIssn(getEntity().getIssn().toUpperCase());
			journal = journalService.update(journal, getLoginUser());

			resourcesBuyers = resourcesUnionService.getByObjSerNo(
							journal.getSerNo(), Journal.class).getResourcesBuyers();
			resourcesBuyers.setStartDate(getRequest().getParameter(
					"resourcesBuyers.startDate"));
			resourcesBuyers.setMaturityDate(getRequest().getParameter(
					"resourcesBuyers.maturityDate"));
			resourcesBuyers.setrCategory(Category.valueOf(getRequest()
					.getParameter("rCategory")));
			resourcesBuyers.setrType(Type.valueOf(getRequest().getParameter(
					"rType")));
			resourcesBuyers.setDbChtTitle(getRequest().getParameter(
					"resourcesBuyers.dbChtTitle"));
			resourcesBuyers.setDbEngTitle(getRequest().getParameter(
					"resourcesBuyers.dbEngTitle"));
			resourcesBuyersService.update(resourcesBuyers, getLoginUser());

			List<ResourcesUnion> resourcesUnions = resourcesUnionService
					.getResourcesUnionsByObj(journal, Journal.class);

			for (int j = 0; j < cusSerNo.length; j++) {
				for (int i = 0; i < resourcesUnions.size(); i++) {
					resourcesUnion = resourcesUnions.get(i);
					if (resourcesUnion.getCustomer().getSerNo() == Long
							.parseLong(cusSerNo[j])) {
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
			while (i < cusSerNo.length) {
				if (!resourcesUnionService.isExist(journal, Journal.class,
						Long.parseLong(cusSerNo[i]))) {
					resourcesUnionService.save(new ResourcesUnion(customerService.getBySerNo(Long.parseLong(cusSerNo[i])),
									resourcesBuyers, 0L, 0L, journal
											.getSerNo()), getLoginUser());
				}

				i++;
			}

			resourcesUnion = resourcesUnionService.getByObjSerNo(
					journal.getSerNo(), Journal.class);
			journal.setResourcesBuyers(resourcesUnion.getResourcesBuyers());

			List<ResourcesUnion> resourceUnions = resourcesUnionService
					.getResourcesUnionsByObj(journal, Journal.class);
			List<Customer> customers = new ArrayList<Customer>();

			iterator = resourceUnions.iterator();
			while (iterator.hasNext()) {
				resourcesUnion = iterator.next();
				customers.add(resourcesUnion.getCustomer());
			}

			journal.setCustomers(customers);
			setEntity(journal);
			addActionMessage("修改成功");
			return VIEW;
		} else {
			getRequest().setAttribute("typeList", typeList);
			getRequest().setAttribute("categoryList", categoryList);
			getRequest().setAttribute("allCustomers",
					customerService.getAllCustomers());

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
		validateDelete();
		Iterator<String> iteratorMsg = actionErrors.iterator();
		while(iteratorMsg.hasNext()){
			addActionError(iteratorMsg.next());
		}

		if (!hasActionErrors()) {
			int j = 0;
			while (j < checkItem.length) {
				if (journalService.getBySerNo(Long.parseLong(checkItem[j])) != null) {
					List<ResourcesUnion> resourcesUnions = resourcesUnionService
							.getResourcesUnionsByObj(journalService
									.getBySerNo(Long.parseLong(checkItem[j])),
									Journal.class);
					resourcesUnion = resourcesUnions.get(0);

					Iterator<ResourcesUnion> iterator = resourcesUnions.iterator();
					while (iterator.hasNext()) {
						resourcesUnion = iterator.next();
						resourcesUnionService.deleteBySerNo(resourcesUnion
								.getSerNo());
					}
					resourcesBuyersService.deleteBySerNo(resourcesUnion.getResourcesBuyers().getSerNo());
					journalService.deleteBySerNo(Long.parseLong(checkItem[j]));
				}
				j++;
			}

			DataSet<Journal> ds = journalService.getByRestrictions(initDataSet());
			List<Journal> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setResourcesBuyers(
						resourcesUnionService.getByObjSerNo(results.get(i).getSerNo(),
								Journal.class).getResourcesBuyers());
				i++;
			}

			setDs(ds);
			addActionMessage("刪除成功");
			return LIST;
		} else {
			DataSet<Journal> ds = journalService.getByRestrictions(initDataSet());
			List<Journal> results = ds.getResults();

			int i = 0;
			while (i < results.size()) {
				results.get(i).setResourcesBuyers(
						resourcesUnionService.getByObjSerNo(results.get(i).getSerNo(),
								Journal.class).getResourcesBuyers());
				i++;
			}

			setDs(ds);
			return LIST;
		}
	}

	public String view() throws NumberFormatException, Exception {
		getRequest().setAttribute("viewSerNo", getRequest().getParameter("viewSerNo"));
		
		if (getRequest().getParameter("viewSerNo") == null 
				|| !NumberUtils.isDigits(getRequest().getParameter("viewSerNo"))){
			addActionError("流水號不正確");
		} else {
			journal = journalService.getBySerNo(Long.parseLong(getRequest()
					.getParameter("viewSerNo")));
			if (journal == null){
				addActionError("期刊不存在");
				} else {
					resourcesUnion = resourcesUnionService.getByObjSerNo(
							journal.getSerNo(), Journal.class);
					}
			}
		
		if(!hasActionErrors()){
		
		journal.setResourcesBuyers(resourcesUnion.getResourcesBuyers());

		List<ResourcesUnion> resourceUnions = resourcesUnionService.getResourcesUnionsByObj(
				journal, Journal.class);
		List<Customer> customers = new ArrayList<Customer>();

		Iterator<ResourcesUnion> iterator = resourceUnions.iterator();
		while (iterator.hasNext()) {
			resourcesUnion = iterator.next();
			customers.add(resourcesUnion.getCustomer());
		}
		
		journal.setCustomers(customers);
		setEntity(journal);
		}
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
			excelWorkSheet = new ExcelWorkSheet<Journal>();

			// 保存工作單名稱
			Row firstRow = sheet.getRow(0);

			// 保存列名
			List<String> cellNames = new ArrayList<String>();
			String[] rowTitles = new String[17];
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

			LinkedHashSet<Journal> originalData = new LinkedHashSet<Journal>();
			for (int i = 1; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);

				String[] rowValues = new String[17];
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

				List<Category> categoryList=new ArrayList<Category>(Arrays.asList(Category.values()));
				categoryList.remove(categoryList.size()-1);
				
				String category = "";
				if(rowValues[11] == null ||
						rowValues[11].trim().equals("")){
					category = Category.未註明.getCategory();
				} else {
					boolean isLegalCategory=false;
					for (int j=0; j < categoryList.size(); j++){
						if(rowValues[11].trim().equals(categoryList.get(j).getCategory())){
							category = categoryList.get(j).getCategory();
							isLegalCategory=true;
						}
					}
					
					if (!isLegalCategory){
						category = Category.不明.getCategory();
					}	
				}
				
				List<Type> typeList=new ArrayList<Type>(Arrays.asList(Type.values()));
				String type = "";
				if(rowValues[12] == null ||
						rowValues[12].trim().equals("")){
					type = Type.期刊.getType();
				} else {
					boolean isLegalType=false;
					for (int j=0; j < typeList.size(); j++){
						if(rowValues[12].trim().equals(typeList.get(j).getType())){
							type = typeList.get(j).getType();
							isLegalType=true;
						}
					}
					
					if (!isLegalType){
						type = Type.期刊.getType();
					}	
				}				

				resourcesBuyers = new ResourcesBuyers(rowValues[9],
						rowValues[10], Category.valueOf(category), Type.valueOf(type),
						rowValues[13], rowValues[14]);

				String issn = rowValues[3].trim().toUpperCase();
				String[] issnSplit = issn.split("-");

				StringBuilder stringBuilder= new StringBuilder("");
				int j = 0;
				while (j < issnSplit.length) {
					stringBuilder.append(issnSplit[j]);
					j++;
				}
				
				issn = stringBuilder.toString();

				journal = new Journal(rowValues[0], rowValues[1], rowValues[2],
						"", issn, rowValues[4], rowValues[5], rowValues[6], "",
						"", "", rowValues[7], rowValues[8], null, resourcesBuyers,
						null, "");

				customer = new Customer();
				customer.setName(rowValues[15].trim());
				customer.setEngName(rowValues[16].trim());

				List<Customer> customers = new ArrayList<Customer>();
				customers.add(customer);
				journal.setCustomers(customers);
				
				if (isIssn(issn)) {
					long jouSerNo = journalService.getJouSerNoByIssn(issn
							.toUpperCase());

					long cusSerNo = customerService
							.getCusSerNoByName(rowValues[15].trim());
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
							if (journal.getResourcesBuyers().getrCategory()
									.equals(Category.不明)) {
								journal.setExistStatus("資源類型不明");
							} else {
								journal.setExistStatus("正常");
							}
						}
					} else {
						journal.setExistStatus("無此客戶");
					}
				} else {
					journal.setExistStatus("ISSN異常");
				}
				originalData.add(journal);
			}
			
			Iterator<Journal> setIterator = originalData.iterator();
			
			int normal = 0;
			while (setIterator.hasNext()) {
				journal = setIterator.next();
				excelWorkSheet.getData().add(journal);
				if (journal.getExistStatus().equals("正常")) {
					normal = normal + 1;
				}
			}
			
			DataSet<Journal> ds = initDataSet();
			List<Journal> results=ds.getResults();
			
			ds.getPager().setTotalRecord((long)excelWorkSheet.getData().size());
			ds.getPager().setRecordPoint(0);
			
			if(excelWorkSheet.getData().size() < ds.getPager().getRecordPerPage()){
				int i=0;
				while(i < excelWorkSheet.getData().size()){
					results.add(excelWorkSheet.getData().get(i));
					i++;
				}
			} else {
				int i=0;
				while(i < ds.getPager().getRecordPerPage()){
					results.add(excelWorkSheet.getData().get(i));
					i++;
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
	public String paginate() throws Exception {
		clearCheckedItem();

		List<Journal> importList = (List<Journal>) getSession().get("importList");
		
		DataSet<Journal> ds = initDataSet();
		ds.setPager(Pager.getChangedPager(
				getRequest().getParameter("recordPerPage"), getRequest()
						.getParameter("recordPoint"), ds.getPager()));

		if (importList == null){
			return null;
		} else {
			ds.getPager().setTotalRecord((long) importList.size());
			
		}
		
		int first = ds.getPager().getRecordPerPage()
				* (ds.getPager().getCurrentPage() - 1);
		int last = first + ds.getPager().getRecordPerPage();

		List<Journal> results = new ArrayList<Journal>();

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
	}

	public void allCheckedItem() {
		Map<String, Object> checkItemMap = new TreeMap<String, Object>();

		int i = 0;
		while (i < importSerNos.length) {
			checkItemMap.put(importSerNos[i], importSerNos[i]);
			i++;
		}

		getSession().put("checkItemMap", checkItemMap);
	}

	public void clearCheckedItem() {
		Map<String, Object> checkItemMap = new TreeMap<String, Object>();
		getSession().put("checkItemMap", checkItemMap);
	}

	@SuppressWarnings("unchecked")
	public String importData() throws Exception {
		List<Journal> importList = (List<Journal>) getSession().get("importList");

		Map<String, Object> checkItemMap = (TreeMap<String, Object>) getSession()
				.get("checkItemMap");

		if (checkItemMap == null || checkItemMap.size() == 0) {
			addActionError("請選擇一筆或一筆以上的資料");
		}

		if (!hasActionErrors()) {
			Iterator<?> it = checkItemMap.values().iterator();
			List<Journal> importIndexs = new ArrayList<Journal>();
			while (it.hasNext()) {
				String index = it.next().toString();
				
				if(NumberUtils.isDigits(index)){
					if(Integer.parseInt(index) >=0 && Integer.parseInt(index) < importList.size()){
						importIndexs.add(importList.get(Integer.parseInt(index)));
				}
					}
			}

			for (int i = 0; i < importIndexs.size(); i++) {
				long jouSerNo = journalService.getJouSerNoByIssn(importIndexs
						.get(i).getIssn());
				long cusSerNo = customerService.getCusSerNoByName(importIndexs
						.get(i).getCustomers().get(0).getName());

				if (jouSerNo == 0) {
					resourcesBuyers = resourcesBuyersService.save(importIndexs
							.get(i).getResourcesBuyers(), getLoginUser());
					journal = journalService.save(importIndexs.get(i),
							getLoginUser());

					resourcesUnionService.save(new ResourcesUnion(customerService.getBySerNo(cusSerNo), 
							resourcesBuyers, 0L, 0L, journal.getSerNo()),
							getLoginUser());
				} else {
					resourcesUnion = resourcesUnionService.getByObjSerNo(
							jouSerNo, Journal.class);
					resourcesUnionService.save(new ResourcesUnion(customerService.getBySerNo(cusSerNo),
							resourcesUnion.getResourcesBuyers(), 0L, 0L, jouSerNo),
							getLoginUser());

				}
			}

			clearCheckedItem();
			getRequest().setAttribute("successCount", importIndexs.size());
			return VIEW;
		} else {
			paginate();
			return QUEUE;
		}
	}
	
	public void removeSessionObj() {
		getSession().remove("cellNames");
		getSession().remove("importList");
		getSession().remove("total");
		getSession().remove("normal");
		getSession().remove("abnormal");
		getSession().remove("checkItemMap");
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

	public String exports() throws Exception {
		reportFile = "journal_sample.xlsx";

		// Create blank workbook
		XSSFWorkbook workbook = new XSSFWorkbook();
		// Create a blank sheet
		XSSFSheet spreadsheet = workbook.createSheet("journal");
		// Create row object
		XSSFRow row;
		// This data needs to be written (Object[])
		Map<String, Object[]> empinfo = new LinkedHashMap<String, Object[]>();
		empinfo.put("1", new Object[] { "中文刊名", "英文刊名", "英文縮寫刊名", "ISSN", "語文",
				"出版項/出版社", "出版年", "刊別/期刊頻率", "國會分類號", "起始日", "到期日",
				"資源類型(買斷/租用)", "資源分類", "資料庫中文題名", "資料庫英文題名", "購買單位名稱",
				"購買單位英文名稱" });

		empinfo.put("2", new Object[] { "N/A",
				"The New England Journal of Medicine", "", "15334406", "eng",
				"NEJM", "1812", "weekly", "N/A", "N/A", "N/A", "租貸", "期刊", "",
				"", "衛生福利部台北醫院", "" });
		empinfo.put("3", new Object[] { "N/A",
				"The New England Journal of Medicine", "", "15334406", "eng",
				"NEJM", "1812", "weekly", "N/A", "N/A", "N/A", "租貸", "期刊", "",
				"", "衛生福利部桃園醫院", "" });

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
