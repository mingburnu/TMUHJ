package com.asiaworld.tmuhj.module.apply.ebook.service;

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
import com.asiaworld.tmuhj.module.apply.ebook.entity.Ebook;
import com.asiaworld.tmuhj.module.apply.ebook.entity.EbookDao;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Service
public class EbookService extends GenericServiceFull<Ebook> {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private EbookDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Ebook> getByRestrictions(DataSet<Ebook> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		Ebook entity = ds.getEntity();
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		if (StringUtils.isNotEmpty(entity.getBookName())) {
			restrictions.likeIgnoreCase("bookName", entity.getBookName(),
					MatchMode.ANYWHERE);
		}
		if (entity.getIsbn() > 0) {
			restrictions.eq("isbn", entity.getIsbn());
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoFull<Ebook> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public long getEbkSerNoByIsbn(long isbn) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Ebook.class);
		criteria.add(Restrictions.eq("isbn", isbn));
		if (criteria.list().size() > 0) {
			return ((Ebook) criteria.list().get(0)).getSerNo();
		} else {
			return 0;
		}
	}
}
