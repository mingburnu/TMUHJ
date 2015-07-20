package com.asiaworld.tmuhj.core.apply.customer;

import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.stereotype.Repository;

import com.asiaworld.tmuhj.core.dao.ModuleDaoFull;

@Repository
public class CustomerDao extends ModuleDaoFull<Customer> {

	public boolean delRelatedObj(long cusSerNo) {

		Map<String, ClassMetadata> map = (Map<String, ClassMetadata>) getSession()
				.getSessionFactory().getAllClassMetadata();

		for (String entityName : map.keySet()) {
			Query query = getSession().createQuery("FROM " + entityName);
			query.setFirstResult(0);
			query.setMaxResults(1);

			if (query.list().toString().contains("customer=")
					&& !query.list().toString().contains("cDTime=")
					&& !query.list().toString().contains("uDTime=")) {
				Query resourceQuery = getSession().createQuery(
						"SELECT COUNT(*) FROM " + entityName
								+ " WHERE customer.serNo=?");

				if ((Long) resourceQuery.setLong(0, cusSerNo).list().get(0) > 0) {
					return false;
				}
			}
		}

		checkFK(false);

		for (String entityName : map.keySet()) {
			Query query = getSession().createQuery("FROM " + entityName);
			query.setFirstResult(0);
			query.setMaxResults(1);

			if (query.list().toString().contains("customer=")) {
				Query delQuery = getSession()
						.createQuery(
								"DELETE FROM " + entityName
										+ " WHERE customer.serNo=?");
				delQuery.setLong(0, cusSerNo).executeUpdate();
			}
		}

		checkFK(false);
		return true;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(Map<String,Object> datas) {
		Query query = getSession().createQuery("SELECT c.name, c.serNo FROM Customer c");
		List<Object[]> rows = query.list();
		
		for (Object[] row : rows) {
		    String name = (String) row[0];
		    Long serNo = (Long) row[1];
		    datas.put(name, serNo);
		}
		
		return datas;
	}
}
