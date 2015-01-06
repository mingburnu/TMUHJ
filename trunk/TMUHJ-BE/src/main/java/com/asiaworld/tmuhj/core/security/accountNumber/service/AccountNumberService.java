package com.asiaworld.tmuhj.core.security.accountNumber.service;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.asiaworld.tmuhj.core.dao.GenericDaoFull;
import com.asiaworld.tmuhj.core.dao.DsRestrictions;
import com.asiaworld.tmuhj.core.enums.Role;
import com.asiaworld.tmuhj.core.enums.Status;
import com.asiaworld.tmuhj.core.model.DataSet;
import com.asiaworld.tmuhj.core.security.accountNumber.entity.AccountNumber;
import com.asiaworld.tmuhj.core.security.accountNumber.entity.AccountNumberDao;
import com.asiaworld.tmuhj.core.service.GenericServiceFull;
import com.asiaworld.tmuhj.core.util.EncryptorUtil;
import com.asiaworld.tmuhj.core.util.DsBeanFactory;
import com.asiaworld.tmuhj.module.apply.customer.entity.Customer;
import com.asiaworld.tmuhj.module.apply.customer.service.CustomerService;

/**
 * 使用者 Service
 * 
 * @author Roderick
 * @version 2014/9/30
 */
@Service
public class AccountNumberService extends GenericServiceFull<AccountNumber> {

	@Autowired
	private AccountNumberDao dao;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	protected GenericDaoFull<AccountNumber> getDao() {
		return dao;
	}

	@Override
	public DataSet<AccountNumber> getByRestrictions(DataSet<AccountNumber> ds)
			throws Exception {
		Assert.notNull(ds);
		Assert.notNull(ds.getEntity());

		AccountNumber entity = ds.getEntity();
		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();

		if (StringUtils.isNotEmpty(entity.getUserId())) {
			restrictions.eq("userId", entity.getUserId());
		}
		if (entity.getCustomer() != null
				&& StringUtils.isNotEmpty(entity.getCustomer().getName())) {
			List<?> customerList = customerService.getCustomerListByName(entity
					.getCustomer().getName());
			Iterator<?> iterator = customerList.iterator();

			String sql = "";
			while (iterator.hasNext()) {
				Customer customer = (Customer) iterator.next();
				sql = sql + "cus_serNo=" + customer.getSerNo() + " or ";
			}
			if (!sql.isEmpty()) {
				restrictions.sqlQuery(sql.substring(0, sql.length() - 4));
			} else {
				return ds;
			}

		}

		return dao.findByRestrictions(restrictions, ds);
	}

	@Override
	public AccountNumber save(AccountNumber entity, AccountNumber user)
			throws Exception {
		Assert.notNull(entity);
		// entity.setTimeToSystime();
		entity.initInsert(user);
		if (StringUtils.isNotEmpty(entity.getUserPw())) { // 密碼非空則進行加密
			final String encryptedPassword = EncryptorUtil.encrypt(entity
					.getUserPw());
			entity.setUserPw(encryptedPassword);
		}

		AccountNumber dbEntity = dao.save(entity);
		makeUserInfo(Arrays.asList(dbEntity));

		return dbEntity;
	}

	/**
	 * 檢查登入帳密
	 * 
	 * @param entity
	 * @return
	 * @throws Exception
	 */
	public Boolean checkUserId(AccountNumber entity) throws Exception {
		Assert.notNull(entity);

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("userId", entity.getUserId());
		restrictions.ne("role", Role.使用者);
		List<AccountNumber> secUsers = dao.findByRestrictions(restrictions);
		if (secUsers == null || secUsers.isEmpty()) {
			return false;
		} else {
			return true;
		}

	}

	public Boolean checkUserPw(AccountNumber entity) throws Exception {
		Assert.notNull(entity);

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("userId", entity.getUserId());
		restrictions.ne("role", Role.使用者);
		restrictions.ne("status", Status.審核中);
		restrictions.ne("status", Status.不生效);
		List<AccountNumber> secUsers = dao.findByRestrictions(restrictions);
		if (secUsers == null || secUsers.isEmpty()) {
			return false;
		}
		AccountNumber secUser = secUsers.get(0);

		return EncryptorUtil.checkPassword(entity.getUserPw(),
				secUser.getUserPw());
	}

	public boolean userIdIsExist(AccountNumber entity) throws Exception {
		Assert.notNull(entity);

		DsRestrictions restrictions = DsBeanFactory.getDsRestrictions();
		restrictions.eq("userId", entity.getUserId().trim());
		List<AccountNumber> accountNumbers = dao
				.findByRestrictions(restrictions);
		if (accountNumbers == null || accountNumbers.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

}
