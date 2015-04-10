package com.asiaworld.tmuhj.module.setting.resourcesUnion.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.asiaworld.tmuhj.core.GenericTest;
import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumber;
import com.asiaworld.tmuhj.core.apply.accountNumber.AccountNumberService;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnion;
import com.asiaworld.tmuhj.module.apply.resourcesUnion.ResourcesUnionService;

/**
 * ResourcesUnionServiceTest
 * 
 * @author Roderick
 * @version 2014/10/15
 */
public class ResourcesUnionServiceTest extends GenericTest {
	@Autowired
	private ResourcesUnionService service;

	@Autowired
	private AccountNumberService userService;

	@Autowired
	private DataSet<ResourcesUnion> ds;

	@Test
	public void testCRUD() throws Exception {

		final Long cusSerNo1 = 123l;
		final Long cusSerNo2 = 456l;
		AccountNumber user = userService.getBySerNo(1L);

		// Save resourcesUnion1
		ResourcesUnion resourcesUnion1 = new ResourcesUnion();
//		resourcesUnion1.setCusSerNo(cusSerNo1);
		resourcesUnion1.setDatSerNo(1l);
		resourcesUnion1.setEbkSerNo(1l);
		resourcesUnion1.setJouSerNo(1l);
//		resourcesUnion1.setResSerNo(1l);

		ResourcesUnion dbResourcesUnion1 = service.save(resourcesUnion1, user);
		final Long dbResourcesUnion1SerNo = dbResourcesUnion1.getSerNo();
		Assert.assertEquals(cusSerNo1, dbResourcesUnion1.getCustomer().getSerNo());

		// Save dbResourcesUnion2
		ResourcesUnion resourcesUnion2 = new ResourcesUnion();
//		resourcesUnion2.setCusSerNo(cusSerNo2);
		resourcesUnion2.setDatSerNo(1l);
		resourcesUnion2.setEbkSerNo(1l);
		resourcesUnion2.setJouSerNo(1l);
//		resourcesUnion2.setResSerNo(1l);

		ResourcesUnion dbResourcesUnion2 = service.save(resourcesUnion2, user);
		final Long dbResourcesUnion2SerNo = dbResourcesUnion2.getSerNo();
		Assert.assertEquals(cusSerNo2, dbResourcesUnion2.getCustomer());

		// Query by id
		dbResourcesUnion1 = service.getBySerNo(dbResourcesUnion1SerNo);
		Assert.assertEquals(cusSerNo1, dbResourcesUnion1.getCustomer().getSerNo());

		// update
		final Long dbResourcesUnion1UpdNum = 789L;
//		dbResourcesUnion1.setCusSerNo(dbResourcesUnion1UpdNum);
		dbResourcesUnion1 = service.update(dbResourcesUnion1, user);
		Assert.assertEquals(dbResourcesUnion1UpdNum,
				dbResourcesUnion1.getCustomer().getSerNo());

		// query by condition
		ResourcesUnion queryResourcesUnion = new ResourcesUnion();
		// queryResourcesUnion.setCusSerNo(dbResourcesUnion1UpdNum);
		ds.setEntity(queryResourcesUnion);
		ds = service.getByRestrictions(ds);
		List<ResourcesUnion> resourcesUnions = ds.getResults();
		Assert.assertEquals(2, resourcesUnions.size());
		Assert.assertEquals(dbResourcesUnion1UpdNum, resourcesUnions.get(0)
				.getCustomer().getSerNo());

		// delete by id
		boolean deleted = true;
		try {
			service.deleteBySerNo(dbResourcesUnion1SerNo);
			service.deleteBySerNo(dbResourcesUnion2SerNo);
		} catch (Exception e) {
			deleted = false;
		}
		Assert.assertTrue(deleted);
	}
}
