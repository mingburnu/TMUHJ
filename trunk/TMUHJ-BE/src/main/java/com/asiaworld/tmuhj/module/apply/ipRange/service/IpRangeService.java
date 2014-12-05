package com.asiaworld.tmuhj.module.apply.ipRange.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.IiiRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.IiiBeanFactory;
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
		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoFull<IpRange> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<IpRange> getByCusSerNo(DataSet<IpRange> ds, long cusSerNo)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		IiiRestrictions restrictions = IiiBeanFactory.getIiiRestrictions();
		
		if(cusSerNo>0){
			restrictions.eq("cusSerNo", cusSerNo);
		}else{
			Pager pager = ds.getPager();
			pager.setTotalRecord(0L);
			ds.setPager(pager);
			return ds;
		}
		
/**		if (StringUtils.isNotEmpty(entity.getCusSerNo())
				&& StringUtils.isNotBlank(entity.getCusSerNo()) {
			restrictions.eq("cusSerNo", entity.getCusSerNo()
					);
		}*/

		return dao.findByRestrictions(restrictions, ds);
	}

}
