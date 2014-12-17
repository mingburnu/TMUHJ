package com.asiaworld.tmuhj.module.apply.ipRange.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
		IpRange entity = ds.getEntity();

		HttpServletRequest request = ServletActionContext.getRequest();
		
		String recordPerPage = request.getParameter("recordPerPage");
		String recordPoint = request.getParameter("recordPoint");

		Pager pager = ds.getPager();

		if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0 && recordPoint != null
				&& NumberUtils.isDigits(recordPoint)
				&& Integer.parseInt(recordPoint) >= 0) {
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			pager.setCurrentPage(Integer.parseInt(recordPoint)
					/ Integer.parseInt(recordPerPage) + 1);
			pager.setOffset(Integer.parseInt(recordPerPage)
					* (pager.getCurrentPage() - 1));
			pager.setRecordPoint(Integer.parseInt(recordPoint));
			ds.setPager(pager);
		} else if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
				&& Integer.parseInt(recordPerPage) > 0 && recordPoint == null) {
			pager.setRecordPerPage(Integer.parseInt(recordPerPage));
			pager.setRecordPoint(pager.getOffset());
			ds.setPager(pager);
		} else {
			pager.setRecordPoint(pager.getOffset());
			ds.setPager(pager);
		}
		
		if (entity.getCusSerNo() > 0) {
			restrictions.eq("cusSerNo", entity.getCusSerNo());
		}

		restrictions.addOrderAsc("serNo");
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
	
	public List<IpRange> getAllIpList(long ipRangeSerNo) {
		List<IpRange> allIpList=new ArrayList<IpRange>();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IpRange.class);
		criteria.add(Restrictions.ne("serNo", ipRangeSerNo));
		Iterator<?> iterator=criteria.list().iterator();
		
		while(iterator.hasNext()){
			IpRange ipRange=(IpRange) iterator.next();
			allIpList.add(ipRange);
		}
		return allIpList;	
	}
	
	public List<?> getOwnerIpRangeByCusSerNo(long cusSerNo) {
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IpRange.class);
		criteria.add(Restrictions.eq("cusSerNo", cusSerNo));
		return criteria.list();
	}
}
