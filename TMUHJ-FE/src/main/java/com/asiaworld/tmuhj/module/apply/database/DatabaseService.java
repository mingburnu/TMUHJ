package com.asiaworld.tmuhj.module.apply.database;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.dao.GenericDao;
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

		if (!ArrayUtils.isEmpty(wordArray)) {
			Junction orGroup = Restrictions.disjunction();
			for (int i = 0; i < wordArray.length; i++) {
				orGroup.add(Restrictions.ilike("dbChtTitle", wordArray[i],
						MatchMode.ANYWHERE));
				orGroup.add(Restrictions.ilike("dbEngTitle", wordArray[i],
						MatchMode.ANYWHERE));
			}

			restrictions.customCriterion(orGroup);

		} else {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<Database> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Database> getByOption(DataSet<Database> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		Database entity = ds.getEntity();
		String keywords = entity.getKeywords();
		String option = entity.getOption();

		if (option.equals("中文題名")) {
			option = "dbChtTitle";
		} else if (option.equals("英文題名")) {
			option = "dbEngTitle";
		} else if (option.equals("出版社")) {
			option = "publishName";
		} else if (option.equals("內容描述")) {
			option = "content";
		} else {
			option = "";
		}

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

		if (!ArrayUtils.isEmpty(wordArray) && StringUtils.isNotBlank(option)) {
			Junction orGroup = Restrictions.disjunction();
			for (int i = 0; i < wordArray.length; i++) {
				orGroup.add(Restrictions.ilike(option, wordArray[i],
						MatchMode.ANYWHERE));
			}

			restrictions.customCriterion(orGroup);

		} else {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
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

		List<ResourcesUnion> resourcesUnionList = resourcesUnionService
				.totalDb(customer, pager);

		if (CollectionUtils.isNotEmpty(resourcesUnionList)) {
			Junction orGroup = Restrictions.disjunction();
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				orGroup.add(Restrictions.eq("serNo", resourcesUnionList.get(i)
						.getDatSerNo()));
			}

			restrictions.customCriterion(orGroup);
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
