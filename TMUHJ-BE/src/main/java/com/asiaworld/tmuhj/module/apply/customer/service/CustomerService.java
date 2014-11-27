package com.asiaworld.tmuhj.module.apply.customer.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
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
				&& StringUtils.isNotBlank(entity.getEngName())) {
			restrictions.likeIgnoreCase("engName", entity.getEngName(),
					MatchMode.ANYWHERE);
		} else if (StringUtils.isNotEmpty(entity.getName())
				&& StringUtils.isNotBlank(entity.getName())) {
			restrictions.likeIgnoreCase("name", entity.getName(),
					MatchMode.ANYWHERE);
			// } else {
			// return null;
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

	public boolean nameIsExist(Customer entity)
			throws Exception {
		Assert.notNull(entity);

		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();
		restrictions.eq("name", entity.getName().trim());

		List<Customer> customers = dao.findByRestrictions(restrictions);
		if (customers == null || customers.isEmpty()) {
			return false;
		} else {
			return true;
		}
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
