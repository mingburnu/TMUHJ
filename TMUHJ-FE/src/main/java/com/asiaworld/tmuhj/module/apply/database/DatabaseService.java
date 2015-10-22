package com.asiaworld.tmuhj.module.apply.database;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDao;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

@Service
public class DatabaseService extends GenericServiceFull<Database> {

	@Autowired
	private DatabaseDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Database> getByRestrictions(DataSet<Database> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = getDsRestrictions();
		Database entity = ds.getEntity();

		String indexTerm = StringUtils.replaceChars(entity.getIndexTerm()
				.trim(), "０１２３４５６７８９", "0123456789");
		indexTerm = indexTerm.replaceAll(
				"[^0-9\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}]", " ");
		Set<String> keywordSet = new HashSet<String>(Arrays.asList(indexTerm
				.split(" ")));
		String[] wordArray = keywordSet.toArray(new String[keywordSet.size()]);

		if (!ArrayUtils.isEmpty(wordArray)) {
			Junction or = Restrictions.disjunction();
			Junction dbChtTitleAnd = Restrictions.conjunction();
			Junction dbEngTitleAnd = Restrictions.conjunction();
			for (int i = 0; i < wordArray.length; i++) {
				dbChtTitleAnd.add(Restrictions.ilike("dbChtTitle",
						wordArray[i], MatchMode.ANYWHERE));
				dbEngTitleAnd.add(Restrictions.ilike("dbEngTitle",
						wordArray[i], MatchMode.ANYWHERE));
			}

			or.add(dbEngTitleAnd).add(dbChtTitleAnd);
			restrictions.customCriterion(or);
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

		DsRestrictions restrictions = getDsRestrictions();
		Database entity = ds.getEntity();
		String option = entity.getOption();

		if (option.equals("中文題名")) {
			option = "dbChtTitle";
		} else if (option.equals("英文題名")) {
			option = "dbEngTitle";
		} else if (option.equals("出版社")) {
			option = "publishName";
		} else if (option.equals("內容描述")) {
			option = "content";
		}

		String indexTerm = StringUtils.replaceChars(entity.getIndexTerm()
				.trim(), "０１２３４５６７８９", "0123456789");
		indexTerm = indexTerm.replaceAll(
				"[^0-9\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}]", " ");
		Set<String> keywordSet = new HashSet<String>(Arrays.asList(indexTerm
				.split(" ")));
		String[] wordArray = keywordSet.toArray(new String[keywordSet.size()]);

		if (!ArrayUtils.isEmpty(wordArray)) {
			for (int i = 0; i < wordArray.length; i++) {
				restrictions.likeIgnoreCase(option, wordArray[i],
						MatchMode.ANYWHERE);
			}
		} else {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	public DataSet<Database> getByCusSerNo(DataSet<Database> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = getDsRestrictions();
		Database entity = ds.getEntity();
		Pager pager = ds.getPager();

		List<ResourcesUnion> resourcesUnionList = resourcesUnionService
				.totalDb(entity.getCusSerNo(), pager);

		if (CollectionUtils.isNotEmpty(resourcesUnionList)) {
			Junction or = Restrictions.disjunction();
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				or.add(Restrictions.eq("serNo", resourcesUnionList.get(i)
						.getDatSerNo()));
			}

			restrictions.customCriterion(or);
		} else {
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		List<Database> results = dao.findByRestrictions(restrictions);
		pager.setTotalRecord(resourcesUnionService.countTotalDb(entity
				.getCusSerNo()));
		ds.setResults(results);
		ds.setPager(pager);
		return ds;
	}
}
