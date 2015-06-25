package com.asiaworld.tmuhj.module.apply.feLogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.asiaworld.tmuhj.core.dao.GenericDao;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceLog;

@Service
public class FeLogsService extends GenericServiceLog<FeLogs> {

	@Autowired
	private FeLogsDao dao;

	@Override
	public DataSet<FeLogs> getByRestrictions(DataSet<FeLogs> ds)
			throws Exception {
		return dao.getRanks(ds);
	}

	@Override
	protected GenericDao<FeLogs> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
