package com.asiaworld.tmuhj.module.apply.ipRange.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.ipRange.entity.IpRange;
import com.asiaworld.tmuhj.module.apply.ipRange.entity.IpRangeDao;

@Service
public class IpRangeService extends GenericServiceFull<IpRange> {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private IpRangeDao dao;

	@Override
	public DataSet<IpRange> getByRestrictions(DataSet<IpRange> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoFull<IpRange> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public List<IpRange> getAllIpList() {
		List<IpRange> allIpList=new ArrayList<IpRange>();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IpRange.class);
		Iterator<?> iterator=criteria.list().iterator();
		
		while(iterator.hasNext()){
			IpRange ipRange=(IpRange) iterator.next();
			allIpList.add(ipRange);
		}
		return allIpList;	
	}

}
