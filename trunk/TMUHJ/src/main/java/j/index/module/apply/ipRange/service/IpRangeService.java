package j.index.module.apply.ipRange.service;

import java.util.ArrayList;
import java.util.Iterator;

import j.index.core.dao.GenericDao;
import j.index.core.dao.IiiRestrictions;
import j.index.core.model.DataSet;
import j.index.core.service.GenericService;
import j.index.core.util.IiiBeanFactory;
import j.index.module.apply.ipRange.entity.IpRange;
import j.index.module.apply.ipRange.entity.IpRangeDao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class IpRangeService extends GenericService<IpRange> {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private IpRangeDao dao;

	@Override
	public DataSet<IpRange> getByRestrictions(DataSet<IpRange> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<IpRange> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public ArrayList<IpRange> getIpList() {
		ArrayList<IpRange> ipList=new ArrayList<IpRange>();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(IpRange.class);
		Iterator<?> iterator = criteria.list().iterator();
		
		while(iterator.hasNext()){
			IpRange ipRange=(IpRange) iterator.next();
			ipList.add(ipRange);
		}
		
		return ipList;	
	}

}
