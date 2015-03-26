package com.asiaworld.tmuhj.core.apply.beLogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Query;
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
		
		Query listQuery= null;
		SQLQuery totalQuery = null;
		if (entity.getEnd() != null) {
			String listHql = "SELECT B.userSerNo, B.cusSerNo, B.actionType, count(B.userSerNo) FROM BeLogs B WHERE B.cDTime >'"
					+ start
					+ "' and B.cDTime <'"
					+ end
					+ "' and B.cusSerNo ='"
					+ entity.getCusSerNo()
					+ "' GROUP BY B.userSerNo ORDER BY count(B.userSerNo) DESC";
			String countSql = "SELECT count(*) as total FROM (SELECT count(fk_account_serNo) FROM BE_Logs WHERE cDTime >'"
					+ start
					+ "' and cDTime <'"
					+ end
					+ "' and fk_customer_serNo ='"
					+ entity.getCusSerNo()
					+ "' group by fk_account_serNo) as countTotal";

			if (entity.getCusSerNo() == 0) {
				listHql = listHql.replace("' and B.cusSerNo ='" + entity.getCusSerNo(), "");
				countSql = countSql.replace("' and fk_customer_serNo ='" + entity.getCusSerNo(), "");
			}

			listQuery=session.createQuery(listHql);
			totalQuery=session.createSQLQuery(countSql);
		} else {
			String listHql = "SELECT B.userSerNo, B.cusSerNo, B.actionType, count(B.userSerNo) FROM BeLogs B WHERE B.cDTime >'"
					+ start
					+ "' and B.cusSerNo ='"
					+ entity.getCusSerNo()
					+ "' GROUP BY B.userSerNo ORDER BY count(B.userSerNo) DESC";
			String countSql = "SELECT count(*) as total FROM (SELECT count(fk_account_serNo) FROM BE_Logs WHERE cDTime >'"
					+ start
					+ "' and fk_customer_serNo ='"
					+ entity.getCusSerNo()
					+ "' group by fk_account_serNo) as countTotal";

			if (entity.getCusSerNo() == 0) {
				listHql = listHql.replace("' and B.cusSerNo ='" + entity.getCusSerNo(), "");
				countSql = countSql.replace("' and fk_customer_serNo ='" + entity.getCusSerNo(), "");
			}

			listQuery=session.createQuery(listHql);
			totalQuery=session.createSQLQuery(countSql);
			
		}
		
		listQuery.setFirstResult(ds.getPager().getOffset());
		listQuery.setMaxResults(ds.getPager().getRecordPerPage());
		
		List<?> data = listQuery.list();
		List<?> total = totalQuery.list();
		Iterator<?> iterator = data.iterator();
		List<BeLogs> ranks = new ArrayList<BeLogs>();
		
		int i = ds.getPager().getOffset() + 1;
		while(iterator.hasNext()){
			Object[] row = (Object[])iterator.next();
			
			beLogs = new BeLogs(Act.valueOf(row[2].toString()), 
					Long.parseLong(row[0].toString()),
					Long.parseLong(row[1].toString()));
			beLogs.setCount(Integer.parseInt(row[3].toString()));
			beLogs.setCustomer(customerService.getBySerNo(beLogs.getCusSerNo()));
			beLogs.setAccountNumber(accountNumberService.getBySerNo(beLogs.getUserSerNo()));
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
