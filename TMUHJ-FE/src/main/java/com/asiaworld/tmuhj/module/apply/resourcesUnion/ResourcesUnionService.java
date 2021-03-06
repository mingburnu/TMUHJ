package com.asiaworld.tmuhj.module.apply.resourcesUnion;

import java.util.List;

import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDao;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.model.Pager;
import com.asiaworld.tmuhj.core.service.GenericServiceSerNo;
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

		DsRestrictions restrictions = getDsRestrictions();
		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	protected GenericDao<ResourcesUnion> getDao() {
		// TODO Auto-generated method stub
		return dao;
	}

	public List<ResourcesUnion> totalDb(long cusSerNo, Pager pager)
			throws Exception {
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("customer.serNo", cusSerNo),
				Restrictions.gt("datSerNo", 0L));
		return dao.getTotal(andOperator, pager);
	}

	public long countTotalDb(long cusSerNo) {
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("customer.serNo", cusSerNo),
				Restrictions.gt("datSerNo", 0L));

		return dao.countTotal(andOperator);
	}

	public List<ResourcesUnion> totalJournal(long cusSerNo, Pager pager)
			throws Exception {

		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("customer.serNo", cusSerNo),
				Restrictions.ne("jouSerNo", 0L));

		return dao.getTotal(andOperator, pager);
	}

	public long countTotalJournal(long cusSerNo) {
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("customer.serNo", cusSerNo),
				Restrictions.gt("jouSerNo", 0L));

		return dao.countTotal(andOperator);
	}

	public List<ResourcesUnion> totalEbook(long cusSerNo, Pager pager)
			throws Exception {
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("customer.serNo", cusSerNo),
				Restrictions.gt("ebkSerNo", 0L));
		return dao.getTotal(andOperator, pager);
	}

	public long countTotalEbook(long cusSerNo) {
		LogicalExpression andOperator = Restrictions.and(
				Restrictions.eq("customer.serNo", cusSerNo),
				Restrictions.gt("ebkSerNo", 0L));
		return dao.countTotal(andOperator);
	}

	public ResourcesUnion getByObjSerNo(long objSerNo, Class<?> objClass)
			throws Exception {
		DataSet<ResourcesUnion> ds = new DataSet<ResourcesUnion>();
		ds.setPager(new Pager());
		ds.getPager().setRecordPerPage(1);
		DsRestrictions restrictions = getDsRestrictions();

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
		DsRestrictions restrictions = getDsRestrictions();
		restrictions.eq("jouSerNo", jouSerNo);
		restrictions.ne("customer.serNo", 0L);
		return dao.findByRestrictions(restrictions);
	}

	public List<ResourcesUnion> getByEbkSerNo(long ebkSerNo) throws Exception {
		DsRestrictions restrictions = getDsRestrictions();
		restrictions.eq("ebkSerNo", ebkSerNo);
		restrictions.ne("customer.serNo", 0L);
		return dao.findByRestrictions(restrictions);
	}

	public List<ResourcesUnion> getByDatSerNo(long datSerNo) throws Exception {
		DsRestrictions restrictions = getDsRestrictions();
		restrictions.eq("datSerNo", datSerNo);
		restrictions.ne("customer.serNo", 0L);
		return dao.findByRestrictions(restrictions);
	}
}
