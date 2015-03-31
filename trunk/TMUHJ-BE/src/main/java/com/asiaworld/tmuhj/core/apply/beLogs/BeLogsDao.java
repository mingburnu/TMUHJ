package com.asiaworld.tmuhj.core.apply.beLogs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.apply.enums.Act;
import com.asiaworld.tmuhj.core.dao.ModuleDaoLog;
import com.asiaworld.tmuhj.core.model.DataSet;

@Repository
public class BeLogsDao extends ModuleDaoLog<BeLogs> {
	
	@Autowired
	private BeLogs beLogs;
	
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

			listQuery=getSession().createQuery(listHql);
			totalQuery=getSession().createSQLQuery(countSql);
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

			listQuery=getSession().createQuery(listHql);
			totalQuery=getSession().createSQLQuery(countSql);
			
		}
		
		listQuery.setFirstResult(ds.getPager().getOffset());
		listQuery.setMaxResults(ds.getPager().getRecordPerPage());
		
		List<?> data = listQuery.list();
		List<?> total = totalQuery.list();
		Iterator<?> iterator = data.iterator();
		List<BeLogs> ranks = new ArrayList<BeLogs>();
		
		while(iterator.hasNext()){
			Object[] row = (Object[])iterator.next();
			
			beLogs = new BeLogs(Act.valueOf(row[2].toString()), 
					Long.parseLong(row[0].toString()),
					Long.parseLong(row[1].toString()));
			beLogs.setCount(Integer.parseInt(row[3].toString()));
			
			ranks.add(beLogs);
		}
		
		ds.getPager().setTotalRecord(Long.parseLong(total.get(0).toString()));
		ds.setResults(ranks);
		
		return ds;
	}
}
