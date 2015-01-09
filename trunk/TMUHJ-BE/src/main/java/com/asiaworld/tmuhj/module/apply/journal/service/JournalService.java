package com.asiaworld.tmuhj.module.apply.journal.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
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
		
		if (StringUtils.isNotEmpty(entity.getChineseTitle())
				&& StringUtils.isNotBlank(entity.getChineseTitle())) {
			restrictions.likeIgnoreCase("chineseTitle",
					entity.getChineseTitle(), MatchMode.ANYWHERE);
		}
		if (StringUtils.isNotEmpty(entity.getEnglishTitle())
				&& StringUtils.isNotBlank(entity.getEnglishTitle())) {
			restrictions.likeIgnoreCase("englishTitle",
					entity.getEnglishTitle(), MatchMode.ANYWHERE);
		}
		if (StringUtils.isNotEmpty(entity.getIssn())
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

	public long getJouSerNoByIssn(String issn) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Journal.class);
		criteria.add(Restrictions.eq("issn", issn));
		if (criteria.list().size() > 0) {
			return ((Journal) criteria.list().get(0)).getSerNo();
		} else {
			return 0;
		}
	}
	
}
