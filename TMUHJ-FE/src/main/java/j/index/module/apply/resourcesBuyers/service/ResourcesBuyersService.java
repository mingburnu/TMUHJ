package j.index.module.apply.resourcesBuyers.service;

import j.index.core.dao.GenericDao;
import j.index.core.dao.IiiRestrictions;
import j.index.core.model.DataSet;
import j.index.core.service.GenericService;
import j.index.core.util.IiiBeanFactory;
import j.index.module.apply.resourcesBuyers.entity.ResourcesBuyers;
import j.index.module.apply.resourcesBuyers.entity.ResourcesBuyersDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ResourcesBuyersService extends GenericService<ResourcesBuyers> {
	@Autowired
	private ResourcesBuyersDao dao;

	@Override
	public DataSet<ResourcesBuyers> getByRestrictions(
			DataSet<ResourcesBuyers> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<ResourcesBuyers> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
