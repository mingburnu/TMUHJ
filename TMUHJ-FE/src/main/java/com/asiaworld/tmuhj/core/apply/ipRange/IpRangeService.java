package com.asiaworld.tmuhj.core.apply.ipRange;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDao;
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
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<IpRange> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public List<IpRange> getAllIpList() throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		return dao.findByRestrictions(restrictions);	
	}

}
