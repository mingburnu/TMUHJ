package com.asiaworld.tmuhj.module.setting.resourcesUnion.entity;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiaworld.tmuhj.core.GenericTest;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionDao;

/**
 * ResourcesUnionDaoTest
 * 
 * @author Roderick
 * @version 2014/10/15
 */
public class ResourcesUnionDaoTest extends GenericTest {
	@Autowired
	private ResourcesUnionDao dao;

	@Test
	public void testCRUD() throws Exception {

		final long cusSerNo1 = 123;
		final long cusSerNo2 = 456;

		// Save dbResourcesUnion1
		ResourcesUnion resourcesUnion1 = new ResourcesUnion();
		resourcesUnion1.setCusSerNo(cusSerNo1);

		ResourcesUnion dbResourcesUnion1 = dao.save(resourcesUnion1);
		final Long resourcesUnionSerNo1 = dbResourcesUnion1.getSerNo();
		Assert.assertEquals(cusSerNo1, dbResourcesUnion1.getCusSerNo());

		// Save dbResourcesUnion2
		ResourcesUnion resourcesUnion2 = new ResourcesUnion();
		resourcesUnion2.setCusSerNo(cusSerNo2);

		ResourcesUnion dbResourcesUnion2 = dao.save(resourcesUnion2);
		final Long resourcesUnionSerNo2 = dbResourcesUnion2.getSerNo();
		Assert.assertEquals(cusSerNo2, dbResourcesUnion2.getCusSerNo());

		// Query by id
		dbResourcesUnion1 = dao.findBySerNo(resourcesUnionSerNo1);
		Assert.assertEquals(cusSerNo1, dbResourcesUnion1.getCusSerNo());

		// update
		final long dbResourcesUnion1UpdNum = 789;
		dbResourcesUnion1.setCusSerNo(dbResourcesUnion1UpdNum);
		boolean updated = true;
		try {
			dao.update(dbResourcesUnion1);
		} catch (Exception e) {
			updated = false;
		}
		Assert.assertTrue(updated);

		// query by condition
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("cusSerNo", dbResourcesUnion1UpdNum);
		List<ResourcesUnion> resourcesUnions = dao
				.findByRestrictions(restrictions);
		Assert.assertEquals(1, resourcesUnions.size());
		Assert.assertEquals(dbResourcesUnion1UpdNum, resourcesUnions.get(0)
				.getCusSerNo());

		// delete by id
		boolean deleted = true;
		try {
			dao.deleteBySerNo(resourcesUnionSerNo1);
			dao.deleteBySerNo(resourcesUnionSerNo2);
		} catch (Exception e) {
			deleted = false;
		}
		Assert.assertTrue(deleted);
	}
}
