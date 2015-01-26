package com.asiaworld.tmuhj.module.apply.feLogs.service;

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

import com.asiaworld.tmuhj.core.dao.GenericDaoLog;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceLog;
import com.asiaworld.tmuhj.module.apply.feLogs.entity.FeLogs;
import com.asiaworld.tmuhj.module.apply.feLogs.entity.FeLogsDao;
import com.asiaworld.tmuhj.module.enums.Act;

@Service
public class FeLogsService extends GenericServiceLog<FeLogs> {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private FeLogsDao dao;
	
	@Autowired
	private FeLogs feLogs;

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

	public List<FeLogs> getRanks(DataSet<FeLogs> ds) {
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
		if (entity.getEnd() != null) {
			sqlQuery = session
					.createSQLQuery("SELECT keyword, actionType, count(keyword) as amount FROM FE_Logs WHERE cDTime >'"
							+ start
							+ "' and cDTime <'"
							+ end
							+ "' group by keyword Order by amount desc");
		} else {
			sqlQuery = session
					.createSQLQuery("SELECT keyword, actionType, count(keyword) as amount FROM FE_Logs WHERE cDTime >'"
							+ start + "' group by keyword Order by amount desc");
		}

		sqlQuery.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);

		List<?> data = sqlQuery.list();

		Iterator<?> iterator = data.iterator();
		List<FeLogs> ranks = new ArrayList<FeLogs>();
		int i = 1;
		while (iterator.hasNext()) {
			Map<?, ?> row = (Map<?, ?>) iterator.next();
			feLogs = new FeLogs(Act.valueOf(row.get("ACTIONTYPE")
					.toString()), row.get("KEYWORD").toString(), 0, 0, 0, 0, 0);
			feLogs.setCount(Integer.parseInt(row.get("AMOUNT").toString()));
			feLogs.setRank(i);
			log.debug(feLogs);
			ranks.add(feLogs);
			i++;
		}

		return ranks;
	}
}
