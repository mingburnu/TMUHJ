package com.asiaworld.tmuhj.module.apply.journal.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.journal.entity.Journal;
import com.asiaworld.tmuhj.module.apply.journal.entity.JournalDao;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Service
public class JournalService extends GenericServiceFull<Journal> {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private JournalDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Journal> getByRestrictions(DataSet<Journal> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		Journal entity = ds.getEntity();
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		HttpServletRequest request = ServletActionContext.getRequest();

		String recordPerPage = request.getParameter("recordPerPage");
		String recordPoint = request.getParameter("recordPoint");

		Pager pager = ds.getPager();

		if (pager != null) {
			if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
					&& Integer.parseInt(recordPerPage) > 0
					&& recordPoint != null && NumberUtils.isDigits(recordPoint)
					&& Integer.parseInt(recordPoint) >= 0) {
				pager.setRecordPerPage(Integer.parseInt(recordPerPage));
				pager.setCurrentPage(Integer.parseInt(recordPoint)
						/ Integer.parseInt(recordPerPage) + 1);
				pager.setOffset(Integer.parseInt(recordPerPage)
						* (pager.getCurrentPage() - 1));
				pager.setRecordPoint(Integer.parseInt(recordPoint));
				ds.setPager(pager);
			} else if (recordPerPage != null
					&& NumberUtils.isDigits(recordPerPage)
					&& Integer.parseInt(recordPerPage) > 0
					&& recordPoint == null) {
				pager.setRecordPerPage(Integer.parseInt(recordPerPage));
				pager.setRecordPoint(pager.getOffset());
				ds.setPager(pager);
			} else {
				pager.setRecordPoint(pager.getOffset());
				ds.setPager(pager);
			}
		}

		if (StringUtils.isNotEmpty(entity.getChineseTitle())
				&& StringUtils.isNotBlank(entity.getChineseTitle())) {
			restrictions.likeIgnoreCase("chineseTitle",
					entity.getChineseTitle(), MatchMode.ANYWHERE);
		} else if (StringUtils.isNotEmpty(entity.getEnglishTitle())
				&& StringUtils.isNotBlank(entity.getEnglishTitle())) {
			restrictions.likeIgnoreCase("englishTitle",
					entity.getEnglishTitle(), MatchMode.ANYWHERE);
		} else if (StringUtils.isNotEmpty(entity.getIssn())
				&& StringUtils.isNotBlank(entity.getIssn())) {
			restrictions.likeIgnoreCase("issn", entity.getIssn());
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoFull<Journal> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
