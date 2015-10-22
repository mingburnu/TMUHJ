package com.asiaworld.tmuhj.module.apply.ebook;

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
public class EbookService extends GenericServiceFull<Ebook> {

	@Autowired
	private EbookDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Ebook> getByRestrictions(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = getDsRestrictions();
		Ebook entity = ds.getEntity();
		String indexTerm = StringUtils.replaceChars(entity.getIndexTerm()
				.trim(), "－０１２３４５６７８９", "-0123456789");

		if (ISBN_Validator.isIsbn(indexTerm)) {
			restrictions.eq("isbn", Long.parseLong(indexTerm.replace("-", "")));
		} else {
			indexTerm = indexTerm.replaceAll(
					"[^0-9\\p{Ll}\\p{Lm}\\p{Lo}\\p{Lt}\\p{Lu}]", " ");
			Set<String> keywordSet = new HashSet<String>(
					Arrays.asList(indexTerm.split(" ")));
			String[] wordArray = keywordSet.toArray(new String[keywordSet
					.size()]);

			if (!ArrayUtils.isEmpty(wordArray)) {
				Junction or = Restrictions.disjunction();
				Junction bookNameAnd = Restrictions.conjunction();
				Junction publishNameAnd = Restrictions.conjunction();
				Junction autherNameAnd = Restrictions.conjunction();
				for (int i = 0; i < wordArray.length; i++) {
					bookNameAnd.add(Restrictions.ilike("bookName",
							wordArray[i], MatchMode.ANYWHERE));
					publishNameAnd.add(Restrictions.ilike("publishName",
							wordArray[i], MatchMode.ANYWHERE));
					autherNameAnd.add(Restrictions.ilike("autherName",
							wordArray[i], MatchMode.ANYWHERE));
				}

				or.add(bookNameAnd).add(publishNameAnd).add(autherNameAnd);
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
	protected GenericDao<Ebook> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Ebook> getByOption(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = getDsRestrictions();
		Ebook entity = ds.getEntity();
		String option = entity.getOption();
		String indexTerm = StringUtils.replaceChars(entity.getIndexTerm()
				.trim(), "－０１２３４５６７８９", "-0123456789");

		if (option.equals("書名")) {
			option = "bookName";
		} else if (option.equals("ISBN")) {
			option = "isbn";
		} else if (option.equals("出版社")) {
			option = "publishName";
		} else if (option.equals("作者")) {
			option = "autherName";
		}

		if (option.equals("isbn")) {
			if (ISBN_Validator.isIsbn(indexTerm)) {
				restrictions.eq("isbn",
						Long.parseLong(indexTerm.replace("-", "")));
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

	public DataSet<Ebook> getByCusSerNo(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = getDsRestrictions();
		Ebook entity = ds.getEntity();
		Pager pager = ds.getPager();

		List<ResourcesUnion> resourcesUnionList = null;
		resourcesUnionList = resourcesUnionService.totalEbook(
				entity.getCusSerNo(), pager);

		if (CollectionUtils.isNotEmpty(resourcesUnionList)) {
			Junction or = Restrictions.disjunction();
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				or.add(Restrictions.eq("serNo", resourcesUnionList.get(i)
						.getEbkSerNo()));
			}

			restrictions.customCriterion(or);
		} else {
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}

		List<Ebook> results = dao.findByRestrictions(restrictions);
		pager.setTotalRecord(resourcesUnionService.countTotalEbook(entity
				.getCusSerNo()));
		ds.setResults(results);
		ds.setPager(pager);
		return ds;
	}
}
