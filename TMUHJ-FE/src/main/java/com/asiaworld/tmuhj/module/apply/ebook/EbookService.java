package com.asiaworld.tmuhj.module.apply.ebook;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
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
public class EbookService extends GenericServiceFull<Ebook> {

	@Autowired
	private EbookDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Ebook> getByRestrictions(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		Ebook entity = ds.getEntity();
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

				if (NumberUtils.isDigits(wordArray[i].replace("-", ""))) {
					orGroup.add(Restrictions.eq("isbn",
							Long.parseLong(wordArray[i].replace("-", ""))));
				} else {
					String[] splitMinus = wordArray[i].split("-");

					for (int j = 0; j < splitMinus.length; j++) {
						orGroup.add(Restrictions.ilike("bookName",
								splitMinus[j], MatchMode.ANYWHERE));
						orGroup.add(Restrictions.ilike("publishName",
								splitMinus[j], MatchMode.ANYWHERE));
						orGroup.add(Restrictions.ilike("autherName",
								splitMinus[j], MatchMode.ANYWHERE));
					}
				}
			}

			orGroup.add(Restrictions.eq("isbn", -1L));
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
	protected GenericDao<Ebook> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Ebook> getByOption(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		Ebook entity = ds.getEntity();
		String indexTerm = entity.getIndexTerm();
		String option = entity.getOption();

		if (option.equals("書名")) {
			option = "bookName";
		} else if (option.equals("ISBN")) {
			option = "isbn";
		} else if (option.equals("出版社")) {
			option = "publishName";
		} else if (option.equals("作者")) {
			option = "autherName";
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
				if (option.equals("isbn")) {
					if (NumberUtils.isDigits(wordArray[i].replace("-", ""))) {
						orGroup.add(Restrictions.eq(option,
								Long.parseLong(wordArray[i].replace("-", ""))));
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

	public DataSet<Ebook> getByCusSerNo(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		Ebook entity = ds.getEntity();
		Pager pager = ds.getPager();

		List<ResourcesUnion> resourcesUnionList = null;
		resourcesUnionList = resourcesUnionService.totalEbook(
				entity.getCusSerNo(), pager);

		if (CollectionUtils.isNotEmpty(resourcesUnionList)) {
			Junction orGroup = Restrictions.disjunction();
			for (int i = 0; i < resourcesUnionList.size(); i++) {
				orGroup.add(Restrictions.eq("serNo", resourcesUnionList.get(i)
						.getEbkSerNo()));
			}

			restrictions.customCriterion(orGroup);
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
