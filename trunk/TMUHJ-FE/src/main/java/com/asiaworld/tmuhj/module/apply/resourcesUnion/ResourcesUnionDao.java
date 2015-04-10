package com.asiaworld.tmuhj.module.apply.resourcesUnion;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

import com.asiaworld.tmuhj.core.dao.ModuleDaoSerNo;
import com.asiaworld.tmuhj.core.model.Pager;

@Repository
public class ResourcesUnionDao extends ModuleDaoSerNo<ResourcesUnion> {
	
	public long countTotal(Criterion criterion) {

		Criteria criteria = getSession().createCriteria(ResourcesUnion.class);
		criteria.add(criterion);

		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.list().get(0);
	}
	
	@SuppressWarnings("unchecked")
	public List<ResourcesUnion> getTotal(Criterion criterion, Pager pager) {

		Criteria criteria = getSession().createCriteria(ResourcesUnion.class);
		criteria.add(criterion);

		criteria.setFirstResult(pager.getOffset());
		criteria.setMaxResults(pager.getRecordPerPage());
		
		return criteria.list();
	}
		
}
