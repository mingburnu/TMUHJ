package com.asiaworld.tmuhj.core.apply.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.dao.GenericDao;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;

@Service
public class CustomerService extends GenericServiceFull<Customer> {

	@Autowired
	private CustomerDao dao;

	@Override
	public DataSet<Customer> getByRestrictions(DataSet<Customer> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		Customer entity = ds.getEntity();
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		if (entity.getOption().equals("entity.engName")) {
			if (StringUtils.isNotBlank(entity.getEngName())) {
				restrictions.likeIgnoreCase("engName", entity.getEngName(),
						MatchMode.ANYWHERE);
			}
		}

		if (entity.getOption().equals("entity.name")) {
			if (StringUtils.isNotBlank(entity.getName())) {
				restrictions.likeIgnoreCase("name", entity.getName(),
						MatchMode.ANYWHERE);
			}
		}

		restrictions.addOrderAsc("serNo");
		restrictions.addOrderAsc("engName");

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<Customer> getDao() {
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

	public long getCusSerNoByName(String name) throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("name", name);

		if (dao.findByRestrictions(restrictions).size() > 0) {
			return dao.findByRestrictions(restrictions).get(0).getSerNo();
		} else {
			return 0;
		}
	}

	public List<Customer> getAllCustomers() throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		return dao.findByRestrictions(restrictions);
	}

	public List<Customer> getUncheckCustomers(List<Customer> checkedCustomers)
			throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		if (checkedCustomers != null) {
			int i = 0;
			while (i < checkedCustomers.size()) {
				restrictions.ne("serNo", checkedCustomers.get(i).getSerNo());
				i++;
			}
		}

		return dao.findByRestrictions(restrictions);
	}

	public Map<String, Object> getCusDatas() {
		return dao.getMap(new HashMap<String, Object>());
	}

	public boolean deleteOwnerObj(long cusSerNo) {
		return dao.delRelatedObj(cusSerNo);
	}
}
