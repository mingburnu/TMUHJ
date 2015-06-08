package com.asiaworld.tmuhj.module.apply.database;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

@Service
public class DatabaseService extends GenericServiceFull<Database> {

	@Autowired
	private Customer customer;

	@Autowired
	private DatabaseDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Autowired
	private CustomerService customerService;

	@Override
	public DataSet<Database> getByRestrictions(DataSet<Database> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		Database entity = ds.getEntity();

		String keywords = entity.getKeywords();
		if (StringUtils.isBlank(keywords)) {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		String option = entity.getOption();

		if (StringUtils.isBlank(option)) {
			option = "";
		} else if (option.equals("中文題名")) {
			option = "DBchttitle";
		} else if (option.equals("英文題名")) {
			option = "DBengtitle";
		} else if (option.equals("出版社")) {
			option = "publishname";
		} else if (option.equals("內容描述")) {
			option = "Content";
		} else {
			option = "";
		}

		if (StringUtils.isNotBlank(keywords)) {
			char[] cArray = keywords.toCharArray();
			StringBuilder keywordsBuilder = new StringBuilder();
			for (int i = 0; i < cArray.length; i++) {
				int charCode = (int) cArray[i];
				if (charCode > 65280 && charCode < 65375) {
					int halfChar = charCode - 65248;
					cArray[i] = (char) halfChar;
				}
				keywordsBuilder.append(cArray[i]);
			}

			keywords = keywordsBuilder.toString();

			keywords = keywords.replaceAll(
					"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9]", " ");
			String[] wordArray = keywords.split(" ");

			StringBuilder sqlBuilder = new StringBuilder();
			for (int i = 0; i < wordArray.length; i++) {
				if (!wordArray[i].isEmpty() && !option.isEmpty()) {
					sqlBuilder.append("LOWER(" + option + ") like LOWER('%"
							+ wordArray[i] + "%') or ");
				}
			}

			String sql = sqlBuilder.toString();
			if (sql.isEmpty()) {
				Pager pager = ds.getPager();
				pager.setTotalRecord(0L);
				ds.setPager(pager);
				return ds;
			} else {
				restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
			}
		}
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoFull<Database> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Database> getBySql(DataSet<Database> ds, String keywords)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		if (StringUtils.isBlank(keywords)) {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		if (StringUtils.isNotBlank(keywords)) {
			char[] cArray = keywords.toCharArray();
			StringBuilder keywordsBuilder = new StringBuilder();
			for (int i = 0; i < cArray.length; i++) {
				int charCode = (int) cArray[i];
				if (charCode > 65280 && charCode < 65375) {
					int halfChar = charCode - 65248;
					cArray[i] = (char) halfChar;
				}
				keywordsBuilder.append(cArray[i]);
			}
			keywords = keywordsBuilder.toString();

			keywords = keywords.replaceAll(
					"[^a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9]", " ");
			String[] wordArray = keywords.split(" ");

			StringBuilder sqlBuilder = new StringBuilder();
			for (int i = 0; i < wordArray.length; i++) {
				if (!wordArray[i].isEmpty()) {
					sqlBuilder.append("LOWER(DBchttitle) like LOWER('%"
							+ wordArray[i]
							+ "%') or  LOWER(DBengtitle) like LOWER('%"
							+ wordArray[i] + "%') or ");
				}

			}

			String sql = sqlBuilder.toString();
			if (sql.isEmpty()) {
				Pager pager = ds.getPager();
				pager.setTotalRecord(0L);
				ds.setPager(pager);
				return ds;
			} else {
				restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
			}
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	public DataSet<Database> getByCusSerNo(DataSet<Database> ds, long cusSerNo)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		Pager pager = ds.getPager();

		customer = customerService.getBySerNo(cusSerNo);

		List<ResourcesUnion> resourcesUnionList = null;
		if (cusSerNo > 0) {
			resourcesUnionList = resourcesUnionService.totalDb(customer, pager);
		}

		if (CollectionUtils.isNotEmpty(resourcesUnionList)) {
			StringBuilder sqlBuilder = new StringBuilder();
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				sqlBuilder.append("serNo="
						+ resourcesUnionList.get(i).getDatSerNo() + " or ");
			}

			String sql = sqlBuilder.toString();
			restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		} else {
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		List<Database> results = dao.findByRestrictions(restrictions);
		pager.setTotalRecord(resourcesUnionService.countTotalDb(customer));
		ds.setResults(results);
		ds.setPager(pager);
		return ds;
	}
}
