package com.asiaworld.tmuhj.module.apply.database.service;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.database.entity.Database;
import com.asiaworld.tmuhj.module.apply.database.entity.DatabaseDao;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.entity.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Service
public class DatabaseService extends GenericServiceFull<Database> {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DatabaseDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Database> getByRestrictions(DataSet<Database> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		Database entity = ds.getEntity();

		String keywords = entity.getKeywords();
		if (keywords == null || keywords.trim().equals("")) {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		String option = entity.getOption();

		if (option == null) {
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

		if (StringUtils.isNotEmpty(keywords)) {
			char[] cArray = keywords.toCharArray();
			StringBuilder keywordsBuilder = new StringBuilder("");
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

			StringBuilder sqlBuilder = new StringBuilder("");
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

		if (keywords == null || keywords.trim().equals("")) {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		if (StringUtils.isNotEmpty(keywords)) {
			char[] cArray = keywords.toCharArray();
			StringBuilder keywordsBuilder = new StringBuilder("");
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

			StringBuilder sqlBuilder = new StringBuilder("");
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

		ArrayList<ResourcesUnion> resourcesUnionList = null;
		if (cusSerNo > 0) {
			resourcesUnionList = resourcesUnionService.totalDb(cusSerNo);
		}

		if (resourcesUnionList != null && !resourcesUnionList.isEmpty()
				&& resourcesUnionList.size() > 0) {
			StringBuilder sqlBuilder = new StringBuilder("");
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				sqlBuilder.append("serNo="
						+ resourcesUnionList.get(i).getDatSerNo() + " or ");
			}

			String sql = sqlBuilder.toString();
			restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
		} else {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}
		return dao.findByRestrictions(restrictions, ds);
	}
}
