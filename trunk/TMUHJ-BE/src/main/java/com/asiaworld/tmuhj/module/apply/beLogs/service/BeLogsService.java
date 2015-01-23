package com.asiaworld.tmuhj.module.apply.beLogs.service;

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

import com.asiaworld.tmuhj.core.dao.GenericDaocDTime;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServicecDTime;
import com.asiaworld.tmuhj.module.apply.beLogs.entity.BeLogs;
import com.asiaworld.tmuhj.module.apply.beLogs.entity.BeLogsDao;
import com.asiaworld.tmuhj.module.enums.Act;

@Service
public class BeLogsService extends GenericServicecDTime<BeLogs> {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private BeLogsDao dao;

	@Override
	public DataSet<BeLogs> getByRestrictions(DataSet<BeLogs> ds)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public List<BeLogs> getRanks(DataSet<BeLogs> ds) {
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
		if (entity.getEnd() != null) {
			sqlQuery = session
					.createSQLQuery("SELECT fk_account_serNo, fk_customer_serNo, actionType, count(fk_account_serNo) as amount FROM BE_Logs WHERE cDTime >'"
							+ start
							+ "' and cDTime <'"
							+ end
							+ "' group by fk_account_serNo Order by amount desc");
		} else {
			sqlQuery = session
					.createSQLQuery("SELECT fk_account_serNo, fk_customer_serNo, actionType, count(fk_account_serNo) as amount FROM BE_Logs WHERE cDTime >'"
							+ start
							+ "' group by fk_account_serNo Order by amount desc");
		}

		sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		List<?> data = sqlQuery.list();

		Iterator<?> iterator = data.iterator();
		List<BeLogs> ranks = new ArrayList<BeLogs>();
		int i = 1;
		while (iterator.hasNext()) {
			Map<?, ?> row = (Map<?, ?>) iterator.next();
			BeLogs beLogs = new BeLogs(Act.valueOf(row.get("ACTIONTYPE")
					.toString()), Long.parseLong(row.get("FK_ACCOUNT_SERNO")
					.toString()), Long.parseLong(row.get("FK_CUSTOMER_SERNO")
					.toString()));

			beLogs.setCount(Integer.parseInt(row.get("AMOUNT").toString()));
			beLogs.setRank(i);
			log.debug(beLogs);
			ranks.add(beLogs);
			i++;
		}

		return ranks;
	}

	@Override
	protected GenericDaocDTime<BeLogs> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
