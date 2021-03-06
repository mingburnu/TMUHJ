package com.asiaworld.tmuhj.module.apply.feLogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.asiaworld.tmuhj.core.apply.customer.Customer;
import com.asiaworld.tmuhj.core.apply.enums.Act;
import com.asiaworld.tmuhj.core.converter.JodaTimeConverter;
import com.asiaworld.tmuhj.core.dao.ModuleDaoLog;
import com.asiaworld.tmuhj.core.model.DataSet;

@Repository
public class FeLogsDao extends ModuleDaoLog<FeLogs> {

	@Autowired
	private FeLogs feLogs;

	@Autowired
	private AccountNumber accountNumber;

	@Autowired
	private JodaTimeConverter converter;

	public DataSet<FeLogs> loginRanks(DataSet<FeLogs> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		FeLogs entity = ds.getEntity();

		String start = "";
		String end = "";
		if (entity.getStart() != null) {
			start = converter.convertToString(null, entity.getStart());
		}

		if (entity.getEnd() != null) {
			end = converter.convertToString(null, entity.getEnd().plusDays(1));
		}

		Query listQuery = null;
		Query countQuery = null;
		if (entity.getEnd() != null) {
			String listHql = "SELECT F.accountNumber, F.customer, F.actionType, count(F.accountNumber) FROM FeLogs F WHERE F.cDTime >'"
					+ start
					+ "' and F.cDTime <'"
					+ end
					+ "' and F.customer.serNo ='"
					+ entity.getCustomer().getSerNo()
					+ "' and F.actionType ='"
					+ Act.登入
					+ "' GROUP BY F.accountNumber ORDER BY count(F.accountNumber) DESC";

			if (entity.getCustomer().getSerNo() == 0) {
				listHql = listHql.replace("' and F.customer.serNo ='"
						+ entity.getCustomer().getSerNo(), "");
			}

			listQuery = getSession().createQuery(listHql);
			countQuery = getSession().createQuery(listHql);
		} else {
			String listHql = "SELECT F.accountNumber, F.customer, F.actionType, count(F.accountNumber) FROM FeLogs F WHERE F.cDTime >'"
					+ start
					+ "' and F.customer.serNo ='"
					+ entity.getCustomer().getSerNo()
					+ "' and F.actionType ='"
					+ Act.登入
					+ "' GROUP BY F.accountNumber ORDER BY count(F.accountNumber) DESC";

			if (entity.getCustomer().getSerNo() == 0) {
				listHql = listHql.replace("' and F.customer.serNo ='"
						+ entity.getCustomer().getSerNo(), "");
			}

			listQuery = getSession().createQuery(listHql);
			countQuery = getSession().createQuery(listHql);
		}

		listQuery.setFirstResult(ds.getPager().getOffset());
		listQuery.setMaxResults(ds.getPager().getRecordPerPage());

		List<?> data = listQuery.list();
		long totalRecord = countQuery.list().size();

		Iterator<?> iterator = data.iterator();
		List<FeLogs> ranks = new ArrayList<FeLogs>();

		int i = 0;
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();

			feLogs = new FeLogs(Act.valueOf(row[2].toString()), null,
					(Customer) row[1], (AccountNumber) row[0], null, null, null);

			feLogs.setCount(Integer.parseInt(row[3].toString()));
			feLogs.setRank(i + ds.getPager().getOffset() + 1);
			ranks.add(feLogs);
			i++;
		}

		log.info("totalRecord:" + totalRecord);
		ds.getPager().setTotalRecord(totalRecord);
		ds.setResults(ranks);

		return ds;
	}

	public DataSet<FeLogs> keywordRanks(DataSet<FeLogs> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		FeLogs entity = ds.getEntity();

		String start = "";
		String end = "";
		if (entity.getStart() != null) {
			start = converter.convertToString(null, entity.getStart());
		}

		if (entity.getEnd() != null) {
			end = converter.convertToString(null, entity.getEnd().plusDays(1));
		}

		Query listQuery = null;
		SQLQuery totalQuery = null;
		if (entity.getEnd() != null) {
			String listHql = "SELECT F.customer, F.keyword, count(F.keyword) FROM FeLogs F WHERE F.cDTime >'"
					+ start
					+ "' and F.cDTime <'"
					+ end
					+ "' and F.customer.serNo ='"
					+ entity.getCustomer().getSerNo()
					+ "' GROUP BY F.customer.serNo, F.keyword ORDER BY count(F.keyword) DESC";
			String countSql = "SELECT count(*) as total FROM (SELECT count(keyword) FROM FE_Logs WHERE cDTime >'"
					+ start
					+ "' and cDTime <'"
					+ end
					+ "' and cus_SerNo ='"
					+ entity.getCustomer().getSerNo()
					+ "' group by cus_SerNo, keyword) as countTotal";

			if (entity.getCustomer().getSerNo() == 0) {
				listHql = listHql.replace("' and F.customer.serNo ='"
						+ entity.getCustomer().getSerNo(), "");
				countSql = countSql.replace("' and cus_SerNo ='"
						+ entity.getCustomer().getSerNo(), "");
			}

			listQuery = getSession().createQuery(listHql);
			totalQuery = getSession().createSQLQuery(countSql);
		} else {
			String listHql = "SELECT F.customer, F.keyword, count(F.keyword) FROM FeLogs F WHERE F.cDTime >'"
					+ start
					+ "' and F.customer.serNo ='"
					+ entity.getCustomer().getSerNo()
					+ "' GROUP BY F.customer.serNo, F.keyword ORDER BY count(F.keyword) DESC";
			String countSql = "SELECT count(*) as total FROM (SELECT count(keyword) FROM FE_Logs WHERE cDTime >'"
					+ start
					+ "' and cus_SerNo ='"
					+ entity.getCustomer().getSerNo()
					+ "' group by cus_SerNo, keyword) as countTotal";

			if (entity.getCustomer().getSerNo() == 0) {
				listHql = listHql.replace("' and F.customer.serNo ='"
						+ entity.getCustomer().getSerNo(), "");
				countSql = countSql.replace("' and cus_SerNo ='"
						+ entity.getCustomer().getSerNo(), "");
			}

			listQuery = getSession().createQuery(listHql);
			totalQuery = getSession().createSQLQuery(countSql);
		}

		listQuery.setFirstResult(ds.getPager().getOffset());
		listQuery.setMaxResults(ds.getPager().getRecordPerPage());

		List<?> data = listQuery.list();
		List<?> total = totalQuery.list();
		Iterator<?> iterator = data.iterator();
		List<FeLogs> ranks = new ArrayList<FeLogs>();

		int i = 0;
		while (iterator.hasNext()) {
			Object[] row = (Object[]) iterator.next();
			feLogs = new FeLogs(Act.綜合查詢, row[1].toString(), (Customer) row[0],
					null, 0L, 0L, 0L);
			feLogs.setCount(Integer.parseInt(row[2].toString()));
			feLogs.setRank(i + ds.getPager().getOffset() + 1);
			ranks.add(feLogs);
			i++;
		}

		ds.getPager().setTotalRecord(Long.parseLong(total.get(0).toString()));
		ds.setResults(ranks);

		return ds;
	}
}
