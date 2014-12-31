package com.asiaworld.tmuhj.module.apply.database.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.database.entity.Database;
import com.asiaworld.tmuhj.module.apply.database.entity.DatabaseDao;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.service.ResourcesUnionService;

@Service
public class DatabaseService extends GenericServiceFull<Database> {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private DatabaseDao dao;

	@Autowired
	private ResourcesUnionService resourcesUnionService;

	@Override
	public DataSet<Database> getByRestrictions(DataSet<Database> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());
		Database entity = ds.getEntity();
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		HttpServletRequest request = ServletActionContext.getRequest();

		String recordPerPage = request.getParameter("recordPerPage");
		String recordPoint = request.getParameter("recordPoint");

		Pager pager = ds.getPager();

		if (pager != null) {
			if (recordPerPage != null && NumberUtils.isDigits(recordPerPage)
					&& Integer.parseInt(recordPerPage) > 0
					&& recordPoint != null && NumberUtils.isDigits(recordPoint)
					&& Integer.parseInt(recordPoint) >= 0) {
				pager.setRecordPerPage(Integer.parseInt(recordPerPage));
				pager.setCurrentPage(Integer.parseInt(recordPoint)
						/ Integer.parseInt(recordPerPage) + 1);
				pager.setOffset(Integer.parseInt(recordPerPage)
						* (pager.getCurrentPage() - 1));
				pager.setRecordPoint(Integer.parseInt(recordPoint));
				ds.setPager(pager);
			} else if (recordPerPage != null
					&& NumberUtils.isDigits(recordPerPage)
					&& Integer.parseInt(recordPerPage) > 0
					&& recordPoint == null) {
				pager.setRecordPerPage(Integer.parseInt(recordPerPage));
				pager.setRecordPoint(pager.getOffset());
				ds.setPager(pager);
			} else {
				pager.setRecordPoint(pager.getOffset());
				ds.setPager(pager);
			}
		}

		if (StringUtils.isNotEmpty(entity.getDbChtTitle())) {
			restrictions.likeIgnoreCase("dbChtTitle", entity.getDbChtTitle());
		}
		if (StringUtils.isNotEmpty(entity.getDbEngTitle())) {
			restrictions.likeIgnoreCase("dbEngTitle", entity.getDbEngTitle());
		}

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoFull<Database> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
}
