package com.asiaworld.tmuhj.core.apply.accountNumber;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.asiaworld.tmuhj.core.dao.GenericHibernateDaoFull;

/**
 * 使用者 Dao
 * 
 * @author Roderick
 * @version 2014/9/29
 */
@Repository
public class AccountNumberDao extends GenericHibernateDaoFull<AccountNumber> {
	
	public long countByCusSerNo(long cusSerNo) {
		Criteria criteria = getSession().createCriteria(AccountNumber.class);
		criteria.add(Restrictions.eq("cusSerNo", cusSerNo));
		criteria.setProjection(Projections.rowCount());
		Long totalUser = (Long) criteria.list().get(0);

		return totalUser;
	}
}
