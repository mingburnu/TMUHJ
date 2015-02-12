package com.asiaworld.tmuhj.module.apply.feLogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.dao.GenericDaoLog;
import com.asiaworld.tmuhj.core.enums.Act;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceLog;

@Service
public class FeLogsService extends GenericServiceLog<FeLogs> {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private FeLogsDao dao;

	@Autowired
	private FeLogs feLogs;

	@Autowired
	private CustomerService customerService;

	@Override
	public DataSet<FeLogs> getByRestrictions(DataSet<FeLogs> ds)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericDaoLog<FeLogs> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public DataSet<FeLogs> getRanks(DataSet<FeLogs> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		FeLogs entity = ds.getEntity();

		String start = "";
		String end = "";
		if (entity.getStart() != null) {
			start = entity.getStart().toString().split("T")[0];
		}

		if (entity.getEnd() != null) {
			end = entity.getEnd().plusDays(1).toString().split("T")[0];
		}

		Session session = sessionFactory.getCurrentSession();

		SQLQuery sqlQuery = null;
		SQLQuery totalQuery = null;
		if (entity.getEnd() != null) {
			String listSql = "SELECT cus_SerNo, keyword, count(keyword) as amount FROM FE_Logs WHERE cDTime >'"
					+ start
					+ "' and cDTime <'"
					+ end
					+ "' and cus_SerNo ='"
					+ entity.getCusSerNo()
					+ "' group by cus_SerNo, keyword Order by amount desc";
			String countSql = "SELECT count(*) as total FROM (SELECT count(keyword) FROM FE_Logs WHERE cDTime >'"
					+ start
					+ "' and cDTime <'"
					+ end
					+ "' and cus_SerNo ='"
					+ entity.getCusSerNo()
					+ "' group by cus_SerNo, keyword) as countTotal";

			if (entity.getCusSerNo() == 0) {
				listSql = listSql.replace(
						"' and cus_SerNo ='" + entity.getCusSerNo(), "");
				countSql = countSql.replace(
						"' and cus_SerNo ='" + entity.getCusSerNo(), "");
			}

			sqlQuery = session.createSQLQuery(listSql);
			totalQuery = session.createSQLQuery(countSql);
		} else {
			String listSql = "SELECT cus_SerNo, keyword, count(keyword) as amount FROM FE_Logs WHERE cDTime >'"
					+ start
					+ "' and cus_SerNo ='"
					+ entity.getCusSerNo()
					+ "' group by cus_SerNo, keyword Order by amount desc";
			String countSql = "SELECT count(*) as total FROM (SELECT count(keyword) FROM FE_Logs WHERE cDTime >'"
					+ start
					+ "' and cus_SerNo ='"
					+ entity.getCusSerNo()
					+ "' group by cus_SerNo, keyword) as countTotal";

			if (entity.getCusSerNo() == 0) {
				listSql = listSql.replace(
						"' and cus_SerNo ='" + entity.getCusSerNo(), "");
				countSql = countSql.replace(
						"' and cus_SerNo ='" + entity.getCusSerNo(), "");
			}

			sqlQuery = session.createSQLQuery(listSql);
			totalQuery = session.createSQLQuery(countSql);
		}

		sqlQuery.setFirstResult(ds.getPager().getOffset());
		sqlQuery.setMaxResults(ds.getPager().getRecordPerPage());

		sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		List<?> data = sqlQuery.list();
		List<?> total = totalQuery.list();

		Iterator<?> iterator = data.iterator();
		List<FeLogs> ranks = new ArrayList<FeLogs>();
		int i = ds.getPager().getOffset() + 1;
		while (iterator.hasNext()) {
			Map<?, ?> row = (Map<?, ?>) iterator.next();

			// 資料庫不同，寫法不一樣
			if (row.get("keyword") != null) {
				feLogs = new FeLogs(Act.綜合查詢, row.get("keyword").toString(),
						Integer.parseInt(row.get("cus_SerNo").toString()), 0,
						0, 0, 0);
				feLogs.setCount(Integer.parseInt(row.get("amount").toString()));
				feLogs.setCustomer(customerService.getBySerNo(feLogs
						.getCusSerNo()));
			} else {
				feLogs = new FeLogs(Act.綜合查詢, row.get("KEYWORD").toString(),
						Integer.parseInt(row.get("CUS_SERNO").toString()), 0,
						0, 0, 0);
				feLogs.setCount(Integer.parseInt(row.get("AMOUNT").toString()));
				feLogs.setCustomer(customerService.getBySerNo(feLogs
						.getCusSerNo()));
			}
			feLogs.setRank(i);
			ranks.add(feLogs);
			i++;
		}

		ds.getPager().setTotalRecord(Long.parseLong(total.get(0).toString()));
		ds.setResults(ranks);

		return ds;
	}
}
