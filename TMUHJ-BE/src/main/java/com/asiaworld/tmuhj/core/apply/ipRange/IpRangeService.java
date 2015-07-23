package com.asiaworld.tmuhj.core.apply.ipRange;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.dao.GenericDao;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;

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
		DsRestrictions restrictions = getDsRestrictions();

		restrictions.eq("customer.serNo", entity.getCustomer().getSerNo());

		restrictions.addOrderAsc("serNo");
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<IpRange> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public List<IpRange> getAllIpList(long ipRangeSerNo) throws Exception {
		DsRestrictions restrictions = getDsRestrictions();
		restrictions.ne("serNo", ipRangeSerNo);

		return dao.findByRestrictions(restrictions);
	}

	public IpRange getTargetEntity(DataSet<IpRange> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		IpRange entity = ds.getEntity();
		DsRestrictions restrictions = getDsRestrictions();

		if (entity.getSerNo() != null) {
			restrictions.eq("serNo", entity.getSerNo());
		} else {
			restrictions.eq("serNo", -1L);
		}

		if (entity.getCustomer() != null) {
			restrictions.eq("customer.serNo", entity.getCustomer().getSerNo());
		} else {
			restrictions.eq("customer.serNo", -1L);
		}

		List<IpRange> results = dao.findByRestrictions(restrictions);

		if (results.size() == 1) {
			return results.get(0);
		} else {
			return null;
		}
	}

}
