package com.asiaworld.tmuhj.module.apply.resourcesUnion;

import java.util.List;

import org.hibernate.criterion.LogicalExpression;
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

	public List<ResourcesUnion> totalDb(long cusSerNo) throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.customCriterion(Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("datSerNo", 0L)));
		return dao.findByRestrictions(restrictions);
	}

	public long countTotalDb(long cusSerNo) {
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("datSerNo", 0L));
		
		return dao.countTotal(andOperator);
	}

	public List<ResourcesUnion> totalJournal(long cusSerNo) throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.customCriterion(Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("jouSerNo", 0L)));
		return dao.findByRestrictions(restrictions);
	}

	public long countTotalJournal(long cusSerNo) {
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("jouSerNo", 0L));
		return dao.countTotal(andOperator);
	}

	public List<ResourcesUnion> totalEbook(long cusSerNo) throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.customCriterion(Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("ebkSerNo", 0L)));
		return dao.findByRestrictions(restrictions);
	}

	public long countTotalEbook(long cusSerNo) {
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("cusSerNo", cusSerNo),
				Restrictions.gt("ebkSerNo", 0L));
		return dao.countTotal(andOperator);
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

	public List<ResourcesUnion> getByJouSerNo(long jouSerNo) throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("jouSerNo", jouSerNo);
		return dao.findByRestrictions(restrictions);
	}

	public List<ResourcesUnion> getByEbkSerNo(long ebkSerNo) throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("ebkSerNo", ebkSerNo);
		return dao.findByRestrictions(restrictions);
	}

	public List<ResourcesUnion> getByDatSerNo(long datSerNo) throws Exception {
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("datSerNo", datSerNo);
		return dao.findByRestrictions(restrictions);
	}
}
