package com.asiaworld.tmuhj.core.apply.accountNumber;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.metadata.ClassMetadata;
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

	public void updateCustomer(AccountNumber accountNumber) {
		Map<String, ClassMetadata> map = (Map<String, ClassMetadata>) getSession()
				.getSessionFactory().getAllClassMetadata();

		for (String entityName : map.keySet()) {
			Query query = getSession().createQuery("FROM " + entityName);
			query.setFirstResult(0);
			query.setMaxResults(1);

			if (query.list().toString().contains("customer=")
					&& query.list().toString().contains("accountNumber=")) {
				Query update = getSession().createQuery(
						"UPDATE " + entityName + " SET customer.serNo ="
								+ accountNumber.getCustomer().getSerNo()
								+ " WHERE accountNumber.serNo ="
								+ accountNumber.getSerNo());
				update.executeUpdate();
			}

		}
	}
}
