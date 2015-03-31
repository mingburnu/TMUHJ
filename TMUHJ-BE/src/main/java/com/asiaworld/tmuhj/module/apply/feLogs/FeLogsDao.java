package com.asiaworld.tmuhj.module.apply.feLogs;

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
public class FeLogsDao extends ModuleDaoLog<FeLogs> {
	
	@Autowired
	private FeLogs feLogs;

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

		Query listQuery= null;
		SQLQuery totalQuery = null;
		if (entity.getEnd() != null) {
			String listHql = "SELECT F.cusSerNo, F.keyword, count(F.keyword) FROM FeLogs F WHERE F.cDTime >'"
					+ start
					+ "' and F.cDTime <'"
					+ end
					+ "' and F.cusSerNo ='"
					+ entity.getCusSerNo()
					+ "' GROUP BY F.cusSerNo, F.keyword ORDER BY count(F.keyword) DESC";
			String countSql = "SELECT count(*) as total FROM (SELECT count(keyword) FROM FE_Logs WHERE cDTime >'"
					+ start
					+ "' and cDTime <'"
					+ end
					+ "' and cus_SerNo ='"
					+ entity.getCusSerNo()
					+ "' group by cus_SerNo, keyword) as countTotal";

			if (entity.getCusSerNo() == 0) {
				listHql = listHql.replace("' and F.cusSerNo ='" + entity.getCusSerNo(), "");
				countSql = countSql.replace("' and cus_SerNo ='" + entity.getCusSerNo(), "");
			}

			listQuery = getSession().createQuery(listHql);
			totalQuery = getSession().createSQLQuery(countSql);
		} else {
			String listHql = "SELECT F.cusSerNo, F.keyword, count(F.keyword) FROM FeLogs F WHERE F.cDTime >'"
					+ start
					+ "' and F.cusSerNo ='"
					+ entity.getCusSerNo()
					+ "' GROUP BY F.cusSerNo, F.keyword ORDER BY count(F.keyword) DESC";
			String countSql = "SELECT count(*) as total FROM (SELECT count(keyword) FROM FE_Logs WHERE cDTime >'"
					+ start
					+ "' and cus_SerNo ='"
					+ entity.getCusSerNo()
					+ "' group by cus_SerNo, keyword) as countTotal";

			if (entity.getCusSerNo() == 0) {
				listHql = listHql.replace("' and F.cusSerNo ='" + entity.getCusSerNo(), "");
				countSql = countSql.replace("' and cus_SerNo ='" + entity.getCusSerNo(), "");
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
		
		while (iterator.hasNext()) {
			Object[] row = (Object[])iterator.next();
			feLogs = new FeLogs(Act.綜合查詢, row[1].toString(),
					Long.parseLong(row[0].toString()), 0L,
					0L, 0L, 0L);
			feLogs.setCount(Integer.parseInt(row[2].toString()));
			
			ranks.add(feLogs);
		}

		ds.getPager().setTotalRecord(Long.parseLong(total.get(0).toString()));
		ds.setResults(ranks);

		return ds;
	}
}
