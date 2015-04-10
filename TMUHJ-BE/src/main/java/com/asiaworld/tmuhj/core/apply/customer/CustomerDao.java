package com.asiaworld.tmuhj.core.apply.customer;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.asiaworld.tmuhj.core.dao.ModuleDaoFull;

@Repository
public class CustomerDao extends ModuleDaoFull<Customer> {

	public void delRelatedObj(long cusSerNo) {

		SQLQuery fkDisable = getSession().createSQLQuery(
				"SET FOREIGN_KEY_CHECKS=0");
		fkDisable.executeUpdate();

		SQLQuery showTables = getSession().createSQLQuery("SHOW TABLES");
		showTables.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		List<?> tableList = showTables.list();

		int i = 0;
		while (i < tableList.size()) {
			Map<?, ?> row = (Map<?, ?>) tableList.get(i);

			// 資料庫不同，寫法不一樣
			if (row.get("TABLE_NAME") != null) {

				SQLQuery columnQuery = getSession()
						.createSQLQuery(
								"SHOW COLUMNS FROM "
										+ row.get("TABLE_NAME").toString());
				columnQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				List<?> columnList = columnQuery.list();

				if (columnList.toString().toUpperCase()
						.contains("cus_serNo".toUpperCase())) {
					SQLQuery delQuery = getSession().createSQLQuery(
							"DELETE FROM " + row.get("TABLE_NAME").toString()
									+ " WHERE cus_serNo=" + cusSerNo);
					delQuery.executeUpdate();
				}

			} else {

				SQLQuery columnQuery = getSession()
						.createSQLQuery(
								"SHOW COLUMNS FROM "
										+ row.get("table_name").toString());
				columnQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
				List<?> columnList = columnQuery.list();

				if (columnList.toString().toUpperCase()
						.contains("cus_serNo".toUpperCase())) {
					SQLQuery delQuery = getSession().createSQLQuery(
							"DELETE FROM " + row.get("table_name").toString()
									+ " WHERE cus_serNo=" + cusSerNo);
					delQuery.executeUpdate();
				}

			}

			i++;
		}
	}
}
