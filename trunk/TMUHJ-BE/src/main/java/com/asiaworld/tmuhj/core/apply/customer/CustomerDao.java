package com.asiaworld.tmuhj.core.apply.customer;

import java.util.Map;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Repository;

import com.asiaworld.tmuhj.core.dao.ModuleDaoFull;

@Repository
public class CustomerDao extends ModuleDaoFull<Customer> {

	public void delRelatedObj(long cusSerNo) {

		// SET REFERENTIAL_INTEGRITY FALSE
		SQLQuery fkDisable = getSession().createSQLQuery(
				"SET FOREIGN_KEY_CHECKS=0");
		fkDisable.executeUpdate();

		Map<String, ClassMetadata> map = (Map<String, ClassMetadata>) getSession()
				.getSessionFactory().getAllClassMetadata();
		for (String entityName : map.keySet()) {
			Query query = getSession().createQuery("FROM " + entityName);
			query.setFirstResult(0);
			query.setMaxResults(1);

			if (query.list().toString().contains("accountNumber=")) {
				Query update = getSession()
						.createQuery(
								"UPDATE "
										+ entityName
										+ " SET accountNumber = null where accountNumber != null");
				update.executeUpdate();
			}

		}

		for (String entityName : map.keySet()) {
			Query query = getSession().createQuery("FROM " + entityName);
			query.setFirstResult(0);
			query.setMaxResults(1);
			log.info(query.getQueryString());

			if (query.list().toString().contains("customer=")) {
				Query selQuery = getSession().createQuery(
						"FROM " + entityName + " WHERE customer.serNo="
								+ cusSerNo);
				selQuery.setFirstResult(0);
				selQuery.setMaxResults(1);

				if (selQuery.list().size() > 0) {
					Query delQuery = getSession().createQuery(
							"DELETE FROM " + entityName
									+ " WHERE customer.serNo=" + cusSerNo);
					delQuery.executeUpdate();
				}
			}
		}

		// SET REFERENTIAL_INTEGRITY TRUE
		SQLQuery fkAble = getSession().createSQLQuery(
				"SET FOREIGN_KEY_CHECKS=1");
		fkAble.executeUpdate();
	}
}
