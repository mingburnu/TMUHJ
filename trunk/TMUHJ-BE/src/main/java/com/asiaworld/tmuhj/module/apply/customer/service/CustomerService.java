package com.asiaworld.tmuhj.module.apply.customer.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
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
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
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

		if (StringUtils.isNotEmpty(entity.getEngName())
				&& StringUtils.isNotBlank(entity.getEngName())) {
			restrictions.likeIgnoreCase("engName", entity.getEngName(),
					MatchMode.ANYWHERE);
		} else if (StringUtils.isNotEmpty(entity.getName())
				&& StringUtils.isNotBlank(entity.getName())) {
			restrictions.likeIgnoreCase("name", entity.getName(),
					MatchMode.ANYWHERE);
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

	public boolean nameIsExist(Customer entity) throws Exception {
		Assert.notNull(entity);

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("name", entity.getName().trim());

		List<Customer> customers = dao.findByRestrictions(restrictions);
		if (customers == null || customers.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public List<?> getCustomerListByName(String name) throws Exception {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Customer.class);
		criteria.add(Restrictions.like("name", name.trim(), MatchMode.ANYWHERE));
		return criteria.list();
	}
	
	public List<Customer> getAllCustomers(){
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Customer.class);
		
		List<Customer> customers=new ArrayList<Customer>();
		
		Iterator<?> iterator=criteria.list().iterator();
		
		while(iterator.hasNext()){
		Customer customer=(Customer)iterator.next();
		customers.add(customer);
		}
		
		return customers;
	}
}
