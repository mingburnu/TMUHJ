package j.index.module.apply.customer.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import j.index.core.dao.GenericDao;
import j.index.core.dao.IiiRestrictions;
import j.index.core.model.DataSet;
import j.index.core.service.GenericService;
import j.index.core.util.IiiBeanFactory;
import j.index.module.apply.customer.entity.Customer;
import j.index.module.apply.customer.entity.CustomerDao;

@Service
public class CustomerService extends GenericService<Customer> {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private CustomerDao dao;

	@Override
	public DataSet<Customer> getByRestrictions(DataSet<Customer> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		Customer entity = ds.getEntity();

		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();

		if (StringUtils.isNotEmpty(entity.getEngName())
				|| StringUtils.isNotBlank(entity.getEngName())) {
			restrictions.likeIgnoreCase("engName", entity.getEngName());
		}

		if (StringUtils.isNotEmpty(entity.getName())
				|| StringUtils.isNotBlank(entity.getName())) {
			restrictions.likeIgnoreCase("name", entity.getName());
		}

		if (StringUtils.isNotEmpty(entity.getcUid())
				|| StringUtils.isNotBlank(entity.getcUid())) {
			restrictions.likeIgnoreCase("cUid", entity.getcUid());
		}

		restrictions.addOrderAsc("serNo");
		restrictions.addOrderAsc("engName");

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<Customer> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<Customer> getEditedData(DataSet<Customer> ds, long serNo)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();
		restrictions.eq("serNo", serNo);
		return dao.findByRestrictions(restrictions, ds);
	}
}
