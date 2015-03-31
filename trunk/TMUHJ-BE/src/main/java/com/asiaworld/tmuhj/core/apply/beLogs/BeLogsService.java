package com.asiaworld.tmuhj.core.apply.beLogs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.asiaworld.tmuhj.core.dao.GenericDaoLog;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.service.GenericServiceLog;

@Service
public class BeLogsService extends GenericServiceLog<BeLogs> {

	@Autowired
	private BeLogsDao dao;

	@Override
	public DataSet<BeLogs> getByRestrictions(DataSet<BeLogs> ds)
			throws Exception {
		
		return dao.getRanks(ds);
	}

	@Override
	protected GenericDaoLog<BeLogs> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

}
