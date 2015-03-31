package com.asiaworld.tmuhj.core.apply.customer;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.asiaworld.tmuhj.core.dao.ModuleDaoFull;

@Repository
public class CustomerDao extends ModuleDaoFull<Customer> {

	public void delRelatedObj(long cusSerNo) {

		SQLQuery showTables = getSession().createSQLQuery("SHOW TABLES");
		showTables.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<?> list = showTables.list();

		int i = 0;
		while (i < list.size()) {
			Map<?, ?> row = (Map<?, ?>) list.get(i);

			// 資料庫不同，寫法不一樣
			if (row.get("TABLE_NAME") != null) {
				if (!row.get("TABLE_NAME").toString()
						.equalsIgnoreCase(row.get("TABLE_NAME").toString())) {
					Query delQuery = getSession().createQuery(
							"DELETE FROM :tableName WHERE cusSerNo=:cus_SerNo");
					delQuery.setParameter("tableName", row.get("TABLE_NAME")
							.toString());
					delQuery.setParameter("cus_SerNo", cusSerNo);
					delQuery.executeUpdate();
				}
			} else {
				if (!row.get("table_name").toString()
						.equalsIgnoreCase(row.get("table_name").toString())) {
					Query delQuery = getSession().createQuery(
							"DELETE FROM :tableName WHERE cusSerNo=:cus_SerNo");
					delQuery.setParameter("tableName", row.get("table_name")
							.toString());
					delQuery.setParameter("cus_SerNo", cusSerNo);
					delQuery.executeUpdate();
				}
			}

			i++;
		}

	}
}
