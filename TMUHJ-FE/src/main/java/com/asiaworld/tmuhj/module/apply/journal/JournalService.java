package com.asiaworld.tmuhj.module.apply.journal;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.math.NumberUtils;
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
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
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

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		Journal entity = ds.getEntity();
		String indexTerm = entity.getIndexTerm();

		char[] cArray = indexTerm.toCharArray();
		StringBuilder indexTermBuilder = new StringBuilder();
		for (int i = 0; i < cArray.length; i++) {
			int charCode = (int) cArray[i];
			if (charCode > 65280 && charCode < 65375) {
				int halfChar = charCode - 65248;
				cArray[i] = (char) halfChar;
			}
			indexTermBuilder.append(cArray[i]);
		}

		indexTerm = indexTermBuilder.toString();
		indexTerm = indexTerm.replaceAll(
				"[^-a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9]", " ");
		String[] wordArray = indexTerm.split(" ");

		if (!ArrayUtils.isEmpty(wordArray)) {
			Junction orGroup = Restrictions.disjunction();
			for (int i = 0; i < wordArray.length; i++) {
				if (wordArray[i].replace("-", "").length() == 8
						&& NumberUtils.isDigits(wordArray[i].replace("-", "")
								.substring(0, 6))
						&& (wordArray[i].replace("-", "").charAt(7) == 'x'
								|| wordArray[i].replace("-", "").charAt(7) == 'X' || CharUtils
									.isAsciiNumeric(wordArray[i].replace("-",
											"").charAt(7)))) {
					orGroup.add(Restrictions.ilike("issn",
							wordArray[i].replace("-", ""), MatchMode.EXACT));
				} else {
					String[] splitMinus = wordArray[i].split("-");

					for (int j = 0; j < splitMinus.length; j++) {
						orGroup.add(Restrictions.ilike("chineseTitle",
								splitMinus[j], MatchMode.ANYWHERE));
						orGroup.add(Restrictions.ilike("englishTitle",
								splitMinus[j], MatchMode.ANYWHERE));
						orGroup.add(Restrictions.ilike("abbreviationTitle",
								splitMinus[j], MatchMode.ANYWHERE));
						orGroup.add(Restrictions.ilike("publishName",
								splitMinus[j], MatchMode.ANYWHERE));
					}
				}
			}

			orGroup.add(Restrictions.eq("serNo", -1L));
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
	protected GenericDao<Journal> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Journal> getByOption(DataSet<Journal> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		Journal entity = ds.getEntity();
		String indexTerm = entity.getIndexTerm();
		String option = entity.getOption();

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

		char[] cArray = indexTerm.toCharArray();
		StringBuilder indexTermBuilder = new StringBuilder();
		for (int i = 0; i < cArray.length; i++) {
			int charCode = (int) cArray[i];
			if (charCode > 65280 && charCode < 65375) {
				int halfChar = charCode - 65248;
				cArray[i] = (char) halfChar;
			}
			indexTermBuilder.append(cArray[i]);
		}

		indexTerm = indexTermBuilder.toString();
		indexTerm = indexTerm.replaceAll(
				"[^-a-zA-Z0-9\u4e00-\u9fa5\u0391-\u03a9\u03b1-\u03c9]", " ");
		String[] wordArray = indexTerm.split(" ");

		if (!ArrayUtils.isEmpty(wordArray)) {
			Junction orGroup = Restrictions.disjunction();
			for (int i = 0; i < wordArray.length; i++) {
				if (option.equals("issn")) {
					if (wordArray[i].replace("-", "").length() == 8
							&& NumberUtils.isDigits(wordArray[i].replace("-",
									"").substring(0, 6))
							&& (wordArray[i].replace("-", "").charAt(7) == 'x'
									|| wordArray[i].replace("-", "").charAt(7) == 'X' || CharUtils
										.isAsciiNumeric(wordArray[i].replace(
												"-", "").charAt(7)))) {
						orGroup.add(Restrictions.ilike("issn",
								wordArray[i].replace("-", ""), MatchMode.EXACT));
					}
				} else {
					String[] splitMinus = wordArray[i].split("-");
					for (int j = 0; j < splitMinus.length; j++) {
						orGroup.add(Restrictions.ilike(option, splitMinus[j],
								MatchMode.ANYWHERE));
					}
				}
			}

			orGroup.add(Restrictions.eq("serNo", -1L));
			restrictions.customCriterion(orGroup);

		} else {
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	public DataSet<Journal> getByCusSerNo(DataSet<Journal> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		Journal entity = ds.getEntity();
		Pager pager = ds.getPager();

		List<ResourcesUnion> resourcesUnionList = resourcesUnionService
				.totalJournal(entity.getCusSerNo(), pager);

		if (CollectionUtils.isNotEmpty(resourcesUnionList)) {
			Junction orGroup = Restrictions.disjunction();
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				orGroup.add(Restrictions.eq("serNo", resourcesUnionList.get(i)
						.getJouSerNo()));
			}

			restrictions.customCriterion(orGroup);
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
