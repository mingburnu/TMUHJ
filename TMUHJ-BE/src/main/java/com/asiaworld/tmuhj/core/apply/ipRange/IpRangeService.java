package com.asiaworld.tmuhj.core.apply.ipRange;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;

@Service
public class IpRangeService extends GenericServiceFull<IpRange> {

	@Autowired
	private IpRangeDao dao;

	@Override
	public DataSet<IpRange> getByRestrictions(DataSet<IpRange> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		IpRange entity = ds.getEntity();
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		
		if (entity.getCustomer().getSerNo() > 0) {
			restrictions.eq("customer.serNo", entity.getCustomer().getSerNo());
		}

		restrictions.addOrderAsc("serNo");
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoFull<IpRange> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public List<IpRange> getAllIpList(long ipRangeSerNo) throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.ne("serNo", ipRangeSerNo);
		
		return dao.findByRestrictions(restrictions);	
	}
	
}
