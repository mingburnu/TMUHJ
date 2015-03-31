package com.asiaworld.tmuhj.module.apply.resourcesUnion;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.asiaworld.tmuhj.core.dao.ModuleDaoSerNo;

@Repository
public class ResourcesUnionDao extends ModuleDaoSerNo<ResourcesUnion> {
	@Autowired
	private SessionFactory sessionFactory;
	
	public long countTotal(Criterion criterion) {
		Session session = sessionFactory.getCurrentSession();

		Criteria criteria = session.createCriteria(ResourcesUnion.class);
		criteria.add(criterion);

		criteria.setProjection(Projections.rowCount());
		
		return (Long) criteria.list().get(0);
	}
}
