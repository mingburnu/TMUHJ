package com.asiaworld.tmuhj.module.apply.customer.service;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.IiiRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.IiiBeanFactory;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.customer.entity.CustomerDao;

@Service
public class CustomerService extends GenericServiceFull<Customer> {
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
	protected GenericDaoFull<Customer> getDao() {
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
