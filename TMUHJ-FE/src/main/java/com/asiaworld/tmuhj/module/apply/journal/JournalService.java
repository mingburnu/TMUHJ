package com.asiaworld.tmuhj.module.apply.journal;

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
public class JournalService extends GenericServiceFull<Journal> {

	@Autowired
	private JournalDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Journal> getByRestrictions(DataSet<Journal> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = getDsRestrictions();
		Journal entity = ds.getEntity();

		String indexTerm = StringUtils.replaceChars(entity.getIndexTerm()
				.trim(), "－０１２３４５６７８９", "-0123456789");

		if (ISSN_Validator.isIssn(indexTerm)) {
			restrictions.eq("issn", indexTerm.replace("-", "").toUpperCase());
		} else {
			indexTerm = indexTerm.replaceAll(
					"[^0-9\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}]", " ");
			Set<String> keywordSet = new HashSet<String>(
					Arrays.asList(indexTerm.split(" ")));
			String[] wordArray = keywordSet.toArray(new String[keywordSet
					.size()]);

			if (!ArrayUtils.isEmpty(wordArray)) {
				Junction or = Restrictions.disjunction();
				Junction chTitle = Restrictions.conjunction();
				Junction enTitle = Restrictions.conjunction();
				Junction abbrTitle = Restrictions.conjunction();
				Junction publishName = Restrictions.conjunction();
				for (int i = 0; i < wordArray.length; i++) {
					chTitle.add(Restrictions.ilike("chineseTitle",
							wordArray[i], MatchMode.ANYWHERE));
					enTitle.add(Restrictions.ilike("englishTitle",
							wordArray[i], MatchMode.ANYWHERE));
					abbrTitle.add(Restrictions.ilike("abbreviationTitle",
							wordArray[i], MatchMode.ANYWHERE));
					publishName.add(Restrictions.ilike("publishName",
							wordArray[i], MatchMode.ANYWHERE));
				}

				or.add(chTitle).add(enTitle).add(abbrTitle).add(publishName);
				restrictions.customCriterion(or);

			} else {
				Pager pager = ds.getPager();
				pager.setTotalRecord(0L);
				ds.setPager(pager);
				return ds;
			}
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<Journal> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Journal> getByOption(DataSet<Journal> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = getDsRestrictions();
		Journal entity = ds.getEntity();
		String option = entity.getOption();
		String indexTerm = StringUtils.replaceChars(entity.getIndexTerm()
				.trim(), "－０１２３４５６７８９", "-0123456789");

		if (option.equals("中文刊名")) {
			option = "chineseTitle";
		} else if (option.equals("英文刊名")) {
			option = "englishTitle";
		} else if (option.equals("英文縮寫")) {
			option = "abbreviationTitle";
		} else if (option.equals("出版商")) {
			option = "publishName";
		} else if (option.equals("ISSN")) {
			option = "issn";
		}

		if (option.equals("issn")) {
			if (ISSN_Validator.isIssn(indexTerm)) {
				restrictions.eq("issn", indexTerm.toUpperCase()
						.replace("-", ""));
			} else {
				Pager pager = ds.getPager();
				pager.setTotalRecord(0L);
				ds.setPager(pager);
				return ds;
			}
		} else {
			indexTerm = indexTerm.replaceAll(
					"[^0-9\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}]", " ");
			Set<String> keywordSet = new HashSet<String>(
					Arrays.asList(indexTerm.split(" ")));
			String[] wordArray = keywordSet.toArray(new String[keywordSet
					.size()]);

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
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	public DataSet<Journal> getByCusSerNo(DataSet<Journal> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = getDsRestrictions();
		Journal entity = ds.getEntity();
		Pager pager = ds.getPager();

		List<ResourcesUnion> resourcesUnionList = resourcesUnionService
				.totalJournal(entity.getCusSerNo(), pager);

		if (CollectionUtils.isNotEmpty(resourcesUnionList)) {
			Junction or = Restrictions.disjunction();
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				or.add(Restrictions.eq("serNo", resourcesUnionList.get(i)
						.getJouSerNo()));
			}

			restrictions.customCriterion(or);
		} else {
			ds.getPager().setTotalRecord(0L);
			return ds;
		}

		List<Journal> results = dao.findByRestrictions(restrictions);
		ds.getPager().setTotalRecord(
				resourcesUnionService.countTotalJournal(entity.getCusSerNo()));
		ds.setResults(results);
		return ds;
	}
}
