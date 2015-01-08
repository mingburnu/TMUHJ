package com.asiaworld.tmuhj.module.apply.database.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.database.entity.Database;
import com.asiaworld.tmuhj.module.apply.database.entity.DatabaseDao;
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
		Database entity = ds.getEntity();
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		if (StringUtils.isNotEmpty(entity.getDbChtTitle())) {
			restrictions.likeIgnoreCase("dbChtTitle", entity.getDbChtTitle(),
					MatchMode.ANYWHERE);
		}
		if (StringUtils.isNotEmpty(entity.getDbEngTitle())) {
			restrictions.likeIgnoreCase("dbEngTitle", entity.getDbEngTitle(),
					MatchMode.ANYWHERE);
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoFull<Database> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
