package com.asiaworld.tmuhj.module.apply.resourcesBuyers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDao;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;

@Service
public class ResourcesBuyersService extends GenericServiceFull<ResourcesBuyers> {

	@Autowired
	private ResourcesBuyersDao dao;

	@Override
	public DataSet<ResourcesBuyers> getByRestrictions(DataSet<ResourcesBuyers> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = getDsRestrictions();
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<ResourcesBuyers> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
