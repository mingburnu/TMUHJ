package com.asiaworld.tmuhj.module.apply.resourcesUnion;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoSerNo;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceSerNo;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.database.Database;
import com.asiaworld.tmuhj.module.apply.ebook.Ebook;
import com.asiaworld.tmuhj.module.apply.journal.Journal;

@Service
public class ResourcesUnionService extends GenericServiceSerNo<ResourcesUnion> {

	@Autowired
	private ResourcesUnionDao dao;

	@Override
	public DataSet<ResourcesUnion> getByRestrictions(DataSet<ResourcesUnion> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDaoSerNo<ResourcesUnion> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public ResourcesUnion getByObjSerNo(long objSerNo, Class<?> objClass) throws Exception {
		DataSet<ResourcesUnion> ds = new DataSet<ResourcesUnion>();
		ds.setPager(new Pager());
		ds.getPager().setRecordPerPage(1);
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		if (objClass.equals(Journal.class)) {
			restrictions.eq("jouSerNo", objSerNo);
		} else if (objClass.equals(Ebook.class)) {
			restrictions.eq("ebkSerNo", objSerNo);
		} else {
			restrictions.eq("datSerNo", objSerNo);
		}
		
		return dao.findByRestrictions(restrictions, ds).getResults().get(0);
	}

	public boolean isExist(Object obj, Class<?> objClass, long cusSerNo) throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		
		if (objClass.equals(Journal.class)) {
			Journal journal = (Journal) obj;
			restrictions.customCriterion(Restrictions.and(
					Restrictions.eq("jouSerNo", journal.getSerNo()),
					Restrictions.eq("cusSerNo", cusSerNo)));
		} else if (objClass.equals(Ebook.class)) {
			Ebook ebook = (Ebook) obj;
			restrictions.customCriterion(Restrictions.and(
					Restrictions.eq("ebkSerNo", ebook.getSerNo()),
					Restrictions.eq("cusSerNo", cusSerNo)));
		} else {
			Database database = (Database) obj;
			restrictions.customCriterion(Restrictions.and(
					Restrictions.eq("datSerNo", database.getSerNo()),
					Restrictions.eq("cusSerNo", cusSerNo)));
		}

		if (dao.findByRestrictions(restrictions).size() != 0) {
			return true;
		} else {
			return false;
		}
	}

	public List<ResourcesUnion> getResourcesUnionsByObj(Object obj, Class<?> objClass) throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		
		if (objClass.equals(Journal.class)) {
			Journal journal = (Journal) obj;
			restrictions.eq("jouSerNo", journal.getSerNo());
		} else if (objClass.equals(Ebook.class)) {
			Ebook ebook = (Ebook) obj;
			restrictions.eq("ebkSerNo", ebook.getSerNo());
		} else {
			Database database = (Database) obj;
			restrictions.eq("datSerNo", database.getSerNo());
		}

		return dao.findByRestrictions(restrictions);
	}

}
