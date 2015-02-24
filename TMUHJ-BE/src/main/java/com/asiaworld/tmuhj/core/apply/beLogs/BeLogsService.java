package com.asiaworld.tmuhj.core.apply.beLogs;

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

import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumberService;
import com.asiaworld.tmuhj.core.apply.customer.CustomerService;
import com.asiaworld.tmuhj.core.apply.enums.Act;
import com.asiaworld.tmuhj.core.dao.GenericDaoLog;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceLog;

@Service
public class BeLogsService extends GenericServiceLog<BeLogs> {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private BeLogsDao dao;

	@Autowired
	private BeLogs beLogs;

	@Autowired
	private AccountNumberService accountNumberService;

	@Autowired
	private CustomerService customerService;

	@Override
	public DataSet<BeLogs> getByRestrictions(DataSet<BeLogs> ds)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public DataSet<BeLogs> getRanks(DataSet<BeLogs> ds) throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		BeLogs entity = ds.getEntity();

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
			String listSql = "SELECT fk_account_serNo, fk_customer_serNo, actionType, count(fk_account_serNo) as amount FROM BE_Logs WHERE cDTime >'"
					+ start
					+ "' and cDTime <'"
					+ end
					+ "' and fk_customer_serNo ='"
					+ entity.getCusSerNo()
					+ "' group by fk_account_serNo Order by amount desc";
			String countSql = "SELECT count(*) as total FROM (SELECT count(fk_account_serNo) FROM BE_Logs WHERE cDTime >'"
					+ start
					+ "' and cDTime <'"
					+ end
					+ "' and fk_customer_serNo ='"
					+ entity.getCusSerNo()
					+ "' group by fk_account_serNo) as countTotal";

			if (entity.getCusSerNo() == 0) {
				listSql = listSql.replace("' and fk_customer_serNo ='" + entity.getCusSerNo(), "");
				countSql = countSql.replace("' and fk_customer_serNo ='" + entity.getCusSerNo(), "");
			}

			sqlQuery = session.createSQLQuery(listSql);
			totalQuery = session.createSQLQuery(countSql);

		} else {
			String listSql = "SELECT fk_account_serNo, fk_customer_serNo, actionType, count(fk_account_serNo) as amount FROM BE_Logs WHERE cDTime >'"
					+ start
					+ "' and fk_customer_serNo ='"
					+ entity.getCusSerNo()
					+ "' group by fk_account_serNo Order by amount desc";
			String countSql = "SELECT count(*) as total FROM (SELECT count(fk_account_serNo) FROM BE_Logs WHERE cDTime >'"
					+ start
					+ "' and fk_customer_serNo ='"
					+ entity.getCusSerNo()
					+ "' group by fk_account_serNo) as countTotal";

			if (entity.getCusSerNo() == 0) {
				listSql = listSql.replace("' and fk_customer_serNo ='" + entity.getCusSerNo(), "");
				countSql = countSql.replace("' and fk_customer_serNo ='" + entity.getCusSerNo(), "");
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
		List<BeLogs> ranks = new ArrayList<BeLogs>();
		int i = ds.getPager().getOffset() + 1;
		while (iterator.hasNext()) {
			Map<?, ?> row = (Map<?, ?>) iterator.next();

			// 資料庫不同，寫法不一樣
			if (row.get("actionType") != null) {
				beLogs = new BeLogs(Act.valueOf(row.get("actionType").toString()), 
						Long.parseLong(row.get("fk_account_serNo").toString()),
						Long.parseLong(row.get("fk_customer_serNo").toString()));
				beLogs.setCount(Integer.parseInt(row.get("amount").toString()));
				beLogs.setCustomer(customerService.getBySerNo(beLogs.getCusSerNo()));
				beLogs.setAccountNumber(accountNumberService.getBySerNo(beLogs.getUserSerNo()));
			} else {
				beLogs = new BeLogs(Act.valueOf(row.get("ACTIONTYPE").toString()), 
						Long.parseLong(row.get("FK_ACCOUNT_SERNO").toString()),
						Long.parseLong(row.get("FK_CUSTOMER_SERNO").toString()));
				beLogs.setCount(Integer.parseInt(row.get("AMOUNT").toString()));
				beLogs.setCustomer(customerService.getBySerNo(beLogs.getCusSerNo()));
				beLogs.setAccountNumber(accountNumberService.getBySerNo(beLogs.getUserSerNo()));
			}

			beLogs.setRank(i);
			ranks.add(beLogs);
			i++;
		}

		ds.getPager().setTotalRecord(Long.parseLong(total.get(0).toString()));
		ds.setResults(ranks);

		return ds;
	}

	@Override
	protected GenericDaoLog<BeLogs> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
